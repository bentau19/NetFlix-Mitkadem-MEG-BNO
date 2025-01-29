
import React, { useState } from 'react';
import SignupForm from '../components/Signup';
import { post } from '../components/httpUtils';
import { useNavigate } from 'react-router-dom';
const Signup = () => {
  const [formData, setFormData] = useState({
    DisplayName: '',
    password: '',
    userName: '',
    image: ''
  });

  const [message, setMessage] = useState('');


  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  const navigate = useNavigate();
  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();  // Prevent default form submission behavior

    try {
      // Send POST request using the post utility function
      const result = await post('/users', formData);

      // Handle successful response
      setMessage('Signup successful!');
      console.log(result); // Log the response from the server
      navigate('/signin');
    } catch (error) {
      // Handle network or other errors
      setMessage(''+error);

    }
  };

  return (
    <div className='form_background'>

    <SignupForm
      formData={formData}
      handleChange={handleChange}
      handleSubmit={handleSubmit}
      message={message}
      updateFormData={(key, value) => setFormData({ ...formData, [key]: value })}
    />
    </div>
  );
};

export default Signup;
