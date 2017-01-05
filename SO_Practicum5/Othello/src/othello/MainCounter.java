package othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainCounter extends Application{
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		stage.setTitle("MainCounter");
		
		GridPane root = new GridPane();
		
		Counter blackCounter = new Counter(-1);
		Counter whiteCounter = new Counter(1);
		Counter emptyCounter = new Counter(0);
		
		
		for(int i=0;i<3;i++){
			OthelloPiece othello = new OthelloPiece();
			othello.stateProperty().addListener(blackCounter);
			othello.stateProperty().addListener(whiteCounter);
			othello.stateProperty().addListener(emptyCounter);
			emptyCounter.increment();
			root.add(othello.getContent(), 0, i);
		}
		root.add(emptyCounter.getContent(), 1, 0);
		root.add(whiteCounter.getContent(), 1, 1);
		root.add(blackCounter.getContent(), 1, 2);
		
		stage.setScene(new Scene(root));
		stage.sizeToScene();
		stage.show();
	}
	
}
