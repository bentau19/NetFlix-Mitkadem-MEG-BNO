const express = require('express');
var router = express.Router();
const categoryController = require('../controllers/categoriesControllers');
router.route('/')
.get(categoryController.getCategories)
.post(categoryController.createCategory);
router.route('/search/:query?')
.get(categoryController.getQueryCat)
router.route('/:id')
.get(categoryController.getCategorieById)
.patch(categoryController.updateCategory)
.delete(categoryController.deleteCategory);
module.exports = router;