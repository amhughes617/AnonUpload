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

        if (isPerm != null) {
            File dir = new File("public/permanent_uploads");
            dir.mkdirs();
            File f = File.createTempFile("file", file.getOriginalFilename(), dir);
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(file.getBytes());
            AnonFile anonFile = new AnonFile(f.getName(), file.getOriginalFilename(), comment);
            if (comment.isEmpty()) {
                anonFile.setComment(file.getOriginalFilename());
            }
            anonFile.setPerm(true);
            files.save(anonFile);
        }
        else {
            File dir = new File("public/uploads");
            dir.mkdirs();
            File f = File.createTempFile("file", file.getOriginalFilename(), dir);
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(file.getBytes());
            AnonFile anonFile = new AnonFile(f.getName(), file.getOriginalFilename(), comment);
            if (comment.isEmpty()) {
                anonFile.setComment(file.getOriginalFilename());
            }
            files.save(anonFile);
            //could just do an if statement below
            while (files.countByIsPerm(false) > 3) {//in case james somehow adds a bunch of things to my database this will
                AnonFile anonFileToDelete = files.findFirstByIsPermOrderByDateTimeAsc(false);//delete all until only the 10 newest remain
                files.delete(anonFileToDelete);
                File fileToDelete = new File("public/uploads/" + anonFileToDelete.getFilename());
                fileToDelete.delete();
            }
        }
        response.sendRedirect("/");
    }

    @RequestMapping(path = "/files", method = RequestMethod.GET)
    public List<AnonFile> getFiles() {
        return (List<AnonFile>) files.findAll();
    }

}
