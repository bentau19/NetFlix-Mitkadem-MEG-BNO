
const hexToBase64 = (hex) => {
  const binary = hex.match(/.{1,2}/g).map(byte => String.fromCharCode(parseInt(byte, 16))).join('');
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
function hexToDataUrl(hexString) {
  // Convert the hexadecimal string to a byte array
  const bytes = [];
  for (let i = 0; i < hexString.length; i += 2) {
    bytes.push(parseInt(hexString.substr(i, 2), 16));
  }

  // Convert the byte array to a binary string
  const binary = String.fromCharCode(...bytes);

  // Convert the binary string to a base64 string
  const base64 = btoa(binary);

  // Create a data URL
  return `data:image/png;base64,${base64}`;
}
  
  module.exports = { hexToBase64, convertFileToHex,hexToDataUrl };
  