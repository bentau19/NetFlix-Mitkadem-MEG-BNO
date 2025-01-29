import React, { useRef, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { post } from '../components/httpUtils'; 
import { useNavigate } from 'react-router-dom';
import MovieTemplate from '../components/MovieTemplate';
const VideoPlayer = () => {
  const { videoId } = useParams();
  const videoRef = useRef(null);
  const navigate = useNavigate(); 
  const handleclick = () =>{
    navigate('/');
  }

  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.pause();
      videoRef.current.removeAttribute('src');
      videoRef.current.load();
    }
  }, [videoId]);

  const sendWatched = async () => {
    try {
      const result = await post(`/movies/${videoId}/recommend`, {});
      console.log('Movie watched status updated');
    } catch (error) {
      console.error('Error marking movie as watched:', error);
    }
  };

  return (
    <div>
      <button onClick={handleclick} className="home">
        Back to home
      </button>
      <MovieTemplate movieId={videoId} />
    </div>
  );
};

export default VideoPlayer;
