INSERT INTO TB_GROUP_PERMISSION (group_id, permission_id)
SELECT g.id, p.id
FROM TB_GROUP g,
     TB_PERMISSION p
WHERE g.name = 'admins';

INSERT INTO TB_GROUP_PERMISSION (group_id, permission_id)
SELECT g.id, p.id
FROM TB_GROUP g,
     TB_PERMISSION p
WHERE g.name = 'authors'
  AND p.name IN ('post:read', 'post:write', 'comment:read', 'comment:write', 'user:read');

INSERT INTO TB_GROUP_PERMISSION (group_id, permission_id)
SELECT g.id, p.id
FROM TB_GROUP g,
     TB_PERMISSION p
WHERE g.name = 'readers'
  AND p.name IN ('post:read', 'comment:read', 'comment:write', 'user:read');