from flask import render_template, url_for, redirect, g, request, flash
from flask.ext.login import login_user, logout_user, current_user, login_required
from hackathon_project import app, db, lm
from forms import LoginForm, SignupForm
from models import User


#################Template Rendering Functions##############
@app.route('/') 
@app.route('/index')
@login_required
def Index():
	return render_template("index.html")

@app.route('/login', methods=['GET', 'POST'])
def Login():
	if g.user is not None and g.user.is_authenticated():
		return redirect(url_for('Index'))

	form = LoginForm()
	if form.validate_on_submit():
		user_name = form.user_name.data
		password = form.password.data

		user = User.query.filter_by(user_name = user_name).first()
		if user is not None and password == user.password:
			login_user(user)
			return redirect(request.args.get("next", url_for("Index")))
		else:
			flash("Invalid username or password")

	return render_template('login.html', title = 'Log In', form = form)

@app.route('/signup', methods=['GET', 'POST'])
def Signup():
	if g.user is not None and g.user.is_authenticated():
		return redirect(url_for('Index'))

	form = SignupForm()
	if form.validate_on_submit():
		user_name = form.user_name.data
		password = form.password.data

		user = User(user_name = user_name, password = password)
		db.session.add(user)
		db.session.commit()

		return redirect(url_for('Login'))

	return render_template('signup.html', title = 'Sign Up', form = form)

@app.route('/logout')
def Logout():
	logout_user()
	return redirect(url_for('Login'))

#############Helper Functions##############
@app.before_request
def BeforeRequest():
	g.user = current_user

#Used for flask-login
@lm.user_loader
def LoadUser(id):
	return User.query.get(int(id))