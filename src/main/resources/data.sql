-- 1. UTENTE
INSERT INTO utente (id, nome, cognome, email, data_di_nascita) VALUES (1, 'Alessia', 'Occhionero', 'alessia@gmail.com', '2001-03-27')ON CONFLICT (id) DO NOTHING;;
INSERT INTO credentials (id, password, ruolo, username, utente_id, enabled) VALUES (1, '$2a$12$AMC0tDbfcTEOKQd3BizQ3e1Ez5JLajLAIskHZt...4PORrJ8mRHd2', 'ADMIN', 'alessia', 1, true);


INSERT INTO utente (id, nome, cognome, email, data_di_nascita) VALUES (2, 'Mario', 'Rossi', 'mario@gmail.com', '1940-12-01');
INSERT INTO credentials (id, password, ruolo, username, utente_id, enabled) VALUES (2, '$2a$12$iXNJVm5AMTxPkJOkIDgMOuumIiTCEfelZEmq/Eyp0CMbsfG4gy5q2', 'DEFAULT', 'MARIO', 2, true);

-- 2. INGREDIENTI (ID 200+)
INSERT INTO ingrediente (id, nome) VALUES (200, 'Sale');
INSERT INTO ingrediente (id, nome) VALUES (201, 'Pepe');
INSERT INTO ingrediente (id, nome) VALUES (202, 'Olio EVO');
INSERT INTO ingrediente (id, nome) VALUES (203, 'Zucchero');
INSERT INTO ingrediente (id, nome) VALUES (204, 'Farina 00');
INSERT INTO ingrediente (id, nome) VALUES (205, 'Latte');
INSERT INTO ingrediente (id, nome) VALUES (206, 'Burro');
INSERT INTO ingrediente (id, nome) VALUES (207, 'Uova');
INSERT INTO ingrediente (id, nome) VALUES (208, 'Zucchero a velo');
INSERT INTO ingrediente (id, nome) VALUES (209, 'Lievito per dolci');
INSERT INTO ingrediente (id, nome) VALUES (210, 'Vanillina');
INSERT INTO ingrediente (id, nome) VALUES (211, 'Limone (scorza)');
INSERT INTO ingrediente (id, nome) VALUES (212, 'Acqua');

-- Specifici Bignè (ID 34-43)
INSERT INTO ingrediente (id, nome) VALUES (34, 'Zucchero di canna');
INSERT INTO ingrediente (id, nome) VALUES (36, 'Lievito di birra');
INSERT INTO ingrediente (id, nome) VALUES (38, 'Cannella');
INSERT INTO ingrediente (id, nome) VALUES (39, 'Noce moscata');
INSERT INTO ingrediente (id, nome) VALUES (42, 'Olio di semi');
INSERT INTO ingrediente (id, nome) VALUES (43, 'Miele');

-- Specifici Spaghetti Lilli (ID 60-69)
INSERT INTO ingrediente (id, nome) VALUES (60, 'Spaghetti');
INSERT INTO ingrediente (id, nome) VALUES (61, 'Carne macinata mista');
INSERT INTO ingrediente (id, nome) VALUES (63, 'Grana grattugiato');
INSERT INTO ingrediente (id, nome) VALUES (64, 'Passata di pomodoro');
INSERT INTO ingrediente (id, nome) VALUES (65, 'Panino bianco raffermo');
INSERT INTO ingrediente (id, nome) VALUES (69, 'Basilico fresco');

-- Specifici Soufflè
INSERT INTO ingrediente (id, nome) VALUES (83, 'Emmenthal grattugiato');
INSERT INTO ingrediente (id, nome) VALUES (86, 'Albumi');

-- Specifici Torta Aurora
INSERT INTO ingrediente (id, nome) VALUES (110, 'Coloranti alimentari');
INSERT INTO ingrediente (id, nome) VALUES (111, 'Confettini');

-- Specifici Gumbo
INSERT INTO ingrediente (id, nome) VALUES (122, 'Salsiccia fresca');
INSERT INTO ingrediente (id, nome) VALUES (123, 'Gamberetti sgusciati');
INSERT INTO ingrediente (id, nome) VALUES (125, 'Sedano');
INSERT INTO ingrediente (id, nome) VALUES (130, 'Alloro');
INSERT INTO ingrediente (id, nome) VALUES (131, 'Tabasco');
INSERT INTO ingrediente (id, nome) VALUES (132, 'Riso cotto');

-- Specifici Crostata
INSERT INTO ingrediente (id, nome) VALUES (141, 'Burro freddo');
INSERT INTO ingrediente (id, nome) VALUES (144, 'Tuorlo');
INSERT INTO ingrediente (id, nome) VALUES (147, 'Uvaspina matura');
INSERT INTO ingrediente (id, nome) VALUES (149, 'Amido di mais');
INSERT INTO ingrediente (id, nome) VALUES (150, 'Succo Limone');

-- Specifici Biscotti Alice
INSERT INTO ingrediente (id, nome) VALUES (175, 'Cacao amaro');

--Specifici Mele Biancaneve(ID 213+)
INSERT INTO ingrediente (id, nome) VALUES (213, 'Mele biologiche');
INSERT INTO ingrediente (id, nome) VALUES (214, 'Stecchini resistenti');
INSERT INTO ingrediente (id, nome) VALUES (215, 'Baccello di vaniglia');
INSERT INTO ingrediente (id, nome) VALUES (216, 'Chiodi di garofano macinati');
INSERT INTO ingrediente (id, nome) VALUES (217, 'Colorante alimentare rosso');

-- ALTRI INGREDIENTI SPECIFICI (ID ORIGINALI)
INSERT INTO ingrediente (id, nome) VALUES (14, 'Cipolle');
INSERT INTO ingrediente (id, nome) VALUES (15, 'Peperoni verdi');
INSERT INTO ingrediente (id, nome) VALUES (16, 'Peperoni rossi');
INSERT INTO ingrediente (id, nome) VALUES (17, 'Zucchine');
INSERT INTO ingrediente (id, nome) VALUES (18, 'Pomodori');
INSERT INTO ingrediente (id, nome) VALUES (19, 'Erbette provenzali');
INSERT INTO ingrediente (id, nome) VALUES (20, 'Aglio');

-- 3. RICETTE

--Ratatouille 
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (4, 'Ratatouille di Remy', 'La ratatouille di Remy è un piatto elegante e colorato ispirato alla cucina francese, reso famoso dal film Ratatouille. È composta da sottili fette di verdure — come zucchine, melanzane, pomodori e peperoni — disposte con cura su una base di salsa di pomodoro e peperoni.', 'Contorno', 10, 25, 'Media', 4, '', '/images/ratatouille.jpg', '2007-06-29', 1);

-- Bignè di Tiana (ID 5)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (5, 'Bignè di Tiana', 'I celebri bignè di New Orleans della Principessa e il Ranocchio: soffici nuvole di pasta fritta coperte di zucchero a velo e (opzionale) miele.', 'Dolce', 180, 3, 'Media', 6, '1. Pesiamo il burro e lo zucchero e trasferiamoli nella ciotola. In un pentolino facciamo riscaldare il latte (senza bollire), versiamolo sul burro e zucchero e mescoliamo finché sciolti. 2. Quando il latte è tiepido, sciogliamo il lievito, poi aggiungiamo l uovo e iniziamo ad impastare. Unite cannella, noce moscata e scorza di limone. 3. Unite il sale alla farina e aggiungetela poco a poco impastando fino a ottenere un composto morbido non appiccicoso. Lasciate lievitare in una ciotola oliata fino al raddoppio (2-3 ore). 4. Stendete l''impasto spesso 1 cm, tagliate dei quadrati e fate riposare 30 min. 5. Friggete in olio caldo (180°) girandoli appena gonfiano (meno di 1 min per lato). Scolate, cospargete di zucchero a velo e, per il tocco della principessa, una colata di miele.', '/images/bigneTiana.jpg', '2009-12-18', 1);

--Mela avvelenata Biancaneve (ID 6)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (6,'Mela Avvelenata di Biancaneve','Anche se in versione non avvelenata e sicuramente gustosa da mangiare, la mela di Biancaneve incanta grandi e piccoli con il suo rosso lucido e profondo.', 'Dolce', 20,24, 'Media', 8,'1. Lavate accuratamente le mele con acqua e bicarbonato, asciugatele bene ed eliminate il picciolo. 2. Inserite uno stecchino al centro di ogni mela, facendo attenzione che sia ben saldo. 3. In un pentolino unite acqua e zucchero e portate sul fuoco medio finche lo zucchero non sara sciolto. 4. Aggiungete il miele e cuocete fino a raggiungere i 100°C. 5. Unite il colorante rosso, i semi di vaniglia, cannella e chiodi di garofano. 6. Proseguite la cottura fino a 150°C. 7. Immergete con cura una mela alla volta nel caramello, ruotandola per ricoprirla completamente. 8. Decorate subito con granella se desiderato e fate raffreddare su carta forno fino a solidificazione.','/images/mela.JPG', '1937-12-22' ,(SELECT id FROM utente WHERE email = 'mario@gmail.com'));

-- Spaghetti Lilli (ID 7)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (7, 'Spaghetti con polpette', 'Alzi la mano chi non ha mai visto Lilli e il Vagabondo! Tra le scene più iconiche del film c''è la romantica cena sotto le stelle, con un piatto di spaghetti e polpette condiviso dai due innamorati', 'Primo', 15, 30, 'Facile', 4, '1. Iniziate dalle polpette. Tagliate a pezzetti il panino e mettetelo a bagno nel latte per una decina di minuti. 2. Predisponete una ciotola sul piano di lavoro ed unite il pane strizzato e sbriciolato, la carne macinata, l''uovo, il grana e il sale. 3. Mescolate con un cucchiaio, quindi passate alle mani. Dovrete ottenere un impasto morbido e modellabile con il quale poter creare delle polpette della grandezza di una noce. Tenetele da parte. 4. Tritate la cipolla e mettetela a soffriggere in un tegame con poco olio. Una volta dorata, unite la passata di pomodoro e fatela insaporire per qualche minuto. 5. Non appena sarà giunta a bollore, unite le polpette con cura e fate cuocere il tutto per 15 minuti avendo cura di girarle almeno una volta. 6. Spegnete la fiamma e mettete a cuocere gli spaghetti in acqua bollente salata fino a cottura completa. 7. Condite gli spaghetti con il sugo e serviteli ponendo sopra qualche polpetta ed una foglia di basilico fresco.', '/images/spaghetti.jpg', '1955-12-15', 1);

-- Soufflè al Formaggio (ID 8)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (8, 'Soufflè al Formaggio', 'Tra i piatti danzanti davanti agli occhi increduli di Belle spicca lui: un antipasto raffinato, soffice e dal gusto incredibile che va servito appena sfornato.', 'Antipasto', 30, 40, 'Media', 4, '1. Preparate la besciamella: sciogliete il burro in una pentola, unite la farina tutta in una volta e mescolate. Cuocete per qualche minuto, poi aggiungete il latte freddo continuando a mescolare finché non si addensa. Regolate di sale, pepe e noce moscata, poi fate raffreddare. 2. Unite i tuorli (dalle 3 uova) e i formaggi grattugiati alla besciamella ormai fredda. 3. Montate a neve ferma i 3 albumi aggiuntivi (e se volete anche quelli avanzati dalle uova) e uniteli al composto delicatamente, senza smontarli. 4. Accendete il forno a 170°. Imburrate lo stampo, versate il composto e livellate. 5. Cuocete per 35-40 minuti alzando gradualmente la temperatura fino a 200°. Sfornate e servite immediatamente prima che si sgonfi!', '/images/souffle.jpg', '1992-12-2', (SELECT id FROM utente WHERE email = 'mario@gmail.com'));

-- Torta Aurora (ID 9)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (9, 'Torta Sbilenca di Aurora', 'Ispirata alla torta di compleanno preparata dalle fate (senza magia ma con tanto amore!). Una torta a strati volutamente imperfetta, coloratissima e glassata generosamente, proprio come farebbe Fauna.', 'Dolce', 40, 35, 'Difficile', 12, '1. Monta le 4 uova con lo zucchero semolato fino a ottenere un composto chiaro e spumoso. 2. Aggiungi 120ml di latte, l''olio e la vaniglia, poi incorpora la farina e il lievito setacciati. 3. Dividi l''impasto in 3 stampi di dimensioni diverse (piccolo, medio, grande) e cuoci a 170°C per 30-35 minuti. 4. Lascia raffreddare, poi impila gli strati uno sull''altro in modo volutamente storto (o usa stecchini per fissarli). 5. Prepara la glassa: monta il burro morbido con lo zucchero a velo, aggiungendo 3 cucchiai di latte finché è cremosa. Dividila in ciotole e usa i coloranti (rosa, verde, blu). 6. Decora generosamente con la sac-à-poche lasciando che la glassa coli un po'' e aggiungi confettini. Più è tremolante, meglio è!', '/images/torta.jpg', '1959-12-01', 1);

-- Gumbo di Tiana (ID 10)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (10, 'Gumbo di Tiana', 'Il piatto forte di Tiana: una zuppa tipica della Louisiana che unisce carne, pesce e spezie, servita con riso bianco. Un piatto unico corroborante con una marcia in più data dal tabasco.', 'Primo', 20, 45, 'Media', 4, '1. Fate cuocere la salsiccia spellata e sbriciolata in una padella con poco olio fino a doratura. Prelevatela e tenetela da parte. Nella stessa padella rosolate cipolla e aglio tritati. 2. In una pentola grande sciogliete il burro, unite la farina e mescolate per ottenere il roux. 3. Aggiungete sedano, peperone, sale, pepe e il soffritto di cipolla/aglio. Fate insaporire 2 minuti poi versate l''acqua. 4. Unite i pomodori a dadini e l''alloro. Portate a bollore, poi abbassate la fiamma e cuocete a fuoco lento per 30 minuti. 5. Aggiungete infine le salsicce cotte, i gamberetti e il tabasco. Cuocete per altri 15 minuti. Servite caldissimo accompagnato da riso bianco.', '/images/gumboTiana.jpg', '2009-12-19', 1);

-- Crostata Biancaneve (ID 11)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (11, 'Crostata di uva spina', 'Dolce e profumata, con un guscio di pasta frolla croccante e un ripieno acidulo e goloso. La torta che Biancaneve prepara per i sette nani, perfetta da gustare come nelle fiabe.', 'Dolce', 60, 40, 'Media', 8, '1. Per la frolla: fate una fontana con la farina, aggiungete il burro freddo a pezzetti e lavorate fino a ottenere un composto sabbioso. Unite zucchero a velo, uovo, tuorlo, scorza di limone e un pizzico di sale. Impastate velocemente, formate un panetto e fate riposare in frigo per 30 min. 2. Per il ripieno: lavate le uvaspine, mescolatele in una ciotola con zucchero, amido di mais e succo di limone. Lasciate riposare. 3. Assemblaggio: stendete la frolla (3-4 mm) e foderate una tortiera imburrata. Versate il ripieno di uvaspine. Con la frolla avanzata create una griglia sulla superficie. 4. Cuocete in forno preriscaldato a 180°C per 35-40 minuti fino a doratura. Lasciate raffreddare prima di servire.', '/images/crostata.JPG', '1937-12-21', 1);

-- Crema Edgar (ID 12)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (12, 'Crema di Edgar','Una crema dolce, vellutata e avvolgente, perfetta da gustare al cucchiaio o per farcire dolci. Ispirata alla ricetta (in versione non corretta!) preparata dal maggiordomo Edgar.',  'Dolce', 10, 15, 'Facile', 6, '1. In una ciotola, sbatti i tuorli con lo zucchero fino a ottenere un composto chiaro e spumoso. 2. Aggiungi la farina setacciata e un pizzico di sale, mescolando bene per evitare grumi. 3. Scalda il latte con la vaniglia in un pentolino fino quasi a ebollizione. 4. Versa il latte caldo a filo sul composto di uova, mescolando continuamente. 5. Riporta tutto sul fuoco basso e cuoci mescolando costantemente fino a ottenere una crema densa e vellutata. 6. Togli dal fuoco e, se vuoi, incorpora il burro per rendere la crema più lucida. 7. Lascia raffreddare leggermente prima di servire, oppure usa la crema per farcire dolci e crostate.', '/images/crema.jpg', '1971-11-13',(SELECT id FROM utente WHERE email = 'mario@gmail.com') );

-- Biscotti Alice (ID 13)
INSERT INTO ricetta (id, titolo, descrizione, categoria, tempo_di_preparazione, tempo_di_cottura, difficolta, porzioni, procedimento, image_url, data_inserimento, autore_id) VALUES (13, 'Biscotti Mangiami', 'Biscotti magici ispirati ad Alice nel Paese delle Meraviglie: fragranti, colorati e curiosamente invitanti, perfetti da gustare tuffandosi in un mondo di fantasia.', 'Dolce', 30, 12, 'Media', 4, '1. Preparate l''impasto: grattugiate la buccia di limone e mescolatela con zucchero e sale. Lavorate il burro a crema con lo zucchero, unite la farina e infine l''uovo. Impastate e fate riposare in frigo 30 min. 2. Formate i biscotti: dividete l''impasto e coloratene alcune parti (viola, arancione, cacao). Stendete e ritagliate forme strane (cerchi, scacchiere, carte da gioco). Raffreddate le forme in frigo 15 min. 3. Cuocete in forno a 180°C per 12 minuti. Fate raffreddare. 4. Preparate la glassa mescolando zucchero a velo e acqua. Dividetela e coloratela (rosa, azzurro). 5. Decorate i biscotti con le scritte "Eat Me", "Try Me" o disegni a piacere.', '/images/biscottiMangiami.jpg', '1951-12-06', 1);



-- 4. COLLEGAMENTI (RICETTA_INGREDIENTE) - ID VERIFICATI SUI LOG
-- Ratatouille (ID 4)
-- ==================================================================================
-- COLLEGAMENTI RICETTA_INGREDIENTE PER RATATOUILLE (ID 4)
-- ==================================================================================
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (101, 200, 'g', 14, 4); --cipolla
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (102, 300, 'g', 15, 4);--- Peperoni verdi 
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (103, 300, 'g', 16, 4);-- Peperoni rossi 
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (104, 300, 'g', 17, 4);--  Zucchine 
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (105, 400, 'g', 18, 4);--  Pomodori 
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (106, 1, 'mezzo cucchiaio', 19, 4);--Erbette provenzali 
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (107, 1, 'spicchio', 20, 4);-- aglio
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (108, 0, 'q.b.', 202, 4); -- Olio 
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (109, 0, 'q.b.', 200, 4); --sale 
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (110, 0, 'q.b.', 201, 4);-- Pepe 

-- ==================================================================================
-- 4. RICETTA_INGREDIENTE (COLLEGAMENTI COMPLETI)
-- ==================================================================================

-- Bignè di Tiana (ID 5)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (111, 400, 'g', 204, 5); -- Farina 00
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (112, 245, 'g', 205, 5); -- Latte
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (113, 30, 'g', 206, 5);  -- Burro
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (114, 15, 'g', 203, 5);  -- Zucchero
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (115, 15, 'g', 34, 5);   -- Zucchero di canna
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (116, 1, 'pz', 207, 5);  -- Uova
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (117, 10, 'g', 36, 5);   -- Lievito di birra
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (118, 1, 'pz', 211, 5);  -- Limone (scorza)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (119, 1, 'mezzo cucch.', 38, 5); -- Cannella
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (120, 1, 'mezzo cucch.', 39, 5); -- Noce moscata
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (121, 1, 'cucchiaino', 200, 5); -- Sale
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (122, 0, 'q.b.', 208, 5); -- Zucchero velo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (123, 0, 'q.b.', 42, 5);  -- Olio di semi
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (124, 0, 'q.b.', 43, 5);  -- Miele


-- Mela Biancaneve (ID 6) 
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (250, 8, 'pz', 213, 6); -- Mele biologiche
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (251, 8, 'pz', 214, 6); -- Stecchini resistenti
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (252, 900, 'g', 203, 6); -- Zucchero
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (253, 200, 'ml', 212, 6); -- Acqua
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (254, 80, 'g', 43, 6);   -- Miele
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (255, 1, 'pz', 215, 6);  -- Baccello di vaniglia
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (256, 0, 'q.b.', 38, 6);  -- Cannella
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (257, 0, 'q.b.', 216, 6); -- Chiodi di garofano
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (258, 0, 'q.b.', 217, 6); -- Colorante rosso

-- Spaghetti Lilli e il Vagabondo (ID 7)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (125, 320, 'g', 60, 7); -- Spaghetti
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (126, 300, 'g', 61, 7); -- Carne macinata
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (127, 1, 'pz', 207, 7);  -- Uovo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (128, 40, 'g', 63, 7);  -- Grana
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (129, 750, 'ml', 64, 7); -- Passata pomodoro
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (130, 1, 'pz', 65, 7);   -- Panino
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (131, 0, 'q.b.', 205, 7); -- Latte
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (132, 0, 'q.b.', 200, 7); -- Sale
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (133, 1, 'metà', 14, 7);  -- Cipolla
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (134, 0, 'q.b.', 69, 7);  -- Basilico
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (135, 0, 'q.b.', 202, 7); -- Olio EVO

-- Soufflè al Formaggio (ID 8)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (136, 50, 'g', 204, 8);  -- Farina 00
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (137, 50, 'g', 206, 8);  -- Burro
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (138, 350, 'ml', 205, 8); -- Latte
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (139, 150, 'g', 83, 8);  -- Emmenthal
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (140, 50, 'g', 63, 8);   -- Grana
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (141, 3, 'pz', 207, 8);  -- Uova
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (142, 3, 'pz', 86, 8);   -- Albumi
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (143, 0, 'q.b.', 39, 8);  -- Noce moscata
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (144, 0, 'q.b.', 200, 8); -- Sale
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (145, 0, 'q.b.', 201, 8); -- Pepe

-- Torta Aurora (ID 9)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (146, 300, 'g', 204, 9); -- Farina
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (147, 250, 'g', 203, 9); -- Zucchero
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (148, 4, 'pz', 207, 9);  -- Uova
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (149, 120, 'ml', 205, 9); -- Latte
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (150, 120, 'ml', 42, 9);  -- Olio semi
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (151, 1, 'bustina', 209, 9); -- Lievito
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (152, 1, 'cucchiaino', 210, 9); -- Vaniglia
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (153, 250, 'g', 206, 9); -- Burro (glassa)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (154, 500, 'g', 208, 9); -- Zucchero velo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (155, 3, 'cucchiai', 205, 9); -- Latte (glassa)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (156, 0, 'q.b.', 110, 9); -- Coloranti
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (157, 0, 'q.b.', 111, 9); -- Confettini

-- Gumbo di Tiana (ID 10)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (158, 230, 'g', 206, 10); -- Burro
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (159, 115, 'g', 204, 10); -- Farina
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (160, 200, 'g', 122, 10); -- Salsiccia
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (161, 500, 'g', 123, 10); -- Gamberetti
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (162, 1, 'pz', 14, 10);   -- Cipolla
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (163, 2, 'coste', 125, 10); -- Sedano
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (164, 1, 'pz', 16, 10);   -- Peperone rosso
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (165, 250, 'g', 18, 10); -- Pomodori
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (166, 2, 'spicchi', 20, 10); -- Aglio
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (167, 2000, 'ml', 212, 10); -- Acqua
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (168, 2, 'foglie', 130, 10); -- Alloro
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (169, 0, 'q.b.', 131, 10); -- Tabasco
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (170, 0, 'q.b.', 132, 10); -- Riso
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (171, 0, 'q.b.', 200, 10); -- Sale

-- Crostata di uva spina (ID 11)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (172, 300, 'g', 204, 11); -- Farina
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (173, 150, 'g', 141, 11); -- Burro freddo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (174, 120, 'g', 208, 11); -- Zucchero velo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (175, 1, 'pz', 207, 11);  -- Uovo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (176, 1, 'pz', 144, 11);  -- Tuorlo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (177, 1, 'pz', 211, 11);  -- Limone
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (178, 0, 'q.b.', 200, 11); -- Sale
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (179, 500, 'g', 147, 11); -- Uvaspina
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (180, 100, 'g', 203, 11); -- Zucchero
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (181, 1, 'cucchiaio', 149, 11); -- Amido
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (182, 1, 'mezzo', 150, 11); -- Succo limone

-- Crema di Edgar (ID 12)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (183, 500, 'ml', 205, 12); -- Latte
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (184, 4, 'pz', 144, 12);   -- Tuorli
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (185, 120, 'g', 203, 12);  -- Zucchero
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (186, 40, 'g', 204, 12);   -- Farina
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (187, 1, 'cucchiaino', 210, 12); -- Vaniglia
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (188, 1, 'pizzico', 200, 12); -- Sale
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (189, 50, 'g', 206, 12);   -- Burro

-- Biscotti Mangiami (ID 13)
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (190, 200, 'g', 204, 13); -- Farina
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (191, 115, 'g', 206, 13); -- Burro
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (192, 60, 'g', 203, 13);  -- Zucchero
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (193, 1, 'pz', 207, 13);  -- Uovo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (194, 1, 'pz', 211, 13);  -- Limone
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (195, 0, 'q.b.', 175, 13); -- Cacao
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (196, 0, 'q.b.', 110, 13); -- Coloranti
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (197, 1, 'pizzico', 200, 13); -- Sale
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (198, 100, 'g', 208, 13); -- Zucchero velo
INSERT INTO ricetta_ingrediente (id, quantita, unita, ingrediente_id, ricetta_id) VALUES (199, 2, 'cucchiai', 212, 13); -- Acqua

-- 5. SEQUENZE
ALTER SEQUENCE utente_seq RESTART WITH 100;
ALTER SEQUENCE credentials_seq RESTART WITH 100;
ALTER SEQUENCE ricetta_seq RESTART WITH 50;
ALTER SEQUENCE ingrediente_seq RESTART WITH 300;
ALTER SEQUENCE ricetta_ingrediente_seq RESTART WITH 400;