package teamjamin.ffs;

/**
 * Created by Jenny on 2/17/16.
 */

import android.widget.LinearLayout;

        import android.content.Context;
        import android.view.animation.Interpolator;
        import android.view.View;
        import android.util.AttributeSet;
        import android.widget.Scroller;
        import android.os.Handler;

/**
 * Created by Jenny on 2/16/16.
 */
public class FlyOutContainer extends LinearLayout {
    private View menu;
    private View content;

    protected static final int menuMargin = 150;

    public enum MenuState {
        CLOSED, OPEN, CLOSING, OPENING
    };

    protected MenuState menuCurrentState = MenuState.CLOSED;


    protected int currentContentOffset = 0;


    //Animation objects
    protected Scroller menuAnimationScroller = new Scroller(this.getContext(), new SmoothInterpolator());
    protected Runnable menuAnimationRunnable = new AnimationRunnable();
    protected Handler menuAnimationHandle = new Handler();


    //Animation constants
    private static final int menuAnimationDuration = 1000;
    private static final int menuAnimationPollingInterval = 16;

    public FlyOutContainer(Context context) {
        super(context);
    }

    public FlyOutContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FlyOutContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.menu = this.getChildAt(0);
        this.content = this.getChildAt(1);
        this.menu.setVisibility(View.GONE);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            this.calculateChildDimensions();
        }

        this.menu.layout(left,top,right-menuMargin,bottom);

        this.content.layout(left + this.currentContentOffset, top,
                right + this.currentContentOffset, bottom);

    }

    private void adjustContentPosition(boolean isAnimationOngoing) {
        int scrollerOffset = this.menuAnimationScroller.getCurrX();

        this.content.offsetLeftAndRight(scrollerOffset- this.currentContentOffset);
        this.currentContentOffset = scrollerOffset;

        this.invalidate();

        if(isAnimationOngoing) {
            this.menuAnimationHandle.postDelayed(this.menuAnimationRunnable, menuAnimationPollingInterval);
        }

        else {
            this.onMenuTransitionComplete();
        }
    }

    private void onMenuTransitionComplete () {
        switch (this.menuCurrentState) {
            case OPENING:
                this.menuCurrentState = MenuState.OPEN;
                break;
            case CLOSING:
                this.menuCurrentState = MenuState.CLOSED;
                this.menu.setVisibility(View.GONE);
                break;
            default:
                return;
        }
    }

    public void toggleMenu() {
        switch (this.menuCurrentState) {
            case CLOSED:
                this.menuCurrentState = MenuState.OPENING;
                this.menu.setVisibility(View.VISIBLE);
                this.menuAnimationScroller.startScroll(0,0, this.getMenuWidth(),0, menuAnimationDuration);
                //this.currentContentOffset = this.getMenuWidth();
                //this.content.offsetLeftAndRight(currentContentOffset);
                break;

            case OPEN:
                this.menuCurrentState = MenuState.CLOSING;
                this.menuAnimationScroller.startScroll(this.currentContentOffset, 0,
                        -this.currentContentOffset, 0, menuAnimationDuration);
                //this.content.offsetLeftAndRight(-currentContentOffset);
                //this.currentContentOffset = 0;
                //this.menu.setVisibility(View.GONE);
                break;
            default:
                return;
        }
        this.menuAnimationHandle.postDelayed(this.menuAnimationRunnable, menuAnimationPollingInterval);
        //this.invalidate();
    }

    protected class SmoothInterpolator implements Interpolator {
        @Override
        public float getInterpolation (float t) {
            return (float) Math.pow(t-1, 5) + 1;
        }
    }

    protected class AnimationRunnable implements Runnable {
        public void run() {
            boolean isAnimationOngoing = FlyOutContainer.this.menuAnimationScroller.computeScrollOffset();
            FlyOutContainer.this.adjustContentPosition(isAnimationOngoing);
        }
    }

    private int getMenuWidth() {
        return this.menu.getLayoutParams().width;
    }

    private void calculateChildDimensions () {
        this.content.getLayoutParams().height = this.getHeight();
        this.content.getLayoutParams().width = this.getWidth();

        this.menu.getLayoutParams().width = this.getWidth() - menuMargin;
        this.menu.getLayoutParams().height = this.getHeight();

    }
}
