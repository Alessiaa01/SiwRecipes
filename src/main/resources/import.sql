-- ==================================================================================
-- 1. UTENTE
-- ==================================================================================
INSERT INTO utente (id, nome, cognome, email, data_di_nascita) VALUES (1, 'Alessia', 'Occhionero', 'alessia@gmail.com', '2001-03-27');

-- ==================================================================================
-- 2. CREDENZIALI (CORRETTO)
-- Ho aggiunto la colonna 'id' all'inizio e il valore '1'
-- ==================================================================================
INSERT INTO credentials (id, password, ruolo, username, utente_id, enabled) VALUES (1, '$2a$12$AMC0tDbfcTEOKQd3BizQ3e1Ez5JLajLAIskHZt...4PORrJ8mRHd2', 'ADMIN', 'alessia', 1, true);

-- RESET SEQUENZE (Fondamentale: diciamo a Java di partire da 100 per i prossimi inserimenti)
ALTER SEQUENCE utente_seq RESTART WITH 100;
ALTER SEQUENCE credentials_seq RESTART WITH 100;



-- ==================================================================================
-- 2. RICETTA (Ratatouille - ID 4)
-- ==================================================================================
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (4, 'Ratatouille di Remy', 'Un contorno colorato...', 'Contorno', 10, 25, 'Media', 4, '1. Lavate le verdure...', '/images/ratatouille.JPG', '2007-06-29', 1);

-- ==================================================================================
-- 3. INGREDIENTI (Passaggio 1: Creazione Ingredienti)
-- ==================================================================================
INSERT INTO ingrediente (id, nome) VALUES (14, 'Cipolle');
INSERT INTO ingrediente (id, nome) VALUES (15, 'Peperoni verdi');
INSERT INTO ingrediente (id, nome) VALUES (16, 'Peperoni rossi');
INSERT INTO ingrediente (id, nome) VALUES (17, 'Zucchine');
INSERT INTO ingrediente (id, nome) VALUES (18, 'Pomodori');

-- ==================================================================================
-- 4. COLLEGAMENTO (Passaggio 2: Associazione nella tabella ricetta_ingredienti)
-- Nota: Controlla in pgAdmin se le colonne si chiamano ricetta_id e ingredienti_id
-- ==================================================================================
-- Cambia 'ricetta_ingrediente' con il nome che Hibernate genera per la tua lista
INSERT INTO ricetta_ricetta_ingredienti (ricetta_id, ricetta_ingredienti_id) VALUES (4, 14);
INSERT INTO ricetta_ricetta_ingredienti (ricetta_id, ricetta_ingredienti_id) VALUES (4, 15);
INSERT INTO ricetta_ricetta_ingredienti (ricetta_id, ricetta_ingredienti_id) VALUES (4, 16);
INSERT INTO ricetta_ricetta_ingredienti (ricetta_id, ricetta_ingredienti_id) VALUES (4, 17);
INSERT INTO ricetta_ricetta_ingredienti (ricetta_id, ricetta_ingredienti_id) VALUES (4, 18);

-- ==================================================================================
-- 5. TAGS E SEQUENZE
-- ==================================================================================
INSERT INTO ricetta_tags (ricetta_id, tags) VALUES (4, 'Vegano');
INSERT INTO ricetta_tags (ricetta_id, tags) VALUES (4, 'Senza Glutine');

SELECT setval('ricetta_seq', 100);
SELECT setval('ingrediente_seq', 200);