import React, { useEffect, useState } from 'react';

const ItemList = ({ type }) => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchItems = async () => {
      const url = type === 'category' ? 'http://localhost:5000/api/categories' : 'http://localhost:5000/api/movies/search';
      try {
        const response = await fetch(url);

        // Check if the response status is ok (200-299)
        if (!response.ok) {
          throw new Error(`Error: ${response.statusText}`);
        }

        const data = await response.json(); // Parse the JSON response
        setItems(data.message || data);  // Make sure 'message' contains the array
        setLoading(false);
      } catch (error) {
        alert('Failed to load items: ' + error.message);
        setLoading(false);
      }
    };

    fetchItems();
  }, [type]); // Only run when `type` changes

  if (loading) {
    return <div>Loading...</div>;
  }

  if (items.length === 0) {
    return <div>No items available</div>;
  }

  return (
    <ul>
      {items.map(item => (
        <li key={item._id}>
          <strong>{type === 'category' ? item.name : item.title}</strong> (ID: {item._id})
        </li>
      ))}
    </ul>
  );
};

export default ItemList;
