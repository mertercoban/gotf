package entities;

import main.Game;
import util.AssetManager;
import util.Directions;

import java.awt.*;
import java.awt.image.BufferedImage;

import static util.Directions.*;

public class Player extends Entity {

    private boolean moveUp, moveDown, moveRight, moveLeft;
    private int playerSpeed = 2;
    private Rectangle attackHitbox;
    private boolean attacking;
    private Directions direction;
    private BufferedImage[][] sprite;
    private int animIndex, animTick;


    public Player(Rectangle hitbox) {
        this.hitbox = hitbox;
        this.attackHitbox = new Rectangle(hitbox.x + hitbox.width, hitbox.y, hitbox.width, hitbox.height);
        this.direction = RIGHT;
        loadSprite();
    }


    public void update() {
        animate();
        move();
        updateAttackHitbox();
    }

    private void animate() {
        animTick++;
        if (animTick >= 50) {
            animTick = 0;
            animIndex++;
            if (animIndex>=4)
                animIndex = 0;
        }
    }

    private void move() {
        int xSpeed = 0, ySpeed = 0;
        if (moveUp)
            ySpeed -= playerSpeed;
        if (moveDown)
            ySpeed += playerSpeed;
        if (moveLeft)
            xSpeed -= playerSpeed;
        if (moveRight)
            xSpeed += playerSpeed;

        hitbox.x += xSpeed;
        hitbox.y += ySpeed;
    }

    private void updateAttackHitbox() {
        switch (direction) {
            case UP -> {
                attackHitbox.y = hitbox.y - hitbox.height;
                attackHitbox.x = hitbox.x;
            }
            case DOWN -> {
                attackHitbox.y = hitbox.y + hitbox.height;
                attackHitbox.x = hitbox.x;
            }
            case RIGHT -> {
                attackHitbox.y = hitbox.y;
                attackHitbox.x = hitbox.x + hitbox.width;
            }
            case LEFT -> {
                attackHitbox.y = hitbox.y;
                attackHitbox.x = hitbox.x - hitbox.width;
            }
        }
    }

    public void draw(Graphics g) {
        drawHitbox(g);
        switch (direction) {
            case DOWN -> {
                if (attacking)
                    g.drawImage(sprite[4][0], hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
                else
                    g.drawImage(sprite[0][animIndex], hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
            }
            case UP -> {
                if (attacking)
                    g.drawImage(sprite[4][1], hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
                else
                    g.drawImage(sprite[1][animIndex], hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
            }
            case LEFT -> {
                if (attacking)
                    g.drawImage(sprite[4][2], hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
                else
                    g.drawImage(sprite[2][animIndex], hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
            }
            case RIGHT -> {
                if (attacking)
                    g.drawImage(sprite[4][3], hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
                else
                    g.drawImage(sprite[3][animIndex], hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
            }
        }
    }

    private void loadSprite() {
        BufferedImage temp = AssetManager.getSprite(AssetManager.PLAYER_SPRITE);
        sprite = new BufferedImage[8][4];
        for (int i = 0; i < 4; i++) {
            sprite[0][i] = temp.getSubimage(0, i * 16, 16, 16); // down
            sprite[1][i] = temp.getSubimage(16, i * 16, 16, 16); // up
            sprite[2][i] = temp.getSubimage(32, i * 16, 16, 16); // left
            sprite[3][i] = temp.getSubimage(48, i * 16, 16, 16); // right
        }
        sprite[4][0] = temp.getSubimage(0,64,16,16); // attack down
        sprite[4][1] = temp.getSubimage(16,64,16,16); // attack up
        sprite[4][2] = temp.getSubimage(32,64,16,16); // attack left
        sprite[4][3] = temp.getSubimage(48,64,16,16); // attack right
    }

    private void drawHitbox(Graphics g) {
        if (Game.DEBUG_MODE) {
            g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            if (attacking)
                g.setColor(Color.RED);
            else
                g.setColor(Color.GREEN);
            g.drawRect(attackHitbox.x, attackHitbox.y, attackHitbox.width, attackHitbox.height);
        }
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
        direction = UP;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
        direction = DOWN;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
        direction = RIGHT;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
        direction = LEFT;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
