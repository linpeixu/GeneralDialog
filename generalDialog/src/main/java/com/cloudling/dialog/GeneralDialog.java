package com.cloudling.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * 描述: 通用Dialog
 * 联系: 1966353889@qq.com
 * 日期: 2022/1/15
 */
public class GeneralDialog extends Dialog {
    /**
     * 窗口配置
     */
    WindowManager.LayoutParams wLp;
    /**
     * 窗口宽度
     */
    int width;
    /**
     * 窗口高度
     */
    int height;
    /**
     * 窗口对齐方式
     */
    int gravity;
    /**
     * 窗口动画
     */
    int windowAnimations;
    /**
     * 窗口布局id
     */
    int layoutId;
    /**
     * 窗口内容View
     */
    View contentView;
    /**
     * 是否可取消
     */
    boolean cancelable;
    /**
     * 点击外部区域是否可取消
     */
    boolean canceledOnTouchOutside;
    /**
     * 横坐标
     */
    int x;
    /**
     * 纵坐标
     */
    int y;
    /**
     * 窗口展示监听
     */
    OnShowListener onShowListener;
    /**
     * 窗口隐藏监听
     */
    OnDismissListener onDismissListener;
    /**
     * 点击事件监听
     */
    OnDialogClickListener onClickListener;
    /**
     * 存储设置点击事件监听的viewIds
     */
    ArrayList<Integer> clickViewsIds;
    /**
     * 初始化监听
     */
    OnInitListener onInitListener;

    private GeneralDialog(Context context, Builder builder) {
        super(context, builder.themeResId);
        this.wLp = builder.wLp;
        this.width = builder.width;
        this.height = builder.height;
        this.gravity = builder.gravity;
        this.windowAnimations = builder.windowAnimations;
        this.layoutId = builder.layoutId;
        this.contentView = builder.contentView;
        this.cancelable = builder.cancelable;
        this.canceledOnTouchOutside = builder.canceledOnTouchOutside;
        this.x = builder.x;
        this.y = builder.y;
        this.onShowListener = builder.onShowListener;
        this.onDismissListener = builder.onDismissListener;
        this.onClickListener = builder.onClickListener;
        this.clickViewsIds = builder.clickViewsIds;
        this.onInitListener = builder.onInitListener;
        if (this.contentView == null && this.layoutId != 0) {
            this.contentView = getLayoutInflater().inflate(this.layoutId, null);
        }
        if (this.contentView != null) {
            setContentView(this.contentView);
        }
        if (this.onInitListener != null && this.contentView != null) {
            this.onInitListener.onInit(this, this.contentView);
        }
        /*由于设置窗口尺寸必须要放在setContentView之后，为了兼容BindingGeneralDialog的初始化方法设置尺寸，这里加了个方法，
         * BindingGeneralDialog重写setDialogSizeInInit()返回false*/
        if (setDialogSizeInInit()) {
            setDialogSize();
        }
        setCancelable(this.cancelable);
        setCanceledOnTouchOutside(this.canceledOnTouchOutside);
        setOnShowListener(this.onShowListener);
        setOnDismissListener(this.onDismissListener);
        if (this.onClickListener != null && clickViewsIds != null && clickViewsIds.size() > 0) {
            for (int viewId : clickViewsIds) {
                View view = findViewById(viewId);
                if (view != null) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GeneralDialog.this.onClickListener.onClick(v, GeneralDialog.this);
                        }
                    });
                }
            }
        }
    }

    /**
     * 是否在初始化方法设置窗口尺寸
     */
    public boolean setDialogSizeInInit() {
        return true;
    }

    /**
     * 设置窗口尺寸
     */
    protected void setDialogSize() {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(windowAnimations);
            if (wLp != null) {
                window.setAttributes(wLp);
            } else {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = this.width;
                lp.height = this.height;
                lp.gravity = this.gravity;
                lp.x = this.x;
                lp.y = this.y;
                window.setAttributes(lp);
            }
        }
    }

    /**
     * 支持通过ViewDataBinding设置contentView的GeneralDialog
     */
    public static class BindingGeneralDialog<Binding> extends GeneralDialog {
        OnBindingInitListener<Binding> onBindingInitListener;
        ViewDataBinding<Binding> viewDataBinding;

        private BindingGeneralDialog(Context context, BindingBuilder<Binding> builder) {
            super(context, builder);
            this.onBindingInitListener = builder.onBindingInitListener;
            this.viewDataBinding = builder.viewDataBinding;
            if (this.viewDataBinding != null) {
                setContentView(this.viewDataBinding.getRoot());
            }
            setDialogSize();
            if (this.onBindingInitListener != null && this.viewDataBinding != null) {
                this.onBindingInitListener.onInit(this, viewDataBinding.viewDataBinding);
            }
        }

        @Override
        public boolean setDialogSizeInInit() {
            return false;
        }
    }

    /**
     * 用于创建GeneralDialog
     */
    public static class Builder {
        /**
         * 窗口配置
         */
        WindowManager.LayoutParams wLp;
        /**
         * 窗口宽度
         */
        int width;
        /**
         * 窗口高度
         */
        int height;
        /**
         * 窗口对齐方式
         */
        int gravity;
        /**
         * 窗口动画
         */
        int windowAnimations;
        /**
         * 窗口主题
         */
        int themeResId;
        /**
         * 窗口布局id
         */
        int layoutId;
        /**
         * 窗口内容View
         */
        View contentView;
        /**
         * 是否可取消
         */
        boolean cancelable = true;
        /**
         * 点击外部区域是否可取消
         */
        boolean canceledOnTouchOutside = true;
        /**
         * 横坐标
         */
        int x;
        /**
         * 纵坐标
         */
        int y;
        /**
         * 窗口展示监听
         */
        OnShowListener onShowListener;
        /**
         * 窗口隐藏监听
         */
        OnDismissListener onDismissListener;
        /**
         * 点击事件监听
         */
        OnDialogClickListener onClickListener;
        /**
         * 存储设置点击事件监听的viewIds
         */
        ArrayList<Integer> clickViewsIds;
        /**
         * 初始化监听
         */
        OnInitListener onInitListener;

        public Builder layoutParams(WindowManager.LayoutParams wLp) {
            this.wLp = wLp;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder windowAnimations(int resId) {
            this.windowAnimations = resId;
            return this;
        }

        public Builder theme(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder layoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public Builder view(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }

        public Builder onShowListener(OnShowListener onShowListener) {
            this.onShowListener = onShowListener;
            return this;
        }

        public Builder onDismissListener(OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder onClickListener(OnDialogClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder clickViews(int... viewIds) {
            if (viewIds != null && viewIds.length > 0) {
                if (clickViewsIds == null) {
                    clickViewsIds = new ArrayList<>();
                    for (int viewId : viewIds) {
                        clickViewsIds.add(viewId);
                    }
                }
            }
            return this;
        }

        public Builder onInitListener(OnInitListener onInitListener) {
            this.onInitListener = onInitListener;
            return this;
        }

        public GeneralDialog build(Context context) {
            return new GeneralDialog(context, this);
        }
    }

    /**
     * 用于创建GeneralDialog<Binding>
     */
    public static class BindingBuilder<Binding> extends Builder {
        OnBindingInitListener<Binding> onBindingInitListener;
        ViewDataBinding<Binding> viewDataBinding;

        public BindingBuilder<Binding> layoutParams(WindowManager.LayoutParams wLp) {
            super.layoutParams(wLp);
            return this;
        }

        public BindingBuilder<Binding> width(int width) {
            super.width(width);
            return this;
        }

        public BindingBuilder<Binding> height(int height) {
            super.height(height);
            return this;
        }

        public BindingBuilder<Binding> gravity(int gravity) {
            super.gravity(gravity);
            return this;
        }

        public BindingBuilder<Binding> windowAnimations(int resId) {
            super.windowAnimations(resId);
            return this;
        }

        public BindingBuilder<Binding> theme(int themeResId) {
            super.theme(themeResId);
            return this;
        }

        public BindingBuilder<Binding> layoutId(int layoutId) {
            super.layoutId(layoutId);
            return this;
        }

        public BindingBuilder<Binding> view(View contentView) {
            super.view(contentView);
            return this;
        }

        public BindingBuilder<Binding> cancelable(boolean cancelable) {
            super.cancelable(cancelable);
            return this;
        }

        public BindingBuilder<Binding> canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            super.canceledOnTouchOutside(canceledOnTouchOutside);
            return this;
        }

        public BindingBuilder<Binding> x(int x) {
            super.x(x);
            return this;
        }

        public BindingBuilder<Binding> y(int y) {
            super.y(y);
            return this;
        }

        public BindingBuilder<Binding> onShowListener(OnShowListener onShowListener) {
            super.onShowListener(onShowListener);
            return this;
        }

        public BindingBuilder<Binding> onDismissListener(OnDismissListener onDismissListener) {
            super.onDismissListener(onDismissListener);
            return this;
        }

        public BindingBuilder<Binding> onClickListener(OnDialogClickListener onClickListener) {
            super.onClickListener(onClickListener);
            return this;
        }

        public BindingBuilder<Binding> clickViews(int... viewIds) {
            super.clickViews(viewIds);
            return this;
        }

        public BindingBuilder<Binding> onInitListener(OnInitListener onInitListener) {
            super.onInitListener(onInitListener);
            return this;
        }

        public BindingBuilder<Binding> onInitListener(OnBindingInitListener<Binding> onBindingInitListener) {
            this.onBindingInitListener = onBindingInitListener;
            return this;
        }

        public BindingBuilder<Binding> viewDataBinding(ViewDataBinding<Binding> viewDataBinding) {
            this.viewDataBinding = viewDataBinding;
            return this;
        }

        public BindingGeneralDialog<Binding> build(Context context) {
            return new BindingGeneralDialog<>(context, this);
        }
    }

    /**
     * 点击事件监听
     */
    public abstract static class OnDialogClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            /*空实现*/
        }

        public abstract void onClick(View v, DialogInterface dialog);
    }

    /**
     * 初始化监听
     */
    public interface OnInitListener {
        void onInit(DialogInterface dialog, View contentView);
    }

    /**
     * 初始化监听（ViewDataBinding）
     */
    public interface OnBindingInitListener<Binding> {
        void onInit(DialogInterface dialog, Binding viewDataBinding);
    }

    /**
     * 用于支持设置DataBinding的ContentView
     */
    public abstract static class ViewDataBinding<Binding> {
        protected Binding viewDataBinding;

        public ViewDataBinding(Binding viewDataBinding) {
            this.viewDataBinding = viewDataBinding;
        }

        protected abstract View getRoot();
    }
}
