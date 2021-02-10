#Rahul Madala, Programming Languages, Assignment 7, April 16, 2020
import tkinter
from tkinter import *
import re, sys
#rectpack external library is used to assist in determining the coordinates of the origins
#of the rectangles in the canvas
from rectpack import newPacker
#initializes the graphics window
root = Tk()
#Class that takes two arguments specifying the dimensions of the canvas.
class CustomCanvas:
    def __init__(self, newHeight, newWidth):
        #Each class has a canvas variable containing the instance of the class
        self.canvas = Canvas(height = int(newHeight), width = int(newWidth))
#Rectangle class takes four arguments specifying the dimensions of the rectangle and the origin
#of the rectangle, which is the upper left corner. If the coordinates are not given, then the
#default values are 0.
class Rectangle:
    def __init__(self, height, width, x=0, y=0):
        self.height = int(height)
        self.width = int(width)
        self.x = int(x)
        self.y = int(y)
#Responsible for returning a list of Rectangle objects that contains the coordinates and dimensions
#that, when placed, do not overlap.
def pack(allRect, canvasSize):
    #instantiates the rectpack packer
    packer = newPacker()
    #Iterates through each rectangle in the list and creates a tuple containing the dimensions
    #of the rectangle and adds them to the packer.
    for rect in allRect:
        rectTuple = (rect.width, rect.height)
        packer.add_rect(*rectTuple)
    #Converts the String canvasSize variables into an int tuple containing the dimensions of the canvas
    canvasInt = (int(canvasSize[0]), int(canvasSize[1]))
    #Adds the bin in which all rectangles in the list must fit in.
    packer.add_bin(*canvasInt)
    packer.pack()
    #Returns list of tuples containing the dimensions and coordinates of the placed rectangles
    rectList = packer.rect_list()
    returnRect = []
    for rect in rectList:
        b, x, y, w, h, rid = rect
        #Instantiates a Rectangle object and adds it to the list to be returned.
        newRect = Rectangle(h, w, x, y)
        returnRect.append(newRect)
    return returnRect

if __name__ == "__main__":
    textFile = open(sys.argv[1], "r")
    #Parses through text file, allocating the proper dimensions to canvas and rectangles 
    canvasDimensions = str(textFile.readline())
    canvasDimList = re.split(",", canvasDimensions)
    canvas = CustomCanvas(canvasDimList[0], canvasDimList[1])
    canvasTuple = (canvasDimList[0], canvasDimList[1])
    rectList = []
    while textFile.readable():
        line = textFile.readline()
        if line == "":
            break
        rectDim = re.split(",", line)
        rect = Rectangle(rectDim[0], rectDim[1])
        rectList.append(rect)
    #List of placed rectangles
    packedRects = pack(rectList, canvasTuple)    
    for rect in packedRects:
        canvas.canvas.create_rectangle(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, fill='Blue')
    canvas.canvas.pack()
    root.mainloop()    