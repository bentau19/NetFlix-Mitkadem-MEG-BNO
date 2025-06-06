const counter = require('../utils/idManager');
const Movies = require('../models/movies');
const Categories = require('../models/categories');
const Users = require('../models/user');
const movies = require('../models/movies');
const serverData = require('./SendData');
const ERROR_MESSAGES = require('../validation/errorMessages');
const { convertToHex } = require('../utils/imageConverter');
const fs = require('fs');

const getMoviesByCategory = async (userId) => {
  try {
    if (!userId) {
      throw ERROR_MESSAGES.BAD_REQUEST;
    }

    // Step 1: Get the user's watched movies
    const user = await Users.findById(userId);
    if (!user) {
      throw 'User not found';
    }

    const watchedMovies = user.movies.map((movie) => movie.movie); // Extract movie IDs
    const lastWatchedMovies = user.movies
      .sort((a, b) => b.whenWatched - a.whenWatched) // Sort by most recent
      .slice(0, 20)
      .map((movie) => movie.movie); // Extract the last 20 watched movie IDs

    // Step 2: Find all promoted categories
    const categories = await Categories.find({ promoted: true });

    // Step 3: Loop through categories and fetch movies
    const unwatchedCategories = await Promise.all(
      categories.map(async (category) => {
        // Filter movies to exclude those the user has watched
        const unwatchedMovieIds = category.movies.filter(
          (movieId) => !watchedMovies.includes(movieId)
        );

        // Limit to 20 unwatched movie IDs
        const topUnwatchedMovieIds = unwatchedMovieIds.slice(0, 20);

        // Fetch movie details from the Movies collection
        const topUnwatchedMovies = await Movies.find({
          _id: { $in: topUnwatchedMovieIds },
        });

        return {
          name: category.name, // Include category name
          movies: topUnwatchedMovies, // Actual movie objects
        };
      })
    );

    // Step 4: Add the "watched" category
    const watchedMoviesDetails = await Movies.find({
      _id: { $in: lastWatchedMovies },
    });

    const watchedCategory = {
      name: 'Watched',
      movies: watchedMoviesDetails, // Actual movie objects
    };

    // Step 5: Combine results
    const result = [...unwatchedCategories, watchedCategory];

    return result;

  } catch (error) {
    console.error(error); // Log the actual error for debugging
    throw ERROR_MESSAGES.SERVER_ERROR;
  }
};


const getMovieById = async (id) => {
  if (!id) {
    throw ERROR_MESSAGES.BAD_REQUEST;
  }
            const movie = await Movies.findById(id);
            return movie; 
};

const createMovie = async (title, logline, image, categories) => {
  if (!title) {
    throw ERROR_MESSAGES.BAD_REQUEST;
  }

  const test = await Movies.findOne({ title: title });
  if (test) throw ERROR_MESSAGES.Existing("movie");

  const movies = new Movies({ title: title });

  let categoriesArray = [];
  if (categories) {
    if (typeof categories === "string") {
      try {
        categoriesArray = JSON.parse(categories);
      } catch (error) {
        console.log("Error parsing categories:", error); // Log parse errors
        throw ERROR_MESSAGES.BAD_REQUEST;
      }
    } else {
      categoriesArray = categories;
    }

    // Validate categories before saving the movie
    for (const id of categoriesArray) {
      const category = await Categories.findOne({ _id: id });
      if (!category) {
        console.log("Invalid category ID:", id);
        throw ERROR_MESSAGES.BAD_REQUEST;
      }
    }

    movies.categories = categoriesArray;
  }

  if (logline) movies.logline = logline;
  if (image) movies.image = image;

  try {
    const res = await movies.save(); // Save the movie

    // Handle category associations if categories are present
    if (categoriesArray.length) {
      for (const categoryId of categoriesArray) {
        const category = await Categories.findOne({ _id: categoryId });
        if (category) {
          category.movies.push(res._id);
          await category.save();
        }
      }
    }

    return res; // Return the saved movie
  } catch (error) {
    console.log("Error creating movie:", error); // Log the error details
    throw ERROR_MESSAGES.BAD_REQUEST; // Return error message if saving fails
  }
};


const updateMovie = async (movieId, updateData) => {
  if (!movieId) {
    throw ERROR_MESSAGES.BAD_REQUEST;
  }

  const movie = await Movies.findById(movieId);
  if (!movie) {
    throw ERROR_MESSAGES.BAD_REQUEST;
  }

  // First, remove the movie from all categories
  await Categories.updateMany(
    { movies: movieId },  // Find categories where the movie is listed
    { $pull: { movies: movieId } }  // Remove the movie from the category's movies array
  );

  // Step 3: Prepare the updated movie data
  const updatedMovie = { ...updateData };
  updatedMovie._id = movieId;

  // Handle the categories logic
  if (updatedMovie.categories) {
    let categoriesArray = [];

    if (typeof updatedMovie.categories === "string") {
      try {
        categoriesArray = JSON.parse(updatedMovie.categories); // Convert string to array
      } catch (error) {
        console.log("Error parsing categories:", error); // Log parse errors
        throw ERROR_MESSAGES.BAD_REQUEST;
      }
    } else {
      categoriesArray = updatedMovie.categories; // If it's already an array
    }

    // Validate categories before updating the movie
    for (const id of categoriesArray) {
      const category = await Categories.findOne({ _id: id });
      if (!category) {
        console.log("Invalid category ID:", id);
        throw ERROR_MESSAGES.BAD_REQUEST;
      }
    }

    updatedMovie.categories = categoriesArray;

    // Add the movie to the new categories
    await Categories.updateMany(
      { _id: { $in: updatedMovie.categories } },  // Find categories with the given IDs
      { $addToSet: { movies: movieId } }  // Add movieId to the movies array if not already present
    );
  }

  // Step 4: Update the movie document with the new data
  const updated = await Movies.findOneAndReplace(
    { _id: movieId }, // Filter to find the document
    updatedMovie,     // The new object to replace the document with
    { new: true }     // Return the replaced document after the operation
  );

  if (!updated) {
    throw 'Failed to update movie';
  }

  return updated; // Return the updated movie object
};
const deleteMovie = async (id) => {

      const movie = await Movies.findById(id);
      if(!movie)throw ERROR_MESSAGES.BAD_REQUEST;
      const users = await Users.find(
        { 'movies.movie': id }, // Find users who have watched the movie
        { _id: 1 } // Only return the _id field
      );
  
      // Step 2: Extract user IDs from the result
      const userIds = users.map(user => user._id);
  
      // Step 3: Apply the function to each user ID
      for (const userId of userIds) {
         await serverData.communicateWithServer("DELETE "+userId+" "+id);                
      }
      await Users.updateMany(
        { 'movies.movie': id }, // Find users who watched the movie
        { $pull: { movies: { movie: id } } } // Remove the movie from the movies array
      );
      await Categories.updateMany(
        { 'movies': id }, // Find categories that have the movie ID in their 'movies' array
        { $pull: { movies: id } } // Remove the movie ID from the 'movies' array
      );
    
      await Movies.deleteOne({ _id: id });
      counter.addReusableId("movieId", id);
      return movie; // Will return the user or null if not found
};
const getRecommendMovie = async (userId,movieId) => {

  if(!userId||!movieId)throw ERROR_MESSAGES.BAD_REQUEST;
  const user = await Users.findOne({ _id: userId });
  if (!user) throw ERROR_MESSAGES.BAD_REQUEST;

  // Check if the movie exists
  const movie = await Movies.findOne({ _id: movieId });
  if (!movie) throw  ERROR_MESSAGES.BAD_REQUEST;
      const res=await serverData.communicateWithServer("GET "+userId+" "+movieId);  
      if(res==="200 Ok \n \n\n")return[]
      const lines = res.trim().replace(/^200 Ok\s*\n+/i, '');
      const arr = lines.split(" ").map(Number);
      const movies = await Movies.find({ _id: { $in: arr } }).exec();
      return movies; // Will return the user or null if not found              
};

const addMovieToUser = async (userId,movieId) => {
      if(!userId||!movieId)throw ERROR_MESSAGES.BAD_REQUEST;
      const user = await Users.findOne({ _id: userId });
      if (!user) throw ERROR_MESSAGES.BAD_REQUEST;
  
      // Check if the movie exists
      const movie = await Movies.findOne({ _id: movieId });
      if (!movie) throw  ERROR_MESSAGES.BAD_REQUEST;
    
       const test = await Users.findOne({'_id':userId, 'movies.movie': movieId });
      if (test) throw ERROR_MESSAGES.Existing("movie");   
      const result = await Users.updateOne(
        { _id: userId }, // Find the user by their _id
        { $push: { movies: { movie: movieId, whenWatched: new Date() } } } // Add movieId to the movies array
      );
      const res=await serverData.communicateWithServer("PATCH "+userId+" "+movieId);
      return res; // Will return the user or null if not found
};
const getQueryMovie = async (query) => {
  if(!query)
    return await Movies.find({}); 
  // Convert query to an integer for category search (if valid number)
  const categoryQuery = parseInt(query, 10);

  // Start constructing the conditions object with title and logline regex search
  const conditions = {
    $or: [
      { title: { $regex: query, $options: 'i' } },  // Case-insensitive search on title
      { logline: { $regex: query, $options: 'i' } }  // Case-insensitive search on logline
    ]
  };

  // If the query is a valid number, add the categories condition to the search
  if (!isNaN(categoryQuery)) {
    // Here, we need to combine the conditions without replacing anything
    conditions.$or.push({ categories: { $in: [categoryQuery] } });  // Add categories to the existing $or
  }

  // Perform the search with the constructed conditions
  const testQuery = await Movies.find(conditions);

  return testQuery;
};


 
module.exports = { getMoviesByCategory,getMovieById,createMovie,updateMovie
  ,getRecommendMovie,deleteMovie,addMovieToUser,getQueryMovie
}