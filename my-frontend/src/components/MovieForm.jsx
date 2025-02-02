import React, { useState } from 'react';
import { hexToBase64, convertFileToHex} from '../utils/imageConverter';
const MovieForm = ({ onClose, initialValues = {} , onSuccess }) => {
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
      
      let imageBase64 = null;
      if (imageState && imageState instanceof File) {
        // Only convert if it's a file, not a string
        imageBase64 = await convertFileToHex(imageState);
      } else if (typeof imageState === 'string') {
        // If it's already a base64 string, use it directly
        imageBase64 = imageState;
      }
      
  
      const response = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          'token': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjUsInVzZXJOYW1lIjoiZGl2YSIsImFkbWluIjp0cnVlLCJpYXQiOjE3MzgwMDg0NDgsImV4cCI6MTczODYxMzI0OH0.XItd2S3Ot0ET9uD_pureCQO8b3LIkT6yq7HR9q8mDJw', // Replace with your actual token
        },
        body: JSON.stringify({
          title: titleState,
          logline: loglineState,
          image: imageBase64, // Use Base64 encoded image here
          categories: categoriesState
            .split(',')
            .map(id => id.trim())
            .filter(id => id),
        }),
      });
  
      // Log raw response
      const responseText = await response.text();
      console.log('Full Response:', responseText);
  
      // Detailed error handling
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}, response: ${responseText}`);
      } else {
          alert(`Movie ${isEditing ? 'updated' : 'created'} successfully!`);
          onClose();
          onSuccess(); // Trigger the refresh        
      }
    } catch (error) {
      console.error('Submission Error:', error);
      alert('Error: ' + error.message);
    }
  };
  


  return (
    <div>
      <h2>{isEditing ? 'Edit Movie' : 'Create Movie'}</h2>
      <div class="form-group">
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
      {typeof imageState === 'string' ? (
        <img
        src={hexToBase64(imageState)}
          alt="Uploaded Movie"
          style={{ maxWidth: '300px', maxHeight: '200px' }}
        />
      ) : imageState ? (
        <img
          src={URL.createObjectURL(imageState)}
          alt="Uploaded Movie"
          style={{ maxWidth: '300px', maxHeight: '200px' }}
        />
      ) : null}
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
      </div>
      <button onClick={handleSubmit} >
        {isEditing ? 'Update Movie' : 'Create Movie'}
      </button>
      <button onClick={onClose}>Cancel</button>

    </div>
  );
};

export default MovieForm;