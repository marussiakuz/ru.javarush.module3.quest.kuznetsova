INSERT INTO quest (quest_name, description)
VALUES ('Quest one', 'Some description');

INSERT INTO step (question, quest_id)
VALUES ('You''ve lost your memory. Accept the UFO challenge?', 1);
INSERT INTO step (question, quest_id)
VALUES ('You accepted the challenge. Going up to the bridge to the captain?', 1);
INSERT INTO step (question, quest_id)
VALUES ('You went up to the bridge. Who are you?', 1);

INSERT INTO choice (answer, state, current_step_id)
VALUES ('Reject a call', 'FAILURE', 1);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Refuse to go up to the bridge', 'FAILURE', 2);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Lie about yourself', 'FAILURE', 3);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Take the challenge', 'CONTINUE', 1);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Climb to the bridge', 'CONTINUE', 2);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Tell the truth about yourself', 'WIN', 3);

INSERT INTO the_end (choice_id, text)
VALUES (1, 'You declined the call');
INSERT INTO the_end (choice_id, text)
VALUES (2, 'You didn''t negotiate');
INSERT INTO the_end (choice_id, text)
VALUES (3, 'Your lies have been exposed');
INSERT INTO the_end (choice_id, text)
VALUES (6, 'You declined the call');

INSERT INTO to_be_continue (choice_id, step_id)
VALUES (4, 2);
INSERT INTO the_end (choice_id, step_id)
VALUES (5, 3);


