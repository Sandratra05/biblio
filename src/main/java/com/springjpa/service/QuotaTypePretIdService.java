package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.QuotaTypePretId;
import com.springjpa.repository.QuotaTypePretIdRepository;

@Service
public class QuotaTypePretIdService {
    @Autowired
    private QuotaTypePretIdRepository quotaTypePretIdRepository;

    public QuotaTypePretId findById(Integer id){
        return quotaTypePretIdRepository.findById(id).get();
    }

    public List<QuotaTypePretId> findAll(){
        return quotaTypePretIdRepository.findAll();
    }

    public void save(QuotaTypePretId quotaTypePretId){
        quotaTypePretIdRepository.save(quotaTypePretId);
    }
}
