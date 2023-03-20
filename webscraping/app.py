import requests
from bs4 import BeautifulSoup
from flask import Flask, render_template

app = Flask(__name__, template_folder='./templates')

@app.route('/')
def home():
	# Make a request to the website
	url = "https://fr.vikidia.org/wiki/Pomme"
	response = requests.get(url)
	# Parse the HTML content using BeautifulSoup
	soup = BeautifulSoup(response.content, 'html.parser')
	# Extract the title and first paragraph of the article
	title = soup.find('title').text
	first_paragraph = soup.find('div', class_='mw-parser-output').find('p').text
	# Render the data to the HTML template
	return render_template('home.html', title=title, summary=first_paragraph)

if __name__ == '__main__':
	app.run(debug=True)