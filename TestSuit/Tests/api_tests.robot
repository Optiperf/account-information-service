***Settings***
Documentation     A test suite for Account Information Service APIs.
Library           RequestsLibrary
Library           Collections
Library           String
Library           JSONLibrary
# Useful for dictionary/list manipulations
# For string manipulation and assertion keywords
# For more complex JSON assertions
Suite Setup       Create Session    details_api    http://localhost:8082    # Base URL for the service

***Variables***
${VALID_ACCOUNT_NUMBER}    520020015
${NON_EXISTENT_ACCOUNT_NUMBER}    999999999

***Test Cases***
Get Existing Account Details Successfully
    [Documentation]    Fetches details for a known account and verifies the response.
    [Tags]    positive
    ${response}=    GET On Session    details_api    /account-details/${VALID_ACCOUNT_NUMBER}
    Should Be Equal As Strings    ${response.status_code}    200
    ${json_response}=    Set Variable    ${response.json()}
    ${expected_account_number_as_int}=    Convert To Integer    ${VALID_ACCOUNT_NUMBER}
    Dictionary Should Contain Item    ${json_response}    accountNumber    ${expected_account_number_as_int}
    Dictionary Should Contain Key    ${json_response}    accountName
    Log    ${response.content}

Get Non Existent Account Details
    [Documentation]    Attempts to fetch details for an account that does not exist.
    [Tags]    negative
    ${response}=    GET On Session    details_api    /account-details/${NON_EXISTENT_ACCOUNT_NUMBER}    expected_status=404
    Should Be Equal As Strings    ${response.status_code}    404
    Log    ${response.content}
    # You could add more assertions here about the error message if your API returns a specific format

Post New Account Fails Due To Unknown Field
    [Documentation]    Attempts to create an account with an unknown field, expecting a 400.
    [Tags]    negative    validation
    &{headers}=       Create Dictionary    Content-Type=application/json
    &{payload}=       Create Dictionary
    ...               accountNumber=123450001
    ...               accountName=new-test-account
    ...               accountType=SAVINGS
    ...               currency=EUR
    ...               status=ACTIVE
    ...               createdDate=2025-07-01T10:00:00.000Z
    ...               unknownField=shouldFail
# Assuming POST to /account-details/ creates a new account
    ${response}=    POST On Session
    ...               details_api
    ...               /account-details/${payload.accountNumber}    
    ...               json=${payload}
    ...               headers=${headers}
    ...               expected_status=400

    Should Be Equal As Strings    ${response.status_code}    400
    ${json_response}=    Set Variable    ${response.json()}
    # Example: Asserting the error message contains the unknown field name
    # This depends on your actual error response structure Or whatever your error message structure is
    Should Be Equal As Strings    ${json_response['message']}    Request input error
    Log    ${json_response}


***Keywords***
# You can define custom keywords here if needed
