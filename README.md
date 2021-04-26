![AppVeyor](https://img.shields.io/appveyor/build/Romsou/Devops-project)
![GitHub pull requests](https://img.shields.io/github/issues-pr/Romsou/Devops-project)
![Packagist Stars](https://img.shields.io/packagist/stars/Romsou/!%5BGitHub%20pull%20requests%5D(https://img.shields.io/github/issues-pr/Romsou/Devops-project))
# Devops project


## Features

Coming

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

## Feedback

Coming
