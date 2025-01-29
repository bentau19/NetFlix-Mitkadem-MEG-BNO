// signin.jsx
import React from 'react';


const SigninForm = ({ formData, handleChange, handleSubmit, message }) => {
  return (
    <div className="signin-container">
      <div style={{ height: "50px" }}></div>

      <form onSubmit={handleSubmit}>
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
        <button type="submit">Sign In</button>
      </form>

      {message && <p>{message}</p>}
    </div>
  );
};

export default SigninForm;