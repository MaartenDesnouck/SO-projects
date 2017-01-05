/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import chat.ChatPanel;
import game.GameManager;
import game.exceptions.GameLoadException;
import game.exceptions.NoGameLoadedException;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import network.Network;

/**
 *
 * @author maartendesnouck
 */
public class Main extends Application {
    
    Network network;
    GameManager manager;
    
    @Override
    public void start(Stage primaryStage){
        
        primaryStage.setTitle("Log In");
        
        GridPane pane = new GridPane();
        pane.add(new Label("Username:"),0,0);
        pane.add(new Label("Port:"),0,1);
        TextField username = new TextField();
        TextField port = new TextField();
        pane.add(username, 1, 0);
        pane.add(port, 1, 1);
        Button login = new Button("Log in");
        pane.add(login, 0, 2);
        
        login.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(checkGeldig(port.getText())){
                    login.disarm();
                    loggedIn(primaryStage, Integer.parseInt(port.getText()), username.getText());
                }else{
                    username.clear();
                    port.clear();
                }
            }
    });
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean checkGeldig(String tekst){
        return !(tekst.length()==0 || !tekst.matches("[0-9]+") || Integer.parseInt(tekst) >= 65536);
    }
    
    public void loggedIn(Stage primaryStage, int port, String username){
        
        event.EventBroker.getEventBroker().start();
        
        network = new Network(port); 
        manager = new GameManager();
        
        ChatPanel panel = ChatPanel.createChatPanel();
        panel.getChatModel().setName(username);
        
        BorderPane borderPane = new BorderPane();
        
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem connect = new MenuItem("Connect");
        
        connect.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage stage = new Stage();
                stage.setTitle("Connect to other instance");
                
                VBox root = new VBox();
                root.getChildren().add(new Label("Enter IP address and port number"));
                
                TextField addressField = new TextField();
                root.getChildren().add(addressField);
                
                Button ok = new Button("OK");
                ok.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e){
                        connect(addressField.getText());
                        addressField.clear();
                        stage.close();
                    }
                });
                root.getChildren().add(ok);
                
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
        
        MenuItem file = new MenuItem("Load Game");
        
        file.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Stage stage = new Stage();
                FileChooser fileChooser = new FileChooser();
                
                fileChooser.setTitle("Open Game File");
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Jar Files", "*.jar"));
                File selectedFile = fileChooser.showOpenDialog(stage);
                
                try {
                    manager.loadGame(selectedFile);
                    game.GameInterface game = manager.getLoadedGame();
                    borderPane.setCenter(game.getGamePanel());
                    primaryStage.sizeToScene();
                } catch (GameLoadException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        MenuItem start = new MenuItem("Start Game");
        
        start.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                try {
                    manager.sendInvitation();
                } catch (NoGameLoadedException ex) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("No game loaded!");
                    error.setContentText(null);
                    error.showAndWait();
                }
            }
        });
        
        menu.getItems().addAll(connect,file,start);
        
        menuBar.getMenus().add(menu);
        
        borderPane.setTop(menuBar);
        borderPane.setBottom(panel.getContent());
        
        primaryStage.setTitle("Chat");
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent we){
                event.EventBroker.getEventBroker().stop();
                network.terminate();
            }
        });
        
        primaryStage.show();
    }
    
    public void connect(String address){
        String[] parts = address.split(":");
        try {
            network.connect(InetAddress.getByName(parts[0]),Integer.parseInt(parts[1]));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
