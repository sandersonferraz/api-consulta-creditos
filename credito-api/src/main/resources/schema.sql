DROP TABLE IF EXISTS credito;
CREATE TABLE IF NOT EXISTS credito (
    id BIGSERIAL PRIMARY KEY,
    numero_credito VARCHAR(50) NOT NULL UNIQUE,
    numero_nfse VARCHAR(50) NOT NULL,
    data_constituicao DATE NOT NULL,
    valor_issqn NUMERIC(15, 2) NOT NULL,
    tipo_credito VARCHAR(50) NOT NULL,
    simples_nacional BOOLEAN NOT NULL,
    aliquota NUMERIC(5, 2) NOT NULL,
    valor_faturado NUMERIC(15, 2) NOT NULL,
    valor_deducao NUMERIC(15, 2) NOT NULL,
    base_calculo NUMERIC(15, 2) NOT NULL,
    created_at TIMESTAMP
);