import React, { useEffect, useState } from 'react';
const ItemList = ({ type, query, onEdit }) => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchItems = async () => {
      const url = type === 'category' ? 'http://localhost:5000/api/categories/search' : 'http://localhost:5000/api/movies/search';
      const searchUrl = query ? `${url}/${encodeURIComponent(query)}` : url;
      try {
        const response = await fetch(searchUrl);
        if (!response.ok) {
          throw new Error(`Error: ${response.statusText}`);
        }
        const data = await response.json();
        setItems(data.message || data); // Assuming `data.message` is the result
        setLoading(false);
      } catch (error) {
        alert('Failed to load items: ' + error.message);
        setLoading(false);
      }
    };
    if (query || type) {
      fetchItems();
    }
  }, [type, query]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (items.length === 0) {
    return <div>No items available</div>;
  }

  return (
    <div className="item-list">
      {items.map((item) => (
        <div key={item._id} className="item-container">
          <h3>{type === 'category' ? item.name : item.title}</h3>
          <p><strong>ID:</strong> {item._id}</p>
          {type === 'movie' && (
            <>
              <p><strong>Image:</strong> {item.image}</p>
              <p><strong>Logline:</strong> {item.logline}</p>
              <p><strong>Categories:</strong></p>
              <ul>
                {Array.isArray(item.categories) && item.categories.length > 0 ? (
                  item.categories.map((categoryId) => <li key={categoryId}>ID: {categoryId}</li>)
                ) : (
                  <li>No categories</li>
                )}
              </ul>
            </>
          )}
          {type === 'category' && (
            <p><strong>Promoted:</strong> {item.promoted ? 'Yes' : 'No'}</p>
          )}
          <button onClick={() => onEdit(item)} className="edit-button">
            Edit
          </button>
        </div>
      ))}
    </div>
  );
};

export default ItemList;
