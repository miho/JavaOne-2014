package eu.mihosoft.vrl.visolines;

public class Data_float {
    private float[] data;
    private int width;
    private int height;

    private static Data_float fData;
     private static Data_float dData;

    public Data_float() {
           }

    public Data_float(int width, int height) {
       setData(new float[width*height],width,height);
    }

    public Data_float(float[] data, int width, int height) {
       setData(data,width,height);
    }
    
    public final void setData(float[] data, int width, int height) {
        this.data = data;
        if (data.length != width*height) {
            throw new IllegalArgumentException("Data argument, width and height do not match. Data.length!=width*height.");
        }
        this.width = width;
        this.height = height;
    }
    
    public float[] getData() {
        return this.data;
    }
    
    public int getWidth() {return width;}
    public int getHeight(){return height;}

    public float get(int x, int y) {
        return data[y*width+x];
    }

    public void set(float value, int x, int y) {
        data[y*width+x] = value;
    }

    public void plus(float value, int x, int y) {
        data[y*width+x] += value;
    }

    public void minus(float value, int x, int y) {
        data[y*width+x] -= value;
    }

    public void times(float value, int x, int y) {
        data[y*width+x] *= value;
    }

    public void divide(float value, int x, int y) {
        data[y*width+x] /= value;
    }

    public boolean isEmpty() {
        return data.length==0;
    }


}
