import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Signup from "./pages/Signup";
import Admin from "./pages/Admin";
import Admin2 from "./pages/Admin2";

const App = () => {
  return (
    <Router> {/* Wrap Routes with Router */}
      <Routes>
        <Route path="/" element={<Signup />} />
        <Route path="/admin" element={<Admin />} />
        <Route path="/admin2" element={<Admin2 />} />

      </Routes>
    </Router>
  );
};

export default App;
