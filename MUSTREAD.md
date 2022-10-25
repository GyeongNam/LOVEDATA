## PI docker 서버 처음부터 실행하는 법

1. 우선 db 서버부터 실행하기
```docker
docker start lovedata_db
```

2. 이후 tomcat 서버 실행하기
```docker
docker start lovedata_tomcat
```

3. 마지막으로 nginx 서버 실행하기
```docker
docker start web
```

- lovedatat_db
    | 범주 | 내용 |
    |---|---|
    | 내부 ip | 172.168.0.2/16 |
    | 외부 ip | mon0mon.iptime.org |
    | open port | 13306 |

- lovedata_tomcat
    | 범주 | 내용 |
    | --- | --- |
    | 내부 ip | 172.168.0.3/16 |
    | 외부 ip | lovedata.duckdns.org |
    | open port | 587, 8080 |

- web
    | 범주 | 내용 |
    | --- | --- |
    | 내부 ip | 172.168.0.4/16 |
    | 외부 ip | mon0mon.iptime.org |
    | open port | 80, 443 |

## PI docker 서버에 war파일 배포하는 법


1. src/main/resources/application.yml에서 datasource-url을
 jdbc:mysql://mon0mon.iptime.org:13306/lovedata?serverTimezone=UTC&characterEncoding=UTF-8으로 수정하기

2. username을 lovedatauser, password를 love로 설정하기

3. mail - password를 ~!12qwaszx으로 수정하기

4. src/main/java/com/project/love_data/controller/HomeController.java에서
 71 ~ 74행 주석 풀고, url을 https://graph.facebook.com/v12.0/105161258693771/feed?fields=attachments,message&limit=1&access_token=EAAMwisXrG3oBAMFjTpcJFP2u4ekAltQbqxaKWwXDdEiBdbEq4MjfMZAZBHspxfnxQEqs9ibfW6Am9o2hyjdvhGorMYAXD0JmhBfgTZAaGWpruBVdub5NaQ0re3RF54iwolHHTRZAaYKM3wTHCMtNwPqvBxY631z2UNSQWZAaUhAZDZD
 으로 수정하기

 5. Gradle 메뉴에서 love_data - Tasks - build - bootWar로 빌드하기
 (빌드 파일 경로는 build/libs)

 6. 빌드 후 Tools - Deployment - UploadHere 로 파이 서버에 배포

 7. PI에 터미널 접속 후 경로로 이동
 ```bash
 cd /home/pi/docker/web/personal/lovedata
 ```

 8. docker ps로 실행 중인 컨테이너 확인 후 love_data.war 파일 복사
```docker
docker cp lovedata_tomcat:/opt/apache-tomcat-9.0.46/webapps/love_data/WEB-INF/classes/static/image .
docker cp love_data.war lovedata_tomcat:/opt/apache-tomcat-9.0.46/webapps
```

9. 컨테이너 재 시작
```docker
docker restart lovedata_tomcat
```

10. 컨테이너에 터미널 접속
```docker
docker exec -it lovedata_tomcat /bin/bash
```

11. 톰캣 실행
```bash
cd /opt/apache-tomcat-9.0.46/bin
./startup.sh
```

12. 백업한 이미지 파일 넣기
```docker
docker cp image lovedata_tomcat:/opt/apache-tomcat-9.0.46/webapps/love_data/WEB-INF/classes/static/
```