package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.*;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.util.Duration;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


import java.security.Principal;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.ListIterator;

public class TestMove extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        final Group root = new Group();

        primaryStage.setTitle("Hello World");
        final Button button = new Button("Push me");
        button.setLayoutX(200);
        button.setLayoutY(50);


        root.getChildren().add(button);


        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);


        final Ball ball = new Ball(50, 90, 50, Color.RED);

        //Add ground to the application, this is where balls will land
        Utils.addGround(100, 10);


        //Add left and right walls so balls will not move outside the viewing area.
        Utils.addWall(50,100,100,1);
        Utils.addWall(0,100,1,100); //Left wall
        Utils.addWall(99,100,1,100); //Right wall
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.

        //Create an ActionEvent, on trigger it executes a world time step and moves the balls to new position
        EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //Create time step. Set Iteration count 8 for velocity and 3 for positions
                Utils.world.step(1.0f/60.f, 8, 3);

                //Move balls to the new position computed by JBox2D
                final Body body = (Body)ball.node.getUserData();
//                if(ball.node.getLayoutY() < 50){
//                    Vec2 force  = new Vec2(0, -999999999.0f);
//                    Vec2 point = body.getWorldPoint(body.getWorldCenter());
//                    body.applyForce(force ,point);
//                }


                float xpos = Utils.toPixelPosX(body.getPosition().x);
                float ypos = Utils.toPixelPosY(body.getPosition().y);
                ball.node.setLayoutX(xpos);
                ball.node.setLayoutY(ypos);




                final EventHandler<javafx.scene.input.KeyEvent> keyEventHandler =
                        new EventHandler<javafx.scene.input.KeyEvent>() {
                            @Override
                            public void handle(javafx.scene.input.KeyEvent event) {
                               if(event.getCode() == KeyCode.UP){

                                   Vec2 force  = new Vec2(0, 10.0f);
                                   Vec2 point = body.getWorldPoint(body.getWorldCenter());
                                   body. applyLinearImpulse(force, point, true);
                               }
                               if(event.getCode() == KeyCode.LEFT){
                                   Vec2 force  = new Vec2(-10,0.0f);
                                   Vec2 point = body.getWorldPoint(body.getWorldCenter());
                                   body.applyForce(force ,point);
                               }
                                if(event.getCode() == KeyCode.RIGHT){
                                    Vec2 force  = new Vec2(10,0.0f);
                                    Vec2 point = body.getWorldPoint(body.getWorldCenter());
                                    body.applyForce(force ,point);
                                }
                                if(event.getCode() == KeyCode.DOWN){
                                    Vec2 force  = new Vec2(0,-10.0f);
                                    Vec2 point = body.getWorldPoint(body.getWorldCenter());
                                    body.applyForce(force ,point);
                                }
                            }
                        };
                root.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED,keyEventHandler);
            }
        };






        /**
         * Set ActionEvent and duration to the KeyFrame.
         * The ActionEvent is trigged when KeyFrame execution is over.
         */
        KeyFrame frame = new KeyFrame(duration, ae, null,null);
        timeline.getKeyFrames().add(frame);
        root.getChildren().add(ball.node);
        root.getChildren().add(new Circle(0,0,5,Color.BLACK));

        Rectangle rec = new Rectangle(0,0,600,50);
        Rectangle rec2 = new Rectangle(600,0,600,50);
        rec.setFill(Color.AQUA);
        rec2.setFill(Color.RED);
//        root.getChildren().add(rec);
//        root.getChildren().add(rec2);



        primaryStage.setScene(new Scene(root, 600, 600));

        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN,  new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                timeline.play();
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
