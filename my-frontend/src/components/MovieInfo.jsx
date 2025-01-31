import React, { useState, useEffect } from 'react';
import Popup from './Popup';
import { hexToBase64 } from '../utils/imageConverter.js';
import testImage from './mainScreen/test.png'
import { useNavigate } from 'react-router-dom';
import { get } from '../components/httpUtils.jsx';
const MovieInfoPage = ({ movie }) => {
  const navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const validateToken = async () => {
      try {
        const result = await get('/tokens');
        setIsAuthenticated(!!result._id);
      } catch (error) {
        console.error('Token validation failed:', error);
        setIsAuthenticated(false);
      } finally {
        setIsLoading(false);
      }
    };

    validateToken();
  }, []);

  const handleButtonClick = () => {
    if (isAuthenticated) {
      navigate(`/movie/${movie._id}`);
    } else {
      navigate('/signin');
    }
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }
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
