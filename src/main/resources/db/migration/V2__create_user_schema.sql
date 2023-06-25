DROP TABLE IF EXISTS user;

CREATE TABLE "user"
(
    id              UUID         NOT NULL,
    created_at      TIMESTAMP,
    last_updated_at TIMESTAMP,
    version         INT,
    username        VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    user_address_id UUID,
    status          VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE "user"
    ADD CONSTRAINT FK_USER_ON_USER_ADDRESS FOREIGN KEY (user_address_id) REFERENCES user_address (id);