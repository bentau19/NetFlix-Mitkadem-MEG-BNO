const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const IdsCounter = new Schema({
    _id: { type: String, required: true }, // type to count
    seq: { type: Number, default: 0 }, // Current id
    reusableIds: { type: [Number], default: [] } // Pool of reusable IDs
});

module.exports = mongoose.model('Counter', IdsCounter);
