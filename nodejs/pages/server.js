// server.js - Move this file to the root directory, NOT in public
const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const path = require('path');
const cors = require('cors');

// Import routes
const usersRoute = require('../routes/user');
const tokenRoute = require('../routes/token');
const categoriesRoute = require('../routes/categories');
const moviesRoute = require('../routes/movies');

require('custom-env').env(process.env.NODE_ENV, './config');
mongoose.connect(process.env.CONNECTION_STRING);

const app = express();

// Serve static files from the public directory
app.use(express.static(path.join(__dirname, 'public')));

app.use(cors());
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.json());

// Routes
app.use('/api/users', usersRoute);
app.use('/api/tokens', tokenRoute);
app.use('/api/categories', categoriesRoute);
app.use('/api/movies', moviesRoute);

// Serve the index.html page
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// Undefined route handler
app.use((req, res, next) => {
    res.status(404).json({
        error: {
            message: '404 : Route not found',
        },
    });
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});