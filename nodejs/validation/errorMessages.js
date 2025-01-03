const ERROR_MESSAGES = {
    Existing: (type) => `the ${type} is already existing`,
    DBFail: "Fail to save in DB",
    FORBIDDEN: "Error 403: Forbidden",
    SERVER_ERROR: "Error 500: Internal server error",
    BAD_REQUEST: "Error 400: Bad request",
    VALIDATION_FAILED: "Validation failed: Please check your input",
};

export default ERROR_MESSAGES;
