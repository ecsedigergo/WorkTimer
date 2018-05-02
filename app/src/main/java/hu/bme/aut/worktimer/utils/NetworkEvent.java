package hu.bme.aut.worktimer.utils;

public abstract class NetworkEvent {
    private int code;
    private Throwable throwable;

    public NetworkEvent(){

    }
    public NetworkEvent(int code, Throwable throwable) {
        this.code = code;
        this.throwable = throwable;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}