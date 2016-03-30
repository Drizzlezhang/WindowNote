package com.drizzle.windownote;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by drizzle on 16/3/28.
 */
public class EditWindowManager {
	private static EditView editView;
	private static FabView fabView;
	private static WindowManager.LayoutParams editLayoutparams;
	private static WindowManager.LayoutParams fabLayoutparams;
	private static WindowManager windowManager;

	public static void createEditWindow(final Context context) {
		WindowManager windowManager = getWindowManager(context);
		if (editView == null) {
			editView = new EditView(context);
			if (editLayoutparams == null) {
				editLayoutparams = new WindowManager.LayoutParams();
				editLayoutparams.gravity = Gravity.CENTER;
				editLayoutparams.type = WindowManager.LayoutParams.TYPE_TOAST;
				editLayoutparams.format = PixelFormat.RGBA_8888;
				editLayoutparams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
				editLayoutparams.width = EditView.viewWidth;
				editLayoutparams.height = EditView.viewHeight;
			}
			editView.setLayoutParams(editLayoutparams);
			editView.setVisibility(View.GONE);
			windowManager.addView(editView, editLayoutparams);
			ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(editView, "scaleX", 0.0f, 1.0f);
			ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(editView, "scaleY", 0.0f, 1.0f);
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.playTogether(objectAnimator, objectAnimator1);
			animatorSet.setDuration(150).setInterpolator(new DecelerateInterpolator());
			animatorSet.addListener(new Animator.AnimatorListener() {
				@Override public void onAnimationStart(Animator animation) {
					editView.setVisibility(View.VISIBLE);
				}

				@Override public void onAnimationEnd(Animator animation) {
				}

				@Override public void onAnimationCancel(Animator animation) {

				}

				@Override public void onAnimationRepeat(Animator animation) {

				}
			});
			animatorSet.start();
		}
	}

	public static void createFabWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		if (fabView == null) {
			fabView = new FabView(context);
			if (fabLayoutparams == null) {
				fabLayoutparams = new WindowManager.LayoutParams();
				fabLayoutparams.format = PixelFormat.RGBA_8888;
				fabLayoutparams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
				fabLayoutparams.type = WindowManager.LayoutParams.TYPE_TOAST;
				fabLayoutparams.flags =
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
				fabLayoutparams.width = FabView.viewWidth;
				fabLayoutparams.height = FabView.viewHeight;
			}
			fabView.setLayoutParams(fabLayoutparams);
			fabView.setVisibility(View.GONE);
			windowManager.addView(fabView, fabLayoutparams);
			ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fabView, "scaleX", 0.0f, 1.0f);
			ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(fabView, "scaleY", 0.0f, 1.0f);
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.playTogether(objectAnimator, objectAnimator1);
			animatorSet.setDuration(100).setInterpolator(new DecelerateInterpolator());
			animatorSet.addListener(new Animator.AnimatorListener() {
				@Override public void onAnimationStart(Animator animation) {
					fabView.setVisibility(View.VISIBLE);
				}

				@Override public void onAnimationEnd(Animator animation) {
				}

				@Override public void onAnimationCancel(Animator animation) {

				}

				@Override public void onAnimationRepeat(Animator animation) {

				}
			});
			animatorSet.start();
		}
	}

	public static void removeEditView(Context context) {
		if (editView != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(editView);
			editView = null;
		}
	}

	public static void removeFabView(Context context) {
		if (fabView != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(fabView);
			fabView = null;
		}
	}

	public static boolean isWindowShowing() {
		return editView != null || fabView != null;
	}

	private static WindowManager getWindowManager(Context context) {
		if (windowManager == null) {
			windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return windowManager;
	}
}
