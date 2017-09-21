package com.lida.carcare.widget.logisticsview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lida.carcare.R;

import java.util.List;

/**
 * 自定义物流节点线
 * Created by xkr on 2017/8/16.
 */

public class LogisticsNodeLineView extends View {
    private Context context;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 节点线顶部节点外环半径
     */
    private int topNodeOutSideRadius;
    /**
     * 节点先顶部节点内环半径
     */
    private int topNodeInSideRadius;
    /**
     * 节点线其他节点半径
     */
    private int otherNodeRadius;
    /**
     * 节点线节点之间的距离（距离相等的情况）
     */
    private int nodeRadiusDistance;
    /**
     * 节点线顶部节点颜色
     */
    private int topNodeColor;
    /**
     * 节点线其他节点颜色
     */
    private int otherNodeColor;
    /**
     * item顶部内边距
     */
    private float itemPaddingTop;
    /**
     * 节点线节点个数
     */
    private int nodeCount;
    /**
     * 节点线X位置
     */
    private int nodeLineX;
    /**
     * 节点线宽度
     */
    private int viewWidth;
    /**
     * 顶部节点外环厚度
     */
    private float topNodePaintStrokeWidth;
    /**
     * 每个节点之间的距离集合（距离不等的情况）
     */
    private List<Float> nodeRadiusDistances;

    public LogisticsNodeLineView(Context context) {
        this(context, null);
    }

    public LogisticsNodeLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogisticsNodeLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.context = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 抗锯齿
        // 初始化属性数组
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LogisticsNodeLineView);
        // 节点线顶部节点外环半径
        topNodeOutSideRadius = (int) a.getDimension(
                R.styleable.LogisticsNodeLineView_topNodeOutSideRadius,
                Dp2Px(context, 9));
        // 节点线顶部节点内环半径
        topNodeInSideRadius = (int) a.getDimension(
                R.styleable.LogisticsNodeLineView_topNodeInSideRadius,
                Dp2Px(context, 7));
        // 节点线其他节点半径
        otherNodeRadius = (int) a.getDimension(
                R.styleable.LogisticsNodeLineView_otherNodeRadius,
                Dp2Px(context, 5));
        // 节点线节点距离
        nodeRadiusDistance = (int) a.getDimension(
                R.styleable.LogisticsNodeLineView_nodeRadiusDistance,
                Dp2Px(context, 20));
        // 顶部节点颜色
        topNodeColor = a.getColor(R.styleable.LogisticsNodeLineView_topNodeColor,
                Color.parseColor("#5FCC7C"));
        // 其他节点颜色
        otherNodeColor = a.getColor(
                R.styleable.LogisticsNodeLineView_otherNodeColor,
                Color.parseColor("#D8D8D8"));
        // item顶部内边距
        itemPaddingTop = a.getDimension(
                R.styleable.LogisticsNodeLineView_itemPaddingTop,
                Dp2Px(context, 10));
        // 节点线节点个数
        nodeCount = (int) a.getInteger(
                R.styleable.LogisticsNodeLineView_nodeCount, 0);
        // 节点线的宽度
        viewWidth = (int) a.getDimension(
                R.styleable.LogisticsNodeLineView_viewWidth, Dp2Px(context, 1));
        // 顶部节点外环厚度
        topNodePaintStrokeWidth = 1.0f;
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 默认节点线的X坐标位于空间的正中间
        nodeLineX = getMeasuredWidth() / 2;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        // 设置画笔颜色为顶部节点颜色，绘制顶部节点
        mPaint.setColor(topNodeColor);
        float nodeLineY = 0.0f;
        //item顶部内边距
        itemPaddingTop = nodeRadiusDistances.get(nodeRadiusDistances.size() - 2);
        //listview item分割线高度
        float itemDividerHeight = nodeRadiusDistances.get(nodeRadiusDistances.size() - 1);
        // 遍历节点
        for (int i = 0; i < nodeCount; i++) {
            if (i == 0) {
                // 设置画笔样式为空心
                mPaint.setStyle(Paint.Style.STROKE);
                // 外环画笔宽度
                mPaint.setStrokeWidth(topNodePaintStrokeWidth);
                // 绘制顶部节点外环
                canvas.drawCircle(nodeLineX, topNodeOutSideRadius
                        + itemPaddingTop, topNodeOutSideRadius, mPaint);
                // 设置画笔为实心
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                // 绘制顶部节点内环
                canvas.drawCircle(nodeLineX, topNodeOutSideRadius
                        + itemPaddingTop, topNodeInSideRadius, mPaint);
                // 设置画笔颜色
                mPaint.setColor(otherNodeColor);
                // 节点线条宽度
                mPaint.setStrokeWidth(viewWidth);
                // 第一个节点以上的高度(包括节点一)
                float nodeOneTopHeight = itemPaddingTop + 2 * topNodeOutSideRadius + topNodePaintStrokeWidth;
                // 第一个节点下的节点线长度
                nodeLineY = nodeOneTopHeight + nodeRadiusDistances.get(i);
                // 绘制其他节点线
                canvas.drawLine(nodeLineX, nodeOneTopHeight, nodeLineX,
                        nodeLineY, mPaint);
                nodeLineY = nodeRadiusDistances.get(i) + itemDividerHeight;
            } else{
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                mPaint.setStrokeWidth(viewWidth);
                // 绘制其他节点
                canvas.drawCircle(nodeLineX, nodeLineY + itemPaddingTop + 2 * (float)otherNodeRadius,
                        (float)otherNodeRadius, mPaint);
                //绘制线条
                canvas.drawLine(nodeLineX, nodeLineY + itemPaddingTop + (float)otherNodeRadius,
                        nodeLineX,nodeLineY + itemPaddingTop + (float)otherNodeRadius + nodeRadiusDistances.get(i),
                        mPaint);
                nodeLineY += nodeRadiusDistances.get(i) + itemDividerHeight;
            }
        }
    }

    /**
     * dp转换成px
     *
     * @param context
     * @param dp
     * @return int
     */
    private static int Dp2Px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f * (dp >= 0 ? 1 : -1));
    }

    public int getTopNodeRadius() {
        return topNodeOutSideRadius;
    }

    public void setTopNodeRadius(int topNodeRadius) {
        this.topNodeOutSideRadius = topNodeRadius;
        invalidate();
    }

    public int getNodeRadius() {
        return otherNodeRadius;
    }

    public void setNodeRadius(int nodeRadius) {
        this.otherNodeRadius = nodeRadius;
        invalidate();
    }

    public int getNodeRadiusDistance() {
        return nodeRadiusDistance;
    }

    public void setNodeRadiusDistance(int nodeRadiusDistance) {
        this.nodeRadiusDistance = nodeRadiusDistance;
        invalidate();
    }

    public int getTopNodeColor() {
        return topNodeColor;
    }

    public void setTopNodeColor(int topNodeColor) {
        this.topNodeColor = topNodeColor;
        invalidate();
    }

    public int getOtherNodeColor() {
        return otherNodeColor;
    }

    public void setOtherNodeColor(int otherNodeColor) {
        this.otherNodeColor = otherNodeColor;
        invalidate();
    }

    public void setItemPaddingTop(float itemPaddingTop) {
        this.itemPaddingTop = itemPaddingTop;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
        invalidate();
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
        invalidate();
    }

    public float getTopNodePaintStrokeWidth() {
        return topNodePaintStrokeWidth;
    }

    public void setTopNodePaintStrokeWidth(float topNodePaintStrokeWidth) {
        this.topNodePaintStrokeWidth = topNodePaintStrokeWidth;
        invalidate();
    }

    public int getTopNodeOutSideRadius() {
        return topNodeOutSideRadius;
    }

    public void setTopNodeOutSideRadius(int topNodeOutSideRadius) {
        this.topNodeOutSideRadius = topNodeOutSideRadius;
        invalidate();
    }

    public int getTopNodeInSideRadius() {
        return topNodeInSideRadius;
    }

    public void setTopNodeInSideRadius(int topNodeInSideRadius) {
        this.topNodeInSideRadius = topNodeInSideRadius;
        invalidate();
    }

    public int getOtherNodeRadius() {
        return otherNodeRadius;
    }

    public void setOtherNodeRadius(int otherNodeRadius) {
        this.otherNodeRadius = otherNodeRadius;
        invalidate();
    }

    public List<Float> getNodeRadiusDistances() {
        return nodeRadiusDistances;
    }

    public void setNodeRadiusDistances(List<Float> nodeRadiusDistances) {
        this.nodeRadiusDistances = nodeRadiusDistances;
        invalidate();
    }

}
