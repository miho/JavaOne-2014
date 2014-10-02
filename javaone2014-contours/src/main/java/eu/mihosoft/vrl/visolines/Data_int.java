package eu.mihosoft.vrl.visolines;

public class Data_int {
    private int[] data;
    private int width;
    private int height;

    private static Data_float fData;
     private static Data_float dData;

    public Data_int() {
           }

    public Data_int(int width, int height) {
       setData(new int[width*height],width,height);
    }

    public Data_int(int[] data, int width, int height) {
       setData(data,width,height);
    }
    
    public final void setData(int[] data, int width, int height) {
        this.data = data;
        if (data.length != width*height) {
            throw new IllegalArgumentException("Data argument, width and height do not match. Data.length!=width*height.");
        }
        this.width = width;
        this.height = height;
    }
    
    public int[] getData() {
        return this.data;
    }
    
    public int getWidth() {return width;}
    public int getHeight(){return height;}

    public int get(int x, int y) {
        return data[y*width+x];
    }

    public void set(int value, int x, int y) {
        data[y*width+x] = value;
    }

    public void plus(int value, int x, int y) {
        data[y*width+x] += value;
    }

    public void minus(int value, int x, int y) {
        data[y*width+x] -= value;
    }

    public void times(int value, int x, int y) {
        data[y*width+x] *= value;
    }

    public void divide(int value, int x, int y) {
        data[y*width+x] /= value;
    }

    public boolean isEmpty() {
        return data.length==0;
    }


}
