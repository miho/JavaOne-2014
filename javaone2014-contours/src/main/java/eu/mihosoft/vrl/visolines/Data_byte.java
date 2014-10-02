package eu.mihosoft.vrl.visolines;

public class Data_byte {
    private byte[] data;
    private int width;
    private int height;

    private static Data_float fData;
     private static Data_float dData;

    public Data_byte() {
           }

    public Data_byte(int width, int height) {
       setData(new byte[width*height],width,height);
    }

    public Data_byte(byte[] data, int width, int height) {
       setData(data,width,height);
    }
    
    public final void setData(byte[] data, int width, int height) {
        this.data = data;
        if (data.length != width*height) {
            throw new IllegalArgumentException("Data argument, width and height do not match. Data.length!=width*height.");
        }
        this.width = width;
        this.height = height;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public int getWidth() {return width;}
    public int getHeight(){return height;}

    public byte get(int x, int y) {
        return data[y*width+x];
    }

    public void set(byte value, int x, int y) {
        data[y*width+x] = value;
    }

    public void plus(byte value, int x, int y) {
        data[y*width+x] += value;
    }

    public void minus(byte value, int x, int y) {
        data[y*width+x] -= value;
    }

    public void times(byte value, int x, int y) {
        data[y*width+x] *= value;
    }

    public void divide(byte value, int x, int y) {
        data[y*width+x] /= value;
    }

    public boolean isEmpty() {
        return data.length==0;
    }


}
