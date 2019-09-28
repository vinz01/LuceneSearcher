# LuceneSearcher

Log files such as Syslog are voluminous and grow continuously. Develop a tool to parse, index and query the log file contents using Lucene.Java may be choice of developing this mechanism.

I have attached screenshots of outputs in OutputExample folder


Instructions to run the code :

1)You can use any log file.

I have included an System.log file in the project for testing.You can replace it with your log file and rename your log file to System.log

2)Run lucene.java

3)You will get 4 outputs:

  a)Console output.
  
  b)GUI search results output.
  
  c)Pie chart for the search results(5 seconds after output b).
  
  d)Line Graph for logs in the file (5 seconds after output c).
  
Pie chart and line chart are customized to a specific search string(errors).You can change it according to your search string

4)make sure you add all the jar files into the project via build path.

Jar files reqd :

a)javafx

b)jcommon

c)jfreechart

d)lucene-analyzers-common

e)lucene-core

f)lucene-queryparser
