package com.theironyard.services;

import com.theironyard.entities.AnonFile;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zach on 3/16/16.
 */
public interface AnonFileRepository extends CrudRepository<AnonFile, Integer> {
}
