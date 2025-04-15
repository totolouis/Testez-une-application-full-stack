# Yoga App !

## Start the project

1. Navigate to the project directory:
    ```bash
    cd back
    ```
2. Install dependencies:
    ```bash
    mvn install
    ```
3. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Ressources

### MySQL

SQL script for creating the schema is available `ressources/sql/script.sql`

By default the admin account is:
- login: yoga@studio.com
- password: test!1234

### Test

#### Jacoco Tests

For launch and generate the jacoco code coverage:
> mvn clean test

Tests are available at this path:  "\back\target\site\jacoco\index.html"
