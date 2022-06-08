# spring-boot-vite-react

An example project of Spring Boot + React with vite (HMR supported)

Run the following commands:

```
# frontend part
$ ( cd src/frontend && npm run dev )

# backend part
$ gradle bootRun
```

Then, open http://localhost:8080/ .

Click the button on the page, then the frontend will fetch the data from the backend.

Owing to Hot-Module-Replacement, you will see the changes asap when you edit the source code of the frontend.

## Mechanism

* the frontend part runs on port 3000 (default)
* the backend part runs on port 8080 (default)
* if the backend part meets any unhandled request, proxy the request to the frontend (port 3000)
