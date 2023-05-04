package com.MTBTrail.MTBTrail.Controller;

import com.MTBTrail.MTBTrail.DAO.TrailDAO;
import com.MTBTrail.MTBTrail.Entity.Trail;
import com.MTBTrail.MTBTrail.Service.TrailService;
import com.MTBTrail.MTBTrail.Service.TrailServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrailsController {
    private TrailService trailService;

    public TrailsController(TrailService theTrailService) {
        trailService = theTrailService;
    }

    @GetMapping("/trails")
    public List<Trail> findAll(){
        return trailService.findAll();
    }

    @GetMapping("/trails/{trailId}")
    public Trail getTrail(@PathVariable int trailId){
        Trail theTrail = trailService.findById(trailId);

        if(theTrail == null){
            throw new RuntimeException("Trail ID not found" + trailId);
        }

        return theTrail;
    }

    @PostMapping("/trails")
    public Trail addTrail(@RequestBody Trail theTrail){
        theTrail.setId(0);

        Trail dbTrail = trailService.save(theTrail);

        return dbTrail;
    }

    @PutMapping("/trails")
    public Trail updateTrail(@RequestBody Trail theTrail){
        Trail dbTrail = trailService.save(theTrail);

        return dbTrail;
    }

    @DeleteMapping("/trails/{trailId}")
    public String deleteTrail(@PathVariable int trailId){
        Trail tempTrail = trailService.findById(trailId);

        if(tempTrail == null){
            throw new RuntimeException("Could not find trail id " + trailId);
        }
        trailService.deleteById(trailId);

        return "Deleted trail id " + trailId;
    }
}
