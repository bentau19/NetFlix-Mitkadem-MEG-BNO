import React from "react";
import { useNavigate } from "react-router-dom";

const GuestMain = () => {
  const navigate = useNavigate();

  return (

    <div>
        
      <div>
        <button onClick={() => navigate("/signup")}>Sign Up</button>
        <button onClick={() => navigate("/signin")}>Log In</button>
      </div>
      <h1>Welcome to Our Website!</h1>
      <p>
        Explore our platform and discover amazing features. Join us today to get
        started!
      </p>

      <div>
        <h2>Features</h2>
        <ul>
          <li>Easy-to-use interface</li>
          <li>Secure and reliable</li>
          <li>Accessible from anywhere</li>
        </ul>
      </div>
    </div>
  );
};

export default GuestMain;