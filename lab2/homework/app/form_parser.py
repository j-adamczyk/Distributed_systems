from datetime import datetime
from time import strptime, time


def parse_form(form):
    city = form["city"]

    date_from = form["date_from"]
    date_to = form["date_to"]

    if not date_from and not date_to:  # no date provided - use current date
        today = time()
        date_from = date_to = today
    elif not date_from and date_to:  # from current date to date_to
        date_from = time()
        date_to = strptime(date_to, "%d-%m-%Y")
    elif date_from and not date_to:  # only use date_from
        date_to = date_from = strptime(date_from, "%d-%m-%Y")
    else:  # both dates provided, only convert type
        date_from = strptime(date_from, "%d-%m-%Y")
        date_to = strptime(date_to, "%d-%m-%Y")

    return city, date_from, date_to
