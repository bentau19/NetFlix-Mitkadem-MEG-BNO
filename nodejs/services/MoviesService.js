const Movies = require('../models/movies');
const Categories = require('../models/categories');
const Users = require('../models/user');
const movies = require('../models/movies');
const serverData = require('./SendData');
const getMoviesByCategory = async (userId) => {
    try {
      // Step 1: Get the user's watched movies
      const user = await Users.findById(userId);
      if (!user) {
        throw new Error('User not found');
      }
      const watchedMovies = user.movies.map((movie) => movie.movie); // Extract movie IDs
      const lastWatchedMovies = user.movies
        .sort((a, b) => b.whenWatched - a.whenWatched) // Sort by most recent
        .slice(0, 20)
        .map((movie) => movie.movie); // Extract the last 20 watched movies
  
      // Step 2: Find all promoted categories
      const categories = await Categories.find({ promoted: true });
  
      // Step 3: Loop through categories and fetch movies
      const unwatchedCategories = await Promise.all(categories.map(async (category) => {
        // Filter movies to exclude those the user has watched
        const unwatchedMovies = category.movies.filter(
          (movieId) => !watchedMovies.includes(movieId)
        );
  
        // Limit to 20 unwatched movies
        const topUnwatchedMovies = unwatchedMovies.slice(0, 20);
  
        return {
          categoryName: category.name, // Include category name
          movies: topUnwatchedMovies, // Top 20 unwatched movies
        };
      }));
  
      // Step 4: Add the "watched" category
      const watchedCategory = {
        categoryName: 'Watched',
        movies: lastWatchedMovies,
      };
  
      // Step 5: Combine results
      const result = [...unwatchedCategories, watchedCategory];
  
      return result;
  
    } catch (error) {
      console.error('Error fetching movies by category:', error);
      return [];
    }
  };
  

const getMovieById = async (id) => {
        try {
            // Query the database for a user with matching name and password
            const movie = await Movies.findById(id);
            return movie; // Will return the user or null if not found
        } catch (err) {
            return null;
        }
};
const createMovie = async (title,logline,image,categories) => {
  const movies = new Movies({ title : title});
  if (categories&&categories!=[]){
    for (const categoryName of categories) {
      const category = await Categories.findOne({ name: categoryName });
      if(!category)throw new Error("No Cat find!");
    }
  }

  if (logline) movies.logline = logline;
  if (image) movies.image = image;
  const res =await movies.save();
    for (const categoryName of categories) {
      const category = await Categories.findOne({ name: categoryName });
      if(category){
        category.movies.push(res._id);
        res.categories.push(category._id);
        await category.save();
      }
    }

  return await res.save();
};

const updateMovie = async (movieId, updateData) => {
  try {
    // Step 1: Get the movie by its _id
    const movie = await movies.findById(movieId);
    if (!movie) {
      throw new Error('Movie not found');
    }

      // First, remove the movie from all categories
      await Categories.updateMany(
        { movies: movieId },  // Find categories where the movie is listed
        { $pull: { movies: movieId } }  // Remove the movie from the category's `movies` array
      );


    // Step 3: Prepare the updated movie data
    const updatedMovie = { ...updateData };
    updateData._id=movieId;

    if(updatedMovie.categories){
    await Categories.updateMany(
      { _id: { $in: updatedMovie.categories } },  // Find categories with the given IDs
      { $addToSet: { movies: movieId } }  // Add movieId to the `movies` array if not already present
    );
  }

    // Step 4: Update the movie document with the new data
const updated = await Movies.findOneAndReplace(
  { _id: movieId }, // Filter to find the document
  updatedMovie,     // The new object to replace the document with
  { new: true }     // Return the replaced document after the operation
);


    if (!updated) {
      throw new Error('Failed to update movie');
    }

    return updated; // Return the updated movie object

  } catch (error) {
    console.error('Error updating movie:', error);
    throw error; // Rethrow the error to handle it where needed
  }
};
const deleteMovie = async (id) => {
  try {
      // Query the database for a user with matching name and password
      const movie = await Movies.findById(id);
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
      return movie; // Will return the user or null if not found
  } catch (err) {
      return null;
  }
};
const getRecommendMovie = async (userId,movieId) => {
  try {
      const res=await serverData.communicateWithServer("GET "+userId+" "+movieId);                
      return res; // Will return the user or null if not found
  } catch (err) {
      return null;
  }
};
const addMovieToUser = async (userId,movieId) => {
  try {
      const res=await serverData.communicateWithServer("PATCH "+userId+" "+movieId);                
      const result = await Users.updateOne(
        { _id: userId }, // Find the user by their _id
        { $push: { movies: { movie: movieId, whenWatched: new Date() } } } // Add movieId to the movies array
      );
      return res; // Will return the user or null if not found
  } catch (err) {
      return null;
  }
};
const getQueryMovie = async (query) => {
  try {
    // Search for movies where the query string is found in any of the specified fields
    const movies = await Movies.find({
      $or: [
        { title: { $regex: query, $options: 'i' } },  // Case-insensitive search on title
        { logline: { $regex: query, $options: 'i' } } // Case-insensitive search on logline
      ]
    });

    return movies; // Return the found movies
  } catch (error) {
    console.error('Error searching movies:', error);
    throw error; // Handle or propagate the error
  }
};         
module.exports = {getMoviesByCategory,getMovieById,createMovie,updateMovie
  ,getRecommendMovie,deleteMovie,addMovieToUser,getQueryMovie
}