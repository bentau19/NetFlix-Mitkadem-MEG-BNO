const User = require('../models/user');
const serverData = require('../services/SendData');
const ERROR_MESSAGES = require('../validation/errorMessages');
const jwt = require('jsonwebtoken');
require('dotenv').config();
const SECRET_KEY = process.env.SECRET_KEY;

const createUser = async (displayName,userName, image, password) => {
    if(!displayName||!userName||!password)throw ERROR_MESSAGES.VALIDATION_FAILED;
    const test = await User.findOne({ userName: userName });
    if (test) throw ERROR_MESSAGES.Existing("user");
    const temp = { displayName : displayName,userName: userName, password:password };
    if (image) temp.image = image;
    const user = new User(temp);
    try{
        const res =await user.save();
        try{
            await serverData.communicateWithServer("POST "+res._id+" 0");
            await serverData.communicateWithServer("DELETE "+res._id+" 0");
        }catch(error){
            await User.deleteOne({ userName: userName });
            throw ERROR_MESSAGES.SERVER_ERROR;
        }

        return res;
    }catch(err){
        throw ERROR_MESSAGES.VALIDATION_FAILED;
    }
    
}

const getUser = async (id) => {
    try{
        const res =await User.findById(id);;
        return res;
    }catch(err){
        throw ERROR_MESSAGES.VALIDATION_FAILED;
    }
};


async function generateJWT(user) {
    const payload = {
        _id: user._id,
        userName: user.userName,
        displayName: user.displayName,
        admin: user.admin,
    };

    const token = jwt.sign(payload, SECRET_KEY, {
        expiresIn: '1h', // Token expiration time
    });

    return token;
}

const findUserByNP = async (userName, password) => {
    try {
        const user = await User.findOne({ userName: userName, password: password });
        if(!user)return null;
        (async () => {
            try {
                const token = await generateJWT(user); // Replace 123 with the desired user ID
                return(token);
            } catch (err) {
                return null;
            }
        })();
    } catch (err) {
        return null;
    }
}

function verifyToken(token) {
    try {
        return jwt.verify(token, SECRET_KEY);
    } catch (error) {
        throw new Error('Invalid or expired token');
    }
}


function isManager(token) {
    const decoded = verifyToken(token);
    return decoded.admin === true; // Check the admin field in the token payload
}


function getUserId(token) {
    const decoded = verifyToken(token);
    return decoded._id; // Extract the user ID from the token payload
}

module.exports = {createUser,getUser,findUserByNP}