const API_BASE_URL = 'http://localhost:5000/api'; // Change this to your API's base URL

/**
 * Handles fetch requests with shared logic.
 * @param {string} endpoint - The API endpoint (e.g., '/movies').
 * @param {string} method - HTTP method (GET, POST, PATCH, DELETE).
 * @param {Object} [body] - Request body for POST, PATCH, DELETE methods.
 * @param {Object} [headers] - Optional headers (default includes JSON content type).
 * @returns {Promise<Object>} - The response data as a JSON object.
 */
const fetchRequest = async (endpoint, method, body = null, headers = {}) => {
  const url = `${API_BASE_URL}${endpoint}`;
  console.log(url); // Log the URL to ensure it's correct
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...headers,
    },
  };
  console.log(method); // Log the method to ensure it's correct

  if (body) {
    options.body = JSON.stringify(body);
  }

  try {
    const response = await fetch(url, options);

    if (!response.ok) {
      const errorData = await response.json().catch(() => null); // Prevent JSON parsing error
      throw new Error(errorData?.message || `HTTP error! Status: ${response.status}`);
    }

    // Check if response has content before parsing JSON
    const contentType = response.headers.get("content-type");
    if (contentType && contentType.includes("application/json")) {
      return response.json(); // Parse JSON to return proper data
    } else {
      return { ok: response.ok, status: response.status }; // Return status for non-JSON responses
    }
    
  } catch (error) {
    console.error(`Error during ${method} request to ${url}:`, error.message);
    throw error;
  }
};

export const get = async (url) => await fetchRequest(url, 'GET');
export const post = async (url, body) => await fetchRequest(url, 'POST', body);
export const patch = async (url, body) => await fetchRequest(url, 'PATCH', body);
export const put = async (url, body) => await fetchRequest(url, 'PUT', body);
export const del = async (url) => await fetchRequest(url, 'DELETE');

export const deleteMovie = async (id) => {
  const url = `http://localhost:5000/api/movies/${id}`;

  try {
    const response = await fetch(url, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        // Add any other necessary headers (e.g., Authorization)
      }
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Handle successful response
    console.log('Movie deleted successfully');
  } catch (error) {
    console.error('Error deleting movie:', error);
  }
};
export const deleteCategory = async (id) => {
  const url = `http://localhost:5000/api/categories/${id}`;

  try {
    const response = await fetch(url, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        // Add any other necessary headers (e.g., Authorization)
      }
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Handle successful response
    console.log('Movie deleted successfully');
  } catch (error) {
    console.error('Error deleting movie:', error);
  }
};