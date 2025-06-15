CREATE TRIGGER set_comment_updated_at
    BEFORE UPDATE
    ON TB_COMMENT
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp_column();

COMMENT ON TRIGGER set_comment_updated_at ON TB_COMMENT IS 'Gatilho para definir o updated_at de TB_COMMENT antes de uma atualização';