/*
 * @(#)LifePanel.java    1.0 01/08/16
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

import java.awt.*;
import javax.swing.*;

public class LifePanel extends JPanel{
	protected Life life;
	
	public LifePanel(Life life){
		this.life = life;
	}
	
	public void setLife(Life life){
		this.life = life;
		repaint();
	}
	
	public void paint(Graphics g){
		int[] size = this.life.getSize();
		int cellWidth = this.getWidth() / size[0];
		int cellHeight = this.getHeight() / size[1];
		boolean[][] grid = this.life.getGrid();
		g.setColor(Color.white);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(Color.black);
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				if(grid[i][j]){
					g.fillOval((cellWidth * i),(cellHeight * j),cellWidth, cellHeight);
				}
			}
		}
	}
}

