import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Signup from "./pages/Signup";
import Admin from "./pages/Admin";

const App = () => {
  return (
    <Router> {/* Wrap Routes with Router */}
      <Routes>
        <Route path="/" element={<Signup />} />
        <Route path="/admin" element={<Admin />} />

      </Routes>
    </Router>
  );
};

export default App;
