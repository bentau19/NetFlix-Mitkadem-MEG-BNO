import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Signup from "./pages/Signup";
import Signin from './pages/Signin';
import Admin from "./pages/Admin";
import Guest from "./pages/mainPage/GuestMain";
import Logged from "./pages/mainPage/LoggedMain"
import AuthCheck from "./utils/AuthCheck"
import VideoPlayer from "./pages/videoPlayer"; 
import { useEffect } from "react";
import { ThemeProvider } from "./context/ThemeContext";
const App = () => {
  useEffect(() => {
    document.title = "MEG";
  }, []); // Runs once when the component mounts

  return (
    <ThemeProvider>
    <Router>

      <Routes>
        <Route path="/" element={<AuthCheck />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/signin" element={<Signin />} />
        <Route path="/admin" element={<Admin />} />
        <Route path="/movie" element={<VideoPlayer />} />
        {/* Add other routes as needed */}
      </Routes>
    </Router>
    </ThemeProvider>
  );
};

export default App;
