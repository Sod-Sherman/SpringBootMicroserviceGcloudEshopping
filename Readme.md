# Deployment 
Mini Project at Enterprise Architecture course, MUM.EDU. 
 > Deployment Pre-request:
- Google Cloud Kubernetes (Standard trial version would be OK)
- Read the instructions (steps) carefully
- Ask if any trouble
(Automated deployment in the deployment folder!)       
The deployment has several steps:


## Overview
This *CS 544 - Enterprise Architecture assignment #2* was crafted by Rustem Bayetov 610133 and Sodbileg Shirmen 610170.  

We implemented web store where users can see catalog (products list), register, authenticate, place order and pay.

During this assignment we created 9 microservices, tested and deployed to Google Cloud Platform. The project is fully consistent with the assignment requirements.  

In this project we used:
- Docker to build and containerize our services
- Kubernetes to run containerised images 
- OpenFeign to discover services
- Kubernetes Configuration to manage secrets and configurations
- Spring security to authenticate users
- MySql as container

Extra:
- We configured pulling, compiling and deploying services from GitHub

## Prerequisites
- Have a good mood to score this work
- Our source code
- Good machine with minikube or Google cloud platform (or other Kubernetes engine powered platform)

## Run project


#### Step 1
Open Google Cloud shell and
clone the source code from GitHub link:
 [GitHub](https://github.com/Rustem-bayetov/ea2) (ask us about accessing to git repository or use our provided source code)

#### Step 2
Run following command:
```
$ cd ea2/deployment
```
#### Step 3
Push all docker images for all microservices. It would 
compile source codes and push to Docker Hub, automatically.

```
$ make docker-push-all  
```

#### Step 4
Create static public IP address for gateway! If have problem 
you can use [this IP address](http://34.102.228.59/products/list) after deploying all
services including ingress gateway.  
```
$ make apply-static-IP
```
#### Step 5
Deploy docker images to pods in Kubernetes cluster.
```
$ make kubectl-apply-all-services 
```
It should be apply all pods and services with secrets which
will be used to connect MySQL DB and running arguments for Spring 
Boot Rest Application (services).

#### Step 6
Wait for mysql service runnig and then execute following 
command to create schema databases for running Spring Boot App.
```
$ make kubectl-create-init-db:
```

#### Step 7
Apply for Ingress Proxy using following command.

```shell script
$ make kubectl-apply-ingress
```

#### Step last (checking services working)
The ingress proxy should be delayd for creating related mappings
for front end services. Check this status following command:
```shell script
$ kubectl get ingress

NAME          HOSTS   ADDRESS          PORTS   AGE
ea2-ingress   *       34.102.228.59   80      161m
```
Now we can open any web browsers or postman to check functionality
of our application.

- example: 
    - http://34.102.228.59/products/list 
     
      --> can be point to our orders-service inside k8s
      
Result screenshot:

![alt Result screen shot](https://github.com/Rustem-bayetov/ea2/blob/master/deployment/screenshot.png)

### Another way:
 
### Run project on kubernetes from images 
- apply `config/global-secrets.yaml` file to configure secrets
- run mysql. apply `mysql/deployment.yaml`
- run all services. Go to each service folder and apply `manifests/deployment.yaml` and `manifests/service.yaml` files.

### Run project on kubernetes from sources
To run project on kubernetes you need:
- go to each service and edit `application.properties` file. Uncomment kubernetes settings and comment out localhost settings.
- in each service go to the `interfaces` package and check if there is `FeignClient` annotation exists. Comment out `url` property.
- rebuild all packages and push to hub.docker.com (use `make docker-push` command located each service folder). But you will need access to my `rustembayetov` account repositories. If you wanna push to your account change it in `makefile` file.
- after that follow `Run project on kubernetes from images` section (above)

### Run project on localhost
To run project on localhost you need:
- go to each service and edit `application.properties` file. Uncomment localhost settings and comment out kubernetes settings.
- run mysql database according on `application.poperties` file settings (user: sa, password: password).
- in each service go to the `interfaces` package and check if there is `FeignClient` annotation exists. Uncomment `url` property.

## Usage
> Use our Postman predefined requests collection https://www.getpostman.com/collections/35129f327bae6e52ad6b

1. See products catalog. Navigate to `http://localhost:8085/products/list` (if you run on localhost) or `http://34.102.228.59/products/list` (if you run from kubernetes)
2. Register new user. Navigate to `localhost:8081/auth/register` or `http://34.102.228.59/auth/register`
    
    Payload:  
    ```
    {
        "firstName": "Rustem",
        "lastName": "Bayetov",
        "email": "rustem.bayetov@gmail.com",
        "password": "1234",
    
        "adrStreet": "1000 N 4th St",
        "adrCity": "Fairfield",
        "adrState": "IA",
        "adrZip": "52557",
            
        "paymentCreditCard": {
            "type": "cc",
            "cardNumber": "1234567812345678",
            "holderName": "rustem bayetov",
            "cvc": "123",
            "bankAccountNumber": "",
            "payPalUsername": ""
        },
        "paymentBankAccount": {
            "type": "ba",
            "cardNumber": "",
            "holderName": "",
            "cvc": "",
            "bankAccountNumber": "12341234",
            "payPalUsername": ""
        },
        "paymentPayPal": {
            "type": "pp",
            "cardNumber": "",
            "holderName": "",
            "cvc": "",
            "bankAccountNumber": "",
            "payPalUsername": "rustem.bayetov"
        },
        "preferredPaymentMethod": "cc"
    }
    ```
3. Sign in. Navigate to `localhost:8081/auth/sign-in` or `http://34.102.228.59/auth/sign-in`

    Payload:  
    ```
    {
        "email": "rustem.bayetov@gmail.com",
        "password": "1234"
    }
    ```
   Grab given JWT token and use it for further calls. Use it as Bearer token in your requests.
   
4. Add products to catalog.  Navigate to `http://localhost:8085/products/add` or `http://34.102.228.59/products/products/add`

    Payload:
    ```
    {
        "name":"iPhone 11",
        "vendor": "Apple",
        "category":"Mobile phones",
        "availableCount":10,
        "price":800
    }
    ```

5. Place an order. Navigate to `http://localhost:8086/orders/place-order` or `http://34.102.228.59/orders/place-order`
    Payload:
    ```
    {
        "userId":1,
        
        "usePreferredAddress": true,
        "adrStreet":"",
        "adrCity":"",
        "adrState": "",
        "adrZip":"",
        "usePreferredPaymentMethod": true,
        "payType":"ba",
        "payCardNumber":"",
        "payHolderName":"",
        "payCvc":"",
        "payPayPalUsername":"",
        "items": [
            {
                "productId": 1,
                "quantity": 2
            }
        ]
    }
    ```

## How it's work?
When you placing order: 
1. `orders-service` check that user has valid JWT
2. `orders-service` check passed data.
3. `orders-service` retrieve user data from `auth-service`
    1. `auth-service` check service secret
    2. `auth-service` get user data from `mysql` database
    2. `auth-service` return user data
4. `orders-service` check retrieved user data
5. `orders-service` retrieve all products data from `products-service`
6. `orders-service` check all products available count 
7. `orders-service` asks `products-service` to decrease products count
8. `orders-service` determining user payment info (get from request or from user predefined data)
9. `orders-service` asks `pays-service` to make an payment
    1.  `pays-service` based on payment method (Credit card `cc`, Bank Account `ba`, PayPal `pp`) calls on of 3 services:
        1. `pays-cc-service` to make payment using Credit card
        2. `pays-ba-service` to make payment using Bank Account
        3. `pays-pp-service` to make payment using PayPal
10. `orders-service` saving order to database
11. `orders-service` notifying `ship-service` about placed order
12. `orders-service` return *Saved successfully* message

PS:
- every service-to-service communication requires to use ServiceSecret.
- `auth-service` has his own schema `users` in database
- `products-service` has his own schema `products` in database
- `orders-service` has his own schema `orders` in database    

## WHY we decided design our project in this way?
- We decided to split databases by services because it gives us opportunity to increase project horizontally
- We split payment payments methods to different services because evert of them have to deal with 3rd party services (eg. Banks, PayPal). Some 3rd party endpoint can have different security requirements. For example: keep our endpoint isolated in network and physically.
- We decided keep all user authorization, sign in functionality in single service because this functions are coupled by logic. Also there are no huge calculation so this projects can live long until redesign.
- We decided use meSQL database because for this assignment requirements it is easy, convenient and enough powerful choice.
- We tested Rest template and OpenFeign service discovery methods and selected second one because it easier to use and it comes from Netflix.
- Unfortunately, we had no much time to compare and understand other techniques.          

## Endpoints
### Client without authentication can access this endpoints
- To get products list (catalog) `GET products-service/products/list`
- To get product by id `GET products-service/products/get/{id}`

### Authentication (spring boot security auth using JWT) need to access this endpoints
- To add product `POST products-service/products/add`. Put data in body.
- To delete product by id `DELETE products-service/products/delete/{id}`

### Service to service communication endpoints (Using token)

#### Pays general service:
*Service secret required*

On kubernetes: `pays-service/pays_pp/pay`

On localhost: `http://localhost:8090/pays/pay`

Payload:  
```
{
    "type":"cc",
    "cardNumber":"1234567890123456",
    "holderName":"Rustem",
    "cvc":"123",
    "bankAccountNumber": "12345678",
    "payPalUsername":"rustem.bayetov"
}
```

#### Pays using credit card service:
*Service secret required*

On kubernetes: `pays-cc-service/pays_cc/pay`

On localhost: `http://localhost:8091/pays_cc/pay`

Payload:  
```
{
    "type": "cc",
    "cardNumber": "1234567890123456",
    "holderName": "Buus",
    "cvc": "123",
    "bankAccountNumber": "",
    "payPalUsername": ""
}
```

#### Pays using bank account service:
*Service secret required*

On kubernetes: `pays-ba-service/pays_ba/pay`

On localhost: `http://localhost:8092/pays_ba/pay`

Payload:  
```
{
    "type":"ba",
    "cardNumber":"",
    "holderName":"",
    "cvc":"",
    "bankAccountNumber": "12345678",
    "payPalUsername":""
}
```

#### Pays using pay pal service:
*Service secret required*

On kubernetes: `pays-pp-service/pays_pp/pay`

On localhost: `http://localhost:8093/pays_pp/pay`

Payload:  
```
{
    "type": "pp",
    "cardNumber": "",
    "holderName": "",
    "cvc": "",
    "bankAccountNumber": "",
    "payPalUsername": "rustem.bayetov"
}
```
