DROP TABLE IF EXISTS territory;
CREATE TABLE territory (
  id SERIAL PRIMARY KEY,
  country VARCHAR(45) NOT NULL UNIQUE,
  win_after INT NOT NULL,
  max_per_day INT NOT NULL,
  overall_max INT NOT NULL
);

CREATE TABLE redeemed (
  id SERIAL PRIMARY KEY,
  ticket VARCHAR(10) NOT NULL UNIQUE,
  name VARCHAR(10) NOT NULL,
  city VARCHAR(10) NOT NULL,
  street VARCHAR(10) NOT NULL,
  zip VARCHAR(10) NOT NULL,
  winner BOOLEAN NOT NULL,
  timestamp DATE NOT NULL DEFAULT CURRENT_DATE
);


INSERT INTO territory (id, country, win_after, max_per_day, overall_max)
VALUES (1, 'GERMANY', 40, 250, 10000);

INSERT INTO territory (id, country, win_after, max_per_day, overall_max)
VALUES (2, 'HUNGARY', 80, 100, 5000);