# Work
找工作的简历附件-暨南大学

Date -- 2024/10/30 现在实现了投票网站的登录，用户增删改查，创建投票与投票。
	使用，JWT进行登录并保存在localStorage中。保证增删改查都要登陆才行。


老版本依赖

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.example</groupId>
  <artifactId>SSM_test</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>SSM_test Maven Webapp</name>
  <url>http://maven.apache.org</url>
<!--
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.5.3</version>  指定版本
    <relativePath/>
  </parent>
-->

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.2</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.9</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>3.0.2</version>
      <exclusions>
        <exclusion>
          <artifactId>spring-context</artifactId>
          <groupId>org.springframework</groupId>
        </exclusion>
        <exclusion>
          <artifactId>slf4j-api</artifactId>
          <groupId>org.slf4j</groupId>
        </exclusion>

      </exclusions>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.30</version>
    </dependency>

    <dependency>
      <groupId>org.apache.tomcat.embed</groupId>
      <artifactId>tomcat-embed-jasper</artifactId>

    </dependency>


    <!-- Spring Boot 相关依赖 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
      <version>3.1.0</version>
      <exclusions>
        <exclusion>
          <artifactId>spring-context</artifactId>
          <groupId>org.springframework</groupId>
        </exclusion>
      </exclusions>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
      <version>3.1.0</version>
      <exclusions>
        <exclusion>
          <artifactId>tomcat-embed-el</artifactId>
          <groupId>org.apache.tomcat.embed</groupId>
        </exclusion>
        <exclusion>
          <artifactId>spring-boot-starter-tomcat</artifactId>
          <groupId>org.springframework.boot</groupId>
        </exclusion>
      </exclusions>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc -->

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-cache</artifactId>
      <version>3.1.0</version>
    </dependency>

    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.9.6</version>
    </dependency>

<!--    <dependency>-->
<!--      <groupId>taglibs</groupId>-->
<!--      <artifactId>standard</artifactId>-->
<!--      <version>1.1.2</version>-->
<!--    </dependency>-->

    <dependency>
      <groupId>org.jetbrains</groupId>
      <artifactId>annotations</artifactId>
      <version>13.0</version>
      <scope>compile</scope>
    </dependency>

    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-pool2</artifactId>
      <version>2.11.1</version>
    </dependency>

    <dependency>
      <groupId>org.apache.httpcomponents</groupId>
      <artifactId>httpcore</artifactId>
      <version>4.3.3</version>
    </dependency>

    <!-- jsp网页的依赖 -->

    <!-- https://mvnrepository.com/artifact/javax.servlet/jstl -->
    <!-- 添加 JSTL 依赖 -->
<!--    <dependency>-->
<!--      <groupId>jstl</groupId>-->
<!--      <artifactId>jstl</artifactId>-->
<!--      <version>1.2</version>-->
<!--    </dependency>-->
<!--    <dependency>-->
<!--      <groupId>javax.servlet</groupId>-->
<!--      <artifactId>jstl</artifactId>-->
<!--      <version>1.2</version> &lt;!&ndash; 或者使用最新版本 &ndash;&gt;-->
<!--    </dependency>-->



    <!-- 添加 Jakarta Servlet API 依赖 -->
    <dependency>
      <groupId>jakarta.servlet</groupId>
      <artifactId>jakarta.servlet-api</artifactId>
      <version>5.0.0</version> <!-- 或者使用最新版本 -->
    </dependency>
<!--    <dependency>-->
<!--      <groupId>taglibs</groupId>-->
<!--      <artifactId>standard</artifactId>-->
<!--      <version>1.1.2</version>-->
<!--    </dependency>-->

    <!-- https://mvnrepository.com/artifact/org.apache.tomcat.embed/tomcat-embed-jasper -->
    <dependency>
      <groupId>org.apache.tomcat.embed</groupId>
      <artifactId>tomcat-embed-jasper</artifactId>
      <version>10.1.31</version>
      <scope>provided</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>org.glassfish.web</groupId>
      <artifactId>jakarta.servlet.jsp.jstl</artifactId>
      <version>2.0.0</version>
    </dependency>

        <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/javax.servlet.jsp-api -->
    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>javax.servlet.jsp-api</artifactId>
      <version>2.3.3</version>
      <scope>provided</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-jsp-api -->
    <dependency>
      <groupId>org.apache.tomcat</groupId>
      <artifactId>tomcat-jsp-api</artifactId>
      <version>10.1.15</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-tomcat -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-tomcat</artifactId>
      <version>3.1.5</version>
      <exclusions>
        <exclusion>
          <artifactId>tomcat-embed-core</artifactId>
          <groupId>org.apache.tomcat.embed</groupId>
        </exclusion>
        <exclusion>
          <artifactId>tomcat-embed-el</artifactId>
          <groupId>org.apache.tomcat.embed</groupId>
        </exclusion>
      </exclusions>
    </dependency>

  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <version>1.5.4.RELEASE</version>
      </plugin>
    </plugins>
    <!--
            <plugins>
              <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                  <port>8080</port>
                  <path>/</path>
                </configuration>
              </plugin>
          </plugins>
    -->
    <finalName>SSM_test</finalName>
  </build>
</project>
