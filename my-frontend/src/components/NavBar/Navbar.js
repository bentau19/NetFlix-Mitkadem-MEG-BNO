import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import { isManager } from "../httpUtils.jsx";
import CategoriesDropdown from "../CategoriesDropdown.jsx";
import "./Navbar.css";
import { useNavigate } from 'react-router-dom';

const Navbar = ({ onSearchChange, selectedOption, setSelectedOption, theme }) => {
  const [inputValue, setInputValue] = useState(""); // Local state for the input value
  const [admin, setAdmin] = useState(false); // Properly initialize admin as a boolean
  const navigate = useNavigate();

  // Check if the user is an admin on component mount
  useEffect(() => {
    const checkAdminStatus = async () => {
      const isAdmin = await isManager(); // Assume isManager() returns a Promise
      setAdmin(isAdmin);
    };
    checkAdminStatus();
  }, []); // Empty dependency array ensures this runs only once

  const handleInputChange = (event) => {
    const query = event.target.value;
    setInputValue(query);
    onSearchChange(query); // Pass the value to the parent component
  };

  const handleSignOut = () => {
    // Your sign-out logic here
    sessionStorage.removeItem("token"); // Clear user token or any session storage
    alert("You have been signed out."); // Optional: Alert user
    navigate("/signin"); // Navigate to the sign-in page after signing out
  };

  // Choose the logo based on the current theme
  const logoSrc = theme === 'dark'
    ? '/photobackgroundweb.jpeg' 
    : 'https://upload.wikimedia.org/wikipedia/commons/0/08/Netflix_2015_logo.svg'; // Light logo

  return (
    <div className={`wrapper`}>
      <div className="content">
        <nav id="navbar">
          <div id="logo" style={{ width: "200px" }}>
            <NavLink to="/">
              <img
                src={logoSrc} // Use the theme-based logo source
                alt="logo"
              />
            </NavLink>
          </div>

          <NavLink>
            <div>
              <CategoriesDropdown selectedOption={selectedOption} setSelectedOption={setSelectedOption} />
            </div>
          </NavLink>

          <div>
            <NavLink to="/signin" onClick={handleSignOut}>
              SignOut
            </NavLink>
            <NavLink to="/">Home</NavLink>

            {/* Conditionally render Movies link based on admin status */}
            {admin ? <NavLink to="/admin">admin</NavLink> : null}
          </div>

          <div className="special-input-container">
            <input
              type="text"
              placeholder="Search Movies"
              className="special-input"
              value={inputValue}
              onChange={handleInputChange} // Trigger the input change
            />
          </div>

          <div className="pro">
            <NavLink to="/profile">
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/0/0b/Netflix-avatar.png"
                alt="logopro"
              />
            </NavLink>
          </div>
        </nav>
      </div>
    </div>
  );
};

export default Navbar;
