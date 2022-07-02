# Social Media API

A Social Media API, developed with:

- Java 17
- Maven
- Docker
- Docker Compose
- MongoDB

## Features

### Homepage

- The homepage, by default, will show a feed of all posts (including reposts and quote posts).
- There is a toggle switch "All / following" that allows you to switch between seeing all posts and just posts by those you follower.
- New posts can be written from this page.

### User profile page

- Shows data about the user:
    - Username
    - Date joined the social media, formatted as such: "March 25, 2021"
    - Number of followers
    - Number following
    - Count of number of posts the user has made (including reposts and quote posts)
- Shows a feed of all posts the user has made (including reposts and quote posts)
- Shows whether you follower the user or not
- Follow/unfollow actions:
  - You can follower the user by clicking "Follow" on their profile
  - You can unfollow the user by clicking "Unfollow" on their profile
- New posts can be written from this page

## Instructions to Execute the Project

Run the following command in the root of the project. This command will set up the database used by the social media.

```
docker-compose up -d --build
```

In the root of the application, execute the following command to download all dependencies:

```
mvn clean install
```

In the root of the application, you can execute it with the following command:

```
mvn spring-boot:run
```

In the root of the application, you can execute its tests with the following command:

```
mvn test
```

## Author

Vitor Vidal - More about me [here](https://github.com/vitorvidaldev).

## License

This project uses [the MIT license](LICENSE).