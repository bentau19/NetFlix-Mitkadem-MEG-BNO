
const hexToBase64 = (hex) => {
  // Convert hex string to a byte array
  const bytes = [];
  for (let i = 0; i < hex.length; i += 2) {
    bytes.push(parseInt(hex.substr(i, 2), 16));
  }
  
  // Convert byte array to Base64
  const binary = String.fromCharCode(...bytes);
  return `data:image/png;base64,${btoa(binary)}`;
};
  
  module.exports = { hexToBase64 };
  