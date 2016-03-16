package com.theironyard.services;

import com.theironyard.entities.AnonFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zach on 3/16/16.
 */
public interface AnonFileRepository extends CrudRepository<AnonFile, Integer> {
    int countByIsPermFalse();//saw checking the boolean in the statement in brandon's code
    AnonFile findFirstByIsPermFalseOrderByDateTimeAsc();
}
