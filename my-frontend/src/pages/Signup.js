import React, { useState } from 'react';
import './styles.css';

const Signup = () => {
  // Define state for form fields
  const [formData, setFormData] = useState({
    name: '',
    password: '',
    email: '',
    image: '1'
  });
  
  // Define state for handling errors or success messages
  const [message, setMessage] = useState('');

  // Handle form input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();  // Prevent default form submission behavior

    try {
      // Send POST request using fetch
      const response = await fetch('http://localhost:5000/api/users', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),  // Convert form data to JSON
      });

      const result = await response.json();  // Parse the JSON response

      if (response.ok) {
        // Handle successful response
        setMessage('Signup successful!');
        console.log(result); // Log the response from the server
      } else {
        // Handle failed response
        setMessage('Signup failed. Please try again.');
      }
    } catch (error) {
      // Handle network or other errors
      setMessage('An error occurred. Please try again later.');
      console.error(error);
    }
  };

  return (
    <div className="signup-container">
      <h2>Sign Up</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="displayName">name</label>
          <input
            type="text"
            id="displayName"
            name="displayName"
            value={formData.displayName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="userName">Email</label>
          <input
            type="text"
            id="userName"
            name="userName"
            value={formData.userName}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit">Sign Up</button>
      </form>

      {message && <p>{message}</p>}  {/* Display success or error message */}
    </div>
  );
};

export default Signup;
