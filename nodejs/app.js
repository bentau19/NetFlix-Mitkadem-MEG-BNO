const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const mongoose = require('mongoose');
const users = require('./routes/user');
const token = require('./routes/token');
const categories = require('./routes/categories');
const movies = require('./routes/movies');


require('custom-env').env(process.env.NODE_ENV, './config');
mongoose.connect(process.env.CONNECTION_STRING);
var app = express();
app.use(cors());
app.use(bodyParser.urlencoded({extended : true}));
app.use(express.json());
app.use('/api/users', users);
app.use('/api/tokens', token);
app.use('/api/categories', categories);
app.use('/api/movies', movies);
// Undefined route handler

app.use((req, res, next) => {
  res.status(404).json({
      error: {
          message: '404 : Route not found',
      },
  });
});
app.listen(process.env.PORT, () => {
    console.log(`Server is running on port ${process.env.PORT}`);
  });
app.use(cors({
  origin: 'http://localhost:3000', // Replace with your React app's URL if different
  credentials: true,
}));
  