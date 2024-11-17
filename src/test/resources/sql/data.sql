INSERT INTO users (id, password, username, chat_id, telegram)
VALUES (1, 'password123', 'user1@test.test', 1001, 'user1'),
       (2, 'password456', 'user2@test.test', 1002, 'user2'),
       (3, 'password789', 'user3@test.test', 1003, 'user3'),
       (4, 'password012', 'user4@test.test', 1004, 'user4'),
       (5, 'password345', 'user5@test.test', 1005, 'user5'),
       (6, 'password678', 'user6@test.test', 1006, 'user6');

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO reminder (id, title, description, remind, user_id, is_sent, is_sent_telegram)
VALUES (1, 'Meeting', 'Team meeting in conference room', '2024-11-11 09:00:00', 1, 'NOT_SENT',
        'NOT_SENT'),
       (2, 'Doctor Appointment', 'Dentist appointment at 10 AM', '2024-11-11 10:00:00', 2,
        'NOT_SENT', 'NOT_SENT'),
       (3, 'Grocery Shopping', 'Buy groceries after work', '2024-11-11 18:00:00', 3, 'NOT_SENT',
        'NOT_SENT'),
       (4, 'Workout', 'Gym session at 7 PM', '2024-11-15 19:00:00', 4, 'NOT_SENT', 'NOT_SENT'),
       (5, 'Project Deadline', 'Submit project report', '2024-11-16 23:59:00', 5, 'NOT_SENT',
        'NOT_SENT'),
       (6, 'Call Mom', 'Weekly call with mom', '2024-11-17 20:00:00', 6, 'NOT_SENT', 'NOT_SENT'),
       (7, 'Car Service', 'Car service appointment at 9 AM', '2024-11-18 09:00:00', 1, 'NOT_SENT',
        'NOT_SENT'),
       (8, 'Friend’s Birthday', 'Buy a gift for Tom birthday', '2024-11-19 15:00:00', 2,
        'NOT_SENT', 'NOT_SENT'),
       (9, 'Online Course', 'Complete Module 4 of online course', '2024-11-20 21:00:00', 3,
        'NOT_SENT', 'NOT_SENT'),

       (10, 'Family Dinner', 'Dinner with family at 7 PM', '2024-11-21 19:00:00', 4, 'NOT_SENT',
        'NOT_SENT');

SELECT SETVAL('reminder_id_seq', (SELECT MAX(id) FROM reminder));



