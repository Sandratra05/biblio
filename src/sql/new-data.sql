-- AUTEUR
INSERT INTO auteur (id_auteur, nom_auteur, prenom_auteur) VALUES (1, 'Hugo', 'Victor');
INSERT INTO auteur (id_auteur, nom_auteur, prenom_auteur) VALUES (2, 'Camus', 'Albert');
INSERT INTO auteur (id_auteur, nom_auteur, prenom_auteur) VALUES (3, 'Rowling', 'J.K.');


-- EDITEUR
INSERT INTO editeur (id_editeur, nom_editeur, localisation) VALUES (1, 'Gallimard', 'Paris');
INSERT INTO editeur (id_editeur, nom_editeur, localisation) VALUES (2, 'Flammarion', 'Lyon');
INSERT INTO editeur (id_editeur, nom_editeur, localisation) VALUES (3, 'Seuil', 'Paris');

-- CATEGORIE
INSERT INTO categorie (id_categorie, nom_categorie) VALUES (1, "Littérature classique");
INSERT INTO categorie (id_categorie, nom_categorie) VALUES (2, "Philosophie");
INSERT INTO categorie (id_categorie, nom_categorie) VALUES (3, "Jeunesse / Fantastique");

-- PROFIL
INSERT INTO profil (id_profil, nom_profil, quota_pret, quota_reservation, nb_jour_penalite) VALUES (1, "Etudiant", 3, 1, 10); -- Etudiant
INSERT INTO profil (id_profil, nom_profil, quota_pret, quota_reservation, nb_jour_penalite) VALUES (2, "Enseignant", 5, 2, 9); -- Prof
INSERT INTO profil (id_profil, nom_profil, quota_pret, quota_reservation, nb_jour_penalite) VALUES (3, "Professionnel", 3, 3, 8); -- Etudiant

-- ADMIN
INSERT INTO admin (id_admin, nom_admin, prenom_admin, password) VALUES (1, 'Dupont', 'Jean', 'admin1');
INSERT INTO admin (id_admin, nom_admin, prenom_admin, password) VALUES (2, 'Martin', 'Claire', 'admin2');

-- TYPE_PRET
INSERT INTO type_pret (id_type_pret, type) VALUES (1, 'A domicile');

-- DUREE_PRET
INSERT INTO duree_pret (id_duree_pret, duree, id_profil) VALUES (1, 7, 1);
INSERT INTO duree_pret (id_duree_pret, duree, id_profil) VALUES (2, 9, 2);
INSERT INTO duree_pret (id_duree_pret, duree, id_profil) VALUES (3, 12, 3);

-- Insertion pour le profil 1
INSERT INTO quota_prolongement (quota, id_profil) 
VALUES (3, 1);

-- Insertion pour le profil 2
INSERT INTO quota_prolongement (quota, id_profil) 
VALUES (5, 2);

INSERT INTO quota_prolongement (quota, id_profil) 
VALUES (7, 3);

-- STATUT_RESERVATION
INSERT INTO statut_reservation (id_statut_reservation, nom_statut) VALUES (1, 'En attente');
INSERT INTO statut_reservation (id_statut_reservation, nom_statut) VALUES (2, 'Validée');
INSERT INTO statut_reservation (id_statut_reservation, nom_statut) VALUES (3, 'Annulée');


INSERT INTO statut_prolongement (nom_statut) VALUES ('En attente');
INSERT INTO statut_prolongement (nom_statut) VALUES ('En cours');
INSERT INTO statut_prolongement (nom_statut) VALUES ('Annule');
INSERT INTO statut_prolongement (nom_statut) VALUES ('Termine');

-- LIVRE (5 livres)
INSERT INTO livre (id_livre, titre, isbn, langue, annee_publication, synopsis, nb_page, age_requis, id_editeur, id_auteur)
VALUES (1, 'Les Miserables', '9782070409189', 'Français', 1862, 'Roman historique', 1200, 5, 1, 1);
INSERT INTO livre (id_livre, titre, isbn, langue, annee_publication, synopsis, nb_page, age_requis, id_editeur, id_auteur)
VALUES (2, 'L`Etranger', '9782070360022', 'Français', 1885, 'Roman social', 600, 5, 2, 2);
INSERT INTO livre (id_livre, titre, isbn, langue, annee_publication, synopsis, nb_page, age_requis, id_editeur, id_auteur)
VALUES (3, 'Harry Potter à l`école des sorciers', '9782070643026', 'Français', 1844, 'Jeune et fantastique', 1300, 5, 1, 3);

-- ADHERANT (4 adhérants, 2 profs, 2 étudiants)
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, date_naissance, id_profil)
VALUES (1, 'Bensaïd', 'Amine', 'etu1', '1980-05-10', 1);
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, date_naissance, id_profil)
VALUES (2, 'El Khattabi', 'Sarah', 'etu2', '1978-11-22', 1);
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, date_naissance, id_profil)
VALUES (3, 'Moujahid', 'Youssef', 'etu3', '2002-03-15', 1);

INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, date_naissance, id_profil)
VALUES (4, 'Benali', 'Nadia', 'ens1', '2008-07-30', 2);
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, date_naissance, id_profil)
VALUES (5, 'Haddadi', 'Karim', 'ens2', '2008-07-30', 2);
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, date_naissance, id_profil)
VALUES (6, 'Touhami', 'Salima', 'ens3', '2008-07-30', 2);

INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, date_naissance, id_profil)
VALUES (7, 'El Mansouri', 'Rachid', 'prof1', '2008-07-30', 3);
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, date_naissance, id_profil)
VALUES (8, 'Amina', 'Zerouali', 'prof2', '2008-07-30', 3);



-- INSCRIPTION (dates en 2025)
INSERT INTO inscription (id_inscription, date_debut, date_fin, id_adherant)
VALUES (1, '2025-02-01 00:00:00', '2025-07-24 23:59:59', 1);
INSERT INTO inscription (id_inscription, date_debut, date_fin, id_adherant)
VALUES (2, '2025-02-01 00:00:00', '2025-07-01 23:59:59', 2);
INSERT INTO inscription (id_inscription, date_debut, date_fin, id_adherant)
VALUES (3, '2025-04-01 00:00:00', '2025-12-01 23:59:59', 3);
INSERT INTO inscription (id_inscription, date_debut, date_fin, id_adherant)
VALUES (4, '2025-07-01 00:00:00', '2026-07-01 23:59:59', 4);
INSERT INTO inscription (id_inscription, date_debut, date_fin, id_adherant)
VALUES (5, '2025-08-01 00:00:00', '2026-05-01 23:59:59', 5);
INSERT INTO inscription (id_inscription, date_debut, date_fin, id_adherant)
VALUES (6, '2025-07-01 00:00:00', '2025-06-01 23:59:59', 6);
INSERT INTO inscription (id_inscription, date_debut, date_fin, id_adherant)
VALUES (7, '2025-06-01 00:00:00', '2025-12-01 23:59:59', 7);
INSERT INTO inscription (id_inscription, date_debut, date_fin, id_adherant)
VALUES (8, '2024-10-01 00:00:00', '2025-06-01 23:59:59', 8);

-- EXEMPLAIRE (3 exemplaires par livre)
INSERT INTO exemplaire (id_exemplaire, id_livre) VALUES (1, 1);
INSERT INTO exemplaire (id_exemplaire, id_livre) VALUES (2, 1);
INSERT INTO exemplaire (id_exemplaire, id_livre) VALUES (3, 1);
INSERT INTO exemplaire (id_exemplaire, id_livre) VALUES (4, 2);
INSERT INTO exemplaire (id_exemplaire, id_livre) VALUES (5, 2);
INSERT INTO exemplaire (id_exemplaire, id_livre) VALUES (6, 3);

-- CATEGORIE_LIVRE (chaque livre dans une catégorie différente pour l'exemple)
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES (1, 1);
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES (2, 2);
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES (3, 3);

-- QUOTA_TYPE_PRET (exemple pour chaque profil/type)
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES (1, 1, 2);
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES (2, 1, 3);
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES (3, 1, 4);


INSERT INTO ferie (jour_ferie) VALUES 
('2025-07-13'),
('2025-07-19'),
('2025-07-20'),
('2025-07-26'),
('2025-07-27'),
('2025-08-03'),
('2025-08-10'),
('2025-08-17');

-- RESTRICTION_CATEGORIE (aucune restriction pour l'exemple)
-- (si tu veux tester des restrictions, ajoute des lignes ici)

-- SELECT COUNT(*) FROM pret p WHERE p.id_adherant = 4 AND p.id_type_pret = 1 AND p.id_pret NOT IN (SELECT r.id_pret FROM retour r);
-- SELECT * FROM pret p WHERE p.id_exemplaire = 3;


-- -- Tu peux faire cette requête SQL pour voir quels exemplaires sont en cours :
-- SELECT p.*
-- FROM pret p
-- LEFT JOIN retour r ON p.id_pret = r.id_pret
-- LEFT JOIN fin_pret f ON p.id_pret = f.id_pret
-- WHERE r.id_retour IS NULL AND f.id_fin_pret IS NULL;

-- SELECT p.*
-- FROM pret p
-- WHERE p.id_adherant = 3
--   AND p.id_pret NOT IN (
--       SELECT r.id_pret FROM retour r
--   )
--   AND (
--       NOT EXISTS (
--           SELECT 1 FROM fin_pret f WHERE f.id_pret = p.id_pret
--       )
--       OR EXISTS (
--           SELECT 1 FROM fin_pret f WHERE f.id_pret = p.id_pret AND f.date_fin > NOW()
--       )
--   );