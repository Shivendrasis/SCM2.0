package com.scm20.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile contactImage , String filename);

    //method that gives url
    String getUrlFromPublicId(String publicId);


    String uploadProfileImage(MultipartFile file) throws IOException;
    
}
