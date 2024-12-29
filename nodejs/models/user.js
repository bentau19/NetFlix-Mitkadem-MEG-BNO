const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const User = new Schema({
    _id: { 
        type: Number, 
        required: true 
    },
    name:{
        type: String,
        required: true
    },
    image: {
        type: Buffer, // Store binary data
        default: null
    },
    password:{
        type: String,
        required: true
    },
    movies: [
        {
            movie: {
                type: Number, // Reference to the movie ID
                ref: 'movie',
                required: true
            },
            whenWatched: {
                type: Date,
                default: Date.now
            }
        }
    ]

});

module.exports = mongoose.model('User', User);