package com.example.servertemplateforcardsupdate2122;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.swing.JOptionPane;
import socketfx.Constants;
import socketfx.FxSocketServer;
import socketfx.SocketListener;

public class HelloController implements Initializable {
    boolean areReady = false;
    boolean clientReady = false;

    @FXML
    private Button sendButton,ready,button1,button2,button3,button4,button5,button6,button7,button8,button9,button10;
    @FXML
    private TextField sendTextField;
    @FXML
    private Button connectButton;
    @FXML
    private TextField portTextField;
    @FXML
    private Label lblMessages, reveal;



    private final static Logger LOGGER =
            Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private boolean isConnected;
    private int counter = 0;
    private String color;

    public enum ConnectionDisplayState {

        DISCONNECTED, WAITING, CONNECTED, AUTOCONNECTED, AUTOWAITING
    }

    private FxSocketServer socket;

    private void connect() {
        socket = new FxSocketServer(new FxSocketListener(),
                Integer.valueOf(portTextField.getText()),
                Constants.instance().DEBUG_NONE);
        socket.connect();
    }

    private void displayState(ConnectionDisplayState state) {
//        switch (state) {
//            case DISCONNECTED:
//                connectButton.setDisable(false);
//                sendButton.setDisable(true);
//                sendTextField.setDisable(true);
//                break;
//            case WAITING:
//            case AUTOWAITING:
//                connectButton.setDisable(true);
//                sendButton.setDisable(true);
//                sendTextField.setDisable(true);
//                break;
//            case CONNECTED:
//                connectButton.setDisable(true);
//                sendButton.setDisable(false);
//                sendTextField.setDisable(false);
//                break;
//            case AUTOCONNECTED:
//                connectButton.setDisable(true);
//                sendButton.setDisable(false);
//                sendTextField.setDisable(false);
//                break;
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isConnected = false;
        displayState(ConnectionDisplayState.DISCONNECTED);




        Runtime.getRuntime().addShutdownHook(new ShutDownThread());

        /*
         * Uncomment to have autoConnect enabled at startup
         */
//        autoConnectCheckBox.setSelected(true);
//        displayState(ConnectionDisplayState.WAITING);
//        connect();
    }

    class ShutDownThread extends Thread {

        @Override
        public void run() {
            if (socket != null) {
                if (socket.debugFlagIsSet(Constants.instance().DEBUG_STATUS)) {
                    LOGGER.info("ShutdownHook: Shutting down Server Socket");
                }
                socket.shutdown();
            }
        }
    }
    @FXML
    private void handleConnectButton(ActionEvent event) {
        connectButton.setDisable(true);

        displayState(ConnectionDisplayState.WAITING);
        connect();
    }
    //****************************************************************
    class FxSocketListener implements SocketListener {

        @Override
        public void onMessage(String line) {
            System.out.println("message received client");
            lblMessages.setText(line);
            if (line.equals("ready") && areReady){
                deal.setDisable(false);
                ready.setVisible(false);

            }else if(line.equals("ready")){
                clientReady=true;

            }else if(line.equals(("clientDrawCard"))){

                clientDrawCard();
            }else if(line.startsWith("checkCard")){
                checkCard(hand2D,Integer.parseInt(line.substring(10,11)),next(hand2D));
            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {

        }
    }

    @FXML
    private void handleSendMessageButton(ActionEvent event) {
        if (!sendTextField.getText().equals("")) {
            socket.sendMessage(sendTextField.getText());
            System.out.println("Message sent client");
        }
    }
    @FXML
    private void handleReady(ActionEvent event) {
//        if (!sendTextField.getText().equals("")) {
//            String x = sendTextField.getText();
//            socket.sendMessage(x);
//            System.out.println("sent message client");
//        }
        areReady=true;
        socket.sendMessage("ready");
        if (clientReady){
            ready.setVisible(false);
            deal.setDisable(false);

        }else{
            ready.setDisable(true);
        }
    }


    @FXML
    private void handleDeal(ActionEvent event){
        hand1I.add(imgS0);
        hand1I.add(imgS1);
        hand1I.add(imgS2);
        hand1I.add(imgS3);
        hand1I.add(imgS4);
        hand1I.add(imgS5);
        hand1I.add(imgS6);
        hand1I.add(imgS7);
        hand1I.add(imgS8);
        hand1I.add(imgS9);

        hand2I.add(imgC0);
        hand2I.add(imgC1);
        hand2I.add(imgC2);
        hand2I.add(imgC3);
        hand2I.add(imgC4);
        hand2I.add(imgC5);
        hand2I.add(imgC6);
        hand2I.add(imgC7);
        hand2I.add(imgC8);
        hand2I.add(imgC9);

        hand3I.add(imgB0);
        hand3I.add(imgB1);
        hand3I.add(imgB2);
        hand3I.add(imgB3);
        hand3I.add(imgB4);
        hand3I.add(imgB5);
        hand3I.add(imgB6);
        hand3I.add(imgB7);
        hand3I.add(imgB8);
        hand3I.add(imgB9);

        imgC0.setImage(imageBack);
        imgC1.setImage(imageBack);
        imgC2.setImage(imageBack);
        imgC3.setImage(imageBack);
        imgC4.setImage(imageBack);
        imgC5.setImage(imageBack);
        imgC6.setImage(imageBack);
        imgC7.setImage(imageBack);


        deck.clear();
        //populate deck
        for(int i = 0;i<13;i++)
        {

            deck.add(new Card("B" +Integer.toString(i)));
            deck.add(new Card("G"+Integer.toString(i)));
            deck.add(new Card("R"+Integer.toString(i)));
            deck.add(new Card("Y"+Integer.toString(i)));


        }


        for(int i = 0;i<13;i++)
        {

            deck.add(new Card("B" +Integer.toString(i)));
            deck.add(new Card("G"+Integer.toString(i)));
            deck.add(new Card("R"+Integer.toString(i)));
            deck.add(new Card("Y"+Integer.toString(i)));


        }


        //deal each hand
        hand2D.clear();
        hand1D.clear();
        hand3D.clear();
        //deal server hand
        numCardsInDeck = deck.size();
        System.out.println(numCardsInDeck);
        System.out.println("Server hand");
        for(int i = 0;i<7;i++)
        {
            int y = (int)(Math.random()*(54-i));
            try {
                tempCard = new FileInputStream(deck.get(y).getCardPath());
                imageFront = new Image(tempCard);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            hand1I.get(i).setImage(imageFront);
            System.out.println(deck.get(y).getCardPath());
            hand1D.add(deck.remove(y));
            numCardsInDeck--;

        }
        for(int i = 0;i<7;i++)
        {
            int y = (int)(Math.random()*(47-i));
            try {
                tempCard = new FileInputStream(deck.get(y).getCardPath());
                imageFront = new Image(tempCard);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            hand3I.get(i).setImage(imageFront);
            System.out.println(deck.get(y).getCardPath());
            hand3D.add(deck.remove(y));
            numCardsInDeck--;

        }

        //deal client hand
        System.out.println("Client hand");
        for(int i = 0;i<7;i++)
        {
            int y = (int)(Math.random()*(40-i));
            System.out.println(deck.get(y).getCardPath());
            hand2D.add(deck.remove(y));
            numCardsInDeck--;

        }
        socket.sendMessage("dealt");
        sendHandClient();
        lblMessages.setText("server draw a card");
        turn =0;
        //deal discard
        discardPile.add(deck.get((int)(Math.random()*numCardsInDeck)));
        numCardsInDeck--;
        try {
            tempCard = new FileInputStream(discardPile.get(discardPile.size()-1).getCardPath());
            imageFront = new Image(tempCard);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        imgDiscard.setImage(imageFront);
        sendDiscardPile();
        deal.setDisable(true);
        draw.setDisable(false);

    }
    public void handleDraw(){
        if (turn ==0){
            hand1D.add(deck.get((int)(Math.random()*numCardsInDeck)));
            numCardsInDeck--;
            printServerScreen();
            changeTurn(direction);
            socket.sendMessage("serverDrawCard");
            draw.setDisable(true);
            changeButtons(true);


        }else{
            System.out.println("clients turn");
        }

    }
    public void clientDrawCard(){
        hand2D.add(deck.get((int)(Math.random()*numCardsInDeck)));
        numCardsInDeck--;
        changeTurn(direction);
        draw.setDisable(false);
        printServerScreen();
        sendHandClient();
        changeButtons(false);
    }

    public void checkCard(List<Card> hand, int index, List<Card> handNext){
        System.out.println("checking");
        String name = hand.get(index).cName;
        String discard = discardPile.get(discardPile.size()-1).getCardName();
        System.out.println(name);
        System.out.println(discard);
        if(name.substring(0,1).equals(discard.substring(0,1)) || name.substring(1,name.length()).equals(discard.substring(1,discard.length())) || name.charAt(0) == 'W'){
            System.out.println("true");
            discardPile.add(hand.get(index));
            try {
                tempCard = new FileInputStream(discardPile.get(discardPile.size()-1).getCardPath());
                imageFront = new Image(tempCard);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            imgDiscard.setImage(imageFront);
            hand.remove(index);
            numCardsInDeck--;
            sendHandClient();
            sendDiscardPile();

            printServerScreen();

            if(turn == 1){
                if(name.substring(1).equals("12")){
                    handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    numCardsInDeck--;
                    handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    numCardsInDeck--;
                }
                if(name.substring(1).equals("1")){
                    for (int i = 0; i < hand2D.size(); i++) {
                        for (int j = i + 1; j < hand2D.size(); j++) {
                            if (hand2D.get(i).getCardName().substring(1).equals(hand2D.get(j).getCardName().substring(1))) {
                                hand2D.remove(j);
                                j--;
                            }
                        }
                    }
                }
                if(name.substring(1).equals("2")){

                    for(int i = 0; i<handNext.size(); i++){
                        if(handNext.get(i).getCardName().substring(1).equals("2")){

                            handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                            numCardsInDeck--;
                        }
                    }
                }
                if(name.substring(1).equals("3")){
                    if(hand2D.size()>3){
                        hand2D.remove(2);
                    }
                }
                if(name.substring(1).equals("4")){
                    if(handNext.size()>3){
                        socket.sendMessage("reveal" + handNext.get(3).getCardName());
                    }



                }
                if(name.substring(1).equals("5")){
                    if(handNext.size()>4){
                        socket.sendMessage("reveal" + handNext.get(4).getCardName());
                    }
                    if(hand2D.size()>4){
                        hand2D.remove(4);
                    }
                }
                if(name.substring(1).equals("6")){
                    boolean finished = false;
                    while(!finished) {
                        if (hand2D.size() > 6) {
                            hand2D.remove(6);
                        }else{
                            finished = true;
                        }
                    }
                }
                if(name.substring(1).equals("7")){
                    int rand = (int) (Math.random()*(2));
                    if(rand == 2){
                        for(int i = 0; i<hand2D.size();i++){
                            if(hand2D.get(i).getCardName().substring(0,1).equals(name.substring(0,1))){
                                hand2D.remove(i);
                            }
                        }
                    }
                }
                if(name.substring(1).equals("8")){
                    int rand = (int) (Math.random()*(1));
                    int rand1 = (int) (Math.random()*(1));
                    if(rand1 == 1){
                        rand = rand*-1;
                    }
                    if(rand>0){
                        hand2D.remove(0);
                    }else{
                        hand2D.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    }
                }
                if(name.substring(1).equals("9")){
                    for(int i =0; i < hand2D.size();i++){
                        if(hand2D.get(i).getCardName().substring(1).equals("9") || hand2D.get(i).getCardName().substring(1).equals("5")){
                            hand2D.remove(i);
                        }
                    }

                }
                if(name.substring(1).equals("10")){
                    changeTurn(direction);
                    socket.sendMessage("turnChange");
                }
                if(name.substring(1).equals("11")){
                    if(direction==0){
                        direction = 1;
                    }
                    if(direction==1){
                        direction = 0;
                    }
                    socket.sendMessage("direction");
                }
                changeTurn(direction);
                changeButtons(false);
                socket.sendMessage("successful");
                sendHandClient();
                printServerScreen();
            }else if(turn == 0){
                if(name.substring(1).equals("12")){
                    handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    numCardsInDeck--;
                    handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    numCardsInDeck--;
                }
                if(name.substring(1).equals("1")){
                    for (int i = 0; i < hand1D.size(); i++) {
                        for (int j = i + 1; j < hand1D.size(); j++) {
                            if (hand1D.get(i).getCardName().substring(1).equals(hand1D.get(j).getCardName().substring(1))) {
                                hand1D.remove(j);
                                j--;
                            }
                        }
                    }
                }
                if(name.substring(1).equals("2")){

                    for(int i = 0; i<handNext.size(); i++){
                        if(handNext.get(i).getCardName().substring(1).equals("2")){
                            handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                            numCardsInDeck--;
                        }
                    }
                }
                if(name.substring(1).equals("3")){
                    if(hand1D.size()>3){
                        hand1D.remove(2);
                    }
                }
                if(name.substring(1).equals("4")){
                    if(handNext.size()>4){
                        reveal.setText(hand2D.get(3).getCardName());
                    }



                }
                if(name.substring(1).equals("5")) {
                    if (handNext.size() > 4) {
                        reveal.setText(hand2D.get(4).getCardName());
                    }
                    if (hand1D.size() > 4) {
                        hand1D.remove(4);
                    }
                }
                if(name.substring(1).equals("6")){
                    boolean finished = false;
                    while(!finished) {
                        if (hand1D.size() > 6) {
                            hand1D.remove(6);
                        }else{
                            finished = true;
                        }
                    }
                }
                if(name.substring(1).equals("7")){
                    int rand = (int) (Math.random()*(2));
                    if(rand == 2){
                        for(int i = 0; i<hand1D.size();i++){
                            if(hand1D.get(i).getCardName().substring(0,1).equals(name.substring(0,1))){
                                hand1D.remove(i);
                            }
                        }
                    }
                }
                if(name.substring(1).equals("8")){
                    int rand = (int) (Math.random()*(2));

                    if(rand==1){
                        hand1D.remove(0);
                    }else{
                        hand1D.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    }
                }
                if(name.substring(1).equals("9")){
                    for(int i =0; i < hand1D.size();i++){
                        if(hand1D.get(i).getCardName().substring(1).equals("9") || hand1D.get(i).getCardName().substring(1).equals("5")){
                            hand1D.remove(i);
                        }
                    }


                }
                if(name.substring(1).equals("10")){
                    changeTurn(direction);
                    socket.sendMessage("turnChange");
                }
                if(name.substring(1).equals("11")){
                    if(direction==0){
                        direction = 1;
                    }
                    if(direction==1){
                        direction = 0;
                    }
                    socket.sendMessage("direction");
                }

                changeTurn(direction);
                System.out.println(turn);
                if(turn == 1) {
                    socket.sendMessage("serverTurnOver");
                }
                changeButtons(true);
                sendHandClient();
                printServerScreen();

            } else if(turn == 2){
                if(name.substring(1).equals("12")){
                    handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    numCardsInDeck--;
                    handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    numCardsInDeck--;
                }
                if(name.substring(1).equals("1")){
                    for (int i = 0; i < hand1D.size(); i++) {
                        for (int j = i + 1; j < hand1D.size(); j++) {
                            if (hand1D.get(i).getCardName().substring(1).equals(hand1D.get(j).getCardName().substring(1))) {
                                hand1D.remove(j);
                                j--;
                            }
                        }
                    }
                }
                if(name.substring(1).equals("2")){

                    for(int i = 0; i<handNext.size(); i++){
                        if(handNext.get(i).getCardName().substring(1).equals("2")){
                            handNext.add(deck.get((int)(Math.random()*numCardsInDeck)));
                            numCardsInDeck--;
                        }
                    }
                }
                if(name.substring(1).equals("3")){
                    if(hand3D.size()>3){
                        hand3D.remove(2);
                    }
                }
                if(name.substring(1).equals("4")){
                    if(handNext.size()>3){
                        reveal.setText(handNext.get(3).getCardName());
                    }



                }
                if(name.substring(1).equals("5")) {
                    if (handNext.size() > 6) {
                        reveal.setText(handNext.get(4).getCardName());
                    }
                    if (hand3D.size() > 6) {
                        hand3D.remove(4);
                    }
                }
                if(name.substring(1).equals("6")){
                    boolean finished = false;
                    while(!finished) {
                        if (hand3D.size() > 6) {
                            hand3D.remove(6);
                        }else{
                            finished = true;
                        }
                    }
                }
                if(name.substring(1).equals("7")){
                    int rand = (int) (Math.random()*(2));
                    if(rand == 2){
                        for(int i = 0; i<hand3D.size();i++){
                            if(hand3D.get(i).getCardName().substring(0,1).equals(name.substring(0,1))){
                                hand3D.remove(i);
                            }
                        }
                    }
                }
                if(name.substring(1).equals("8")){
                    int rand = (int) (Math.random()*(1));
                    int rand1 = (int) (Math.random()*(1));
                    if(rand1 == 1){
                        rand = rand*-1;
                    }
                    if(rand>0){
                        hand3D.remove(0);
                    }else{
                        hand3D.add(deck.get((int)(Math.random()*numCardsInDeck)));
                    }
                }
                if(name.substring(1).equals("9")){
                    for(int i =0; i < hand3D.size();i++){
                        if(hand3D.get(i).getCardName().substring(1).equals("9") || hand3D.get(i).getCardName().substring(1).equals("5")){
                            hand3D.remove(i);
                        }
                    }


                }
                if(name.substring(1).equals("10")){
                    changeTurn(direction);
                    socket.sendMessage("turnChange");
                }
                if(name.substring(1).equals("11")){
                    if(direction==0){
                        direction = 1;
                    } else if(direction==1){
                        direction = 0;
                    }
                    socket.sendMessage("direction");
                }

                changeTurn(direction);
                if(turn == 1) {
                    socket.sendMessage("serverTurnOver");
                    changeButtons(true);
                }
                if(turn == 0){

                    changeButtons(false);
                }
                sendHandClient();
                printServerScreen();
            }


            System.out.println("turn: " + turn);
            System.out.println("direction: " +direction);
        }
    }


    public void printServerScreen(){
        for (ImageView x: hand1I){
            x.setImage(null);
        }
        for (ImageView x: hand2I){
            x.setImage(null);
        }
        for (ImageView x: hand3I){
            x.setImage(null);
        }
        for (int i = 0;i<hand1D.size();i++){
            try {
                tempCard = new FileInputStream(hand1D.get(i).getCardPath());
                imageFront = new Image(tempCard);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            hand1I.get(i).setImage(imageFront);
        }
        for (int i = 0;i<hand3D.size();i++){
            try {
                tempCard = new FileInputStream(hand3D.get(i).getCardPath());
                imageFront = new Image(tempCard);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            hand3I.get(i).setImage(imageFront);
        }

        for(int i =0;i<hand2D.size();i++){
            hand2I.get(i).setImage(imageBack);
        }
        sendHandClient();

    }
    public void sendHandClient(){
        String temp = "cCards";
        socket.sendMessage("cCardStart");
        for (Card x: hand2D){
//            temp+="," + x.getCardName();
            socket.sendMessage("cCards"+ x.getCardName());
        }
        socket.sendMessage("sCardNum"+hand1D.size());

    }

    public List<Card> next(List<Card> temp){
        if(temp.equals(hand1D)&&direction == 0){
            return hand2D;

        }
        if(temp.equals(hand1D)&&direction == 1){
            return hand3D;

        }
        if(temp.equals(hand2D)&&direction == 0){
            return hand3D;

        }
        if(temp.equals(hand2D)&&direction == 1){
            return hand1D;

        }
        if(temp.equals(hand3D)&&direction == 0){
            return hand1D;

        }
        return hand2D;

    }

    @FXML
    private void checkOne(){
        checkCard(hand1D,0,next(hand2D));
    }
    @FXML
    private void checkTwo(){
        checkCard(hand1D,1,next(hand2D));
    }
    @FXML
    private void checkThree(){
        checkCard(hand1D,2,next(hand2D));
    }
    @FXML
    private void checkFour(){
        checkCard(hand1D,3,next(hand2D));
    }
    @FXML
    private void checkFive(){
        checkCard(hand1D,4,next(hand2D));
    }
    @FXML
    private void checkSix(){
        checkCard(hand1D,5,next(hand2D));
    }
    @FXML
    private void checkSeven(){
        checkCard(hand1D,6,next(hand2D));
    }
    @FXML
    private void checkEight(){
        checkCard(hand1D,7,next(hand2D));
    }
    @FXML
    private void checkNine(){
        checkCard(hand1D,8,next(hand2D));
    }
    @FXML
    private void checkTen(){
        checkCard(hand1D,9,next(hand2D));
    }

    @FXML
    private void computerSim(){
        if(turn == 2) {
            int index = checkCompIndex();
            if (index == -1) {
                if (turn == 2) {
                    hand3D.add(deck.get((int) (Math.random() * numCardsInDeck)));
                    numCardsInDeck--;
                    printServerScreen();
                    changeTurn(direction);
                    if(turn == 1) {
                        socket.sendMessage("serverDrawCard");
                    }
                    if(turn == 0){
                        changeButtons(false);
                    }


                }
            }
            else{
                checkCard(hand3D,index,next(hand3D));
            }
        }

    }
    public int checkCompIndex(){
        for(int i = 0; i<hand3D.size();i++){
            String name = hand3D.get(i).cName;
            String discard = discardPile.get(discardPile.size()-1).getCardName();
            if(name.substring(0,1).equals(discard.substring(0,1)) || name.substring(1,name.length()).equals(discard.substring(1,discard.length())) || name.charAt(0) == 'W')
                return i;
        }
        return -1;
    }



    public void sendDiscardPile(){

        socket.sendMessage("dis"+discardPile.get(discardPile.size()-1).getCardName());
    }
    public HelloController(){
        try {
            back1 = new FileInputStream("src/main/resources/Images/Back.png");
            imageBack = new Image(back1);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public void changeButtons(boolean change){
        button1.setDisable(change);
        button2.setDisable(change);
        button3.setDisable(change);
        button4.setDisable(change);
        button5.setDisable(change);
        button6.setDisable(change);
        button7.setDisable(change);
        button8.setDisable(change);
        button9.setDisable(change);
        button10.setDisable(change);
        draw.setDisable(change);
    }

    public void changeTurn(int d){
        if(d == 0 && turn < 2){
            turn ++;
        }else if(turn == 2 && d == 0){
            turn = 0;
        }
        if(d == 1 & turn > 0){
            turn--;
        }else if(turn == 0 && d == 1){
            turn = 2;
        }
    }


    @FXML
    private ImageView imgS0,imgS1,imgS2,imgS3,imgS4,imgS5,imgS6,imgS7,imgS8,imgS9,
    imgC0,imgC1,imgC2,imgC3,imgC4, imgC5,imgC6,imgC7,imgC8,imgC9,imgDiscard, imgB0,imgB1,imgB2,imgB3,imgB4, imgB5,imgB6,imgB7,imgB8,imgB9;

    @FXML
    Button deal,draw;
    FileInputStream back1,tempCard;
    Image imageBack;
    Image imageFront;
    List<Card> deck = new ArrayList<>();
    Card discard;
    List<ImageView> hand1I = new ArrayList<>();
    List<ImageView> hand2I = new ArrayList<>();
    List<ImageView> hand3I = new ArrayList<>();
    List<Card> hand1D = new ArrayList<>();
    List<Card> hand2D = new ArrayList<>();
    List<Card> hand3D = new ArrayList<>();
    List<Card> discardPile = new ArrayList<>();
    private int numCardsInDeck;
    private int turn = 0;
    int direction = 0;
    private String clientColor = "";
    private String serverColor = "";
}
