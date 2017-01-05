/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import chat.ChatPanel;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import network.Network;

/**
 *
 * @author maartendesnouck
 */
public class Main extends Application {
    
    Network network;
    
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
        
        eventbroker.EventBroker.getEventBroker().start();    
        network = new Network(port);   
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
        
        menu.getItems().add(connect);
        
        MenuItem file = new MenuItem("Choose File");
        
        file.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Stage stage = new Stage();
                FileChooser fileChooser = new FileChooser();
                
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Jar Files", "*.jar"));
                File selectedFile = fileChooser.showOpenDialog(stage);
                
                if (selectedFile != null){
                    try {
                        URLClassLoader loader = new URLClassLoader(new URL[]{selectedFile.toURI().toURL()});
                        
                        //get name
                        String className = selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf('.'));
                        
                        //load class
                        Class gameClass = loader.loadClass(className.toLowerCase()+"."+className);
                        
                        //create instance and invoke getGamePanel
                        Object game = gameClass.newInstance();
                        Method m = gameClass.getMethod("getGamePanel", new Class<?>[0]);
                        Pane pane = (Pane) m.invoke(game);
                        
                        //add to scene
                        borderPane.setCenter(pane);
                        primaryStage.sizeToScene();
                    } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException
                            | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        menu.getItems().add(file);
        
        menuBar.getMenus().add(menu);
        
        borderPane.setTop(menuBar);
        borderPane.setBottom(panel.getContent());
        
        primaryStage.setTitle("Chat");
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
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
