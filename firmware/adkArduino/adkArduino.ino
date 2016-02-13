/*
  Arduino Sketch for the "Wearable LED Matrix Sign", which shows
  a Tweet of Twitter.
            29 April 2015, Takashi Yamanoue @ Fukuyama University. 
*/
#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>

// Adafruit_NeoMatrix example for single NeoPixel Shield.
// Scrolls 'Howdy' across the matrix in a portrait (vertical) orientation.

#include <Adafruit_GFX.h>
#include <Adafruit_NeoMatrix.h>
#include <Adafruit_NeoPixel.h>
#ifndef PSTR
 #define PSTR // Make Arduino Due happy
#endif

//#include <stdio.h>

#define VWMax 96
#define VHMax 16
#define RWMax 32
#define RHMax 16

#define PIN 6

// MATRIX DECLARATION:
// Parameter 1 = width of NeoPixel matrix
// Parameter 2 = height of matrix
// Parameter 3 = pin number (most are valid)
// Parameter 4 = matrix layout flags, add together as needed:
//   NEO_MATRIX_TOP, NEO_MATRIX_BOTTOM, NEO_MATRIX_LEFT, NEO_MATRIX_RIGHT:
//     Position of the FIRST LED in the matrix; pick two, e.g.
//     NEO_MATRIX_TOP + NEO_MATRIX_LEFT for the top-left corner.
//   NEO_MATRIX_ROWS, NEO_MATRIX_COLUMNS: LEDs are arranged in horizontal
//     rows or in vertical columns, respectively; pick one or the other.
//   NEO_MATRIX_PROGRESSIVE, NEO_MATRIX_ZIGZAG: all rows/columns proceed
//     in the same order, or alternate lines reverse direction; pick one.
//   See example below for these values in action.
// Parameter 5 = pixel type flags, add together as needed:
//   NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs)
//   NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
//   NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
//   NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)


// Example for NeoPixel Shield.  In this application we'd like to use it
// as a 5x8 tall matrix, with the USB port positioned at the top of the
// Arduino.  When held that way, the first pixel is at the top right, and
// lines are arranged in columns, progressive order.  The shield uses
// 800 KHz (v2) pixels that expect GRB color data.
/* */
Adafruit_NeoMatrix matrix = Adafruit_NeoMatrix(32, 16, PIN,
  NEO_MATRIX_TOP     + NEO_MATRIX_LEFT +
  NEO_MATRIX_COLUMNS + NEO_MATRIX_ZIGZAG,
  NEO_GRB            + NEO_KHZ800);

const uint16_t colors[] = {
  matrix.Color(255, 0, 0), matrix.Color(0, 255, 0), matrix.Color(0, 0, 255) };
  
uint16_t backGround;
uint16_t virtualGraphicsArea[VWMax][VHMax];
uint16_t realGraphicsArea[RWMax][RHMax];


/*

   Android
   +---------------------+ tweet
       ...
   +---------------------+ tweet max 132 char
     ^        |
     |        | send font at p
    ask       | to arduino and p++, 
    new       | if p>= tweet.length{ 
    font      |   if no next tweet, p=0
    from      |   else renew current tweet by the next and p=0
    arduino   | }
              |
              v
  at the Arduino
      if new font received,
      put the received font at lp, lp++;
      if(lp>=vfmax) lp=0;  
              
   dp (displaying position) = x/8
  
   initial ... x=0, lp=0, dp=0
   
  if lp=dp-1 {
     ask no font
  }
  else{
     ask new font
  }
  
 
                      Android ADK
                         | 'f', [font kind (0 or 1)], f[32]
                         v
                      +-------+
                      | 32byte| or 16 byte
   dp=x/8  char       +-------+
        |                | putFontAt ( char position)
        |                |
        |                | lp ... last received font position
        v                v
+-----------------------------------+ vfmax =VWMax/16
|     virtual matrix                |
|     width: 6x16= 96 dot           |
|     height: 16 dot                |
+-----------------------------------+
        |      |
        |x     |x+31
+-------+      |     copyVirtualPart2Real, x++,
|              |     if x>= VWMax, x=0;
v              v
****************   real matrix
**************** ^
**************** |
   ...           16 dot
**************** |
**************** v
****************
****************
 <--32 dot->

*/

void clearVirtualGraphicsArea(){
  int i,j;
//  printf("start clearVirtualGraphicsArea\n");
    for( i=0;i<VWMax;i++){
      for( j=0;j<VHMax;j++){
          virtualGraphicsArea[i][j]=backGround;
      }
    }
//  printf("end clearVirtualGraphicsArea\n");
}
void clearRealGraphicsArea(){
  int i,j;
//  printf("start clearRealGraphicsArea\n");
    for( i=0;i<RWMax;i++){
      for( j=0;j<RHMax;j++){
          realGraphicsArea[i][j]=backGround;
      }
    }
//  printf("end clearRealGraphicsArea\n");
}
/* */
/* */
void copyVirtualPart2Real(int x){
  
  int i,j,k;
//  printf("start copyVirtualPart2Real\n");
  for( i=0;i<RWMax;i++){
     k=x+i;
     if(k>=VWMax){
         k=(x+i)%VWMax;
     }
     for( j=0;j<RHMax;j++){
         realGraphicsArea[i][j]=virtualGraphicsArea[k][j];
     }
  }
}

void initialVirtualGraphicsArea(){
     int i;
    clearVirtualGraphicsArea();
    for(int c=0;c<10;c++){
        for( i=0;i<RHMax;i++){
           int w=i+c*16;
           if(w<VWMax){
               virtualGraphicsArea[i+c*16][i]=matrix.Color((255-c*16),c*16,c*16);
           }
        }
    }
 
}

void putFontAt(byte f[], int p, uint16_t color){ // font width=8
//   int oneFontSize=8;
//   int w=(oneFontSize+7)/8;
//   Serial.print("pos=");
//   Serial.print(x);
//   Serial.print("\n\r");
//   if(lp>=vfmax) lp=0;
   if(p>=VWMax) return;
   backGround=matrix.Color(0,0,0);   
   for(int i=0;i<VHMax;i++){
	int l=f[i];
	int mask=0x0080;
	for(int k=0;k<8;k++){
	        if((mask & l)!=0){
		    virtualGraphicsArea[k+p*8][i]=color;
//                    Serial.print("*");
                }
		else {
		    virtualGraphicsArea[k+p*8][i]=backGround;
//                    Serial.print(" ");
                }
		mask=mask>>1;
	}
//	Serial.print("\n\r");
   }
//   lp++; 
}
/* */
/*
data format

pin
A0-A7 ... analog input

4-7 ... digital input
8-11 ... digital/analog(pwm) output

receive 
[
  byte cmd[256]; 
]


send
[
  4 (=data_length(byte)),
  'a' 
  port,
  data(high),
  data(low)
]
or
[
  4 (=data_length(byte)),
  'd'
  data  (pin 0-7)
  data  (0x00)
  data  (0x00)
]

*/
#define analogInMax 8
#define digitalInMax 6
#define digitalOutMax 14
int analogIns[analogInMax];
int digitalIns[digitalInMax];
int digitalOuts[digitalOutMax];
int digitalVal;

AndroidAccessory acc("Google, Inc.",
		     "Twitter2NeoMatrixEx4",
		     "Arduino Board for twitter2neomatrixex3",
		     "1.0",
		     "http://www.android.com",
		     "0000000012345000");
void setup();
void loop();

int realX;
int lp; // last received font position on the virtualGraphicsArea
int dp; // displayed font position on the virtualGraphicsArea
int vfmax; // virtual font area max ... = VWMax/16;

void setup()
{
   Serial.begin(57600);
   Serial.print("\r\nStart\n\r");
   for(int i=0;i<digitalInMax;i++) digitalIns[i]=i;
   for(int i=0;i<digitalOutMax;i++) digitalOuts[i]=i;

   for(int i=0;i<digitalInMax;i++)
      pinMode(digitalIns[i],INPUT);
   for(int i=digitalInMax+1;i<digitalOutMax;i++)
      pinMode(digitalOuts[i],OUTPUT);
//   pinMode(ledPin, OUTPUT);
//   pinMode(b3Pin, INPUT);
   analogIns[0]=A0;
   analogIns[1]=A1;
   analogIns[2]=A2;
   analogIns[3]=A3;
   analogIns[4]=A4;
   analogIns[5]=A5;
   analogIns[6]=A6;
   analogIns[7]=A7;
   delay(5); // add 2012 12/9
   Serial.print("acc.powerOn Start\n\r");
   acc.powerOn();
   Serial.print("acc.powerOn\n\r");
  matrix.begin();
  matrix.setBrightness(40); 
  backGround=matrix.Color(0,0,0); 
  initialVirtualGraphicsArea();
  /* */
  Serial.print("setup end\n");
  realX=0;
  lp=0;
  dp=0;
  vfmax=VWMax/16;
}

void readAcc(){
  byte inMsg[64];
//     Serial.print("acc is connected\n");
     int len = acc.read(inMsg, sizeof(inMsg), 1);
     int i;
     byte b;
     if(len<=0){
       return;
     }
//       Serial.print("inMsg[0]="); Serial.print(inMsg[0]); Serial.print("\n\r");
       if(inMsg[0]=='a'){
         if(inMsg[1]<digitalOutMax)
            analogWrite(digitalOuts[inMsg[1]], inMsg[2]);
       }
       else
       if(inMsg[0]=='d'){
         if(digitalInMax-1<inMsg[1] && inMsg[1]<digitalOutMax){
             if(inMsg[2]==1)
                digitalWrite(digitalOuts[inMsg[1]], HIGH);
             else
                digitalWrite(digitalOuts[inMsg[1]], LOW);            
         }
       }
       /* */
       else
       if(inMsg[0]=='f'){
         int fontKind=inMsg[1];
         byte font[16];
         if(fontKind==2){
            for(int i=0;i<16;i++){
              font[i]=inMsg[i*2+2];
            }
//         Serial.print("x="); Serial.print(x); Serial.print("\n\r");
//         Serial.print("lp="); Serial.print(lp); Serial.print("\n\r");
//         Serial.print("dp="); Serial.print(dp); Serial.print("\n\r"); 
            int fcolor=lp%6;
            putFontAt(font,lp, matrix.Color((255-fcolor*16),fcolor*16,fcolor*16));
            lp++;
            if(lp>=(VWMax/8)) lp=0;
            for(int i=0;i<16;i++){
              font[i]=inMsg[i*2+3];
            }
//         Serial.print("x="); Serial.print(x); Serial.print("\n\r");
//         Serial.print("lp="); Serial.print(lp); Serial.print("\n\r");
//         Serial.print("dp="); Serial.print(dp); Serial.print("\n\r"); 
//            int fcolor=lp%6;
            putFontAt(font,lp, matrix.Color((255-fcolor*16),fcolor*16,fcolor*16));
            lp++;            
         }
         else
         if(fontKind==1){
             for(int i=0;i<16;i++){
              font[i]=inMsg[i+2];
            }
//         Serial.print("x="); Serial.print(x); Serial.print("\n\r");
//         Serial.print("lp="); Serial.print(lp); Serial.print("\n\r");
//         Serial.print("dp="); Serial.print(dp); Serial.print("\n\r"); 
            int fcolor=lp%6;
            putFontAt(font,lp, matrix.Color((255-fcolor*16),fcolor*16,fcolor*16));
            lp++;          
         }
         if(lp>=(VWMax/8)) lp=0;
       }
      /* */  
}
void writeAcc(){
  byte outMsg[4];
     digitalVal=0;
     for(int i=0;i<digitalInMax;i++){
       int b=0;
       if(digitalRead(digitalIns[i])==HIGH)
          b=1;
       digitalVal=digitalVal<<1 | b;
     }
     outMsg[0]='d';
     outMsg[1]=digitalVal & 0xff;
     outMsg[2]=0;
     outMsg[3]=0;
     acc.write(outMsg,4);
     int sensorValue;
     for(int i=0;i<analogInMax;i++){
          sensorValue = analogRead(analogIns[i]);
          outMsg[0]='a';
          outMsg[1]=i;
          outMsg[2]=(sensorValue>>8) & 0xff;
          outMsg[3]=sensorValue & 0xff;
          acc.write(outMsg,4);
     }
   if(realX%8==0){
     if(lp==dp-1){
//       outMsg[0]='f';
//       outMsg[1]='n';
//       outMsg[2]=0;
//       outMsg[3]=0;
//       acc.write(outMsg,4);
     }
     else
     if(lp==dp-2){
     }
     else{
       outMsg[0]='f';
       outMsg[1]='r';
       outMsg[2]=0;
       outMsg[3]=0;
       acc.write(outMsg,4);     
     }
   }  
}

void loop()
{
  byte inMsg[64];
  byte outMsg[4];
  if(realX%4==0){
    if (acc.isConnected()) {
      readAcc();
      writeAcc();
    }
  }
//  Serial.print("neo matrix\n\r");   
  /* */
  matrix.fillScreen(0);
  
  copyVirtualPart2Real(realX);
  for(int i=0;i<RWMax;i++){
     for(int j=0;j<RHMax;j++){
       uint16_t c=realGraphicsArea[i][j];
//       printf("%d\n",c);
       matrix.drawPixel(i,j,c);
     }
  }

  realX++;
  if(realX>=VWMax) realX=0;
  dp=realX/8;
    
  matrix.show();   
  /* */   
   delay(20);
}

