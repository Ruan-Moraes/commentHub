CREATE OR REPLACE FUNCTION fn_set_created_at_on_insert()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.created_at IS NULL THEN
        NEW.created_at = CURRENT_TIMESTAMP;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION fn_set_created_at_on_insert() IS 'Define created_at automaticamente em INSERT quando não informado';