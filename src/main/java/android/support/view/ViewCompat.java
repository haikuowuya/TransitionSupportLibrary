package android.support.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.internal.view.ViewOverlayImpl;
import android.support.internal.view.ViewOverlayWrapper;
import android.support.util.CompatUtils;
import android.view.View;

import java.lang.reflect.Method;

public class ViewCompat {
	interface ViewCompatImpl {
		float getTransitionAlpha(View v);

		ViewOverlay getSupportViewOverlay(View v);

		boolean isLaidOut(View v);

		void setClipBounds(View v, Rect clipBounds);

		void setTransitionAlpha(View v, float alpha);
	}

	static class BaseViewCompatImpl implements ViewCompatImpl {
		@Override
		public float getTransitionAlpha(View v) {
			// TODO: Implement support behavior
			return 1;
		}

		@Override
		public ViewOverlay getSupportViewOverlay(View v) {
			return new ViewOverlayImpl(v.getContext(), v);
		}

		@Override
		public boolean isLaidOut(View v) {
			// TODO: Implement support behavior
			return true;
		}

		@Override
		public void setClipBounds(View v, Rect clipBounds) {
			// TODO: Implement support behavior
		}

		@Override
		public void setTransitionAlpha(View v, float alpha) {
			// TODO: Implement support behavior
		}
	}

	@TargetApi(VERSION_CODES.JELLY_BEAN_MR2)
	static class JellyBeanMR2ViewCompatImpl extends BaseViewCompatImpl {
		@Override
		public void setClipBounds(View v, Rect clipBounds) {
			ViewCompatJellybeanMr2.setClipBounds(v, clipBounds);
		}

		@Override
		public ViewOverlay getSupportViewOverlay(View v) {
			return new ViewOverlayWrapper(v);
		}
	}

	@TargetApi(VERSION_CODES.KITKAT)
	static class KitKatViewCompatImpl extends JellyBeanMR2ViewCompatImpl {
		@Override
		public float getTransitionAlpha(View v) {
			return ViewCompatKitKat.getTransitionAlpha(v);
		}

		@Override
		public boolean isLaidOut(View v) {
			return ViewCompatKitKat.isLaidOut(v);
		}

		@Override
		public void setTransitionAlpha(View v, float alpha) {
			ViewCompatKitKat.setTransitionAlpha(v, alpha);
		}
	}

	static final ViewCompatImpl IMPL;

	static {
		final int version = VERSION.SDK_INT;
		if (version >= VERSION_CODES.KITKAT) {
			IMPL = new KitKatViewCompatImpl();
		}
		else if (version >= VERSION_CODES.JELLY_BEAN_MR2) {
			IMPL = new JellyBeanMR2ViewCompatImpl();
		}
		else {
			IMPL = new BaseViewCompatImpl();
		}
	}

	public static float getTransitionAlpha(View v) {
		return IMPL.getTransitionAlpha(v);
	}

	public static ViewOverlay getSupportOverlay(View v) {
		return IMPL.getSupportViewOverlay(v);
	}

	public static boolean isLaidOut(View v) {
		return IMPL.isLaidOut(v);
	}

	public static void setClipBounds(View v, Rect clipBounds) {
		IMPL.setClipBounds(v, clipBounds);
	}

	public static void setTransitionAlpha(View v, float alpha) {
		IMPL.setTransitionAlpha(v, alpha);
	}
}
