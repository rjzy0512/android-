package com.example.car43;

public class character {
	public String X,Y,Z;
	char yunsuan ;
	public character(String s){
		char[] chars=s.toCharArray();
		char[] x=new char[500];
		char[] y=new char[500];
		
		int biaoji=0;
		int xx=0,yy=0;
		for(int i=0;i<chars.length;i++){
			if(chars[i]>='0'&&chars[i]<='9'&&biaoji==0) x[xx++]=chars[i];
			else if(biaoji==0) {
				yunsuan=chars[i];
				biaoji=1;
				continue;
			}
			if(chars[i]>='0'&&chars[i]<='9'&&biaoji==1) y[yy++]=chars[i];
		}
		this.X=String.valueOf(x);
		this.Y=String.valueOf(y);
		this.X = this.X.substring(0,xx);
		this.Y = this.Y.substring(0,yy);
	}
	
	public String getX(){
		return this.X;
	}
	
	public String getY(){
		return this.Y;
	}
	public String getYunsuan(){
		return String.valueOf(this.yunsuan);
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		character ch=new character("12345678901234567890123456+98765432109876543210987654");
//		System.out.println("X="+ch.getX());
//		System.out.println("Y="+ch.getY());
//		System.out.println("杩愮畻锛�"+ch.getYunsuan());
//	}

}
