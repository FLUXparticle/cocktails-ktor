# cocktails-kotlin

Kotlin/Ktor-Nachbau des Go-Projekts `cocktails-go` mit:

- Ktor REST API
- Koin Dependency Injection
- Kotlinx Serialization
- Repository/Service-Schichten
- Cocktails-Datensatz aus Textdatei (`src/main/resources/cocktails.txt`)
- Annotationen + Reflection-Import (`/api/import/json`, `/api/import/kv`)
- Kotest-Tests (inkl. Data-driven)

## Start

```bash
mvn -q -DskipTests compile
mvn -q exec:java
```

## Wichtige Endpunkte

- `GET /api/cocktails`
- `GET /api/cocktails/{id}`
- `GET /api/ingredients`
- `GET /api/ingredients/{id}`
- `GET /api/search?query=...`
- `POST /api/possible`
- `POST /api/contains`
- `GET /api/user/{userId}/fridge`
- `PATCH /api/user/{userId}/fridge/{ingredientId}`
- `GET /api/user/{userId}/possible`
- `GET /api/user/{userId}/contains`
- `POST /api/import/json`
- `POST /api/import/kv`

## Textformat (wie in `initdb.go`)

Eine Cocktail-Gruppe besteht aus:

1. Cocktailname (erste Zeile)
2. Zutatenzeilen in Form `2cl:Rum` oder ohne Mengenangabe `Lime`
3. Leerzeile als Trenner
