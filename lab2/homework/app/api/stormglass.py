from datetime import datetime, timedelta
from statistics import mean, stdev

import requests


def parse_results(results):
    for result in results:
        for hour in result["hours"]:
            for attr, items in hour.items():
                if attr == "time":
                    continue
                items = [item["value"] for item in items]
                avg_value = round(mean(items), 2)
                hour[attr] = avg_value

    attributes = set(results[0]["hours"][0].keys())
    parsed_results = []
    for result in results:
        parsed_result = dict()
        for attr in attributes:
            if attr == "time":
                continue
            vals = [hour[attr] for hour in result["hours"]]
            attr_val = dict()
            attr_val["min"] = round(min(vals), 2)
            attr_val["max"] = round(max(vals), 2)
            attr_val["avg"] = round(mean(vals), 2)
            attr_val["stdev"] = round(stdev(vals), 2)
            parsed_result[attr] = attr_val

        parsed_results.append(parsed_result)

    return parsed_results


class StormGlassAPI:
    def __init__(self, key):
        self.base = "https://api.stormglass.io/v1/weather/point"
        self.headers = {"Authorization": key}
        self.params = ','.join(
            ["airTemperature", "pressure", "cloudCover", "gust", "humidity",
             "precipitation", "visibility", "windSpeed"]),

    def request(self, lat, lng, date_from, date_to):
        params = {"lat": lat, "lng": lng, "params": self.params}

        results = []
        curr_date = date_from
        while curr_date <= date_to:
            start = str(int(datetime.timestamp(curr_date)))
            end = str(int(datetime.timestamp(curr_date + timedelta(days=1))))
            params["start"] = start
            params["end"] = end

            response = requests.get(
                self.base,
                params=params,
                headers=self.headers)

            result = response.json()

            results.append(result)
            curr_date += timedelta(days=1)

        results = parse_results(results)
        return results
