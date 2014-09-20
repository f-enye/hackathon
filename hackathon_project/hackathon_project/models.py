from hackathon_project import db

class User(db.Model):
	id = db.Column(db.Integer, primary_key = True)
	user_name = db.Column(db.String(120), index = True, unique = True)
	password = db.Column(db.String(120), index = True)

	# login manager required functions
	def is_authenticated(self):
		return True

	def is_active(self):
		return True

	def is_anonymous(self):
		return False

	def get_id(self):
		return unicode(self.id)

	def __repr__(self):
		return '<User %r>' % (self.user_name)

	def serialize(self):
		return {"user_name": self.user_name}