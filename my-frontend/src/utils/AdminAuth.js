import React, { useEffect, useState } from "react";
import { get } from "../components/httpUtils"; 

const AdminAuth = ({ GuestComponent, LoggedComponent }) => {
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
        const result = await get('/tokens', {});
        setIsAuthenticated(result.admin);
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

  return isAuthenticated ? <LoggedComponent /> : <GuestComponent />;
};

export default AdminAuth;
