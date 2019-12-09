# Generating SSL keys

Execucte following commands in the console, inside src/main/resources

```
openssl genrsa -des3 -out private.pem 4096
```

```
rsa -in private.pem -outform PEM -pubout -out public.pem
```