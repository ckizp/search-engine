import requests
from bs4 import BeautifulSoup
from flask import Flask, redirect, render_template, request, url_for

app = Flask(__name__, template_folder='./templates')

# Choix d'un mot au hasard
import random
import os

@app.route('/')
def home():
    
    """URL 1 """

    with open('test.txt', 'r') as f:
        mots = f.readlines()
    mot_choisi = random.choice(mots).strip()
    url = 'https://fr.vikidia.org/wiki/{}'.format(mot_choisi)
    response = requests.get(url)
    soup = BeautifulSoup(response.content, 'html.parser')
    title_tag = soup.find('title')
    title = title_tag.string.replace(" - l’encyclopédie des 8-13 ans", " ")
    title_tag.string.replace_with(title)
    first_heading = soup.find('h1', {'class': 'firstHeading'})
    heading_text = first_heading.get_text()
    contents = soup.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    remove_string_h2 = "[modifier | modifier le wikicode]"


    """ URL 2"""
    with open('test.txt', 'r') as f:
        mots = f.readlines()
    mot_choisi = random.choice(mots).strip()
    url2 = 'https://fr.vikidia.org/wiki/{}'.format(mot_choisi)
    response2 = requests.get(url2)
    soup2 = BeautifulSoup(response2.content, 'html.parser')
    title_tag2 = soup2.find('title')
    title2 = title_tag2.string.replace(" - l’encyclopédie des 8-13 ans", " ")
    title_tag2.string.replace_with(title2)
    first_heading2 = soup.find('h1', {'class': 'firstHeading'})
    heading_text2 = first_heading2.get_text()
    contents2 = soup2.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    remove_string_h2 = "[modifier | modifier le wikicode]"

    """ URL 3 """
    with open('test.txt', 'r') as f:
        mots = f.readlines()
    mot_choisi = random.choice(mots).strip()
    url3 = 'https://fr.vikidia.org/wiki/{}'.format(mot_choisi)
    response3 = requests.get(url3)
    soup3 = BeautifulSoup(response3.content, 'html.parser')
    title_tag3 = soup3.find('title')
    title3 = title_tag3.string.replace(" - l’encyclopédie des 8-13 ans", " ")
    title_tag3.string.replace_with(title3)
    contents3 = soup3.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    first_heading3 = soup.find('h1', {'class': 'firstHeading'})
    heading_text3 = first_heading3.get_text()
    remove_string_h2 = "[modifier | modifier le wikicode]"


    """ URL 4 """
    with open('test.txt', 'r') as f:
        mots = f.readlines()
    mot_choisi = random.choice(mots).strip()
    url4 = 'https://fr.vikidia.org/wiki/{}'.format(mot_choisi)
    response4 = requests.get(url4)
    soup4 = BeautifulSoup(response4.content, 'html.parser')
    title_tag4 = soup4.find('title')
    title4 = title_tag4.string.replace(" - l’encyclopédie des 8-13 ans", " ")
    title_tag4.string.replace_with(title4)
    contents4 = soup4.find_all(['h1', 'h2', 'h3', 'p', 'ul', 'li'])
    first_heading4 = soup.find('h1', {'class': 'firstHeading'})
    heading_text4 = first_heading4.get_text()
    remove_string_h2 = "[modifier | modifier le wikicode]"

    # Lecture de l'article pour la page d'accueil
    with open('test.txt', 'r') as f:
        article = f.read()

    for h2 in soup.find_all('h3'):
        if remove_string_h2 in h2.text:
            h2.string = h2.text.replace(remove_string_h2, "")

    return render_template('index.html', title=title, contents=contents,contents2=contents2,contents3=contents3,contents4=contents4,article=article,heading_text=heading_text,heading_text2=heading_text2,heading_text3=heading_text3,heading_text4=heading_text4)


if __name__ == '__main__':
    app.run(debug=True)
