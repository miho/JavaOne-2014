package eu.mihosoft.vrl.visolines;

public class Data_double {
    private double[] data;
    private int width;
    private int height;

    private static Data_float fData;
     private static Data_float dData;

    public Data_double() {
           }

    public Data_double(int width, int height) {
       setData(new double[width*height],width,height);
    }

    public Data_double(double[] data, int width, int height) {
       setData(data,width,height);
    }
    
    public final void setData(double[] data, int width, int height) {
        this.data = data;
        if (data.length != width*height) {
            throw new IllegalArgumentException("Data argument, width and height do not match. Data.length!=width*height.");
        }
        this.width = width;
        this.height = height;
    }
    
    public double[] getData() {
        return this.data;
    }
    
    public int getWidth() {return width;}
    public int getHeight(){return height;}

    public double get(int x, int y) {
        return data[y*width+x];
    }

    public void set(double value, int x, int y) {
        data[y*width+x] = value;
    }

    public void plus(double value, int x, int y) {
        data[y*width+x] += value;
    }

    public void minus(double value, int x, int y) {
        data[y*width+x] -= value;
    }

    public void times(double value, int x, int y) {
        data[y*width+x] *= value;
    }

    public void divide(double value, int x, int y) {
        data[y*width+x] /= value;
    }

    public boolean isEmpty() {
        return data.length==0;
    }


}
