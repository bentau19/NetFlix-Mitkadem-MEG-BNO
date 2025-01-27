
const MovieService = require('../services/MoviesService');
const UserService = require('../services/UsersService');
const ERROR_MESSAGES = require('../validation/errorMessages');
const fs = require('fs');
const path = require('path');
const getMoviesByCategories = async (req, res) => {
    try {
        const id =UserService.isUser(req.headers['token']);
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
        if(!UserService.isManager(req.headers['token'])){
            throw ERROR_MESSAGES.BAD_REQUEST;
        }
        const result = await MovieService.createMovieWithImage(
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
                res.status(200).json(result);
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
        if(!UserService.isManager(req.headers['token'])){
            throw ERROR_MESSAGES.BAD_REQUEST;
        }
        const result = await MovieService.updateMovie(
            req.params.id,req.body
        );
        if (result) {
            res.status(204).json(result);
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
        if(!UserService.isManager(req.headers['token'])){
            throw ERROR_MESSAGES.BAD_REQUEST;
        }
            const result = await MovieService.deleteMovie(
                req.params.id
            );
            if (result)
            res.status(204).json({ message: 'Movie deleted successfully' });
            else res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        } catch (error) {
            if( ERROR_MESSAGES.BAD_REQUEST==error)
                res.status(400).json({ message: error});
            else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
        }
};
const getRecommendMovie = async (req, res) => {
    try {
        const id =UserService.isUser(req.headers['token']);
        const result = await MovieService.getRecommendMovie(
            id,
            req.params.id
        );
        if (result) {
            res.status(200).json(result);
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
        const id =UserService.isUser(req.headers['token']);
        const result = await MovieService.addMovieToUser(
            id,
            req.params.id
        );
        if (result) {
            res.status(204).json(result);
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
        const query = req.params.query||''; // Get the query parameter from the URL
        
        const result = await MovieService.getQueryMovie(
            query
        );
        if (result) {
            // Assuming createUser returns a truthy value on success
            res.status(200).json(result);
        } else {
            res.status(400).json({ message: ERROR_MESSAGES.BAD_REQUEST});
        }
    } catch (error) {
        if( ERROR_MESSAGES.BAD_REQUEST==error)
            res.status(400).json({ message: error});
        else res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR});
    }
};
const play = async (req, res) => {
    try {
        const fileName = req.params.id;
        const movie = await MovieService.getMovieById(fileName);
        const filePath = path.join(__dirname, "../public", movie.title + ".mp4");
    if(!filePath){
        return res.status(404).send('File not found')
    }

    const stat = fs.statSync(filePath);
    const fileSize = stat.size;
    const range = req.headers.range;

    if(range){
        const parts = range.replace(/bytes=/, '').split('-')
        const start = parseInt(parts[0], 10);
        const end = parts[1] ? parseInt(parts[1], 10) : fileSize - 1;

        const chunksize = end - start + 1;
        const file = fs.createReadStream(filePath, {start, end});
        const head = {
            'Content-Range': `bytes ${start}-${end}/${fileSize}`,
            'Accept-Ranges': 'bytes',
            'Content-Length': chunksize,
            'Content-Type': 'video/mp4'
        };
        res.writeHead(206, head);
        file.pipe(res);
    }
    else{
        const head = {
            'Content-Length': fileSize,
            'Content-Type': 'video/mp4'
        };
        res.writeHead(200, head);
        fs.createReadStream(filePath).pipe(res)
    }
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: ERROR_MESSAGES.SERVER_ERROR, error: error.message });
    }
};
module.exports = {deleteMovie, switchMovie,getMovieById,createMovie,getMoviesByCategories
    , getRecommendMovie,addMovieToUser,getQueryMovie,play };
