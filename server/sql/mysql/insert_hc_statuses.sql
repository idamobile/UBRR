-- Statuses for credit cards
INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (1, '1', 'неактивная', 'Inactive', 'Неактивная');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (2, '1', 'Активная', 'Active', 'Активная');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (3, '1', 'заблокирована', 'Locked', 'Заблокирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (4, '1', 'Утеряна', 'Revoked', 'Аннулирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (5, '1', 'Украдена', 'Revoked', 'Аннулирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (6, '1', 'аннулирована', 'Revoked', 'Аннулирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (7, '1', 'выдана запасная карта', 'Inactive', 'Аннулирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (8, '1', 'обновлена', 'Inactive', 'Неактивная');

-- Statuses for Credits
INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (9, '2', 'Действующий', 'Active', 'Действующий');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (10, '2', 'Подписанный', 'Signed', 'Подписанный');

-- Statuses for Debit Cards
INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (11, '3', '0', 'Locked', 'Заблокирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (12, '3', '1', 'Active', 'Активная');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (13, '3', '8', 'Revoked', 'Аннулирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (14, '3', '9', 'Revoked', 'Аннулирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (15, '3', 'C', 'Inactive', 'Неактивная');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (16, '3', '4', 'Locked by collection agency', 'Заблокирована службой взыскания');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (17, '3', 'F', 'Revoked', 'Аннулирована');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (18, '3', 'A', 'Temporarily locked', 'Временно заблокирована');

-- Statuses for Deposits
INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (19, '4', '0', 'Opened', 'Открыт');

INSERT INTO ida_hc_statuses (id, product_kind, status_key, status_value_en, status_value_ru)
VALUES (20, '4', '1', 'Closed', 'Закрыт');
