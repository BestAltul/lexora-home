package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.dto.GoodRecord;
import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.repository.GoodRepository;
import com.lexorahome.Lexora.main.utils.GoodMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GoodServiceApi {
    private final GoodRepository goodRepository;
    private final GoodMapper goodMapper;

    public List<Good> getAllGoods(){
        return goodRepository.findAll();
    }

    @Transactional
    public List<GoodRecord> getAllGoodRecords(){
        List<Good> goodList = getAllGoods();
        return goodMapper.toRecordList(goodList);
    }
}
