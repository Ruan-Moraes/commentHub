INSERT INTO TB_PERMISSION (name, description)
VALUES ('post:read', 'Permissão para ler posts'),
       ('post:write', 'Permissão para criar e editar posts'),
       ('post:delete', 'Permissão para deletar posts'),
       ('comment:read', 'Permissão para ler comentários'),
       ('comment:write', 'Permissão para criar e editar comentários'),
       ('comment:delete', 'Permissão para deletar comentários'),
       ('user:read', 'Permissão para ler informações de usuário'),
       ('user:manage', 'Permissão para gerenciar usuários'),
       ('admin:all', 'Permissões administrativas completas');