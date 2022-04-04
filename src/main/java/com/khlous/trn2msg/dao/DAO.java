package com.khlous.trn2msg.dao;

import org.springframework.stereotype.Service;

@Service
public interface DAO {

    void save(Object object) throws IllegalArgumentException;
}
