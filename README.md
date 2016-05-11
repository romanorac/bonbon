#bonbon
bonbon is a minimalistic backend, ready to deploy to App Engine. It's powered by Guice, RESTEasy, Objectify, Jackson and Java 8.

bonbon shows:

- dependency injection with Guice,
- basic CRUD operations with Objectify,
- basic authentication,
- working with request contexts,
- role based authorization and exception wrapping with RESTEasy,

I would recommend you to use Intellij EAP as it imports project automatically and is free to use for 30 days. Note that https typically is used with Basic Authentication.
When you are ready, start a server and make a few api calls as described bellow.

## Api calls

1. Check server health:
    ```bash
    curl -i http://localhost:8080/rest/health
    ```

2. Register a user and create a sesssion:
    ```bash
    curl -i -H "Content-Type: application/json" -X POST -d '{"email":"some.user@bonbon.com","password":"pwd_hash", "name":"some", "lastname":"user"}' http://localhost:8080/rest/user/register
    ```

3. Login and create or take an existing session:
    ```bash
    curl -i -H "Content-Type: application/json" -X POST -d '{"email":"some.user@bonbon.com","password":"pwd_hash"}' http://localhost:8080/rest/user/login
    ```

4. Read user entity from database. Insert a token from login response:
    ```bash
    curl -i -H "Authorization: Basic <token>" http://localhost:8080/rest/content/info
    ```

5. Logout and remove a session. Insert a token from login response:
    ```bash
    curl -i -H "Content-Type: application/json" -H "Authorization: Basic <token>" -X POST -d '{}'  http://localhost:8080/rest/user/logout
    ```

6. Register admin and create a session. Admin email is hardcoded in UserServiceImpl:
    ```bash
    curl -i -H "Content-Type: application/json" -X POST -d '{"email":"roman.orac@bonbon.com","password":"pwd_hash", "name":"roman", "lastname":"orac"}' http://localhost:8080/rest/user/register
    ```

7. List all users with admin. Insert a token from register response:
    ```bash
    curl -i -H "Authorization: Basic <admin_token>" http://localhost:8080/rest/admin/users
    ```

8. List all users with a user. Insert a token from user's login response. You receive 403 Forbidden response:
    ```bash
    curl -i -H "Authorization: Basic <user_token>" http://localhost:8080/rest/admin/users
    ```

9. Remove a user with admin:
    ```bash
    curl -i -H "Content-Type: application/json" -H "Authorization: Basic <admin_token>" -X POST -d '{"email":"some.user@bonbon.com"}' http://localhost:8080/rest/admin/users/delete
    ```

10. User cannot login as it was deleted, response is 404 Not Found:
    ```bash
    curl -i -H "Content-Type: application/json" -X POST -d '{"email":"some.user@bonbon.com","password":"pwd_hash"}' http://localhost:8080/rest/user/login
    ```
