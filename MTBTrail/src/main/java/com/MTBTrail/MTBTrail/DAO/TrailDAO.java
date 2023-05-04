package com.MTBTrail.MTBTrail.DAO;

import com.MTBTrail.MTBTrail.Entity.Trail;

import java.util.List;

//No need for following annotation due to JpaRepository already having built in @Repository, and extending that class includes it
//@Repository

public interface TrailDAO {
    List<Trail> findAll();

    Trail findById(int theId);

    Trail save(Trail theTrail);

    void deleteById(int theId);
}
