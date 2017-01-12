package ksk.marujolla.signaturepad;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/***
 * Created by shivakartik on 1/3/17.
 */

public class SignaturePad extends View {
    private final int DEFAULT_ATTR_PEN_COLOR = Color.BLACK;
    private final float DEFAULT_ATTR_PEN_WIDTH_DP = 5;
    private static float STROKE_WIDTH;
    private static float HALF_STROKE_WIDTH;

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private float mLastTouchX;
    private float mLastTouchY;
    private final RectF mDirtyRect = new RectF();
    private Context mContext;

    public SignaturePad(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context, attrs);
    }

    public SignaturePad(Context context) {
        super(context);
        setupView(context, null);
    }

    public SignaturePad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context, attrs);
    }

    public void setupView(Context context, AttributeSet attrs) {
        mContext = context;

        //Fixed parameters
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        if (attrs == null) {
            setDefaultPenWidth();
            mPaint.setColor(DEFAULT_ATTR_PEN_COLOR);
            mPaint.setStrokeWidth(STROKE_WIDTH);
        } else {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.SignaturePad,
                    0, 0);
            setPenWidth(a.getDimensionPixelSize(R.styleable.SignaturePad_penWidth,
                    (int)DEFAULT_ATTR_PEN_WIDTH_DP));
            mPaint.setColor(a.getColor(R.styleable.SignaturePad_penColor, DEFAULT_ATTR_PEN_COLOR));
        }
    }

    public void setPenWidth(float penWidth) {
        STROKE_WIDTH = penWidth;
        HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        mPaint.setStrokeWidth(STROKE_WIDTH);
    }

    public void setDefaultPenWidth() {
        STROKE_WIDTH = DEFAULT_ATTR_PEN_WIDTH_DP;
        HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        mPaint.setStrokeWidth(STROKE_WIDTH);
    }

    /*public void save(View v)
        {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);

                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                Log.v("log_tag","url: " + url);
                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
                //If you want to convert the image to string use base64 converter

            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
        }
    */

    public Bitmap getSignature(){
        View v = this;
        Bitmap bitmap = null;

       try{
           bitmap =Bitmap.createBitmap (v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
           Canvas c = new Canvas(bitmap);
           v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
           v.draw(c);
       }catch (Exception e){
            e.printStackTrace();
       }
        return bitmap;
    }
    public void clear() {
        mPath.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(eventX, eventY);
                mLastTouchX = eventX;
                mLastTouchY = eventY;
                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:

                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    mPath.lineTo(historicalX, historicalY);
                }
                mPath.lineTo(eventX, eventY);
                break;

            default:
                return false;
        }

        invalidate((int) (mDirtyRect.left - HALF_STROKE_WIDTH),
                (int) (mDirtyRect.top - HALF_STROKE_WIDTH),
                (int) (mDirtyRect.right + HALF_STROKE_WIDTH),
                (int) (mDirtyRect.bottom + HALF_STROKE_WIDTH));

        mLastTouchX = eventX;
        mLastTouchY = eventY;

        return true;
    }

    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < mDirtyRect.left) {
            mDirtyRect.left = historicalX;
        } else if (historicalX > mDirtyRect.right) {
            mDirtyRect.right = historicalX;
        }

        if (historicalY < mDirtyRect.top) {
            mDirtyRect.top = historicalY;
        } else if (historicalY > mDirtyRect.bottom) {
            mDirtyRect.bottom = historicalY;
        }
    }

    private void resetDirtyRect(float eventX, float eventY) {
        mDirtyRect.left = Math.min(mLastTouchX, eventX);
        mDirtyRect.right = Math.max(mLastTouchX, eventX);
        mDirtyRect.top = Math.min(mLastTouchY, eventY);
        mDirtyRect.bottom = Math.max(mLastTouchY, eventY);
    }

}

