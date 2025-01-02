const Movies = require('../models/movies');
const Categories = require('../models/categories');
const Users = require('../models/user');
const getMoviesByCategory = async (userId) => {
    try {
      // Step 1: Get the user's watched movies
      const user = await User.findById(userId);
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
          (movieId) => !watchedMovies.includes(movieId.toString())
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
const createMovie = async (title,logline,image) => {
  const movies = new Movies({ title : title});
  if (logline) movies.logline = logline;
  if (image) movies.image = image;
  return await movies.save();
};

const updateMovie = async (movieId, updateData) => {
  try {
    // Step 1: Get the movie by its _id
    const movie = await Movie.findById(movieId);
    if (!movie) {
      throw new Error('Movie not found');
    }

      // First, remove the movie from all categories
      await Category.updateMany(
        { movies: movieId },  // Find categories where the movie is listed
        { $pull: { movies: movieId } }  // Remove the movie from the category's `movies` array
      );


    // Step 3: Prepare the updated movie data
    const updatedMovie = { ...updateData };

    // Remove fields that are undefined or null
    for (const key in updatedMovie) {
      if (updatedMovie[key] === undefined || updatedMovie[key] === null) {
        delete updatedMovie[key]; // Remove the key if its value is undefined or null
      }
    }
    await Category.updateMany(
      { _id: { $in: categoryIds } },  // Find categories with the given IDs
      { $addToSet: { movies: movieId } }  // Add movieId to the `movies` array if not already present
    );
    // Step 4: Update the movie document with the new data
    const updated = await Movie.findOneAndUpdate(
      { _id: movieId },
      { $set: updatedMovie },
      { new: true } // Return the updated movie after the operation
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
        { 'movies': id }, // Find users who watched the movie
        { $pull: { movies: { movie: id } } } // Remove the movie from the movies array
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
      const result = await User.updateOne(
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
   // Filter for string fields only
   const stringFields = movieFields.filter(field => {
    const fieldType = Movies.paths[field].instance;
    return fieldType === 'String'; // Only search in string fields
  });

  // Dynamically build the query
  const searchQuery = {};
  stringFields.forEach(field => {
    searchQuery[field] = { $regex: query, $options: 'i' }; // Case-insensitive search in each string field
  });

  // Search movies with the constructed query
  const movies = await Movies.find(searchQuery);

  // Return the found movies
  return res.json(movies);
}
 catch (err) {
      return null;
  }
};         
module.exports = {getMoviesByCategory,getMovieById,createMovie,updateMovie
  ,getRecommendMovie,deleteMovie,addMovieToUser,getQueryMovie
}