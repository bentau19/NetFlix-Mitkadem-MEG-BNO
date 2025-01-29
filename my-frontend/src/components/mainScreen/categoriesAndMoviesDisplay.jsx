import React, { useRef } from 'react';
import testImage from './test.png'; // Adjust path as needed
import { hexToBase64 } from '../../utils/imageConverter.js';
const CategoriesAndMoviesDisplay = ({ data }) => {
  const categoryRefs = useRef([]);

  const scrollLeft = (index) => {
    if (categoryRefs.current[index]) {
      categoryRefs.current[index].scrollBy({ left: -200, behavior: 'smooth' });
    }
  };

  const scrollRight = (index) => {
    if (categoryRefs.current[index]) {
      categoryRefs.current[index].scrollBy({ left: 200, behavior: 'smooth' });
    }
  };

  return data.map((category, index) => (
    <div key={index} className="categorySection">
      <h2 className="categoryName">{category.name}</h2>
      <div className="movieRowContainer">
        <div
          ref={(el) => (categoryRefs.current[index] = el)}
          className="movieRow"
        >
          {category.movies && category.movies.length > 0 ? (
            category.movies.map((movie) => (
              <div key={`${category._id}-${movie._id}`} className="movieCard">
                <img
                  src={movie.image?hexToBase64(movie.image):testImage} // Replace with movie image when available
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
        <button
          onClick={() => scrollLeft(index)}
          className="scrollButton left"
        >
          &lt;
        </button>
        <button
          onClick={() => scrollRight(index)}
          className="scrollButton right"
        >
          &gt;
        </button>
      </div>
    </div>
  ));
};

export default CategoriesAndMoviesDisplay;