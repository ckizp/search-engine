import requests
from bs4 import BeautifulSoup
from flask import Flask, redirect, render_template, request, url_for

app = Flask(__name__, template_folder='./templates')

@app.route('/')
def home():
    """
    The home function is the main function of this application.
    It makes a request to the website, parses its HTML content using BeautifulSoup and extracts all h2, h3, p and ul tags from it.
    Then it renders the data to an HTML template.

    :return: The html template with the data
    """
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
    remove_string_h2 = "[modifier | modifier le wikicode]"
    for h2 in soup.find_all('h2'):
        if remove_string_h2 in h2.text:
            h2.string = h2.text.replace(remove_string_h2, "")
    # Render the data to the HTML template
    return render_template('index.html', title=title, image=image, contents=contents)


@app.route('/article', methods=['GET', 'POST'])
def article():
    """
    The article function is used to display the article page.
    It takes no arguments and returns a rendered template of the article page.

    :return: The article
    """
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
    remove_string_h2 = "[modifier | modifier le wikicode]"
    for content in contents:
        if remove_string_h2 in content.text:
            content.string = content.text.replace(remove_string_h2, "")

    if request.method == 'POST':
        # do stuff when the form is submitted

        # redirect to end the POST handling
        # the redirect can be to the same route or somewhere else
        return redirect(url_for('home'))

    # show the form, it wasn't submitted
    return render_template('article.html', title=title, image=image, contents=contents)

if __name__ == '__main__':
    app.run(debug=True)
