const fs = require('fs');

const convertToHex = (filePath) => {
  return new Promise((resolve, reject) => {
    fs.readFile(filePath, (err, data) => {
      if (err) return reject(err);
      resolve(data.toString('hex'));
    });
  });
};

// Convert hex back to base64 for display
const hexToBase64 = (hexString) => {
    const bytes = Buffer.from(hexString, 'hex');
    return Buffer.from(bytes).toString('base64');
  };
  
  module.exports = { convertToHex, hexToBase64 };
  