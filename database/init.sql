CREATE TABLE empresa (
    id      SERIAL PRIMARY KEY,
    cep     VARCHAR(8) NOT NULL,
    nome    VARCHAR(200) NOT NULL,
    doc     VARCHAR(14) NOT NULL UNIQUE
);

CREATE TABLE fornecedor (
    id          SERIAL PRIMARY KEY,
    nome        VARCHAR(200) NOT NULL,
    cep         VARCHAR(8) NOT NULL,
    email       VARCHAR(60) NOT NULL,
    doc         VARCHAR(14) UNIQUE,
    rg          VARCHAR(9),
    dat_nasc    DATE
);

CREATE TABLE empresa_fornecedor (
    id_empresa INT NOT NULL,
    id_fornecedor INT NOT NULL,

    CONSTRAINT pk_empresa_fornecedor primary key(id_empresa,id_fornecedor),
    CONSTRAINT fk_id_empresa FOREIGN KEY (id_empresa) REFERENCES empresa(id),
    CONSTRAINT fk_id_fornecedor FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id)
);