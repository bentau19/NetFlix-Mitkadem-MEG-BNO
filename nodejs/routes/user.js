const express = require('express');
var router = express.Router();
const userController = require('../controllers/userController');
router.route('/')
.post(userController.createUser);
router.route('/:id')
.get(userController.getUser)
module.exports = router;