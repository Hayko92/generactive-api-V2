CREATE TABLE configuration
(
    Id         SERIAL PRIMARY KEY,
    Resolution VARCHAR(3) CHECK ( Resolution IN ('HD', 'FHD', '_4K'))
);
CREATE TABLE item
(
    Id               SERIAL PRIMARY KEY,
    Title            VARCHAR(20),
    Price            INT,
    Image_Url        VARCHAR(30),
    Currency         VARCHAR(3),
    Parent           INT,
    Configuration_Id INT REFERENCES Configuration (Id),
    Complexity       DECIMAL
);

CREATE TABLE "group"
(
    Id     SERIAL PRIMARY KEY,
    Title  VARCHAR(20),
    Parent INT REFERENCES "group" (Id)
);
ALTER TABLE item
    ADD CONSTRAINT Fkey FOREIGN KEY (Parent) REFERENCES "group" (Id);
