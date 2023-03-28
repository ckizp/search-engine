#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 28 21:50:32 2023

@author: akira
"""
import jpype


getLink = jpype.getClassPath()
jpype.addClassPath(getLink)
jpype.startJVM()
WebScraping = jpype.JClass("webscraping.WebScraping")
result = WebScraping.scrapeUrl("https://fr.vikidia.org/wiki/Banane")
print(result)
jpype.shutdownJVM()
