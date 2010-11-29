/*
 * @(#)LifeMIDlet.java  1.0 01/08/16
 * 
 * Game of Life
 * Copyright (C) 2004 Phil Christensen
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of version 2 of the GNU General Public
 * License as published by the Free Software Foundation.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;

public class LifeMIDlet extends MIDlet implements Runnable{
    protected Display display;
    protected Life life = new Life(50,50);
    protected LifeCanvas lifeCanvas = new LifeCanvas(life);
    protected boolean running = true;
    protected Thread thread;
    //protected Timer timer = new Timer();

    public LifeMIDlet(){
        display = Display.getDisplay(this);
        life.populate(800);
        thread = new Thread(this);
    }

    protected void destroyApp(boolean unconditional){}

    protected void startApp(){
        running = true;
        display.setCurrent(lifeCanvas);
        thread.start();
        //timer.schedule(new LifeIterator(), 100, 100);
    }

    protected void pauseApp(){}

    public void exit(){
        //timer.cancel();
        running = false;
        destroyApp(true);
        notifyDestroyed();
    }

    public void run(){
        while(running){
            life.iterate();
            lifeCanvas.repaint();
            try{
                Thread.sleep(100);
            }
            catch(InterruptedException e){
                //do nothing
            }
        }
    }

    public static class LifeCanvas extends Canvas{
        protected Life life;
        
        public LifeCanvas(Life life){
            this.life = life;
        }
    
        public void paint(Graphics g){
            int[] size = this.life.getSize();
            int cellWidth = this.getWidth() / size[0];
            int cellHeight = this.getHeight() / size[1];
            boolean[][] grid = this.life.getGrid();
            g.setColor(255,255,255);
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            g.setColor(0,0,0);
            for(int i = 0; i < grid.length; i++){
                for(int j = 0; j < grid[i].length; j++){
                    if(grid[i][j]){
                        g.fillRect((cellWidth * i),(cellHeight * j),
                        cellWidth, cellHeight);
                    }
                }
            }
        }
    }
}
