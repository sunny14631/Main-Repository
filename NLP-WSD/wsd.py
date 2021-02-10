#Rahul Madala CMSC 416 PA4 March 31, 2020
#The objective of this project is to determine the missing word from a paragraph through
#a Naive Bayes Classifier. The missing word is either "phone" or "product". In order to determine
#which word is the missing word in the test data file, a training file with the "sense" already found
#in different paragraph instances is analyzed for discerning features. These features will help determine
#which word is the missing word in the test file. The features used in this Naive Bayes Classifier are:
#1. The word immediately preceding the missing word
#2. The word immediately following the missing word
#3. The pair of words immediately preceding the missing word
#4. The pair of words immediately following the missing word
#5. The presence of the word "product" in the paragraph
#6. The presence of the word "phone" in the paragraph 
import sys
import re
import math
#The key is the word. First index in list is P(word | phone), second index is P(word | product)
beforeWordProb = {
    "":[]
}
afterWordProb = {
    "":[]
}
afterPairProb = {
    "":[]
}
beforePairProb = {
    "":[]
}
#Variables are used to store the probability that a given instance is a phone or a product
phoneProb = [0.0]
productProb = [0.0]
#Variables are used to store the total number of phone and product instances
numPhones = [0]
numProducts = [0]
#Variables used to store the probabilies that the sense is phone (first index) and product (second index)
#respectively, given the word product or phone is found in the context.
productWordProb = [0.0, 0.0]
phoneWordProb = [0.0, 0.0]
#The key is the word. First index of list is phone frequency, second index of list is the product frequency 
beforeWordFreq = {
    "":[]
}
afterWordFreq = {
    "":[]
}
beforePairFreq = {
    "":[]
}
afterPairFreq = {
    "":[]
}
#These two lists keep track of the frequency of the word product being in sense phone and product respectively
productFreq = [0, 0]
phoneFreq = [0, 0]
#This method analyzes the testFile text document and tags each instance with either product or phone
#By using the bayes classifier made in readTrain method. The output is written in the form of answer
#tags for each instance.
def readTest(testFile):
    #Reads corpus and language lines
    testFile.readline()
    testFile.readline()
    #instanceID stores the instance id of each instance so that it can be outputted in the answer tag
    instanceID = ""
    #conditionals that check whether the iterator is in an instance tag and/or context tag
    instance = False
    context = False
    #All feature variables used to check with the Bayes Classifier at the end of each instance
    beforeWord = ""
    beforePair = ""
    afterWord = ""
    afterPair = ""
    phoneWord = False
    productWord = False
    #Reads text file line-by-line
    while testFile.readable():
        line = str(testFile.readline())
        if line is None or line == "":
            break
        #searches for the instance id in the instance tag
        if line.startswith("<instance"):
            idSplit = re.split("instance", line)
            idSecondSplit = re.split(">", idSplit[1])
            instanceID = idSecondSplit[0]
            instance = True
        #signals beginning of lines to be analyzed for features
        elif instance and line.startswith("<context"):
            context = True
        #signals end of lines to be analyzed for features
        elif line.startswith("</context>"):
            context = False
        #At the end of each instance, the probabilities are calculated and the appropriate sense is given
        elif line.startswith("</instance>"):
            #If the word phone or product is not found in the context, default values are 1 to signify absence
            phoneWordPhoneProbability = int(1.0)
            phoneWordProductProbability = int(1.0)
            productWordPhoneProbability = int(1.0)
            productWordProductProbability = int(1.0)
            #If the words found in the feature are not part of the classifier, then the word is added to the
            #dictionary with the values of 1 so that the calculation is ignored
            if beforeWord not in beforeWordProb:
                beforeWordProb[beforeWord] = [1, 1]
            if afterWord not in afterWordProb:
                afterWordProb[afterWord] = [1, 1]
            if afterPair not in afterPairProb:
                afterPairProb[afterPair] = [1, 1]
            if beforePair not in beforePairProb:
                beforePairProb[beforePair] = [1, 1]
            #If the word phone or product is found, the probability variables are updated to that of the classifier
            if phoneWord:
                phoneWordPhoneProbability = phoneWordProb[0]
                phoneWordProductProbability = phoneWordProb[1]
            if productWord:
                productWordProductProbability = productWordProb[1]
                productWordPhoneProbability = productWordProb[0]
            #Calculates the probablity of the sense being phone or product given the features found
            probPhone = phoneProb[0] + beforeWordProb[beforeWord][0] + afterWordProb[afterWord][0] + beforePairProb[beforePair][0] + afterPairProb[afterPair][0] + phoneWordPhoneProbability + productWordPhoneProbability
            probProd = productProb[0] + beforeWordProb[beforeWord][1] + afterWordProb[afterWord][1] + beforePairProb[beforePair][1] + afterPairProb[afterPair][1] + phoneWordProductProbability + productWordProductProbability
            #Compares the probabilities and selects the sense with the greater probability
            if probPhone > probProd:
                sys.stdout.write("<answer" + instanceID + " senseid=\"phone\"/>\n")
            else:
                sys.stdout.write("<answer" + instanceID + " senseid=\"product\"/>\n")
            #Resets all local variables for the next instance analysis.
            instanceID = ""
            instance = False
            context = False
            beforeWord = ""
            beforePair = ""
            afterWord = ""
            afterPair = ""
            phoneWord = False
            productWord = False
        #If the missing word position is found the line is split up and the adjacent words are stored
        elif instance and context and line.find("<head>line</head>") != -1:
            headSplit = re.split(" <head>line</head> ", line)
            beforeSplit = re.split("\s+", headSplit[0])
            afterSplit = re.split("\s+", headSplit[1])
            beforePair = beforeSplit[len(beforeSplit) - 2] + " " + beforeSplit[len(beforeSplit) - 1]
            afterPair = afterSplit[0] + " " + afterSplit[1]
            beforeWord = beforeSplit[len(beforeSplit) - 1]
            afterWord = afterSplit[0]
        elif instance and context and line.find("<head>lines</head>") != -1:
            headSplit = re.split(" <head>lines</head> ", line)
            beforeSplit = re.split("\s+", headSplit[0])
            afterSplit = re.split("\s+", headSplit[1])
            beforePair = beforeSplit[len(beforeSplit) - 2] + " " + beforeSplit[len(beforeSplit) - 1]
            afterPair = afterSplit[0] + " " + afterSplit[1]
            beforeWord = beforeSplit[len(beforeSplit) - 1]
            afterWord = afterSplit[0]
        elif instance and context and line.find("<head>Lines</head>") != -1:
            headSplit = re.split(" <head>Lines</head> ", line)
            beforeSplit = re.split("\s+", headSplit[0])
            afterSplit = re.split("\s+", headSplit[1])
            beforePair = beforeSplit[len(beforeSplit) - 2] + " " + beforeSplit[len(beforeSplit) - 1]
            afterPair = afterSplit[0] + " " + afterSplit[1]
            beforeWord = beforeSplit[len(beforeSplit) - 1]
            afterWord = afterSplit[0]
        elif instance and context and line.find("<head>Line</head>") != -1:
            headSplit = re.split(" <head>Line</head> ", line)
            beforeSplit = re.split("\s+", headSplit[0])
            afterSplit = re.split("\s+", headSplit[1])
            beforePair = beforeSplit[len(beforeSplit) - 2] + " " + beforeSplit[len(beforeSplit) - 1]
            afterPair = afterSplit[0] + " " + afterSplit[1]
            beforeWord = beforeSplit[len(beforeSplit) - 1]
            afterWord = afterSplit[0]
        #If phone or product is found in the context, then that feature exists and can be used in calculation
        if instance and context and line.find("phone") != -1:
            phoneWord = True
        if instance and context and line.find("product") != -1:
            productWord = True
#This method's goal is to read the training file and gather the unique word frequencies in feature categories
#Once these frquencies are found, the probabilities for the Bayes Classifier can be calcualted.
def readTrain(trainFile):
    #Reads corpus and language lines
    trainFile.readline()
    trainFile.readline()
    #conditionals that check whether the iterator is in an instance tag and/or context tag
    instance = False
    context = False
    #All feature variables used to create the Bayes Classifier
    beforeWord = ""
    beforePair = ""
    afterWord = ""
    afterPair = ""
    #keeps track of total number of instances for probability calculation
    total = 0
    #conditional used to check whether the instance has sense phone or product
    isPhone = False
    #conditionals used to check whether the word phone or product is found in the context.
    phoneWordFound = False
    productWordFound = False
    while trainFile.readable():
        line = str(trainFile.readline())
        if line == "" or line is None:
            break
        #checks for beginning of instance
        if line.startswith("<instance"):
            instance = True
        #checks to see if the line is the answer tag
        elif instance and line.startswith("<answer"):
            total += 1
            #If the sense is phone, then this instance will populate phone frequency dictionaries and variables
            if line.find("phone") != -1:
                numPhones[0] += 1
                isPhone = True
            #Else product variables and frequency dictionaries
            else:
                numProducts[0] += 1
                isProd = True
        #Signals beginning of context to be analyzed
        elif instance and line.startswith("<context"):
            context = True
        #Signifies the end of context
        elif line.startswith("</context>"):
            context = False
        #At the end of each instance, each feature's words are added into the feature dictionaries containing
        #the frequency of each word.
        elif line.startswith("</instance>"):
            instance = False
            #Checks to see if the word is in the dictionary. If it is not in the dictionary, the word
            #is added to the dictionary as a key, and the list is initialized depending on the sense.
            if beforeWord not in beforeWordFreq:
                newList = [0, 0]
                if isPhone:
                    newList[0] = 1
                else:
                    newList[1] = 1
                beforeWordFreq[beforeWord] = newList
            #If the sense is a phone, then the word's count for phone is incremented
            elif isPhone:
                newList = beforeWordFreq[beforeWord]
                newList[0] = newList[0] + 1
                beforeWordFreq[beforeWord] = newList
            #If the sense is a product, then the word's count for product is incremented
            else:
                newList = beforeWordFreq[beforeWord]
                newList[0] = newList[0] + 1
                beforeWordFreq[beforeWord] = newList
            #Following word case
            if afterWord not in afterWordFreq:
                newList = [0, 0]
                if isPhone:
                    newList[0] = 1
                else:
                    newList[1] = 1
                afterWordFreq[afterWord] = newList
            elif isPhone:
                newList = afterWordFreq[afterWord]
                newList[0] = newList[0] + 1
                afterWordFreq[afterWord] = newList
            else:
                newList = afterWordFreq[afterWord]
                newList[0] = newList[0] + 1
                afterWordFreq[afterWord] = newList
            #Preceding pair of words case
            if beforePair not in beforePairFreq:
                newList = [0, 0]
                if isPhone:
                    newList[0] = 1
                else:
                    newList[1] = 1
                beforePairFreq[beforePair] = newList
            elif isPhone:
                newList = beforePairFreq[beforePair]
                newList[0] = newList[0] + 1
                beforePairFreq[beforePair] = newList
            else:
                newList = beforePairFreq[beforePair]
                newList[0] = newList[0] + 1
                beforePairFreq[beforePair] = newList
            #Following pair of words case
            if afterPair not in afterPairFreq:
                newList = [0, 0]
                if isPhone:
                    newList[0] = 1
                else:
                    newList[1] = 1
                afterPairFreq[afterPair] = newList
            elif isPhone:
                newList = afterPairFreq[afterPair]
                newList[0] = newList[0] + 1
                afterPairFreq[afterPair] = newList
            else:
                newList = afterPairFreq[afterPair]
                newList[0] = newList[0] + 1
                afterPairFreq[afterPair] = newList
            #Resets the feature variables for next instance
            beforeWord = ""
            afterWord = ""
            answerKey = ""
            beforePair = ""
            afterPair = ""
            isPhone = False
            productWordFound = False
            phoneWordFound = False
        #If the missing word is found, the features for the following and preceding words are assigned
        elif instance and context and line.find("<head>line</head>") != -1:
            headSplit = re.split(" <head>line</head> ", line)
            beforeSplit = re.split("\s+", headSplit[0])
            afterSplit = re.split("\s+", headSplit[1])
            beforePair = beforeSplit[len(beforeSplit) - 2] + " " + beforeSplit[len(beforeSplit) - 1]
            afterPair = afterSplit[0] + " " + afterSplit[1]
            beforeWord = beforeSplit[len(beforeSplit) - 1]
            afterWord = afterSplit[0]
        elif instance and context and line.find("<head>lines</head>") != -1:
            headSplit = re.split(" <head>lines</head> ", line)
            beforeSplit = re.split("\s+", headSplit[0])
            afterSplit = re.split("\s+", headSplit[1])
            beforePair = beforeSplit[len(beforeSplit) - 2] + " " + beforeSplit[len(beforeSplit) - 1]
            afterPair = afterSplit[0] + " " + afterSplit[1]
            beforeWord = beforeSplit[len(beforeSplit) - 1]
            afterWord = afterSplit[0]
        elif instance and context and line.find("<head>Lines</head>") != -1:
            headSplit = re.split(" <head>Lines</head> ", line)
            beforeSplit = re.split("\s+", headSplit[0])
            afterSplit = re.split("\s+", headSplit[1])
            beforePair = beforeSplit[len(beforeSplit) - 2] + " " + beforeSplit[len(beforeSplit) - 1]
            afterPair = afterSplit[0] + " " + afterSplit[1]
            beforeWord = beforeSplit[len(beforeSplit) - 1]
            afterWord = afterSplit[0]
        elif instance and context and line.find("<head>Line</head>") != -1:
            headSplit = re.split(" <head>Line</head> ", line)
            beforeSplit = re.split("\s+", headSplit[0])
            afterSplit = re.split("\s+", headSplit[1])
            beforePair = beforeSplit[len(beforeSplit) - 2] + " " + beforeSplit[len(beforeSplit) - 1]
            afterPair = afterSplit[0] + " " + afterSplit[1]
            beforeWord = beforeSplit[len(beforeSplit) - 1]
            afterWord = afterSplit[0]
        #Hardcode case in trainFile
        elif instance and context and line.find("<head>line</") != -1:
            beforeWord = "a"
            beforePair = "a car"
            afterWord = "anymore,"
            afterPair = "anymore, laments"
        #If the word phone is found in the context and the sense is phone, then that counter is incremented
        if instance and context and line.find("phone") != -1 and isPhone and not phoneWordFound:
            phoneFreq[0] = phoneFreq[0] + 1
            phoneWordFound = True
        #If the word phone is found in the context and the sense is product, then that counter is incremented
        if instance and context and line.find("phone") != -1 and not isPhone and not phoneWordFound:
            phoneFreq[1] = phoneFreq[1] + 1
            phoneWordFound = True
        #If the word product is found in the context and the sense is phone, then that counter is incremented
        if instance and context and line.find("product") != -1 and isPhone and not productWordFound:
            productFreq[0] = productFreq[0] + 1
            productWordFound = True
        #If the word product is found in the context and the sense is product, then that counter is incremented
        if instance and context and line.find("product") != -1 and not isPhone and not productWordFound:
            productFreq[1] = productFreq[1] = 1
            productWordFound = True
    #Calculates probability of a given instance having a sense of phone and product
    #without considering features
    phoneProb[0] = math.log10(float(numPhones[0]/total))
    productProb[0] = math.log10(float(numProducts[0]/total))
    phoneWordProb = [math.log10(float((phoneFreq[0] + 1)/numPhones[0])), math.log10(float((phoneFreq[1]+1)/numPhones[0]))]
    productWordProb = [math.log10(float((productFreq[0]+1)/numProducts[0])), math.log10(float((productFreq[1]+1)/numProducts[0]))]
#This method iterates through each key in the word dictionaries and calculates the probabilities
#of each word belonging to a product sense and a phone sense
def createBayesClassifier():
    #Preceding word case
    for word in beforeWordFreq:
        if not word == "":
            freqList = beforeWordFreq[word]
            wordGivenPhone = math.log10(float((freqList[0] + 1)/int(numPhones[0])))
            wordGivenProduct = math.log10(float((freqList[1] + 1)/int(numProducts[0])))
            beforeWordProb[word] = [wordGivenPhone, wordGivenProduct]
    #Following word case
    for word in afterWordFreq:
        if not word == "":
            freqList = afterWordFreq[word]
            wordGivenPhone = math.log10(float((freqList[0] + 1)/numPhones[0]))
            wordGivenProduct = math.log10(float((freqList[1] + 1)/numProducts[0]))
            afterWordProb[word] = [wordGivenPhone, wordGivenProduct]
    #Following pair of words case
    for word in afterPairFreq:
        if not word == "":
            freqList = afterPairFreq[word]
            wordGivenPhone = math.log10(float((freqList[0] + 1)/numPhones[0]))
            wordGivenProduct = math.log10(float((freqList[1] + 1)/numProducts[0]))
            afterPairProb[word] = [wordGivenPhone, wordGivenProduct]
    #Preceding pair of words case
    for word in beforePairFreq:
        if not word == "":
            freqList = beforePairFreq[word]
            wordGivenPhone = math.log10(float((freqList[0] + 1)/numPhones[0]))
            wordGivenProduct = math.log10(float((freqList[1] + 1)/numProducts[0]))
            beforePairProb[word] = [wordGivenPhone, wordGivenProduct]
#This method writes all the probabilities calculated from the training file into the model text file so that
#the model can be used to verify whether the training file was successfully analyzed.
def createModel(modelFile):
    #Probabilities of phone and product sense occuring
    modelFile.write("Probability of the phone sense appearing in an instance: " + str(phoneProb[0]) + "\n \n")
    modelFile.write("Probability of the product sense appearing in an instance: " + str(productProb[0]) + "\n \n")

    #Preceding word probabilities
    modelFile.write("List of all the probablities of words preceding the missing word in sense phone and product respectively:\n \n")
    for key in beforeWordProb:
        if not key == "":
            probList = beforeWordProb[key]
            modelFile.write(str(key) + ": Phone Probability: " + str(probList[0]) + ", Product Probability: " + str(probList[1]) + "\n \n")
    #Following word probabilities
    modelFile.write("List of all the probabilities of words following the missing word in sense phone and product respectively:\n\n")
    for key in afterWordProb:
        if not key == "":
            probList = afterWordProb[key]
            modelFile.write(str(key) + ": Phone Probability: " + str(probList[0]) + ", Product Probability: " + str(probList[1]) + "\n\n")
    #Preceding pairs of words probabilities
    modelFile.write("List of all the probabilities of pairs of words preceding the missing word in sense phone and product respectively:\n\n")
    for key in beforePairProb:
        if not key == "":
            probList = beforePairProb[key]
            modelFile.write(str(key) + ": Phone Probability: " + str(probList[0]) + ", Product Probability: " + str(probList[1]) + "\n\n")
    #Following pairs of words probabilities
    modelFile.write("List of all the probabilities of pairs of words following the missing word in sense phone and product respectively:\n\n")
    for key in afterPairProb:
        if not key == "":
            probList = afterPairProb[key]
            modelFile.write(str(key) + ": Phone Probability: " + str(probList[0]) + ", Product Probability: " + str(probList[1]) + "\n\n")
    #Phone word and Product word appearing in context probabilities
    modelFile.write("Probabilities of sense being phone and product respectively, given the word phone is in the context: \n\n")
    modelFile.write("Phone Sense Probability: " + str(phoneWordProb[0]) + ", Product Sense Probability: " + str(phoneWordProb[1]) + "\n\n")
    modelFile.write("Probabilities of sense being phone and product respectively, given the word product is in the context: \n\n")
    modelFile.write("Phone Sense Probability: " + str(productWordProb[0]) + ", Product Sense Probability: " + str(productWordProb[1]) + "\n\n")
def main():
    trainFile = open(sys.argv[1], "r")
    readTrain(trainFile)
    createBayesClassifier()
    testFile = open(sys.argv[2], "r")
    modelFile = open(sys.argv[3], "w")
    createModel(modelFile)
    readTest(testFile)
if __name__ == "__main__":
    main()