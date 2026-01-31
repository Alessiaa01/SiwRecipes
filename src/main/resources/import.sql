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

