const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const { generateId } = require('../utils/idManager');
const Categorie = new Schema({
    _id: { 
        type: Number, 
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
    promoted:{
        type: Boolean,
        default: false
    }
});
Categorie.pre('save', async function (next) {
    if (this.isNew) {
        this._id = await generateId('categorieId');
    }
    next();
});
module.exports = mongoose.model('Categorie', Categorie);
