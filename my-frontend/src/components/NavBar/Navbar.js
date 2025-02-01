import React, { useState, useEffect, useContext } from "react";
import { NavLink } from "react-router-dom";
import { isManager,getUser } from "../httpUtils.jsx";
import  CategoriesDropdown  from "../CategoriesDropdown.jsx";
import { ThemeContext } from "../../context/ThemeContext";


import "./Navbar.css";
import { useNavigate } from 'react-router-dom';
import { hexToBase64 } from '../../utils/imageConverter.js';
const Navbar = ({ onSearchChange,selectedOption,setSelectedOption }) => {


  const [userPhoto,setUserPhoto]=useState("https://upload.wikimedia.org/wikipedia/commons/0/0b/Netflix-avatar.png")

 // const [theme,setTheme]=useState("dark")
   const { theme, toggleTheme } = useContext(ThemeContext);
  const [inputValue, setInputValue] = useState(""); // Local state for the input value
  const [admin, setAdmin] = useState(false); // Properly initialize admin as a boolean
  const navigate = useNavigate();

  // Check if the user is an admin on component mount
  useEffect(() => {

    const checkAdminStatus = async () => {
      const isAdmin = await isManager(); // Assume isManager() returns a Promise
      setAdmin(isAdmin);
    };
    const getUserInit = async () => {
      const usr = await getUser(); // Assume isManager() returns a Promise
      if(usr.image){
        setUserPhoto(hexToBase64(usr.image));
      }
    };
    getUserInit();
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
    ? '/image/meg_purple.png' 
    : '/image/meg_red.png'; // Light logo

  return (
    <div className={`wrapper`}>
      <div className="content">
        <nav id="navbar">
        <NavLink>
        <button onClick={toggleTheme}>
        Switch to {theme === "light" ? "Dark" : "Light"} Mode
        </button>
            </NavLink>
        <div id="logo" style={{marginLeft:"-60px", width: "170px" }}>
            <NavLink to="/">
              <img
                src= {userPhoto}
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
            <NavLink>
           <img
                src= {userPhoto}
                alt="logo"

              />
            </NavLink>

          </div>

        </nav>
      </div>
    </div>
  );
};

export default Navbar;
