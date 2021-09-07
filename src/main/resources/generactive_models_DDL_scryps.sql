CREATE TABLE configuration
(
    id         SERIAL PRIMARY KEY,
    resolution VARCHAR(3) CHECK ( resolution IN ('HD', 'FHD', '_4K'))
);
CREATE TABLE item
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(20),
    price            INT,
    image_URL        VARCHAR(30),
    currency         VARCHAR(3),
    parent           INT,
    configuration_Id INT REFERENCES Configuration (id),
    complexity       DECIMAL
);

CREATE TABLE "group"
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR(20),
    parent INT REFERENCES "group" (id)
);
ALTER TABLE item
    ADD CONSTRAINT Fkey FOREIGN KEY (parent) REFERENCES "group" (id);
