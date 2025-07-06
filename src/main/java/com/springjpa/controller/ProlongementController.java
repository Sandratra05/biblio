package com.springjpa.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.springjpa.entity.*;
import com.springjpa.service.*;

@Controller
@RequestMapping("/prolongement")
public class ProlongementController {

    @Autowired
    private ProlongementService prolongementService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private QuotaProlongementService quotaProlongementService;

    @Autowired
    private ProlongementStatutService prolongementStatutService;

    @Autowired
    private StatutProlongementService statutProlongementService;

    @Autowired
    private ProfilService profilService;

    @PostMapping("/demander-prolongement")
    @ResponseBody
    public String prolonger(
        @RequestParam("idPret") Integer idPret,
        @RequestParam("idAdherant") Integer idAdherant
    ) {
        try {
            // 1. Récupérer l'adhérant et son profil
            Adherant adherant = adherantService.findById(idAdherant);
            Profil profil = adherant.getProfil();

            int duree = profilService.getDureePret(profil.getIdProfil());

            // 2. Obtenir le quota de prolongement pour ce profil
            int quota = quotaProlongementService.findQuotaByProfil(profil.getIdProfil());

            // 3. Compter les prolongements actifs (date fin > maintenant + statut "en cours")
            int prolongementsActifs = prolongementService.countProlongementsActifsParAdherant(idAdherant);

            if (prolongementsActifs >= quota) {
                return "Quota de prolongement dépassé.";
            }

            // 4. Insérer le prolongement avec date de fin et id_pret
            Prolongement prolongement = prolongementService.creerProlongement(idPret, duree);



            // 5. Associer le statut "en attente" au prolongement
            StatutProlongement statut = statutProlongementService.findById(1);
            prolongementStatutService.associerStatut(prolongement, statut);

            return "Demande de prolongement enregistrée avec succès.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur : " + e.getMessage();
        }
    }
}
