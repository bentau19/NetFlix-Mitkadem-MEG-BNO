
const userService = require('../services/UsersService');

const ERROR_MESSAGES = require('../validation/errorMessages');
const createUser = async (req, res) => {
    try {
        console.log('yup');
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
        res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR });
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
            res.status(200).json({ id:result._id});
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'wrong Username or password' });
        }
    } catch (error) {
        res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR });
    }
};

module.exports = {createUser, getUser,signIn};
