import React from 'react';
import testImage from './test.png'; // Adjust path as needed

const CategoriesAndMoviesDisplay = ({ data }) => {
  return data.map((category,index) => (
    <div key={index} className="categorySection">
      <h2 className="categoryName">{category.categoryName}</h2>
      <div className="movieRow">
        {category.movies && category.movies.length > 0 ? (
          category.movies.map((movie) => (
            <div key={`${category._id}-${movie._id}`} className="movieCard">
              <img
                src={testImage} // Replace with movie image when available
                alt={movie.title}
                className="movieImage"
              />
              <div className="movieInfo">
                <h3>{movie.title}</h3>
              </div>
            </div>
          ))
        ) : (
          <p>No movies available in this category.</p>
        )}
      </div>
    </div>
  ));
};

export default CategoriesAndMoviesDisplay;
