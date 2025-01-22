const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const { generateId } = require('../utils/idManager');
const User = new Schema({

    _id: { 
        type: Number 
    },
    userName: {
        type: String,
        required: true
    },
    displayName: {
        type: String,
        required: true
    },
    image: {
        type: Buffer, // Store binary data
        default: null
    },
    password: {
        type: String,
        required: true
    },
    admin:{
        type:Boolean,
        default:false
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

User.pre('save', async function (next) {
    if (this.isNew) {
        this._id = await generateId('userId');
    }
    next();
});
module.exports = mongoose.model('User', User);

