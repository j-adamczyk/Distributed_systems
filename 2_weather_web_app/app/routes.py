from app import app, config
from app.api.darksky import DarkSkyAPI
from app.api.stormglass import StormGlassAPI
from app.form_parser import parse_form
from app.forms import UserInputForm
from app.summary_parser import get_summary
from flask import render_template, redirect, url_for, session, request


@app.route("/", methods=["GET", "POST"])
def index():
    form = UserInputForm()
    if form.validate_on_submit():
        session["form"] = request.form
        return redirect(url_for("results"))
    return render_template("index.html", form=form)


@app.route("/results")
def results():
    city = session["form"]["city"]
    lat, lng, date_from, date_to = parse_form(session["form"])

    dark_sky_api = DarkSkyAPI(config.dark_sky_key)
    dark_sky_results = dark_sky_api.request(lat, lng, date_from, date_to)

    storm_glass_api = StormGlassAPI(config.storm_glass_key)
    storm_glass_results = storm_glass_api.request(lat, lng, date_from, date_to)

    summary = get_summary(dark_sky_results, storm_glass_results)
    dark_sky_summary, storm_glass_summary = summary

    print(dark_sky_summary)

    date_from = date_from.strftime("%d/%m/%Y")
    date_to = date_to.strftime("%d/%m/%Y")
    return render_template("results.html",
                           city=city,
                           date_from=date_from,
                           date_to=date_to,
                           dark_sky_summary=dark_sky_summary,
                           storm_glass_summary=storm_glass_summary)
