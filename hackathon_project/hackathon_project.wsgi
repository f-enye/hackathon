#!/usr/bin/python
import sys
import logging
logging.basicConfig(stream=sys.stderr)
sys.path.insert(0,"/var/www/hackathon_project/")

from hackathon_project import app as application
application.secret_key = 'THIS IS A SECRET KEY'
