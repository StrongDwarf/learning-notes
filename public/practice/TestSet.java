public class TestSet{
    private String read;

    public String getRead(){
        return read;
    }
    public void setRead(String read){
        this.read = read;
    }

    @Override
    public String toString(){
        return "TestSet[ read" + read + "]";
    }
}