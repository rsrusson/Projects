package com.MTBTrail.MTBTrail.DAO;

import com.MTBTrail.MTBTrail.Entity.Trail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrailDAOImpl implements TrailDAO {
    private EntityManager entityManager;

    @Autowired
    public TrailDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Trail> findAll() {
        TypedQuery<Trail> theQuery = entityManager.createQuery("from Trail", Trail.class);

        List<Trail> trails = theQuery.getResultList();

        return trails;
    }

    @Override
    public Trail findById(int theId){
        Trail theTrail = entityManager.find(Trail.class, theId);

        return theTrail;
    }

    @Override
    public Trail save(Trail theTrail){
        Trail dbTrail = entityManager.merge(theTrail);

        return dbTrail;
    }

    @Override
    public void deleteById(int theId){
        Trail theTrail = entityManager.find(Trail.class, theId);

        entityManager.remove(theTrail);
    }
}
