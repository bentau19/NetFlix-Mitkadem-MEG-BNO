import React, { useRef, useEffect, useState } from 'react';
import { get } from '../httpUtils.jsx';
const BigMovie = () => {
  const videoRef = useRef(null);
  const [isReadyToPlay, setIsReadyToPlay] = useState(false);
  const [movieId, setMovieId] = useState(null);

  // Function to fetch all movies and pick one at random
  const fetchRandomMovie = async () => {
    try {
      const response = await fetch('http://localhost:5000/api/movies/search/');
      const movies = await response.json();

      if (movies.result.length > 0) {
        const randomMovie = movies.result[Math.floor(Math.random() * movies.result.length)];
        setMovieId(randomMovie._id); // Assuming the movie object has an 'id' field
      }
    } catch (error) {
      console.error('Error fetching movies:', error);
    }
  };

  useEffect(() => {
    fetchRandomMovie();
  }, []);

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
        {movieId ? (
          <video
            ref={videoRef}
            width="100%"
            height="auto"
            controls
            muted
            loop
            onCanPlay={() => setIsReadyToPlay(true)}
          >
            <source src={`http://localhost:5000/api/movies/${movieId}/play`} type="video/mp4" />
            Your browser does not support the video tag.
          </video>
        ) : (
          <p>Loading movie...</p>
        )}
      </div>
    </div>
  );
};

export default BigMovie;
