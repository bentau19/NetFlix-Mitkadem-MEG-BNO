import React, { useState, useEffect, useCallback, useContext  } from 'react';
import { useNavigate } from "react-router-dom";
import "./GuestMain.css"
import { ThemeContext } from "../../context/ThemeContext";

import { get } from '../../components/httpUtils';
import GuestScreenTemp from '../../components/mainScreen/GuestScreenTemp';
const GuestMain = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
     const { theme, toggleTheme } = useContext(ThemeContext);
  
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

 // Choose the logo based on the current theme
 const logoSrc = theme === 'dark'
 ? '/image/meg_purple.png' 
 : '/image/meg_red.png'; // Light logo

  return (

        
        <div >
           <div class="hero-container">
  <div class="hero-content">
  <h1 className="hero-title">
            Welcome to 
            <div 
              id="logo" 
              style={{ marginLeft: "-30px", width: "170px" }}
            >
              <img 
                src={logoSrc} 
                alt="Meg Logo" 
                style={{ width: "70%", height: "auto" }}
              />
            </div>
          </h1>
    <p class="hero-subtitle">
      Explore our platform and discover amazing features. Join us today to get started!
    </p>

    <div class="hero-buttons">
      <button onClick={() => navigate("/signup")}>Sign Up</button>
      <button onClick={() => navigate("/signin")}>Log In</button>
    </div>
  </div>
  </div>

  <div class="feature-section">
  <h2>Features</h2>
  <div class="features-grid">
  <div class="feature-item">
  <p>Easy-to-use interface</p>
</div>
<div class="feature-item">
  <p>Secure and reliable</p>
</div>
<div class="feature-item">
  <p>Accessible from anywhere</p>
</div>

  </div>
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