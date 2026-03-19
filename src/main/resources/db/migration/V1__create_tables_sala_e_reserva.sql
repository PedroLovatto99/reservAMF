CREATE TABLE salas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    bloco int NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    ativa Boolean NOT NULL
);

CREATE TABLE reservas(
   id BIGSERIAL PRIMARY KEY,
   sala_id BIGINT NOT NULL,
   data_hora_inicio TIMESTAMP NOT NULL,
   data_hora_fim TIMESTAMP NOT NULL,
   solicitante VARCHAR(100) NOT NULL,
   motivo VARCHAR(300) NOT NULL,
   status VARCHAR NOT NULL,
   CONSTRAINT fk_sala FOREIGN KEY (sala_id) REFERENCES salas(id)
);
