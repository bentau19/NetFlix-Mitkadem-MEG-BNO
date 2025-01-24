import React, { useState, useEffect, useCallback } from 'react';
import './LoggedMainD.css';

const LoggedMain = () => {
  const [token, setToken] = useState('');
  const [data, setData] = useState('');

  // Memoize handleSubmit to avoid re-creation
  const handleSubmit = useCallback(async () => {
    try {
      const response = await fetch('http://localhost:5000/api/movies/', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          token: token, // Use the token state here
        },
      });

      const result = await response.json(); // Parse the JSON response
      setData(result); // Update the data state
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }, [token]); // Only re-create if token changes

  useEffect(() => {
    // Set token and call handleSubmit when component mounts
    setToken(
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjEsInVzZXJOYW1lIjoiYmVuIiwiYWRtaW4iOmZhbHNlLCJpYXQiOjE3Mzc3MTYyMzUsImV4cCI6MTczODMyMTAzNX0.qGWmV6F7TVl4ejEpIT3r-s7Apjvwro4e5M-pY6cMnWU'
    );
  }, []); // Only runs once when the component mounts

  useEffect(() => {
    if (token) {
      handleSubmit(); // Fetch data after token is set
    }
  }, [token, handleSubmit]); // Runs when token or handleSubmit changes

  return (
    <div className="mainContainer">
      <p>Data: {JSON.stringify(data)}</p> {/* Display the value of 'data' */}
    </div>
  );
};

export default LoggedMain;
