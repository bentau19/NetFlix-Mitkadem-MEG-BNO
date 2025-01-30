import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from "react-router-dom";

import { get } from '../../components/httpUtils';
import GuestScreenTemp from '../../components/mainScreen/GuestScreenTemp';
const GuestMain = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const [data, setData] = useState([]);
  const handleSubmit = useCallback(async () => {
    setIsLoading(true);
    setError(null);
  
    try {
      const result = await get('/movies/search/'); // Remove `{}` unless needed
      if (!result || result.length === 0) {
        setError('No movies found for your search.');
        setData([]); 
      } else {
        setData(result);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
      setError('An error occurred while fetching data.');
    } finally {
      setIsLoading(false);
    }
  }, [setData, setIsLoading, setError]); // Include dependencies
  useEffect(() => {
    handleSubmit();
  }, [handleSubmit]);


  return (

    <div>
        
      <div>
        <button onClick={() => navigate("/signup")}>Sign Up</button>
        <button onClick={() => navigate("/signin")}>Log In</button>
      </div>
      <h1>Welcome to Our Website!</h1>
      <p>
        Explore our platform and discover amazing features. Join us today to get
        started!
      </p>

      <div>
        <h2>Features</h2>
        <ul>
          <li>Easy-to-use interface</li>
          <li>Secure and reliable</li>
          <li>Accessible from anywhere</li>
        </ul>
      </div>
      <GuestScreenTemp
        isLoading={isLoading}
        error={error}
        data={data}
      />
    </div>
  );
};

export default GuestMain;