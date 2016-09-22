# AnimationDemo

###属性动画
[郭霖的博客](http://blog.csdn.net/guolin_blog?viewmode=contents)
>Android 3.0版本引入
####简介
正由于补间动画的局限性才有了属性动画的引入, 那么补间动画都有哪些局限性呢:
1. 只能对View对象进行操作；
2. 只有移动、缩放、旋转、淡入淡出四种动画操作；
3. 只能改变View的显示效果，不能改变View的属性。

属性动画是通过对目标对象进行赋值并改变其属性来实现的.

####ValueAnimator
ValueAnimator的内部使用一种时间循环的机制来计算值与值之间的动画过渡，我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。除此之外，ValueAnimator还负责管理动画的播放次数、播放模式、以及对动画设置监听器等。

```
ValueAnimator animator = ValueAnimator.ofInt(1, 4,5,8);
animator.setDuration(300);
animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
{
    @Override
    public void onAnimationUpdate(ValueAnimator animation)
    {
        int currentValue = (int)animation.getAnimatedValue();
        Log.e(TAG, "current value is " + currentValue);
    }
});

animator.start();
```

####ObjectAnimator
>ObjectAnimator可以直接对任意对象的任意属性值进行动画操作, 比如View的alpha属性.

1. ofFloat方法
>ofFloat(Object target, String propertyName, float... values)
- @param target : 运行动画的对象
- @param propertyName : 属性名
- @param values : 动画运行的值的集合

这个方法的意思就是不断修改 target 对象的 propertyName 属性值, 从 value0 到 valuex;

propertyName 属性的值是`任意`的, 它代表可以通过相应的get和set方法来修改 propertyName属性的值; 
比如View中的setAlpha(float value) 和 getAlpha()方法.

2. 可操作的View属性大致有: 
- alpha: 渐变
- rotation: 旋转
- translationX/Y/Z: 位移
- scaleX/Y:缩放

比如X轴位移动画

```
float curTranslationX = view.getTranslationX();

ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", curTranslationX, -500f, curTranslationX);
animator.setDuration(2000);
animator.start();
```

####AnimatorSet
AnimatorSet这个类是实现组合动画功能的主要方法，这个类提供了一个`play()`方法，如果我们向这个方法中传入一个Animator对象(ValueAnimator或ObjectAnimator)将会返回一个AnimatorSet.Builder的实例，AnimatorSet.Builder中包括以下四个方法：
-  after(Animator anim)   将现有动画插入到传入的动画之后执行
-  after(long delay)   将现有动画延迟指定毫秒后执行
-  before(Animator anim)   将现有动画插入到传入的动画之前执行
-  with(Animator anim)   将现有动画和传入的动画同时执行

```
ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", 0f, -500f, 0f);
ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
AnimatorSet animSet = new AnimatorSet();
animSet.play(rotate).with(fadeInOut).after(moveIn);
animSet.setDuration(5000);
animSet.start();
```

####动画监听器
对于ValueAnimator, ObjectAnimator和AnimatorSet都可以使用addListener()监听动画的各种事件.

```
anim.addListener(new AnimatorListener() {  
    @Override  
    public void onAnimationStart(Animator animation) {  
    }  
  
    @Override  
    public void onAnimationRepeat(Animator animation) {  
    }  
  
    @Override  
    public void onAnimationEnd(Animator animation) {  
    }  
  
    @Override  
    public void onAnimationCancel(Animator animation) {  
    }  
});  
```
实现动画监听还有一种简单方式, 使用Androd 提供的 AnimatorListenerAdapter 适配器类.

```
anim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
            }
        });
```

####使用xml编写动画
如果想要使用XML来编写动画，首先要在res目录下面新建一个`animator`文件夹，所有属性动画的XML文件都应该存放在这个文件夹当中属性动画的xml文件一共可以使用如下三个标签:
- <animator>  对应代码中的ValueAnimator
- <objectAnimator>  对应代码中的ObjectAnimator
- <set>  对应代码中的AnimatorSet

那么比如说我们想要实现一个从0到100平滑过渡的动画，在XML当中就可以这样写：

```
<animator xmlns:android="http://schemas.android.com/apk/res/android"  
    android:valueFrom="0"  
    android:valueTo="100"  
    android:valueType="intType"/>  
```

而如果我们想将一个视图的alpha属性从1变成0，就可以这样写：

```
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"  
    android:valueFrom="1"  
    android:valueTo="0"  
    android:valueType="floatType"  
    android:propertyName="alpha"/>  
```

另外，我们也可以使用XML来完成复杂的组合动画操作，比如将一个视图先移出屏幕再移入屏幕，然后开始旋转360度，旋转的同时进行淡入淡出操作，就可以这样写：

```
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:ordering="sequentially">

    <objectAnimator
        android:propertyName="translationX"
        android:valueFrom="0"
        android:valueTo="500"
        android:valueType="floatType"
        android:duration="1000">
    </objectAnimator>

    <objectAnimator
        android:propertyName="translationX"
        android:valueFrom="500"
        android:valueTo="0"
        android:valueType="floatType"
        android:duration="1000">
    </objectAnimator>

    <set android:ordering="together">
        <objectAnimator
            android:propertyName="rotation"
            android:valueFrom="0"
            android:valueTo="360"
            android:valueType="floatType"
            android:duration="2000">
        </objectAnimator>

        <set android:ordering="sequentially">
            <objectAnimator
                android:propertyName="alpha"
                android:valueFrom="1"
                android:valueTo="0"
                android:valueType="floatType"
                android:duration="1000">
            </objectAnimator>

            <objectAnimator
                android:propertyName="alpha"
                android:valueFrom="0"
                android:valueTo="1"
                android:valueType="floatType"
                android:duration="1000">
            </objectAnimator>
        </set>
    </set>
</set>
```
<set>标签有一个`ordering`属性, 它有两个值
- sequentially : 依次播放
- together : 同时播放

如何在代码中把文件加载进来并将动画启动呢？只需调用如下代码即可：

```
Animator animator = AnimatorInflater.loadAnimator(context, R.animator.anim_file);  
animator.setTarget(view);  
animator.start();  
```

调用AnimatorInflater的loadAnimator来将XML动画文件加载进来，然后再调用setTarget()方法将这个动画设置到某一个对象上面，最后再调用start()方法启动动画就可以了，就是这么简单。

####TypeEvaluator
>告诉系统动画是如何从一个值过渡到另一个值的

ValueAnimator.ofFloat()方法就是实现了初始值与结束值之间的平滑过度，它其实就是系统内置了一个FloatEvaluator，它通过计算告知动画系统如何从初始值过度到结束值，我们来看一下FloatEvaluator的代码实现：

```
public class FloatEvaluator implements TypeEvaluator {  
    public Object evaluate(float fraction, Object startValue, Object endValue) {  
        float startFloat = ((Number) startValue).floatValue();  
        return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);  
    }  
}  
```

可以看到，FloatEvaluator`实现了TypeEvaluator接口`，然后`重写evaluate()方法`。evaluate()方法当中传入了三个参数，第一个参数fraction非常重要，这个参数用于表示动画的完成度的，我们应该根据它来计算当前动画的值应该是多少，第二第三个参数分别表示动画的初始值和结束值。那么上述代码的逻辑就比较清晰了，`用结束值减去初始值，算出它们之间的差值，然后乘以fraction这个系数，再加上初始值，那么就得到当前动画的值了`。

####ValueAnimator使用Evaluator制定控件轨迹
>以我的感觉来说, 自定义Evaluator在ValueAnimator中的作用就是制定控件的运动轨迹, 比如自定义位移控件

首先定义个 Point 类用来记控件坐标的位置

```
public class Point 
{  
	private float x;  
    private float y;  
  
    public Point(float x, float y) 
    {  
        this.x = x;  
        this.y = y;  
    }
  
    public float getX() 
    {  
        return x;  
    }
  
    public float getY() 
    {  
        return y;  
    }
}
```

自定义Evaluator计算当前Point的位置, 并构成一个新的Point返回

```
public class PointEvaluator implements TypeEvaluator
{  
    @Override  
    public Object evaluate(float fraction, Object startValue, Object endValue) 
    {  
        Point startPoint = (Point) startValue;  
        Point endPoint = (Point) endValue;  
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());  
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());  
        Point point = new Point(x, y);  
        return point;  
    } 
} 
```

自定义一个控件 MyAnimView,

```
public class MyAnimView extends View 
{  
    public static final float RADIUS = 50f;  
    private Point currentPoint;  
    private Paint mPaint;  
  
    public MyAnimView(Context context, AttributeSet attrs) 
    {  
        super(context, attrs);  
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        mPaint.setColor(Color.BLUE);  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) 
    {  
        if (currentPoint == null) 
        {  
            currentPoint = new Point(RADIUS, RADIUS);  
            drawCircle(canvas);  
            startAnimation();  
        } 
        else 
        {  
            drawCircle(canvas);  
        }  
    }  
  
    private void drawCircle(Canvas canvas) 
    {  
        float x = currentPoint.getX();  
        float y = currentPoint.getY();  
        canvas.drawCircle(x, y, RADIUS, mPaint);  
    }  
  
    private void startAnimation() 
    {  
        Point startPoint = new Point(RADIUS, RADIUS);  
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);  
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);  
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {  
            @Override  
            public void onAnimationUpdate(ValueAnimator animation) {  
                currentPoint = (Point) animation.getAnimatedValue();  
                invalidate();  
            }  
        });  
        anim.setDuration(5000);  
        anim.start();  
    } 
}  
```

####ObjectAnimator使用Evaluator制定控件属性改变
>以我的感觉来说, 自定义Evaluator在ObjectAnimator中的作用就是改变控件某个属性的值, 比如改变控件颜色.

在MyAnimView中定义 `color` 属性

```
public class MyAnimView extends View 
{  
    ...  
  
    private String color;  
  
    public String getColor() 
    {  
        return color;  
    }  
  
	/**
     * 通过改变画笔颜色立即刷新视图,到达动态修改控件颜色的目的
     * @param color
     */
    public void setColor(String color) 
    {  
        this.color = color;  
        mPaint.setColor(Color.parseColor(color));  
        invalidate();  
    }  
  
    ...  
} 
```

创建ColorEvaluator并实现TypeEvaluator接口，代码如下所示：
>这段代码中色值计算的逻辑不是很懂啊......

```
public class ColorEvaluator implements TypeEvaluator 
{  
    private int mCurrentRed = -1;  
    private int mCurrentGreen = -1;  
    private int mCurrentBlue = -1;  
  
    @Override  
    public Object evaluate(float fraction, Object startValue, Object endValue) 
    {  
        String startColor = (String) startValue;  
        String endColor = (String) endValue;  
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);  
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);  
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);  
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);  
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);  
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);  
        // 初始化颜色的值  
        if (mCurrentRed == -1) {  
            mCurrentRed = startRed;  
        }  
        if (mCurrentGreen == -1) {  
            mCurrentGreen = startGreen;  
        }  
        if (mCurrentBlue == -1) {  
            mCurrentBlue = startBlue;  
        }  
        // 计算初始颜色和结束颜色之间的差值  
        int redDiff = Math.abs(startRed - endRed);  
        int greenDiff = Math.abs(startGreen - endGreen);  
        int blueDiff = Math.abs(startBlue - endBlue);  
        int colorDiff = redDiff + greenDiff + blueDiff;  
        if (mCurrentRed != endRed) {  
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,  
                    fraction);  
        } else if (mCurrentGreen != endGreen) {  
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,  
                    redDiff, fraction);  
        } else if (mCurrentBlue != endBlue) {  
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,  
                    redDiff + greenDiff, fraction);  
        }  
        // 将计算出的当前颜色的值组装返回  
        String currentColor = "#" + getHexString(mCurrentRed)  
                + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);  
        return currentColor;  
    }  
  
    /** 
     * 根据fraction值来计算当前的颜色。 
     */  
    private int getCurrentColor(int startColor, int endColor, int colorDiff,  
            int offset, float fraction) {  
        int currentColor;  
        if (startColor > endColor) {  
            currentColor = (int) (startColor - (fraction * colorDiff - offset));  
            if (currentColor < endColor) {  
                currentColor = endColor;  
            }  
        } else {  
            currentColor = (int) (startColor + (fraction * colorDiff - offset));  
            if (currentColor > endColor) {  
                currentColor = endColor;  
            }  
        }  
        return currentColor;  
    }  
      
    /** 
     * 将10进制颜色值转换成16进制。 
     */  
    private String getHexString(int value) 
    {  
        String hexString = Integer.toHexString(value);  
        if (hexString.length() == 1) {  
            hexString = "0" + hexString;  
        }  
        return hexString;  
    }  
}  
```

使用组合动画, 修改MyAnimView中的代码，如下所示：

```
public class MyAnimView extends View 
{  
    ...  
  
    private void startAnimation() 
    {  
        Point startPoint = new Point(RADIUS, RADIUS);  
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);  
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);  
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {  
            @Override  
            public void onAnimationUpdate(ValueAnimator animation) {  
                currentPoint = (Point) animation.getAnimatedValue();  
                invalidate();  
            }  
        });  
        
        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),   
                "#0000FF", "#FF0000");  
                
        AnimatorSet animSet = new AnimatorSet();  
        animSet.play(anim).with(anim2);  
        animSet.setDuration(5000);  
        animSet.start();  
    }  
} 
```

这样就可以实现控件色值的改变了....

####Interpolator
####ViewPropertyAnimator
这两篇请参考郭霖大神的博客
