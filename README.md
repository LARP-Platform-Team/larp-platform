# LARP-Platform [![Build Status](https://travis-ci.org/LARP-Platform-Team/larp-platform.svg?branch=devel)](https://travis-ci.org/LARP-Platform-Team/larp-platform) #

Here is a little manual:

* Install JDK 1.8
* Install Grails
* Install PostgreSQL and create database for application
* Run grails application.

### How to install and configure JDK? ###

Download and extract **Oracle Java SE Development Kit 8** or **OpenJDK 8**. Then add to **~/.profile** file this line:

```
export JAVA_HOME=<path-to-jdk>
PATH=$PATH:$JAVA_HOME/bin
```

You can also install JDK via standart repositories:

```
sudo apt-get install openjdk-8-jdk
```

If JDK already installed in your system there may be some problems with default comands like **java** and **javac**. They can be linked with older version of Java. To know it exactly you should execute:

```
update-alternatives --config java
```

You will see a list of paths to different versions of JDK. Then choose a number of the correct version.

If there is no correct version, you can add it by the command:

```
update-alternatives --install /usr/bin/java java <path-to-jdk>/bin/java 1
```

Then try to configure symlink again and repeat this part for **javac** command.

### How to install and configure Grails? ###

Download and extract **Grails** from official site. Then add to **~/.profile** file this line:

```
export GRAILS_HOME=<path-to-grails>
PATH=$PATH:$GRAILS_HOME/bin
```

### How to install PostgreSQL and create database? ###

Install PostgreSQL via standart repositories:

```
sudo apt-get install postgresql
```

If there is no russian locale in your system, you should install it:

```
sudo locale-get ru_RU.UTF-8
```

Then drop and create again cluster with russian locale:

```
sudo pg_dropcluster --stop <version> main
sudo pg_createcluster --locale ru_RU.UTF-8 <version> main
```
After this manipulations PostgreSQL daemon will be down. You should start it again:

```
sudo service postgresql start
```

In **/etc/postgresql/<version>/main/pg_hba.conf** add rules:

```
local sameuser all md5
local template1 all md5
```

And restart daemon.

Enter in PosgreSQL console like this:

```
sudo -upostgres psql
```

And create user and database:

```
#!sql
CREATE USER larp_platform ENCRYPTED PASSWORD 'larp_platform';
CREATE DATABASE larp_platform OWNER 'larp_platform';
```

To check connection try this:

```
psql -Ularp_platform
```

### How to configure database settings? ###

Edit file **grails-app/conf/DataSource.groovy**:

```
#!groovy
dataSource {
    ...
    username = "larp_platform"
    password = "larp_platform"
}

...
environments {
    development {
        dataSource {
            ...
            url = "jdbc:postgresql://<host>:5432/larp_platform"
        }
    }
    ...
}
```

### How to run application? ###

You can run application on internal tomcat (development mode by default):

```
grails [-Dgrails.server.port.http=8090] [dev|test|prod] run-app
```

Also you can deploy war-file on tomcat manualy. To build war-file execute (production mode by default):

```
grails [dev|test|prod] war
```
