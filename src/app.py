import requests
from bs4 import BeautifulSoup
import subprocess
import threading
from flask import Flask, redirect, render_template, request, url_for
import random
import os
from jinja2 import Environment
from urllib.parse import unquote

import os



app = Flask(__name__, template_folder='./templates')
app.jinja_env.globals.update(len=len)
app.jinja_env.globals.update(zip=zip)


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
    with open('test.txt', 'r') as f:
        article = f.read()
    for h2 in soup.find_all('h3'):
        if remove_string_h2 in h2.text:
            h2.string = h2.text.replace(remove_string_h2, "")

    return render_template('index.html', title=title, contents=contents)


""" GLOBAL VARIABLE : IF ! NULL, THEN == ERROR """
result = None

@app.route('/search', methods=['POST'])
def search():
    global result 
    root_path = os.path.abspath(os.path.dirname(__file__))
    jar_file_path = os.path.join(root_path, "static", "jar", "search-engine.jar")
    jar_file_path = "C:/Users/akira/Desktop/web_release_0.1/web_app_0.1/static/jar/search-engine.jar"
    keyword = request.form['keyword']
    
    def run_jar():
        global result 
        process = subprocess.Popen(["java", "-jar", jar_file_path, keyword], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        output, error = process.communicate()
        if error:
            result = "Une erreur est survenue : {}".format(error.decode())
        else:
            result = output.decode(encoding="ISO-8859-1")
    
    thread = threading.Thread(target=run_jar)
    thread.start()
    
    if result:
        urls = result.split('\n')[:-1]
        titles = []
        links  = []
        for url in urls:
            if "https://fr.vikidia.org/wiki/" in url:
                title = url.split("https://fr.vikidia.org/wiki/")[1].split(",")[0]
                title = unquote(title)
                title = title.replace('_', ' ')
                titles.append(title)
                link = url.split('=')[1].split(']')[0]
                links.append(link)
        return render_template('results.html', urls=urls, titles=titles, links=links)
    else:
        return render_template('loading.html')


@app.route('/licence')
def licence():
    return render_template('licence.html')

def refresh():
        return redirect(url_for('index'))
""" FLASK RUN """
if __name__ == '__main__':
    app.run(debug=True)
