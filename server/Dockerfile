#使用java 8 版本
FROM java:8
COPY demo-0.0.1-SNAPSHOT.jar musicol.jar
#对外暴露的端口
EXPOSE 8481
ENTRYPOINT ["java", "-jar", "musicol.jar", "--spring.profiles.active=pro"]