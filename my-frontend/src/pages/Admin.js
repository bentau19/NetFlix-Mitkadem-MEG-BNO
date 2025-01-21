import React, { useState, useEffect } from 'react';
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

  useEffect(() => {
    setSearchQuery('');
  }, [viewType]);

  const openPopup = (type) => {
    setFormType(type);
    setIsPopupOpen(true);
  };

  const closePopup = () => setIsPopupOpen(false);

  const handleSearch = (query) => {
    console.log('Search Query:', query);
    setSearchQuery(query);
  };

  return (
    <div>
      <div className="content-wrapper">
        <div className="search-section">
          <SearchBar onSearch={handleSearch} query={searchQuery} />
          <div className="search-controls">
            <select onChange={(e) => setViewType(e.target.value)} value={viewType}>
              <option value="movie">Movie</option>
              <option value="category">Category</option>
            </select>
            <button onClick={() => openPopup(viewType)}>Add</button>
          </div>
        </div>
        <div className="item-list-container">
          <ItemList type={viewType} query={searchQuery} />
        </div>
      </div>
      <Popup isOpen={isPopupOpen} onClose={closePopup}>
        {formType === 'movie' ? <MovieForm onClose={closePopup} /> : <CategoryForm onClose={closePopup} />}
      </Popup>
    </div>
  );
};

export default Admin;