import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Signup from "./pages/Signup";
import Signin from './pages/Signin';
import Admin from "./pages/Admin";
import LoggedMain from "./pages/mainPage/LoggedMain";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Signup />} />
        <Route path="/signin" element={<Signin />} />
        <Route path="/admin" element={<Admin />} />
        {/* Add other routes as needed */}
      </Routes>
    </Router>
  );
};

export default App;
