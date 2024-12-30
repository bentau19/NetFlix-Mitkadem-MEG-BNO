const express = require('express');
var router = express.Router();
const categoryController = require('../controllers/movies');
router.route('/')
.get(categoryController.getMoviesByCategory)
.post(categoryController.createMovie);
router.route('/:id')
.get(categoryController.getMovie)
.put(categoryController.switchMovie)
.delete(categoryController.deleteMovie);
module.exports = router;