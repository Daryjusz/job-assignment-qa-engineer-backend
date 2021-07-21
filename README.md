# Assignment for Backend QA Engineer job applicants

## Background

Your task is to test a simple CRUD application. The application is based on [Conduit, "The mother of all demo apps"](https://github.com/gothinkster/realworld), which is a simple clone of [Medium.com](https://medium.com/).

First, we would like you to create a series of manual test cases that  would cover the basic operations.
We do not expect 100% coverage, but please use the opportunity to showcase the skills you have.
<br>
Then, please decide which of the manual test cases should be included in an automated test suite and write scripts that would execute them.


You will find OpenAPI Specification (Swagger 2.0) in `docs/schema/swagger.json`.

## Business requirements

Each manual test case should include detailed instructions like:

* Test ID
* Summary
* Description (if you think Summary is not descriptive enough)
* Preconditions
* Steps
* Expected result

Please go through test cases and provide the test execution report with the following fields (in addition to the above):

* Status (either Pass or Fail)
* Actual result (when Status is Fail)

## Technical requirements

Please clone this repository and make it public.

You can use the test automation framework of your choice and you are free to use any of the below programming languages for your scripts:

* Java
* JavaScript
* Python
* Ruby


For your convenience, we have included application to test as a Docker image which you can run together with database using [Docker Compose](https://docs.docker.com/compose/).

You can run the application to test by executing:

    docker-compose up

You will also need to initialise the database (first time you use it or any time you want to reset it to initial state), to do so run:

    docker-compose run --rm api npm run db:reset

To clean up the application completely and start over, run:

    docker-compose down --remove-orphans

The test scripts you create should also run inside a [Docker](https://www.docker.com/) container, as it will greatly improve the ease of setup and test execution when we review your solution. For your convenience we have provided very basic example of a `Dockerfile`.

This is how we should be able to run the tests:

    docker build -t job-assignment-qa-engineer-backend .
    docker run --rm --network host job-assignment-qa-engineer-backend

Running the above in fresh clone of this repository should result in "Success" message being printed.

## Tips

The goal of this task is to provide a discussion context for the subsequent technical interview and is not meant to be time consuming.
Although it is intended for all levels of QA Engineers, we expect more attention to detail the more experienced you are.

Using solutions recommended by the test automation framework you decided to use is preferred.
You can emphasise on certain aspects of the task to showcase your skills, either through cleverly using framework built in mechanisms or leaving TODO notes for more advanced solutions that come to your mind or when taking shortcuts to reduce solution time.

The time to complete this project will depend on your expertise, but based on our own employees executing this task, we estimate that it should not take more than 4-8 hours depending on your proficiency.
We understand and honor that you have a life outside work so we recommend that you do not exceed the above mentioned limit.

## Evaluation criteria

There are certain aspects that will be considered when evaluating your solution:

* expertise in defining testing strategy
* expertise in using testing frameworks
* reliability of tests
* variety of tests over test coverage
* ease of setup
* ease of executing test suites
* code quality
