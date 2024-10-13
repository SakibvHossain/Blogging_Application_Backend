package com.example.blogging_application_api.services.implementation;

import com.example.blogging_application_api.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //Filename
        String fileName = file.getOriginalFilename();
        //Full file path
        String filePath = path + File.separator + fileName;
        //Create folder if not created
        File file1 = new File(path);
        if (!file1.exists()){
            file1.mkdirs();
        }

        if (fileName!=null){
            Files.copy(file.getInputStream(), Paths.get(filePath));
        }

        return fileName;
    }

    @Override
    public InputStream downloadImage(String path, String filename) throws FileNotFoundException {
        String filePath = path + File.separator + filename;
        try{
            return new FileInputStream(filePath);
        }catch (FileNotFoundException e){
            throw new FileNotFoundException();
        }
    }
}
