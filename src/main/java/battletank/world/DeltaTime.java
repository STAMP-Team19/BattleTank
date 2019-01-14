package battletank.world;

public class DeltaTime {

    long time=0;
    private long lastSysTime=System.currentTimeMillis();

    public void update(){
        long currentTime = System.currentTimeMillis();
        time =currentTime-lastSysTime;
        lastSysTime =System.currentTimeMillis();

    }

    public double last(){
        return time;
    }

    public void setTime(long time){
        this.time=time;
    }
}
