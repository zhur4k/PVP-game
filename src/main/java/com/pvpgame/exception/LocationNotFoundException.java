package com.pvpgame.exception;

import com.pvpgame.model.Direction;

public class LocationNotFoundException extends RuntimeException{

    public LocationNotFoundException(Long locationId, Direction direction){
        super("No neighboring location found from location ID " + locationId + " in direction " + direction);

    }
}
