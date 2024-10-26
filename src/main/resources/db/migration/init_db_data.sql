-- Заполнение таблицы roles
INSERT INTO roles (role_id, position)
VALUES (10000, 'admin'),
       (10001, 'teacher'),
       (10002, 'user');

-- Заполнение таблицы persons
INSERT INTO persons (person_id, name, surname, email, avatar_img)
VALUES (10000, 'Alice', 'Smith', 'alice.smith@example.com', null),
       (10001, 'Bob', 'Johnson', 'bob.johnson@example.com', null),
       (10002, 'Charlie', 'Williams', 'charlie.williams@example.com', 'avatar-10002t=1729100272856.png');

-- Заполнение таблицы users
INSERT INTO users (user_id, person_id, role_id, login, password, diamonds)
VALUES (10000, 10000, 10000, 'admin', '$2a$10$8v.FICNBT6hBvi1TGFr7W.I1tgogJNryFduVFu/0JbL1P/HOtBop2', 0),
       (10001, 10001, 10001, 'teacher', '$2a$10$74i63XHBZHbm5pw2JWDRvOQT2Tx2EMlnx5IgkDDUvC0PTgbUXjDZa', 50),
       (10002, 10002, 10002, 'user', '$2a$10$mefnFn0olDvoI3HIo4TE2u4.qyGNl6yYjL1febttKp9bYQuEB8SXy', 10);

-- Заполнение таблицы registration_keys
INSERT INTO registration_keys (email, reg_key)
VALUES ('alice.smith@example.com', 'REGKEY123'),
       ('bob.johnson@example.com', 'REGKEY456'),
       ('charlie.williams@example.com', 'REGKEY789');

-- Заполнение таблицы courses
INSERT INTO courses (course_id, author_id, course_img, course_name, topic, skill_level, short_description,
                     long_description, last_update)
VALUES (10000, 10001, 'course-1.png', 'Introduction to Programming', 'Programming', 'START',
        'A beginner-friendly course. An advanced course for experienced programmers.',
        '<p>This course covers the basics of programming. This course covers the basics of programming</p>',
        CURRENT_TIMESTAMP),
       (10001, 10001, 'course-1.png', 'Advanced Java', 'Java', 'PRO',
        'An advanced course for experienced programmers. An advanced course for experienced programmers.',
        '<p>This course dives deep into Java.</p>', CURRENT_TIMESTAMP),
       (10002, 10000, 'course-1.png', 'Teaching Techniques', 'Education', 'NORMAL',
        'Effective teaching strategies. An advanced course for experienced programmers.',
        '<p>This course helps teachers improve their methods.</p>', CURRENT_TIMESTAMP),
       (10003, 10000, 'course-1.png', 'Course-10003', 'Education', 'NORMAL',
        'Effective teaching strategies. An advanced course for experienced programmers.',
        '<p>This course helps teachers improve their methods.</p>', CURRENT_TIMESTAMP),
       (10004, 10000, 'course-1.png', 'Course-10004', 'Education', 'NORMAL',
        'Effective teaching strategies. An advanced course for experienced programmers.',
        '<p>This course helps teachers improve their methods.</p>', CURRENT_TIMESTAMP);

-- Заполнение таблицы chapters
INSERT INTO chapters (chapter_id, course_id, chapter_title, chapter_order)
VALUES (10000, 10000, 'Introduction', 1),
       (10001, 10000, 'Variables', 2),
       (10002, 10001, 'Advanced Topics', 1);

-- Заполнение таблицы lessons
INSERT INTO lessons (lesson_id, chapter_id, lesson_type, lesson_title, diamond_reward, lesson_order, duration)
VALUES (10000, 10000, 'VIDEO', 'What is Programming?', 5, 1, 120),
       (10001, 10000, 'TEXT', 'Getting Started', 10, 2, 35),
       (10002, 10001, 'TEST', 'Basic Concepts Quiz', 15, 1, null);

-- Заполнение таблицы questions
INSERT INTO questions (question_id, user_id, lesson_id, title, body, created_at, updated_at)
VALUES (10000, 10002, 10000, 'What is a variable?', '<p>Can someone explain what a variable is in programming?</p>',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10001, 10001, 10001, 'How to teach programming?',
        '<p>What are the best practices for teaching programming?</p>', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10002, 10000, 10002, 'How to manage a classroom?', '<p>Any tips on classroom management?</p>',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Заполнение таблицы answers
INSERT INTO answers (answer_id, question_id, user_id, body, created_at, updated_at)
VALUES (10000, 10000, 10001, '<p>A variable is a storage location for data.</p>', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10001, 10001, 10000, '<p>Using real-world examples can help.</p>', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10002, 10002, 10002, '<p>Establish clear rules and expectations.</p>', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Заполнение таблицы video_lessons
INSERT INTO video_lessons (lesson_id, video_url, video_lesson_id)
VALUES (10000, '/upload-videos/X2Download.com-Hollywood Undead - Bullet (Lyric Video).mp4', 10000),
       (10001, '/upload-videos/X2Download.com-Hollywood Undead - Bullet (Lyric Video).mp4', 10001),
       (10002, '/upload-videos/X2Download.com-Hollywood Undead - Bullet (Lyric Video).mp4', 10002);

-- Заполнение таблицы text_lessons
INSERT INTO text_lessons (lesson_id, lesson_body)
VALUES (10001, '<h2>Введение в JavaScript</h2>
<p>JavaScript — это язык программирования, который позволяет создавать интерактивные элементы на веб-страницах.</p>

<h3>Основные концепции</h3>
<ul>
    <li>Переменные</li>
    <li>Типы данных</li>
    <li>Условия</li>
    <li>Циклы</li>
</ul>

<h3>Переменные</h3>
<p>Переменные используются для хранения данных. В JavaScript можно объявить переменные с помощью <code>var</code>, <code>let</code> или <code>const</code>.</p>

<ol>
    <li><strong>var</strong> - устаревший способ объявления переменных.</li>
    <li><strong>let</strong> - позволяет создавать переменные с блочной областью видимости.</li>
    <li><strong>const</strong> - используется для объявления констант, которые не могут быть изменены.</li>
</ol>

<h3>Типы данных</h3>
<p>JavaScript поддерживает несколько основных типов данных:</p>
<ul>
    <li><strong>string</strong> - строки текста.</li>
    <li><strong>number</strong> - числовые значения.</li>
    <li><strong>boolean</strong> - логические значения (<code>true</code> или <code>false</code>).</li>
    <li><strong>object</strong> - сложные структуры данных.</li>
    <li><strong>array</strong> - массивы, которые являются типом объекта.</li>
</ul>

<h3>Условия</h3>
<p>Условия позволяют выполнять разные блоки кода в зависимости от выполнения определенных условий. Пример:</p>
<code>
if (condition) {<br>
    // код, выполняемый при выполнении условия<br>
}
</code>

<h3>Циклы</h3>
<p>Циклы используются для выполнения одного и того же блока кода несколько раз. Пример цикла <code>for</code>:</p>
<code>
for (let i = 0; i < 10; i++) {<br>
    console.log(i);<br>
}
</code>

<h3>Заключение</h3>
<p>JavaScript — мощный инструмент для веб-разработчиков, который позволяет создавать динамичные и интерактивные веб-страницы.</p>'),
       (10002, '<p>Test your knowledge with this quiz.</p>');
-- Test lessons do not have a duration

-- Заполнение таблицы tests
INSERT INTO tests (lesson_id, test_description, test_id)
VALUES (10002, 'Test your understanding of basic programming concepts.', 10002);


-- Заполнение таблицы test_questions без вариантов ответа
INSERT INTO test_questions (question_id, test_id, question_text, test_answer_id, answer_description)
VALUES (10000, 10002, 'What does a variable do?', null, 'A variable holds information.'),
       (10001, 10002, 'What does a for do?', null, 'for make a loop.'),
       (10002, 10002, 'What does a if do?', null, 'if make a condition');

-- Заполнение таблицы test_answers
INSERT INTO test_answers (test_answer_id, question_id, answer)
VALUES (10000, 10000, 'A variable holds information'),
       (10001, 10000, 'A variable is a storage location with a name.'),
       (10002, 10001, 'A for loop is used to iterate over a sequence.'),
       (10003, 10001, 'A for loop executes a block of code multiple times.'),
       (10004, 10002, 'An if statement executes code based on a condition.'),
       (10005, 10002, 'An if statement checks whether a condition is true.');

-- Обновление таблицы test_questions для установки правильных вариантов ответов
UPDATE test_questions
SET test_answer_id =
        CASE
            WHEN question_id = 10000 THEN 10000 -- Для вопроса о переменной
            WHEN question_id = 10001 THEN 10002 -- Для вопроса о цикле for
            WHEN question_id = 10002 THEN 10004 -- Для вопроса об условии if
            END
WHERE test_answer_id IS NULL;

-- Заполнение таблицы resources
INSERT INTO resources (resource_id, lesson_id, resource_title, resource_link)
VALUES (10000, 10000, 'Example Resource', 'http://example.com/resource1'),
       (10001, 10001, 'Another Resource', 'http://example.com/resource2'),
       (10002, 10002, 'Quiz Resource', 'http://example.com/resource3'),
       (10003, 10000, 'Example', 'http://example.com/resource1');

-- Заполнение таблицы reviews
INSERT INTO reviews (review_id, course_id, user_id, rating, review_text, created_at)
VALUES (10000, 10000, 10002, 5, 'Great course for beginners!', CURRENT_TIMESTAMP),
       (10001, 10001, 10001, 4, 'Very informative and well-structured.', CURRENT_TIMESTAMP),
       (10002, 10002, 10000, 5, 'Excellent teaching techniques!', CURRENT_TIMESTAMP),
       (10003, 10000, 10001, 4, 'Very informative and well-structured.', CURRENT_TIMESTAMP),
       (10004, 10000, 10000, 5, 'Excellent teaching techniques!', CURRENT_TIMESTAMP),
       (10005, 10001, 10002, 4, 'Very informative and well-structured.', CURRENT_TIMESTAMP),
       (10006, 10001, 10000, 5, 'Excellent teaching techniques!', CURRENT_TIMESTAMP);

-- Заполнение таблицы user_progress
INSERT INTO user_progress (progress_id, user_id, lesson_id, course_id)
VALUES (10000, 10002, 10000, 10000),
       (10001, 10001, 10001, 10000),
       (10002, 10000, 10002, 10001);

-- Заполнение таблицы course_access
INSERT INTO course_access (access_id, user_id, course_id, request_date, granted_date, status)
VALUES (10000, 10002, 10000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'APPROVED'),
       (10001, 10001, 10001, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'APPROVED'),
       (10002, 10000, 10002, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'APPROVED'),
       (10003, 10002, 10001, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'PENDING'),
       (10004, 10002, 10002, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'REJECTED');

-- Заполнение таблицы become_teacher
INSERT INTO become_teacher (become_teacher_id, user_id, request_date, granted_date, status, course_name, course_sphere,
                            course_description)
VALUES (10001, 10000, CURRENT_TIMESTAMP, null, 'PENDING', 'Course TEST', 'Frontend', 'Description...');

-- Заполнение таблицы user_notes
INSERT INTO user_notes (note_id, user_id, lesson_id, note_text, created_at)
VALUES (10000, 10002, 10000, '<p>This is a note about programming.</p>', CURRENT_TIMESTAMP),
       (10001, 10001, 10001, '<p>Important concepts to remember.</p>', CURRENT_TIMESTAMP),
       (10002, 10000, 10002, '<p>Review this before the test.</p>', CURRENT_TIMESTAMP);