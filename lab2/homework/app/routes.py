from app import app
from app.forms import UserInputForm
from flask import render_template, redirect, url_for, session, request
from app.form_parser import parse_form


@app.route("/", methods=["GET", "POST"])
def index():
    form = UserInputForm()
    if form.validate_on_submit():
        session["form"] = request.form
        return redirect(url_for("results"))
    return render_template("index.html", form=form)


@app.route("/results")
def results():
    city, date_from, date_to = parse_form(session["form"])

    return render_template("result.html")
