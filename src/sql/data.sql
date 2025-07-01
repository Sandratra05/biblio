-- CATEGORIE
INSERT INTO categorie (id_categorie, nom_categorie) VALUES (1, 'Roman');
INSERT INTO categorie (id_categorie, nom_categorie) VALUES (2, 'Essai');

-- PROFIL
INSERT INTO profil (id_profil, nom_profil, quota_pret, quota_reservation) VALUES (1, 'Etudiant', 3, 2);
INSERT INTO profil (id_profil, nom_profil, quota_pret, quota_reservation) VALUES (2, 'Professeur', 10, 5);

-- INSCRIPTION_PROFIL
INSERT INTO inscription_profil (id_inscri_profil, duree, id_profil) VALUES (1, 365, 1);
INSERT INTO inscription_profil (id_inscri_profil, duree, id_profil) VALUES (2, 730, 2);

-- ADMIN
INSERT INTO admin (id_admin, nom_admin, prenom_admin, password) VALUES (1, 'Martin', 'Paul', 'admin123');
INSERT INTO admin (id_admin, nom_admin, prenom_admin, password) VALUES (2, 'Durand', 'Sophie', 'admin456');

-- TYPE_PRET
INSERT INTO type_pret (id_type_pret, type) VALUES (1, 'Sur place');
INSERT INTO type_pret (id_type_pret, type) VALUES (2, 'A domicile');

-- DUREE_PRET
INSERT INTO duree_pret (id_duree_pret, duree, id_profil) VALUES (1, 15, 1);
INSERT INTO duree_pret (id_duree_pret, duree, id_profil) VALUES (2, 30, 2);

-- STATUT_RESERVATION
INSERT INTO statut_reservation (id_statut, nom_statut) VALUES (1, 'En attente');
INSERT INTO statut_reservation (id_statut, nom_statut) VALUES (2, 'Validée');

-- AUTEUR
INSERT INTO auteur (id_auteur, nom_auteur, prenom_auteur) VALUES (1, 'Hugo', 'Victor');
INSERT INTO auteur (id_auteur, nom_auteur, prenom_auteur) VALUES (2, 'Zola', 'Emile');

-- EDITEUR
INSERT INTO editeur (id_editeur, nom_editeur, localisation) VALUES (1, 'Gallimard', 'Paris');
INSERT INTO editeur (id_editeur, nom_editeur, localisation) VALUES (2, 'Flammarion', 'Lyon');

-- LIVRE
INSERT INTO livre (id_livre, titre, isbn, langue, annee_publication, synopsis, nb_page, id_editeur, id_auteur) VALUES
(1, 'Les Misérables', '9781234567890', 'Français', 1862, 'Roman historique', 1200, 1, 1),
(2, 'Germinal', '9780987654321', 'Français', 1885, 'Roman social', 600, 2, 2),
(3, 'Notre-Dame de Paris', '9781111111111', 'Français', 1831, 'Roman gothique', 940, 1, 1),
(4, 'L’Assommoir', '9782222222222', 'Français', 1877, 'Roman naturaliste', 500, 2, 2),
(5, 'Le Dernier Jour d’un Condamné', '9783333333333', 'Français', 1829, 'Roman engagé', 200, 1, 1);

-- ADHERANT
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, id_profil) VALUES (1, 'Martin', 'Paul', 'passprof', 2);
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, id_profil) VALUES (2, 'Durand', 'Sophie', 'passetud', 1);

-- INSCRIPTION
INSERT INTO inscription (id_inscription, date_inscription, etat, id_adherant) VALUES (1, '2024-01-01 10:00:00', TRUE, 1);
INSERT INTO inscription (id_inscription, date_inscription, etat, id_adherant) VALUES (2, '2024-02-01 11:00:00', TRUE, 2);

-- PENALITE
INSERT INTO penalite (id_penalite, duree, date_penalite, id_adherant) VALUES (1, 7, '2024-06-01 09:00:00', 2);

-- EXEMPLAIRE
INSERT INTO exemplaire (id_exemplaire, dispo, id_livre) VALUES (1, FALSE, 1);
INSERT INTO exemplaire (id_exemplaire, dispo, id_livre) VALUES (2, FALSE, 2);
INSERT INTO exemplaire (id_exemplaire, dispo, id_livre) VALUES (3, FALSE, 3);
INSERT INTO exemplaire (id_exemplaire, dispo, id_livre) VALUES (4, FALSE, 4);
INSERT INTO exemplaire (id_exemplaire, dispo, id_livre) VALUES (5, FALSE, 5);

-- PRET
INSERT INTO pret (id_pret, date_debut, id_admin, id_type_pret, id_exemplaire, id_adherant) VALUES (1, '2024-06-10 09:00:00', 1, 1, 1, 1);
INSERT INTO pret (id_pret, date_debut, id_admin, id_type_pret, id_exemplaire, id_adherant) VALUES (2, '2024-06-11 10:00:00', 2, 2, 2, 2);

-- RESERVATION
INSERT INTO reservation (id_reservation, date_de_reservation, id_admin, id_statut, id_exemplaire, id_adherant) VALUES (1, '2024-06-12 14:00:00', 1, 1, 3, 1);
INSERT INTO reservation (id_reservation, date_de_reservation, id_admin, id_statut, id_exemplaire, id_adherant) VALUES (2, '2024-06-13 15:00:00', 2, 2, 4, 2);

-- FIN_PRET
INSERT INTO fin_pret (id_fin_pret, date_fin, id_pret) VALUES (1, '2024-06-20 09:00:00', 1);
INSERT INTO fin_pret (id_fin_pret, date_fin, id_pret) VALUES (2, '2024-06-21 10:00:00', 2);

-- CATEGORIE_LIVRE
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES (1, 1);
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES (2, 2);
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES (3, 1);
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES (4, 2);
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES (5, 1);

-- QUOTA_TYPE_PRET
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES (1, 1, 2); -- Etudiant, Sur place
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES (1, 2, 1); -- Etudiant, A domicile
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES (2, 1, 5); -- Professeur, Sur place
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES (2, 2, 5); -- Professeur, A domicile

SELECT COUNT(p.id_pret) FROM pret p
WHERE p.id_adherant = 1
AND p.id_type_pret = 1
AND p.id_pret NOT IN (SELECT f.id_pret FROM fin_pret f)