const net = require('net');
const mongoose = require('mongoose');
require('custom-env').env(process.env.NODE_ENV, './nodejs/config');
mongoose.connect(process.env.CONNECTION_STRING);
function communicateWithServer(dataToSend) {
    host = process.env.hostClient
    port = process.env.portClient
    return new Promise((resolve, reject) => {
      const client = net.createConnection({ host, port }, () => {
        client.write(dataToSend);
      });
  
      let responseData = '';
  
      client.on('data', (data) => {
        responseData += data.toString();
        resolve(responseData);
        client.end();
      });

      


    });
}
module.exports = {communicateWithServer};