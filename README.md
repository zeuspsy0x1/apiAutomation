
# Api Automation

In this repository you will find automated steps to test a mock api. 

# Have in mind:

1. Sometimes the mock api does not allow more than 30 fast requests, so a test could fail in that scenario.
2. You can replace the link of your own mock api in the class baseTest, it has to have the same format though.
3. The mock api in this repo can only have up to 100 records. 


# How to run it:

- Open the project, run the test from the Suite.xml file. This will run all the 4 tests starting with: 

1. @Test1 checks that the endpoint is empty, if not, it deletes everything from it.
2. @Test2 creates 11 fake bank transactions and posts 10 of those (the non duplicated, the one left has a duplicated email) to the api.
3. @Test3 makes a get request to the api and checks that there are not duplicated emails in the bank transactions.
4. @Test4 creates a new fake account number and updates the first available bank transaction with it.
