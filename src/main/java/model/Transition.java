package model;

public class Transition {

    private String originState;

    private CharSequence labelTransition;

    private String destinyState;



    public Transition() {
    }

    public Transition(String originState, CharSequence labelTransition, String destinyState ) {
        this.originState = originState;
        this.labelTransition = labelTransition;
        this.destinyState = destinyState;

    }

    public String getOriginState() {
        return originState;
    }

    public void setOriginState(String originState) {
        this.originState = originState;
    }

    public String getDestinyState() {
        return destinyState;
    }

    public void setDestinyState(String destinyState) {
        this.destinyState = destinyState;
    }

    public CharSequence getLabelTransition() {
        return labelTransition;
    }

    public void setLabelTransition(CharSequence labelTransition) {
        this.labelTransition = labelTransition;
    }

    @Override
    public String toString() {
        return "\nTransition{" +
                "originState=" + originState +
                ", destinyState=" + destinyState +
                ", labelTransition=" + labelTransition +
                '}';
    }
}
