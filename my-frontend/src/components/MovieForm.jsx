import React, { useState } from 'react';
import { hexToBase64, convertFileToHex } from '../utils/imageConverter';
import { post, put } from '../components/httpUtils';

const MovieForm = ({ onClose, initialValues = {}, onSuccess }) => {
  const { title, logline, image, categories, _id } = initialValues || {};
  
  const [titleState, setTitle] = useState(title || '');
  const [loglineState, setLogline] = useState(logline || '');
  const [imageState, setImage] = useState(image || null);
  const [categoriesState, setCategories] = useState(categories ? categories.join(', ') : '');

  const isEditing = Boolean(_id);

  const handleSubmit = async () => {
    try {
      let imageBase64 = null;
      if (imageState instanceof File) {
        imageBase64 = await convertFileToHex(imageState);
      } else if (typeof imageState === 'string') {
        imageBase64 = imageState;
      }

      const movieData = {
        title: titleState,
        logline: loglineState,
        image: imageBase64,
        categories: categoriesState.split(',').map(id => id.trim()).filter(id => id),
      };

      if (isEditing) {
        await put(`/movies/${_id}`, movieData);
      } else {
        await post('/movies', movieData);
      }

      alert(`Movie ${isEditing ? 'updated' : 'created'} successfully!`);
      onClose();
      onSuccess();
    } catch (error) {
      console.error('Submission Error:', error);
      alert('Error: ' + error.message);
    }
  };

  return (
    <div>
      <h2>{isEditing ? 'Edit Movie' : 'Create Movie'}</h2>
      <div className="form-group">
        <input value={titleState} onChange={(e) => setTitle(e.target.value)} placeholder="Movie Title" required />
        <input value={loglineState} onChange={(e) => setLogline(e.target.value)} placeholder="Movie Logline" />
        
        {typeof imageState === 'string' ? (
          <img src={hexToBase64(imageState)} alt="Uploaded Movie" style={{ maxWidth: '300px', maxHeight: '200px' }} />
        ) : imageState ? (
          <img src={URL.createObjectURL(imageState)} alt="Uploaded Movie" style={{ maxWidth: '300px', maxHeight: '200px' }} />
        ) : null}

        <input type="file" accept="image/*" onChange={(e) => setImage(e.target.files[0])} required={!isEditing} />
        <input value={categoriesState} onChange={(e) => setCategories(e.target.value)} placeholder="Categories (comma-separated)" />
      </div>
      
      <button onClick={handleSubmit}>{isEditing ? 'Update Movie' : 'Create Movie'}</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default MovieForm;
