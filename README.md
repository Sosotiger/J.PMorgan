# J.PMorgan
A sample messages application :
I created Java application using Eclipse IDE and its main class is JPMorganMain that call MessageHandler class to invoke its methods to load sample data file and print out required reports.
The application loading sample data text file and read it line by line to handle input messages.
There are 3 types of message : 
  1. I.E 'apple at 10p' I parsed this type by count of words is 3 and second word must be 'at'.
  2. I.E '20 sales of apples at 10p each' I parsed this type by count of words is 7 and first word is digits caracters.
  3. I.E 'add 20p apples' I parsed this type by count of words is 3 and first word is Add, Subtract or Multiply.
For each 10 messages application printing out a summary report for each product count and value.
When application reached 50 messages it stops reading data and generate adjustments report.
