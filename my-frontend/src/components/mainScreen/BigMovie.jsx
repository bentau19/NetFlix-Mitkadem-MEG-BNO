import React from 'react';
import testImage from './test.png'; // Adjust path as needed

const BigMovie = () => {
  return (
                <div>
                    <div className="hero">
                <div className="overlay"></div>
            <img
                src={testImage} // Replace with your image URL
                alt="Hero"
                className="hero-image"
            /></div>  
    </div>
  );
};

export default BigMovie;
