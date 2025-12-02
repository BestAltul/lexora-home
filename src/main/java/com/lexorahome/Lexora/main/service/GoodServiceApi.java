package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.dto.GoodRecord;
import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.exception.GoodNotFoundById;
import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.picture_service.repository.PictureRepository;
import com.lexorahome.Lexora.main.picture_service.service.PictureService;
import com.lexorahome.Lexora.main.repository.GoodRepository;
import com.lexorahome.Lexora.main.utils.GoodMapper;
import com.lexorahome.Lexora.main.utils.GoodMapperCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GoodServiceApi {
    private final GoodRepository goodRepository;
    private final GoodMapper goodMapper;
    private final PictureService pictureService;
    private final PictureRepository pictureRepository;

    public List<Good> getAllGoods(){
        return goodRepository.findAll();
    }

    @Transactional
    public List<GoodRecord> getAllGoodRecords(){
        List<Good> goodList = getAllGoods();
        return GoodMapperCustom.toListGoodRecord(goodList);
    }


    public GoodRecord findGoodRecordById(String id){
        UUID foundId = UUID.fromString(id);
        Good good = goodRepository.findById(foundId).orElseThrow(()->new GoodNotFoundById("Couldn't find good by ID "+id));
        return goodMapper.toRecord(good);
    }

    public boolean updateGood(GoodRecord goodRecord,String id) {

        UUID foundId = UUID.fromString(id);
        Picture updatePicture;
        Optional<Picture> pictureOptional = pictureRepository.findById(foundId);
        if (pictureOptional.isPresent()) {
            updatePicture = pictureOptional.get();
        } else {
            updatePicture = new Picture();
        }


        updatePicture.setLink(goodRecord.picture().get(0).link());
        updatePicture.setName(goodRecord.picture().get(0).name());
        updatePicture.setNotes(goodRecord.picture().get(0).notes());

        List<Picture> newList = new ArrayList<>();
        newList.add(updatePicture);

        Good good = goodRepository.findById(foundId).orElseThrow(()->new GoodNotFoundById("Good not found by ID "+id));
        good.setPicture(newList);

        goodRepository.save(good);
        return true;
    }
}
