CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name TEXT not null,
    email TEXT not null,
    age INT not null
) ;