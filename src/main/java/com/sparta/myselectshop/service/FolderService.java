package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderRequestDto;
import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public void addFolders(List<String> folderNames, User user) {

        List<Folder> existsFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames); // 이미 유저가 가지고 있는 것 중에서 폴더 이름 똑같은 거 있는지 없는지 찾는 코드

        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNames){
            if (!isExistFolderName(folderName, existsFolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            } else {
                throw new IllegalArgumentException("폴더명이 중복되었습니다.");
            }
        }
        folderRepository.saveAll(folderList);
    }

    public List<FolderResponseDto> getFolders(User user) {
        List<Folder> folderList = folderRepository.findAllByUser(user);
        List<FolderResponseDto> responseDtoList = new ArrayList<>();

        for (Folder folder : folderList){
            responseDtoList.add(new FolderResponseDto(folder));
        }

        return responseDtoList;
    }

    private boolean isExistFolderName(String folderName, List<Folder> existsFolderList) {
        for (Folder existFolder : existsFolderList) {
            if(folderName.equals(existFolder.getName())){
                return true;
            }


        }
        return false;
    }


}
