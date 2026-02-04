-- ==================================================================================
-- 1. UTENTE
-- ==================================================================================
INSERT INTO utente (id, nome, cognome, email, data_di_nascita) VALUES (1, 'Alessia', 'Occhionero', 'alessia@gmail.com', '2001-03-27');

-- ==================================================================================
-- 2. CREDENZIALI (CORRETTO)
-- Ho aggiunto la colonna 'id' all'inizio e il valore '1'
-- ==================================================================================
INSERT INTO credentials (id, password, ruolo, username, utente_id, enabled) VALUES (1, '$2a$12$AMC0tDbfcTEOKQd3BizQ3e1Ez5JLajLAIskHZt...4PORrJ8mRHd2', 'ADMIN', 'alessia', 1, true);





-- Ricetta
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (4, 'Ratatouille di Remy', 'Un contorno colorato...', 'Contorno', 10, 25, 'Media', 4, '1. Lavate le verdure...', '/images/ratatouille.JPG', '2007-06-29', 1);

-- Ingredienti
INSERT INTO ingrediente (id, nome) VALUES (14, 'Cipolle');
INSERT INTO ingrediente (id, nome) VALUES (15, 'Peperoni verdi');
INSERT INTO ingrediente (id, nome) VALUES (16, 'Peperoni rossi');
INSERT INTO ingrediente (id, nome) VALUES (17, 'Zucchine');
INSERT INTO ingrediente (id, nome) VALUES (18, 'Pomodori');

-- Collegamento (Tabella ricetta_ingrediente)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (101, 2, 'pezzi', 14, 4);
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (102, 1, 'pezzo', 15, 4);
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (103, 1, 'pezzo', 16, 4);
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (104, 300, 'grammi', 17, 4);
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (105, 4, 'pezzi', 18, 4);

-- Reset Sequenze (Fondamentale per evitare errori "duplicate key" successivi)
ALTER SEQUENCE utente_seq RESTART WITH 100;
ALTER SEQUENCE credentials_seq RESTART WITH 100;
ALTER SEQUENCE ricetta_seq RESTART WITH 100;
ALTER SEQUENCE ingrediente_seq RESTART WITH 100;
ALTER SEQUENCE ricetta_ingrediente_seq RESTART WITH 200;
