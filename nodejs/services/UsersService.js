const User = require('../models/user');
const serverData = require('../services/SendData');
const ERROR_MESSAGES = require('../validation/errorMessages');
const jwt = require('jsonwebtoken');
require('dotenv').config();
const SECRET_KEY = process.env.SECRET_KEY;

function isPasswordValid(password) {
    // Ensure password is a string and meets criteria
    if (typeof password !== 'string') {
        return false;
    }
    const hasLetters = /[a-zA-Z]/.test(password); // Check if it contains letters
    const hasNumbers = /\d/.test(password);       // Check if it contains numbers
    const isAtLeast8Chars = password.length >= 8; // Check if it's at least 8 characters long
    
    return hasLetters && hasNumbers && isAtLeast8Chars;
}

const createUser = async (displayName,userName, image, password) => {
    if(!displayName||!userName||!password)throw ERROR_MESSAGES.VALIDATION_FAILED;
    if(!isPasswordValid(password))throw ERROR_MESSAGES.VALIDATION_FAILED;
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

const getUserbyToken = async (token) => {
    try{
        const userName= getUserName(token);
        const res = await User.findOne({ userName: userName});
        return res;
    }catch(err){
        if(err==ERROR_MESSAGES.BAD_REQUEST)throw err;
        throw ERROR_MESSAGES.VALIDATION_FAILED;
    }
};

async function generateJWT(user) {
    const payload = {
        _id:user._id,
        userName: user.userName,
        admin: user.admin,
    };
    const token = jwt.sign(payload, SECRET_KEY, {
        expiresIn: '7d', // Token expiration time
    });

    return token;
}

const findUserByNP = async (userName, password) => {
    try {
        const user = await User.findOne({ userName: userName, password: password });
        if(!user)return null;
                const token = await generateJWT(user); // Replace 123 with the desired user ID
                return(token);
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
    try{
        const decoded = verifyToken(token);
        return decoded.admin === true;
    }catch(e){
        throw ERROR_MESSAGES.BAD_REQUEST;
    }
}
function isUser(token) {
    try{
        const decoded= verifyToken(token);
        return decoded._id;
    }catch(e){
        throw ERROR_MESSAGES.BAD_REQUEST;
    }
}

function getUserName(token) {
    try{
        const decoded = verifyToken(token);
        return decoded.userName; 
    }catch(e){
        throw ERROR_MESSAGES.BAD_REQUEST;
    }
}

module.exports = {createUser,getUser,findUserByNP,getUserbyToken,isUser,isManager}