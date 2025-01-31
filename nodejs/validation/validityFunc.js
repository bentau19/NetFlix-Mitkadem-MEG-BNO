
const ERROR_MESSAGES = require('./errorMessages');
const UserService = require('../services/UsersService');
const catchAction = (error,res) => {
        if( ERROR_MESSAGES.BAD_REQUEST==error|| ERROR_MESSAGES.TOKEN_ERR==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
};

const adminExistingValidity = (token) => {
    if(!UserService.isManager(token)){
        throw  ERROR_MESSAGES.TOKEN_ERR;
    }
};

const validProgram = (result,status,res) => {
    if (result) {
        res.status(status).json({result});
    } else {
        res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST });
    }
};

module.exports = {catchAction,adminExistingValidity,validProgram };