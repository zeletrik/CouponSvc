DROP TABLE IF EXISTS territory;
CREATE TABLE territory (
  id SERIAL PRIMARY KEY,
  country VARCHAR(45) NOT NULL UNIQUE,
  win_after INT NOT NULL,
  max_per_day INT NOT NULL,
  overall_max INT NOT NULL
);

DROP TABLE IF EXISTS actual_status;
CREATE TABLE actual_status (
  id SERIAL PRIMARY KEY,
  country VARCHAR(45) NOT NULL UNIQUE,
  redeemed_today INT NOT NULL,
  win_overall INT NOT NULL
);

DROP TABLE IF EXISTS redeemed;
CREATE TABLE redeemed (
  id SERIAL PRIMARY KEY,
  ticket VARCHAR(10) NOT NULL UNIQUE,
  name VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  territory VARCHAR(45) NOT NULL,
  city VARCHAR(45) NOT NULL,
  street VARCHAR(45) NOT NULL,
  zip VARCHAR(10) NOT NULL,
  winner BOOLEAN NOT NULL,
  timestamp DATE NOT NULL DEFAULT CURRENT_DATE
);

INSERT INTO territory (id, country, win_after, max_per_day, overall_max)
VALUES (1, 'GERMANY', 40, 250, 10000);

INSERT INTO territory (id, country, win_after, max_per_day, overall_max)
VALUES (2, 'HUNGARY', 80, 100, 5000);

INSERT INTO actual_status (id, country, redeemed_today, win_overall)
VALUES (1, 'GERMANY', 119, 120);

INSERT INTO actual_status (id, country, redeemed_today, win_overall)
VALUES (2, 'HUNGARY', 98, 13);