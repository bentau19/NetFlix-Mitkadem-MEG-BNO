const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const AutoIncrement = require('mongoose-sequence')(mongoose);

const UserSchema = new Schema({
    _id: { 
        type: Number 
    },
    name: {
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

// Attach auto-increment plugin to generate unique `_id` values
UserSchema.plugin(AutoIncrement, { id: 'user_seq', inc_field: '_id', start_seq: 0 });

module.exports = mongoose.model('User', UserSchema);
