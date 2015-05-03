package com.example.q.camara.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.List;

import javax.inject.Named;

import static com.example.q.camara.backend.OfyService.ofy;

/**
 * Created by q on 29/04/15.
 */
@Api(name = "play", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.camara.q.example.com", ownerName = "backend.camara.q.example.com", packagePath = ""))
public class PlayEndpoint {


    @ApiMethod(name = "play")
    public PlayRecord registerPlayer(@Named("playIp") String playIp, @Named("numberCells") Integer numberCells, @Named("regId") String  regId) {

        PlayRecord playRecord = searchOpponent(playIp, numberCells, regId);

        if(playRecord != null){
            return playRecord;
        }

        else if (findRecord(playIp) != null){
            Integer nc = ofy().load().type(PlayRecord.class).id(findRecord(playIp).id).now().getNumberCells();
            if (nc != numberCells)
                ofy().load().type(PlayRecord.class).id(findRecord(playIp).id).now().setNumberCells(numberCells);
        }

        else{
            //si no hi ha jugador es guarda al servidor per que altres el busqui
            PlayRecord record = new PlayRecord();
            record.setPlayIp(playIp);
            record.setRegId(regId);
            record.setNumberCells(numberCells);
            ofy().save().entity(record).now();
        }

        return null;
    }

    @ApiMethod(name = "searchOpponent")
    private PlayRecord searchOpponent(@Named("playIp") String playIp, @Named("numberCells") Integer numberCells, @Named("regId") String  regId){

        List<PlayRecord> playRecordList =  ofy().load().type(PlayRecord.class).filter("numberCells", numberCells).list();
        for( PlayRecord playRecord : playRecordList){
            if(!playRecord.getPlayIp().equals(playIp) && !playRecord.getRegId().equals(regId))
                return playRecord;
        }

        return null;
    }


    private PlayRecord findRecord(String playIp) {
        return ofy().load().type(PlayRecord.class).filter("playIp", playIp).first().now();
    }

}
