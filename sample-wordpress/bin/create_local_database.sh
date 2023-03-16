#!/bin/bash
#brew install mysql@5.7
#brew install gradle
#mysql.server start
mysql -uroot -h 127.0.0.1 -e "CREATE DATABASE IF NOT EXISTS wordpress" | exit 0
mysql -uroot -h 127.0.0.1 wordpress < ./wordpress.sql
mysql -uroot -h 127.0.0.1 -e "CREATE USER 'wordpress'@'localhost' IDENTIFIED BY 'wordpress'"
mysql -uroot -h 127.0.0.1 -e "GRANT ALL ON wordpress.* TO 'wordpress'@'localhost'"
