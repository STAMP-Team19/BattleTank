package battletank.world;

public class DeltaTime {

    long time=0;

    public void update(){
        time =System.currentTimeMillis()-time;
    }

    public double last(){
        return time;
    }

    public void setTime(long time){
        this.time=time;
    }
}
