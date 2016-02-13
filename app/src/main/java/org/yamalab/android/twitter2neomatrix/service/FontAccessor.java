package org.yamalab.android.twitter2neomatrix.service;

public class FontAccessor {
	byte[] fontArray;
    int base;
    int oneFontSize;
    int fontWidthByte;
    /*
     ???????????????????????????????????????????????????????????????
     - CJKOS Japanese Fonts ... http://palm.roguelife.org/cjkos/
     - ?????????????????? ... http://charset.7jp.net/jis.html
     - FONTX ???????????? ... http://elm-chan.org/docs/dosv/fontx.html
      ???????????? 16x16 ??????????????????????????????????????????????????????????????????????????????
     http://palm.roguelife.org/cjkos/download/ShinonomeMin_16.zip
     - ??????????????????????????? ... http://www.tohoho-web.com/wwwkanji.htm
    
      004C-004D ... 0010 ... FontBlock Number?

      004E-0051 ... "FBLK"
      0052-0053 ... 00 00
      0054-0055 ... 00 00
      0056-0057 ... 00 F0
      
      ...
      
      00DA-00DD ... "FBLK"
      00DE-00DF ... 00 0E
      00E0-00E1 ... 00 03
      00E2-00E3 ... 80 F0  
      
      00E4-00E7 ... "FBLK"
      00E8-00E9 ... 00 0F
      00EA-00EB ... 00 03
      00EC-00ED ... C0 F0  
           
     */
   int[] asciicode={
	       0x2121, 0x2121,0x2121,0x2121,  //0x00, 0x01, 0x02, 0x03, 
	       0x2121, 0x2121,0x2121,0x2121,  //0x04, 0x05, 0x06, 0x07,
	       0x2121, 0x2121,0x2121,0x2121,  //0x08, 0x09, 0x0A, 0x0B, 
	       0x2121, 0x2121,0x2121,0x2121,  //0x0C, 0x0D, 0x0E, 0x0F,
	       0x2121, 0x2121,0x2121,0x2121,  //0x10, 0x11, 0x12, 0x13, 
	       0x2121, 0x2121,0x2121,0x2121,  //0x14, 0x15, 0x16, 0x17,
	       0x2121, 0x2121,0x2121,0x2121,  //0x18, 0x19, 0x1A, 0x1B, 
	       0x2121, 0x2121,0x2121,0x2121,  //0x1C, 0x1D, 0x1E, 0x1F,
	       0x2121, 0x212A,0x2148,0x2174,  //0x20, 0x21, 0x22, 0x23, ' ', '!', '"', '#', 
	       0x2170, 0x2173,0x2175,0x212D,  //0x24, 0x25, 0x26, 0x27, '$', '%', '&', ''',
	       0x214A, 0x214B,0x2176,0x215C,  //0x28, 0x29, 0x2A, 0x2B, '(', ')', '*', '+',
	       0x2124, 0x215D,0x2125,0x213F,  //0x2C, 0x2D, 0x2E, 0x2F, ',', '-', '.', '/',
	       0x2330, 0x2331,0x2332,0x2333,  //0x30, 0x31, 0x32, 0x33, '0', '1', '2', '3',
	       0x2334, 0x2335,0x2336,0x2337,  //0x34, 0x35, 0x36, 0x37, '4', '5', '6', '7',
	       0x2338, 0x2339,0x2127,0x2128,  //0x38, 0x39, 0x3A, 0x3B, '8', '9', ':', ';',
	       0x2163, 0x2161,0x2164,0x2129,  //0x3C, 0x3D, 0x3E, 0x3F, '<', '=', '>', '?',
	       0x2177, 0x2341,0x2342,0x2343,  //0x40, 0x41, 0x42, 0x43, '@', 'A', 'B', 'C', 
	       0x2344, 0x2345,0x2346,0x2347,  //0x44, 0x45, 0x46, 0x47, 'D', 'E', 'F', 'G',
	       0x2348, 0x2349,0x234A,0x234B,  //0x48, 0x49, 0x4A, 0x4B, 'H', 'I', 'J', 'K',
	       0x234C, 0x234D,0x234E,0x234F,  //0x4C, 0x4D, 0x4E, 0x4F, 'L', 'M', 'N', 'O',
	       0x2350, 0x2351,0x2352,0x2353,  //0x50, 0x51, 0x52, 0x53, 'P', 'Q', 'R', 'S',
	       0x2354, 0x2355,0x2356,0x2357,  //0x54, 0x55, 0x56, 0x57, 'T', 'U', 'V', 'W',
	       0x2358, 0x2359,0x235A,0x214E,  //0x58, 0x59, 0x5A, 0x5B, 'X', 'Y', 'Z', '[',
	       0x2140, 0x214F,0x2130,0x2132,  //0x5C, 0x5D, 0x5E, 0x5F, '\', ']', '^', '_',
	       0x212D, 0x2361,0x2362,0x2363,  //0x60, 0x61, 0x62, 0x63, '`', 'a', 'b', 'c', 
	       0x2364, 0x2365,0x2366,0x2367,  //0x64, 0x65, 0x66, 0x67, 'd', 'e', 'f', 'g',
	       0x2368, 0x2369,0x236A,0x236B,  //0x68, 0x69, 0x6A, 0x6B, 'h', 'i', 'j', 'k',
	       0x236C, 0x236D,0x236E,0x236F,  //0x6C, 0x6D, 0x6E, 0x6F, 'l', 'm', 'n', 'o',
	       0x2370, 0x2371,0x2372,0x2373,  //0x70, 0x71, 0x72, 0x73, 'p', 'q', 'r', 's',
	       0x2374, 0x2375,0x2376,0x2377,  //0x74, 0x65, 0x76, 0x77, 't', 'u', 'v', 'w',
	       0x2378, 0x2379,0x237A,0x2150,  //0x78, 0x69, 0x7A, 0x7B, 'x', 'y', 'z', '{',
	       0x2143, 0x2151,0x2141,0x2121  //0x7C, 0x6D, 0x7E, 0x7F, '|', '}', '~', ' ',     
   };
   public FontAccessor(String path){
	   
   }
   public FontAccessor(byte[] f, int fsize, int base){
	   fontArray=f;
	   oneFontSize=fsize;
	   this.base=base;
	   fontWidthByte=(oneFontSize+7)/8;
       for(int i=0;i<asciiFont.length;i++){
    	   asciiFont[i]=(byte)(asciiFontData[i]);
       }
   }
   
   public char[] getSJISString(String x){
	   char[] rtn=new char[x.length()];
	   byte[] barray;
	   /*
	   for(int i=0;i<x.length();i++){
		   char c=x.charAt(i);
           String ss=Integer.toHexString((int)c).toUpperCase();
           System.out.println(ss);
	   }	   
	   System.out.println("");
	   */
	   try{
	      barray=x.getBytes("SJIS");
	   }
	   catch(Exception e){
		  return null;
	   }
	   int bl=barray.length;
	   int p=0;
	   int cp=0;
	   while(p<bl){
		  if(barray[p]<0){
			  rtn[cp]=(char)(barray[p]<<8 | ((0x00ff) & barray[p+1]));
			  cp++;
			  p=p+2;
		  }
		  else{
			  rtn[cp]=(char)(barray[p]);
			  cp++;
			  p++;
		  }
	   }
	   /*
	   for(int i=0;i<rtn.length;i++){
		   char c=rtn[i];
          String ss=Integer.toHexString((int)c).toUpperCase();
          System.out.println(ss);
	   }
	   */
	   return rtn;
	   
   }
   
   public char sjis2jis(char x){
	   int c1=((int)x >>8) & 0x00ff;
	   int c2=x & 0x00ff;
	   if (c1 >= 0xe0) { c1 = c1 - 0x40; }
	   if (c2 >= 0x80) { c2 = c2 - 1; }
	   if (c2 >= 0x9e) {
	       c1 = (c1 - 0x70) * 2;
	       c2 = c2 - 0x7d;
	   } else {
	       c1 = ((c1 - 0x70) * 2) - 1;
	       c2 = c2 - 0x1f;
	   }
	   char rtn=(char)((c1<<8) | c2);
	   return rtn;
   }
   
   public char[] getJISString(String x){
	   char[] tmp=getSJISString(x);
	   if(tmp==null) return null;
	   char[] rtn=new char[tmp.length];
	   int bl=tmp.length;
//	   int p=0;
//	   int cp=0;
	   for(int i=0;i<bl;i++){
		    int cx=(tmp[i] & 0x0000ffff);
		    if(cx>0x2100)
			  rtn[i]=sjis2jis(tmp[i]);
		    else
		      rtn[i]=tmp[i];
	   }
	   return rtn;
   }
   /*
    * 16 x 16 -> 8 x 16 + 8 x 16 
    */
   public byte[] getFontJIS(int code){ // x=JIS Code
//	   System.out.println("code="+Integer.toHexString((int)code).toUpperCase());
//	   long x;
	   int hb=(0xff00 & code)>>8;
	   int lb=(0x00ff & code);
	   if(code<0x2100){
//	          hb=(0xff00 & asciicode[code]) >>8;
//	          lb=(0x00ff & asciicode[code]);
		   return getAsciiFont(code);
	   }
	   int fn=((hb-0x21)*(0x7f-0x21))+lb-0x21;
	   long pos=(long)base+(long)(fn*oneFontSize*fontWidthByte);
	   return getFontRaw(pos);
   }
   byte[] asciiFont = new byte[256*8];
   int[] asciiFontData = new int[]{
		   // 
		   // use https://github.com/alexmac/alcextra/blob/master/aalib-1.4.0/src/font8.c
		   //
		    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, //0x00 null
		    0x7e, 0x81, 0xa5, 0x81, 0xbd, 0x99, 0x81, 0x7e,
		    0x7e, 0xff, 0xdb, 0xff, 0xc3, 0xe7, 0xff, 0x7e,
		    0x6c, 0xfe, 0xfe, 0xfe, 0x7c, 0x38, 0x10, 0x00,
		    0x10, 0x38, 0x7c, 0xfe, 0x7c, 0x38, 0x10, 0x00,
		    0x38, 0x7c, 0x38, 0xfe, 0xfe, 0x92, 0x10, 0x7c,
		    0x00, 0x10, 0x38, 0x7c, 0xfe, 0x7c, 0x38, 0x7c,
		    0x00, 0x00, 0x18, 0x3c, 0x3c, 0x18, 0x00, 0x00,
		    0xff, 0xff, 0xe7, 0xc3, 0xc3, 0xe7, 0xff, 0xff,
		    0x00, 0x3c, 0x66, 0x42, 0x42, 0x66, 0x3c, 0x00,
		    0xff, 0xc3, 0x99, 0xbd, 0xbd, 0x99, 0xc3, 0xff,
		    0x0f, 0x07, 0x0f, 0x7d, 0xcc, 0xcc, 0xcc, 0x78,
		    0x3c, 0x66, 0x66, 0x66, 0x3c, 0x18, 0x7e, 0x18,
		    0x3f, 0x33, 0x3f, 0x30, 0x30, 0x70, 0xf0, 0xe0,
		    0x7f, 0x63, 0x7f, 0x63, 0x63, 0x67, 0xe6, 0xc0,
		    0x99, 0x5a, 0x3c, 0xe7, 0xe7, 0x3c, 0x5a, 0x99,
		    0x80, 0xe0, 0xf8, 0xfe, 0xf8, 0xe0, 0x80, 0x00, //0x10
		    0x02, 0x0e, 0x3e, 0xfe, 0x3e, 0x0e, 0x02, 0x00,
		    0x18, 0x3c, 0x7e, 0x18, 0x18, 0x7e, 0x3c, 0x18,
		    0x66, 0x66, 0x66, 0x66, 0x66, 0x00, 0x66, 0x00,
		    0x7f, 0xdb, 0xdb, 0x7b, 0x1b, 0x1b, 0x1b, 0x00,
		    0x3e, 0x63, 0x38, 0x6c, 0x6c, 0x38, 0x86, 0xfc,
		    0x00, 0x00, 0x00, 0x00, 0x7e, 0x7e, 0x7e, 0x00,
		    0x18, 0x3c, 0x7e, 0x18, 0x7e, 0x3c, 0x18, 0xff,
		    0x18, 0x3c, 0x7e, 0x18, 0x18, 0x18, 0x18, 0x00,
		    0x18, 0x18, 0x18, 0x18, 0x7e, 0x3c, 0x18, 0x00,
		    0x00, 0x18, 0x0c, 0xfe, 0x0c, 0x18, 0x00, 0x00,
		    0x00, 0x30, 0x60, 0xfe, 0x60, 0x30, 0x00, 0x00,
		    0x00, 0x00, 0xc0, 0xc0, 0xc0, 0xfe, 0x00, 0x00,
		    0x00, 0x24, 0x66, 0xff, 0x66, 0x24, 0x00, 0x00,
		    0x00, 0x18, 0x3c, 0x7e, 0xff, 0xff, 0x00, 0x00,
		    0x00, 0xff, 0xff, 0x7e, 0x3c, 0x18, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, //0x20 space
		    0x18, 0x3c, 0x3c, 0x18, 0x18, 0x00, 0x18, 0x00,
		    0x6c, 0x6c, 0x6c, 0x00, 0x00, 0x00, 0x00, 0x00,
		    0x6c, 0x6c, 0xfe, 0x6c, 0xfe, 0x6c, 0x6c, 0x00,
		    0x18, 0x7e, 0xc0, 0x7c, 0x06, 0xfc, 0x18, 0x00,
		    0x00, 0xc6, 0xcc, 0x18, 0x30, 0x66, 0xc6, 0x00,
		    0x38, 0x6c, 0x38, 0x76, 0xdc, 0xcc, 0x76, 0x00,
		    0x30, 0x30, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00,
		    0x18, 0x30, 0x60, 0x60, 0x60, 0x30, 0x18, 0x00,
		    0x60, 0x30, 0x18, 0x18, 0x18, 0x30, 0x60, 0x00,
		    0x00, 0x66, 0x3c, 0xff, 0x3c, 0x66, 0x00, 0x00,
		    0x00, 0x18, 0x18, 0x7e, 0x18, 0x18, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0x00, 0x18, 0x18, 0x30,
		    0x00, 0x00, 0x00, 0x7e, 0x00, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0x00, 0x18, 0x18, 0x00,
		    0x06, 0x0c, 0x18, 0x30, 0x60, 0xc0, 0x80, 0x00,
		    0x7c, 0xce, 0xde, 0xf6, 0xe6, 0xc6, 0x7c, 0x00, //0x30 0
		    0x30, 0x70, 0x30, 0x30, 0x30, 0x30, 0xfc, 0x00, // 1
		    0x78, 0xcc, 0x0c, 0x38, 0x60, 0xcc, 0xfc, 0x00, // 2
		    0x78, 0xcc, 0x0c, 0x38, 0x0c, 0xcc, 0x78, 0x00, // 3
		    0x1c, 0x3c, 0x6c, 0xcc, 0xfe, 0x0c, 0x1e, 0x00, // 4
		    0xfc, 0xc0, 0xf8, 0x0c, 0x0c, 0xcc, 0x78, 0x00, // 5
		    0x38, 0x60, 0xc0, 0xf8, 0xcc, 0xcc, 0x78, 0x00, // 6
		    0xfc, 0xcc, 0x0c, 0x18, 0x30, 0x30, 0x30, 0x00, // 7
		    0x78, 0xcc, 0xcc, 0x78, 0xcc, 0xcc, 0x78, 0x00, // 8
		    0x78, 0xcc, 0xcc, 0x7c, 0x0c, 0x18, 0x70, 0x00, // 9
		    0x00, 0x18, 0x18, 0x00, 0x00, 0x18, 0x18, 0x00,
		    0x00, 0x18, 0x18, 0x00, 0x00, 0x18, 0x18, 0x30,
		    0x18, 0x30, 0x60, 0xc0, 0x60, 0x30, 0x18, 0x00,
		    0x00, 0x00, 0x7e, 0x00, 0x7e, 0x00, 0x00, 0x00,
		    0x60, 0x30, 0x18, 0x0c, 0x18, 0x30, 0x60, 0x00,
		    0x3c, 0x66, 0x0c, 0x18, 0x18, 0x00, 0x18, 0x00,
		    0x7c, 0xc6, 0xde, 0xde, 0xdc, 0xc0, 0x7c, 0x00, //0x40 @
		    0x30, 0x78, 0xcc, 0xcc, 0xfc, 0xcc, 0xcc, 0x00, // A
		    0xfc, 0x66, 0x66, 0x7c, 0x66, 0x66, 0xfc, 0x00, // B
		    0x3c, 0x66, 0xc0, 0xc0, 0xc0, 0x66, 0x3c, 0x00, // C
		    0xf8, 0x6c, 0x66, 0x66, 0x66, 0x6c, 0xf8, 0x00, // D
		    0xfe, 0x62, 0x68, 0x78, 0x68, 0x62, 0xfe, 0x00, // E
		    0xfe, 0x62, 0x68, 0x78, 0x68, 0x60, 0xf0, 0x00, // F
		    0x3c, 0x66, 0xc0, 0xc0, 0xce, 0x66, 0x3a, 0x00, // G
		    0xcc, 0xcc, 0xcc, 0xfc, 0xcc, 0xcc, 0xcc, 0x00, // H
		    0x78, 0x30, 0x30, 0x30, 0x30, 0x30, 0x78, 0x00, // I
		    0x1e, 0x0c, 0x0c, 0x0c, 0xcc, 0xcc, 0x78, 0x00, // J
		    0xe6, 0x66, 0x6c, 0x78, 0x6c, 0x66, 0xe6, 0x00, // K
		    0xf0, 0x60, 0x60, 0x60, 0x62, 0x66, 0xfe, 0x00, // L
		    0xc6, 0xee, 0xfe, 0xfe, 0xd6, 0xc6, 0xc6, 0x00, // M
		    0xc6, 0xe6, 0xf6, 0xde, 0xce, 0xc6, 0xc6, 0x00, // N
		    0x38, 0x6c, 0xc6, 0xc6, 0xc6, 0x6c, 0x38, 0x00, // O
		    0xfc, 0x66, 0x66, 0x7c, 0x60, 0x60, 0xf0, 0x00, // 0x50 P
		    0x7c, 0xc6, 0xc6, 0xc6, 0xd6, 0x7c, 0x0e, 0x00, // Q
		    0xfc, 0x66, 0x66, 0x7c, 0x6c, 0x66, 0xe6, 0x00, // R
		    0x7c, 0xc6, 0xe0, 0x78, 0x0e, 0xc6, 0x7c, 0x00, // S
		    0xfc, 0xb4, 0x30, 0x30, 0x30, 0x30, 0x78, 0x00, // T
		    0xcc, 0xcc, 0xcc, 0xcc, 0xcc, 0xcc, 0xfc, 0x00, // U
		    0xcc, 0xcc, 0xcc, 0xcc, 0xcc, 0x78, 0x30, 0x00, // V
		    0xc6, 0xc6, 0xc6, 0xc6, 0xd6, 0xfe, 0x6c, 0x00, // W
		    0xc6, 0xc6, 0x6c, 0x38, 0x6c, 0xc6, 0xc6, 0x00, // X
		    0xcc, 0xcc, 0xcc, 0x78, 0x30, 0x30, 0x78, 0x00, // Y
		    0xfe, 0xc6, 0x8c, 0x18, 0x32, 0x66, 0xfe, 0x00, // Z
		    0x78, 0x60, 0x60, 0x60, 0x60, 0x60, 0x78, 0x00,
		    0xc0, 0x60, 0x30, 0x18, 0x0c, 0x06, 0x02, 0x00,
		    0x78, 0x18, 0x18, 0x18, 0x18, 0x18, 0x78, 0x00,
		    0x10, 0x38, 0x6c, 0xc6, 0x00, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xff,
		    0x30, 0x30, 0x18, 0x00, 0x00, 0x00, 0x00, 0x00, // 0x60 
		    0x00, 0x00, 0x78, 0x0c, 0x7c, 0xcc, 0x76, 0x00, // 0x61 a
		    0xe0, 0x60, 0x60, 0x7c, 0x66, 0x66, 0xdc, 0x00, // b
		    0x00, 0x00, 0x78, 0xcc, 0xc0, 0xcc, 0x78, 0x00, // c
		    0x1c, 0x0c, 0x0c, 0x7c, 0xcc, 0xcc, 0x76, 0x00, // d
		    0x00, 0x00, 0x78, 0xcc, 0xfc, 0xc0, 0x78, 0x00, // e
		    0x38, 0x6c, 0x64, 0xf0, 0x60, 0x60, 0xf0, 0x00, // f
		    0x00, 0x00, 0x76, 0xcc, 0xcc, 0x7c, 0x0c, 0xf8, // g
		    0xe0, 0x60, 0x6c, 0x76, 0x66, 0x66, 0xe6, 0x00, // h
		    0x30, 0x00, 0x70, 0x30, 0x30, 0x30, 0x78, 0x00, // i
		    0x0c, 0x00, 0x1c, 0x0c, 0x0c, 0xcc, 0xcc, 0x78, // j
		    0xe0, 0x60, 0x66, 0x6c, 0x78, 0x6c, 0xe6, 0x00, // k
		    0x70, 0x30, 0x30, 0x30, 0x30, 0x30, 0x78, 0x00, // l
		    0x00, 0x00, 0xcc, 0xfe, 0xfe, 0xd6, 0xd6, 0x00, // m
		    0x00, 0x00, 0xb8, 0xcc, 0xcc, 0xcc, 0xcc, 0x00, // n
		    0x00, 0x00, 0x78, 0xcc, 0xcc, 0xcc, 0x78, 0x00, // o
		    0x00, 0x00, 0xdc, 0x66, 0x66, 0x7c, 0x60, 0xf0, // 0x70 p
		    0x00, 0x00, 0x76, 0xcc, 0xcc, 0x7c, 0x0c, 0x1e, // q
		    0x00, 0x00, 0xdc, 0x76, 0x62, 0x60, 0xf0, 0x00, // r
		    0x00, 0x00, 0x7c, 0xc0, 0x70, 0x1c, 0xf8, 0x00, // s 
		    0x10, 0x30, 0xfc, 0x30, 0x30, 0x34, 0x18, 0x00, // t
		    0x00, 0x00, 0xcc, 0xcc, 0xcc, 0xcc, 0x76, 0x00, // u
		    0x00, 0x00, 0xcc, 0xcc, 0xcc, 0x78, 0x30, 0x00, // v
		    0x00, 0x00, 0xc6, 0xc6, 0xd6, 0xfe, 0x6c, 0x00, // w
		    0x00, 0x00, 0xc6, 0x6c, 0x38, 0x6c, 0xc6, 0x00, // z
		    0x00, 0x00, 0xcc, 0xcc, 0xcc, 0x7c, 0x0c, 0xf8,
		    0x00, 0x00, 0xfc, 0x98, 0x30, 0x64, 0xfc, 0x00,
		    0x1c, 0x30, 0x30, 0xe0, 0x30, 0x30, 0x1c, 0x00,
		    0x18, 0x18, 0x18, 0x00, 0x18, 0x18, 0x18, 0x00,
		    0xe0, 0x30, 0x30, 0x1c, 0x30, 0x30, 0xe0, 0x00,
		    0x76, 0xdc, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		    0x00, 0x10, 0x38, 0x6c, 0xc6, 0xc6, 0xfe, 0x00,
		    0x7c, 0xc6, 0xc0, 0xc6, 0x7c, 0x0c, 0x06, 0x7c,
		    0x00, 0xcc, 0x00, 0xcc, 0xcc, 0xcc, 0x76, 0x00,
		    0x1c, 0x00, 0x78, 0xcc, 0xfc, 0xc0, 0x78, 0x00, //0x80
		    0x7e, 0x81, 0x3c, 0x06, 0x3e, 0x66, 0x3b, 0x00,
		    0xcc, 0x00, 0x78, 0x0c, 0x7c, 0xcc, 0x76, 0x00,
		    0xe0, 0x00, 0x78, 0x0c, 0x7c, 0xcc, 0x76, 0x00,
		    0x30, 0x30, 0x78, 0x0c, 0x7c, 0xcc, 0x76, 0x00,
		    0x00, 0x00, 0x7c, 0xc6, 0xc0, 0x78, 0x0c, 0x38,
		    0x7e, 0x81, 0x3c, 0x66, 0x7e, 0x60, 0x3c, 0x00,
		    0xcc, 0x00, 0x78, 0xcc, 0xfc, 0xc0, 0x78, 0x00,
		    0xe0, 0x00, 0x78, 0xcc, 0xfc, 0xc0, 0x78, 0x00,
		    0xcc, 0x00, 0x70, 0x30, 0x30, 0x30, 0x78, 0x00,
		    0x7c, 0x82, 0x38, 0x18, 0x18, 0x18, 0x3c, 0x00,
		    0xe0, 0x00, 0x70, 0x30, 0x30, 0x30, 0x78, 0x00,
		    0xc6, 0x10, 0x7c, 0xc6, 0xfe, 0xc6, 0xc6, 0x00,
		    0x30, 0x30, 0x00, 0x78, 0xcc, 0xfc, 0xcc, 0x00,
		    0x1c, 0x00, 0xfc, 0x60, 0x78, 0x60, 0xfc, 0x00,
		    0x00, 0x00, 0x7f, 0x0c, 0x7f, 0xcc, 0x7f, 0x00,
		    0x3e, 0x6c, 0xcc, 0xfe, 0xcc, 0xcc, 0xce, 0x00, //0x90
		    0x78, 0x84, 0x00, 0x78, 0xcc, 0xcc, 0x78, 0x00,
		    0x00, 0xcc, 0x00, 0x78, 0xcc, 0xcc, 0x78, 0x00,
		    0x00, 0xe0, 0x00, 0x78, 0xcc, 0xcc, 0x78, 0x00,
		    0x78, 0x84, 0x00, 0xcc, 0xcc, 0xcc, 0x76, 0x00,
		    0x00, 0xe0, 0x00, 0xcc, 0xcc, 0xcc, 0x76, 0x00,
		    0x00, 0xcc, 0x00, 0xcc, 0xcc, 0x7c, 0x0c, 0xf8,
		    0xc3, 0x18, 0x3c, 0x66, 0x66, 0x3c, 0x18, 0x00,
		    0xcc, 0x00, 0xcc, 0xcc, 0xcc, 0xcc, 0x78, 0x00,
		    0x18, 0x18, 0x7e, 0xc0, 0xc0, 0x7e, 0x18, 0x18,
		    0x38, 0x6c, 0x64, 0xf0, 0x60, 0xe6, 0xfc, 0x00,
		    0xcc, 0xcc, 0x78, 0x30, 0xfc, 0x30, 0xfc, 0x30,
		    0xf8, 0xcc, 0xcc, 0xfa, 0xc6, 0xcf, 0xc6, 0xc3,
		    0x0e, 0x1b, 0x18, 0x3c, 0x18, 0x18, 0xd8, 0x70,
		    0x1c, 0x00, 0x78, 0x0c, 0x7c, 0xcc, 0x76, 0x00,
		    0x38, 0x00, 0x70, 0x30, 0x30, 0x30, 0x78, 0x00,
		    0x00, 0x1c, 0x00, 0x78, 0xcc, 0xcc, 0x78, 0x00, //0xA0
		    0x00, 0x1c, 0x00, 0xcc, 0xcc, 0xcc, 0x76, 0x00,
		    0x00, 0xf8, 0x00, 0xb8, 0xcc, 0xcc, 0xcc, 0x00,
		    0xfc, 0x00, 0xcc, 0xec, 0xfc, 0xdc, 0xcc, 0x00,
		    0x3c, 0x6c, 0x6c, 0x3e, 0x00, 0x7e, 0x00, 0x00,
		    0x38, 0x6c, 0x6c, 0x38, 0x00, 0x7c, 0x00, 0x00,
		    0x18, 0x00, 0x18, 0x18, 0x30, 0x66, 0x3c, 0x00,
		    0x00, 0x00, 0x00, 0xfc, 0xc0, 0xc0, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0xfc, 0x0c, 0x0c, 0x00, 0x00,
		    0xc6, 0xcc, 0xd8, 0x36, 0x6b, 0xc2, 0x84, 0x0f,
		    0xc3, 0xc6, 0xcc, 0xdb, 0x37, 0x6d, 0xcf, 0x03,
		    0x18, 0x00, 0x18, 0x18, 0x3c, 0x3c, 0x18, 0x00,
		    0x00, 0x33, 0x66, 0xcc, 0x66, 0x33, 0x00, 0x00,
		    0x00, 0xcc, 0x66, 0x33, 0x66, 0xcc, 0x00, 0x00,
		    0x22, 0x88, 0x22, 0x88, 0x22, 0x88, 0x22, 0x88,
		    0x55, 0xaa, 0x55, 0xaa, 0x55, 0xaa, 0x55, 0xaa,
		    0xdb, 0xf6, 0xdb, 0x6f, 0xdb, 0x7e, 0xd7, 0xed, //0xB0
		    0x18, 0x18, 0x18, 0x18, 0x18, 0x18, 0x18, 0x18,
		    0x18, 0x18, 0x18, 0x18, 0xf8, 0x18, 0x18, 0x18,
		    0x18, 0x18, 0xf8, 0x18, 0xf8, 0x18, 0x18, 0x18,
		    0x36, 0x36, 0x36, 0x36, 0xf6, 0x36, 0x36, 0x36,
		    0x00, 0x00, 0x00, 0x00, 0xfe, 0x36, 0x36, 0x36,
		    0x00, 0x00, 0xf8, 0x18, 0xf8, 0x18, 0x18, 0x18,
		    0x36, 0x36, 0xf6, 0x06, 0xf6, 0x36, 0x36, 0x36,
		    0x36, 0x36, 0x36, 0x36, 0x36, 0x36, 0x36, 0x36,
		    0x00, 0x00, 0xfe, 0x06, 0xf6, 0x36, 0x36, 0x36,
		    0x36, 0x36, 0xf6, 0x06, 0xfe, 0x00, 0x00, 0x00,
		    0x36, 0x36, 0x36, 0x36, 0xfe, 0x00, 0x00, 0x00,
		    0x18, 0x18, 0xf8, 0x18, 0xf8, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0xf8, 0x18, 0x18, 0x18,
		    0x18, 0x18, 0x18, 0x18, 0x1f, 0x00, 0x00, 0x00,
		    0x18, 0x18, 0x18, 0x18, 0xff, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0xff, 0x18, 0x18, 0x18, //0xC0
		    0x18, 0x18, 0x18, 0x18, 0x1f, 0x18, 0x18, 0x18,
		    0x00, 0x00, 0x00, 0x00, 0xff, 0x00, 0x00, 0x00,
		    0x18, 0x18, 0x18, 0x18, 0xff, 0x18, 0x18, 0x18,
		    0x18, 0x18, 0x1f, 0x18, 0x1f, 0x18, 0x18, 0x18,
		    0x36, 0x36, 0x36, 0x36, 0x37, 0x36, 0x36, 0x36,
		    0x36, 0x36, 0x37, 0x30, 0x3f, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x3f, 0x30, 0x37, 0x36, 0x36, 0x36,
		    0x36, 0x36, 0xf7, 0x00, 0xff, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0xff, 0x00, 0xf7, 0x36, 0x36, 0x36,
		    0x36, 0x36, 0x37, 0x30, 0x37, 0x36, 0x36, 0x36,
		    0x00, 0x00, 0xff, 0x00, 0xff, 0x00, 0x00, 0x00,
		    0x36, 0x36, 0xf7, 0x00, 0xf7, 0x36, 0x36, 0x36,
		    0x18, 0x18, 0xff, 0x00, 0xff, 0x00, 0x00, 0x00,
		    0x36, 0x36, 0x36, 0x36, 0xff, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0xff, 0x00, 0xff, 0x18, 0x18, 0x18,
		    0x00, 0x00, 0x00, 0x00, 0xff, 0x36, 0x36, 0x36, //0xD0
		    0x36, 0x36, 0x36, 0x36, 0x3f, 0x00, 0x00, 0x00,
		    0x18, 0x18, 0x1f, 0x18, 0x1f, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x1f, 0x18, 0x1f, 0x18, 0x18, 0x18,
		    0x00, 0x00, 0x00, 0x00, 0x3f, 0x36, 0x36, 0x36,
		    0x36, 0x36, 0x36, 0x36, 0xff, 0x36, 0x36, 0x36,
		    0x18, 0x18, 0xff, 0x18, 0xff, 0x18, 0x18, 0x18,
		    0x18, 0x18, 0x18, 0x18, 0xf8, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0x1f, 0x18, 0x18, 0x18,
		    0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff,
		    0x00, 0x00, 0x00, 0x00, 0xff, 0xff, 0xff, 0xff,
		    0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0,
		    0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f,
		    0xff, 0xff, 0xff, 0xff, 0x00, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x76, 0xdc, 0xc8, 0xdc, 0x76, 0x00,
		    0x00, 0x78, 0xcc, 0xf8, 0xcc, 0xf8, 0xc0, 0xc0,
		    0x00, 0xfc, 0xcc, 0xc0, 0xc0, 0xc0, 0xc0, 0x00, // 0xE0
		    0x00, 0x00, 0xfe, 0x6c, 0x6c, 0x6c, 0x6c, 0x00,
		    0xfc, 0xcc, 0x60, 0x30, 0x60, 0xcc, 0xfc, 0x00,
		    0x00, 0x00, 0x7e, 0xd8, 0xd8, 0xd8, 0x70, 0x00,
		    0x00, 0x66, 0x66, 0x66, 0x66, 0x7c, 0x60, 0xc0,
		    0x00, 0x76, 0xdc, 0x18, 0x18, 0x18, 0x18, 0x00,
		    0xfc, 0x30, 0x78, 0xcc, 0xcc, 0x78, 0x30, 0xfc,
		    0x38, 0x6c, 0xc6, 0xfe, 0xc6, 0x6c, 0x38, 0x00,
		    0x38, 0x6c, 0xc6, 0xc6, 0x6c, 0x6c, 0xee, 0x00,
		    0x1c, 0x30, 0x18, 0x7c, 0xcc, 0xcc, 0x78, 0x00,
		    0x00, 0x00, 0x7e, 0xdb, 0xdb, 0x7e, 0x00, 0x00,
		    0x06, 0x0c, 0x7e, 0xdb, 0xdb, 0x7e, 0x60, 0xc0,
		    0x38, 0x60, 0xc0, 0xf8, 0xc0, 0x60, 0x38, 0x00,
		    0x78, 0xcc, 0xcc, 0xcc, 0xcc, 0xcc, 0xcc, 0x00,
		    0x00, 0x7e, 0x00, 0x7e, 0x00, 0x7e, 0x00, 0x00,
		    0x18, 0x18, 0x7e, 0x18, 0x18, 0x00, 0x7e, 0x00,
		    0x60, 0x30, 0x18, 0x30, 0x60, 0x00, 0xfc, 0x00, // 0xF0
		    0x18, 0x30, 0x60, 0x30, 0x18, 0x00, 0xfc, 0x00,
		    0x0e, 0x1b, 0x1b, 0x18, 0x18, 0x18, 0x18, 0x18,
		    0x18, 0x18, 0x18, 0x18, 0x18, 0xd8, 0xd8, 0x70,
		    0x18, 0x18, 0x00, 0x7e, 0x00, 0x18, 0x18, 0x00,
		    0x00, 0x76, 0xdc, 0x00, 0x76, 0xdc, 0x00, 0x00,
		    0x38, 0x6c, 0x6c, 0x38, 0x00, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x18, 0x18, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0x18, 0x00, 0x00, 0x00,
		    0x0f, 0x0c, 0x0c, 0x0c, 0xec, 0x6c, 0x3c, 0x1c,
		    0x58, 0x6c, 0x6c, 0x6c, 0x6c, 0x00, 0x00, 0x00,
		    0x70, 0x98, 0x30, 0x60, 0xf8, 0x00, 0x00, 0x00,
		    0x00, 0x00, 0x3c, 0x3c, 0x3c, 0x3c, 0x00, 0x00,
		    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
   };		   

   /*
		    0x00, 0x00, 0x00, 0x00, 0x00,  // 0x00 null
		   	0x3E, 0x5B, 0x4F, 0x5B, 0x3E,
		   	0x3E, 0x6B, 0x4F, 0x6B, 0x3E,
		   	0x1C, 0x3E, 0x7C, 0x3E, 0x1C,
		   	0x18, 0x3C, 0x7E, 0x3C, 0x18,
		   	0x1C, 0x57, 0x7D, 0x57, 0x1C,
		   	0x1C, 0x5E, 0x7F, 0x5E, 0x1C,
		   	0x00, 0x18, 0x3C, 0x18, 0x00,
		   	0xFF, 0xE7, 0xC3, 0xE7, 0xFF,
		   	0x00, 0x18, 0x24, 0x18, 0x00,
		   	0xFF, 0xE7, 0xDB, 0xE7, 0xFF,
		   	0x30, 0x48, 0x3A, 0x06, 0x0E,
		   	0x26, 0x29, 0x79, 0x29, 0x26,
		   	0x40, 0x7F, 0x05, 0x05, 0x07,
		   	0x40, 0x7F, 0x05, 0x25, 0x3F,
		   	0x5A, 0x3C, 0xE7, 0x3C, 0x5A,
		   	0x7F, 0x3E, 0x1C, 0x1C, 0x08, //0x10
		   	0x08, 0x1C, 0x1C, 0x3E, 0x7F,
		   	0x14, 0x22, 0x7F, 0x22, 0x14,
		   	0x5F, 0x5F, 0x00, 0x5F, 0x5F,
		   	0x06, 0x09, 0x7F, 0x01, 0x7F,
		   	0x00, 0x66, 0x89, 0x95, 0x6A,
		   	0x60, 0x60, 0x60, 0x60, 0x60,
		   	0x94, 0xA2, 0xFF, 0xA2, 0x94,
		   	0x08, 0x04, 0x7E, 0x04, 0x08,
		   	0x10, 0x20, 0x7E, 0x20, 0x10,
		   	0x08, 0x08, 0x2A, 0x1C, 0x08,
		   	0x08, 0x1C, 0x2A, 0x08, 0x08,
		   	0x1E, 0x10, 0x10, 0x10, 0x10,
		   	0x0C, 0x1E, 0x0C, 0x1E, 0x0C,
		   	0x30, 0x38, 0x3E, 0x38, 0x30,
		   	0x06, 0x0E, 0x3E, 0x0E, 0x06,
		   	0x00, 0x00, 0x00, 0x00, 0x00, // 0x20 ... space
		   	0x00, 0x00, 0x5F, 0x00, 0x00,
		   	0x00, 0x07, 0x00, 0x07, 0x00,
		   	0x14, 0x7F, 0x14, 0x7F, 0x14,
		   	0x24, 0x2A, 0x7F, 0x2A, 0x12,
		   	0x23, 0x13, 0x08, 0x64, 0x62,
		   	0x36, 0x49, 0x56, 0x20, 0x50,
		   	0x00, 0x08, 0x07, 0x03, 0x00,
		   	0x00, 0x1C, 0x22, 0x41, 0x00,
		   	0x00, 0x41, 0x22, 0x1C, 0x00,
		   	0x2A, 0x1C, 0x7F, 0x1C, 0x2A,
		   	0x08, 0x08, 0x3E, 0x08, 0x08,
		   	0x00, 0x80, 0x70, 0x30, 0x00,
		   	0x08, 0x08, 0x08, 0x08, 0x08,
		   	0x00, 0x00, 0x60, 0x60, 0x00,
		   	0x20, 0x10, 0x08, 0x04, 0x02,
		   	0x3E, 0x51, 0x49, 0x45, 0x3E, // 0x30 0
		   	0x00, 0x42, 0x7F, 0x40, 0x00, // 1
		   	0x72, 0x49, 0x49, 0x49, 0x46, // 2
		   	0x21, 0x41, 0x49, 0x4D, 0x33, // 3
		   	0x18, 0x14, 0x12, 0x7F, 0x10, // 4
		   	0x27, 0x45, 0x45, 0x45, 0x39, // 5
		   	0x3C, 0x4A, 0x49, 0x49, 0x31, // 6
		   	0x41, 0x21, 0x11, 0x09, 0x07, // 7
		   	0x36, 0x49, 0x49, 0x49, 0x36, // 8
		   	0x46, 0x49, 0x49, 0x29, 0x1E, // 9
		   	0x00, 0x00, 0x14, 0x00, 0x00, // :
		   	0x00, 0x40, 0x34, 0x00, 0x00,
		   	0x00, 0x08, 0x14, 0x22, 0x41,
		   	0x14, 0x14, 0x14, 0x14, 0x14,
		   	0x00, 0x41, 0x22, 0x14, 0x08,
		   	0x02, 0x01, 0x59, 0x09, 0x06,
		   	0x3E, 0x41, 0x5D, 0x59, 0x4E, // 0x40 @
		   	0x7C, 0x12, 0x11, 0x12, 0x7C, // 0x41 A ...
		   	0x7F, 0x49, 0x49, 0x49, 0x36, // 0x42 B
		   	0x3E, 0x41, 0x41, 0x41, 0x22, // C
		   	0x7F, 0x41, 0x41, 0x41, 0x3E, // D
		   	0x7F, 0x49, 0x49, 0x49, 0x41, // E
		   	0x7F, 0x09, 0x09, 0x09, 0x01, // F
		   	0x3E, 0x41, 0x41, 0x51, 0x73, // G
		   	0x7F, 0x08, 0x08, 0x08, 0x7F, // H
		   	0x00, 0x41, 0x7F, 0x41, 0x00, // I
		   	0x20, 0x40, 0x41, 0x3F, 0x01, // J
		   	0x7F, 0x08, 0x14, 0x22, 0x41, // K
		   	0x7F, 0x40, 0x40, 0x40, 0x40, // L
		   	0x7F, 0x02, 0x1C, 0x02, 0x7F, // M
		   	0x7F, 0x04, 0x08, 0x10, 0x7F, // N
		   	0x3E, 0x41, 0x41, 0x41, 0x3E, // O
		   	0x7F, 0x09, 0x09, 0x09, 0x06, // 0x50 P
		   	0x3E, 0x41, 0x51, 0x21, 0x5E, // 0x51 Q
		   	0x7F, 0x09, 0x19, 0x29, 0x46, // R
		   	0x26, 0x49, 0x49, 0x49, 0x32, // S
		   	0x03, 0x01, 0x7F, 0x01, 0x03, // T
		   	0x3F, 0x40, 0x40, 0x40, 0x3F, // U
		   	0x1F, 0x20, 0x40, 0x20, 0x1F, // V
		   	0x3F, 0x40, 0x38, 0x40, 0x3F, // W
		   	0x63, 0x14, 0x08, 0x14, 0x63, // X
		   	0x03, 0x04, 0x78, 0x04, 0x03, // Y
		   	0x61, 0x59, 0x49, 0x4D, 0x43, // Z
		   	0x00, 0x7F, 0x41, 0x41, 0x41,
		   	0x02, 0x04, 0x08, 0x10, 0x20,
		   	0x00, 0x41, 0x41, 0x41, 0x7F,
		   	0x04, 0x02, 0x01, 0x02, 0x04,
		   	0x40, 0x40, 0x40, 0x40, 0x40,
		   	0x00, 0x03, 0x07, 0x08, 0x00, // 0x60 
		   	0x20, 0x54, 0x54, 0x78, 0x40, // 0x61 a
		   	0x7F, 0x28, 0x44, 0x44, 0x38, // b
		   	0x38, 0x44, 0x44, 0x44, 0x28, // c
		   	0x38, 0x44, 0x44, 0x28, 0x7F, // d
		   	0x38, 0x54, 0x54, 0x54, 0x18, // e
		   	0x00, 0x08, 0x7E, 0x09, 0x02, // f
		   	0x18, 0xA4, 0xA4, 0x9C, 0x78, // g
		   	0x7F, 0x08, 0x04, 0x04, 0x78, // h
		   	0x00, 0x44, 0x7D, 0x40, 0x00, // i
		   	
		   	0x20, 0x40, 0x40, 0x3D, 0x00, // j
		   	0x7F, 0x10, 0x28, 0x44, 0x00, // k
		   	0x00, 0x41, 0x7F, 0x40, 0x00, // l
		   	0x7C, 0x04, 0x78, 0x04, 0x78, // m
		   	0x7C, 0x08, 0x04, 0x04, 0x78, // n
		   	0x38, 0x44, 0x44, 0x44, 0x38, // o
		   	0xFC, 0x18, 0x24, 0x24, 0x18, // p
		   	0x18, 0x24, 0x24, 0x18, 0xFC, // q
		   	0x7C, 0x08, 0x04, 0x04, 0x08, // r
		   	0x48, 0x54, 0x54, 0x54, 0x24,// 0x70
		   	0x04, 0x04, 0x3F, 0x44, 0x24,
		   	0x3C, 0x40, 0x40, 0x20, 0x7C,
		   	0x1C, 0x20, 0x40, 0x20, 0x1C,
		   	0x3C, 0x40, 0x30, 0x40, 0x3C,
		   	0x44, 0x28, 0x10, 0x28, 0x44,
		   	0x4C, 0x90, 0x90, 0x90, 0x7C,
		   	0x44, 0x64, 0x54, 0x4C, 0x44,
		   	0x00, 0x08, 0x36, 0x41, 0x00,
		   	0x00, 0x00, 0x77, 0x00, 0x00,
		   	0x00, 0x41, 0x36, 0x08, 0x00,
		   	0x02, 0x01, 0x02, 0x04, 0x02,
		   	0x3C, 0x26, 0x23, 0x26, 0x3C,
		   	0x1E, 0xA1, 0xA1, 0x61, 0x12,
		   	0x3A, 0x40, 0x40, 0x20, 0x7A,
		   	0x38, 0x54, 0x54, 0x55, 0x59,
		   	0x21, 0x55, 0x55, 0x79, 0x41,
		   	0x21, 0x54, 0x54, 0x78, 0x41, // 0x80
		   	0x21, 0x55, 0x54, 0x78, 0x40,
		   	0x20, 0x54, 0x55, 0x79, 0x40,
		   	0x0C, 0x1E, 0x52, 0x72, 0x12,
		   	0x39, 0x55, 0x55, 0x55, 0x59,
		   	0x39, 0x54, 0x54, 0x54, 0x59,
		   	0x39, 0x55, 0x54, 0x54, 0x58,
		   	0x00, 0x00, 0x45, 0x7C, 0x41,
		   	0x00, 0x02, 0x45, 0x7D, 0x42,
		   	0x00, 0x01, 0x45, 0x7C, 0x40,
		   	0xF0, 0x29, 0x24, 0x29, 0xF0,
		   	0xF0, 0x28, 0x25, 0x28, 0xF0,
		   	0x7C, 0x54, 0x55, 0x45, 0x00,
		   	0x20, 0x54, 0x54, 0x7C, 0x54,
		   	0x7C, 0x0A, 0x09, 0x7F, 0x49,
		   	0x32, 0x49, 0x49, 0x49, 0x32,
		   	0x32, 0x48, 0x48, 0x48, 0x32,
		   	0x32, 0x4A, 0x48, 0x48, 0x30, // 0x90
		   	0x3A, 0x41, 0x41, 0x21, 0x7A,
		   	0x3A, 0x42, 0x40, 0x20, 0x78,
		   	0x00, 0x9D, 0xA0, 0xA0, 0x7D,
		   	0x39, 0x44, 0x44, 0x44, 0x39,
		   	0x3D, 0x40, 0x40, 0x40, 0x3D,
		   	0x3C, 0x24, 0xFF, 0x24, 0x24,
		   	0x48, 0x7E, 0x49, 0x43, 0x66,
		   	0x2B, 0x2F, 0xFC, 0x2F, 0x2B,
		   	0xFF, 0x09, 0x29, 0xF6, 0x20,
		   	0xC0, 0x88, 0x7E, 0x09, 0x03,
		   	0x20, 0x54, 0x54, 0x79, 0x41,
		   	0x00, 0x00, 0x44, 0x7D, 0x41,
		   	0x30, 0x48, 0x48, 0x4A, 0x32,
		   	0x38, 0x40, 0x40, 0x22, 0x7A,
		   	0x00, 0x7A, 0x0A, 0x0A, 0x72,
		   	0x7D, 0x0D, 0x19, 0x31, 0x7D,
		   	0x26, 0x29, 0x29, 0x2F, 0x28, // 0xA0
		   	0x26, 0x29, 0x29, 0x29, 0x26,
		   	0x30, 0x48, 0x4D, 0x40, 0x20,
		   	0x38, 0x08, 0x08, 0x08, 0x08,
		   	0x08, 0x08, 0x08, 0x08, 0x38,
		   	0x2F, 0x10, 0xC8, 0xAC, 0xBA,
		   	0x2F, 0x10, 0x28, 0x34, 0xFA,
		   	0x00, 0x00, 0x7B, 0x00, 0x00,
		   	0x08, 0x14, 0x2A, 0x14, 0x22,
		   	0x22, 0x14, 0x2A, 0x14, 0x08,
		   	0xAA, 0x00, 0x55, 0x00, 0xAA,
		   	0xAA, 0x55, 0xAA, 0x55, 0xAA,
		   	0x00, 0x00, 0x00, 0xFF, 0x00,
		   	0x10, 0x10, 0x10, 0xFF, 0x00,
		   	0x14, 0x14, 0x14, 0xFF, 0x00,
		   	0x10, 0x10, 0xFF, 0x00, 0xFF,
		   	0x10, 0x10, 0xF0, 0x10, 0xF0,
		   	0x14, 0x14, 0x14, 0xFC, 0x00, // 0xB0
		   	0x14, 0x14, 0xF7, 0x00, 0xFF,
		   	0x00, 0x00, 0xFF, 0x00, 0xFF,
		   	0x14, 0x14, 0xF4, 0x04, 0xFC,
		   	0x14, 0x14, 0x17, 0x10, 0x1F,
		   	0x10, 0x10, 0x1F, 0x10, 0x1F,
		   	0x14, 0x14, 0x14, 0x1F, 0x00,
		   	0x10, 0x10, 0x10, 0xF0, 0x00,
		   	0x00, 0x00, 0x00, 0x1F, 0x10,
		   	0x10, 0x10, 0x10, 0x1F, 0x10,
		   	0x10, 0x10, 0x10, 0xF0, 0x10,
		   	0x00, 0x00, 0x00, 0xFF, 0x10,
		   	0x10, 0x10, 0x10, 0x10, 0x10,
		   	0x10, 0x10, 0x10, 0xFF, 0x10,
		   	0x00, 0x00, 0x00, 0xFF, 0x14,
		   	0x00, 0x00, 0xFF, 0x00, 0xFF,
		   	0x00, 0x00, 0x1F, 0x10, 0x17,
		   	0x00, 0x00, 0xFC, 0x04, 0xF4, // 0xC0
		   	0x14, 0x14, 0x17, 0x10, 0x17,
		   	0x14, 0x14, 0xF4, 0x04, 0xF4,
		   	0x00, 0x00, 0xFF, 0x00, 0xF7,
		   	0x14, 0x14, 0x14, 0x14, 0x14,
		   	0x14, 0x14, 0xF7, 0x00, 0xF7,
		   	0x14, 0x14, 0x14, 0x17, 0x14,
		   	0x10, 0x10, 0x1F, 0x10, 0x1F,
		   	0x14, 0x14, 0x14, 0xF4, 0x14,
		   	0x10, 0x10, 0xF0, 0x10, 0xF0,
		   	0x00, 0x00, 0x1F, 0x10, 0x1F,
		   	0x00, 0x00, 0x00, 0x1F, 0x14,
		   	0x00, 0x00, 0x00, 0xFC, 0x14,
		   	0x00, 0x00, 0xF0, 0x10, 0xF0,
		   	0x10, 0x10, 0xFF, 0x10, 0xFF,
		   	0x14, 0x14, 0x14, 0xFF, 0x14,
		   	0x10, 0x10, 0x10, 0x1F, 0x00, // 0xD0
		   	0x00, 0x00, 0x00, 0xF0, 0x10,
		   	0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
		   	0xF0, 0xF0, 0xF0, 0xF0, 0xF0,
		   	0xFF, 0xFF, 0xFF, 0x00, 0x00,
		   	0x00, 0x00, 0x00, 0xFF, 0xFF,
		   	0x0F, 0x0F, 0x0F, 0x0F, 0x0F,
		   	0x38, 0x44, 0x44, 0x38, 0x44,
		   	0x7C, 0x2A, 0x2A, 0x3E, 0x14,
		   	0x7E, 0x02, 0x02, 0x06, 0x06,
		   	0x02, 0x7E, 0x02, 0x7E, 0x02,
		   	0x63, 0x55, 0x49, 0x41, 0x63,
		   	0x38, 0x44, 0x44, 0x3C, 0x04,
		   	0x40, 0x7E, 0x20, 0x1E, 0x20,
		   	0x06, 0x02, 0x7E, 0x02, 0x02,
		   	0x99, 0xA5, 0xE7, 0xA5, 0x99,
		   	0x1C, 0x2A, 0x49, 0x2A, 0x1C, // 0xE0
		   	0x4C, 0x72, 0x01, 0x72, 0x4C,
		   	0x30, 0x4A, 0x4D, 0x4D, 0x30,
		   	0x30, 0x48, 0x78, 0x48, 0x30,
		   	0xBC, 0x62, 0x5A, 0x46, 0x3D,
		   	0x3E, 0x49, 0x49, 0x49, 0x00,
		   	0x7E, 0x01, 0x01, 0x01, 0x7E,
		   	0x2A, 0x2A, 0x2A, 0x2A, 0x2A,
		   	0x44, 0x44, 0x5F, 0x44, 0x44,
		   	0x40, 0x51, 0x4A, 0x44, 0x40,
		   	0x40, 0x44, 0x4A, 0x51, 0x40,
		   	0x00, 0x00, 0xFF, 0x01, 0x03,
		   	0xE0, 0x80, 0xFF, 0x00, 0x00,
		   	0x08, 0x08, 0x6B, 0x6B, 0x08,
		   	0x36, 0x12, 0x36, 0x24, 0x36,
		   	0x06, 0x0F, 0x09, 0x0F, 0x06,
		   	0x00, 0x00, 0x18, 0x18, 0x00, // 0xF0
		   	0x00, 0x00, 0x10, 0x10, 0x00,
		   	0x30, 0x40, 0xFF, 0x01, 0x01,
		   	0x00, 0x1F, 0x01, 0x01, 0x1E,
		   	0x00, 0x19, 0x1D, 0x17, 0x12,
		   	0x00, 0x3C, 0x3C, 0x3C, 0x3C,
		   	0x00, 0x00, 0x00, 0x00, 0x00
		   };
        */   
   public byte[] getAsciiFont(int c){
	// Standard ASCII 5x7 font
	// Draw a character
	  byte[] rtn=new byte[16];
	  int offset=6;
	  for(int i=0;i<8;i++){
		  rtn[offset+i]=asciiFont[8*c+i];
	  }
	  return rtn;
   }
   public byte[] getFontRaw(long pos){ // font file position
//	   System.out.println("pos="+Integer.toHexString((int)pos).toUpperCase());
//	   long x;
	   if(fontArray==null) return null;
	   byte[] rtn=new byte[oneFontSize*fontWidthByte];
	   if(pos+oneFontSize*fontWidthByte>fontArray.length){
		   System.out.println("exceeded the max font range.");
		   return null;
	   }
	   for(int i=0;i<oneFontSize*fontWidthByte;i++){
		   rtn[i]=fontArray[(int)(pos+i)];
	   }
	   return rtn;
   }
   
   public void printFont(byte[] f){
	   if(f==null) return;
	   int w=oneFontSize/8;
	   int fsize=f.length;
	   if(fsize<=16) w=1;
	   for(int i=0;i<oneFontSize;i++){
		   for(int j=0;j<w;j++){
			   int l=f[i*w+j];
			   int mask=0x0080;
			   for(int k=0;k<8;k++){
				   if((mask & l)!=0)
					   System.out.print("*");
				   else
					   System.out.print(" ");
				   mask=mask>>1;
			   }
		   }
		   System.out.println("");
	   }
	   System.out.println("");
   }
}
