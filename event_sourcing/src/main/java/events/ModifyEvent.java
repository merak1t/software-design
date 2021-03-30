package events;

public abstract class ModifyEvent extends Event {

    protected int memberId;

    public int getMemberId() {
        return memberId;
    }

}
