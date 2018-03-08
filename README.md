Questionnaire web application
=====

Create and populate questionnaire with four different question types:
1. text question
2. yes-no question
3. single choice question
4. multiple choice question

Application is created in the Play Framework and it uses PostgreSQL database.

### Start application instructions
- clone or download this repository
- install Play Framework (min version 2.5)
- create database questionnaire
- create Reference.conf file in conf directory
- populate Reference.conf with following values
  - SECRET_KEY (application secret key)
  - DB_USERNAME (username for password)
  - DB_PASSWORD (password for database)
  - EMAIL_EMAIL (email from which password reset will be sent)
  - EMAIL_PASSWORD (password for above email account)
  - HOST (localhost:9000)
- navigate with terminal to apllication directory and start application with `activator run` or `sbt run`
- application is served at localhost:9000
