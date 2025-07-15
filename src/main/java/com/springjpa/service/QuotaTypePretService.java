package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.QuotaTypePret;
import com.springjpa.repository.PretRepository;
import com.springjpa.repository.QuotaTypePretRepository;

@Service
public class QuotaTypePretService {
    @Autowired
    private QuotaTypePretRepository quotaTypePretRepository;

    @Autowired
    private PretRepository pretRepository;


    // public QuotaTypePret findById(Integer id){
    //     return quotaTypePretRepository.findById(id).;
    // }

    public List<QuotaTypePret> findAll(){
        return quotaTypePretRepository.findAll();
    }

    public void save(QuotaTypePret quotaTypePret){
        quotaTypePretRepository.save(quotaTypePret);
    }

    public boolean adherantDepasseQuota(Integer idAdherant, Integer idProfil, Integer idTypePret) {
        Integer quota = quotaTypePretRepository.findQuota(idProfil, idTypePret);

        if (quota == null) {
            return true;
        }

        int nbPrets = pretRepository.countPretsEnCours(idAdherant, idTypePret);
        return nbPrets >= quota;
    }

    public Integer quotaRestant(Integer idAdherant, Integer idProfil, Integer idTypePret) {
        Integer quota = quotaTypePretRepository.findQuota(idProfil, idTypePret);

        if (quota == null) {
            return 0;
        }

        int nbPrets = pretRepository.countPretsEnCours(idAdherant, idTypePret);
        return quota - nbPrets;

    }
}
