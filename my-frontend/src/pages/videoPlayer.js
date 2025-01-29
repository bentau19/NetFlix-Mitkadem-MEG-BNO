import React, { useRef, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { post } from '../components/httpUtils'; 
import { useNavigate } from 'react-router-dom';
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
      <video
        ref={videoRef}
        width="320"
        height="240"
        controls
        autoPlay
        onPlay={sendWatched}
      >
        <source
          src={`http://localhost:5000/api/movies/${videoId}/play`}
          type="video/mp4"
        />
        Your browser does not support the video tag.
      </video>
    </div>
  );
};

export default VideoPlayer;
