#Rahul Madala CMSC 416 PA 1, January 30, 2020
#Class Time: TTR 2:00 - 3:15
import re
import random
import string
#Project Summary:
#This program engages in a psychotherapy session with the user. In order to simulate a real-world session, the program
#utilizes word-spotting and statement-to-question transformations to engage with the user. In the introductory dialogue, the program will ask
#for the user's name. The program will search for the user's name and store it so that the program will refer to the user by name throughout the
#session. When the user enters a statement, the program will first search for a keyword (e.g. love, hate, sad), and respond to that keyword.
#If no keyword is found, the program will transform the statement by the user into a question, probing the user to write more until a keyword is found.
#If the user enters incomplete sentences or gibberish, a default statement will be outputted to the user, saying that the statement is unintelligible.
#When the user wishes to leave the program by entering a single keyword (exit, quit or goodbye), the program will terminate.


#Matches set stores the keywords needed for word-spotting responses. This contains the bulk of regex use. Most words used here are a particular emotion
#since discussing emotions are a critical aspect of psychotherapy. Since patients suffering from mental problems are also usually tied to family members
#or loved ones, those keywords are also used for word-spotting.
matches = [
   ["(.*)?lov(e|es|ed|ing|ely)(.*)?", ["Why do you feel such strong affection?", "That's really sweet of you to feel that way, {0}.", "Do you want to hold onto that feeling, {0}?"]],
   ["(.*)?sa(d|dness|dly)(.*)?", ["Why do you feel sad?", "I am sorry you feel that way, is there anything you can do to feel better?", "What is the root of your sadness?"]],
    ["(.*)?(happ(y|iness|ily)|ecstatic|ecstasy|jo(y|yful)|pleasant)(.*)?",["What makes you happy, {0}?", "Why do you feel happy, {0}?", "I'm glad to hear you are happy {0}."]],
    ["(.*)?depress(ed|ion|ing|ingly)(.*)?", ["I'm sorry to hear that. Please tell me why you feel that way, {0}", "How long have you been feeling this way, {0}?", "Have you talked to anyone else about this?"]],
    ["(.*)?crav(e|ing|ed)(.*)?", ["Please tell me more about your cravings, {0}", "Why do you crave this {0}?", "How long have you been craving this, {0}?"]],
    ["(.*)?(anxiou(s|sness)|nervou(s|sness))(.*)?",["Why do you feel nervous, {0}?", "What caused this anxiousness, {0}?", "What can I do to ease your anxiousness, {0}?"]],
    ["(.*)?(father|dad|stepfather|daddy|papa)(.*)?",["Tell me more about your father, {0}", "Why do you feel that way about your father, {0}?", "What is your father like?"]],
    ["(.*)?(mother|mom|stepmother|mommy|mama)(.*)?",["Tell me more about your mother, {0}", "Why do you feel that way about your mother, {0}?", "What is your mother like?"]],
    ["(.*)?(son|daughter|child)(.*)?",["What is your relationship like with your child, {0}?", "What do you do with your child?", "Do you want to spend more time with your child?"]],
    ["(.*)?(sons|daughters|children)(.*)?",["What is your relationship like with your children, {0}?", "What do you do with your children, {0}?", "Do you want to spend more time with your children?"]],
    ["(.*)?hat(e|es|ed|ing|red)(.*)?",["That's a very negative emotion. What caused this hate?", "Why do you feel that strongly about this, {0}?", "Please explain why you feel this hate."]],
    ["(.*)?desir(e|es|ed|able|ing)(.*)?",["Tell me more about your desires, {0}.", "Why do you desire this, {0}?", "Do you keep your desire bottled up?"]],
    ["(.*)?(ang(er|ers|ered|rily)|irritat(e|es|ing|ed|ingly)|mad)(.*)?", ["Why do you feel such anger, {0}?", "How do you calm down from this, {0}?", "Do you feel that your anger is justified?"]],
    ["(.*)?wan(t|ts|ted|ting)(.*)?",["Explain why you want this, {0}?", "Do you need to have this in your life?", "Tell me more about what you want, {0}."]],
    ["(.*)?viole(nce|nt)(.*)?",["What are your past experiences with violence?", "Has someone inflicted unjust violence towards you?", "Do you think this violence is justified?"]],
    ["(.*)?cr(y|ied)(.*)?",["What are you crying about, {0}?", "Do you think releasing your tears makes you feel better, {0}?", "Your sorrow makes me sad, {0}."]],
    ["(.*)?frustrat(ed|ion|e|es)(.*)?",["What caused this frustration?", "What is the root of your frustration, {0}?", "Do you think you need to be more patient to ease your frustration?"]]
]
#Transform set is used to transform sentences beginning with pronouns into grammatically correct questions.
transform = {
    "^your": "Why is {0}, {1}?",
    "^you were": "Why {0}, {1}?",
    "^was i": "Why do you think {0}, {1}?",
    "^you would": "Why would you {0}, {1}?",
    "^you are": "Why {0}, {1}?",
    "^you":"Why do {0}, {1}?",
    "^i am": "Why {0}, {1}?",
    "^i": "Why do {0}, {1}?",
    "^my": "Why is {0}, {1}?",
    "^were":"Why do you think that I {0}, {1}?",

}
#Default set messages used when user enters invalid characters.
defaults = [
    "Is there anything else you want to talk about?",
    "I'm sorry, I didn't understand what you said, could you please repeat that?"
]
#Inverts set is used to convert pronouns used in the user's statements into the correct pronouns needed
#for program response. (E.g: User: I hate my dog., Eliza: You hate your dog.)
inverts = {
    "i've":"you have",
    "i'd":"you would",
    "i":"you",
    "you'd":"I would",
    "you'll": "I will",
    "your":"my",
    "you":"I",
    "me": "you",
    "my":"your",
    "am":"are",
   "are":"am",
    "was":"were",
    "were":"was",
    "myself":"yourself",
    "yourself":"myself"
}
#When the user asks Eliza a question, she answers and probes for more information.
questionAnswer = [
    "I don't know. Do you want to talk about it more?",
    "Perhaps we can find the answer together."
]
#List of articles used to discount any of these words as names in the findName method.
articles = ["the", "this", "that", "a", "an"]
#This method is responsible for converting a user's statement into a question.
def question(input, name):
    #Pronouns are inverted to prepare for question.
    input = invert(input)
    #The words are broken up and the punctuation is stripped for easier readability.
    wordList = re.split("\s+", input.rstrip(".?"))
    #Return Value
    output = ""
    isRegexTrue = False
    #Checks to see if the sentence begins with pronoun phrases and accounts for rearranging
    #of words when converting into a question.
    for regex in transform:
        finalQuestion = re.match(regex, input)
        if finalQuestion:
            
            if regex == "^i am" or regex == "^you are":
                isRegexTrue = True
                for word in wordList:
                    if word == "i":
                        output += "am "
                    elif word == "am":
                        output += "I "
                    elif word == "you":
                        output += "are "
                    elif word == "are":
                        output += "you "
                    elif word != "":
                       output += word + " "
                    input = output.strip()
            elif regex == "^was I":
                isRegexTrue = True
                for word in wordList:
                    if word == "I":
                        output += "was "
                    elif word == "was":
                        output += "I "
                    else:
                       output += word + " "
                input = output.strip()
            elif regex == "^you were":
                isRegexTrue = True
                for word in wordList:
                    if word == "you":
                        output += "were "
                    elif word == "were":
                        output += "you "
                    else:
                       output += word + " "
                input = output.strip()
            if not isRegexTrue:
                output = input.rstrip(".?") 
            finalQuestion = transform[regex].format(output, name).strip()
            return finalQuestion
    #If the sentence does not start with a pronoun, the default question is used
    output = "Why do you believe {0}, {1}?"
    input = input.lower()
    output = output.format(input, name)
    return output

#This method is responsible for changing pronouns to its opposite pronoun
#so that when the statement is transformed to a question, the process is easier.
def invert(input): 
    output = ""
    wordList = re.split("\s+", input)
    count = 0
    for word in wordList:
        count+=1
        word = word.rstrip(".?")
        if word.lower() in inverts:
            if word == "you" and count == len(wordList):
                output += "me"
                continue
            output += inverts[word.lower()] + " "
        else:
            output += word + " "
    return output.strip()
#This method is responsible for finding a person's name in their first input.
#This is accomplished by searching for the first captial word that is not an article.
def findName(input):
    input = input.translate(input.maketrans('', '', string.punctuation))
    name = ""
    wordList = re.split("\s+", input)
    if len(wordList) == 1 and wordList.isupper():
        name = wordList[0]
        return name
    else:
        for word in wordList:
            if word.lower() not in inverts and word[0].isupper() and word.lower() not in articles:
                name = word
                break
        return name
#This method searches for keywords found in
def wordSpot(input, name, wordMatched):
    for regex, response in matches:
        spottedWord = re.search(regex, input.rstrip(".!"))
        if spottedWord and (regex not in wordMatched):
            wordMatched.append(regex)
            finalResponse = str(random.choice(response))
            return finalResponse.format(name)
    finalResponse = question(input, name)
    finalResponse = finalResponse.strip()
    return finalResponse
#checks sentence for invalid characters such as digits or special characters
def checkSentence(input):
    for char in input:
        if char in string.punctuation and char is not ',' and char is not '.' and char is not '!' and char is not '?':
            return False
        if char.isdigit():
            return False
    return True
def main():
    #Responsible for not repeating certain keywords multiple times
    wordMatched = []
    #Initial Dialogue that asks for user's name and stores it for future use.
    print("Eliza: Hello, my name is Eliza. What's your name?")
    nameResponse = str(input())
    while not nameResponse:
        print("Eliza: I would like to get to know you. Please tell me your name.")
        nameResponse = str(input())
    name = findName(nameResponse)
    response = "Eliza: Hi {}, what can I do for you today?"
    print(response.format(name))
    #Conversation loop that ends with goodbye statement.
    while True:
        userInput = input(name + ": ")
        if not checkSentence(userInput):
            print("Eliza: " + str(random.choice(defaults)))
            continue
        if userInput:
            question = re.search("\?", userInput)
            if question:
                print("Eliza: " + str(random.choice(questionAnswer)))
                continue
            if userInput == "goodbye":
                goodbye = "Goodbye {0}"
                print(goodbye.format(name))
                break
            userOutput = wordSpot(userInput, name, wordMatched)
            print("Eliza: " + userOutput)
        else:
            #Default message if user doesn't enter anything.
            print("Eliza: Please say something. I really want to talk to you.")
if __name__ == '__main__':
    main()