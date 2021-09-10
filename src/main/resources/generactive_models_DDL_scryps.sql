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
    parent           INT ,
    configuration_Id INT
);

CREATE TABLE "group"
(
    id     SERIAL primary key ,
    title  VARCHAR(20),
    parent INT REFERENCES "group" (id)
);


CREATE TABLE generactive_item (
     complexity DECIMAL
 ) INHERITS (item) ;

DROP TABLE Configuration,"group",item,Generactive_Item;

ALTER TABLE item ADD CONSTRAINT Fkey_gr FOREIGN KEY (parent) REFERENCES "group" (id);
ALTER TABLE item ADD CONSTRAINT Fkey_conf FOREIGN KEY (Configuration_Id) REFERENCES Configuration (id);

ALTER TABLE "group"
    ADD CONSTRAINT Fkey_par FOREIGN KEY (Parent) REFERENCES "group" (id);

ALTER TABLE item
    DROP CONSTRAINT Fkey_gr ;
ALTER TABLE item
    DROP CONSTRAINT Fkey_conf ;

ALTER TABLE "group"
    DROP CONSTRAINT Fkey_par;