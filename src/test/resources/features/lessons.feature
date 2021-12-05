
Feature: Lesson validation

  Scenario Outline: Add user to a Lesson
  			AC 1 - the author should receve credits
    Given A valid Lesson with cost <lessonCost> and a User with <userInitialCredits> positive credits
    When A User buy a lesson
    Then The lesson and the student should reference each other
    And The Author of the lesson should have <lessonCost> credits
    And The User who bought the lesson should have <userFinalCredits>
    
    Examples: 
    |lessonCost		|userInitialCredits		|userFinalCredits		|
		|250					|300									|50									|
		|50						|1000									|950								|
		|112					|4875									|4763								|
