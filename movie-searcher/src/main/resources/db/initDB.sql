CREATE TABLE IF NOT EXISTS director(
                         id SERIAL PRIMARY KEY,
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR (255) NOT NULL,
                         birth_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS film(
                     id SERIAL PRIMARY KEY,
                     director_id INTEGER REFERENCES director NOT NULL,
                     name VARCHAR(255),
                     release_date DATE,
                     genre VARCHAR (255)
);