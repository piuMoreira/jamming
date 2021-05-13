
Feature: Lesson validation

  Scenario: Add user to a Lesson
    Given A valid Lesson
    And A User with credits
    When A User buy a lesson
    Then The lesson and the student have a relationship

