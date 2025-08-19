*** Settings ***
Library    RequestsLibrary

*** Keywords ***
Create API Session account_details
    Create Session    account_details    ${BASE_URL_DETAILS}    headers={"Content-Type": "application/json"}
Create API Session account_address
    Create Session    account_address    ${BASE_URL_ADDRESS}    headers={"Content-Type": "application/json"}
Create API Session account_balance
    Create Session    account_balance    ${BASE_URL_BALANCE}    headers={"Content-Type": "application/json"}

Call API
    [Arguments]    ${session_alias}     ${method}    ${endpoint}    ${data}=None
    ${response}=    Run Keyword     ${method} On Session    ${session_alias}    ${endpoint}    json=${data}
    RETURN    ${response}

Validate API Response
    [Arguments]    ${response}    ${expected_status}
    Should Be Equal As Strings    ${response.status_code}    ${expected_status}