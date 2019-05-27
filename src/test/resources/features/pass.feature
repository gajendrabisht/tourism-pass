Feature: Pass

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


    Scenario: Cancel a pass
    Given a customer exists as
    | id                                   | name | city   |
    | 11111111-1111-1111-1111-111111111111 | John | London |
    And customer '11111111-1111-1111-1111-111111111111' adds a pass
    | id                                   | city   | length | status |
    | 22222222-2222-2222-2222-222222222222 | London | 7      | Active |
    When customer '11111111-1111-1111-1111-111111111111' cancels pass '22222222-2222-2222-2222-222222222222'
    Then I should get response status 200
    And pass '22222222-2222-2222-2222-222222222222' status should be 'Inactive'

    Scenario: Renew a pass
    Given a customer exists as
    | id                                   | name | city   |
    | 11111111-1111-1111-1111-111111111111 | John | London |
    And customer '11111111-1111-1111-1111-111111111111' adds a pass
    | id                                   | city   | length | status   |
    | 22222222-2222-2222-2222-222222222222 | London | 7      | Inactive |
    When customer '11111111-1111-1111-1111-111111111111' renews pass '22222222-2222-2222-2222-222222222222'
    Then I should get response status 200
    And pass '22222222-2222-2222-2222-222222222222' status should be 'Active'
