DROP TABLE IF EXISTS user_address;

CREATE TABLE user_address
(
    id              UUID         NOT NULL,
    created_at      TIMESTAMP,
    last_updated_at TIMESTAMP,
    version         INT,
    street          VARCHAR(255) NOT NULL,
    city            VARCHAR(255) NOT NULL,
    province        VARCHAR(255),
    postal_code     VARCHAR(5)   NOT NULL,
    country_code    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_address PRIMARY KEY (id)
);