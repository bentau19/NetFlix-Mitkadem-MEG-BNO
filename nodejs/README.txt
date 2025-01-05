FOR RUNNING:
npm install
npm run start


avaible commends:
curl -i -X POST http://localhost:3000/api/users -H "Content-Type: application/json" -d "{\"name\": \"aaaaaaaaa\", \"image\": \"oscar\", \"password\": \"tau\"}"

curl -i -X GET http://localhost:3000/api/users/100006(id)

curl -i -X POST http://localhost:3000/api/tokens -H "Content-Type: application/json" -d "{\"name\": \"d(username)\",\"pas
sword\": \"tau(password)\"}"