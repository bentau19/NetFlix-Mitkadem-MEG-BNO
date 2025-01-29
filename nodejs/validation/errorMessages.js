const ERROR_MESSAGES = {
    Existing: (type) => `The ${type} already exists.`,
    DBFail: "Failed to save to the database.",
    FORBIDDEN: "Error 403: Forbidden.",
    SERVER_ERROR: "Error 500: Internal server error.",
    BAD_REQUEST: "Error 400: Bad request.",
    VALIDATION_FAILED: "Validation failed: Please check your input.",
    TOKEN_ERR:"Error 400: Token Error."
};


module.exports = ERROR_MESSAGES;
