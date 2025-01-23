import React, { useState } from 'react';

const MovieForm = ({ onClose, initialValues = {} }) => {
  const { title, logline, image, categories } = initialValues || {};
  
  const [titleState, setTitle] = useState(title || '');
  const [loglineState, setLogline] = useState(logline || '');
  const [imageState, setImage] = useState(image || null);
  const [categoriesState, setCategories] = useState(categories ? categories.join(', ') : '');
  
  const isEditing = Boolean(initialValues);
  
const handleSubmit = async () => {
  try {

    const url = isEditing
      ? `http://localhost:5000/api/movies/${initialValues._id}`
      : 'http://localhost:5000/api/movies';
    const method = isEditing ? 'PUT' : 'POST';
    
    const formData = new FormData();
    formData.append('title', titleState);
    formData.append('logline', loglineState);
    
    // Parse categories into array
    const categoriesArray = categoriesState
      .split(',')
      .map(id => id.trim())
      .filter(id => id); // Remove empty strings
    formData.append('categories', JSON.stringify(categoriesArray));
    
    if (imageState) {
      formData.append('image', imageState);
    }

    const response = await fetch(url, {
      method,
      body: formData
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message);
    }

    alert(`Movie ${isEditing ? 'updated' : 'created'} successfully!`);
    onClose();
  } catch (error) {
    alert('Error: ' + error.message);
  }
};

  return (
    <div>
      <h2>{isEditing ? 'Edit Movie' : 'Create Movie'}</h2>
      <input
        value={titleState}
        onChange={(e) => setTitle(e.target.value)}
        placeholder="Movie Title"
        required
      />
      <input
        value={loglineState}
        onChange={(e) => setLogline(e.target.value)}
        placeholder="Movie Logline"
      />
      <input
        type="file"
        accept="image/*"
        onChange={(e) => setImage(e.target.files[0])}
        required={!isEditing}
      />
      <input
        value={categoriesState}
        onChange={(e) => setCategories(e.target.value)}
        placeholder="Categories (comma-separated)"
      />
      <button onClick={handleSubmit} >
        {isEditing ? 'Update Movie' : 'Create Movie'}
      </button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default MovieForm;