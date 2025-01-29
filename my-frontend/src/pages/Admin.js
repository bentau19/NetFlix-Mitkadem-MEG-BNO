import React, { useState, useEffect, useContext } from 'react';
import { ThemeContext } from "../context/ThemeContext";
import { useNavigate } from 'react-router-dom';

import Popup from '../components/Popup';
import MovieForm from '../components/MovieForm';
import CategoryForm from '../components/CategoryForm';
import ItemList from '../components/ItemList';
import SearchBar from '../components/SearchBar';
import './stylesb.css';


const Admin = () => {
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [formType, setFormType] = useState('movie');
  const [viewType, setViewType] = useState('movie');
  const [searchQuery, setSearchQuery] = useState('');
  const [editingItem, setEditingItem] = useState(null);
  const { theme, toggleTheme } = useContext(ThemeContext);
  const navigate = useNavigate();

  useEffect(() => {
    // Check if the token exists and if the user is a manager
    const token = sessionStorage.getItem('token');
    
    if (!token) {
      navigate('/');
    }
  }, [navigate]);  // The dependency array makes sure this check runs when the component loads.

  const openPopup = (type, item = null) => {
    setFormType(type);
    setEditingItem(item);
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
    setEditingItem(null);
  };

  const handleSearch = (query) => {
    setSearchQuery(query);
  };

  return (
    <div>
      <div className="top-container">
        <div className="search-section">
          <SearchBar onSearch={handleSearch} query={searchQuery} />
          <div className="search-controls">
            <select onChange={(e) => setViewType(e.target.value)} value={viewType}>
              <option value="movie">Movie</option>
              <option value="category">Category</option>
            </select>
            <button onClick={() => openPopup(viewType)}>Add</button>
            <button onClick={() => navigate('/')}>Back to Main</button>
          </div>
        </div>
        <button onClick={toggleTheme}>
          Switch to {theme === "light" ? "Dark" : "Light"} Mode
        </button>
      </div>
    
      <Popup isOpen={isPopupOpen} onClose={closePopup}>
        {formType === 'movie' ? (
          <MovieForm onClose={closePopup} initialValues={editingItem} />
        ) : (
          <CategoryForm onClose={closePopup} initialValues={editingItem} />
        )}
      </Popup>

      <div className="item-list-container">
        <ItemList
          type={viewType}
          query={searchQuery}
          onEdit={(item) => openPopup(viewType, item)}
        />
      </div>
    </div>
  );
};

export default Admin;
