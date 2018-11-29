The Genetic Algorithm can be found in the Biocomp2 folder.

to switch between data set 1 and 2 you will have to change a few things:

In Main.Java:
  change int g_size (line 26) to 60 for data1 or 80 for data2
  change file (line 30) to data1 or data2

In individual Java:
  change int num_rules (line 23) to 6 for data1 and 8 for data2
  change int training (line 24) to 32 for data1 or 64 for data2
  
If you would like to use roulette wheel selection instead of tournament selection:

  go to Selection.java, comment out tournament selection and uncomment roulette wheel selection.




If you would like to see the tables and results used in the report go to folder 'Bio comp results'
