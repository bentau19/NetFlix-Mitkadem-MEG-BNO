import React from 'react';
import testImage from './test.png'; // Adjust the path if needed
import { hexToBase64 } from '../../utils/imageConverter.js';import '../PolaroidFrame/PolaroidFrame.css';

const QueryCardView = ({ isLoading, error, data, searchQuery }) => {
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
    <div className="moviesGrid">
      {data.map((movie, index) => (
        <div key={index} className="movieCard">
          {/* Polaroid Frame Component */}
          <div className="polaroid-frame">
            <img
             src={movie.image?hexToBase64(movie.image):testImage}
              alt={movie.title || 'Movie Title'}
              className="movieImage"
            />
            <div className="caption">
              {movie.title || 'Untitled Movie'}
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default QueryCardView;
