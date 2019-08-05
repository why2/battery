package com.scooter.battery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class BatteryView extends View {
    /**
     * 边长
     */
    private int sideLength;
    /**
     * 边宽
     */
    private int sideWidth;
    /**
     * 电量
     */
    private int battery = 100;
    /**
     * 预警电量
     */
    private int warningBattery = 20;
    /**
     * 电池颜色
     */
    private @ColorInt
    int batteryColor = Color.GREEN;
    /**
     * 预警电池颜色
     */
    private @ColorInt
    int warningBatteryColor = Color.RED;

    private Paint paintBorder, paintThunder;
    private float[] points = new float[24];
    private Path pathThunder;
    /**
     * 中心点
     */
    private float centerPointX, centerPointY;

    public BatteryView(Context context) {
        this(context, null);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BatteryView);
        sideLength = typedArray.getDimensionPixelSize(R.styleable.BatteryView_side_length, dp2px(context, 68));
        sideWidth = typedArray.getDimensionPixelSize(R.styleable.BatteryView_side_width, dp2px(context, 4));
        batteryColor = typedArray.getColor(R.styleable.BatteryView_normal_color, Color.GREEN);
        warningBatteryColor = typedArray.getColor(R.styleable.BatteryView_warning_color, Color.RED);
        warningBattery = typedArray.getInteger(R.styleable.BatteryView_warning_percent, 20);
        initPoints();
        centerPointX = sideLength;
        centerPointY = ((float) (Math.sqrt(3) * sideLength / 2) + sideLength) / 2;
        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setStrokeWidth(sideWidth);
        paintBorder.setStrokeCap(Paint.Cap.ROUND);
        paintBorder.setColor(batteryColor);
        pathThunder = new Path();
        paintThunder = new Paint();
        paintThunder.setColor(Color.WHITE);
        paintThunder.setStyle(Paint.Style.FILL);
        typedArray.recycle();
    }


    /**
     * 六边形顶点坐标
     */
    private void initPoints() {
        points[0] = sideLength / 2;
        points[1] = sideWidth / 2;
        points[2] = sideLength / 2 + sideLength;
        points[3] = sideWidth / 2;
        points[4] = sideLength / 2 + sideLength;
        points[5] = sideWidth / 2;
        points[6] = sideLength * 2;
        points[7] = (float) (Math.sqrt(3) * sideLength / 2) + sideWidth / 2;
        points[8] = sideLength * 2;
        points[9] = (float) (Math.sqrt(3) * sideLength / 2) + sideWidth / 2;
        points[10] = sideLength / 2 + sideLength;
        points[11] = (float) (Math.sqrt(3) * sideLength) + sideWidth / 2;
        points[12] = sideLength / 2 + sideLength;
        points[13] = (float) (Math.sqrt(3) * sideLength) + sideWidth / 2;
        points[14] = sideLength / 2;
        points[15] = (float) (Math.sqrt(3) * sideLength) + sideWidth / 2;
        points[16] = sideLength / 2;
        points[17] = (float) (Math.sqrt(3) * sideLength) + sideWidth / 2;
        points[18] = sideWidth / 2;
        points[19] = (float) (Math.sqrt(3) * sideLength / 2) + sideWidth / 2;
        points[20] = sideWidth / 2;
        points[21] = (float) (Math.sqrt(3) * sideLength / 2) + sideWidth / 2;
        points[22] = sideLength / 2;
        points[23] = sideWidth / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(sideLength * 2 + sideWidth / 2, (int) (Math.sqrt(3) * sideLength) + sideWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLines(points, paintBorder);
        drawThunder(canvas);
    }


    /**
     * 绘制闪电
     *
     * @param canvas
     */
    private void drawThunder(Canvas canvas) {
        pathThunder.moveTo(centerPointX - sideLength * 0.35f, centerPointY + sideLength * 0.08f);
        pathThunder.lineTo(centerPointX + sideLength * 0.2f, centerPointY - sideLength * 0.6f);
        pathThunder.lineTo(centerPointX + sideLength * 0.05f, centerPointY - sideLength * 0.08f);
        pathThunder.lineTo(centerPointX + sideLength * 0.35f, centerPointY - sideLength * 0.08f);
        pathThunder.lineTo(centerPointX - sideLength * 0.2f, centerPointY + sideLength * 0.6f);
        pathThunder.lineTo(centerPointX - sideLength * 0.05f, centerPointY + sideLength * 0.08f);
        pathThunder.lineTo(centerPointX - sideLength * 0.35f, centerPointY + sideLength * 0.08f);
        canvas.drawPath(pathThunder, paintThunder);
    }

    public static int dp2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int sp2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }
}
