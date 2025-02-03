import React, { useState, useContext } from 'react';
import { ThemeContext } from "../context/ThemeContext";
import { useNavigate } from 'react-router-dom';
import AdminAuth from '../utils/AdminAuth'; // Import AdminAuth

import Popup from '../components/Popup';
import MovieForm from '../components/MovieForm';
import CategoryForm from '../components/CategoryForm';
import ItemList from '../components/ItemList';
import SearchBar from '../components/SearchBar';
import './stylesb.css';

// Redirect Component for Unauthorized Users
const RedirectToHome = () => {
  const navigate = useNavigate();
  
  React.useEffect(() => {
    navigate('/'); //navigate to home page
  }, [navigate]);

  return null; // Return nothing since we're just redirecting
};

const AdminPanel = () => {
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [formType, setFormType] = useState('movie');
  const [viewType, setViewType] = useState('movie');
  const [searchQuery, setSearchQuery] = useState('');
  const [editingItem, setEditingItem] = useState(null);
  const [refreshKey, setRefreshKey] = useState(0);

  const triggerRefresh = () => {
    setRefreshKey(prevKey => prevKey + 1); //refreash
  };
    const navigate = useNavigate();

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

      </div>
    
            <Popup isOpen={isPopupOpen} onClose={closePopup}>
        {formType === 'movie' ? (
          <MovieForm onClose={closePopup} onSuccess={triggerRefresh} initialValues={editingItem} />
        ) : (
          <CategoryForm onClose={closePopup} onSuccess={triggerRefresh} initialValues={editingItem} />
        )}
      </Popup>


      <div className="item-list-container">
        <ItemList
          type={viewType}
          query={searchQuery}
          onEdit={(item) => openPopup(viewType, item)}
          refreshKey={refreshKey} // refresh when change happen as seen in onSuccess above

        />
      </div>
    </div>
  );
};

const Admin = () => {
  //if not logged or logged but isnt admin send back to home page
  return <AdminAuth GuestComponent={RedirectToHome} LoggedComponent={AdminPanel} />;
};

export default Admin;
