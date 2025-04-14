-- Заполнение таблицы roles
INSERT INTO roles (role_id, position)
VALUES (10000, 'admin'),
       (10001, 'teacher'),
       (10002, 'user');

-- Заполнение таблицы persons
INSERT INTO persons (person_id, name, surname, email, avatar_img)
VALUES (10000, 'Alice', 'Smith', 'alice.smith@example.com', 'avatar-10002t=1730915435444.png'),
       (10001, 'Bob', 'Johnson', 'bob.johnson@example.com', null),
       (10002, 'Charlie', 'Williams', 'charlie.williams@example.com', 'avatar-10002t=1731497145372.png');

-- Заполнение таблицы avatar_strokes (Рамки аватаров)
INSERT INTO avatar_strokes (avatar_stroke_id, label, value, url, price)
VALUES (10000, 'Smoking', 'smoking', 'smoking-stroke.png', 100),
       (10001, 'Pink', 'pink', 'pink-stroke.png', 100),
       (10003, 'Tree', 'tree', 'tree-stroke.png', 300),
       (10004, 'Rainbow Clouds', 'rainbow-clouds', 'rainbow-clouds-stroke.png', 500);

-- Заполнение таблицы dignities (Титулы)
INSERT INTO dignities (dignity_id, dignity_name, price)
VALUES (10000, 'Мастер Джедай', 75),
       (10001, 'Профессионал', 100),
       (10002, 'Робот', 40),
       (10003, 'Гигант мысли', 70),
       (10004, 'МегаМозг', 80),
       (10005, 'Чоткий Пасан', 120),
       (10006, 'Черный Мечник', 300),
       (10007, 'Тертый Калач', 120),
       (10008, 'Всезнающий', 60),
       (10009, 'В активном поиске', 1),
       (10010, 'Сыр Камамбер', 99),
       (10011, 'Сок "Добрый"', 150),
       (10012, 'Черный Маг', 100),
       (10013, 'Мистер Президент', 333),
       (10014, 'Валенок', 40),
       (10015, 'Утюг', 77),
       (10016, 'Киллер в отставке', 777),
       (10017, 'Падаван', 40);

-- Заполнение таблицы nickname_colors (Цвета ников)
INSERT INTO nickname_colors (nickname_color_id, name, color, price)
VALUES (10000, 'Голубой', '{"color":"#4abecd"}', 50),
       (10001, 'Бронзовый', '{"color":"#eacb44"}', 50),
       (10002, 'Оранжевый', '{"color":"#ef9d38"}', 50),
       (10003, 'Розовый', '{"color":"#e82da2"} ', 50),
       (10004, 'Фиолетовый', '{"color":"#992ae1"}', 50),
       (10005, 'Зеленый', '{"color":"#26c044"}', 50),
       (10006, 'Красный', '{"color":"#ef0606"}', 50),
       (10007, 'Retro Wagon',
        '{"background": "linear-gradient(90deg, #FDBB2D 0%, #22C1C3 100%)", "WebkitBackgroundClip": "text", "color": "transparent"}',
        100),
       (10008, 'Fresco Crush',
        '{"background": "linear-gradient(90deg, #FDBB2D 0%, #3A1C71 100%)", "WebkitBackgroundClip": "text", "color": "transparent"}',
        100),
       (10009, 'Ooey Gooey',
        '{"background": "linear-gradient(90deg, #0700b8 0%, #00ff88 100%)", "WebkitBackgroundClip": "text", "color": "transparent"}',
        100),
       (100010, 'Disco Club',
        '{"background": "linear-gradient(90deg, #FC466B 0%, #3F5EFB 100%)", "WebkitBackgroundClip": "text", "color": "transparent"}',
        100),
       (100011, 'Aqua Spray',
        '{"background": "linear-gradient(90deg, #00d2ff 0%, #3a47d5 100%)", "WebkitBackgroundClip": "text", "color": "transparent"}',
        100),
       (100012, 'Bloody Mimosa',
        '{"background": "linear-gradient(90deg, #d53369 0%, #daae51 100%)", "WebkitBackgroundClip": "text", "color": "transparent"}',
        100),
    (100013, 'Shady Lane',
        '{"background": "linear-gradient(90deg, #3F2B96 0%, #A8C0FF 100%)", "WebkitBackgroundClip": "text", "color": "transparent"}',
        100),
       (100014, 'Kale Salad',
        '{"background": "#2A7B9B", "background": "linear-gradient(90deg,rgba(42, 123, 155, 1) 0%, rgba(87, 199, 133, 1) 50%, rgba(237, 221, 83, 1) 100%)", "WebkitBackgroundClip": "text", "color": "transparent"}',
        100);

-- Заполнение таблицы users
INSERT INTO users (user_id, person_id, role_id, login, password, diamonds)
VALUES (10000, 10000, 10000, 'admin', '$2a$10$8v.FICNBT6hBvi1TGFr7W.I1tgogJNryFduVFu/0JbL1P/HOtBop2', 0),
       (10001, 10001, 10001, 'teacher', '$2a$10$74i63XHBZHbm5pw2JWDRvOQT2Tx2EMlnx5IgkDDUvC0PTgbUXjDZa', 50);
INSERT INTO users (user_id, person_id, role_id, login, password, diamonds, avatar_stroke_id, dignity_id,
                   nickname_color_id)
VALUES (10002, 10002, 10002, 'user', '$2a$10$mefnFn0olDvoI3HIo4TE2u4.qyGNl6yYjL1febttKp9bYQuEB8SXy', 3300, 10003, 10000,
        10000);

-- Присваиваем (покупаем) пользователям рамки для аватарок
INSERT INTO user_purchased_avatar_strokes (avatar_stroke_id, user_id)
VALUES (10000, 10002),
       (10001, 10002);

-- Присваиваем (покупаем) пользователям титулы
INSERT INTO user_purchased_dignities (dignity_id, user_id)
VALUES (10000, 10002),
       (10001, 10002);

-- Присваиваем (покупаем) пользователям цвета ников
INSERT INTO user_purchased_nickname_colors (nickname_color_id, user_id)
VALUES (10000, 10002),
       (10001, 10002);

-- Заполнение таблицы registration_keys
INSERT INTO registration_keys (email, reg_key)
VALUES ('alice.smith@example.com', 'REGKEY123'),
       ('bob.johnson@example.com', 'REGKEY456'),
       ('charlie.williams@example.com', 'REGKEY789');

-- Заполнение таблицы courses
INSERT INTO courses (course_id, author_id, course_img, course_name, topic, skill_level, short_description,
                     long_description, last_update)
VALUES (10000, 10001, 'course-previewt=1731490709350.png', 'Introduction to Programming', 'Programming', 'START',
        'A beginner-friendly course. An advanced course for experienced programmers.',
        '<p>This course covers the basics of programming. This course covers the basics of programming</p>',
        CURRENT_TIMESTAMP),
       (10001, 10001, 'course-previewt=1731490571085.png', 'Advanced Java', 'Java', 'PRO',
        'An advanced course for experienced programmers. An advanced course for experienced programmers.',
        '<p>This course dives deep into Java.</p>', CURRENT_TIMESTAMP);

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
       (10003, 10002, 10001, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'PENDING');

-- Заполнение таблицы become_teacher
INSERT INTO become_teacher (become_teacher_id, user_id, request_date, granted_date, status, course_name, course_sphere,
                            course_description)
VALUES (10001, 10000, CURRENT_TIMESTAMP, null, 'PENDING', 'Course TEST', 'Frontend', 'Description...');

-- Заполнение таблицы user_notes
INSERT INTO user_notes (note_id, user_id, lesson_id, note_text, created_at)
VALUES (10000, 10002, 10000, '<p>This is a note about programming.</p>', CURRENT_TIMESTAMP),
       (10001, 10001, 10001, '<p>Important concepts to remember.</p>', CURRENT_TIMESTAMP),
       (10002, 10000, 10002, '<p>Review this before the test.</p>', CURRENT_TIMESTAMP);