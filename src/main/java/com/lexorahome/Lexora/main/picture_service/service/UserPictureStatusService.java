package com.lexorahome.Lexora.main.picture_service.service;

import com.lexorahome.Lexora.main.picture_service.dto.in.PictureRequest;
import com.lexorahome.Lexora.main.picture_service.repository.UserPictureStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserPictureStatusService {
    private final UserPictureStatusRepository userPictureStatusRepository;

    public void createNewNotes(PictureRequest pictureRequest){

//        Picture pic = new Picture()
//                pic.s

    }
}
