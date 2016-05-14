package per.rick.contacts.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import per.rick.contacts.R;

/**
 * 侧边字母索引
 * Created by Rick on 2016/5/14.
 */
public class SideBar extends View {
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	public static String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H",
			"I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#"};// 索引列表

	private int choose = -1;// 选中
	private Paint paint = new Paint();

	private TextView mTextDialog;// 当前字母提示

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}


	public SideBar(Context context) {
		super(context);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SideBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = height / chars.length;// 获取每一个字母的高度

		for (int i = 0; i < chars.length; i++) {
			paint.setColor(ContextCompat.getColor(this.getContext(), R.color
					.colorSideBarText));
			// paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(getResources().getDimensionPixelSize(
					R.dimen.sideBarTextSize));
			// 选中的状态
			if (i == choose) {
				paint.setColor(ContextCompat.getColor(this.getContext(), R.color
						.colorSideBarTextPressed));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(chars[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(chars[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}

	}


	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * chars.length);
		// 点击y坐标所占总高度的比例*b数组的长度就等于点击chars中的个数.

		switch (action) {
			case MotionEvent.ACTION_UP:
				this.setPressed(false);
				choose = -1;//
				invalidate();
				if (mTextDialog != null) {
					mTextDialog.setVisibility(View.INVISIBLE);
				}
				break;

			default:
				this.setPressed(true);
				if (oldChoose != c) {
					if (c >= 0 && c < chars.length) {
						if (listener != null) {
							listener.onTouchingLetterChanged(chars[c]);
						}
						if (mTextDialog != null) {
							mTextDialog.setText(chars[c]);
							mTextDialog.setVisibility(View.VISIBLE);
						}

						choose = c;
						invalidate();
					}
				}
				break;
		}
		return true;
	}

	/**
	 * 设置触摸动作监听器
	 *
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 接口
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}
}
