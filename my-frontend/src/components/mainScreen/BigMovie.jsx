import React, { useRef, useEffect } from 'react';
import testImage from './test.png'; // Adjust path as needed

const BigMovie = () => {
  const videoRef = useRef(null);
  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.play();
    }
  }, []);
  return (
    <div>
      <div className="hero">
      <div className="overlay"></div>
      <video
          ref={videoRef}
          width="100%"
          height="auto"
          controls
          autoPlay
          muted
          loop
        >
          <source src="http://localhost:5000/api/movies/1/play" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
      </div>  
    </div>
  );
};

export default BigMovie;
