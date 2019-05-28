Feature: Add a pass

  Scenario: Customer not found
    When customer '11111111-1111-1111-1111-111111111111' adds a pass
      | city   | length |
      | London | 7      |
    Then I should get response status 404
    And I should get error message '{"errors":["Customer Not Found for id: 11111111-1111-1111-1111-111111111111"]}'

  Scenario: Add a pass
    Given a customer exists as
      | id                                   | name | city   |
      | 11111111-1111-1111-1111-111111111111 | John | London |
    When customer '11111111-1111-1111-1111-111111111111' adds a pass
      | city   | length |
      | London | 7      |
    Then I should get response status 201
    And I should get newly created pass as
      | city   | length | status |
      | London | 7      | Active |