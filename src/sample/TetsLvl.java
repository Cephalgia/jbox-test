package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.jbox2d.dynamics.Body;

/**
 * Created by Someon on 07.01.2016.
 */
public class TetsLvl extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        final Group root = new Group();
        primaryStage.setTitle("Test Lvl");

        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);

        final Brick brick = new Brick(50,60,20,25, Color.ORANGE);

        final Ball [] ball = new Ball[50];

        for (int i = 0; i < ball.length; i++) {
            ball[i] = new Ball(4 * i,90,10,Color.BLACK);
            root.getChildren().add(ball[i].node);
        }

        root.getChildren().add(brick.node);


        Utils.addGround(100, 10);
        Utils.addWall(0,100,1,100); //Left wall
        Utils.addWall(99,100,1,100); //Right wall
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0/60.0);

        EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

                Utils.world.step(1.0f/60.f, 8, 3);

                final Body body = (Body)brick.node.getUserData();
                float xpos = Utils.toPixelPosX(body.getPosition().x);
                float ypos = Utils.toPixelPosY(body.getPosition().y);
                brick.node.setLayoutX(xpos);
                brick.node.setLayoutY(ypos);


                for (int i = 0; i < ball.length; i++) {
                    final Body bodyBall = (Body)ball[i].node.getUserData();
                    float xposBall = Utils.toPixelPosX(bodyBall.getPosition().x);
                    float yposBall = Utils.toPixelPosY(bodyBall.getPosition().y);
                    ball[i].node.setLayoutX(xposBall);
                    ball[i].node.setLayoutY(yposBall);
                }




            }
        };


        KeyFrame frame = new KeyFrame(duration, ae, null,null);
        timeline.getKeyFrames().add(frame);





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
