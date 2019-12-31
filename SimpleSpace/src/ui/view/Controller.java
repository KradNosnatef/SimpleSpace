package ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.Timer;
import java.util.TimerTask;

import core.spaceSimple.*;

public class Controller {
	int pause=1;
	int paint=0;
	CenterEntity earth;
	Entity ship1;
	String centerEntityInfoText,infoText;

	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void onButtonTimeShift1XClick(){
		Phase.playspeed=1;
	}
	@FXML
	private void onButtonTimeShift5XClick(){
		Phase.playspeed=5;
	}
	@FXML
	private void onButtonTimeShift10XClick(){	
		Phase.playspeed=10;
	}
	@FXML
	private void onButtonTimeShift0_1XClick(){	
		Phase.playspeed=0.1;
	}
	@FXML
	private void onButtonTimeShiftPauseClick() {
		if(pause==1)pause=0;
		else pause=1;
	}
	@FXML
	private Circle ShipPosition1;
	@FXML
	private Ellipse Orbit1;
	@FXML
	private Circle ShipPosition2;
	@FXML
	private Ellipse Orbit2;
	
	@FXML
	private void onButtonTryClick() {
		ship1.maneuver.radialAcceleration(Double.valueOf(accelerationPercent.getText())*0.01);
		refreshCenterEntityInfoTextArea();
	}
	@FXML
	private Label Evade;

	@FXML
	private TextField centerEntityMassGTextField;

	@FXML
	private TextArea infoTextArea;
	
	@FXML
	private TextField shipEntitySpeedTextField;

	@FXML
	private TextField shipEntityAPPETextField;
	
	@FXML
	private TextField accelerationPercent;
	
	@FXML
	private void onButtonCenterEntityCheckClick() {
		//System.out.println(centerEntityMassGTextField.getText());
		earth.setMassG(Double.valueOf(centerEntityMassGTextField.getText()));
		ship1.setInitialState(Double.valueOf(shipEntitySpeedTextField.getText()),90, Double.valueOf(shipEntityAPPETextField.getText()),0);
		paint=1;
		refreshCenterEntityInfoTextArea();
		shipEntitySpeedTextField.setText("");
		shipEntityAPPETextField.setText("");
	}
	
	private void refreshCenterEntityInfoTextArea(){
		centerEntityInfoText=String.valueOf(earth.getMassG());
		refreshInfoTextArea();
	}
	
	private void refreshInfoTextArea(){
		infoText="Center entity Mass*G=\n\t"+centerEntityInfoText+"\n";
		infoText+="Ship AP=\n\t"+String.valueOf(ship1.getAP())+"\n";
		infoText+="Ship PE=\n\t"+String.valueOf(ship1.getPE())+"\n";
		infoTextArea.setText(infoText);
		refreshOrbit();
	}
	
	private void refreshOrbit() {
		if(ship1.getPE()<0)Evade.setText("Evade!");
		else{
			Orbit1.setRadiusX(5*ship1.getSemimajorAxis());
			//System.out.println("radiusx="+5*ship1.getSemimajorAxis());
			Orbit1.setRadiusY(5*ship1.getSemiminorAxis());
			Orbit1.setCenterX(5*ship1.getSemifocallength()*Math.cos(Math.toRadians(ship1.rotate)));
			Orbit1.setCenterY(-5*ship1.getSemifocallength()*Math.sin(Math.toRadians(ship1.rotate)));
			Orbit1.setRotate(-ship1.rotate);
			//the fxml Rotate's positive direction is not CounterClockWise,which is different from the usually usage of Polar corrdinates!
			//use Negative number!
			ship1.orbitCalculator.refreshArray();
			refreshShipPosition();
			Evade.setText("");
		}
	}

	
	public void refreshShipPosition() {
		int angle;
		double length;
		angle=ship1.orbitCalculator.getAngle();
		System.out.println(angle);
		length=ship1.getSemiminorAxis()*ship1.getSemiminorAxis()/(ship1.getSemimajorAxis()-ship1.getSemifocallength()*Math.cos(Math.toRadians(Double.valueOf(angle))));
		System.out.println(angle+","+length);
		ShipPosition1.setCenterX(5*Math.cos(Math.toRadians(Double.valueOf(angle+ship1.rotate)))*length);
		ShipPosition1.setCenterY(-5*Math.sin(Math.toRadians(Double.valueOf(angle+ship1.rotate)))*length);
		//the fxml CenterY's positive direction is the Down Direction so that use Negative number!
	}
	
	public void setAppCore(PB16120162 appCore) {
		earth=new CenterEntity();
		ship1=new Entity(earth);
		ship1.orbitCalculator=new OrbitCalculator(ship1);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
		        public void run() {
		            if(pause==0) {
		            	System.out.println("TimerUP!");
		            	Phase.val=Phase.val+Phase.playspeed;
		            	ship1.phase.optimization();
		            	if(paint==1)refreshShipPosition();
		            }
		        }
		}, 0 , 40);
	}
}
