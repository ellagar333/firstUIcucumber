Feature: Validationg the employee webtable functionalities


Scenario: add employee
#Given open Browser with url
Given open browser with url "https://demoqa.com/webtables" 
#When user click on add row button
#Then verify registration form is displayed
And print WebTable values
#When user enters employeedetails
#|firstName|lastName|
#|Rajendra|Prasad|
#And click on submit button
#Then verify employee is added webtable at the end
