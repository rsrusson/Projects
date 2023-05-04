package com.MTBTrail.MTBTrail.Service;

import com.MTBTrail.MTBTrail.DAO.TrailDAO;
import com.MTBTrail.MTBTrail.Entity.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrailServiceImpl implements TrailService{

    private TrailDAO trailDAO;

    @Autowired
    public TrailServiceImpl(TrailDAO theTrailDAO) {
        trailDAO = theTrailDAO;
    }

    @Override
    public List<Trail> findAll() {
        return trailDAO.findAll();
    }

    @Override
    public Trail findById(int theId) {
        Trail theTrail = trailDAO.findById(theId);

        /*Optional<Trail> result = trailDAO.findById(theId);

        Trail theTrail = null;
        if (result.isPresent())
            theTrail = result.get();
        else
            throw new RuntimeException("Did not find trail " + theId);*/

        return theTrail;
    }

    @Transactional
    @Override
    public Trail save(Trail theTrail) {
        trailDAO.save(theTrail);

        return theTrail;
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        trailDAO.deleteById(theId);
    }
}
