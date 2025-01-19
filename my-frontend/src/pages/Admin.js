import React, { useState } from 'react';
import Popup from '../components/Popup';
import MovieForm from '../components/MovieForm';
import CategoryForm from '../components/CategoryForm';
import ItemList from '../components/ItemList';
import SearchBar from '../components/SearchBar';
import "./styles.css";

const Admin = () => {
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [formType, setFormType] = useState('movie');
  const [viewType, setViewType] = useState('movie');

  const openPopup = type => {
    setFormType(type);
    setIsPopupOpen(true);
  };

  const closePopup = () => setIsPopupOpen(false);

  return (
    <div className="container">
      <h1>Movies and Categories</h1>
      <SearchBar onSearch={() => {}} />
      <div>
        <select onChange={e => setViewType(e.target.value)} value={viewType}>
          <option value="movie">Movie</option>
          <option value="category">Category</option>
        </select>
        <button onClick={() => openPopup(viewType)}>Add</button>
      </div>
      <ItemList type={viewType} />
      <Popup isOpen={isPopupOpen} onClose={closePopup}>
        {formType === 'movie' ? <MovieForm onClose={closePopup} /> : <CategoryForm onClose={closePopup} />}
      </Popup>
    </div>
  );
};

export default Admin;
