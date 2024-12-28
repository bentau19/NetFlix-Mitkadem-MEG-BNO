const mongoose = require('mongoose');
const Schema = mongoose.Schema;

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

module.exports = mongoose.model('Movie', Movie);
