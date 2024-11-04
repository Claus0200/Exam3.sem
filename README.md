# Exam backend fall 2024

## Tasks

### 3.3.2 - Test the endpoints using a dev.http file.

#### GET http://localhost:7070/api/trips/

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:12:16 GMT
Content-Type: application/json
Content-Length: 703

[
{
"id": 1,
"starttime": [
2024,
1,
12
],
"endtime": [
2024,
1,
19
],
"longitude": 75.34,
"latitude": 12.43,
"name": "Mountain Adventure",
"price": 200.0,
"category": "sea",
"guide": {
"id": 1,
"firstname": "John",
"lastname": "Doe",
"email": "john.doe@example.com",
"phone": "1234567890",
"yearsOfExperience": 5
}, ...
]

#### GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:13:46 GMT
Content-Type: application/json
Content-Length: 275

{
"id": 1,
"starttime": [
2024,
1,
12
],
"endtime": [
2024,
1,
19
],
"longitude": 75.34,
"latitude": 12.43,
"name": "Mountain Adventure",
"price": 200.0,
"category": "sea",
"guide": {
"id": 1,
"firstname": "John",
"lastname": "Doe",
"email": "john.doe@example.com",
"phone": "1234567890",
"yearsOfExperience": 5
}
}

#### POST http://localhost:7070/api/trips/

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 09:22:41 GMT
Content-Type: application/json
Content-Length: 148

{
"id": 5,
"starttime": [
2024,
1,
12
],
"endtime": [
2024,
1,
19
],
"longitude": 12.43,
"latitude": 75.34,
"name": "Mountain Adventure",
"price": 200.0,
"category": "sea"
}

#### PUT http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:26:06 GMT
Content-Type: application/json
Content-Length: 156

{
"id": 1,
"starttime": [
2024,
1,
12
],
"endtime": [
2024,
1,
19
],
"longitude": 75.34,
"latitude": 12.43,
"name": "Updated Mountain Adventure",
"price": 250.0,
"category": "sea"
}

#### DELETE http://localhost:7070/api/trips/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 09:35:19 GMT
Content-Type: application/json

Response body is empty


#### PUT http://localhost:7070/api/trips/3/guides/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 09:34:33 GMT
Content-Type: text/plain

Response body is empty


### 3.3.5 - Theoretical question: Why do we suggest a PUT method for adding a guide to a trip instead of a POST method?

Siden vi ikke behøver at skrive en ny guide, da begge entiteter er forbundet med hinanden, så kan vi bare opdatere den ene, og så bruge em.merge() for at gemme det nye data.
Desuden vil vi gerne sikre os at guiden kun bliver til føjet til "trip" en gang med PUT, og med POST kan man gøre det gentagne gange.
