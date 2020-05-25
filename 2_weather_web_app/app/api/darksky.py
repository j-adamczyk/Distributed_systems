from datetime import datetime, timedelta
import requests


def parse_results(results):
    parsed_results = []
    for result in results:
        parsed_result = dict()

        day_length = result["sunsetTime"] - result["sunriseTime"]
        parsed_result["day_length"] = str(timedelta(seconds=day_length))

        precipitation = dict()
        precipitation["avg"] = result["precipIntensity"]
        precipitation["max"] = result["precipIntensityMax"]
        precipitation["prob"] = result["precipProbability"]
        precipitation["type"] = result["precipType"]
        parsed_result["precipitation"] = precipitation

        temp = dict()
        low = result["temperatureMin"]
        high = result["temperatureMax"]
        temp["avg"] = (low + high) / 2
        temp["min"] = low
        temp["max"] = high
        parsed_result["temperature"] = temp

        apparent_temp = dict()
        low = result["apparentTemperatureMin"]
        high = result["apparentTemperatureMax"]
        apparent_temp["avg"] = (low + high) / 2
        apparent_temp["min"] = low
        apparent_temp["max"] = high
        parsed_result["apparentTemperature"] = temp

        parsed_result["humidity"] = result["humidity"]
        parsed_result["pressure"] = result["pressure"]
        parsed_result["windSpeed"] = result["windSpeed"]
        parsed_result["windGust"] = result["windGust"]
        parsed_result["cloudCover"] = result["cloudCover"]
        parsed_result["visibility"] = result["visibility"]

        parsed_results.append(parsed_result)

    return parsed_results


class DarkSkyAPI:
    def __init__(self, key):
        self.base = "https://api.darksky.net/forecast/"
        self.key = key
        self.excludes = "exclude=currently,minutely,hourly,alerts,flags"
        self.units = "units=si"

    def request(self, lat, lng, date_from, date_to):
        base_str = self.base + self.key + "/" + lat + "," + lng + ","
        end_str = "?" + self.excludes + "&" + self.units
        headers = {"User-Agent":
                   "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                   "AppleWebKit/537.36 (KHTML, like Gecko) "
                   "Chrome/71.0.3578.98 Safari/537.36"}

        results = []
        curr_date = date_from
        while curr_date <= date_to:
            timestamp_str = str(int(datetime.timestamp(curr_date)))
            URL = base_str + timestamp_str + end_str
            response = requests.get(url=URL, headers=headers)
            result = response.json()["daily"]["data"][0]
            results.append(result)
            curr_date += timedelta(days=1)

        results = parse_results(results)
        return results
