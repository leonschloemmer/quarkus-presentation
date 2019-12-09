# Generating SSL keys

Execucte following commands in the console, inside src/main/resources

```
openssl genpkey -out private.pem -algorithm RSA -pkeyopt rsa_keygen_bits:2048
```

```
openssl rsa -in private.pem -outform PEM -pubout -out public.pem
```