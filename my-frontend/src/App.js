import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Signup from "./pages/Signup";
import Admin from "./pages/Admin";
import LoggedMain from "./pages/mainPage/LoggedMain";

const App = () => {
  return (
    <Router> {/* Wrap Routes with Router */}
      <Routes>
        <Route path="/" element={<Signup />} />
        <Route path="/admin" element={<Admin />} />
        <Route path="/logged-main" element={<LoggedMain />} />
      </Routes>
    </Router>
  );
};

export default App;
