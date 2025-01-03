const express = require('express');
var router = express.Router();
const movieController = require('../controllers/moviesControllers');
router.route('/')
.get(movieController.getMoviesByCategories)
.post(movieController.createMovie);
router.route('/:id')
.get(movieController.getMovieById)
.put(movieController.switchMovie)
.delete(movieController.deleteMovie);
router.route('/:id/recommend')
.get(movieController.getRecommendMovie)
.post(movieController.addMovieToUser);
router.route('/search/:query')
.get(movieController.getQueryMovie)
module.exports = router;