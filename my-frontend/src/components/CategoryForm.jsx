import React, { useState, useEffect } from 'react';

const CategoryForm = ({ onClose, initialValues = {}, onSuccess }) => {
  const { name, promoted } = initialValues || {};

  // Set initial state from initialValues
  const [nameState, setName] = useState(name || '');
  const [promotedState, setPromoted] = useState(promoted || false);
  const isEditing = Boolean(initialValues);

  // If initialValues change (for example, when editing an item), update the state
  useEffect(() => {
    setName(name || '');
    setPromoted(promoted || false);
  }, [initialValues]);

  const handleSubmit = async () => {
    try {
      const token = sessionStorage.getItem('token'); // Use 'token' as a string
      if (!token) {
        throw new Error('No token found. Please sign in.');
      }
      const url = isEditing
        ? `http://localhost:5000/api/categories/${initialValues._id}`
        : 'http://localhost:5000/api/categories';
      const method = isEditing ? 'PATCH' : 'POST';

      const response = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          'token': token,
        },
        body: JSON.stringify({ name: nameState, promoted: promotedState }), // Send the data as JSON
      });

      if (response.ok) {
        alert(`Category ${isEditing ? 'updated' : 'created'} successfully!`);
        onClose();
        onSuccess();
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
      <input
        value={nameState} // Bind to nameState, not name
        onChange={e => setName(e.target.value)} 
        placeholder="Category Name"
      />
      <label>
        Promoted
        <input
          type="checkbox"
          checked={promotedState} // Bind to promotedState, not promoted
          onChange={e => setPromoted(e.target.checked)} 
        />
      </label>
      <div className="form-group"></div>
      <button onClick={handleSubmit}>
        {isEditing ? 'Update Category' : 'Create Category'}
      </button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default CategoryForm;
