import time
from datetime import timedelta
from statistics import mean, stdev, StatisticsError


def parse_dark_sky_day_lengths(day_lengths):
    min_day_length = min(day_lengths)
    max_day_length = max(day_lengths)

    start = time.strptime(day_lengths[0], "%H:%M:%S")
    start = timedelta(hours=start.tm_hour,
                      minutes=start.tm_min,
                      seconds=start.tm_sec).total_seconds()

    end = time.strptime(day_lengths[-1], "%H:%M:%S")
    end = timedelta(hours=end.tm_hour,
                    minutes=end.tm_min,
                    seconds=end.tm_sec).total_seconds()

    diff = end - start
    if diff < 0:
        diff = start - end
        day_length_change = "-" + str(timedelta(seconds=diff))
    else:
        day_length_change = str(timedelta(seconds=diff))

    result = {"min": min_day_length,
              "max": max_day_length,
              "change": day_length_change}
    return result


def parse_dark_sky_precipitations(precipitations):
    avg = mean([prec["avg"] for prec in precipitations])
    maximum = max([prec["max"] for prec in precipitations])
    avg_prob = mean([prec["prob"] for prec in precipitations])
    types = list({prec["type"] for prec in precipitations})
    result = {"avg": round(avg, 2),
              "max": round(maximum, 2),
              "prob": round(avg_prob, 2),
              "types": types}
    return result


def parse_dark_sky_temperatures(temperatures):
    avgs = [temp["avg"] for temp in temperatures]
    mins = [temp["min"] for temp in temperatures]
    maxs = [temp["max"] for temp in temperatures]
    result = {"avg": round(mean(avgs), 2),
              "min": round(min(mins), 2),
              "max": round(max(maxs), 2)}
    return result


def get_dark_sky_summary(results):
    summary = dict()

    day_lengths = [result["day_length"] for result in results]
    summary["day_length"] = parse_dark_sky_day_lengths(day_lengths)

    precipitations = [result["precipitation"] for result in results]
    summary["precipitation"] = parse_dark_sky_precipitations(precipitations)

    temps = [result["temperature"] for result in results]
    summary["temperature"] = parse_dark_sky_temperatures(temps)

    apparent_temps = [result["apparentTemperature"] for result in results]
    summary["apparent_temperature"] = parse_dark_sky_temperatures(apparent_temps)

    other_statistics = ["humidity", "pressure", "windSpeed", "windGust",
                        "cloudCover", "visibility"]

    for statistic in other_statistics:
        values = [result[statistic] for result in results]
        result = {"avg": round(mean(values), 2),
                  "min": round(min(values), 2),
                  "max": round(max(values), 2)}
        try:
            dev = round(stdev(values), 2)
        except StatisticsError:
            dev = 0.00
        result["stdev"] = dev
        summary[statistic] = result

    return summary


def get_storm_glass_summary(results):
    summary = dict()

    statistics = ["airTemperature", "cloudCover", "windSpeed", "humidity",
                  "gust", "pressure", "visibility", "precipitation"]

    for statistic in statistics:
        values = [result[statistic] for result in results]
        avg = mean([value["avg"] for value in values])
        minimum = min([value["min"] for value in values])
        maximum = max([value["max"] for value in values])
        dev = mean([value["stdev"] for value in values])
        result = {"avg": round(avg, 2),
                  "min": round(minimum, 2),
                  "max": round(maximum, 2),
                  "stdev": round(dev, 2)}

        if statistic == "airTemperature":
            summary["temperature"] = result
        elif statistic == "gust":
            summary["windGust"] = result
        else:
            summary[statistic] = result

    return summary


def get_summary(dark_sky_results, storm_glass_results):
    dark_sky_summary = get_dark_sky_summary(dark_sky_results)

    storm_glass_summary = get_storm_glass_summary(storm_glass_results)

    return dark_sky_summary, storm_glass_summary
