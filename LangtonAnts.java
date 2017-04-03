import java.io.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class LangtonAnts extends JPanel{
  
  ArrayList<String> listOfStates = new ArrayList<String>();
  ArrayList<String[]> steps = new ArrayList<String[]>();
  Hashtable<String, Color> stateColor = new Hashtable<String, Color>();
  static int position, stepTime;
  Random r = new Random();
  
  public static void main(String [] args) {
    stepTime = Integer.parseInt(args[0]);
    AntsB a = new AntsB();
    a.processInputs();
    a.setColors();
    a.position = 0;
    JFrame window = new JFrame();
    window.setExtendedState (JFrame.MAXIMIZED_BOTH);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.add(a);
    window.setVisible(true);
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setBackground(Color.black); 
    if (position < steps.size()) {
      for(int i = 0; i <= position; i++) {
        String state = (steps.get(i)[2]);
        g.setColor(stateColor.get(state));
        g.fillRect(Integer.parseInt(steps.get(i)[0])*10 + 960, Integer.parseInt(steps.get(i)[1])*10 + 500, 10, 10);
      }
      g.setColor(Color.white);
      g.fillOval(Integer.parseInt(steps.get(position)[0])*10 + 960, Integer.parseInt(steps.get(position)[1])*10 + 500, 10, 10);
      position++;
    }
    repaint();
    double time = System.currentTimeMillis();
    while(System.currentTimeMillis() < time + stepTime) {}
  }
  
  
  public void processInputs(){
    String input;
    boolean b = false;
    ArrayList<String> dnaList = new ArrayList<String>();
    try {
      
      int a = 0;
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      while((input = br.readLine()) != null) {
        
        if (!(input.trim().indexOf('#')==0)){
          if(input.trim().equals("")){
            String str = movement(dnaList);
            System.out.println(str);
            dnaList.clear();
            b = true;
          }else{
            dnaList.add(input);
            if(input.length() > 10) listOfStates.add(String.valueOf(input.charAt(0)));
            b= false;
          }
        }
        
        
      }
    }catch(IOException io) {
      io.printStackTrace();
    }
    if (b==false){
      String str = movement(dnaList);
      System.out.println(str);
    }
  }
  
  public void setColors() {
    for(String c : listOfStates) {
      Color color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
      stateColor.put(c, color);
    }
  }
  
  
  public String movement(ArrayList<String> dnaList){
    for (int i=0; i < dnaList.size(); i++){
      System.out.println(dnaList.get(i));
    }
    int xCoordinate=0, yCoordinate=0;
    String [] a = new String[2];

    char incomingDirection = 'N', newDirection, newState;
    Hashtable<String, Character> map = new Hashtable<String, Character>();
    String s = dnaList.get(0);
    Character state =s.charAt(0);
    
    for (int p=0; p < Integer.parseInt(dnaList.get(dnaList.size()-1)); p++){
      
      //set state to the last state that square was left in
      if (!map.containsKey(xCoordinate + " " + yCoordinate)){
        state = dnaList.get(0).charAt(0);
      } else{
        state = map.get(xCoordinate + " " + yCoordinate);
      }
          String [] stepsCoord = new String[3];
      stepsCoord[2] = String.valueOf(state);
      //chooses a line dependent on the state
      for (int d=0; d< dnaList.size(); d++){
        if (state == dnaList.get(d).charAt(0)){
          s = dnaList.get(d);
        }
      }
      
      if (incomingDirection == 'N'){
        newDirection = s.charAt(2);
        state =s.charAt(7);
      } else if (incomingDirection == 'E'){
        newDirection = s.charAt(3);
        state =s.charAt(8);
      } else if (incomingDirection == 'S'){
        newDirection = s.charAt(4);
        state = s.charAt(9);
      } else{
        newDirection = s.charAt(5);
        state = s.charAt(10);
      }
      
      stepsCoord[0] = String.valueOf(xCoordinate);
      stepsCoord[1] = String.valueOf(yCoordinate);
      
      steps.add(stepsCoord);
      map.put(String.valueOf(xCoordinate+ " " + yCoordinate), state);
      
      
      if (newDirection == 'N'){
        yCoordinate++;
      } else if (newDirection == 'E'){
        xCoordinate++;
      } else if (newDirection == 'S'){
        yCoordinate--;
      } else{
        xCoordinate--;
      }
      incomingDirection = newDirection;
      
    }
    return ("# " + xCoordinate + " " + yCoordinate);
  }
  
}