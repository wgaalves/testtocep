CREATE DATABASE cepdb;

\c cepdb;

CREATE TABLE "cep" (
                           "code" VARCHAR(9),
                           "street" VARCHAR(50),
                           "neighborhood" VARCHAR(20),
                           "city" VARCHAR(20),
                           "state" VARCHAR(20),
                           "ibge" VARCHAR(20),
                           "gia" VARCHAR(20),
                           "ddd" VARCHAR(20),
                           "siafi" VARCHAR(20),
                           CONSTRAINT "PK_CEP" PRIMARY KEY ("code")
);



