import React, { useEffect, useState } from "react";
import Logged from "../pages/mainPage/LoggedMain";
import Guest from "../pages/mainPage/GuestMain";
import { get } from "../components/httpUtils"; 

const AuthCheck = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const validateToken = async () => {
      const token = sessionStorage.getItem('token');

      if (!token) {
        setIsAuthenticated(false);
        setIsLoading(false);
        return;
      }

      try {
        const result = await get('/tokens', { });
        if (result._id) {
          setIsAuthenticated(true);
        } else {
          setIsAuthenticated(false);
        }
      } catch (error) {
        console.error('Token validation failed:', error);
        setIsAuthenticated(false);
      } finally {
        setIsLoading(false);
      }
    };

    validateToken();
  }, []);


  if (isLoading) {
    return <div>Loading...</div>;
  }


  return isAuthenticated ? <Logged /> : <Guest />;
};

export default AuthCheck;