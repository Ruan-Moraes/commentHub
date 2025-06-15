CREATE OR REPLACE FUNCTION fn_update_timestamp_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION fn_update_timestamp_column() IS 'Função para atualizar a coluna updated_at de uma tabela sempre que um registro for atualizado';