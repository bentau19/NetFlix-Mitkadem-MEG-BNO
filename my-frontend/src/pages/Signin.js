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
  const navigate = useNavigate();
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
      navigate('/');
      sessionStorage.setItem('token', result);
    } catch (error) {
      setMessage('' + error);
      console.error(error);
    }
  };

  return (
    <div className='form_background'>
    <SigninForm
      formData={formData}
      handleChange={handleChange}
      handleSubmit={handleSubmit}
      message={message}
    />
    </div>
  );
};

export default Signin;