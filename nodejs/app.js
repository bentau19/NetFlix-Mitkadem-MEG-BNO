const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const mongoose = require('mongoose');
const multer = require('multer');
const path = require('path');
const fs = require('fs');
const users = require('./routes/user');
const token = require('./routes/token');
const categories = require('./routes/categories');
const movies = require('./routes/movies');

require('custom-env').env(process.env.NODE_ENV, './config');
mongoose.connect(process.env.CONNECTION_STRING);

const app = express();
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.json());
app.use('/api/users', users);
app.use('/api/tokens', token);
app.use('/api/categories', categories);
app.use('/api/movies', movies);

// Create multer upload instance

// Middleware to serve static files (images)
app.use('/uploads', express.static('uploads'));  // Ensure your images are publicly accessible

// Start server
app.listen(process.env.PORT, () => {
  console.log(`Server is running on port ${process.env.PORT}`);
});
