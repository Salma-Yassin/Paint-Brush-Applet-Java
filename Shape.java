import java.awt.Color;
import java.awt.*; 

abstract class Shape{
	
	
	protected int x1;
	protected int x2;
	protected int y1;
	protected int y2;
	protected Color color;
	protected boolean solid;
	protected boolean dotted;
	
	
	
	public Shape(){
		// default constructor
	}
	public Shape(int x1, int y1, int x2, int y2, Color color, boolean solid, boolean dotted)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
		this.solid = solid;
		this.dotted = dotted;
	}
	
	// Abstract function to force implementation in children classes 
	public abstract void draw(Graphics g, Graphics2D g2d);
}

class Rectangle extends Shape{
	public Rectangle(int x1, int y1, int x2, int y2, Color color, boolean solid, boolean dotted)
	{
		super(x1, y1, x2, y2, color, solid, dotted);	
	}
	public void draw(Graphics g, Graphics2D g2d){
		// Call the draw 
		g.setColor(color);
		g2d.setColor(color);
		if(solid)
			g.fillRect(x1,y1,x2,y2);
		else 
			if(!dotted)
				g.drawRect(x1,y1,x2,y2);
			else
				g2d.drawRect(x1,y1,x2,y2);
			
				
	}
}

class Line extends Shape{
	
	public Line(int x1, int y1, int x2, int y2, Color color, boolean solid, boolean dotted)
	{
		super(x1, y1, x2, y2, color, solid, dotted);	
	}
	
	public void draw(Graphics g, Graphics2D g2d){
		// Call the draw 
		g.setColor(color);
		g2d.setColor(color);
		if(!dotted)
			g.drawLine(x1,y1,x2,y2);
		else
			g2d.drawLine(x1,y1,x2,y2);
		
	}
}

class Oval extends Shape{
	
	public Oval(int x1, int y1, int x2, int y2, Color color, boolean solid, boolean dotted)
	{
		super(x1, y1, x2, y2, color, solid, dotted);	
	}
	
	public void draw(Graphics g, Graphics2D g2d){
		// Call the draw 
		g.setColor(color);
		g2d.setColor(color);
		if(solid)
			g.fillOval(x1,y1,x2,y2);
		else 
			if(!dotted)
				g.drawOval(x1,y1,x2,y2);
			else
				g2d.drawOval(x1,y1,x2,y2);
			
	}
}



