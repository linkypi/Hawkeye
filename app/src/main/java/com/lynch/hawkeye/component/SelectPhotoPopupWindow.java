package com.lynch.hawkeye.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lynch.hawkeye.R;

/**
 * Created by JL on 2016/11/26/0026.
 */

public class SelectPhotoPopupWindow extends PopupWindow {

    private View view;
    private Button btn_take_photo,btn_select_photo,btn_cancel;
    public SelectPhotoPopupWindow(Context context, View.OnClickListener itemsOnClick) {

        this.view = LayoutInflater.from(context).inflate(R.layout.window_popup, null);

        btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
        btn_select_photo = (Button) view.findViewById(R.id.btn_select_photo);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        // 取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        // 设置按钮监听
        btn_select_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(500);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        // 设置弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        this.setBackgroundDrawable(new BitmapDrawable(null,""));
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.Popupwindow);

    }
}
