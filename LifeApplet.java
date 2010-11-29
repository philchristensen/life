/*
 * @(#)LifeApplet.java    1.0 01/08/16
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LifeApplet extends JApplet implements Runnable, ActionListener{
	protected Life life;
	protected LifePanel lifePanel;
	protected ControlPanel controlPanel;
	
	protected Thread thread;
	protected boolean running = true;
	protected int delay = 1000;
	protected int population = 3000;
	protected int width = 100;
	protected int height = 100;
	
	public LifeApplet(){
		super();
		this.life = new Life(width,height);
		this.life.populate(population);
		this.lifePanel = new LifePanel(life);
		this.controlPanel = new ControlPanel();
		this.getContentPane().setLayout(new BorderLayout());
		//this.getContentPane().add(controlPanel, BorderLayout.NORTH);
		this.getContentPane().add(lifePanel);
		
		controlPanel.jbGo.addActionListener(this);
	}
	
	public void init(){
		thread = new Thread(this);
	}
	
	public void destroy(){
		thread = null;
		life = null;
	}
	
	public void start(){
		thread.start();
	}
	
	public void stop(){
		running = false;
	}

	public void run(){
		while(running){
			life.iterate();
			lifePanel.repaint();
			
			try{
				Thread.sleep(delay);
			}
			catch(InterruptedException e){
				//nothing
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		stop();
		try{
			thread.join();
		}
		catch(InterruptedException ex){
		}
		this.delay = controlPanel.getDelay();
		this.population = controlPanel.getPopulation();
		this.height = controlPanel.getHeight();
		this.width = controlPanel.getWidth();
		start();
	}
	
	public class ControlPanel extends JPanel{
		protected JLabel jlDelay = new JLabel("Delay: ");
		protected JLabel jlPopulation = new JLabel("Population: ");
		protected JLabel jlWidth = new JLabel("Width: ");
		protected JLabel jlHeight = new JLabel("Height: ");
		
		protected JTextField jtfDelay = new JTextField("1000", 6);
		protected JTextField jtfPopulation = new JTextField("2000", 6);
		protected JTextField jtfWidth = new JTextField("100", 6);
		protected JTextField jtfHeight = new JTextField("100", 6);
		
		protected JButton jbGo = new JButton("Go");
		
		public ControlPanel(){
			this.add(jlDelay);
			this.add(jtfDelay);
			this.add(jlPopulation);
			this.add(jtfDelay);
			this.add(jlWidth);
			this.add(jtfWidth);
			this.add(jlHeight);
			this.add(jtfHeight);
			this.add(jbGo);
		}
		
		public int getDelay(){
			try{
				return Integer.parseInt(jtfDelay.getText());
			}
			catch(Exception e){
				jtfDelay.setText("1000");
				return 1000;
			}
		}

		public int getPopulation(){
			try{
				return Integer.parseInt(jtfPopulation.getText());
			}
			catch(Exception e){
				jtfPopulation.setText("2000");
				return 2000;
			}
		}

		public int getWidth(){
			try{
				return Integer.parseInt(jtfWidth.getText());
			}
			catch(Exception e){
				jtfWidth.setText("100");
				return 100;
			}
		}

		public int getHeight(){
			try{
				return Integer.parseInt(jtfHeight.getText());
			}
			catch(Exception e){
				jtfHeight.setText("100");
				return 100;
			}
		}
	}
}
