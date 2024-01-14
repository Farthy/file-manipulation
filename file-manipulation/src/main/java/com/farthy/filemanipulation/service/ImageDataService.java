package com.farthy.filemanipulation.service;

import com.farthy.filemanipulation.entity.ImageData;
import com.farthy.filemanipulation.repository.ImageRepository;
import com.farthy.filemanipulation.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageDataService {
    private final ImageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }
    public  byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData =repository.findByName(fileName);

      byte[] images =  ImageUtils.decompressImage(dbImageData.get().getImageData());

      return images;


    }
}
