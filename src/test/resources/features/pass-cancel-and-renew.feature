Feature: Cancel or Renew a Pass

  Scenario Outline: Customer not found
    When customer '11111111-1111-1111-1111-111111111111' <ACTION>s pass '22222222-2222-2222-2222-222222222222'
    Then I should get response status 404
    And I should get error message '{"errors":["Customer Not Found for id: 11111111-1111-1111-1111-111111111111"]}'
    Examples:
      | ACTION |
      | cancel |
      | renew  |

  Scenario Outline: Pass not found
    Given a customer exists as
      | id                                   | name | city   |
      | 11111111-1111-1111-1111-111111111111 | John | London |
    When customer '11111111-1111-1111-1111-111111111111' <ACTION>s pass '22222222-2222-2222-2222-222222222222'
    Then I should get response status 404
    And I should get error message '{"errors":["Pass Not Found for id: 22222222-2222-2222-2222-222222222222"]}'

    Examples:
      | ACTION |
      | cancel |
      | renew  |

  Scenario Outline: Pass - Cancel or Renew success
    Given a customer exists as
      | id                                   | name | city   |
      | 11111111-1111-1111-1111-111111111111 | John | London |
    And customer '11111111-1111-1111-1111-111111111111' adds a pass
      | id                                   | city   | length | status           |
      | 22222222-2222-2222-2222-222222222222 | London | 7      | <INITIAL_STATUS> |
    When customer '11111111-1111-1111-1111-111111111111' <ACTION>s pass '22222222-2222-2222-2222-222222222222'
    Then I should get response status 200
    And pass '22222222-2222-2222-2222-222222222222' status should be '<UPDATED_STATUS>'
    Examples:
      | INITIAL_STATUS | ACTION | UPDATED_STATUS |
      | Active         | cancel | Inactive       |
      | Inactive       | renew  | Active         |