import React, { useState } from 'react';

const CategoryForm = ({ onClose }) => {
  const [name, setName] = useState('');
  const [promoted, setPromoted] = useState(false);

  const handleSubmit = async () => {
    try {
      const response = await fetch('http://localhost:5000/api/categories', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name, promoted }), // Send the data as JSON
      });
      
      const data = await response.json(); // Parse the response as JSON
            alert('Category created successfully!');
      onClose();
    } catch (error) {
      alert('Failed to create category: ' + error.message);
    }
  };

  return (
    <div>
      <h2>Create Category</h2>
      <input value={name} onChange={e => setName(e.target.value)} placeholder="Category Name" />
      <label>
        <input type="checkbox" checked={promoted} onChange={e => setPromoted(e.target.checked)} />
        Promoted
      </label>
      <button onClick={handleSubmit}>Create Category</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default CategoryForm;
