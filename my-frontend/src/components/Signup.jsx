
import React, { useState } from 'react';
import { convertFileToHex } from '../utils/imageConverter'
const SignupForm = ({ formData, handleChange, handleSubmit, message, updateFormData }) => {
    const [imageHex, setImageHex] = useState('');
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
  return (
    <div className="signup-container">
      <h2>Sign Up</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input
            type="name"
            id="displayName"
            name="displayName"
            value={formData.name}
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
          <label htmlFor="userName">userName</label>
          <input
            type="text"
            id="userName"
            name="userName"
            value={formData.email}
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
