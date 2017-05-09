package com.example.nickp.kvu.Helpers;

import com.example.nickp.kvu.ServerObjects.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by nickp on 5/1/2017.
 */

public class OverlayNameResolver {
    private HashMap<String, HashSet<String>> overlayMap;
    private HashSet<String> spahrClassroom;
    private HashSet<String> southHallway;
    private HashSet<String> seniorDesignLab;
    private HashSet<String> outside;
    private HashSet<String> northHallway;
    private HashSet<String> lobby;
    private HashSet<String> fishbowl;
    private HashSet<String> eatonThirdFloor;
    private HashSet<String> eatonSecondFloor;
    private HashSet<String> eatonFirstFloor;
    private HashSet<String> eatonGroundFloor;
    private HashSet<String> eatonAtrium;
    private HashSet<String> computerLab;
    private HashSet<String> eatonHall;
    private HashSet<String> leep2;
    private HashSet<String> upstairs;
    private HashSet<String> downstairs;

    public OverlayNameResolver(){
        buildHashSets();
        buildOverlayMap();
    }
    public int getIndexOfCorrespondingNextPoint
            (ArrayList<Point.NextPoint> nextPoints, String attempt){
        if(attempt == null||nextPoints == null){
            return -1;
        }
        for(int i = 0; i < nextPoints.size();i++){
            if(isValid(nextPoints.get(i),attempt)){
                return i;
            }
        }
        return -1;
    }
    private boolean isValid(Point.NextPoint pt, String attempt){
        if(overlayMap.containsKey(pt.overlayName)){
            if(overlayMap.get(pt.overlayName).contains(attempt)){
                return true;
            }
        }
        return false;
    }
    private void buildOverlayMap(){
        overlayMap = new HashMap<String, HashSet<String>>();
        overlayMap.put("Spahr Classroom", spahrClassroom);
        overlayMap.put("South Hallway", southHallway);
        overlayMap.put("Senior Design Lab",seniorDesignLab);
        overlayMap.put("Outside",outside);
        overlayMap.put("North Hallway", northHallway);
        overlayMap.put("Lobby", lobby);
        overlayMap.put("Fishbowl",fishbowl);
        overlayMap.put("Eaton Third Floor",eatonThirdFloor);
        overlayMap.put("Eaton Second Floor",eatonSecondFloor);
        overlayMap.put("Eaton First Floor",eatonFirstFloor);
        overlayMap.put("Eaton Ground Floor",eatonGroundFloor);
        overlayMap.put("Eaton Atrium",eatonAtrium);
        overlayMap.put("Computer Lab",computerLab);
        overlayMap.put("Eaton Hall", eatonHall);
        overlayMap.put("LEEP2",leep2);
        overlayMap.put("Upstairs",upstairs);
        overlayMap.put("Downstairs",downstairs);

    }
    private void buildHashSets(){
        spahrClassroom = new HashSet<String>();
        spahrClassroom.add("spahr classroom"); //correct string
        spahrClassroom.add("spar classroon");
        spahrClassroom.add("spar class room");
        spahrClassroom.add("spahr class room");
        spahrClassroom.add("smart classroom");
        spahrClassroom.add("smartclass room");
        spahrClassroom.add("spart classroom");
        spahrClassroom.add("sparte classroom");
        spahrClassroom.add("smartglass room");
        spahrClassroom.add("spark classroom");
        spahrClassroom.add("sparkz classroom");
        spahrClassroom.add("sparq classroom");
        spahrClassroom.add("sparke classroom");
        spahrClassroom.add("sparc classroom");
        spahrClassroom.add("far classroom");
        spahrClassroom.add("bar classroom");
        spahrClassroom.add("how far classroom?");
        spahrClassroom.add("farh classroom");
        spahrClassroom.add("smart class room");
        spahrClassroom.add("spart class room");
        spahrClassroom.add("sparte class room");
        spahrClassroom.add("smartglass room");
        spahrClassroom.add("spark class room");
        spahrClassroom.add("sparkz class room");
        spahrClassroom.add("sparq class room");
        spahrClassroom.add("sparke class room");
        spahrClassroom.add("sparc class room");
        spahrClassroom.add("far class room");
        spahrClassroom.add("bar class room");
        spahrClassroom.add("how far class room?");
        spahrClassroom.add("farh class room");

        southHallway = new HashSet<String>();
        southHallway.add("south tollway");
        southHallway.add("salt all way");
        southHallway.add("south pole way");
        southHallway.add("salto way");
        southHallway.add("salt always");
        southHallway.add("south hallway"); //correct string
        southHallway.add("south whole way");
        southHallway.add("southall way");
        southHallway.add("south Hall way");
        southHallway.add("southold way");
        southHallway.add("sounds alright");
        southHallway.add("southwell way");
        southHallway.add("southill way");
        southHallway.add("south away");

        seniorDesignLab = new HashSet<String>();
        seniorDesignLab.add("senior design lab"); //correct string
        seniorDesignLab.add("sing a design lab");
        seniorDesignLab.add("senior design web");
        seniorDesignLab.add("singer design lab");
        seniorDesignLab.add("sing a design web");
        seniorDesignLab.add("singer design web");
        seniorDesignLab.add("senior design wap");
        seniorDesignLab.add("senior design app");
        seniorDesignLab.add("senior design map");
        seniorDesignLab.add("cnn design lab");
        seniorDesignLab.add("sing in design lab");
        seniorDesignLab.add("sing me a design love");
        seniorDesignLab.add("senior design love");
        seniorDesignLab.add("sing me a design lab");
        seniorDesignLab.add("cnet as I love");
        seniorDesignLab.add("sing as I love");
        seniorDesignLab.add("senior design");
        seniorDesignLab.add("design lab");
        seniorDesignLab.add("design web");
        seniorDesignLab.add("design love");

        outside = new HashSet<String>();
        outside.add("outside");
        outside.add("i'm outside");
        outside.add("out side");

        northHallway = new HashSet<String>();
        northHallway.add("north hallway"); //correct string
        northHallway.add("north holloway");
        northHallway.add("north hall way");
        northHallway.add("north haul away");
        northHallway.add("north holliway");
        northHallway.add("north tollway");
        northHallway.add("north hollywood");
        northHallway.add("north whole way");
        northHallway.add("north pole way");
        northHallway.add("north pole");
        northHallway.add("north hollywood");
        northHallway.add("north highway");

        lobby = new HashSet<String>();
        lobby.add("lobby"); //correct string
        lobby.add("lobi");
        lobby.add("l'abbe");
        lobby.add("lobbe");
        lobby.add("labbi");
        lobby.add("will be?");
        lobby.add("y'all be");
        lobby.add("clube");
        lobby.add("klubi");
        lobby.add("robbie");
        lobby.add("love you");
        lobby.add("bobby");
        lobby.add("the lobby");
        lobby.add("bobbi");

        fishbowl = new HashSet<String>();
        fishbowl.add("fish bowl");
        fishbowl.add("fishbowl"); //correct string
        fishbowl.add("fishball");
        fishbowl.add("fish ball");
        fishbowl.add("facebook");
        fishbowl.add("ishbel");
        fishbowl.add("ishbal");
        fishbowl.add("fishful");
        fishbowl.add("fish full");

        eatonThirdFloor = new HashSet<String>();
        eatonThirdFloor.add("pizza in third floor");
        eatonThirdFloor.add("pizza and third floor");
        eatonThirdFloor.add("pettson third floor");
        eatonThirdFloor.add("pizen third floor");
        eatonThirdFloor.add("eat on third 4");
        eatonThirdFloor.add("eat on third floor");
        eatonThirdFloor.add("pizza on third floor");
        eatonThirdFloor.add("eaton third floor"); //correct string
        eatonThirdFloor.add("eating third floor");
        eatonThirdFloor.add("eaton 3rd floor");
        eatonThirdFloor.add("it in third floor");
        eatonThirdFloor.add("eat and third floor");
        eatonThirdFloor.add("eaton third 4");
        eatonThirdFloor.add("eaton third floor");
        eatonThirdFloor.add("eat in third floor");
        eatonThirdFloor.add("1834");
        eatonThirdFloor.add("18 third floor");
        eatonThirdFloor.add("83rd floor");
        eatonThirdFloor.add("getting third floor");
        eatonThirdFloor.add("heating third floor");
        eatonThirdFloor.add("eighteen thirty four");
        eatonThirdFloor.add("eighty third floor");

        eatonSecondFloor = new HashSet<String>();
        eatonSecondFloor.add("it's and second floor");
        eatonSecondFloor.add("peyton second floor");
        eatonSecondFloor.add("cancel second floor");
        eatonSecondFloor.add("dayton second floor");
        eatonSecondFloor.add("eating second floor");
        eatonSecondFloor.add("eat on second 4");
        eatonSecondFloor.add("eat on second floor");
        eatonSecondFloor.add("eaton second 4");
        eatonSecondFloor.add("eaton second floor");
        eatonSecondFloor.add("eat and second floor");
        eatonSecondFloor.add("82nd floor");
        eatonSecondFloor.add("8 and second floor");
        eatonSecondFloor.add("eaton second floor"); //correct string
        eatonSecondFloor.add("eaton 2nd floor");
        eatonSecondFloor.add("etson second floor");
        eatonSecondFloor.add("eteson second floor");
        eatonSecondFloor.add("eats and second floor");
        eatonSecondFloor.add("eat and 2nd floor");
        eatonSecondFloor.add("etson 2nd floor");
        eatonSecondFloor.add("eat my second floor");
        eatonSecondFloor.add("even second floor");
        eatonSecondFloor.add("evening second floor");
        eatonSecondFloor.add("it in second floor");
        eatonSecondFloor.add("it and second floor");
        eatonSecondFloor.add("is it in second floor?");

        eatonFirstFloor = new HashSet<String>();
        eatonFirstFloor.add("eaton first floor"); //correct string
        eatonFirstFloor.add("eating first floor");
        eatonFirstFloor.add("etson first floor");
        eatonFirstFloor.add("eteson first floor");
        eatonFirstFloor.add("eat in first floor");
        eatonFirstFloor.add("eatin first floor");
        eatonFirstFloor.add("eat and first floor");
        eatonFirstFloor.add("eat n first floor");
        eatonFirstFloor.add("etin first floor");
        eatonFirstFloor.add("heating first floor");
        eatonFirstFloor.add("meeting first floor");
        eatonFirstFloor.add("ethan first floor");
        eatonFirstFloor.add("eat on first 4");
        eatonFirstFloor.add("eat on first floor");
        eatonFirstFloor.add("eaton first 4");

        eatonFirstFloor.add("heyting first floor");
        eatonFirstFloor.add("eaton 1st floor");
        eatonFirstFloor.add("eating 1st floor");
        eatonFirstFloor.add("etson 1st floor");
        eatonFirstFloor.add("eteson 1st floor");
        eatonFirstFloor.add("eat in 1st floor");
        eatonFirstFloor.add("eatin 1st floor");
        eatonFirstFloor.add("eat and 1st floor");
        eatonFirstFloor.add("eat N 1st floor");
        eatonFirstFloor.add("etin 1st floor");
        eatonFirstFloor.add("heating 1st floor");
        eatonFirstFloor.add("meeting 1st floor");
        eatonFirstFloor.add("ethan 1st floor");
        eatonFirstFloor.add("heyting 1st floor");
        eatonFirstFloor.add("eat'n first floor");
        eatonFirstFloor.add("eat'n 1st floor");
        eatonFirstFloor.add("yton first floor");
        eatonFirstFloor.add("yton 1st floor");
        eatonFirstFloor.add("he didn't first floor");
        eatonFirstFloor.add("he didn't first law");
        eatonFirstFloor.add("it didn't first floor");
        eatonFirstFloor.add("he didn't 1st floor");
        eatonFirstFloor.add("he didn't 1st law");
        eatonFirstFloor.add("it didn't 1st floor");
        eatonFirstFloor.add("hating first floor");
        eatonFirstFloor.add("hating 1st floor");
        eatonFirstFloor.add("evening first 4");
        eatonFirstFloor.add("evening first four");
        eatonFirstFloor.add("eaton first four");
        eatonFirstFloor.add("eaton first 4");
        eatonFirstFloor.add("eating first 4");
        eatonFirstFloor.add("eating first four");
        eatonFirstFloor.add("eaten first floor");
        eatonFirstFloor.add("eaten first 4");
        eatonFirstFloor.add("eaten first four");
        eatonFirstFloor.add("it's on first floor");

        eatonGroundFloor = new HashSet<String>();
        eatonGroundFloor.add("eating ground floor");
        eatonGroundFloor.add("eat in ground floor");
        eatonGroundFloor.add("it in ground floor");
        eatonGroundFloor.add("eat and ground floor");
        eatonGroundFloor.add("heyting ground floor");
        eatonGroundFloor.add("eaton ground floor"); //correct string
        eatonGroundFloor.add("eat'n ground floor");
        eatonGroundFloor.add("eaton ground fault");
        eatonGroundFloor.add("eating ground floor");
        eatonGroundFloor.add("eden ground floor");
        eatonGroundFloor.add("hidden ground floor");
        eatonGroundFloor.add("in ground floor");
        eatonGroundFloor.add("aiden ground floor");
        eatonGroundFloor.add("heating ground floor");
        eatonGroundFloor.add("even ground floor");
        eatonGroundFloor.add("you can ground floor");
        eatonGroundFloor.add("he can ground floor");
        eatonGroundFloor.add("eating around 4");
        eatonGroundFloor.add("evening ground floor");
        eatonGroundFloor.add("eating ground fault");
        eatonGroundFloor.add("heyting ground floor");
        eatonGroundFloor.add("eden ground floor");
        eatonGroundFloor.add("didn't ground floor");
        eatonGroundFloor.add("you didn't ground floor");
        eatonGroundFloor.add("you don't ground floor");
        eatonGroundFloor.add("he didn't ground floor");
        eatonGroundFloor.add("it didn't ground floor");
        eatonGroundFloor.add("it's on ground floor");

        computerLab = new HashSet<String>();
        computerLab.add("computer lab"); //correct string
        computerLab.add("computerland");
        computerLab.add("computer land");
        computerLab.add("computer let");
        computerLab.add("computer");
        computerLab.add("computer labs");
        computerLab.add("the computer lab");
        computerLab.add("computer laptop");
        computerLab.add("computer that");
        computerLab.add("computer lap");
        computerLab.add("computer laps");

        eatonAtrium = new HashSet<String>();
        eatonAtrium.add("eaton atrium"); //correct string
        eatonAtrium.add("eat'n atrium");
        eatonAtrium.add("eaton aetrium");
        eatonAtrium.add("yton atrium");
        eatonAtrium.add("etin atrium");
        eatonAtrium.add("it's an atrium");
        eatonAtrium.add("eat an atrium");
        eatonAtrium.add("eaton a trim");
        eatonAtrium.add("eat and a trim");
        eatonAtrium.add("eating a trim");
        eatonAtrium.add("eaten a trim");
        eatonAtrium.add("eat in a trim");
        eatonAtrium.add("hidden adrian");
        eatonAtrium.add("eden adrian");
        eatonAtrium.add("hidden a dream");
        eatonAtrium.add("idonate rim");
        eatonAtrium.add("edenite rim");
        eatonAtrium.add("idonate him");
        eatonAtrium.add("eating atm");
        eatonAtrium.add("eden atm");
        eatonAtrium.add("idonate trim");
        eatonAtrium.add("idonate am");
        eatonAtrium.add("eating a dream");
        eatonAtrium.add("it in a dream");

        downstairs = new HashSet<String>();
        downstairs.add("downstairs"); //correct string
        downstairs.add("down stairs");
        downstairs.add("i'm downstairs");
        downstairs.add("down the stairs");
        upstairs = new HashSet<String>();
        upstairs.add("upstairs"); //correct string
        upstairs.add("up stairs");
        upstairs.add("hope stairs");
        upstairs.add("ope stairs");
        upstairs.add("i'm upstairs");
        upstairs.add("up the stairs");

        leep2 = new HashSet<String>();
        leep2.add("will you too?");
        leep2.add("do you too?");
        leep2.add("will you be too?");
        leep2.add("are you too?");
        leep2.add("sleep too");
        leep2.add("leap 2");
        leep2.add("flip 2");
        leep2.add("leap too");
        leep2.add("leave too");
        leep2.add("leep 2"); //correct string
        leep2.add("liep 2");
        leep2.add("lepb 2");
        leep2.add("lyp 2");
        leep2.add("lead to");
        leep2.add("lead 2");
        leep2.add("leave 2");
        leep2.add("leave to");
        leep2.add("live too");
        leep2.add("libtool");
        eatonHall = new HashSet<String>();
        eatonHall.add("eaton hall"); //correct string
        eatonHall.add("etson hall");
        eatonHall.add("eteson hall");
        eatonHall.add("eaton haul");
        eatonHall.add("etson haul");
        eatonHall.add("eaton hall");
        eatonHall.add("eden hall");
        eatonHall.add("eating hall");
        eatonHall.add("eating w****");
        eatonHall.add("eating hard");
        eatonHall.add("heating hall");
        eatonHall.add("heating haul");
        eatonHall.add("eating haul");
        eatonHall.add("heating haul");
        eatonHall.add("meeting hall");
        eatonHall.add("eat'n hall");
        eatonHall.add("etanol");
        eatonHall.add("houston hall");
        eatonHall.add("chidon hall");
        eatonHall.add("eaten all");
        eatonHall.add("eaten hall");
        eatonHall.add("eaten haul");
        eatonHall.add("eating all");
    }

}
