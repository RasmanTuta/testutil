INSERT INTO USERS
 VALUES(1,'admin','password');
INSERT INTO USERS
 VALUES(2,'csr','password');
INSERT INTO USERS
 VALUES(3,'guest',
 '{SSHA}zEWG/X8AzSdkHEFXE8pyCt0ddA321ktZz6bx1to9bFikZBS5wlAw3g==');

INSERT INTO ROLES
 VALUES(1,'everyone');
INSERT INTO ROLES
 VALUES(2,'csr');
INSERT INTO ROLES
 VALUES(3,'administrator');

INSERT INTO USER_ROLES
 VALUES(1,1);
INSERT INTO USER_ROLES
 VALUES(1,2);
INSERT INTO USER_ROLES
 VALUES(1,3);
INSERT INTO USER_ROLES
 VALUES(2,1);
INSERT INTO USER_ROLES
 VALUES(2,2);
INSERT INTO USER_ROLES
 VALUES(3,1);