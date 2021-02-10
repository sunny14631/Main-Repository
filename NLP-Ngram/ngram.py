#Rahul Madala, CMSC 416, Spring 2020
#Project 2: N-Gram
#Project Summary: This program's objective is to read and analyze sentences from a collection
#of text files, and, by using a N-gram model, generate random sentences from those input files.
#This program achieves this by storing the frequencies of all phrases containing N words
#in a dictionary. From this dictionary, the probabilities of the phrases occuring will be calculated
#and used to determine the next phrase of a sentence. When the program initializes, the user will
#enter the number of sentences, the kind of N-gram used via integer, and a list of input files to use.
#For example, "python3 2 3 input.txt" will produce three sentences using a bigram model of text found
#in input.txt.
import re
import random
import sys

#This dictionary is used to store the beginning words of sentences. This will be used to begin
#sentence generation.
start = {
    "": 0
}
#This method is responsible for creating an N-gram dictionary from an input file.
#This dictionary contains the frequencies of phrases of N length as well as storing ending phrases.
def createNgram(file, n):
    #If the user gives invalid input, an empty dictionary will be returned.
    thisDict = dict()
    if n < 1:
        return thisDict
    #This dictionary will be returned containing all frequencies of phrases of n length.
    nGramDictionary = {
        "The": 0
    }
    #Splits the input file into sentences.
    sentenceList = re.split('[?.!]', file)
    for sentence in sentenceList:
        #This gets rid of any quotations and leading and trailing space.
        sentence = sentence.strip('\'“')
        sentence = sentence.strip('\'”')
        sentence = sentence.strip()
        #Splits up the sentence into individual words
        wordList = re.split("\s+", sentence)
        #Checks to see if the given sentence is valid for the N-gram
        if len(wordList) < n:
            continue
        startPhrase = ""
        #Stores the starting phrase into the start dictionary
        for x in range(0, n):
            startPhrase += wordList[x] + " "
        if startPhrase in start:
            start[startPhrase] += 1
        else:
            start[startPhrase] = 1
        if len(wordList) < n:
            continue
        #nLimit helps denote n-tuple phrases that occur consecutively
        nLimit = n
        dictKey = ""
        #wordCount is used to find the end of the sentence
        wordCount = -1
        for word in wordList:
            #base case: adds n words to the new or existing dictKey
            wordCount += 1
            dictKey += word + " "
            nLimit = nLimit - 1
            #When n words are in dictkey, that phrase's count is incremented by 1
            if nLimit == 0:
                dictKey = dictKey.strip(" ")
                if wordCount == len(wordList) - 1:
                    dictKey += "."
                if dictKey in nGramDictionary:
                    nGramDictionary[dictKey] = nGramDictionary[dictKey] + 1
                else:
                    nGramDictionary[dictKey] = 1
                dictKeyList = re.split("\s+", dictKey)
                dictKey = ""
                #Gets rid of the first word in the phrase so the next consecutive n-tuple can be found
                if n == 1:
                    dictKey = ""
                elif n == 2:
                    dictKey = dictKeyList[1] + " "
                else:
                    dictKey = dictKeyList[1] + " " + dictKeyList[2] + " "
                nLimit = 1
    return nGramDictionary
#Sums the total frequencies of all phrases in the dictionary
def calculateTotalOccurences(dictionary):
    total = 0
    for key in dictionary:
        total += dictionary[key]
    return total
#This method randomly selects the next n-words by calculating the relative frequencies
#and probabilities of the word occuring.
def calculateNextWordFreq(word, dictionary, dictionary2):
    #Unigram case
    if not dictionary2:
        chance = random.uniform(0, 1)
        prob = float(0)
        for key in dictionary:
            prob += dictionary[key]/calculateTotalOccurences(dictionary)
            if chance <= prob:
                return key + " "
    #Stores all the frequencies of phrases that occur after word
    nextWordSet = {
        "The": 0
    }
    #Searches through the dictionary for phrases after word
    for key in dictionary:
        wordList = re.split("\s+", key)
        #Adds the phrase to the dictionary and calculates the probability of
        #that phrase occuring.
        if wordList[0] == word:
            x = range(1, len(wordList))
            newKey = ""
            if len(wordList) > 1:
                for wordPlace in x:
                    newKey += wordList[wordPlace] + " "
            newKey = newKey.strip()
            nextWordSet[newKey] = float(dictionary[key]/dictionary2[newKey])
    #Randomly chooses the phrase to return
    chance = random.uniform(0, 1)
    prob = float(0)
    for key in nextWordSet:
        prob += nextWordSet[key]
        if chance <= prob:
            return key + " "
    return ""
#This method generates random sentences by choosing from a start dictionary based on
#probability. Then, it generates next phrases based on the method above.
#If the parser detects an ending syntax, the sentence is returned.
def generateSentence(nGramDict, nLessGramDict, text):
    sentence = ""
    begin = True
    while True:
        #This finds the starting phrase of a sentence based on start dicitonary
        if begin:
            startTotal = calculateTotalOccurences(start)
            chance = random.uniform(0, 1)
            prob = float(0)
            for key in start:
                prob += float(start[key]/startTotal)
                if chance <= prob:
                    sentence += key
                    break
            begin = False
        #The last word of the sentence is found and checked to see if a period is there.
        wordList = re.split("\s+", sentence.rstrip())
        lastWord = wordList[len(wordList) - 1]
        if re.match("(.*)?[.!?]", lastWord):
            sentence.strip(" ")
            return sentence
        matchCount = 0
        matchSentence = list()
        if matchCount == 1:
            return matchSentence[0]
        #If the lastWord does not have a sentence the next phrase is generated.
        sentence += calculateNextWordFreq(lastWord, nGramDict, nLessGramDict)
def main():
    nGram = int(sys.argv[1])
    sentenceLines = int(sys.argv[2])
    fileText = ""
    #Reads files from command line
    for x in range(3, len(sys.argv)):
        filename = sys.argv[x]
        newFile = open(filename)
        fileText += newFile.read() + " "
    fileText = fileText.strip()
    nGramTable = createNgram(fileText, nGram)
    nGramTable2 = createNgram(fileText, nGram - 1)
    while sentenceLines != 0:
        print(generateSentence(nGramTable, nGramTable2, fileText))
        sentenceLines -= 1
if __name__ == '__main__':
    main()