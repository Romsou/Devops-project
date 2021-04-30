![GitHub pull requests](https://img.shields.io/github/issues-pr/Romsou/Devops-project)

# Devops project

## Documentation

We created a website thanks to github to host the javadoc of our projet. You may 
find it here: 

- https://romsou.github.io/Devops-project/

## Usage

We created a docker file and used maven and intelliJ to build the project. The following 
set of commands will help you to compile and test the project, and create the docker
image:

```
# compile
mvn compile

# test
mvn test

# create docker image
sudo docker build -t my-maven .

# Run the image
sudo docker run -it --rm my-maven
```

## Features

Our library offers several features, among which:

- Implementation of Series and Dataframe akin to pandas' ones.

- Method to compute the sum of a column.

- Method to compute the minimal and maximal numbers of a column for numeric types.

- Method to compute the mean of a column for numeric types.

- Method to compute print the content of a Serie and of a DataFrame.

- Method to select desired lines or columns.

## Tools chosen

- Code hosting: Github

- Unit test: JUnit 5.7.1

- Build automation: Maven 3.6.3

- Java version: jdk-11

- Code coverage: Jacoco 0.8.6

## Git workflow

We chose to use the gitflow workflow and adapt it to the scope of the project. Since we only have one actual release, we decided to avoid using a release branch and create two first branches: **dev**, the development branch from which we fork our feature branches, and **hotfix** to create quick fixes on the code.

We then create one **feature** branch from **dev** for each feature we want to implement. Once the project is complete, we merge the development branch in the master branch.

We chose the following nomenclature for the project:
- For the main branch: **master**
- For the development branch: **dev**
- For feature branches: **\<feature name\>**-feature
- For the hotfix branch: **hotfix**
- For the test branches: **\<feature name\>**-test

For more security, we define the **dev** branch as the default branch for pull requests, and we define rules to protect branches by forcing the review of code before each merge into the **dev** or the **master** branch.
Moreover we spontaneously create special branches, such as **cleaning** when performing
singular actions such as cleaning the project, or making a very specific fix.

We also added a pull request template to smooth workflow and accelerate the integration and made
use of all the tools given by github to organize work. (creation of project, labels,
milestones, and issued to assign and keep track of the work.)


We finally decided to authorize merging only if a pull request or a push passes all unit
tests to avoid regressions, only making exceptions for test modification, fix,
and hotfix or if a feature is required rapidly and no one can review the files for now.
However, such a merge still needs to pass all tests. This choice was made to add flexibility
to the workflow considering the limited time we had to work with.


## Feedback

The only real setback we had during the project was the same than for any end-year project at UGA: Too many project
to give back at a time, right before the exams. Being able to complete the assigment, 
a few days after the exams or being able to start them sooner would be highly appreciated.