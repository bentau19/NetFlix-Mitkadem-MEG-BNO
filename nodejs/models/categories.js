const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Categorie = new Schema({
    _id: { 
        type: Number, 
        required: true 
    },
    name: { 
        type: String, 
        required: true 
    },
    movies: [
        {
            type: Number,
            ref: 'movie'
        }
    ],
    recommended:{
        type: Boolean,
        default: false
    }
});

module.exports = mongoose.model('Categorie', Categorie);
