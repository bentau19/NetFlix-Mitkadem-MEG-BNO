import React, { useState } from 'react';
import { hexToBase64} from '../utils/imageConverter';
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
       const response = await fetch(url, {
         method,
         headers: {
           'Content-Type': 'application/json',
           'token': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjQsInVzZXJOYW1lIjoiaGgiLCJhZG1pbiI6dHJ1ZSwiaWF0IjoxNzM3NjcxNzQwLCJleHAiOjE3MzgyNzY1NDB9.lrAoaumgyCMFm472E0LoXpxMuImnTCmJsEqqVSR7Njk',
         },
         body: JSON.stringify({
           title: titleState,
           logline: loglineState,
         //  image: imageState ? await convertFileToHex(optimizeImage(imageState)) : null,
         image:image,
           categories: categoriesState
             .split(',')
             .map(id => id.trim())
             .filter(id => id)
         })
       });
 
       // Log raw response
       const responseText = await response.text();
       console.log('Full Response:', responseText);
 
       // More detailed error handling
       if (!response.ok) {
         throw new Error(`HTTP error! status: ${response.status}, response: ${responseText}`);
       }
       else{
        alert(`movie ${isEditing ? 'updated' : 'created'} successfully!`);
        onClose();
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