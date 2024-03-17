package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Particle {
    private Vector2 pos, vel;
    private Color color;

    public Particle(int screenWidth, int screenHeight) {
        Random rand = new Random();
        pos = new Vector2(rand.nextInt(screenWidth), rand.nextInt(screenHeight));
        vel = new Vector2((rand.nextInt(200) - 100) / 100f, (rand.nextInt(200) - 100) / 100f);
        color = Color.BLACK;
    }

    public Particle(Vector2 pos, Vector2 vel, Color color) {
        this.pos = pos;
        this.vel = vel;
        this.color = color;
    }

    private float getDist(Vector2 otherPos) {
        final Vector2 d = new Vector2(pos.x - otherPos.x, pos.y - otherPos.y);
        return (float) Math.sqrt((d.x*d.x) + (d.y*d.y));
    }

    private Vector2 getNormal(Vector2 otherPos) {
        float dist = getDist(otherPos);
        if (dist == 0f) dist = 1f;
        final Vector2 d = new Vector2(pos.x - otherPos.x, pos.y - otherPos.y);
        Vector2 normal = new Vector2(d.x * (1 / dist), d.y * (1 / dist));
        return normal;
    }

    public void attract(Vector2 posToAttract, float multiplier) {
        float dist = Math.max(getDist(posToAttract), 0.5f);
        Vector2 normal = getNormal(posToAttract);

        vel.x -= normal.x / dist;
        vel.y -= normal.y / dist;
    }

    public void doFriction(float amount) {
        vel.scl(amount);
    }

    public void move(int screenWidth, int screenHeight) {
        pos.add(vel);
        if (pos.x < 0)
            pos.x += screenWidth;
        if (pos.x > screenWidth)
            pos.x -= screenWidth;
        if (pos.y < 0)
            pos.y += screenHeight;
        if (pos.y > screenHeight)
            pos.y -= screenHeight;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(pos.x, pos.y, 1);
        shapeRenderer.end();
    }
}
