#Rahul Madala CMSC 416 PA3, March 3, 2020
#The purpose of this program is to develop a POS vocabulary from a training file, storing the frequencies
#of the words and parts of speech in dictionaries below. Once the dictionaries are populated,
#the program reads a text file containing words and punctuation and tags each of them with a POS
#by calculating the highest probability of a tag being associated with a word. These words with POS
#tags are written into a text file where it will be compared with a key text file in scorer.py
import sys
import re
import random
import subprocess
#Handles F(tag|word) (reversed in dictionary)
wordGivenDictionary = {
    "love JJ":0
}
#stores the frequency of all the words in the text
wordDictionary = {
    "Hi":0
}
#stores F(tag|previous tag)
posGivenDictionary = {
    "DF JJ":0
}
#stores frequency of all the tags
posDictionary = {
    "JJ":0
}
#The purpose of this method is to tag each significant word or punctuation with a part of speech
#using the above dictionaries to calculate the most likely tag
def tagTextFile(tagText):
    #splits up the text to be tagged into seperate words
    wordList = re.split("\s+", tagText)
    firstWord = True
    prevPOS = ""
    for word in wordList:
        finalPOS = ""
        posProb = 0
        if word != "[" and word != "]":
            #if the word was not read in the training text file, the default value is "NN"
            if word not in wordDictionary:
                finalPOS = "NN"
            else:
                for key in wordGivenDictionary:
                        wordPOS = key.split()
                        #possible part of speech
                        if word == wordPOS[0]:
                            #P(tag|word)
                            partOneProb = wordGivenDictionary[key]/wordDictionary[word]
                            if firstWord and partOneProb > posProb:
                                posProb = partOneProb
                                finalPOS = wordPOS[1]
                            elif not firstWord and ((wordPOS[1] + " " + prevPOS) in posGivenDictionary):
                                #P(tag|previous tag)
                                partTwoProb = posGivenDictionary[wordPOS[1] + " " + prevPOS]/posDictionary[prevPOS]
                                if partOneProb * partTwoProb > posProb:
                                    posProb = partOneProb * partOneProb
                                    finalPOS = wordPOS[1]
                            #if a unique tag match that was not found in the train text file is encountered,
                            #the first part of the probability is used as a final result
                            elif partOneProb > posProb:
                                posProb = partOneProb
                                finalPOS = wordPOS[1]
            if finalPOS == "":
                finalPOS = ":"
            firstWord = False
            sys.stdout.write(word + "/" + finalPOS + " ")
            #prevPOS keeps track of the previous POS for the purpose of calculating 
            #the second part of the probability
            prevPOS = finalPOS
        elif word == "[":
            sys.stdout.write("\n[ ")
        elif word == "]":
            sys.stdout.write("] ")
#The purpose of this method is to populate the dictionaries above with the frequencies of words,
#parts of speech, parts of speech given another part of speech, and a part of speech given a word.
def readTrain(trainText):
    #splits up the text into distinct POS tokens
    wordList = re.split("\s+", trainText)
    firstTime = True
    prevTag = ""
    for word in wordList:
        #ignores []
        if word != "[" and word != "]" and word != "":
            #Populates the dictionaries by making the key either the POS or word by itself or the word
            #and POS seperated by a space, and the value the frequency of these POSs and words
            wordPOS = re.split("/", word)
            if (wordPOS[0] + " " + wordPOS[1]) not in wordGivenDictionary:
                wordGivenDictionary[wordPOS[0] + " " + wordPOS[1]] = 1
            else:
                wordGivenDictionary[wordPOS[0] + " " + wordPOS[1]] += 1
            if wordPOS[0] not in wordDictionary:
                wordDictionary[wordPOS[0]] = 1
            else:
                wordDictionary[wordPOS[0]] += 1
            if wordPOS[1] not in posDictionary:
                posDictionary[wordPOS[1]] = 1
            else:
                posDictionary[wordPOS[1]] += 1
            if firstTime:
                firstTime = False
            elif (wordPOS[1] + " " + prevTag) not in posGivenDictionary:
                posGivenDictionary[wordPOS[1] + " " + prevTag] = 1
            else:
                posGivenDictionary[wordPOS[1] + " " + prevTag] += 1
            #prevTag is used to keep track of the previous POS
            prevTag = wordPOS[1]          
def main():
    trainFile = open(sys.argv[1], "r")
    trainText = trainFile.read()
    readTrain(trainText)
    tagFile = open(sys.argv[2], "r")
    tagFileText = tagFile.read().strip(" ")
    tagTextFile(tagFileText)
    tagFile.close()
    trainFile.close()
if __name__ == "__main__":
    main()