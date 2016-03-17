import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import becker.robots.*;

/**
 *  This class is the main class of the application. 
 *  This class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  James Taylor, Michael Kölling, David J. Barnes, Robert Topp
 * @version 18.03.2015
 */


public class Game{
  
 private boolean patrolAICanMove;
    private Parser parser;
    private City theWorld; //the map
    private HashMap<String, Room> areas; //stores the rooms
    private Character player; //the player
    private HashMap<String, Character> npc; //stores non player characters
    
    //game states
    private boolean checkedPager;
    private boolean talkedCoroner;
    private boolean checkedEye;
    private boolean checkedEvidence;
    private boolean confrontedChief;
    
        /**
     * Initialises the game
     * Loads the parser and calls functions to initialise the map, items and Characters
     */
    public Game() {
        parser = new Parser();
        areas = new HashMap<String, Room>();
        npc = new HashMap<String, Character>();
     theWorld = new City();
     patrolAICanMove = false;
     createRooms();
  createRoomExits();
     drawRooms();
     drawTrack();
     drawSewer();
        createCharacters();
        addItems();
        checkedPager = false;
        talkedCoroner = false;
        checkedEye = false;
        checkedEvidence = false;
        confrontedChief = false;
    }
  
    /**
     * Creates the rooms inside the HashMap
     */
    private void createRooms() {
     areas.put("apartment", new Room(0, 3, "your apartment", "it's a bit dull"));
  areas.put("streetW", new Room(3, 3, "a street", "it's pretty grubby, your apartment is to the north"));;
  areas.put("clinic", new Room(6, 6, "the clinic", "it's deserted"));
  areas.put("stationNorth", new StationRoom(3, 0, "the north metro station", "most of the lights are broken", Direction.SOUTH));
  areas.put("streetE", new Room(3, 6, "a street", "gang territory is nearby"));
  
  areas.put("stationSouth", new StationRoom(15, 3, "the south metro station", "this is in better condition than the north station", Direction.WEST));
  areas.put("policeHQ", new Room(12, 6, "the Police Station", "it's quiet at this time of night"));
  areas.put("roadN", new Room(15, 6, "the road outside the police station", "it's the cleanest street in the city"));
  areas.put("roadS", new Room(18, 6, "a road", "the crime scene is to the east"));
  areas.put("crimeScene", new Room(18, 9, "the crime scene", "the body is covered up and surrounded by a cordon"));
  
  areas.put("gangLandN", new StationRoom(0, 9, "gang territory", "there's a sewer grate here that might lead to the warehouse", Direction.EAST));
  areas.put("gangLandW", new Room(3, 9, "gang territory", "you are unwelcome here"));
  areas.put("gangLandS", new Room(6, 9, "gang territory", "you are unwelcome here"));
  areas.put("gangLandE", new Room(6, 12, "gang territory", "you are unwelcome here"));
  
  areas.put("yardN", new Room(3, 15, "a yard surrounding the warehouse", "a guard patrols the area! Don't get seen"));
  areas.put("yardNE", new Room(3, 18, "a yard surrounding the warehouse", "a guard patrols the area! Don't get seen"));
  areas.put("yardW", new Room(6, 15, "a yard surrounding the warehouse", "a guard patrols the area! Don't get seen"));
  areas.put("yardS", new Room(9, 15, "a yard surrounding the warehouse", "a guard patrols the area! Don't get seen"));
  areas.put("yardSE", new Room(9, 18, "a yard surrounding the warehouse", "a guard patrols the area! Don't get seen"));  
  areas.put("warehouse", new StationRoom(6, 18, "the warehouse", "there is evidence here that implicates the chief", Direction.EAST));
  
    }
    
    /**
     * Defines the exits of each room
     */
    private void createRoomExits() {
     areas.get("apartment").addExit(Direction.SOUTH, areas.get("streetW"));
     
     areas.get("streetW").addExit(Direction.NORTH, areas.get("apartment"));
     areas.get("streetW").addExit(Direction.WEST, areas.get("stationNorth"));
     areas.get("streetW").addExit(Direction.EAST, areas.get("streetE"));
     
     areas.get("stationNorth").addExit(Direction.EAST, areas.get("streetW"));
     
     areas.get("streetE").addExit(Direction.WEST, areas.get("streetW"));
     areas.get("streetE").addExit(Direction.SOUTH, areas.get("clinic"));
     areas.get("streetE").addExit(Direction.EAST, areas.get("gangLandW"));
     
     areas.get("clinic").addExit(Direction.NORTH, areas.get("streetE"));
     
     areas.get("gangLandW").addExit(Direction.WEST, areas.get("streetE"));
     areas.get("gangLandW").addExit(Direction.NORTH, areas.get("gangLandN"));
     areas.get("gangLandW").addExit(Direction.SOUTH, areas.get("gangLandS"));
     
     areas.get("gangLandN").addExit(Direction.SOUTH, areas.get("gangLandW"));
     
     areas.get("gangLandS").addExit(Direction.NORTH, areas.get("gangLandW"));
     areas.get("gangLandS").addExit(Direction.EAST, areas.get("gangLandE"));
     
     areas.get("gangLandE").addExit(Direction.WEST, areas.get("gangLandS"));
     areas.get("gangLandE").addExit(Direction.EAST, areas.get("yardW"));
     
     areas.get("yardW").addExit(Direction.WEST, areas.get("gangLandE"));
     areas.get("yardW").addExit(Direction.NORTH, areas.get("yardN"));
     areas.get("yardW").addExit(Direction.SOUTH, areas.get("yardS"));
     
     areas.get("yardN").addExit(Direction.SOUTH, areas.get("yardW"));
     areas.get("yardN").addExit(Direction.EAST, areas.get("yardNE"));
     
     areas.get("yardNE").addExit(Direction.WEST, areas.get("yardN"));
     areas.get("yardNE").addExit(Direction.SOUTH, areas.get("warehouse"));
     
     areas.get("yardS").addExit(Direction.NORTH, areas.get("yardW"));
     areas.get("yardS").addExit(Direction.EAST, areas.get("yardSE"));
     
     areas.get("yardSE").addExit(Direction.WEST, areas.get("yardS"));
     areas.get("yardSE").addExit(Direction.NORTH, areas.get("warehouse"));
     
     areas.get("warehouse").addExit(Direction.NORTH, areas.get("yardNE"));
     areas.get("warehouse").addExit(Direction.SOUTH, areas.get("yardSE"));

     areas.get("stationSouth").addExit(Direction.EAST, areas.get("roadN"));
     
     areas.get("roadN").addExit(Direction.WEST, areas.get("stationSouth"));
     areas.get("roadN").addExit(Direction.NORTH, areas.get("policeHQ"));
     areas.get("roadN").addExit(Direction.SOUTH, areas.get("roadS"));
     
     areas.get("policeHQ").addExit(Direction.SOUTH, areas.get("roadN"));  
     
     areas.get("roadS").addExit(Direction.NORTH, areas.get("roadN"));
     areas.get("roadS").addExit(Direction.EAST, areas.get("crimeScene"));
     
     areas.get("crimeScene").addExit(Direction.WEST, areas.get("roadS"));  
     
    }
    
    /**
     * Draws the rooms on the map
     */
    private void drawRooms() {
     for (Room room : areas.values()) {
      room.draw(theWorld);
     }
    }
    
    /**
     * Draws the train track
     */
    private void drawTrack() {
     HashSet<Wall> trainWalls = new HashSet<Wall>();
     for (int i=6 ; i<=15; i++ ) {
      trainWalls.add(new Wall(theWorld, i, 1, Direction.EAST));
      trainWalls.add(new Wall(theWorld, i, 1, Direction.WEST));
     }
     trainWalls.add(new Wall(theWorld, 16, 1, Direction.WEST));
     trainWalls.add(new Wall(theWorld, 16, 1, Direction.SOUTH));
     trainWalls.add(new Wall(theWorld, 16, 2, Direction.NORTH));
     trainWalls.add(new Wall(theWorld, 16, 2, Direction.SOUTH));
    }
    
    /**
     * Draws the sewer
     */
    private void drawSewer() {
     ArrayList<Wall> sewerWalls = new ArrayList<Wall>();
     for (int i=12 ; i<=21; i++ ) {
      sewerWalls.add(new Wall(theWorld, 1, i, Direction.NORTH));
      sewerWalls.add(new Wall(theWorld, 1, i, Direction.SOUTH));
     }
     for (int i=2 ; i<=6; i++ ) {
      sewerWalls.add(new Wall(theWorld, i, 22, Direction.EAST));
      sewerWalls.add(new Wall(theWorld, i, 22, Direction.WEST));
     }
  sewerWalls.add(new Wall(theWorld, 1, 22, Direction.EAST));
  sewerWalls.add(new Wall(theWorld, 1, 22, Direction.NORTH));
  sewerWalls.add(new Wall(theWorld, 7, 22, Direction.EAST));
  sewerWalls.add(new Wall(theWorld, 7, 22, Direction.SOUTH));
  sewerWalls.add(new Wall(theWorld, 7, 21, Direction.NORTH));
  sewerWalls.add(new Wall(theWorld, 7, 21, Direction.SOUTH));
  
  for (int i = 0; i < sewerWalls.size(); i++) {
   sewerWalls.get(i).setColor(Color.DARK_GRAY);
  }
    }
  
    /**
     * Initialises the characters to default locations
     */
    private void createCharacters() {
     player = new PlayerCharacter(theWorld, areas.get("apartment"), Direction.SOUTH);
     player.setColor(Color.BLUE);
     
     npc.put("ganger", new EnemyCharacter(theWorld, areas.get("gangLandW"), Direction.SOUTH, Direction.SOUTH));
     npc.get("ganger").setColor(Color.ORANGE);
     
     npc.put("guard", new EnemyCharacter(theWorld, areas.get("yardW"), Direction.SOUTH, Direction.NORTH));
     
     npc.put("chief", new NPCharacter(theWorld, areas.get("policeHQ"), Direction.SOUTH));
     npc.get("chief").setColor(Color.DARK_GRAY);
     
        npc.put("coroner", new NPCharacter(theWorld, areas.get("crimeScene"), Direction.WEST));
     npc.get("coroner").setColor(Color.CYAN);
     
    }
    
    /**
     * Adds items to their default starting positions
     */
    private void addItems() {
     player.addItem("pager", new Item(1));
     createItem(areas.get("apartment"), "pistol", 5);
     createItem(areas.get("apartment"), "dumbbell", 26); 
     createItem(areas.get("policeHQ"), "tacvest", 10);
     createItem(areas.get("warehouse"), "evidence", 2);
    }
    
    /**
     * Adds an item to a room and the world map
     */
    private void createItem(Room room, String itemName, int weight) {
     room.addItem(itemName, new Item(weight));
     @SuppressWarnings("unused")
  Thing dummy = new Thing(theWorld, room.getPlayerStreet(), room.getPlayerAvenue()); 
    }
  
    /**
     * Loops while game is being played
     */
    public void play() {
     printWelcome();
     boolean finished = false;
        while (! finished)  {
         System.out.println("What do you do?");
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (!player.isAlive()) {
             System.out.println("You have died");
             break;
            }
            if (confrontedChief) {
             System.out.println("He admits the murder");
             System.out.println("You have avenged your partner");            
             break;
            }
        }
        System.out.println("Thank you for playing. Goodbye.");
        System.exit(0);
    }

    /**
     * Processes command and executes appropriate action
     * 
     * @param command Command to be processed
     * @return True if game has ended
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;
        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
        
            case UNKNOWN:
                System.out.println("What do you mean");
                break;

            case MOVE:
             movePlayer();
             processAI();
                break;

            case TURN:
                turnPlayer(command);
                break;
             
            case BACK:
             backPlayer();
             processAI();
             break;
             
            case CHECK:
             checkInventory(command);
             break;
             
            case PICK:
             pickItem(command);
             break;
             
            case DROP:
             dropItem(command);
             break;
             
            case USE:
             playerUse(command);
             processAI();
             break;
             
            case ATTACK:
             playerAttack(command);
             processAI();
             break;
            
            case TALK:
             playerTalk(command);
             break;
                
            case HELP:
                System.out.println("Actions you can do are");
                parser.showCommands();
                break;
                
            case QUIT:
                wantToQuit = true;
                break;
        }
        
        return wantToQuit;
    }
    
    /**
     * Displays start message
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("You are Ford Harrison, a hard boiled detective in an 80s vision of the near future");
        System.out.println("You are relaxing in your apartment after a long day but your pager has started buzzing");
        System.out.println();
        System.out.println("Type 'help' if you need help.");
        System.out.println();
    }
    
    /**
     * Moves the player into the room they are facing
     * Displays an error if there is no exit in the current direction
     */
    private void movePlayer(){
     if (!checkedPager) {
      System.out.println("Why would you want go out? It's late");
      return;
     }
     Room currentRoom = player.getCurrentRoom();
     Direction facing = player.getDirection();
     Exit exit = currentRoom.getExit(facing);
     Room nextRoom = currentRoom.getExitRoom(facing); 
        if (nextRoom == null) {
            System.out.println("There's nothing interesting in that direction");
        } else if (nextRoom.equals(areas.get("gangLandW"))) {
         if (!checkedEye) {
          System.out.println("That's gang teritory. Why would you go there?");
         } else {
             player.moveRoom(exit);
             System.out.println("You are in " + nextRoom.getDescription() + ", " + nextRoom.getLongDescription());
         }
        } else {
         player.moveRoom(exit);
         System.out.println("You are in " + nextRoom.getDescription() + ", " + nextRoom.getLongDescription());
        }
    }
    
    /**
     * Processes the actions of NPCs
     * 
     */
    private void processAI(){
        aiPatrol(npc.get("guard"));
        aiLoiter(npc.get("ganger"));
    }
    
    /**
     * Turns the player in a given direction
     * Displays an error message if the direction is invalid
     * 
     * @param command The direction you want to turn
     */
    private void turnPlayer(Command command){
     if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which way to turn
            System.out.println("turn which way?");
            return;
        }

        String direction = command.getSecondWord();
        if(direction.equals("left")) { 
            player.turnLeft();
        } else if(direction.equals("right")) {
            player.turnRight();
        } else if(direction.equals("around")) {
         player.turnAround();
        } else {
            System.out.println("You cannot turn that way");
        }
        System.out.println("You are currently facing " + player.getDirection().toString().toLowerCase());
    }
    
    /**
     * Moves the player back a room
     */
    private void backPlayer() {
     Direction previousDir = player.getLastRoomDir();
     if (previousDir == null) {
      if (player.getCurrentRoom().equals(areas.get("apartment"))) {
       System.out.println("You can't go back, you haven't been anywhere else yet");
      } else if (player.getCurrentRoom().equals(areas.get("stationNorth")) || player.getCurrentRoom().equals(areas.get("stationSouth"))) {
       System.out.println("You can't go back, the train has just left. Try catching another");
      } else if (player.getCurrentRoom().equals(areas.get("gangLandN")) || player.getCurrentRoom().equals(areas.get("warehouse"))) {
       System.out.println("You have to really want to crawl back through that sewer");    
      }
     } else {
      player.turn(player.getLastRoomDir());
      movePlayer();
     }
    }
    
    /**
     * Checks items and characters in the world
     * @param command the command
     */
    private void checkInventory(Command command){
     if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to check
      System.out.println("What are you trying to check? Your pockets, items in the area, or other people in the area");
      return;
     }
     String target = command.getSecondWord();
     if (target.equals("pockets")) {
      checkPlayerInventory();
     } else if (target.equals("area")) {
         System.out.println("You look around the area");
      checkRoomInventory();
     } else if (target.equals("people")){
      listNPCinRoom(player.getCurrentRoom());
     } else {
      System.out.println("What are you trying to check? Your pockets, items in the area, or other people in the area");
     }  
    }
    
    /**
     * Displays what the player is carrying
     */
    private void checkPlayerInventory() {
     if (player.getInventory().isEmpty()) {
      System.out.println("You aren't carrying anything");
     } else {
      System.out.println("You check your pockets");
      System.out.println("You are currently carrying:");
      player.printInventory();
      System.out.println("In total the items you are carrying weigh " + player.getWeight() +"kgs");
     }
     System.out.println("You can carry " + (Character.MaxWeight - player.getWeight()) + " more kgs");
    }
    
    /**
     * Displays the contents of the room the player is in
     */
    private void checkRoomInventory() {
     Inventory inventory = player.getCurrentRoom().getInventory();
     if (inventory.isEmpty()) {
      System.out.println("There are no useful items here");
     } else {
      System.out.println("Useful items around here are: ");
      inventory.listItems();
     }
    }
    
    /**
     * Attempts to pick an item up
     * @param command the command
     */
 private void pickItem(Command command) {
     if(!command.hasSecondWord()) {
      //if there is no second word, we don't know what item to pick up
      System.out.println("Pick up what?");
      return;
     }
     
     String itemName = command.getSecondWord();
     if (player.getCurrentRoom().hasItem(itemName)) {
      Inventory roomInventory = player.getCurrentRoom().getInventory();
      Item item = roomInventory.getItem(itemName);
      if (player.addItem(itemName, item)) {
          roomInventory.removeItem(itemName);
       System.out.println("You pick up the " + itemName);
       if (player.canPickThing()) {
        player.pickThing();
       }
      } else if (item.getWeight() <= Character.MaxWeight) {
        System.out.println("You could carry that, but you'll need to drop something else first");
        System.out.println("You think it wieghs " + item.getWeight() + "kgs");
      } else {
        System.out.println("You can't pick up a " + itemName);
      }
     } else {
       System.out.println("There isn't a " + itemName + " to pick up");
     }
    } 
    
 /**
  * Moves an item from the player to the floor if it exists
  * 
  * @param command the command
  */
 private void dropItem(Command command) {
     if(!command.hasSecondWord()) {
      //if there is no second word, we don't know what item to drop
      System.out.println("Drop what?");
      return;
     }
     
     String itemName = command.getSecondWord();
  if (player.hasItem(itemName)) {
      Inventory roomInventory = player.getCurrentRoom().getInventory();
      Item item = player.getInventory().getItem(itemName);
      player.removeItem(itemName);
      roomInventory.putItem(itemName, item);
      System.out.println("You drop the " + itemName);
   @SuppressWarnings("unused")
   Thing dummy = new Thing(theWorld, player.getStreet(), player.getAvenue()); 
     } else {
      System.out.println("You don't have a " + itemName + " to drop");
     }   
 }
 
 /**
  * Lets the player use items or features of special rooms
  * 
  * @param command the command
  */
 private void playerUse(Command command) {
     if(!command.hasSecondWord()) {
      //if there is no second word, we don't know what to use
      System.out.println("Use what?");
      return;
     }
     
     String useItem = command.getSecondWord();
     if (useItem.equals("train")) {
      useTrain();
     } else if (useItem.equals("sewer")) { 
      useSewer();
     } else if (useItem.equals("pager")) {
      usePhone();
     } else if (useItem.equals("cybereye")) {
      useEye();
     } else if (useItem.equals("evidence")) {
      useEvidence();
     } else {
      System.out.println("You can't use that");
     }
 }
 
 /**
  * Lets the player use the train special exit
  */
 private void useTrain() {
  if (player.getCurrentRoom().equals(areas.get("stationNorth"))) {
   System.out.println("You board the southbound train");
   player.turn(Direction.SOUTH);
   player.setSpeed(PlayerCharacter.DefaultSpeed * 3);
   player.move(12);
   player.turnLeft();
   player.move(3);
   player.setCurrentRoom(areas.get("stationSouth"));
   player.setSpeed(PlayerCharacter.DefaultSpeed);
         System.out.println("You are in " + player.getCurrentRoom().getDescription() + ", " + player.getCurrentRoom().getLongDescription());
  } else if (player.getCurrentRoom().equals(areas.get("stationSouth"))) {
         System.out.println("You board the northbound train");
   player.turn(Direction.WEST);
   player.setSpeed(PlayerCharacter.DefaultSpeed * 3);
   player.move(3);
   player.turnRight();
   player.move(12);
   player.setCurrentRoom(areas.get("stationNorth"));
   player.setSpeed(PlayerCharacter.DefaultSpeed);
         System.out.println("You are in " + player.getCurrentRoom().getDescription() + ", " + player.getCurrentRoom().getLongDescription());
  } else {
   System.out.println("There isn't a train here");
  }
 }
 
 /**
  * Lets the player use the sewer special exit
  */
 private void useSewer() {
  if (player.getCurrentRoom().equals(areas.get("gangLandN"))) {
         System.out.println("You clamber into the sewer");
   player.turn(Direction.EAST);
   player.move(12);
   player.turnRight();
   player.move(6);
   player.turnRight();
   player.move(3);
   player.setCurrentRoom(areas.get("warehouse"));
         System.out.println("You are in " + player.getCurrentRoom().getDescription() + ", " + player.getCurrentRoom().getLongDescription());
  } else if (player.getCurrentRoom().equals(areas.get("warehouse"))) {
         System.out.println("You clamber into the sewer");
   player.turn(Direction.EAST);
   player.move(3);
   player.turnLeft();
   player.move(6);
   player.turnLeft();
   player.move(12);
   player.setCurrentRoom(areas.get("gangLandN"));
         System.out.println("You are in " + player.getCurrentRoom().getDescription() + ", " + player.getCurrentRoom().getLongDescription());
  } else {
   System.out.println("You can't use that");
  }
 }
 
 /**
  * Attempts to use the pager
  */
 private void usePhone() {
  if (player.hasItem("pager")) {
   System.out.println("You check your pager");
   if (!checkedPager) {
    System.out.println("It's a message from the coroner. It reads: ");
    System.out.println(" --");
    System.out.println(" - Your partner has been found murdered -");
    System.out.println(" - There's some evidence you probably need to see -");
    System.out.println(" - The crime scene is to the south of the police station -");
    System.out.println(" --");
    System.out.println("You should probably go and investigate");
    System.out.println("The police station is a short train journey from here, The metro station is south west of here");
    checkedPager = true;
   } else {
    System.out.println("There are no new messages");
   }
  } else {
   System.out.println("You've dropped your pager somewhere");
  }
 }
 
 /**
  * Attempts to use the cybereye
  */
 private void useEye() {
  if (player.hasItem("cybereye")) {
   System.out.println("You check the cybereye");
   System.out.println("Video on the eye seems to point to a warehouse");
   System.out.println("You recognise it, it's inside gang territory");
   System.out.println("You should investigate it");
   checkedEye = true;
  } else {
   System.out.println("You can't use that");
  } 
 }
 
 /**
  * Attempts to use the evidence
  */
 private void useEvidence() {
  if (player.hasItem("evidence")) {
   System.out.println("You check the evidence");
   System.out.println("It implicates the chief in the murder");
   System.out.println("Your partner discovered the extent of his corruption");
   System.out.println("and was killed because of it");
   System.out.println("You should confront him");   
   checkedEvidence = true;
  } else {
   System.out.println("You can't use that");
  } 
 }
 
 /**
  * Attempts to let the player make an attack
  * @param command the command
  */
 private void playerAttack(Command command){
  if (player.hasItem("pistol")) {
   if(!command.hasSecondWord()) {
    //if there is no second word, we don't know who to attack
    System.out.println("Attack who?");
    return;
   }
   String target = command.getSecondWord(); 
   if (playerCheckNPCinRoom(target)) {
    if (target.equals("coroner") || target.equals("chief")){
     System.out.println("What are you trying to do? they're not hostile");
     System.out.println("You're not a murderer");
    } else{
    System.out.println("You shoot at " + target);
    int damageDone = npc.get(target).damage(6);
    System.out.println("You attack the " + target + " for " + damageDone + " damage");
     }
    if (!npc.get(target).isAlive()) {
     System.out.println("The " + target + " is dead");
     npc.get(target).setTransparency(0.5);
    }
   }
   return;
  }
  System.out.println("You can't attack anyone without a pistol");
 }
 
 /**
  * Attempts to talk to an NPC
  * 
  * @param command the command
  */
 private void playerTalk(Command command) {
  if(!command.hasSecondWord()) {
   //if there is no second word, we don't know who to talk to
   System.out.println("Talk to who?");
   return;
  }
  String target = command.getSecondWord();
  if (playerCheckNPCinRoom(target)) {
   playerTalkTo(target);
  }
 }
 
 /**
  * Checks who the player is trying to talk to and displays an appropriate message
  * @param target the npc
  */
 private void playerTalkTo(String target) {
  if (target.equals("ganger") || target.equals("guard")) {
   System.out.println("Why would you want to talk to them?");
  } else if (target.equals("chief")) {
   talkChief();
  } else if (target.equals("coroner")) {
   talkCoroner();
  }
   
 }
 
 /**
  * Interacts with the coroner
  */
 private void talkCoroner() {
  if (!talkedCoroner) {
      createItem(areas.get("crimeScene"), "cybereye", 1);
      talkedCoroner = true;
      System.out.println("You talk to the coroner");
      System.out.println(" - The chief has taken you off the case");
      System.out.println(" - Something seems suspicious here");
      System.out.println(" - Check this data on his cybereye");
  } else {

      System.out.println(" - Have you checked the cybereye yet?");
  }
 }
 
 /**
  * Interacts with the chief
  */
 private void talkChief() {
  if (!talkedCoroner) {
      System.out.println("You talk to the chief");
      System.out.println(" - What are you doing here this time of night?");
    } else if (!checkedEvidence) {
      System.out.println("The chief has taken you off the case");
      System.out.println("You don't want to talk to him");
    } else {
     if (player.hasItem("evidence")) {
       System.out.println("You confront the chief");
       confrontedChief = true;
     } else {
       System.out.println("You need evidence to confront the chief");
     }
    }
 }
 
 /**
  * Checks if an NPC is in the same room as the player
  * 
  * @param charName the NPCs name
  * @return true if they are here
  */
 private boolean playerCheckNPCinRoom(String charName) {
  if(checkNPCinRoom(charName, player.getCurrentRoom())){
   return true;
  } else {
   System.out.println("They aren't here");
   return false;
  }
 }
 
 /**
  * Checks to see if an NPC is in a room
  * 
  * @param charName the NPC to look for
  * @param roomToCheck the room to check
  * @return
  */
 private boolean checkNPCinRoom(String charName, Room roomToCheck) {
  if (npc.containsKey(charName)) {
   Character character = npc.get(charName);
   if (character.getCurrentRoom() == roomToCheck) {
    return true;
   } else {
    return false;
   }
  } else {
   return false;
  }
 }
 
 /**
  * Lists the NPCs in a room
  * 
  * @param roomToCheck the room to check
  */
 private void listNPCinRoom(Room roomToCheck) {
  System.out.println("You look around for other people here:");
  boolean anyone = false;
  for (String name : npc.keySet()) {
   if (checkNPCinRoom(name, roomToCheck)) {
    System.out.println("The " + name);
    anyone = true;
   } 
  }
  if (!anyone) {
   System.out.println("There's no one here");
  }
 }
 
 /**
  * Allows an AI to check if the player is in the same room
  * 
  * @param searcher the NPC
  * @return true if player is there
  */
 private boolean checkPlayerInSameRoom(Character searcher) {
  if (searcher.getCurrentRoom() == player.getCurrentRoom()) {
   return true;
  } else {
   return false;
  }
 }
 
 /**
  * Allows the NPC to attack the player
  * 
  * @param attacker the NPC
  * @param attackerName the description of the NPC
  * @param damageAmount the damage
  */
 private void aiAttack(Character attacker, String attackerName, int damageAmount) {
  int damageDone = player.damage(damageAmount);
  System.out.println("You are attacked by the " + attackerName + " for " + damageDone + " damage");
  
 }
 
 /**
  * An AI that waits for the player, then attacks
  * 
  * @param loiterer the NPC
  */
 private void aiLoiter(Character loiterer) {
  if (loiterer.isAlive()) {
   if (checkPlayerInSameRoom(loiterer)) {
    aiAttack(loiterer, "ganger", 6);
   }
  }
 }
 
 /**
  * An AI that patrols and attacks the player if they are in the same room
  * @param patroller the NPC
  */
 private void aiPatrol(Character patroller) {
  if (patroller.isAlive()) {
   if (checkPlayerInSameRoom(patroller)) {
    aiAttack(patroller, "guard", 13);
   } else {
    if (patrolAICanMove) {
        patroller.turn(patroller.getLastRoomDir());
        moveNPC(patroller);
        patroller.turn(Direction.SOUTH);
        patrolAICanMove = false;
        if (checkPlayerInSameRoom(patroller)){
      aiAttack(patroller, "guard", 13);
        }
    } else {
     patrolAICanMove = true;
       }
   }
  }
 }

 /**
  * Moves an NPC
  * 
  * @param npc the NPC to move
  */
 private void moveNPC(Character npc){
  Room currentRoom = npc.getCurrentRoom();
  Direction facing = npc.getDirection();
  Exit exit = currentRoom.getExit(facing);
  
  Room nextRoom = currentRoom.getExitRoom(facing); 
     if (nextRoom != null) {
      npc.moveRoom(exit);
     }
 }
 
 
}