Rest api endpoints to test workability:

1) Hit the url: http://localhost:8080/oauth/token?grant_type=password&username=user&password=user to get access token
for user with role regular USER. Method POST
Response example:
    {
       "access_token": "59df7645-c90a-4a1d-a795-9bf8c8530f39",
       "token_type": "bearer",
       "expires_in": 4999,
       "scope": "read write"
    }

2) Add customer to embeded h2 database using endpoint:
http://localhost:8080/customers/add?access_token=59df7645-c90a-4a1d-a795-9bf8c8530f39 with method POST
and payload like:
    {"name":"John",
     "phone":"0631122327",
     "address":"LA"
    }

3) Get list of existing customers by hitting the endoint:

http://localhost:8080/customers/findall?access_token=59df7645-c90a-4a1d-a795-9bf8c8530f39. Method GET.

4) Find customer by id using endpoint:
http://localhost:8080/customers/findbyid?id=3&access_token=59df7645-c90a-4a1d-a795-9bf8c8530f39. Method GET.

5) Find customer by name using endpoint:
http://localhost:8080/customers/findbyname?name=John&access_token=59df7645-c90a-4a1d-a795-9bf8c8530f39 Method GET.

Also if you get access token for user role ADMIN using endpoint:
http://localhost:8080/oauth/token?grant_type=password&username=admin&password=admin Method POST.
{
    "access_token": "c33af380-fc77-4e3f-9ce6-1c81d4ff1cb8",
    "token_type": "bearer",
    "expires_in": 4070,
    "scope": "read write"
}

Then you will get an ability to update some of the customer's fields by hitting the api endpoint:
http://localhost:8080/customers_edit/update?access_token=c33af380-fc77-4e3f-9ce6-1c81d4ff1cb8 Method PUT.

Payload example: {
                  "id":1,
                  "name":"Alex",
                  "address":"New York"
                 }
NOTE:
    User's access code doesn't have any privileges to edit (modify) customers. This option is only
    available for ADMIN role.

    BTW: You have a fantastic option to keep track on what's going on in the database using h2 console by
    hitting an api endpoint:

    http://localhost:8080/h2_console

and pressing button: Connect for provided credentials which are stored in application.properties.