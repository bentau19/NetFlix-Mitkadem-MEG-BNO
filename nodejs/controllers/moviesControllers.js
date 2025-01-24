
const MovieService = require('../services/MoviesService');
const UserService = require('../services/UsersService');
const ERROR_MESSAGES = require('../validation/errorMessages');
const VALIDITY_FUNC = require('../validation/validityFunc');
const getMoviesByCategories = async (req, res) => {
    try {
        const id = UserService.isUser(req.headers['token']);
        const result = await MovieService.getMoviesByCategory(
            id
        );
        res.status(200).json(result);
    } catch (error) {
        if(error==ERROR_MESSAGES.SERVER_ERROR)
        res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR });
        else res.status(400).json({ message: error });
    }
};

const createMovie = async (req, res) => {
    try {
        VALIDITY_FUNC.adminExistingValidity(req.headers['token'])
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
        if( ERROR_MESSAGES.Existing("movie")==error)
            res.status(400).json({ message: error});
        else VALIDITY_FUNC.catchAction(error,res);
    }
}

const getMovieById = async (req, res) => {
        try {
            const result = await MovieService.getMovieById(
                req.params.id
            );
            if (result) {
                // Assuming createUser returns a truthy value on success
                res.status(200).json(result);
            } else {
                // If createUser returns a falsy value (e.g., null or undefined)
                res.status(400).json({ message: 'no movie at this value' });
            }
        } catch (error) {
            VALIDITY_FUNC.catchAction(error,res);
        }
    };

    
const switchMovie = async (req, res) => {
    try {
        VALIDITY_FUNC.adminExistingValidity(req.headers['token'])
        const result = await MovieService.updateMovie(
            req.params.id,req.body
        );
        VALIDITY_FUNC.validProgram(result,204,res)

    } catch (error) {
        VALIDITY_FUNC.catchAction(error,res);
    }
};
const deleteMovie = async (req, res) => {
       try {
        VALIDITY_FUNC.adminExistingValidity(req.headers['token'])
            const result = await MovieService.deleteMovie(
                req.params.id
            );
            VALIDITY_FUNC.validProgram(result,204,res)
        } catch (error) {
            VALIDITY_FUNC.catchAction(error,res);
        }
};
const getRecommendMovie = async (req, res) => {
    try {
        const id =UserService.isUser(req.headers['token']);
        const result = await MovieService.getRecommendMovie(
            id,
            req.params.id
        );
        VALIDITY_FUNC.validProgram(result,200,res)
    } catch (error) {
        if( ERROR_MESSAGES.Existing("user")==error)
            res.status(400).json({ message: error});
        else VALIDITY_FUNC.catchAction(error,res);
    }
};
const addMovieToUser = async (req, res) => {
    try {
        const id =UserService.isUser(req.headers['token']);
        const result = await MovieService.addMovieToUser(
            id,
            req.params.id
        );
        VALIDITY_FUNC.validProgram(result,204,res)
    } catch (error) {
        if( ERROR_MESSAGES.Existing("movie")==error)
            res.status(400).json({ message: error});
        else VALIDITY_FUNC.catchAction(error,res);
    }
};
const getQueryMovie = async (req, res) => {
    try {
        const query = req.params.query||''; // Get the query parameter from the URL
        
        const result = await MovieService.getQueryMovie(
            query
        );
        VALIDITY_FUNC.validProgram(result,200,res)
    } catch (error) {
        VALIDITY_FUNC.catchAction(error,res);
    }
};

module.exports = {deleteMovie, switchMovie,getMovieById,createMovie,getMoviesByCategories
    , getRecommendMovie,addMovieToUser,getQueryMovie };
