import generated.stock_exchange_pb2 as stock
import generated.stock_exchange_pb2_grpc as stock_grpc

from concurrent import futures
from datetime import datetime
import grpc
import logging
import random
from statistics import mean
import time


class CompanyData:
    def __init__(self, spreads, stock_status, transactions):
        self.spreads = spreads
        self.stock_status = stock_status
        self.transactions = transactions


def generate_company_data(min_spread, max_spread, min_transactions,
                          max_transactions):
    # generate some random company data
    spread_values = [round(random.uniform(min_spread, max_spread), 2)
                     for _ in range(24)]

    spread_changes = [0 for _ in range(24)]
    for i in range(1, len(spread_values)):
        spread_changes[i] = round(spread_values[i] - spread_values[i - 1], 2)
    spread_changes[0] = round(mean(spread_changes[1:]), 2)

    spreads = [stock.Spread(value=spread_values[i],
                            changeFromLast=spread_changes[i])
               for i in range(24)]

    avg_spread_value = mean(spread_values)

    if avg_spread_value < -2:
        stock_status = stock.LOSING
    elif -2 <= avg_spread_value <= 2:
        stock_status = stock.SAME
    else:
        stock_status = stock.GAINING

    transactions = random.randint(min_transactions, max_transactions)

    return CompanyData(spreads, stock_status, transactions)


class StockExchangeServicer(stock_grpc.StockExchangeInformatorServicer):
    def __init__(self):
        # this should be e. g. read from file in production
        self.companies = ["Middleware", "ZeroC", "gRPC",
                          "Centralized Systems"]

        self.company_data = dict()

        # generate some random data
        self.company_data["Middleware"] = \
            generate_company_data(-5, -0.5, 10, 30)

        self.company_data["ZeroC"] = \
            generate_company_data(-15, -5, 100, 200)

        self.company_data["gRPC"] = \
            generate_company_data(-2, 2, 50, 100)

        self.company_data["Centralized Systems"] = \
            generate_company_data(5, 20, 100, 300)

    def observe(self, request, context):
        try:
            company_name = request.companyName
        except Exception as e:
            context.set_details(str(e))
            context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
            return stock.StockInfo()

        if company_name not in self.companies:
            context.set_details("Company " + company_name + "does not exist!")
            context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
            return stock.StockInfo()

        while True:
            time.sleep(random.randint(3, 6))
            curr_hour = datetime.now().hour
            company = self.company_data[company_name]

            timestamp = datetime.now().strftime("%d/%m/%Y, %H:%M:%S")
            spreads = company.spreads[:curr_hour]

            last_spread = round(company.spreads[curr_hour].value, 2)
            last_spread += round(random.uniform(-0.2, 0.2), 2)
            diff = round(last_spread - company.spreads[curr_hour].value, 2)
            last_spread = stock.Spread(value=last_spread,
                                       changeFromLast=diff)

            transactions = int(company.transactions * (curr_hour / 24))
            stock_status = company.stock_status

            stock_info = stock.StockInfo(companyName=company_name,
                                         timestamp=timestamp,
                                         spreads=spreads,
                                         lastSpread=last_spread,
                                         transactions=transactions,
                                         stockStatus=stock_status)
            yield stock_info

    def ping(self, request, context):
        return stock.Empty()


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    stock_grpc.add_StockExchangeInformatorServicer_to_server(
        StockExchangeServicer(), server)
    server.add_insecure_port('[::]:50051')
    print("Server starting work.")
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    logging.basicConfig()
    serve()
