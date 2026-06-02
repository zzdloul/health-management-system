CREATE TABLE IF NOT EXISTS t_assessment (
  id int NOT NULL AUTO_INCREMENT,
  member_id int DEFAULT NULL,
  type int DEFAULT NULL,
  assessment_date datetime DEFAULT NULL,
  result varchar(255) DEFAULT NULL,
  score int DEFAULT NULL,
  health_manager_id int DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,
  create_time datetime DEFAULT NULL,
  update_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS t_assessment_question (
  id int NOT NULL AUTO_INCREMENT,
  assessment_type int DEFAULT NULL,
  content varchar(255) DEFAULT NULL,
  type int DEFAULT NULL,
  options varchar(500) DEFAULT NULL,
  scores varchar(500) DEFAULT NULL,
  sort int DEFAULT NULL,
  status int DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS t_assessment_answer (
  id int NOT NULL AUTO_INCREMENT,
  assessment_id int DEFAULT NULL,
  question_id int DEFAULT NULL,
  answer varchar(255) DEFAULT NULL,
  score int DEFAULT NULL,
  PRIMARY KEY (id),
  KEY assessment_id (assessment_id),
  KEY question_id (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_assessment_question (assessment_type, content, type, options, scores, sort, status) VALUES
(1, 'How tall are you?', 1, '{"A":"Below 160cm","B":"160-170cm","C":"170-180cm","D":"Above 180cm"}', '{"A":60,"B":70,"C":80,"D":90}', 1, 1),
(1, 'How much do you weigh?', 1, '{"A":"Below 50kg","B":"50-60kg","C":"60-70kg","D":"Above 70kg"}', '{"A":60,"B":70,"C":80,"D":90}', 2, 1),
(1, 'How many times do you exercise per week?', 1, '{"A":"0 times","B":"1-2 times","C":"3-4 times","D":"5+ times"}', '{"A":40,"B":60,"C":80,"D":95}', 3, 1),
(2, 'Have you been feeling stressed lately?', 1, '{"A":"Yes, very much","B":"A little","C":"Okay","D":"No"}', '{"A":40,"B":60,"C":80,"D":95}', 1, 1),
(2, 'How is your sleep quality?', 1, '{"A":"Poor","B":"Average","C":"Good","D":"Very good"}', '{"A":40,"B":60,"C":80,"D":95}', 2, 1),
(3, 'Do you smoke?', 1, '{"A":"Every day","B":"Occasionally","C":"Used to, but quit","D":"Never"}', '{"A":40,"B":60,"C":80,"D":95}', 1, 1),
(3, 'Do you drink alcohol?', 1, '{"A":"Every day","B":"Occasionally","C":"Used to, but quit","D":"Never"}', '{"A":40,"B":60,"C":80,"D":95}', 2, 1);

INSERT INTO t_assessment (member_id, type, assessment_date, result, score, health_manager_id, remark, create_time, update_time) VALUES
(1, 1, NOW(), 'Good physical condition', 75, 1, 'Regular check-up', NOW(), NOW()),
(1, 2, NOW(), 'Good mental state', 80, 1, 'Maintain good mindset', NOW(), NOW()),
(2, 1, NOW(), 'Average physical condition', 65, 1, 'Suggest more exercise', NOW(), NOW()),
(2, 3, NOW(), 'Medium health risk', 60, 1, 'Suggest improving lifestyle', NOW(), NOW());

INSERT INTO t_assessment_answer (assessment_id, question_id, answer, score) VALUES
(1, 1, 'B', 70),
(1, 2, 'C', 80),
(1, 3, 'B', 60),
(2, 4, 'C', 80),
(2, 5, 'D', 95),
(3, 1, 'A', 60),
(3, 2, 'B', 70),
(3, 3, 'A', 40),
(4, 6, 'B', 60),
(4, 7, 'B', 60);