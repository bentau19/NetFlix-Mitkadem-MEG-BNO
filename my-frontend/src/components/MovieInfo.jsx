import React, { useState } from 'react';
import Popup from './Popup';
import { hexToBase64 } from '../utils/imageConverter.js';
import testImage from './mainScreen/test.png'
import { useNavigate } from 'react-router-dom';
const MovieInfoPage = ({ movie }) => {
  const navigate = useNavigate();
  const handleButtonClick = () => {
    navigate(`/movie/${movie._id}`);
  };
  return (
    <div className="MovieInfo">
      <img
        src={movie.image ? hexToBase64(movie.image) : testImage}
        alt={movie.title}
        className="MovieInfoImage"
      />
      <h2 className="MovieInfoTitle">{movie.title}</h2>
      <h2 className="MovieInfoLog">{movie.logline }</h2>
      <button onClick={handleButtonClick} className="StreamMovie">
        Play
      </button>
    </div>
  );
};


export default MovieInfoPage;
