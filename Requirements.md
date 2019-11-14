## Instructions

### Goals:
- +Given a problem you should be able to model a set of Microservices
- +Apply Service discovery , Configuration and secret management
- +Deploy and scale your solution on a cluster

### Problem statement:
We’ve an e-store business, which allows users to view our items , create an account if they want to buy. Place orders and pay for them.


### We’ve the following concepts:
- Account :
    - +first name , last name , email
    - +Shipping Address
    - +A list of Payment method (PayPal |CreditCard| BankAccount)
    - +A preferred payment method
    - +Each payment method has its attributes (e.g. CC we need number, ccv, ..)
- Product:
    - +Name, vendor, category (e.g. electronics|Apparel|Food,..)
    - +We need to keep track of available Units. This is to prevent users from placing orders on out of stock items
        - +Whenever an item count falls below (some configurable threshold e.g. 50 ) -> we notify Stock back end so they can add more units to our stock
    
- Order
    - +User can place orders , each order can have one or more products
        - +User can specify more than one unit of an item (e.g. 3 T-shirts)
    - +An Order can use the preferred shipping info of the account, or can be different if the user wants to
    - +Similarly the order must have a payment method , which could be the preferred type or different if the user wants to
        - +Only one payment method is allowed per order

### Overall businesses flow:
- +Any user can see our catalog , 
- +but to place and order a user has to make an account
- +User can login and then place an order
- +The order goes to payment
- +if payment was accepted, the order is sent to shipping department

A: OrderService

Create Order -> talk to payment service. Payment service doesn't know upfront who to talk to, it's determined by the payment type from the request

B: PaymentService
- +PaymentType is variable
- +Based on payment type locate some Transaction Service
- +This mapping is dynamic and should be fetched via config management
- +Payment service talks any transaction Service via an API token
- +the token is secured and should be retrieved via Secret management


### Tasks:
- Architecture a micro-services based solution to this problem.
    - Come up with a valid set of micro-services.
    - Define their APIs and their communication pattern
    - Decide which discovery/Config/Secret management approach you’ll use
- Hints:
    - +At a minimum you need 
        - +Authentication, 
        - +Account tracking, 
        - +Order tracking , 
        - +Payment , 
        - +several Transactional services one for each payment type , 
        - stock tracking , 
        - +and shipping services.
    - +For simplicity, use Restful style , but you’re free to change
    - +For simplicity, use Spring Boot,cloud framework , but feel free to change
    - +For simplicity use MySQL for your databases , change if you want
    - +Transaction service accepts just a set of keys/values
- All you services must run in containers ( including your data bases )
- All you services must run more than 1 instance (2 minimum) for scaling
- No hard coded IPs ever in your system, use service discovery
- Between Order service and Payment service use Discovery
    - +Between Payment and Transaction service , use the request attributes
        - +This allows us to add more payment methods In the future  
    - Use config management to locate service name from payment type
    - Use discovery to talk to transaction service
- Run your stack on Kubernetes. For this project using Minikube is acceptable, but later you might need a multi-node cluster.

### Deliverable:
- Your project source code for all services
- UI is NOT required, sample Restful requests is enough
- README.md file
- Explain your design decisions
- Explain how to run your system (Provide scripts to deploy your services to Kubernetes whenever possible)