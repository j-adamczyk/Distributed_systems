from flask_wtf import FlaskForm
from wtforms import StringField, SubmitField
from wtforms.validators import DataRequired


class UserInputForm(FlaskForm):
    city = StringField("City (MUST be provided)", validators=[DataRequired()])
    date_from = StringField("Date from (format DD-MM-YYYY)")
    date_to = StringField("Date to (format DD-MM-YYYY)")
    submit = SubmitField("Search")
