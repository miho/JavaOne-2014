package eu.mihosoft.vrl.visolines;

public class Data_long {
    private long[] data;
    private int width;
    private int height;

    public Data_long() {
           }

    public Data_long(long[] data, int width, int height) {
       setData(data,width,height);
    }
    
    public final void setData(long[] data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }
    
    public long[] getData() {
        return this.data;
    }
    
    public int getWidth() {return width;}
    public int getHeight(){return height;}
}
