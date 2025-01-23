import React, { useState } from 'react';

const CategoryForm = ({ onClose ,  initialValues = {} }) => {
  const { name, promoted } = initialValues || {};

  const [nameState, setName] = useState(name || '' );
  const [promotedState, setPromoted] = useState(promoted || false);
  const isEditing = Boolean(initialValues);

  const handleSubmit = async () => {
    try {
      const url = isEditing
      ? `http://localhost:5000/api/categories/${initialValues._id}`
      : 'http://localhost:5000/api/categories';
    const method = isEditing ? 'PATCH' : 'POST';
      const response = await fetch(url , {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name: nameState, promoted:promotedState }), // Send the data as JSON
      });
      
      if (response.ok) {
        alert(`Category ${isEditing ? 'updated' : 'created'} successfully!`);
        onClose();
      } else {
        const data = await response.json();
        alert(`Error: ${data.message}`);
      }
    } catch (error) {
      alert('Failed to create category: ' + error.message);
    }
  };
  

  return (
    <div>
      <h2>{isEditing ? 'Edit Category' : 'Create Category'}</h2>
      <input value={name} onChange={e => setName(e.target.value)} placeholder="Category Name" />
      <label>
        <input type="checkbox" checked={promoted} onChange={e => setPromoted(e.target.checked)} />
        Promoted
      </label>
      <button onClick={handleSubmit}>
        {isEditing ? 'Update Category' : 'Create Category'}
      </button>      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default CategoryForm;
