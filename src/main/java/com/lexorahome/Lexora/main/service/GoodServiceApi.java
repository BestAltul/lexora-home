package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GoodServiceApi {
    private final GoodRepository goodRepository;


    public List<Good> getAllGoods(){



        return ;
    }
}
