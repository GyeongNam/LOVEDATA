<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DB_TEST</title>
</head>
<body>
    <p>
        CREATE DATABASE lovedata default CHARACTER SET UTF8;
    </p>

    <p>
        GRANT ALL PRIVILEGES ON lovedata.* TO lovedatauser@localhost IDENTIFIED BY 'love';
    </p>

    <p>
        create table test (
        seq INT(5) not null,
        name varchar(10) not null,
        country varchar(3) not null,
        primary key(seq)
        );
    </p>

    <p>
        INSERT INTO test VALUES(1, '김춘배', '냥국');
    </p>
    <p>
        INSERT INTO test VALUES(2, '한보미', '한국');
    </p>
    <p>
        INSERT INTO test VALUES(3, '나남훈', '영국');
    </p>
    <p>
        INSERT INTO test VALUES(4, '오지나', '한국');
    </p>
    <p>
        INSERT INTO test VALUES(5, '반휘혈', '냥국');
    </p>
    <p>
        INSERT INTO test VALUES(6, '최민수', '냥국');
    </p>
</body>
</html>
