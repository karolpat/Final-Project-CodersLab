# RoomBooking Service

Simple web project made on purpose to accomplish Java course in CodersLab. In this project Spring Boot, thymeleaf and Bootstrap template are used. Main feature is based on possibility of creating an account for users and booking a room in a hotel or apartment. All offers are added by registered users.

Note: in this project a free bootstrap templete is used. The templete belongs to [bootstrapious](https://bootstrapious.com).


## Getting Started

Main features:
* Creating new user (user role) account using unique username and email. All user's credentials as well as encrypted password are stored in database.
* Giving user an opportunity to search for the room by room's location, room's capacity and in exact period of time.
* Only enabled users are allowed to book a room.
* User become allowed after sending request to admin (admin role) and accepting this request by admin.
* User could become an Owner (owner role) and create offers of his rooms to be booked. To do so, user sends request to the admin.
* User could become a Hotel Manager (manager role) and create offers of his hotels with rooms to be booked. To do so, user sends request to the admin.
* Admin pannel to control users' requests. There is a list of all current requests. There are two options: accept or reject the request. Before that, admin has possibility to check all personal details of the user.
* Showing room's price in three currencies: USD, EUR and GBP. Price is auto-calculated using NBP (National Polish Bank) API with up-to-date data.
* Possibility of sending messages between users.

### Prerequisites

This project uses Spring Boot, Hibernate, Maven and MySQL and Thymeleaf as a base for views. Changing application.properties you can set the server port that is by default
```
5555
```
and database is named
```
final
```
Before launching the project please fill the database with table as follow:
```
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `sub_name` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`));

INSERT INTO `role` VALUES (1,'ROLE_USER','User'),(2,'ROLE_ADMIN','Admin'),(3,'ROLE_MANAGER','Manager'),(4,'ROLE_OWNER','Owner');
```

### Installing

After all above steps are done, run this project as Spring Boot App. (Eclipse - STS is used in my case).


### TODO
 
* Reset user's password.
* Send emails to user.
* Enable to add more than one picture.
* Add valiadation to forms.
* Promote offers.

## Authors

* **Karol Patecki** as **karolpat** 

As mentioned above, a free bootstrap template is used in this project.


