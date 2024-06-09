Your tasks:


The  service https://randomuser.me provides a free, open-source API for generating random user data.
Have a look at the documentation of the REST API of this service: https://randomuser.me/documentation

1.

Call this service and generate one set of random data (one user)
Check that the response code of you request is 200
Write one test that checks that the returned user age is higher than 40 years
Write one test that checks that he/she comes from the US.
Write one test that checks that the password of the generated user matches a specific pattern (e.g. that it only contains letters and numbers, but not special characters)
Write another test that combines all the 3 previous checks in one test
Note:
In case all conditions are fulfilled this test should be successful
In case one or more conditions are not fulfilled this test should fail
All checks should be performed, even if the first or second one failed
In case this test fails, the result should show which check failed
In case this test fails, the result should show what was expected and what was the current value (for all failed conditions)
You can use a framework of your choice. (We would prefer: Use RestAssured)


2.

Call this service and generate 100 sets of random data (100 users)
Write a Java method that returns all user data ordered alphabetically by firstname, but only when the firstname starts with a specified letter
E.g.: If your call returns:  {... "first":"Mirko" ...}, {... "first":"Florian" ...}, {... "first":"Stefan" ...}, {... "first":"Felix" ...}, {... "first":"Manuel" ...}, {... "first":"Fiona" ...}  and you call your method with the character "F" it should return:  {... "first":"Felix" ...}, {... "first":"Fiona" ...}, {... "first":"Florian" ...}
(All user data where the firstname not starts with "F" are removed, and the rest is given back alphabetically ordered by firstname)
Write Unittests that checks that your Java method works as expected