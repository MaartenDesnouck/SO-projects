package othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainOthelloPiece extends Application{
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		stage.setTitle("MainOthelloPiece");
		
		GridPane root = new GridPane();
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				root.add((new OthelloPiece()).getContent(), i, j);
			}
		}
		
		stage.setScene(new Scene(root));
		stage.sizeToScene();
		stage.show();
	}
	
}
