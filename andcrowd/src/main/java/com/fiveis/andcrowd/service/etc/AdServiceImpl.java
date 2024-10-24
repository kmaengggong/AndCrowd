package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.AdDTO;
import com.fiveis.andcrowd.entity.etc.Ad;
import com.fiveis.andcrowd.repository.etc.AdJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdServiceImpl implements AdService{
    private AdJPARepository adJPARepository;

    @Autowired
    public AdServiceImpl(AdJPARepository adJPARepository){
        this.adJPARepository = adJPARepository;
    }

    @Override
    public List<AdDTO.Find> findAll() {
        List<Ad> adList = adJPARepository.findAll();
        List<AdDTO.Find> findList = new ArrayList<>();
        for(Ad ad : adList){
            findList.add(AdDTO.convertToAdDTOFind(ad));
        }
        return findList;
    }

    @Override
    public AdDTO.Find findById(int adId) {
        if(adJPARepository.findById(adId).isEmpty()) return null;
        return AdDTO.convertToAdDTOFind(adJPARepository.findById(adId).get());
    }

    @Override
    public void save(Ad ad) {
        adJPARepository.save(ad);
    }

    @Override
    public void update(AdDTO.Update update) {
        Ad ad = Ad.builder()
                .adId(update.getAdId())
                .adName(update.getAdName())
                .adPrice(update.getAdPrice())
                .build();
        adJPARepository.save(ad);
    }

    @Override
    public void deleteById(int adId) {
        adJPARepository.deleteById(adId);
    }


}
