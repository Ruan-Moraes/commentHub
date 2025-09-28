CREATE TRIGGER trigger_update_created_at_tb_user
    BEFORE INSERT
    ON TB_USER
    FOR EACH ROW EXECUTE FUNCTION fn_set_created_at_on_insert();

COMMENT ON TRIGGER trigger_update_created_at_tb_user ON TB_USER IS 'Gatilho para definir o created_at de TB_USER antes de uma inserção';


