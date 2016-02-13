import processing.serial.*;

import cc.arduino.*;

Arduino arduino;

color off = color(4, 79, 111);
color on = color(84, 145, 158);

int[] values = { Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW,
 Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW,
 Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW };

int dt=2000;
int oneFrameTerm=1000/20;
int waveDataPointer;

VScrollbar[] waveData=new VScrollbar[16];
VScrollbar period;

RectButton maxPeriod32000;
RectButton maxPeriod400;

int dgt=24;

long lastTime=0;
long lastFrameTime=0;

void setup() {
  size(470, 400);
  
  println(Arduino.list());
  arduino = new Arduino(this, Arduino.list()[6], 57600);
  
  for (int i = 0; i <= 13; i++)
    arduino.pinMode(i, Arduino.OUTPUT);

/*    
  for (int i=0; i<16; i++){
     
  }
 */
  for(int i=0; i<16; i++){
     waveData[i]=new VScrollbar(20+i*dgt,70,10,256,256,3*5+1);
  }
  color buttoncolor = color(204);
  color highlight = color(96,24,128);
  color scolor = color(24,96,128);
  
  period=new VScrollbar(440,70,15,256,30000,3*5+1);
  maxPeriod32000=new RectButton(430,332,25,15,buttoncolor,highlight,scolor);
  maxPeriod32000.setLabel("30000");
  maxPeriod400  =new RectButton(430,352,25,15,buttoncolor,highlight,scolor);
  maxPeriod400.setLabel("1000");
  maxPeriod32000.setSelected(true);
 
  lastTime=millis();
  lastFrameTime=millis();
  waveDataPointer=0;
  frameRate(1000);
}

int currentVal=0;

void draw() {
  long currentFrameTime=millis();
  if(currentFrameTime-lastFrameTime>oneFrameTerm){
    background(off);
    stroke(on);
  
    for (int i = 0; i <= 13; i++) {
      if (values[i] == Arduino.HIGH)
        fill(on);
      else
        fill(off);
      
      rect(420 - i * 30, 30, 20, 20);
    }
    for(int i=0;i<16; i++){
      waveData[i].update();
      waveData[i].display();
    }
    period.update();
    period.display();
    dt=period.getPos()/16;
    maxPeriod32000.update();
    maxPeriod400.update();
    maxPeriod32000.display();
    maxPeriod400.display();
    if(maxPeriod32000.isSelected()){
      maxPeriod400.setSelected(false);
      period.setRange(30000);
    }
    if(maxPeriod400.isSelected()){
      maxPeriod32000.setSelected(false);
      period.setRange(1000);
    }
    
    fill(0,0,0);
    textSize(10);
    text("wave",10,65);
    text("period",415,65);
  }
  
  long currentTime=millis();
  if(currentTime-lastTime>dt){
      lastTime=currentTime;
      if(waveDataPointer==0) waveData[15].setSelected(false);
      else waveData[waveDataPointer-1].setSelected(false);
      currentVal=waveData[waveDataPointer].getPos();
      waveData[waveDataPointer].setSelected(true);
      outAnalog(currentVal);
      waveDataPointer++;
      if(waveDataPointer>=16) waveDataPointer=0;
  }  
}

void outAnalog(int a){
  int b=0x01;
  for(int i=0;i<8;i++){
    if((a & b)!=0)
       values[i+2]=Arduino.HIGH;
    else
       values[i+2]=Arduino.LOW;
    b=b<<1;
  }
  for(int i=0;i<13; i++){
    arduino.digitalWrite(i,values[i]);
  }
}

void mousePressed()
{
  int pin = (450 - mouseX) / 30;
  if(mouseY<30) return;
  if(mouseY>30+20) return;
  if (values[pin] == Arduino.LOW) {
    arduino.digitalWrite(pin, Arduino.HIGH);
    values[pin] = Arduino.HIGH;
  } else {
    arduino.digitalWrite(pin, Arduino.LOW);
    values[pin] = Arduino.LOW;
  }
}

class VScrollbar
{
  int swidth, sheight;    // width and height of bar
  int xpos, ypos;         // x and y position of bar
  float spos, newspos;    // y position of slider
  int sposMin, sposMax;   // max and min values of slider
  int loose;              // how loose/heavy
  boolean over;           // is the mouse over the slider?
  boolean locked;
  float ratio;
  float magnitude;
  boolean selected;

  VScrollbar (int xp, int yp, int sw, int sh, int range, int l) {
    swidth = sw;
    sheight = sh;
    int heighttowidth = sh - sw;
    ratio = (float)sh / (float)heighttowidth;
    magnitude=(float)range/(float)sheight;
    ypos = yp;
    xpos = xp-swidth/2;
    spos = ypos + sheight/2 - swidth/2;
    newspos = spos;
    sposMin = ypos;
    sposMax = ypos + sheight - swidth;
    loose = l;
  }

  void setRange(int range)
  {
    magnitude=(float)range/(float)sheight;    
  }
  
  void update() {
    if(over()) {
      over = true;
    } else {
      over = false;
    }
    if(mousePressed && over) {
      locked = true;
    }
    if(!mousePressed) {
      locked = false;
    }
    if(locked) {
      newspos = constrain(mouseY-swidth/2, sposMin, sposMax);
    }
    if(abs(newspos - spos) > 1) {
      spos = spos + (newspos-spos)/loose;
    }
  }

  int constrain(int val, int minv, int maxv) {
    return min(max(val, minv), maxv);
  }

  boolean over() {
    if(mouseX > xpos && mouseX < xpos+swidth &&
    mouseY > ypos && mouseY < ypos+sheight) {
      return true;
    } else {
      return false;
    }
  }

  void display() {
    fill(255);
    rect(xpos, ypos, swidth, sheight);
    if(over || locked) {
      fill(153, 102, 0);
    } else {
      fill(102, 102, 102);
    }
    if(selected){
      fill(192,64,32);
    }
    rect(xpos, spos, swidth, swidth);
    fill(63,32,8);
    textSize(10);
    text(getPos(),xpos,spos);
  }

  int getPos() {
    // Convert spos to be values between
    // 0 and the total width of the scrollbar
    return (int)(magnitude * ((float)(sheight-(int)((spos-ypos)*ratio))))-1;
  }
  
  void setSelected(boolean f)
  {
    selected=f;
  }
}

class Button
{
  int x, y;
  int size;
  color basecolor, highlightcolor;
  color currentcolor;
  color selectedColor;
  boolean over = false;
  boolean pressed = false;   
  String label;
  boolean locked = false;
  boolean selected = false;

  void update() 
  {
    if(over()) {
      currentcolor = highlightcolor;
    } 
    else {
      currentcolor = basecolor;
    }
    if(mousePressed && over) {
         locked = true;
         if(!selected) selected=true;
         else selected=false;
    }
    if(!mousePressed) {
      locked = false;
    }
    if(isSelected()){
      currentcolor = selectedColor;
    }
    else{
      currentcolor = basecolor;
    }
  }

  boolean pressed() 
  {
    if(over) {
      locked = true;
      return true;
    } 
    else {
      locked = false;
      return false;
    }    
  }

  boolean isSelected()
  {
    return selected;
  }
  void setSelected(boolean f){
    selected=f;
  }

  boolean over() 
  { 
    return true; 
  }

  boolean overRect(int x, int y, int width, int height) 
  {
    if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
      return true;
    } 
    else {
      return false;
    }
  }

  boolean overCircle(int x, int y, int diameter) 
  {
    float disX = x - mouseX;
    float disY = y - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
      return true;
    } 
    else {
      return false;
    }
  }
  
  void setLabel(String x)
  {
    label=x;
  }

}

class CircleButton extends Button
{ 
  CircleButton(int ix, int iy, int isize, color icolor, color ihighlight) 
  {
    x = ix;
    y = iy;
    size = isize;
    basecolor = icolor;
    highlightcolor = ihighlight;
    currentcolor = basecolor;
  }

  boolean over() 
  {
    if( overCircle(x, y, size) ) {
      over = true;
      return true;
    } 
    else {
      over = false;
      return false;
    }
  }

  void display() 
  {
    stroke(255);
    fill(currentcolor);
    ellipse(x, y, size, size);
  }
}

class RectButton extends Button
{
  int rWidth;
  int rHeight;
  RectButton(int ix, int iy, int w, int h, 
     color icolor, color ihighlight, color scolor) 
  {
    x = ix;
    y = iy;
    rWidth = w;
    rHeight= h;
    size=h;
    basecolor = icolor;
    highlightcolor = ihighlight;
    currentcolor = basecolor;
    selectedColor=scolor;
  }

  boolean over() 
  {
    if( overRect(x, y, rWidth, rHeight) ) {
      over = true;
      return true;
    } 
    else {
      over = false;
      return false;
    }
  }

  void display() 
  {
    stroke(255);
    fill(currentcolor);
    rect(x, y, rWidth, rHeight);
    if(label!=null){
       fill(0,64,21);
       textSize(8);
       text(label,x,y+8);
    }
  }
}

