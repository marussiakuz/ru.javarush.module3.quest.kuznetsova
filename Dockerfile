FROM tomcat:9-jre11-openjdk
COPY target/quest-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
ENV H2 true
CMD ["catalina.sh", "run"]