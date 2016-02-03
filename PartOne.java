/*
Christopher McMahon
February, 2, 2016.
Assignment 2 Part 1
*/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class PartOne extends JFrame implements KeyListener, ActionListener {
    private JPanel panelA = new JPanel();
    private JPanel panelB = new JPanel();
    private JPanel panelC = new JPanel();
    private JPanel panelD = new JPanel();

    private JPanel panelKR1 = new JPanel();
    private JPanel panelKR2 = new JPanel();
    private JPanel panelKR3 = new JPanel();
    private JPanel panelKR4 = new JPanel();
    private JPanel panelKR5 = new JPanel();
    private JPanel panelKR6 = new JPanel();
    
    private JLabel infoLabel= new JLabel("Type some text from your keyboard. The keys you press will be highlighted and their text will be displayed");
    private JLabel warnLabel= new JLabel("Note: Clicking the buttons with your mouse will not perfom any action.... for now ;)");
	private JTextArea inputArea = new JTextArea(10,50);
	private JButton[] keyboard = new JButton[525];
    private JButton startButton = new JButton("Start");
    private JButton finishButton = new JButton("Finish");
    
	private final String pangram[] = {"The quick brown fox jumps over a lazy dog",
															"The five boxing wizards jump quickly",
															"We promptly judged antique ivory buckles for the next prize",
															"A mad boxer shot a quick, gloved jab to the jaw of his dizzy opponent",	
															"How razorback-jumping frogs can level six piqued gymnasts"};
	private int max = pangram.length-1, min = 0;
	private static int ranNum;
	private static Random ranGen = new Random();//randomly generates a number between max and min

	public PartOne() {
		setResizable(false);
		setSize(new Dimension(700,500));
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
		panelA.setPreferredSize(new Dimension(650,50));
		panelA.add(infoLabel);
		panelA.add(warnLabel);
		
        this.inputArea.addKeyListener(this);
        this.startButton.addActionListener(this);
        this.finishButton.addActionListener(this);
        
        panelB.setPreferredSize(new Dimension(650,150));
        panelB.add(inputArea);
        
		panelC.setLayout(new GridLayout(6,0));
		panelC.setPreferredSize(new Dimension(650,200));
        keyboardPanel();
        
        finishButton.setEnabled(false);
		panelD.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelD.setPreferredSize(new Dimension(650,50));
		panelD.add(startButton);
		panelD.add(finishButton);

        add(panelA);
        add(panelB);
        add(panelC);
        add(panelD);
	}//PartOne constructor
	
	public void keyboardPanel() {
		int index,count = 0;//index for the array of buttons, counts the number of loops
	    String line;//stores the file.txt line

	    //tries to open a file to read the key.getKeyCode() value and the JButton Text
		try(BufferedReader br = new BufferedReader(new FileReader("src/file.txt"))) {        	
            while((line = br.readLine()) != null) {
    			StringTokenizer keyTok = new StringTokenizer(line," ");
    			index = Integer.parseInt(keyTok.nextToken());
        		
    			keyboard[index] = new JButton(keyTok.nextToken());
        		keyboard[index].setEnabled(false);
        		
        		//hard coded to display the correct keys per line
        		if(count <= 11) {
        			panelKR1.add(keyboard[index]);
        		}//if
        		else if(count >= 12 && count <= 25) {
        			panelKR2.add(keyboard[index]);
        		}//else if
        		else if(count >= 26 && count <= 39) {
        			panelKR3.add(keyboard[index]);
        		}//else if
        		else if(count >= 40 && count <= 52) {
        			panelKR4.add(keyboard[index]);
        		}//else if
        		else if(count >= 53 && count <= 62) {
        			panelKR5.add(keyboard[index]);
        		}//else if
        		else if(count >= 63) {
        			panelKR6.add(keyboard[index]);
        		}//else if
        		count ++;
           }//while  
            panelC.add(panelKR1);
            panelC.add(panelKR2);
            panelC.add(panelKR3);
            panelC.add(panelKR4);
            panelC.add(panelKR5);
            panelC.add(panelKR6);
            
            br.close();//closes the file         
        }//try
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");                
        }//catch 1
        catch(IOException ex) {
            System.out.println("Error reading file ");                  
        }//catch 2
	}//keyboardPanel

	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == startButton) {
			inputArea.setText("");
			startButton.setEnabled(false);
			finishButton.setEnabled(true);
			startDialog();
		}//if
		else if(evt.getSource() == finishButton) {
			startButton.setEnabled(true);
			finishButton.setEnabled(false);
			finishDialog();
		}//else if
	}//actionPerformed
	
    public void keyPressed(KeyEvent evt) {
		int i = evt.getKeyCode();
		
		if(keyboard[i] != null) {
	    	keyboard[i].setBackground(Color.YELLOW);
		}//if
    }//keyPressed

    public void keyReleased(KeyEvent evt) {
		int i = evt.getKeyCode();
		
		if(keyboard[i] != null) {
			keyboard[i].setBackground(new JButton().getBackground());
		}//if
    }//keyReleased

	public void startDialog () {
		JDialog startD = new JDialog(new JFrame(),"Typing Test");
		startD.setModalityType(null);
		startD.setResizable(false);
		startD.setLayout(new FlowLayout());
		startD.setVisible(true);
		startD.setAlwaysOnTop(true);
		startD.setSize(415,75);
		startD.setLocation(inputArea.getX() + 850,inputArea.getY() + 200);

		ranNum = ranGen.nextInt(max - min + 1) + min;
	
		JLabel testLabel = new JLabel(pangram[ranNum]);
		 startD.add(testLabel);
	}//startDialog
	
	public void finishDialog () {
		JDialog finishD = new JDialog(new JFrame(),"Results");
		finishD.setModalityType(null);
		finishD.setResizable(false);
		finishD.setLayout(new FlowLayout());
		finishD.setVisible(true);
		finishD.setAlwaysOnTop(true);
		finishD.setSize(415,115);
		finishD.setLocation(inputArea.getX() + 850,inputArea.getY() + 550);

		int index = 0,count = 0;
		String usrString = inputArea.getText();
		String testString = pangram[ranNum];
		
		//counts the number of correct characters in the strings
		while(index < usrString.length() && index < testString.length()) {
			if(usrString.charAt(index) == testString.charAt(index)) {
				count ++;
			}//if
			index ++;
		}//while
		
		JLabel correctLabel = new JLabel("The Number of Correctly Inputted Characters: " + count);
		JLabel wrongLabel = new JLabel("The Number of Incorrectly Inputted Characters: " + (testString.length() - count));
		JLabel accLabel = new JLabel("The Accuracy Rating: %" + String.format("%.2f", ((double)count / (double)testString.length())*100));
	
		finishD.add(correctLabel);
		finishD.add(wrongLabel);
		finishD.add(accLabel);
	}//finishDialog
	
	public void keyTyped(KeyEvent evt) {
		//nothing to do here :D 
	}//keyTyped
	
	public static void main(String[] args) {
		PartOne Frame = new PartOne();
		Frame.setVisible(true);
	}//main method
}//PartOne class
