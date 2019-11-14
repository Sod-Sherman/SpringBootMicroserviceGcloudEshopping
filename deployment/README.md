# Deployment 
Mini Project at Enterprise Architecture course, MUM.EDU. 
 > Deployment Pre-request:
- Google Cloud Kubernetes (Standard trial version would be OK)
- Read the instructions (steps) carefully
- Ask if any trouble
       
The deployment has several steps:

#### Step 1
Open Google Cloud shell and
clone the source code from GitHub link:
 [GitHub](https://github.com/Rustem-bayetov/ea2)

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
you can use [this IP address](http://34.102.164.129/users/) after deploying all
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
ea2-ingress   *       34.102.164.129   80      161m
```
Now we can open any web browsers or postman to check functionality
of our application.

- example: 
    - http://34.102.164.129/orders/ 
     
      --> can be point to our orders-service inside k8s
      
Result screenshot:

![alt Result screen shot](https://github.com/Rustem-bayetov/ea2/blob/master/deployment/screenshot.png)

##### Thank you!
