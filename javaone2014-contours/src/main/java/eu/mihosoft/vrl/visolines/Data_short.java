package eu.mihosoft.vrl.visolines;

public class Data_short {
    private short[] data;
    private int width;
    private int height;

    public Data_short() {
           }

    public Data_short(short[] data, int width, int height) {
       setData(data,width,height);
    }
    
    public final void setData(short[] data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }
    
    public short[] getData() {
        return this.data;
    }
    
    public int getWidth() {return width;}
    public int getHeight(){return height;}
}
