ROULETTE

Description: This project was made for academic purpose. Simple Client - Server roulette game in Java, based on java socket. Included Hibernate for handling db operations and maven for wrapping modules

The project includes Server and Client module

SERVER

Server: Main class, creates threads for connected Clients

ClientHandler: Handling cases, listening/sending reqests/responses

Models: Class package for db entities classes made with Hibernate

Dao: Class package for CRUD classes for each model

Requests: Class package for user's requests

Responses: Class package for server's responses

Services: Class package for handling logic of the request/response

Util: Package for supporting classes such as DB connection

Resources: Hibernate settings and dummy db

CLIENT:

Launcher: Starts app

Controllers: Class package for different stages

Requests/Responses: Same as in Server's case

Services: Same as in Server's case

Util: Includes SceneManager, ClientSocket, WindowController and variable holders

Resources: Package with images, style and fxmls