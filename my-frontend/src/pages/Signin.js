// signin.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { post } from '../components/httpUtils';
import SigninForm from '../components/signin.jsx'; // Import the JSX component
import './styles.css';

const Signin = () => {
  const [formData, setFormData] = useState({
    userName: '',
    password: '',
  });

  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const result = await post('/tokens', formData);
      setMessage('Signin successful!');
      console.log(result);
      // Redirect to dashboard or home page after successful signin
    } catch (error) {
      setMessage('An error occurred. Please try again later.');
      console.error(error);
    }
  };

  return (
    <SigninForm
      formData={formData}
      handleChange={handleChange}
      handleSubmit={handleSubmit}
      message={message}
    />
  );
};

export default Signin;