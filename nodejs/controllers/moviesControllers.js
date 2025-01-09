
const MovieService = require('../services/MoviesService');
const ERROR_MESSAGES = require('../validation/errorMessages');

const getMoviesByCategories = async (req, res) => {
    try {
        const result = await MovieService.getMoviesByCategory(
            req.headers['userid']
        );

        res.status(201).json(result);
        
    } catch (error) {
        if(error==ERROR_MESSAGES.SERVER_ERROR)
        res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR });
        else res.status(400).json({ message: error });
    }
};
const createMovie = async (req, res) => {
    try {
        const result = await MovieService.createMovie(
            req.body.title,
            req.body.logline,
            req.body.image,
            req.body.categories
        );
        if (result) {
            res.status(201).json({ message: 'Movie created successfully',_id:result._id });
        } else {
            res.status(400).json({ message: 'Movie creation failed' });
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error||ERROR_MESSAGES.Existing("movie")==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: error});
    }
}

const getMovieById = async (req, res) => {
        try {
            const result = await MovieService.getMovieById(
                req.params.id
            );
            if (result) {
                // Assuming createUser returns a truthy value on success
                res.status(201).json(result);
            } else {
                // If createUser returns a falsy value (e.g., null or undefined)
                res.status(400).json({ message: 'no movie at this value' });
            }
        } catch (error) {
            res.status(500).json({ message: 'An internal server error occurred', error: error.message });
        }
    };
const switchMovie = async (req, res) => {
    try {
        const result = await MovieService.updateMovie(
            req.params.id,req.body
        );
        if (result) {
            res.status(201).json(result);
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};
const deleteMovie = async (req, res) => {
       try {
            const result = await MovieService.deleteMovie(
                req.params.id
            );
            if (result)
            res.status(201).json({ message: 'User created successfully',_id:result._id });
            else res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        } catch (error) {
            if( ERROR_MESSAGES.BAD_REQUEST==error)
                res.status(400).json({ message: error});
            else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
        }
};
const getRecommendMovie = async (req, res) => {
    try {
        const result = await MovieService.getRecommendMovie(
            req.headers['userid'],
            req.params.id
        );
        if (result) {
            res.status(201).json(result);
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error||ERROR_MESSAGES.Existing("user")==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};
const addMovieToUser = async (req, res) => {
    try {
        const result = await MovieService.addMovieToUser(
            req.headers['userid'],
            req.params.id
        );
        if (result) {
            res.status(201).json(result);
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error||ERROR_MESSAGES.Existing("movie")==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};
const getQueryMovie = async (req, res) => {
    try {
        const query = req.params.query; // Get the query parameter from the URL

        const result = await MovieService.getQueryMovie(
            query
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(201).json(result);
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};

module.exports = {deleteMovie, switchMovie,getMovieById,createMovie,getMoviesByCategories
    , getRecommendMovie,addMovieToUser,getQueryMovie };