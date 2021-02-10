from socket import *
serverName = '10.0.0.77'
serverPort = 12002

clientSocket = socket(AF_INET, SOCK_STREAM)

clientSocket.connect((serverName, serverPort))

sentence = raw_input('Enter a sentence: ')

print "Enter any number of options from 1 to 5 seperated by comma\n"

options = raw_input()
clientSocket.send(sentence + "\n" + options)

modifiedSentence = clientSocket.recvfrom(2048)

print modifiedSentence
print "\n"
clientSocket.close()