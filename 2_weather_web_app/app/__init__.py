from flask import Flask
from flask_wtf import CSRFProtect

from config import Config

app = Flask(__name__)
app.config['SECRET_KEY'] = "not so secret"
app.config['WTF_CSRF_ENABLED'] = False
config = Config()

from app import routes
