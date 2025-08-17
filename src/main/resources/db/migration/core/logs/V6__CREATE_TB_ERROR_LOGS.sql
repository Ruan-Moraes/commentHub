CREATE TABLE tb_error_logs (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    message VARCHAR(255),
    stacktrace TEXT,
    status CHAR(3)
);