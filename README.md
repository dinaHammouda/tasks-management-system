# Tasks Managmenet Demo

To start the project:
1. Modify database configuration and add the correct username and password in application.properties file.
2. Start the server and the tables will get created.
3. Database export files can be found in  [/sql_scripts/lookups_date.sql](/sql_scripts/lookups_date.sql).

 
  
Authentication and Authorization:
1.  Signup endpoint create user as ADMIN or USER.

POST http://localhost:8090/auth/signup
```
{
  "email":"user3@gmail.com",
  "password":"password",
  "roleId":1
}  
```
2. Login endpoint to generate jwt token, to be added to the authorization header. Note that some endpoints are only accessed by admin and other by only users.

POST http://localhost:8090/auth/login
```
{
  "email":"user3@gmail.com",
  "password":"password"
} 
```
CRUD endpoints:

The following endpoints can only be accessed by USER, it requires token to be added in header Authorization Bearer 'token'
Login, copy generated token

1. To create new task only can be accessed by 'USER' role :

Add header Authorization  
POST http://localhost:8090/tasks

Reuqest sample :
```
POST /tasks HTTP/1.1
Host: localhost:8090
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWd1bGFyLXVzZXJAZ21haWwuY29tIiwiaWF0IjoxNzIwODI3NjM5LCJleHAiOjE3MjA4MzEyMzl9.pZyodDypNaq-eihB9ZlCf6d4-oGczhKARpdExOftQkU
Cookie: JSESSIONID=4B7C29A5387CB3071353B20785021BAE
Content-Length: 160

{

     "title": "testing  post new task ",
    "description" : "Create new task",
   "status": 3,
    "priority":1,
    "dueDate":"2024-07-10"
 
}
```

2. Get all tasks for user or filter 

Add header Authorization  

GET http://localhost:8090/tasks/search?title='{title}'&description='{description}'&priorityId='{priorityId}'

Request Sample

```
GET /tasks/search?title=Complete Report&description=Finish the monthly report&priorityId=2 HTTP/1.1
Host: localhost:8090
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWd1bGFyLXVzZXJAZ21haWwuY29tIiwiaWF0IjoxNzIwODI3NjM5LCJleHAiOjE3MjA4MzEyMzl9.pZyodDypNaq-eihB9ZlCf6d4-oGczhKARpdExOftQkU
Cookie: JSESSIONID=4B7C29A5387CB3071353B20785021BAE
Content-Length: 160

{

     "title": "testing  post new task ",
    "description" : "Create new task",
   "status": 3,
    "priority":1,
    "dueDate":"2024-07-10"
 
}
```

3. Update task:

PUT http://localhost:8090/tasks/${taskId}

Request Sample:
```
PUT /tasks/1 HTTP/1.1
Host: localhost:8090
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWd1bGFyLXVzZXJAZ21haWwuY29tIiwiaWF0IjoxNzIwODI3NjM5LCJleHAiOjE3MjA4MzEyMzl9.pZyodDypNaq-eihB9ZlCf6d4-oGczhKARpdExOftQkU
Cookie: JSESSIONID=4B7C29A5387CB3071353B20785021BAE
Content-Length: 152

{

     "title": "testing update task ",
    "description" : "Create new task",
   "status": 2,
    "priority":1,
    "dueDate":"2024-07-10"
 
}
```

4. Delete Task:

DEL http://localhost:8090/tasks/${taskId}

Request Sample
```
DELETE /tasks/1 HTTP/1.1
Host: localhost:8090
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWd1bGFyLXVzZXJAZ21haWwuY29tIiwiaWF0IjoxNzIwODI3NjM5LCJleHAiOjE3MjA4MzEyMzl9.pZyodDypNaq-eihB9ZlCf6d4-oGczhKARpdExOftQkU
Cookie: JSESSIONID=4B7C29A5387CB3071353B20785021BAE

```

The Following endpoint only accessed by admin

1. GET all tasks

GET http://localhost:8090/tasks/dashboard
```
GET /tasks/dashboard HTTP/1.1
Host: localhost:8090
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE3MjA4Mjg3MzksImV4cCI6MTcyMDgzMjMzOX0.GBRpRCwVK2IlHrDEViq4gWxmZOa84k67JdBfLUYTSAM
Cookie: JSESSIONID=4B7C29A5387CB3071353B20785021BAE
```

Email Notification:

The email and password can be configured in application.properties

There's a schedular set to retrieve all the tasks due date and send remainder to the users. Note: the service for sending the email is commented.


