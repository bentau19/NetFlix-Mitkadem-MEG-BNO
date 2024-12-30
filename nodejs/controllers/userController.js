
const userService = require('../services/UsersService');
const serverData = require('../services/SendData');
const createUser = async (req, res) => {
    try {
        const result = await userService.createUser(
            req.body.name,
            req.body.image,
            req.body.password
        );
        if (result) {
            try{
            const addToLoc =await serverData.communicateWithServer("POST "+result._id+" 0");
            const sec =await serverData.communicateWithServer("Delete "+result._id+" 0");
            res.status(201).json({ message: 'User created successfully',_id:result._id });
            }catch(error){
                console.error(error); // Log the error for debugging
                res.status(500).json({ message: 'An internal server error occurred', error: error.message });
            }
            // Assuming createUser returns a truthy value on success
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'User creation failed' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
    }
}
const getUser = async (req, res) => {
    try {
        const result = await userService.getUser(
            req.params.id
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(201).json({ name: result.name, password:result.password,image:result.image });
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'User creation failed' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
    }
};

const signIn = async (req, res) => {
    try {
        const result = await userService.findUserByNP(
            req.body.name,
            req.body.password
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(201).json({ id:result._id});
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'User doesnt exist' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
    }
};

module.exports = {createUser, getUser,signIn};