import React, { useState, useEffect } from "react";
import "./styles.css";

const App = () => {
  const [selectedType, setSelectedType] = useState("movie");
  const [popupVisible, setPopupVisible] = useState(false);
  const [categoryName, setCategoryName] = useState("");
  const [isPromoted, setIsPromoted] = useState(false);
  const [movieTitle, setMovieTitle] = useState("");
  const [movieLogline, setMovieLogline] = useState("");
  const [movieImage, setMovieImage] = useState("");
  const [movieCategories, setMovieCategories] = useState("");
  const [categories, setCategories] = useState([]);
  const [movies, setMovies] = useState([]);

  // Fetch categories
  const loadCategories = async () => {
    try {
      const response = await fetch("/api/categories");
      const data = await response.json();
      if (!Array.isArray(data.message)) throw new Error("Invalid response format");
      setCategories(data.message);
    } catch (error) {
      console.error("Error loading categories:", error);
      alert("Failed to load categories");
    }
  };

  // Fetch movies
  const loadMovies = async () => {
    try {
      const response = await fetch("/api/movies/search/");
      const data = await response.json();
      if (!Array.isArray(data.message)) throw new Error("Invalid response format");
      setMovies(data.message);
    } catch (error) {
      console.error("Error loading movies:", error);
      alert("Failed to load movies");
    }
  };

  const handleCreateCategory = async () => {
    if (!categoryName) {
      alert("Please enter a category name");
      return;
    }
    try {
      const response = await fetch("/api/categories", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name: categoryName, promoted: isPromoted }),
      });
      const data = await response.json();
      if (!response.ok) throw new Error(data.message || "Failed to create category");
      alert("Category created successfully!");
      setPopupVisible(false);
      loadCategories();
    } catch (error) {
      console.error("Error creating category:", error);
      alert("Failed to create category: " + error.message);
    }
  };

  const handleCreateMovie = async () => {
    if (!movieTitle) {
      alert("Please enter a movie title");
      return;
    }
    try {
      const response = await fetch("/api/movies", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          title: movieTitle,
          logline: movieLogline,
          image: movieImage,
          categories: movieCategories.split(",").map((id) => id.trim()).filter(Boolean),
        }),
      });
      const data = await response.json();
      if (!response.ok) throw new Error(data.message || "Failed to create movie");
      alert("Movie created successfully!");
      setPopupVisible(false);
      loadMovies();
    } catch (error) {
      console.error("Error creating movie:", error);
      alert("Failed to create movie: " + error.message);
    }
  };

  const handleListShow = () => {
    if (selectedType === "category") loadCategories();
    else loadMovies();
  };

  useEffect(() => {
    handleListShow();
  }, [selectedType]);

  return (
    <div className="container">
      <h1>Movies and Categories</h1>
      <div className="input-group">
        <select value={selectedType} onChange={(e) => setSelectedType(e.target.value)}>
          <option value="movie">Movie</option>
          <option value="category">Category</option>
        </select>
        <input type="text" placeholder="Search..." />
        <button onClick={handleListShow}>Search</button>
      </div>
      <button onClick={() => setPopupVisible(true)}>Add</button>
      {selectedType === "category" && (
        <ul>
          {categories.map((category) => (
            <li key={category._id}>
              <strong>{category.name} (ID: {category._id})</strong>
            </li>
          ))}
        </ul>
      )}
      {selectedType === "movie" && (
        <ul>
          {movies.map((movie) => (
            <li key={movie._id}>
              <strong>{movie.title} (ID: {movie._id})</strong>
            </li>
          ))}
        </ul>
      )}
      {popupVisible && (
        <div className="popup">
          <div className="popup-content">
            <span className="close" onClick={() => setPopupVisible(false)}>
              &times;
            </span>
            {selectedType === "category" ? (
              <div>
                <h2>Create Category</h2>
                <input
                  type="text"
                  value={categoryName}
                  onChange={(e) => setCategoryName(e.target.value)}
                  placeholder="Category Name"
                />
                <label>
                  <input
                    type="checkbox"
                    checked={isPromoted}
                    onChange={(e) => setIsPromoted(e.target.checked)}
                  />
                  Promoted
                </label>
                <button onClick={handleCreateCategory}>Create Category</button>
              </div>
            ) : (
              <div>
                <h2>Create Movie</h2>
                <input
                  type="text"
                  value={movieTitle}
                  onChange={(e) => setMovieTitle(e.target.value)}
                  placeholder="Movie Title"
                />
                <input
                  type="text"
                  value={movieLogline}
                  onChange={(e) => setMovieLogline(e.target.value)}
                  placeholder="Movie Logline"
                />
                <input
                  type="text"
                  value={movieImage}
                  onChange={(e) => setMovieImage(e.target.value)}
                  placeholder="Image URL"
                />
                <input
                  type="text"
                  value={movieCategories}
                  onChange={(e) => setMovieCategories(e.target.value)}
                  placeholder="Categories (comma-separated)"
                />
                <button onClick={handleCreateMovie}>Create Movie</button>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default App;
