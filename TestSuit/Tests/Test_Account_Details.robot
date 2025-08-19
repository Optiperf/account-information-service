*** Settings ***
Resource    ../Resources/api_keywords.robot
Resource    ../Resources/config.robot

***Variables***
${VALID_ACCOUNT_NUMBER}    520020015
${NON_EXISTENT_ACCOUNT_NUMBER}    999999999

*** Test Cases ***
Verify GET API Response
    Create API Session account_details
    ${response}=    Call API    account_details    GET    /account-details/${VALID_ACCOUNT_NUMBER}
    Validate API Response    ${response}    200
