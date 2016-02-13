import processing.serial.*;

import cc.arduino.*;

Arduino arduino;

color off = color(4, 79, 111);
color on = color(84, 145, 158);

int dt=200;
int oneFrameTerm=1000/30;
int dgt=24;
VScrollbar[] outputData=new VScrollbar[4];
int[] portMap= {8,9,10,11};
int[] rMax={85,98,100,110};
int[] rMin={135,130,160,175};
int[] iniVal={130,130,130,140};

long lastTime=0;
long lastFrameTime=0;

boolean debug=false;

void setup() {
//  debug=true;
  size(470, 400);
  if(!debug){
    println(Arduino.list());
    arduino = new Arduino(this, Arduino.list()[18], 57600);
  
    for (int i = 0; i <= 13; i++)
    arduino.pinMode(i, Arduino.OUTPUT);
  }

  for(int i=0; i<4; i++){
     outputData[i]=new VScrollbar(20+i*dgt,70,10,256,256,iniVal[i],3*5+1);
     outputData[i].setMax(rMax[i]);
     outputData[i].setMin(rMin[i]);
//     outputData[i].setVal(iniVal[i]);
  }
  color buttoncolor = color(204);
  color highlight = color(96,24,128);
  color scolor = color(24,96,128);

  int dgt=24;

  lastTime=millis();
  lastFrameTime=millis();
  frameRate(1000);
}

int currentVal=0;

void draw() {
  long currentFrameTime=millis();
  if(currentFrameTime-lastFrameTime>oneFrameTerm){
    background(off);
    stroke(on);
  
    for(int i=0;i<4; i++){
      outputData[i].update();
      outputData[i].display();
    }
    
    fill(0,0,0);
    textSize(10);

  }
  
  long currentTime=millis();
  if(currentTime-lastTime>dt){
      lastTime=currentTime;
      for(int i=0;i<4;i++){
         currentVal=outputData[i].getPos();
         if(debug)
           println("currentVal["+i+"]="+currentVal);
         else
           arduino.analogWrite(portMap[i],currentVal);
      }
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
  int vmax, vmin;
  float smax, smin;

  VScrollbar (int xp, int yp, int sw, int sh, int range, int inival, int l) {
    swidth = sw;
    sheight = sh;
    int heighttowidth = sh - sw;
    ratio = (float)sh / (float)heighttowidth;
    magnitude=(float)range / (float)sheight;
//    println("ration="+ratio+ " magnitude="+magnitude);
    ypos = yp;
    xpos = xp-swidth/2;
//    spos = ypos + sheight/2 - swidth/2;
    setVal(inival);
//    println("ypos="+ypos+" xpos="+xpos+ " spos="+spos);
    newspos = spos;
    sposMin = ypos;
    sposMax = ypos + sheight - swidth;
//    println("newspos="+newspos+" sposMin="+sposMin+ " sposMax="+sposMax);    
    loose = l;
    smax=sposMax;
    smin=sposMin;
  }

  void setRange(int range)
  {
    magnitude=(float)range / (float)sheight;    
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
//      int my=constrain(mouseY-swidth/2,(int)smin,(int)smax);
      newspos = constrain(mouseY-swidth/2, sposMin, sposMax);
      newspos=constrain((int)newspos,(int)smin,(int)smax);

    }
    if(abs(newspos - spos) > 1) {
      spos = spos + (newspos-spos)/loose;
    }
  }

  int constrain(int val, int minv, int maxv) {
    int rtn=min(max(val, minv), maxv);
    println("val="+val+" minv="+minv+" maxv="+maxv+" rtn="+rtn);
    return rtn;
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
    return pos2v(spos);
  }
  
  int pos2v(float x){
    int rtn= (int)(magnitude * ((float)(sheight-((x-ypos)*ratio))))-1;
//    println("pos2v x="+x+" rtn="+rtn);
    return rtn;    
  }
  
  void setSelected(boolean f)
  {
    selected=f;
  }
  
  void setVal(int v){
    spos=val2spos(v);
//    println("v="+v+" spos="+spos);
  }
  
  float val2spos(int v){
    return (float)((sheight-(float)(v+1)/magnitude)/ratio +ypos);
  }
  
  void setMax(int v){
    vmax=v;
    smax=val2spos(v);
//    println("v="+v+" smax="+smax);    
  }
  void setMin(int v){
    vmin=v;
//    sposMin=(int)val2spos(v);
//    println("v="+v+" sposMin="+sposMin);
     smin=val2spos(v);
//    println("v="+v+" smin="+smin);
     
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

