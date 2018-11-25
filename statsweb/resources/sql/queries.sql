-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id


-- :name get-lines-per-day :? :*
-- :doc get the lines per day in a channel
SELECT * from linesPerDay(:limit, :interval)

-- :name get-most-active-nicks :? :*
-- :doc get the most active nicks in a channel
SELECT * from mostActiveNicks(:limit, :interval)

-- :name get-activity-by-hour :? :*
-- :doc get the activity by hour of day breakdown
SELECT * from activityByHour(:limit, :interval)
