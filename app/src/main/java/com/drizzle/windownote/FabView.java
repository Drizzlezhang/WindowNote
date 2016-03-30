package com.drizzle.windownote;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by drizzle on 16/3/28.
 */
public class FabView extends FrameLayout{
	public static int viewHeight;
	public static int viewWidth;
	private View mView;
	public FabView(final Context context) {
		super(context);
		LayoutInflater.from(getContext()).inflate(R.layout.float_layout, this);
		mView = findViewById(R.id.float_layout);
		viewHeight = mView.getLayoutParams().height;
		viewWidth = mView.getLayoutParams().width;
		final FloatingActionButton floatingActionButton = (FloatingActionButton) mView.findViewById(R.id.window_fab);
		floatingActionButton.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(floatingActionButton, "scaleX", 1.0f, 0.0f);
				ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(floatingActionButton, "scaleY", 1.0f, 0.0f);
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.playTogether(objectAnimator, objectAnimator1);
				animatorSet.setDuration(100).setInterpolator(new DecelerateInterpolator());
				animatorSet.addListener(new Animator.AnimatorListener() {
					@Override public void onAnimationStart(Animator animation) {

					}

					@Override public void onAnimationEnd(Animator animation) {
						EditWindowManager.removeFabView(context);
						EditWindowManager.createEditWindow(context);
					}

					@Override public void onAnimationCancel(Animator animation) {

					}

					@Override public void onAnimationRepeat(Animator animation) {

					}
				});
				animatorSet.start();
			}
		});
	}

}
