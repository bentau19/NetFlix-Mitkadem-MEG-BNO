import React, { useState } from 'react';

const MovieForm = ({ onClose }) => {
  const [title, setTitle] = useState('');
  const [logline, setLogline] = useState('');
  const [image, setImage] = useState('');
  const [categories, setCategories] = useState('');

  const handleSubmit = async () => {
    try {
      const response = await fetch('http://localhost:5000/api/movies', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 
          title,
          logline,
          image,
          categories: categories.split(',').map(id => id.trim()),
         }), // Send the data as JSON
      });
      
      const data = await response.json(); // Parse the response as JSON

      if(response.ok){
      alert('Movie created successfully!');
      }
      else{
        alert('basa:'+data.message);
      }
      onClose();
    } catch (error) {
      alert('Failed to create movie: ' + error.message);
    }
  };

  return (
    <div>
      <h2>Create Movie</h2>
      <input value={title} onChange={e => setTitle(e.target.value)} placeholder="Movie Title" />
      <input value={logline} onChange={e => setLogline(e.target.value)} placeholder="Movie Logline" />
      <input value={image} onChange={e => setImage(e.target.value)} placeholder="Image URL" />
      <input value={categories} onChange={e => setCategories(e.target.value)} placeholder="Categories (comma-separated)" />
      <button onClick={handleSubmit}>Create Movie</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default MovieForm;
