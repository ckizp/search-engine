import requests
from bs4 import BeautifulSoup
from flask import Flask, redirect, render_template, request, url_for

app = Flask(__name__, template_folder='./templates')

@app.route('/')
def home():
    # Make a request to the website
    url = "https://fr.vikidia.org/wiki/Pomme"
    response = requests.get(url)
    # Parse the HTML content using BeautifulSoup
    soup = BeautifulSoup(response.content, 'html.parser')
    # Extract the title, image and all h1, h2, h3, p, ul, li tags from the article

    remove_string_title = "Vikidia, "
    for title in soup.find_all('title'):
        if remove_string_title in title.text:
            title.string = title.text.replace(remove_string_title, "")

    image = soup.find('div', class_='mw-parser-output').find('img')
    contents = soup.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    # Remove the string " [modifier | modifier le wikicode]" from each h2 tag's text
    remove_string = "[modifier | modifier le wikicode]"
    for content in contents:
        if remove_string in content.text:
            content.string = content.text.replace(remove_string, "")
    # Render the data to the HTML template
    return render_template('index.html', title=title, image=image, contents=contents)


@app.route('/article', methods=['GET', 'POST'])
def article():
    # Define the title, image, and contents variables again
    url = "https://fr.vikidia.org/wiki/Pomme"
    response = requests.get(url)
    soup = BeautifulSoup(response.content, 'html.parser')
    remove_string_title = "Vikidia, "
    for title in soup.find_all('title'):
        if remove_string_title in title.text:
            title.string = title.text.replace(remove_string_title, "")
    title = title.text
    image = soup.find('div', class_='mw-parser-output').find('img')
    contents = soup.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    remove_string = "[modifier | modifier le wikicode]"
    for content in contents:
        if remove_string in content.text:
            content.string = content.text.replace(remove_string, "")

    if request.method == 'POST':
        # do stuff when the form is submitted

        # redirect to end the POST handling
        # the redirect can be to the same route or somewhere else
        return redirect(url_for('home'))

    # show the form, it wasn't submitted
    return render_template('article.html', title=title, image=image, contents=contents)


@app.route('/result_search', methods=['GET', 'POST'])
def result_search():
    # Define the title, image, and contents variables again
    url = "https://fr.vikidia.org/wiki/Pomme"
    response = requests.get(url)
    soup = BeautifulSoup(response.content, 'html.parser')
    remove_string_title = "Vikidia, "
    for title in soup.find_all('title'):
        if remove_string_title in title.text:
            title.string = title.text.replace(remove_string_title, "")
    title = title.text
    image = soup.find('div', class_='mw-parser-output').find('img')
    contents = soup.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    remove_string = "[modifier | modifier le wikicode]"
    for content in contents:
        if remove_string in content.text:
            content.string = content.text.replace(remove_string, "")

    if request.method == 'POST':
        # do stuff when the form is submitted

        # redirect to end the POST handling
        # the redirect can be to the same route or somewhere else
        return redirect(url_for('home'))

    # show the form, it wasn't submitted
    return render_template('result_search.html', title=title, image=image, contents=contents)

if __name__ == '__main__':
    app.run(debug=True)
