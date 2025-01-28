import React, { useState, useEffect, useCallback } from 'react';
import './LoggedMainD.css';
import Navbar from "../NavBar/Navbar";
import {searchMovie, getUserMovies } from '../../components/httpUtils.jsx';
import { useNavigate } from 'react-router-dom';
import MainScreenTemp from "../../components/mainScreen/MainScreenTemp.jsx";
const LoggedMain = () => {
  const [data, setData] = useState([]); // Ensure that data is an array by default
  const [searchQuery, setSearchQuery] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const handleSubmit = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    try {
      let fetchResult;
      if (searchQuery) {
        fetchResult = await searchMovie(searchQuery);
      } else {
        fetchResult = await getUserMovies();
      }
  
      if (!fetchResult || fetchResult.length === 0) {
        setError('No movies found for your search.');
        setData([]); // Set data to an empty array when no results
      } else {
        if (searchQuery) {
          setData(fetchResult); // Use the result directly for search
        } else {
          // Filter out categories with no movies
          const filteredCategories = fetchResult.filter(
            (category) => category.movies && category.movies.length > 0
          );
          setData(filteredCategories);
        }
      }
    } catch (error) {
      console.error('Error fetching data:', error);
      setError('An error occurred while fetching data.');
    } finally {
      setIsLoading(false);
    }
  }, [searchQuery]); // Only depends on `searchQuery`
  
  const handleSearchChange = (query) => {
    setSearchQuery(query);
  };
  
  useEffect(() => {
    if (sessionStorage.getItem('token') === null) {
      navigate('/signin');
    } else {
      handleSubmit(); // Trigger fetch when component mounts
    }
  }, [navigate, handleSubmit]); // Properly include dependencies
  
  return (
    <div className="maindiv">
      <Navbar onSearchChange={handleSearchChange}  />
      <MainScreenTemp isLoading={isLoading} error={error} data={data} searchQuery={searchQuery} />
    </div>
  );
};

export default LoggedMain;
