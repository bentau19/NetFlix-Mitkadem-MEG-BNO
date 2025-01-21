import React, { useState } from 'react'; // Ensure useState is imported

const MovieForm = ({ onClose, initialValues = {} }) => {
  const [title, setTitle] = useState(initialValues.title || '');
  const [logline, setLogline] = useState(initialValues.logline || '');
  const [image, setImage] = useState(initialValues.image || '');
  const [categories, setCategories] = useState(initialValues.categories?.join(', ') || '');
  const isEditing = Boolean(initialValues._id);

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
        },
        body: JSON.stringify({
          title,
          logline,
          image,
          categories: categories.split(',').map((id) => id.trim()),
        }),
      });

      if (response.ok) {
        alert(`Movie ${isEditing ? 'updated' : 'created'} successfully!`);
        onClose();
      } else {
        const data = await response.json();
        alert(`Error: ${data.message}`);
      }
    } catch (error) {
      alert('Failed to submit the form: ' + error.message);
    }
  };

  return (
    <div>
      <h2>{isEditing ? 'Edit Movie' : 'Create Movie'}</h2>
      <input
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        placeholder="Movie Title"
      />
      <input
        value={logline}
        onChange={(e) => setLogline(e.target.value)}
        placeholder="Movie Logline"
      />
      <input
        value={image}
        onChange={(e) => setImage(e.target.value)}
        placeholder="Image URL"
      />
      <input
        value={categories}
        onChange={(e) => setCategories(e.target.value)}
        placeholder="Categories (comma-separated)"
      />
      <button onClick={handleSubmit}>
        {isEditing ? 'Update Movie' : 'Create Movie'}
      </button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default MovieForm;
