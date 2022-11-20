INSERT INTO quest (quest_name, description, img)
VALUES ('Quest one', 'Some description', 'resources/questOne.gif');

INSERT INTO step (question, quest_id, is_start)
VALUES ('You''ve lost your memory. Accept the UFO challenge?', 1, true);
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
VALUES (6, 'You''ve been brought home');

INSERT INTO to_be_continue (choice_id, next_step_id)
VALUES (4, 2);
INSERT INTO to_be_continue (choice_id, next_step_id)
VALUES (5, 3);

INSERT INTO quest (quest_name, description, img)
VALUES ('Quest two', 'Very strange quest', 'resources/questTwo.gif');

INSERT INTO step (question, quest_id, is_start)
VALUES ('You''ve lost your memory. Accept the UFO challenge?', 2, true);
INSERT INTO step (question, quest_id)
VALUES ('You accepted the challenge. Going up to the bridge to the captain?', 2);
INSERT INTO step (question, quest_id)
VALUES ('You went up to the bridge. Who are you?', 2);

INSERT INTO choice (answer, state, current_step_id)
VALUES ('Reject a call', 'FAILURE', 4);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Refuse to go up to the bridge', 'FAILURE', 5);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Lie about yourself', 'FAILURE', 6);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Take the challenge', 'CONTINUE', 4);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Climb to the bridge', 'CONTINUE', 5);
INSERT INTO choice (answer, state, current_step_id)
VALUES ('Tell the truth about yourself', 'WIN', 6);

INSERT INTO the_end (choice_id, text)
VALUES (7, 'You declined the call');
INSERT INTO the_end (choice_id, text)
VALUES (8, 'You didn''t negotiate');
INSERT INTO the_end (choice_id, text)
VALUES (9, 'Your lies have been exposed');
INSERT INTO the_end (choice_id, text)
VALUES (12, 'You''ve been brought home');

INSERT INTO to_be_continue (choice_id, next_step_id)
VALUES (10, 5);
INSERT INTO to_be_continue (choice_id, next_step_id)
VALUES (11, 6);