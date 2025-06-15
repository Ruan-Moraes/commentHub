CREATE TRIGGER tg_set_post_updated_at
    BEFORE UPDATE
    ON TB_POST
    FOR EACH ROW
EXECUTE FUNCTION fn_update_timestamp_column();

COMMENT ON TRIGGER tg_set_post_updated_at ON TB_POST IS 'Gatilho para definir o updated_at de TB_POST antes de uma atualização';