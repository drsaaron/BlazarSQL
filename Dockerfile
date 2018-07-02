from openjdk:latest

RUN apt-get update
RUN apt-get install -qqy x11-apps
ENV DISPLAY :0

ADD target/BlazarSQL-new-1.0-SNAPSHOT.jar /app/BlazarSQL-new-1.0-SNAPSHOT.jar
ADD target/dependency /app/lib
ADD run.sh /app/run.sh

CMD sh /app/run.sh
