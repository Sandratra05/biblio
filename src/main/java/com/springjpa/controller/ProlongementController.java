package com.springjpa.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.springjpa.entity.*;
import com.springjpa.service.*;

@Controller
@RequestMapping("/prolongement")
public class ProlongementController {

    @Autowired
    private PretService pretService;

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
            // 1. Récupérer l'adhérant et son profil et le pret
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

    @GetMapping("/prets-attente")
    public String afficherPretsEnAttente(Model model) {
        List<Pret> prets = pretService.getPretsAvecProlongementEnAttente();
        model.addAttribute("prets", prets);
        return "prets-attente";
    }

    // Pour "Valider" (idStatut = 2)
    @GetMapping("/valider")
    public String validerProlongement(@RequestParam("id") Integer idPret) {
        return insererProlongementStatut(idPret, 2); // 2 = validé
    }

    // Pour "Refuser" (idStatut = 3)
    @GetMapping("/refuser")
    public String refuserProlongement(@RequestParam("id") Integer idPret) {
        return insererProlongementStatut(idPret, 3); // 3 = refusé
    }

    private String insererProlongementStatut(Integer idPret, Integer idStatut) {
        try {
            List<Prolongement> prolongements = prolongementService.findByPretId(idPret);

            if (!prolongements.isEmpty()) {
                Prolongement prolongement = prolongements.get(0); // on prend le premier
                StatutProlongement statut = statutProlongementService.findById(idStatut);

                // Créer d'abord la clé composite
                ProlongementStatutId psId = new ProlongementStatutId();
                psId.setIdProlongement(prolongement.getId());
                psId.setIdStatutProlongement(statut.getIdStatutProlongement());

                // Créer l'entité ProlongementStatut
                ProlongementStatut ps = new ProlongementStatut();
                ps.setId(psId); // Important : définir l'ID avant les relations
                ps.setProlongement(prolongement);
                ps.setStatutProlongement(statut);

                prolongementStatutService.save(ps);
            }

            return "redirect:/prets-attente"; // Correction de l'URL
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/prets-attente?error=" + e.getMessage();
        }
    }
    
    // private String insererProlongementStatut(Integer idPret, Integer idStatut) {
    //     List<Prolongement> prolongements = prolongementService.findByPretId(idPret);

    //     if (!prolongements.isEmpty()) {
    //         Prolongement prolongement = prolongements.get(0); // on prend le premier

    //         ProlongementStatut ps = new ProlongementStatut();
    //         ps.setProlongement(prolongement);
    //         ps.setStatutProlongement(statutProlongementService.findById(idStatut));

    //         prolongementStatutService.save(ps);
    //     }

    //     return "redirect:/prets-attente"; // ou ton URL de retour
    // }


    // private String insererProlongementStatut(int idProlongement, int idStatut) {
    //     Optional<Prolongement> prolongementOpt = prolongementService.findById(idProlongement);
    //     Optional<StatutProlongement> statutOpt = statutProlongementService.findById(idStatut);

    //     if (prolongementOpt.isPresent() && statutOpt.isPresent()) {
    //         ProlongementStatut ps = new ProlongementStatut();

    //         // Clé composée
    //         ProlongementStatutId psId = new ProlongementStatutId();
    //         psId.setIdProlongement(idProlongement);
    //         psId.setIdStatutProlongement(idStatut);

    //         ps.setId(psId);
    //         ps.setProlongement(prolongementOpt.get());
    //         ps.setStatutProlongement(statutOpt.get());

    //         prolongementStatutService.save(ps);
    //     }

    //     return "redirect:/prolongements-attente"; // ou autre URL
    // }
}
