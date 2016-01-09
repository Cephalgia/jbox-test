package sample;

import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;


/**
 * Created by Someon on 08.01.2016.
 */
public class Brick {
    public Node node;
    private float posX;
    private float posY;
    private int width;
    private int height;
    private Color color;

    public Brick(float posX, float posY, int height, int width, Color color) {
        this.posY = posY;
        this.posX = posX;
        this.width = width;
        this.height = height;
        this.color = color;
        node = create();
    }

    public Node create(){
        Rectangle rec = new Rectangle();
        rec.setHeight(height);
        rec.setWidth(width);
        rec.setFill(color);

        rec.setLayoutX(posX - width);
        rec.setLayoutY(posY - height);

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(posX, posY);

        PolygonShape brick = new PolygonShape();
        brick.setAsBox(width,height);

        FixtureDef fd = new FixtureDef();
        fd.shape = brick;
        fd.density = 0.6f;
        fd.friction = 0.3f;
        fd.restitution = 0.2f;

        Body body = Utils.world.createBody(bd);
        body.createFixture(fd);
        rec.setUserData(body);

        return rec;
    }



}
