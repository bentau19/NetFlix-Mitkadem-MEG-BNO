import React, { useEffect, useRef, useState } from "react";

const MovieTemplate = ({ movieId }) => {
  const videoRef = useRef(null);
  const [isVideoLoaded, setIsVideoLoaded] = useState(false);

  // Ensure the video element stays in the DOM and is initialized properly
  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.load();
      setIsVideoLoaded(true);
    }
  }, [movieId]); // Re-run this effect when movieId changes

  const handlePlay = () => {
    if (videoRef.current) {
      // Ensure the video is ready and try to play
      videoRef.current.play().catch((error) => {
        console.error("Error trying to play the video:", error);
      });
    }
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        backgroundColor: "#1c1c1c",
        borderRadius: "8px",
        padding: "20px",
        boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)",
        maxWidth: "800px",
        margin: "20px auto",
        color: "#fff",
      }}
    >
      {/* Video Player */}
      <div style={{ width: "100%", maxWidth: "600px" }}>
        <video
          ref={videoRef}
          controls
          autoPlay={isVideoLoaded}  // Only autoplay when ready
          style={{ width: "100%", height: "auto", borderRadius: "8px" }}
        >
          <source
            src={`http://localhost:5000/api/movies/${movieId}/play`}
            type="video/mp4"
          />
          Your browser does not support the video tag.
        </video>
      </div>
      {/* Button to manually trigger play */}
      {!isVideoLoaded && (
        <button onClick={handlePlay} style={{ marginTop: "10px" }}>
          Play Video
        </button>
      )}
    </div>
  );
};

export default MovieTemplate;
