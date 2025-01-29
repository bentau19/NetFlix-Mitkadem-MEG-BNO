import React, { useRef, useEffect, useState } from 'react';
import testImage from './test.png'; // Adjust path as needed

const BigMovie = () => {
  const videoRef = useRef(null);
  const [isReadyToPlay, setIsReadyToPlay] = useState(false);

  // This function will be triggered when the video is ready to play
  const handleCanPlay = () => {
    setIsReadyToPlay(true);
  };

  // Triggering play when the video is ready
  useEffect(() => {
    if (isReadyToPlay && videoRef.current) {
      videoRef.current.play().catch((error) => {
        console.error('Error playing the video:', error);
      });
    }
  }, [isReadyToPlay]);

  return (
    <div>
      <div className="hero">
        <div className="overlay"></div>
        <video
          ref={videoRef}
          width="100%"
          height="auto"
          controls
          muted
          loop
          onCanPlay={handleCanPlay} // Listen for when the video is ready to play
        >
          <source src="http://localhost:5000/api/movies/1/play" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
      </div>
    </div>
  );
};

export default BigMovie;
