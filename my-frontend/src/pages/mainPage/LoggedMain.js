import React, { useState, useEffect, useCallback } from 'react';
import './LoggedMainD.css';
import Navbar from "../NavBar/Navbar";

const LoggedMain = () => {
  const [token, setToken] = useState('');
  const [data, setData] = useState([]); // Ensure that data is an array by default
  const [searchQuery, setSearchQuery] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = useCallback(async () => {
    setIsLoading(true);
    setError(null);

    const url = searchQuery
      ? `http://localhost:5000/api/movies/search/${searchQuery}`
      : 'http://localhost:5000/api/movies/';

    try {
      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          token: token,
        },
      });

      const result = await response.json();

      if (!result || result.length === 0) {
        setError('No movies found for your search.');
        setData([]); // Set data to an empty array when no results
      } else {
        // For the search query, use the result directly
        if (searchQuery) {
          setData(result);
        } else {
          // For the base case, filter out categories with no movies
          const filteredCategories = result.filter((category) => category.movies && category.movies.length > 0);
          setData(filteredCategories);
        }
      }
    } catch (error) {
      console.error('Error fetching data:', error);
      setError('An error occurred while fetching data.');
    } finally {
      setIsLoading(false);
    }
  }, [token, searchQuery]);

  const handleSearchChange = (query) => {
    setSearchQuery(query);
  };

  useEffect(() => {
    setToken(
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjEsInVzZXJOYW1lIjoiYmVuIiwiYWRtaW4iOmZhbHNlLCJpYXQiOjE3Mzc3MTYyMzUsImV4cCI6MTczODMyMTAzNX0.qGWmV6F7TVl4ejEpIT3r-s7Apjvwro4e5M-pY6cMnWU'
    );
  }, []);

  useEffect(() => {
    if (token) {
      handleSubmit(); // Make the API request when the token or searchQuery changes
    }
  }, [token, searchQuery,handleSubmit]); // Depend on both token and searchQuery to trigger the request

  return (
    <div className="mainContainer">
      <Navbar onSearchChange={handleSearchChange} />
      <h1>Movies for You</h1>

      {/* Display loading state */}
      {isLoading && <p>Loading...</p>}

      {/* Display error message */}
      {error && <p>{error}</p>}

      {/* Render movie categories or list of movies based on searchQuery */}
      {!isLoading && !error && Array.isArray(data) && data.length > 0 ? (
        searchQuery === '' ? (
          // Render categories when no search query
          data.map((category) => (
            <div key={category._id} className="categorySection">
              <h2 className="categoryName">{category.categoryName}</h2>
              <div className="movieRow">
                {category.movies && category.movies.length > 0 ? (
                  category.movies.map((movie) => (
                    <div key={movie._id} className="movieCard">
                      <img
                        // src={movie.image || 'https://via.placeholder.com/300x450'}
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
          ))
        ) : (
          // Render a flat list of movies when there is a search query
          data.map((movie) => (
            <div key={movie._id} className="movieCard">
              <img
                // src={movie.image || 'https://via.placeholder.com/300x450'}
                alt={movie.title}
                className="movieImage"
              />
              <div className="movieInfo">
                <h3>{movie.title}</h3>
              </div>
            </div>
          ))
        )
      ) : (
        <p>No categories or movies to display.</p> // If data is empty or not an array
      )}
    </div>
  );
};

export default LoggedMain;
