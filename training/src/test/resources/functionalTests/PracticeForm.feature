Feature: fill the deatials to the Practice form
@selenium
Scenario Outline: fill form details
 Given open Browser with url
 When fill FormDetails "<firstName>" "<lastName>" "<sex>" "<profision>" "<yearsOfExeperiance>" "<date>"
 #And click On submit button
 #Then user should be present on Home page
 Examples:
 |firstName	|lastName	|sex	|date		|profision			|yearsOfExeperiance	|
 |Raja		|Eallagaram |Male	|17-05-2020	|Automationtester	| 7					|
