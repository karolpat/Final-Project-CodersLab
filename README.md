# RoomBooking Service

Simple web project made on purpose to accomplish Java course in CodersLab. In this project Spring Boot, thymeleaf and Bootstrap template are used. Main feature is based on possibility of creating an account for users and book a room in a hotel or apartment. All offers are added by registered users.

Note: in this project a free bootstrap templete is used. The templete belongs to https://bootstrapious.com.


## Getting Started

Main features:
* Create new user (user role) account using unique username and email. All user's credentials as well as encrypted password are stored in datbase.
* Give user an opportunity to search for the room by room's location, room's capacity and in exact period of time.
* Only enabled users are allowed to book a room.
* User become allowed after sending request to admin (admin role) and accepting this request by admin.
* User could become an Owner (owner role) and create offers of his rooms to be booked. To do so, user sends request to the admin.
* User could become a Hotel Manager (manager role) and create offers of his hotels with rooms to be booked. To do so, user sends request to the admin.
* Admin pannel to control users' requests. There is a list of all current requests. There are two options: accept or reject the request. Before that, admin has possibility to check all personal details of the user.
* Show room's price in three currencies: USD, EUR and GBP. Price is auto-calculated using NBP (National Polish Bank) API with up-to-date data.
* Possibility of sending messages between users.

### Prerequisites

This project uses Spring Boot, Maven and MySQL. Changing application.properties you can set the server port that is 
```
5555
```
by default and database is named
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

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc

