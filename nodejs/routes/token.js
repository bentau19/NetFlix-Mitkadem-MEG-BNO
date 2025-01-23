const express = require('express');
var router = express.Router();
const userController = require('../controllers/userController');
router.route('/')
.get(userController.getUserByToken)
.post(userController.signIn);
module.exports = router;