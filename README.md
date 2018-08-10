# TopBloc

TopBloc Code Test
Download Excel Workbook 1 and Excel Workbook 2
Create a new Java project. Use Maven to manage any open source libraries or packages that you need to complete the test
First, parse and import the two workbooks into memory (use an appropriate data structure or create a class)
Take the numbers in "numberSetOne" in Data1.xlsx and the numbers in "numberSetOne" in Data2.xlsx and multiply them. Store the results in an array ([1,2,3,4])
Take the numbers in "numberSetTwo" in Data1.xlsx and divide them by "numberSetTwo" in Data2.xlsx. Store the results in an array ([1,2,3,4])
Take the the words in "wordSetOne" in Data1.xlsx and concat them to "wordSetOne" in Data2.xlsx. Put a space in between each set of concats. Store the Results in an array (["Mess With", "The Best".....])
Create a new JSON request and post that to this server at the path "/challenge"
You should have 4 attribute value pairs in your JSON request:
"id" which maps to a string with your email
"numberSetOne" which has a value of an array with your mulitplied numbers
"numberSetTwo" which has a value of an array with your divided number set
"wordSetOne" which has an array of your contatenated strings
Post your project to Github and email michael.pavelich@topbloc.com when complete

Assumptions:
  1. Data in first two columns will always be numbers of some sort (not necessarily integers)
  2. Data in third columns will always be strings
  3. First and Second columns will always have the same number of cells populated
