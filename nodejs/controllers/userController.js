
const userService = require('../services/UsersService');

const ERROR_MESSAGES = require('../validation/errorMessages');
const createUser = async (req, res) => {
    try {
        const result = await userService.createUser(
            req.body.displayName,
            req.body.userName,
            req.body.image,
            req.body.password
        );
        res.status(201).json({ message: 'User created successfully',_id:result._id });
    } catch (error) {

        if (error === ERROR_MESSAGES.VALIDATION_FAILED||ERROR_MESSAGES.Existing("user")==error) {
            res.status(400).json({ message: error });
        } else {
            res.status(500).json({ message: error });

        }
    }
}
const getUser = async (req, res) => {
    try {
        const result = await userService.getUser(
            req.params.id
        );
        if (result) {
            result.password="unrelevent";
            // Assuming createUser returns a truthy value on success
            res.status(200).json(result);
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'User doesnt exist' });
        }
    } catch (error) {
        if(error==ERROR_MESSAGES.BAD_REQUEST) res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR });
    }
};
const getUserByToken = async (req, res) => {
    try {
        if(!req.headers['token'])res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        const result = await userService.getUserbyToken(
            req.headers['token']
        );
        if (result) {
            result.password="unrelevent";
            // Assuming createUser returns a truthy value on success
            res.status(200).json(result);
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'User doesnt exist' });
        }
    } catch (error) {
        if(error==ERROR_MESSAGES.BAD_REQUEST) res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR });
    }
};


const signIn = async (req, res) => {
    try {
        const result = await userService.findUserByNP(
            req.body.userName,
            req.body.password
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.cookie('token', result, {
                secure: false,
                secure: process.env.NODE_ENV === 'production', // Use secure cookies in production
                sameSite: 'lax',
                maxAge: 7 * 24 * 60 * 60 * 1000 // 1 week in milliseconds
            });
            res.status(200).json(result);

        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'wrong Username or password' });
        }
    } catch (error) {
        if(error==ERROR_MESSAGES.BAD_REQUEST) res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR });
    }
};

module.exports = {createUser, getUser,signIn,getUserByToken};
