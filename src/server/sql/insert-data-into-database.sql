USE caas;

INSERT INTO authtype (name) VALUES ('User'), ('Admin'), ('Staff');

INSERT INTO orderstatus (name) VALUES ('OPEN'), ('PROCESSED'), ('FINISHED'), ('CANCELED');

INSERT INTO userstatus (name) VALUES ('Active'), ('Blocked');

INSERT INTO weekday (name) VALUES ('Monday'), ('Tuesday'), ('Wednesday'), ('Thursday'), ('Friday');

INSERT INTO person (id, username, password, authType, userStatus, firstName, lastName, profilePicturePath, email) 
  VALUES ('s1310307054', 'admin', 'moEWXsJIYGc=', 2, 1, 'Marius-Constantin', 'Dinu', '', 's1310307054@students.fh-hagenberg.at');