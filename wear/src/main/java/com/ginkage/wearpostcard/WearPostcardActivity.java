package com.ginkage.wearpostcard;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class WearPostcardActivity extends Activity {
	private PostcardSurfaceView mGLView = null;
	private final Handler mHandler = new Handler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

/*		setContentView(R.layout.activity_wear_postcard);
		final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
		stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
			@Override
			public void onLayoutInflated(WatchViewStub stub) {
				mTextView = (TextView) stub.findViewById(R.id.text);
			}
		});
*/
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity
		mGLView = new PostcardSurfaceView(this);
		setContentView(mGLView);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// The following call pauses the rendering thread.
		// If your OpenGL application is memory intensive,
		// you should consider de-allocating objects that
		// consume significant memory here.
		if (mGLView != null)
			mGLView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// The following call resumes a paused rendering thread.
		// If you de-allocated graphic objects for onPause()
		// this is a good place to re-allocate them.
		if (mGLView != null)
			mGLView.onResume();
	}
}

class PostcardSurfaceView extends GLSurfaceView
{
	public PostcardRenderer mRenderer = null;

	public PostcardSurfaceView(Context context) {
		super(context);

		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		mRenderer = new PostcardRenderer(context);
		setRenderer(mRenderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// MotionEvent reports input details from the touch screen
		// and other input controls. In this case, you are only
		// interested in events where the touch position changed.

		switch (e.getAction()) {
			case MotionEvent.ACTION_UP:
				mRenderer.showText = !mRenderer.showText;
				break;
		}

		return true;
	}
}