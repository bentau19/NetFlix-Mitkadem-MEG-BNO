const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const { generateId } = require('../utils/idManager');
const Movie = new Schema({
    _id: { 
        type: Number, 
        required: true 
    },
    title: { 
        type: String, 
        required: true 
    },
    categories: [
        {
            type: Number,
            ref: 'cat'
        }
    ]
});
Movie.pre('save', async function (next) {
    if (this.isNew) {
        this._id = await generateId('movieId');
    }
    next();
});
module.exports = mongoose.model('Movie', Movie);
