CREATE TABLE "orders"
(
    id       VARCHAR(36) NOT NULL,
    created  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    item     VARCHAR(100),
    price    INTEGER,
    quantity INTEGER,
    PRIMARY KEY (id)
);
