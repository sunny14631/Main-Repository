from socket import *
import re
serverPort = 12002
serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('', serverPort))
serverSocket.listen(1)
print "The server is ready to receive"
while 1:
	print "Waiting ..."
	connectionSocket, addr = serverSocket.accept()
	print "accept"
	textInput = connectionSocket.recv(2048)
	args = re.split("\n", textInput)
	sentence = args[0]
	options = args[1]
	print "Message Received: " + sentence
	finalOutput = ""
	optionsList = re.split(",", options)
	for option in optionsList:
		option = option.strip(" ")
		if option == "1":
			modifiedSentence = sentence.upper()
			finalOutput += ("1) " + modifiedSentence + "  ")
		elif option == "2":
			lowerSentence = sentence.lower()
			finalOutput += ("2) " + lowerSentence + "  ")
		elif option == "3":
			length = str(len(sentence))
			finalOutput += ("3) " + length + "  ")
		elif option == "4":
			numVowels = 0
			for character in sentence:
				if character.lower() == "a" or character.lower() == "e" or character.lower() == "i" or character.lower() == "o" or character.lower() == "u":
					numVowels += 1
			numVowels = str(numVowels)
			finalOutput += ("4) " + numVowels + "   ")
		elif option == "5":
			wordList = re.split("\s+", sentence)
			length = str(len(wordList))
			finalOutput += ("5) " + length + "  ")
	connectionSocket.send(finalOutput)
	connectionSocket.close()
