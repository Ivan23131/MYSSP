ALTER TABLE "user"
DROP COLUMN created_at,
DROP COLUMN date_of_birth;
ALTER TABLE "user"
RENAME TO t_user;