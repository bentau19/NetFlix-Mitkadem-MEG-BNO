const express = require('express');
var router = express.Router();
const categoryController = require('../controllers/categories');
router.route('/')
.get(categoryController.getMoviesByCategory)
.post(categoryController.createMovie);
router.route('/:id')
.get(categoryController.getCategort)
.put(categoryController.updateCategory)
.delete(categoryController.deleteCategory);
module.exports = router;