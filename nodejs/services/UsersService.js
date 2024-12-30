const User = require('../models/user');

const createUser = async (name, image, password) => {
    const user = new User({ name : name,image:image,password:password });
    return await user.save();
}

const getUser = async (id) => {
    return await User.findById(id);
};

module.exports = {createUser,getUser}