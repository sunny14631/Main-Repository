SCENARIO. A user receives an email offering to participate in a lottery for a big win. He is
asked to click on a link at the email and further follow the instructions to download and run a
program (bot.exe). Out of curiosity, the user allows the program to run trying to enroll in the
lottery. Unfortunately, the program is a malware that has infected the user’s machine and turned
it to a bot.

GOALS
1) When bot.exe is run on a benign machine, the bot.exe suddenly
disappears. The goal is to find out
• if this executable is still present on the disk, and
• if yes, what is its complete path on the file system
2) After bot.exe is run and compromises a benign machine, the goal is to find out what
data/file is being exchanged between the botmaster and the bot (compromised machine)
in the following cases:
• When bot.exe runs on a benign machine
• When the botmaster sends a command (script) to the bot after compromise
• When the bot logs off and logs in again after the botmaster command
