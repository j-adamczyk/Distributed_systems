from flask import Flask
from flask_wtf import CSRFProtect

from config import Config

app = Flask(__name__)
app.config['SECRET_KEY'] = "not-so-secret"
csfr = CSRFProtect(app)
config = Config()

from app import routes
