from openjdk:11

RUN apt-get update
RUN apt-get install -qqy x11-apps libxext6 libxrender1 libxtst6 libxi6

ENV DISPLAY :0

ADD target/BlazarSQL-new-1.0-SNAPSHOT.jar /app/BlazarSQL.jar
ADD run.sh /app/run.sh

CMD sh /app/run.sh
