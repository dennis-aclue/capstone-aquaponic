Welcome to my capstone project from the "neuefische" bootcamp.
This is a first short introduction to start the  aquaponic application on your localhost.

For the initial project, with backend, frontend on localhost
1. Install Docker Desktop and create a new MongoDb container
2. Install MongoDb Compass and connect to Docker 
3. Create the database "aquaponic"
4. Fork the project and update Maven if necessary
5. Maybe you have to install inside frontend folder
   - npm install axios
   - npm install --save react-router-dom

MongoDb name:   
If you want to change the database name, go inside "backend/src/main/resources" and change the name inside the aquaponic application.properties
spring.data.mongodb.database=aquaponic

Sonar Cloud files:
Backend:
.github/workflows/build-sonar-backend.yml
Frontend:
.github/workflows/build-sonar-frontend.yml

Here you can see my first draw of the project principles:
![Aquaponic capstone project](https://github.com/dennis-aclue/capstone-aquaponic/main/Aquaponic-capstone-drawing.png?raw=true)
![](Aquaponic-capstone-drawing.png)
Here you can find the Excalidraw raw file:
![Aquaponic capstone project Excalidraw file](https://github.com/dennis-aclue/capstone-aquaponic/main/Aquaponic-capstone-file.excalidraw)

