package othello;

import java.io.Serializable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Counter implements Serializable, ChangeListener<Number>{
	
	private static final long serialVersionUID = 2L;
	private ObjectProperty<Integer> number = new SimpleObjectProperty<>();
	private ObjectProperty<Integer> stateToCount = new SimpleObjectProperty<>();
	private Pane pane;
	private Label numberLabel;
	
	public Counter(int state){
		setStateToCount(state);
		numberLabel = new Label();
		setNumber(0);
		
		pane = new AnchorPane();
		pane.setPrefSize(50, 50);
		pane.setStyle("-fx-border-color: black;");
		GridPane.setMargin(pane, new Insets(3.0));
		
		Label text;
		if(state < 0){
			text = new Label("Black");
		}else if(state > 0){
			text = new Label("White");
		}else{
			text = new Label("Empty");
		}
		text.setStyle("-fx-font-weight: bold;");
		
		AnchorPane.setTopAnchor(text,0.0);
		AnchorPane.setBottomAnchor(numberLabel,0.0);
		
		pane.getChildren().addAll(text,numberLabel);
	}
	
	public int getNumber(){
		return number.getValue();
	}
	
	public void setNumber(int value){
		number.set(value);
		numberLabel.setText(""+value);
	}
	
	public ObjectProperty<Integer> numberProperty(){
		return number;
	}
	
	public int getStateToCount(){
		return stateToCount.getValue();
	}
	
	public void setStateToCount(int value){
		stateToCount.set(value);
	}
	
	public ObjectProperty<Integer> stateToCountProperty(){
		return stateToCount;
	}
	
	public void increment(){
		setNumber(getNumber()+1);
	}
	
	public void decrement(){
		if(getNumber() > 0){
			setNumber(getNumber()-1);
		}
	}
	
	public Pane getContent(){
		return pane;
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> observable ,Number oldNumber, Number newNumber){
		if(oldNumber.intValue() == getStateToCount()){
			decrement();
		}
		if(newNumber.intValue() == getStateToCount()){
			increment();
		}
	}

}
