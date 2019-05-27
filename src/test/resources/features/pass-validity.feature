Feature: Pass Validity

    Scenario: Valid Pass
    Given a customer exists as
    | id                                   | name | city   |
    | 11111111-1111-1111-1111-111111111111 | John | London |
    And customer '11111111-1111-1111-1111-111111111111' adds a pass
    | id                                   | city   | length | status |
    | 22222222-2222-2222-2222-222222222222 | London | 7      | Active |
    When vendor 'someVendorId' checks validity of pass '22222222-2222-2222-2222-222222222222'
    Then I should get response status 200
    And validity returned is 'true'

    Scenario: Invalid Pass
    Given a customer exists as
    | id                                   | name | city   |
    | 11111111-1111-1111-1111-111111111111 | John | London |
    And customer '11111111-1111-1111-1111-111111111111' adds a pass
    | id                                   | city   | length | status   |
    | 22222222-2222-2222-2222-222222222222 | London | 7      | Inactive |
    When vendor 'someVendorId' checks validity of pass '22222222-2222-2222-2222-222222222222'
    Then I should get response status 200
    And validity returned is 'false'
    