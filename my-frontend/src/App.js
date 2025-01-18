import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Signup from "./pages/Signup";

const App = () => {
  return (
    <Router> {/* Wrap Routes with Router */}
      <Routes>
        <Route path="/" element={<Signup />} />
      </Routes>
    </Router>
  );
};

export default App;
