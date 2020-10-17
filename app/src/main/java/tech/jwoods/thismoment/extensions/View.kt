package tech.jwoods.thismoment.extensions

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener

// Adapted from: https://stackoverflow.com/questions/55251650/how-to-animate-to-wrap-content/55252233#55252233
fun View.animateVisibile() {
    //this.measure(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    //val targetHeight = this.measuredHeight
    val targetHeight = 100

    this.layoutParams.height = 1
    this.visibility = View.VISIBLE

    val animator = ValueAnimator.ofInt(this.measuredHeight, targetHeight)
    animator.addUpdateListener {valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        this.layoutParams.height = value
    }

    /*animator.addListener(onEnd = {
        // At the end of animation, set the height to wrap content
        // This fix is for long views that are not shown on screen
        val layoutParams: ViewGroup.LayoutParams = this.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
    })*/

    animator.duration = 500
    animator.start()
}

fun View.animateGone() {

}

fun View.animateHeight(from: Int, to: Int, duration: Long) {


    /*ValueAnimator anim = ValueAnimator.ofInt(from, to);
    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int val = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = val;
            view.setLayoutParams(layoutParams);
        }
    });
    anim.setDuration(duration);
    anim.start();*/
}