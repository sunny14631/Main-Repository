##Rahul Madala CMSC 416 PA3, March 3, 2020
#The purpose of this program is to validate the tagger.py output text file by comparing the POS
#tags generated in tagger.py with a key POS tag textfile. If there are discrepancies in either file,
#the program outputs the error location into a report file. In the end of execution this report file will
#output the accuracy of the tagger.py program.
import re
import sys
#This method checks each significant token in both text files for errors and prints out the
#accuracy rate of the tagger.py program.
def compareFiles(createdFileText, keyFileText):
    createdFileList = re.split("\s+", createdFileText)
    keyFileList = re.split("\s+", createdFileText)
    length = range(0, len(keyFileList))
    accuracy = int()
    for index in length:
        #checks if the key token matches the tagger.py token
        if createdFileList[index] == keyFileList[index]:
            accuracy += 1
        else:
            #outputs the place and correction of the error in the textfile
            sys.stdout.write("Error at " + createdFileList[index] + " it should be " + keyFileList[index] + "\n")
    #the accuracy of the tagger.py is calculated and put into the output text file
    accuracyRate = accuracy/len(keyFileList) * 100
    sys.stdout.write("Accuracy of tagger: " + str(accuracyRate) + "%")
    
def main():
    createdFile = open(sys.argv[1], "r")
    keyFile = open(sys.argv[2], "r")
    createdFileText = createdFile.read()
    keyFileText = keyFile.read()
    compareFiles(createdFileText, keyFileText)
if __name__ == "__main__":
    main()