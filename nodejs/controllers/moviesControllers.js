
const MovieService = require('../services/MoviesService');

const getMoviesByCategories = async (req, res) => {
    try {
        const result = await MovieService.getMovieById(
            req.body.userId
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(201).json(result);
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'Movie creation failed' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
    }
};
const createMovie = async (req, res) => {
    try {
        const result = await MovieService.createMovie(
            req.body.title,
            req.body.logline,
            req.body.image
        );
        if (result) {
            try{
            res.status(201).json({ message: 'Movie created successfully',_id:result._id });
            }catch(error){
                console.error(error); // Log the error for debugging
                res.status(500).json({ message: 'An internal server error occurred', error: error.message });
            }
            // Assuming createUser returns a truthy value on success
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'Movie creation failed' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
    }
}

const getMovieById = async (req, res) => {
        try {
            const result = await MovieService.getMovieById(
                req.params.id
            );
            if (result) {
                // Assuming createUser returns a truthy value on success
                res.status(201).json({ name: result.title,categories:result.categories });
            } else {
                // If createUser returns a falsy value (e.g., null or undefined)
                res.status(400).json({ message: 'Movie creation failed' });
            }
        } catch (error) {
            // Handle unexpected errors
            console.error(error); // Log the error for debugging
            res.status(500).json({ message: 'An internal server error occurred', error: error.message });
        }
    };
const switchMovie = async (req, res) => {
    try {
        const result = await MovieService.updateMovie(
            req.params.id,req.body
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(201).json(result);
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'Movie creation failed' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
    }
};
const deleteMovie = async (req, res) => {
       try {
            const result = await MovieService.deleteMovie(
                req.params.id
            );
            if (result) {
                try{
                res.status(201).json({ message: 'User created successfully',_id:result._id });
                }catch(error){
                    console.error(error); // Log the error for debugging
                    res.status(500).json({ message: 'An internal server error occurred', error: error.message });
                }
                // Assuming createUser returns a truthy value on success
            } else {
                // If createUser returns a falsy value (e.g., null or undefined)
                res.status(400).json({ message: 'User creation failed' });
            }
        } catch (error) {
            // Handle unexpected errors
            console.error(error); // Log the error for debugging
            res.status(500).json({ message: 'An internal server error occurred', error: error.message });
        }
};
const getRecommendMovie = async (req, res) => {
    try {
        const result = await MovieService.getRecommendMovie(
            req.body.userId,
            req.params.id
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(201).json(result);
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'Movie creation failed' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
    }
};
const addMovieToUser = async (req, res) => {
    try {
        const result = await MovieService.addMovieToUser(
            req.body.userId,
            req.params.id
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(201).json(result);
        } else {
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'Movie creation failed' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
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
            // If createUser returns a falsy value (e.g., null or undefined)
            res.status(400).json({ message: 'Movie creation failed' });
        }
    } catch (error) {
        // Handle unexpected errors
        console.error(error); // Log the error for debugging
        res.status(500).json({ message: 'An internal server error occurred', error: error.message });
    }
};

module.exports = {deleteMovie, switchMovie,getMovieById,createMovie,getMoviesByCategories
    , getRecommendMovie,addMovieToUser,getQueryMovie };