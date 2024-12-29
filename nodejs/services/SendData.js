const net = require('net');
const mongoose = require('mongoose');
require('custom-env').env(process.env.NODE_ENV, './config');
mongoose.connect(process.env.CONNECTION_STRING);
function communicateWithServer(dataToSend) {
    host = process.env.hostClient
    port = process.env.portClient
    return new Promise((resolve, reject) => {
      const client = net.createConnection({ host, port }, () => {
        client.write(dataToSend);
      });
  
      let responseData = '';
  
      // Handle incoming data
      client.on('data', (data) => {
        responseData += data.toString();
      });
  
      // Handle connection end
      client.on('end', () => {
        resolve(responseData);
      });
      client.on('error', (err) => {
        reject(`Error: ${err.message}`);
      });
    });
}
module.exports = {communicateWithServer};