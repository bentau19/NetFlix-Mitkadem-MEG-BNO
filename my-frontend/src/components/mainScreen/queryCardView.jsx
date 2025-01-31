import React, { useState } from 'react';
import testImage from './test.png'; // Adjust the path if needed
import { hexToBase64 } from '../../utils/imageConverter.js';
import Popup from '../Popup.jsx';
import MovieInfoPage from '../MovieInfo.jsx';
import { get } from '../httpUtils.jsx';
import MoviesList from './MoviesList';

const QueryCardView = ({ isLoading, error, data, searchQuery }) => {
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [recommendedMovies, setRecommendedMovies] = useState([]);

  const handleMovieClick = async (movie) => {
    setSelectedMovie(movie);
    setIsPopupOpen(true);

    try {
      const recommendations = await get(`/movies/${movie._id}/recommend`);
      setRecommendedMovies(recommendations);
    } catch (error) {
      console.error('Error fetching recommended movies:', error);
    }
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message || 'Something went wrong'}</div>;
  }

  if (!data || data.length === 0) {
    return <div>No results found for "{searchQuery}".</div>;
  }

  return (
    <>
      <div className="moviesGrid">
        {data.map((movie, index) => (
          <div key={index} className="movieCard" onClick={() => handleMovieClick(movie)}>
            {/* Polaroid Frame Component */}
            <img
              src={movie.image ? hexToBase64(movie.image) : testImage}
              alt={movie.title || 'Movie Title'}
              className="movieImage"
            />
            <div className="caption">{movie.title || 'Untitled Movie'}</div>
          </div>
        ))}
      </div>

      {/* Popup for Movie Info */}
      <Popup isOpen={isPopupOpen} onClose={closePopup}>
        {selectedMovie && (
          <>
            <MovieInfoPage movie={selectedMovie} />
            <h3>Recommended Movies:</h3>
            <div className="recommendedMovies">
              <MoviesList movies={recommendedMovies} />
            </div>
          </>
        )}
      </Popup>
    </>
  );
};

export default QueryCardView;
