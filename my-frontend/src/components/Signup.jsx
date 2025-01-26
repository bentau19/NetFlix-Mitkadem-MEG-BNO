import React, { useState } from 'react';
import { convertFileToHex } from '../utils/imageConverter';

const SignupForm = ({ formData, handleChange, handleSubmit, message, updateFormData }) => {
  const [imageHex, setImageHex] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [reEnterPassword, setReEnterPassword] = useState('');
  const [reEnterPasswordError, setReEnterPasswordError] = useState('');

  const handleImageChange = async (e) => {
    const file = e.target.files[0];
    if (file) {
      try {
        const hexString = await convertFileToHex(file);
        setImageHex(hexString); // Update local state for debugging or other needs
        updateFormData('image', hexString); // Update form data in parent state
      } catch (error) {
        console.error('Error converting file to hex:', error);
      }
    }
  };

  const validatePassword = (password) => {
    // Password must be at least 8 characters and include letters and numbers
    const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return regex.test(password);
  };

  const handlePasswordChange = (e) => {
    const { value } = e.target;
    handleChange(e); // Update the form data in the parent component

    // Validate the password
    if (!validatePassword(value)) {
      setPasswordError('Password must be at least 8 characters and include letters and numbers.');
    } else {
      setPasswordError('');
    }
  };

  const handleReEnterPasswordChange = (e) => {
    const { value } = e.target;
    setReEnterPassword(value);

    // Check if the re-entered password matches the original password
    if (value !== formData.password) {
      setReEnterPasswordError('Passwords do not match.');
    } else {
      setReEnterPasswordError('');
    }
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();

    // Check if the password is valid
    if (!validatePassword(formData.password)) {
      setPasswordError('Password must be at least 8 characters and include letters and numbers.');
      return;
    }

    // Check if the re-entered password matches the original password
    if (reEnterPassword !== formData.password) {
      setReEnterPasswordError('Passwords do not match.');
      return;
    }

    // If everything is valid, call the parent's handleSubmit function
    handleSubmit(e);
  };

  return (
    <div className="signup-container">
      <h2>Sign Up</h2>
      <form onSubmit={handleFormSubmit}>
        <div className="form-group">
          <label htmlFor="displayName">Name</label>
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
            onChange={handlePasswordChange}
            required
          />
          {passwordError && <p className="error">{passwordError}</p>}
        </div>
        <div className="form-group">
          <label htmlFor="reEnterPassword">Re-enter Password</label>
          <input
            type="password"
            id="reEnterPassword"
            name="reEnterPassword"
            value={reEnterPassword}
            onChange={handleReEnterPasswordChange}
            required
          />
          {reEnterPasswordError && <p className="error">{reEnterPasswordError}</p>}
        </div>
        <div className="form-group">
          <label htmlFor="userName">Username</label>
          <input
            type="text"
            id="userName"
            name="userName"
            value={formData.userName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="image">Profile Image</label>
          <input
            type="file"
            id="image"
            name="image"
            accept="image/*"
            onChange={handleImageChange}
          />
        </div>
        <button type="submit">Sign Up</button>
      </form>

      {message && <p>{message}</p>} {/* Display success or error message */}
    </div>
  );
};

export default SignupForm;