const User = require('../models/user');
const serverData = require('../services/SendData');
const ERROR_MESSAGES = require('../validation/errorMessages');
const createUser = async (name,email, image, password) => {
    if(!name||!email||!password)throw ERROR_MESSAGES.VALIDATION_FAILED;
    const test = await User.findOne({ email: email });
    if (test) throw ERROR_MESSAGES.Existing("user");
    if(!(name&&password)) throw ERROR_MESSAGES.DBFail;
    const temp = { name : name,email: email, password:password };
    if (image) temp.image = image;
    const user = new User(temp);
    try{
        const res =await user.save();
        try{
            await serverData.communicateWithServer("POST "+res._id+" 0");
            await serverData.communicateWithServer("DELETE "+res._id+" 0");
        }catch(error){
            await User.deleteOne({ email: email });
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


const findUserByNP = async (email, password) => {
    try {
        const user = await User.findOne({ email: email, password: password });
        return user; 
    } catch (err) {
        return null;
    }
};

module.exports = {createUser,getUser,findUserByNP}