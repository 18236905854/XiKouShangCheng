package com.xk.mall.view.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.xk.mall.R;


public class ContentWithSpaceEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener{
    private int contentType;
    public static final int TYPE_PHONE = 0;
    public static final int TYPE_CARD = 1;
    public static final int TYPE_IDCARD = 2;

    //EditText右侧的删除按钮
    private Drawable mClearDrawable;
    private boolean hasFoucs;

    public ContentWithSpaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
    }

    public ContentWithSpaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributeSet(context, attrs);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ContentWithSpaceEditText, 0, 0);
        contentType = ta.getInt(R.styleable.ContentWithSpaceEditText_epaysdk_type, 0);
        ta.recycle();
        initType();
        init();
        setSingleLine();
        addTextChangedListener(watcher);
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,获取图片的顺序是左上右下（0,1,2,3,）
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(
                    R.mipmap.ic_edit_clean);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(watcher);
    }


    private void initType(){
        if (contentType == TYPE_PHONE) {
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        } else if (contentType == TYPE_CARD) {
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(31)});
        } else if (contentType == TYPE_IDCARD) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(21)});
        }
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
        initType();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                Rect rect = getCompoundDrawables()[2].getBounds();
                int height = rect.height();
                int distance = (getHeight() - height)/2;
                boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() - getPaddingRight());
                boolean isInnerHeight = y > distance && y <(distance + height);
                if (isInnerWidth && isInnerHeight) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null) {
                return;
            }
            if (hasFoucs) {
                setClearIconVisible(s.length() > 0);
            }
            //判断是否是在中间输入，需要重新计算
            boolean isMiddle = (start + count) < (s.length());
            //在末尾输入时，是否需要加入空格
            boolean isNeedSpace = false;
            if (!isMiddle && isSpace(s.length())) {
                isNeedSpace = true;
            }
            if (isMiddle || isNeedSpace) {
                String newStr = s.toString();
                newStr = newStr.replace(" ", "");
                StringBuilder sb = new StringBuilder();
                int spaceCount = 0;
                for (int i = 0; i < newStr.length(); i++) {
                    sb.append(newStr.substring(i, i+1));
                    //如果当前输入的字符下一位为空格(i+1+1+spaceCount)，因为i是从0开始计算的，所以一开始的时候需要先加1
                    if(isSpace(i + 2 + spaceCount)){
                        sb.append(" ");
                        spaceCount += 1;
                    }
                }
                removeTextChangedListener(watcher);
                setText(sb);
                //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
                if (!isMiddle || count > 1) {
                    setSelection(sb.length());
                } else if (isMiddle) {
                    //如果是删除
                    if (count == 0) {
                        //如果删除时，光标停留在空格的前面，光标则要往前移一位
                        if (isSpace(start - before + 1)) {
                            setSelection((start - before) > 0 ? start - before : 0);
                        } else {
                            setSelection((start - before + 1) > sb.length() ? sb.length() : (start - before + 1));
                        }
                    }
                    //如果是增加
                    else {
                        if (isSpace(start - before + count)) {
                            setSelection((start + count - before + 1) < sb.length() ? (start + count - before + 1) : sb.length());
                        } else {
                            setSelection(start + count - before);
                        }
                    }
                }
                addTextChangedListener(watcher);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    public String getTextWithoutSpace() {
        return super.getText().toString().replace(" ", "");
    }

    public boolean checkTextRight(){
        String text = getTextWithoutSpace();
        //这里做个简单的内容判断
        if (contentType == TYPE_PHONE) {
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(getContext(), "手机号不能为空，请输入正确的手机号",Toast.LENGTH_SHORT).show();
            } else if (text.length() < 11) {
                Toast.makeText(getContext(), "手机号不足11位，请输入正确的手机号",Toast.LENGTH_SHORT).show();
            } else {
                return true;
            }
        } else if (contentType == TYPE_CARD) {
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(getContext(), "银行卡号不能为空，请输入正确的银行卡号",Toast.LENGTH_SHORT).show();
            } else if (text.length() < 14) {
                Toast.makeText(getContext(), "银行卡号位数不正确，请输入正确的银行卡号",Toast.LENGTH_SHORT).show();

            } else {
                return true;
            }
        } else if (contentType == TYPE_IDCARD) {
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(getContext(), "身份证号不能为空，请输入正确的身份证号",Toast.LENGTH_SHORT).show();
            } else if (text.length() < 18) {
                Toast.makeText(getContext(), "身份证号不正确，请输入正确的身份证号",Toast.LENGTH_SHORT).show();
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean isSpace(int length) {
        if (contentType == TYPE_PHONE) {
            return isSpacePhone(length);
        } else if (contentType == TYPE_CARD) {
            return isSpaceCard(length);
        } else if (contentType == TYPE_IDCARD) {
            return isSpaceIDCard(length);
        }
        return false;
    }

    private boolean isSpacePhone(int length) {
        if (length < 4) {
            return false;
        } else if (length == 4) {
            return true;
        } else return (length + 1) % 5 == 0;
    }

    private boolean isSpaceCard(int length) {
        return length % 5 == 0;
    }

    private boolean isSpaceIDCard(int length) {
        if (length <= 6) {
            return false;
        } else if (length == 7) {
            return true;
        } else return (length - 2) % 5 == 0;
    }

    /**
     * 当ClearEditText焦点发生变化的时候，
     * 输入长度为零，隐藏删除图标，否则，显示删除图标
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }
}
