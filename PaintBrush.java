import java.applet.Applet;
import java.util.ArrayList;
import java.awt.*; 
import java.awt.event.*; 


public class PaintBrush extends Applet{
	
	//***************Initializations***************
	//Modes
	public final static int DRAW_RECTANGLE = 1;	
	public final static int DRAW_OVAL = 2;	
	public final static int DRAW_LINE = 3;	
	public final static int PENCILE = 4;
	public final static int ERASER = 5;
	
	// Drawing Specs 
	Color current_color;
	int mode;
	boolean state;
	boolean dotted;
	boolean mousemove;
	
	// Drawn shapes list
	ArrayList<Shape> shapes;
	ArrayList<Shape> reDoShapes;
	//Buttons 
	Button drawRectangle;
	Button drawOval;
	Button drawLine;
	Button drawFree;
	Button Erase;
	Button ClearAll;
	
	Button Redo;
	Button Undo;
	
	Button setColorRed;
	Button setColorGreen;
	Button setColorBlue;
	Checkbox  checkState;
	Checkbox  checkDotted;
	
	Stroke dashed; 
	
	Dimension bufferDimension;
	Image image;
	Graphics buffer;
	
	//draw variables
	int x1,x2,y1,y2;
	boolean start; // Don't start drawing unless dragged 
	
	public void init(){
		current_color = Color.BLUE;
		start = false;
		state = false;
		dotted = false;
		mousemove = false;
		shapes = new ArrayList<Shape>();
		reDoShapes = new ArrayList<Shape>();
		
		dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0, new float[]{9}, 0);
		
		drawRectangle = new Button("Rect");
		drawRectangle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = DRAW_RECTANGLE;
				start = false;
			}
		});
		add(drawRectangle);// add button to applet
		
		drawOval = new Button("Oval");
		drawOval.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = DRAW_OVAL;
			}
		});
		add(drawOval);// add button to applet
		
		drawLine = new Button("Line");
		drawLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = DRAW_LINE;
			}
		});
		add(drawLine);// add button to applet

        drawFree = new Button("Pencile");
		drawFree.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = PENCILE;
			}
		});
		add(drawFree);// add button to applet
		
		Erase = new Button("Erase");
		Erase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = ERASER;
			}
		});
		add(Erase);// add button to applet
		
		ClearAll = new Button("Clear All");
		ClearAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				shapes.clear(); // clear all lists 
				reDoShapes.clear();
				start = false;
				repaint();
			}
		});
		add(ClearAll);// add button to applet
		
		Undo = new Button("Undo");
		Undo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(shapes.size()> 0)
					reDoShapes.add(shapes.remove(shapes.size() - 1));
				start = false;
				repaint();
			}
		});
		add(Undo);// add button to applet
		
		
		Redo = new Button("Redo");
		Redo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(reDoShapes.size()> 0)
					shapes.add(reDoShapes.remove(reDoShapes.size() - 1));
				start = false;
				repaint();
				
			}
		});
		add(Redo);// add button to applet
		
		setColorRed = new Button("RED");	
		setColorRed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				current_color = Color.RED;
			}
		});
		setColorRed.setBackground(Color.RED);
        add(setColorRed);// add button to applet

		setColorGreen = new Button("GREEN");	
		setColorGreen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				current_color = Color.GREEN;
			}
		});
		setColorGreen.setBackground(Color.GREEN);
        add(setColorGreen);// add button to applet

		setColorBlue = new Button("BLUE");	
		setColorBlue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				current_color = Color.BLUE;
			}
		});
		setColorBlue.setBackground(Color.BLUE);
        add(setColorBlue);// add button to applet			
		
		Checkbox checkState = new Checkbox("Filled");
		checkState.addItemListener(new ItemListener(){
			//anonymous inner class overriding ---> need to modify the state variable which is a private variable to PaintBrush class 
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange()== ItemEvent.SELECTED)
					state = true;
				else
					state = false;
			}
			
		});
		add(checkState);// add checkbox to applet
		
		
		Checkbox checkDotted = new Checkbox("Dotted");
		checkDotted.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange()== ItemEvent.SELECTED)
					dotted = true;
				else
					dotted = false;
			}
			
		});
		add(checkDotted);// add checkbox to applet
		
		// Add action listner to click
		this.addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent ev){
					start = false;
					x1 = ev.getX();
					y1 = ev.getY();
					repaint();
				}
				
			});		
		// Add action listner to drag
		this.addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseDragged(MouseEvent ev){
					start = true;
					x2 = ev.getX();
					y2 = ev.getY(); 
					mousemove = false;
					
					if(mode == PENCILE && System.currentTimeMillis() % 5 == 0) // to smoothen the drawing 
					{
						shapes.add(new Line(x1,y1,x2,y2,current_color,state, dotted ));
						x1=x2;
						y1=y2;
					}
					else if(mode == ERASER)
					{
						shapes.add(new Rectangle(x1,y1,10,30,Color.WHITE,state, false));
						x1=x2;
						y1=y2;
						
					}
					
					repaint();
						
				}
				
			});
		// Add action listner to release
		this.addMouseListener(new MouseAdapter(){
				public void mouseReleased(MouseEvent ev){
					// add shape to the array list
					if (start){
						switch(mode){
							case DRAW_RECTANGLE:
								int sx = Math.min(x1,x2);
								int sy = Math.min(y1,y2);
								shapes.add(new Rectangle(sx,sy,Math.abs((x2-x1)),Math.abs((y2-y1)),current_color,state,dotted));
								break;
							case DRAW_OVAL:
								shapes.add(new Oval(Math.min(x1,x2),Math.min(y1,y2),Math.abs((x2-x1)),Math.abs((y2-y1)),current_color,state, dotted));
								break;
							case DRAW_LINE:
								shapes.add(new Line(x1,y1,x2,y2,current_color,state, dotted));
								break;
						}
					}
					start = false;
					repaint();	
				}
				
			});
			
			
		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e)
			{  if(!start)
				{
					x1 = e.getX();
					y1 = e.getY();
					mousemove = true;
					repaint();
				}
				
			}	
		});


		
	}
	
	public void paint(Graphics g){
		update(g);
	}
	
	public void update(Graphics g)
	{
		Dimension d = getSize();
		
		if((buffer == null)|| (d.width != bufferDimension.width)|| (d.height != bufferDimension.height)) 
		{
			bufferDimension = d;
            image = createImage(d.width, d.height);
            buffer = image.getGraphics();
        }
			
		buffer.setColor(getBackground());
        buffer.fillRect(0, 0, d.width, d.height);
		Graphics2D g2d = (Graphics2D) buffer.create();
		g2d.setStroke(dashed);
		
		for(int i = 0; i < shapes.size(); i++)
		{
			shapes.get(i).draw(buffer , g2d );
		}
		
        buffer.setColor(current_color);
		g2d.setColor(current_color);
		
		
		
		
		if(start)
		{ 
			switch(mode){
					case DRAW_RECTANGLE:
					    int sx = Math.min(x1,x2);
						int sy = Math.min(y1,y2);
						if(state)
							buffer.fillRect(sx,sy,Math.abs((x2-x1)),Math.abs((y2-y1)));
						else
							if (!dotted)
								buffer.drawRect(sx,sy,Math.abs((x2-x1)),Math.abs((y2-y1)));
							else
								g2d.drawRect(sx,sy,Math.abs((x2-x1)),Math.abs((y2-y1)));
					
						break;
					case DRAW_OVAL:
						if(state)
							buffer.fillOval(Math.min(x1,x2),Math.min(y1,y2),Math.abs((x2-x1)),Math.abs((y2-y1)));
						else 
							if (!dotted)
								buffer.drawOval(Math.min(x1,x2),Math.min(y1,y2),Math.abs((x2-x1)),Math.abs((y2-y1)));
							else
								g2d.drawOval(Math.min(x1,x2),Math.min(y1,y2),Math.abs((x2-x1)),Math.abs((y2-y1)));
							
						break;
					case DRAW_LINE:
							if (!dotted)
								buffer.drawLine(x1,y1,x2,y2);
							else
								g2d.drawLine(x1,y1,x2,y2);
						
						break;
					case PENCILE:
						buffer.drawLine(x1,y1,x2,y2);
						break;
					case ERASER:
						
						buffer.setColor(Color.WHITE);
						buffer.fillRect(x1,y1,10,30);	
						break;
					
				}	
		}
		else if (mode == ERASER && mousemove){
			buffer.setColor(Color.BLACK);
			buffer.drawRect(x1,y1,10,30);
			buffer.setColor(Color.WHITE);
			buffer.fillRect(x1,y1,10,30);
			
		}
		
		g.drawImage(image, 0, 0, this);
		
		
	}
	
	
}