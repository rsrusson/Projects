package com.MTBTrail.MTBTrail.Service;

import com.MTBTrail.MTBTrail.Entity.Trail;

import java.util.List;

public interface TrailService {
    List<Trail> findAll();

    Trail findById(int theId);

    Trail save(Trail theTrail);

    void deleteById(int theId);
}
