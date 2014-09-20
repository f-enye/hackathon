import secret_key_config
import os

basedir = os.path.abspath(os.path.dirname(__file__))
SQLALCHEMY_DATABASE_URI = 'sqlite:///' + os.path.join(basedir, 'hackathon_project.db')
# Used for migrations
# SQLAlCHEMY_MIGRATE_REPO = os.path.join(basedir, 'db_repository')

CSRF_ENABLED = True
SECRET_KEY = secret_key_config.SECRET_KEY
UPLOAD_FOLDER = '/home/frank/Desktop/'
