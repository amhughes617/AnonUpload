package com.theironyard.controllers;

import com.theironyard.entities.AnonFile;
import com.theironyard.services.AnonFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by zach on 3/16/16.
 */
@RestController
public class AnonFileController {
    @Autowired
    AnonFileRepository files;

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public void upload(MultipartFile file, HttpServletResponse response, Boolean isPerm, String comment) throws IOException {
        AnonFile anonFile;
        if (isPerm != null) {
            anonFile = writeFile("permanent_uploads", file, comment);
            anonFile.setPerm(true);
        }
        else {
            anonFile = writeFile("uploads", file, comment);
            while (files.countByIsPermFalse() > 9) {//in case james somehow adds a bunch of things to my database this will
                AnonFile anonFileToDelete = files.findFirstByIsPermFalseOrderByDateTimeAsc();//delete all until only the 10 newest remain
                files.delete(anonFileToDelete);
                File fileToDelete = new File("public/uploads/" + anonFileToDelete.getFilename());
                fileToDelete.delete();
            }
        }
        files.save(anonFile);
        response.sendRedirect("/");
    }

    @RequestMapping(path = "/files", method = RequestMethod.GET)
    public List<AnonFile> getFiles() {
        return (List<AnonFile>) files.findAll();
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public void delete(int id, HttpServletResponse response) throws IOException {
        AnonFile anonFileToDelete = files.findOne(id);
        files.delete(id);
        File fileToDelete = new File("public/uploads/" + anonFileToDelete.getFilename());
        fileToDelete.delete();
        response.sendRedirect("/");
    }


    public AnonFile writeFile(String path, MultipartFile file, String comment) throws IOException {
        File dir = new File("public/" + path);
        dir.mkdirs();
        File f = File.createTempFile("file", file.getOriginalFilename(), dir);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());
        if (comment.isEmpty()) {
            comment = file.getOriginalFilename();
        }
        return new AnonFile(f.getName(), file.getOriginalFilename(), comment);
    }
}
