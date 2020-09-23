FROM openjdk:11
ADD target/product-information.jar product-information.jar
EXPOSE 7001
ENTRYPOINT ["java", "-jar", "product-information.jar"]