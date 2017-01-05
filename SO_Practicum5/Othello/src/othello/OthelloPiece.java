package othello;

import java.io.Serializable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class OthelloPiece implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private ObjectProperty<Integer> state = new SimpleObjectProperty<>();
	private Pane piece;
	
	public OthelloPiece(){
		piece = new Pane();
		
		piece.setPrefSize(50, 50);
		piece.setStyle("-fx-background-color: grey;");
		GridPane.setMargin(piece, new Insets(3.0));
		
		Circle circle = new Circle(25.00,25.00,25.00);
		circle.setFill(Color.TRANSPARENT);
		circle.setStrokeType(StrokeType.INSIDE);
		piece.getChildren().add(circle);
		
		piece.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				int st = getState();
				setState(((st+2)%3)-1);
			}
		});
		
		setState(0);
	}
	
	public int getState(){
		return state.getValue();
	}
	
	public void setState(int value){
		state.set(value);
		if(value < 0){
			((Circle) piece.getChildren().get(0)).setFill(Color.BLACK);
		}else if(value > 0){
			((Circle) piece.getChildren().get(0)).setFill(Color.WHITE);
			((Circle) piece.getChildren().get(0)).setStroke(Color.BLACK);
		}else{
			((Circle) piece.getChildren().get(0)).setFill(Color.TRANSPARENT);
			((Circle) piece.getChildren().get(0)).setStroke(Color.TRANSPARENT);
		}
	}
	
	public ObjectProperty<Integer> stateProperty(){
		return state;
	}
	
	public Pane getContent(){
		return piece;
	}
}
