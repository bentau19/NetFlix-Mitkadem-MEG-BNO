import React, { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import { Wrapper, Content } from "./navbar.style.js";
import "./Navbar.css";

const Navbar = ({ onSearchChange }) => {
  const [show, handleShow] = useState(false);
  const [inputValue, setInputValue] = useState(''); // Local state for the input value

  useEffect(() => {
    window.addEventListener("scroll", () => {
      if (window.scrollY > 50) {
        handleShow(true);
      } else {
        handleShow(false);
      }

      return () => {
        window.removeEventListener('scroll');
      };
    });
  }, []);

  const handleInputChange = (event) => {
    const query = event.target.value;
    setInputValue(query);
    onSearchChange(query); // Pass the value to the parent component
  };

  return (
    <Wrapper>
      <Content id={`${show && 'nav_black'}`}>
        <nav id="navbar">
          <div id="logo">
            <NavLink to={"/"}><img src="https://upload.wikimedia.org/wikipedia/commons/0/08/Netflix_2015_logo.svg" alt="logo" /></NavLink>
          </div>
          <div>
            <NavLink to="/">Home</NavLink>
            <NavLink to="/TVshow">TV Shows</NavLink>
            <NavLink to="/movies">Movies</NavLink>
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
            <NavLink to={'/profile'}> <img src="https://upload.wikimedia.org/wikipedia/commons/0/0b/Netflix-avatar.png" alt="logopro" /></NavLink>
          </div>
        </nav>
      </Content>
    </Wrapper>
  );
};

export default Navbar;
