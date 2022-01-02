SPRING SECURITY JWT
===================


## How to test

```
MYTOKEN=$(curl 'http://localhost:8080/auth/createToken?userName=user1&password=passw0rd')
curl -H "Authorization: Bearer $MYTOKEN" 'http://localhost:8080/hello'
```
