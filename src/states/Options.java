package states;

import java.awt.*;

public class Options extends State {
    @Override
    public void draw(Graphics g) {
        g.drawString("Options",100,100);
    }

    @Override
    public void update() {

    }
}
