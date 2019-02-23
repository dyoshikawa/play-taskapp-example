# Migration

```
./migrate create -ext sql -dir conf/db/migration/default create_tasks
```

```$xslt
PGSSLMODE=disable ./migrate -source file://./conf/db/migrations -database postgres://default:secret@localhost:5432/default up
```