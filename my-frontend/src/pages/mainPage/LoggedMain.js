import React, { useState, useEffect, useCallback } from 'react';
import './LoggedMainD.css';

const LoggedMain = () => {
  const [token, setToken] = useState('');
  const [data, setData] = useState([]);

  const handleSubmit = useCallback(async () => {
    try {
      const response = await fetch('http://localhost:5000/api/movies/', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          token: token,
        },
      });

      const result = await response.json();
      // Filter out categories with no movies
      const filteredCategories = result.filter((category) => category.movies.length > 0);
      setData(filteredCategories);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }, [token]);

  useEffect(() => {
    setToken(
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjEsInVzZXJOYW1lIjoiYmVuIiwiYWRtaW4iOmZhbHNlLCJpYXQiOjE3Mzc3MTYyMzUsImV4cCI6MTczODMyMTAzNX0.qGWmV6F7TVl4ejEpIT3r-s7Apjvwro4e5M-pY6cMnWU'
    );
  }, []);

  useEffect(() => {
    if (token) {
      handleSubmit();
    }
  }, [token, handleSubmit]);

  return (
    <div className="mainContainer">
      <h1>Movies for You</h1>
      {data.map((category, index) => (
        <div key={index} className="categorySection">
          <h2 className="categoryName">{category.categoryName}</h2>
          <div className="movieRow">
            {category.movies.map((movie) => (
              <div key={movie._id} className="movieCard">
                <img
                  src={movie.image || 'https://via.placeholder.com/300x450'}
                  alt={movie.title}
                  className="movieImage"
                />
                <div className="movieInfo">
                  <h3>{movie.title}</h3>
                </div>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default LoggedMain;
