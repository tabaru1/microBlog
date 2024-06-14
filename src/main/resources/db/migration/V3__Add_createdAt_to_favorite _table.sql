ALTER TABLE follow DROP CONSTRAINT user_id;
ALTER TABLE follow DROP CONSTRAINT following_user_id;
ALTER TABLE follow ADD CONSTRAINT UNIQUE(user_id, following_user_id);