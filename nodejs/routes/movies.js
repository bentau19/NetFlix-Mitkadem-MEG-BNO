const express = require('express');
var router = express.Router();
const movieController = require('../controllers/movies');
router.route('/')
.get(movieController.getMoviesByCategory)
.post(movieController.createMovie);
router.route('/:id')
.get(movieController.getMovie)
.put(movieController.switchMovie)
.delete(movieController.deleteMovie);
router.route('/:id/recommend/')
.get(movieController.getRecommendMovie)
.delete(movieController.addRecommendMovie);
router.route('/search/:query/')
.get(movieController.getQueryMovie)
module.exports = router;