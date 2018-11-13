# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

print("Hello World!");

import urllib.request
import csv


def getInfo(tid):
    url = "https://www.imdb.com/title/" + tid
    f = urllib.request.urlopen(url)
    source = str(f.read())
    
    # get name
    namestart = source.find("h1 class") + 12
    nameend = source.find("nbsp", namestart) - 1
    name = source[namestart: nameend]
    realname = name.replace("\\", "")
    
    # get image url
    imgstart = source.find(name + " Poster")
    imgstart = source.find("src=", imgstart) + 5
    imgend = source.find(".jpg", imgstart) + 4
    imgurl = source[imgstart: imgend]
    
    # get year
    yearstart = source.find("titleYear") + 9
    yearstart = source.find("year", yearstart) + 5
    yearend = yearstart + 4
    year = source[yearstart: yearend]
    
    # get sum
    sumstart = source.find("summary_text")
    sumstart = source.find("\\n", sumstart) + 2
    sumend = source.find("\\n", sumstart)
    sumtext = source[sumstart: sumend].strip().replace("\\", "")

    return realname, year, sumtext, imgurl


tids = "tids.csv"
movies = "Movies.csv"
with open(tids) as r, open(movies, 'w', newline = '') as w:
    reader = csv.reader(r)
    writer = csv.writer(w, dialect='excel')
    writer.writerow(['id', 'name', 'year', 'introduction', 'picture_path'])
    for row in reader:
        tid = str(row[0])
        print(tid)
        realname, year, sumtext, imgurl = getInfo(tid)
        l = [tid, realname, year, sumtext, imgurl]
        writer.writerow(l)