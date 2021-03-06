#!/usr/bin/python3 

# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

print("Hello World!");

import urllib.request
import csv


def validTid(tid):
    if tid.startswith("tt") and len(tid) == 9:
        return True
    else:
        return False

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
    
    # get rating
    ratingstart = source.find("ratingValue")
    ratingstart = ratingstart + 15
    ratingend = ratingstart + 3
    rating = source[ratingstart: ratingend]
    if rating == "\\n\\":
        rating = "0.0"
    
    return realname, year, sumtext, imgurl, rating


tids = "tids.csv"
movies = "Movies.csv"

# create a set to detect duplicate
l = []

# initialize all counters
num_tids = 0
num_invalid = 0
num_duplicate = 0
num_success = 0

with open(tids) as r, open(movies, 'w', newline = '') as w:
    reader = csv.reader(r)
    writer = csv.writer(w, dialect='excel')
    writer.writerow(['id', 'name', 'year', 'introduction', 'picture_path', 'rating', 'rating_number'])
    for row in reader:
        tid = str(row[0])
        num_tids += 1
        #print(tid)
        if not validTid(tid):
            print(tid + " is invalid tid")
            num_invalid += 1
            continue
        if tid in l:
            print(tid + " is duplicate and will not be processed")
            num_duplicate += 1
            continue
        else:
            l.append(tid)
            print(tid + " success")
            num_success += 1
        realname, year, sumtext, imgurl, rating = getInfo(tid)
        movie = [tid, realname, year, sumtext, imgurl, rating, '1']
        writer.writerow(movie)


# summary
print("---------------  Summary  -----------")
print("Totally ", num_tids, " tids have been processed:")
print("-  ", num_success, " tids success")
print("-  ", num_duplicate, " tids duplicate")
print("-  ", num_invalid, " tids invalid")

