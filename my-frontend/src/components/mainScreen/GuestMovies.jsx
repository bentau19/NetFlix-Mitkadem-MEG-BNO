import React, { useRef, useState } from 'react';
import testImage from './test.png'; // Adjust path as needed
import { hexToBase64 } from '../../utils/imageConverter.js';
import MovieInfoPage from '../MovieInfo.jsx';
import Popup from '../Popup.jsx';
import { get } from '../httpUtils.jsx';

const GuestMovies = ({ movies = [] }) => {
  const rowRef = useRef(null);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const handleMovieClick = async (movie) => {
    setSelectedMovie(movie);
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  const scrollLeft = () => {
    if (rowRef.current) {
      rowRef.current.scrollBy({ left: -200, behavior: 'smooth' });
    }
  };

  const scrollRight = () => {
    if (rowRef.current) {
      rowRef.current.scrollBy({ left: 200, behavior: 'smooth' });
    }
  };

  return (
    <div className="movieRowContainer">
      <h2 className="categoryName">Movies</h2>
      <div ref={rowRef} className="movieRow">
        {Array.isArray(movies) && movies.length > 0 ? (
          movies.map((movie) => (
            <div
              key={movie._id}
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
          <p>No movies available.</p>
        )}
      </div>
      <button onClick={scrollLeft} className="scrollButton left">&lt;</button>
      <button onClick={scrollRight} className="scrollButton right">&gt;</button>

      {/* Popup for Movie Info */}
      <Popup isOpen={isPopupOpen} onClose={closePopup}>
        {selectedMovie && (
          <>
            <MovieInfoPage movie={selectedMovie} />
          </>
        )}
      </Popup>
    </div>
  );
};

export default GuestMovies;
