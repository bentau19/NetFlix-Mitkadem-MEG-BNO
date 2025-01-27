import React, { useEffect, useRef } from "react";
import { setupVideoPlayer } from "../pages/videoPlayer"; // Import the video player logic

const MovieTemplate = () => {
  const videoRef = useRef(null);
  const movieId = 1; // Hardcoded movieId

  useEffect(() => {
    if (videoRef.current) {
      // Initialize the video player with the hardcoded movieId
      setupVideoPlayer(videoRef.current, movieId);
    }
  }, [movieId]);

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
      {/* Movie Title */}
      <h2 style={{ fontSize: "24px", margin: "0 0 20px 0", color: "#fff" }}>
        Movie ID: {movieId}
      </h2>

      {/* Video Player */}
      <div style={{ width: "100%", maxWidth: "600px" }}>
        <video
          ref={videoRef}
          controls
          style={{ width: "100%", height: "auto", borderRadius: "8px" }}
        >
          Your browser does not support the video tag.
        </video>
      </div>
    </div>
  );
};

export default MovieTemplate;