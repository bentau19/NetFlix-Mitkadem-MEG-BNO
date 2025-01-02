const User = require('../models/user');

const createUser = async (name, image, password) => {
    const temp = { name : name, password:password };
    if (image) temp.image = image;
    const user = new User(temp);
    return await user.save();
}

const getUser = async (id) => {
    return await User.findById(id);
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