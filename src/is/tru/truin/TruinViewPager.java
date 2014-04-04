package is.tru.truin;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TruinViewPager extends ViewPager {
	
	private boolean enabled=false;
	
	public TruinViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.enabled = true;
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(this.enabled){
			return super.onTouchEvent(event);
		}
		return false;
	}
	
	public boolean onInterceptTouchEvent(MotionEvent event){
		if(this.enabled) {
			return super.onInterceptTouchEvent(event);
		}
		return false;
	}
	
	public void setPagingEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public boolean getPagingEnabled() {
		return this.enabled;
	}
}
