Welcome to my capstone project from the "neuefische" bootcamp.
This is a first short introduction to start the  aquaponic application on your localhost.

For the initial project, with backend, frontend on localhost
1. Install Docker Desktop and create a new MongoDb container
2. Install MongoDb Compass and connect to Docker 
3. Create the database "aquaponic"
4. Fork the project and update Maven if necessary
5. Navigate to frontend folder and run "npm install".
   It will install (axios, react-router-dom, react-validation

MongoDb name:   
If you want to change the database name, go inside "backend/src/main/resources" and change the name inside the aquaponic application.properties
spring.data.mongodb.database=aquaponic

Sonar Cloud files:
Backend:
.github/workflows/build-sonar-backend.yml
Frontend:
.github/workflows/build-sonar-frontend.yml

Here you can see my first draw of the project principles:
![](Aquaponic-capstone-drawing.png)

If you close the IDE during running backend and did not close properly, try this on mac:
~ % lsof -n -i4TCP:8080
~ % kill -9 PID

Tomcat installation procedure:
https://github.com/bartfastiel/spring-boot-tomcat-deployment/blob/main/provisioning.sh

FOR AWS setup:
You do not need to insert iptables then:
(

# redirect port 8080 to port 80 (both locally and for remote requests)

sudo iptables -t nat -p tcp --dport 80 -j REDIRECT --to-ports 8080 -I OUTPUT -d 127.0.0.1
sudo iptables -t nat -p tcp --dport 80 -j REDIRECT --to-ports 8080 -I PREROUTING
)

Tomcat configuration on AWS:

change Tomcat port to 80 inside

- /opt/webserver/apache-tomcat-9.0.70/conf/server.xml

  <Connector port="80" protocol="HTTP/1.1"
  connectionTimeout="20000"
  redirectPort="443" />

add .bash_profile constants under export PATH (ec2-user path and maybe root path also):
export MONGODB_URI={your mongodb uri}
export EMAIL_SECRET={your email secret}
export JAVA_HOME=/opt/webserver/jdk-19




