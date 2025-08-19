***Settings***
Library    BuiltIn
# Library    OperatingSystem  # Commented out to avoid dependency if it's not loading
# Library    String           # Commented out to avoid dependency if it's not loading

***Test Cases***
Inspect Python Environment
    Log To Console    Inspecting Python environment used by Robot Framework...
    ${sys_path}=    Evaluate    sys.path    modules=sys
    Log To Console    Python sys.path (from RF context): ${sys_path}

    ${robot_file}=    Evaluate    robot.__file__    modules=robot
    Log To Console    Robot module location (from RF context): ${robot_file}

    ${robot_version}=    Evaluate    robot.__version__    modules=robot
    Log To Console    Robot version (from RF context): ${robot_version}

    # Attempt to log the location of the String library directly
    # This will fail if 'robot.libraries.String' cannot be imported by Evaluate
    ${string_lib_path}=    Evaluate    robot.libraries.String.__file__    modules=robot.libraries.String
    Log To Console    String library path (from RF context via Evaluate): ${string_lib_path}

    Log To Console    Attempting to use a BuiltIn keyword (Log)...
    Log    This is a log message from BuiltIn.Log
    Log To Console    BuiltIn.Log keyword worked.

    # The following line is to see if the String library keywords are found now.
    # It's expected to fail if the core issue persists, but we want to see if BuiltIn works.
    # Commenting out for now to ensure the diagnostic script runs as far as possible.
Check String contains
    Should Contain    abc    a
    Log    Test completed...