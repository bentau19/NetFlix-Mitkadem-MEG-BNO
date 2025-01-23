
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
// Function to convert the image buffer to a hex string
const convertFileToHex = (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();

    // When the file is loaded
    reader.onloadend = () => {
      const arrayBuffer = reader.result; // The file is loaded as an ArrayBuffer
      const uint8Array = new Uint8Array(arrayBuffer);
      let hexString = '';
      uint8Array.forEach(byte => {
        hexString += byte.toString(16).padStart(2, '0');
      });
      resolve(hexString); // Return the hex string
    };

    // If there's an error reading the file
    reader.onerror = (error) => {
      reject(error);
    };

    reader.readAsArrayBuffer(file); // Read the file as ArrayBuffer
  });
};

  
  module.exports = { hexToBase64, convertFileToHex };
  