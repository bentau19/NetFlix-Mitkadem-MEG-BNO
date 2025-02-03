import React, { useEffect, useState } from 'react';
import { del } from './httpUtils.jsx'; // Assuming you have this function for DELETE requests
import Popup from './Popup'; // Assuming you have the Popup component
import { hexToBase64 } from '../utils/imageConverter.js';
const ItemList = ({ type, query, onEdit , refreshKey}) => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');

  // Fetch items when the component mounts or when query/type changes
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
  }, [type, query,refreshKey]);

  // Handle delete action
  const handleDelete = async () => {
    try {
      const url = itemToDelete
        ? type === 'category'
          ? `/categories/${itemToDelete._id}`
          : `/movies/${itemToDelete._id}`
        : '';

      if (url) {
        const response = await del(url);
        if (response.ok) {
          alert(` ${type === 'category' ? 'category' : 'movie'} with id ${itemToDelete._id} was deleted successfully!`);
          //give coresapanding message
        }
        setIsPopupOpen(false); // Close popup after deletion
        const newItems = items.filter(item => item._id !== itemToDelete._id); // Remove deleted item from list
        setItems(newItems); // Update the item list
      }
    } catch (error) {
      setErrorMessage(error.message); // Show error message in the popup
    }
  };

  // Show the confirmation popup when a delete button is clicked
  const handleDeleteClick = (item) => {
    setItemToDelete(item); // Set the item to delete
    setIsPopupOpen(true);  // Open the popup
  };

  if (loading) {
    //print loading while loading
    return <div>Loading...</div>;
  }

  if (items.length === 0) {
    //if there isnt item to diaplay , tell the user that
    return <div>No items available</div>;
  }

  

  return (
    <div className="item-list">
      {items.map((item) => (
        <div key={item._id} className="item-container">
          <div className="content">
            <h3>{type === 'category' ? item.name : item.title}</h3>
            <p><strong>ID:</strong> {item._id}</p>
            {type === 'movie' && (
              <>
      { item.image ? (
        <img
        src={hexToBase64(item.image)} //if we are in edit mode display current photo
          alt="Uploaded Movie"
          style={{ maxWidth: '100px', maxHeight: '60px' }}
        />
      ) :  (
        <strong>No Image Data</strong>
      ) }     
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
          </div>
          <div className="content">

          <button onClick={() => onEdit(item)} className="edit-button">
            Edit
          </button>
          <button onClick={() => handleDeleteClick(item)} className="delete-button">
            Delete
          </button>
          </div>
        </div>
      ))}
      <Popup isOpen={isPopupOpen} onClose={() => setIsPopupOpen(false)}>
        <h3>Are you sure you want to delete this item?</h3>
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <button onClick={handleDelete} className="confirm-delete">Yes</button>
        <button onClick={() => setIsPopupOpen(false)} className="cancel-delete">No</button>
      </Popup>
    </div>
  );
};

export default ItemList;
