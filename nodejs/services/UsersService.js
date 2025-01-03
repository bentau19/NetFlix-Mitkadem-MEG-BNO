const User = require('../models/user');
const ERROR_MESSAGES = require('../validation/errorMessages');
const createUser = async (name, image, password) => {
    const test = await User.findOne({ name, password });
    if (test) throw new Error(ERROR_MESSAGES.Existing("user"));
    if(!(name&&password)) throw ERROR_MESSAGES.DBFail;
    const temp = { name : name, password:password };
    if (image) temp.image = image;
    const user = new User(temp);
    try{
        const res =await user.save();
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


const findUserByNP = async (name, password) => {
    try {
        // Query the database for a user with matching name and password
        const user = await User.findOne({ name, password });
        return user; // Will return the user or null if not found
    } catch (err) {
        return null;
    }
};

module.exports = {createUser,getUser,findUserByNP}