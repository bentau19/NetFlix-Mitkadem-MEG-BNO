import React from 'react';
import ReactDOM from 'react-dom/client'; // For React 18 or later
import App from './App'; // Import your main component

// Select the root element in the HTML
const rootElement = document.getElementById('root');

// Create a root and render your App component inside it
if (rootElement) {
  const root = ReactDOM.createRoot(rootElement); // React 18
  root.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>
  );
} else {
  console.error('Root element not found');
}
