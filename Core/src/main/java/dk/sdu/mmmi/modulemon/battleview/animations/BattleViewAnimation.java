package dk.sdu.mmmi.modulemon.battleview.animations;

import java.util.List;
import java.util.logging.Logger;

public abstract class BattleViewAnimation {
    private static final Logger LOGGER = Logger.getLogger(BattleViewAnimation.class.getName());
    protected int[] Timeline;
    protected List<float[]> States;
    private int currentTimelineIndex;
    private long millisecondCounter;
    private boolean isRunning = false;
    protected IAnimationCurve animationCurve = AnimationCurves.Linear();

    protected void tick(float dt) {
        if (isRunning)
            millisecondCounter += dt / 1000f;
    }

    public abstract void update(float dt);

    public void start() {
        if (Timeline == null || States == null) {
            throw new IllegalStateException("Timeline and States are not initialized");
        }
        if (Timeline.length < 1) {
            throw new IllegalStateException("Timeline length must be greater or equal to 1");
        }
        if (Timeline[0] != 0) {
            throw new IllegalStateException("The timeline must start with a animation state at 0 time");
        }

        //Check that the timeline is in sequential order, and does not contain negative numbers
        int tempMin = -1;
        for (int timePoint : Timeline) {
            if (timePoint < 0) {
                throw new IllegalStateException("Timeline can not have negative values");
            } else if (timePoint < tempMin) {
                throw new IllegalStateException("Timeline must be a sorted array");
            }
            tempMin = timePoint;
        }

        if (States.size() != Timeline.length) {
            throw new IllegalStateException("Timeline length does not match up with number of animation states");
        }
        int sampleLength = States.get(0).length;
        if (!States.stream().allMatch(x -> x.length == sampleLength)) {
            throw new IllegalStateException("All states must be of the same length");
        }

        currentTimelineIndex = 0;
        millisecondCounter = 0;
        isRunning = true;
    }

    public int getAnimationLength() {
        return Timeline[Timeline.length - 1];
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    private void setCorrectTimelineIndex() {
        for (int i = 0; i < Timeline.length - 1; i++) {
            if (Timeline[i] >= millisecondCounter) {
                currentTimelineIndex = i;
                return;
            }
        }
        //Fallback, should never be hit
        LOGGER.warning("Could not determine the correct timeline index");
        currentTimelineIndex = 0;
    }

    protected float[] getCurrentStates() {
        setCorrectTimelineIndex();
        int fuse = 0;
        int startOfCurrentState;
        int startOfNextState = 0;
        do {
            if (millisecondCounter >= getAnimationLength()) {
                isRunning = false;
                return States.get(Timeline[Timeline.length - 1]);
            }

            startOfCurrentState = Timeline[currentTimelineIndex];
            if(startOfCurrentState > millisecondCounter){
                currentTimelineIndex++;
                continue;
            }
            startOfNextState = Timeline[currentTimelineIndex + 1];
        }while(startOfNextState == 0 || ++fuse < 20);


        float[] currentStates = States.get(currentTimelineIndex);
        float[] nextStates = States.get(currentTimelineIndex+1);

        long timeElapsed = startOfCurrentState + millisecondCounter;
        float stateTransitionProgress = 0;
        if(timeElapsed != 0) {
            stateTransitionProgress = startOfNextState / timeElapsed;
        }

        float[] intermediateValues = new float[currentStates.length];
        for(int i = 0; i < currentStates.length; i++){
            intermediateValues[i] = animationCurve.getValue(currentStates[i], nextStates[i], stateTransitionProgress);
        }

        return intermediateValues;
    }
}
