import React, { useRef, useState } from 'react';
import testImage from './test.png'; // Adjust path as needed
import { hexToBase64 } from '../../utils/imageConverter.js';
import MovieInfoPage from '../MovieInfo.jsx';
import Popup from '../Popup.jsx';
import { get } from '../httpUtils.jsx'

const CategoriesAndMoviesDisplay = ({ data = [] }) => {
  const categoryRefs = useRef([]);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [recommendedMovies, setRecommendedMovies] = useState([]);
  const handleMovieClick = async (movie) => {
    setSelectedMovie(movie);
    setIsPopupOpen(true);

    // Fetch recommended movies based on the selected movie
    try {
      const recommendations = await get('/movies/'+ movie._id + '/recommend',);
      setRecommendedMovies(recommendations);
    } catch (error) {
      console.error("Error fetching recommended movies:", error);
    }
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

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

  return (
    <>
      {data?.map((category, index) => (
        <div key={index} className="categorySection">
          <h2 className="categoryName">{category.name}</h2>
          <div className="movieRowContainer">
            <div
              ref={(el) => (categoryRefs.current[index] = el)}
              className="movieRow"
            >
              {Array.isArray(category.movies) && category.movies.length > 0 ? (
                category.movies.map((movie) => (
                  <div
                    key={`${category._id}-${movie._id}`}
                    className="movieCard"
                    onClick={() => handleMovieClick(movie)}
                  >
                    <img
                      src={movie.image ? hexToBase64(movie.image) : testImage}
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
            <button onClick={() => scrollLeft(index)} className="scrollButton left">
              &lt;
            </button>
            <button onClick={() => scrollRight(index)} className="scrollButton right">
              &gt;
            </button>
          </div>
        </div>
      ))}

      {/* Popup for Movie Info */}
      <Popup isOpen={isPopupOpen} onClose={closePopup}>
        {(
          <>
            <MovieInfoPage movie={selectedMovie} />
            <h3>Recommended Movies:</h3>
            {/* <div className="recommendedMovies">
              {recommendedMovies.length > 0 ? (
                recommendedMovies.map((movie) => (
                  <div key={movie._id} className="movieCard">
                    <img
                      src={movie.image ? hexToBase64(movie.image) : testImage}
                      alt={movie.title}
                      className="movieImage"
                    />
                    <div className="movieInfo">
                      <h3>{movie.title}</h3>
                    </div>
                  </div>
                ))
              ) : (
                <p>No recommendations available.</p>
              )}
            </div> */}
          </>
        )}
      </Popup>
    </>
  );
};

export default CategoriesAndMoviesDisplay;
