# android中自定义Interpolator
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Interpolator是安卓动画执行中的插值器,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;决定了动画进行过程中相关属性值的变化模式,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用方式如下:<br>
```
ValueAnimator anim = ValueAnimator.ofObject(new objecyEvaluator(),object1,object2);
anim.setDuration(5000);
anim.setInterpolator(new BounceInterpolator());
anim.start();
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### TimeInterpolator接口
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自定义Interpolator需要使用TimeInterpolator接口<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关于TimeInterpolator接口的定义如下:<br>
```
/**
 * A time interpolator defines the rate of change of an animation. This allows animations
 * to have non-linear motion, such as acceleration and deceleration.
 */
public interface TimeInterpolator {
 
    /**
     * Maps a value representing the elapsed fraction of an animation to a value that represents
     * the interpolated fraction. This interpolated value is then multiplied by the change in
     * value of an animation to derive the animated value at the current elapsed animation time.
     *
     * @param input A value between 0 and 1.0 indicating our current point
     *        in the animation where 0 represents the start and 1.0 represents
     *        the end
     * @return The interpolation value. This value can be more than 1.0 for
     *         interpolators which overshoot their targets, or less than 0 for
     *         interpolators that undershoot their targets.
     */
    float getInterpolation(float input);
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可以看到接口中有一个getInterpolation()方法。该方法接收一个input参数,这个参数的值会随着动画的运行而不断变化,不过它的变化是有规律的,就是根据设定的动画时长均匀增加，变化范围是0到1。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;先看系统提供的LinearInterpolator是怎样编写的
```

/**
 * An interpolator where the rate of change is constant
 */
@HasNativeInterpolator
public class LinearInterpolator extends BaseInterpolator implements NativeInterpolatorFactory {
 
    public LinearInterpolator() {
    }
 
    public LinearInterpolator(Context context, AttributeSet attrs) {
    }
 
    public float getInterpolation(float input) {
        return input;
    }
 
    /** @hide */
    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createLinearInterpolator();
    }
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;而要利用该接口编写自定义的Interpolator可以如下:<br>
```
public class DecelerateAccelerateInterpolator implements TimeInterpolator{
 
    @Override
    public float getInterpolation(float input) {
        float result;
        if (input <= 0.5) {
            result = (float) (Math.sin(Math.PI * input)) / 2;
        } else {
            result = (float) (2 - Math.sin(Math.PI * input)) / 2;
        }
        return result;
    }
 
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
