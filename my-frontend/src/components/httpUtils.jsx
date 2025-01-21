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
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...headers,
    },
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  try {
    const response = await fetch(url, options);

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || `HTTP error! Status: ${response.status}`);
    }

    return response.json();
  } catch (error) {
    console.error(`Error during ${method} request to ${url}:`, error.message);
    throw error;
  }
};
export const get = async (url) => await fetchRequest(url, 'GET');
export const post = async (url, body) => await fetchRequest(url, 'POST', body);
export const patch = async (url, body) => await fetchRequest(url, 'PATCH', body);
export const put = async (url, body) => await fetchRequest(url, 'PUT', body);
export const del = async (url, body = null) => await fetchRequest(url, 'DELETE', body);