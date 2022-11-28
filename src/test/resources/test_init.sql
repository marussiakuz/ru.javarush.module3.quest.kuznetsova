CREATE TABLE IF NOT EXISTS quest
(
    quest_id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    quest_name VARCHAR(512) UNIQUE NOT NULL,
    description VARCHAR(1000) NOT NULL,
    img VARCHAR(256) NOT NULL,
    CONSTRAINT pk_quest PRIMARY KEY (quest_id)
);

CREATE TABLE IF NOT EXISTS step
(
    step_id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    question VARCHAR(1000) NOT NULL,
    img VARCHAR(256),
    quest_id INT NOT NULL,
    is_start BOOLEAN DEFAULT FALSE NOT NULL,
    CONSTRAINT pk_step PRIMARY KEY (step_id),
    FOREIGN KEY (quest_id) REFERENCES quest (quest_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS choice
(
    choice_id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    answer VARCHAR(1000) NOT NULL,
    state VARCHAR(64) NOT NULL,
    current_step_id INT NOT NULL,
    CONSTRAINT pk_choice PRIMARY KEY (choice_id),
    FOREIGN KEY (current_step_id) REFERENCES step (step_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS the_end
(
    the_end_id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    choice_id INT NOT NULL,
    text VARCHAR(1000) NOT NULL,
    CONSTRAINT pk_the_end PRIMARY KEY (the_end_id),
    FOREIGN KEY (choice_id) REFERENCES choice (choice_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS to_be_continue
(
    choice_id INT NOT NULL,
    next_step_id INT NOT NULL,
    FOREIGN KEY (choice_id) REFERENCES choice (choice_id) ON DELETE CASCADE,
    FOREIGN KEY (next_step_id) REFERENCES step (step_id) ON DELETE CASCADE
);

INSERT INTO quest (quest_name, description, img)
VALUES ('Quest One', 'Some description', 'resources/questOne.gif');

INSERT INTO step (question, quest_id, is_start)
VALUES ('Question on step One and Quest One', 1, true);
INSERT INTO step (question, quest_id)
VALUES ('Question on step Two and Quest One', 1);
INSERT INTO step (question, quest_id)
VALUES ('Question on step Three and Quest One', 1);

INSERT INTO choice (answer, state, current_step_id)
VALUES ('Failure choice on step One and Quest One', 'FAILURE', 1);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Failure choice on step Two and Quest One', 'FAILURE', 2);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Failure choice on step Three and Quest One', 'FAILURE', 3);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Choice with continue on step One and Quest One', 'CONTINUE', 1);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Choice with continue on step Two and Quest One', 'CONTINUE', 2);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Win choice on step One and Quest One', 'WIN', 1);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Win choice on step Three and Quest One', 'WIN', 3);

INSERT INTO the_end (choice_id, text)
VALUES (1, 'Failure answer text on step One and Quest One');
INSERT INTO the_end (choice_id, text)
VALUES (2, 'Failure answer text on step Two and Quest One');
INSERT INTO the_end (choice_id, text)
VALUES (3, 'Failure answer text on step Three and Quest One');
INSERT INTO the_end (choice_id, text)
VALUES (6, 'Win answer text on step Three and Quest One');

INSERT INTO to_be_continue (choice_id, next_step_id)
VALUES (4, 2);
INSERT INTO to_be_continue (choice_id, next_step_id)
VALUES (5, 3);

INSERT INTO quest (quest_name, description, img)
VALUES ('Quest Two', 'Very strange quest', 'resources/questTwo.gif');

INSERT INTO step (question, quest_id, is_start)
VALUES ('Question on step One and Quest Two', 2, true);
INSERT INTO step (question, quest_id)
VALUES ('Question on step Two and Quest Two', 2);
INSERT INTO step (question, quest_id)
VALUES ('Question on step Three and Quest Two', 2);

INSERT INTO choice (answer, state, current_step_id)
VALUES ('Failure choice on step One and Quest Two', 'FAILURE', 4);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Failure choice on step Two and Quest Two', 'FAILURE', 5);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Failure choice on step Three and Quest Two', 'FAILURE', 6);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Choice with continue on step One and Quest Two', 'CONTINUE', 4);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Choice with continue on step Two and Quest Two', 'CONTINUE', 5);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Win choice on step Three and Quest Two', 'WIN', 6);

INSERT INTO the_end (choice_id, text)
VALUES (7, 'Failure answer text on step One and Quest Two');
INSERT INTO the_end (choice_id, text)
VALUES (8, 'Failure answer text on step Two and Quest Two');
INSERT INTO the_end (choice_id, text)
VALUES (9, 'Failure answer text on step Three and Quest Two');
INSERT INTO the_end (choice_id, text)
VALUES (12, 'Win answer text on step Three and Quest Two');

INSERT INTO to_be_continue (choice_id, next_step_id)
VALUES (10, 5);
INSERT INTO to_be_continue (choice_id, next_step_id)
VALUES (11, 6);