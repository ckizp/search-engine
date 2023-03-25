import requests
from bs4 import BeautifulSoup
from flask import Flask, redirect, render_template, request, url_for

app = Flask(__name__, template_folder='./templates')

@app.route('/')
def home():

    """ URL 1 """
    url = "https://fr.vikidia.org/wiki/Pomme"
    response = requests.get(url)
    soup = BeautifulSoup(response.content, 'html.parser')
    title_tag = soup.find('title')
    title = title_tag.string.replace(" - l’encyclopédie des 8-13 ans", "")
    title_tag.string.replace_with(title)
    contents = soup.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    remove_string_h2 = "[modifier | modifier le wikicode]"
    
    """ URL 2 """
    url2 = "https://fr.vikidia.org/wiki/BMW"
    response2 = requests.get(url2)
    soup2 = BeautifulSoup(response2.content, 'html.parser')
    title_tag2 = soup2.find('title')
    title2 = title_tag2.string.replace(" - l’encyclopédie des 8-13 ans", "")
    title_tag2.string.replace_with(title2)
    contents2 = soup2.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    remove_string_h2 = "[modifier | modifier le wikicode]"
    
    """ URL 3 """
    url3 = "https://fr.vikidia.org/wiki/Emmanuel_Macron"
    response3 = requests.get(url3)
    soup3 = BeautifulSoup(response3.content, 'html.parser')
    title_tag3 = soup3.find('title')
    title3 = title_tag3.string.replace(" - l’encyclopédie des 8-13 ans", "")
    title_tag3.string.replace_with(title3)
    contents3 = soup3.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])


    """ URL 4 """
    url4 = "https://fr.vikidia.org/wiki/Nice"
    response4 = requests.get(url4)
    soup4 = BeautifulSoup(response4.content, 'html.parser')
    title_tag4 = soup4.find('title')
    title4 = title_tag4.string.replace(" - l’encyclopédie des 8-13 ans", "")
    title_tag4.string.replace_with(title4)
    contents4 = soup4.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    
    for h2 in soup.find_all('h3'):
        if remove_string_h2 in h2.text:
            h2.string = h2.text.replace(remove_string_h2, "")
            
    for h2 in soup2.find_all('h3'):
        if remove_string_h2 in h2.text:
            h2.string = h2.text.replace(remove_string_h2, "")
    
    return render_template('index.html', title=title,  contents=contents, contents2=contents2,contents3=contents3,contents4=contents4)


if __name__ == '__main__':
    app.run(debug=True)
