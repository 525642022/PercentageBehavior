实现一个自定义百分比移动判断的基类和两个动画效果
喜欢其它效果可以模仿实现比较简单
先看一下最终实现效果
![实现结果](https://github.com/525642022/PercentageBehavior/blob/master/screenhot/test2.gif)  
简单使用


    <ImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_gravity="top"
        android:layout_marginLeft="30dp"
        android:layout_marginTop="80dp"
        android:elevation="10dp"
        android:src="@drawable/default_user_iocn"
        app:behavior_dependTarget="-150dp"
        app:behavior_dependsOn="@+id/app_bar"
        app:behavior_targetHeight="40dp"
        app:behavior_targetWidth="40dp"
        app:behavior_targetX="180dp"
        app:behavior_targetY="8dp"
        app:layout_behavior="@string/simple_view_behavior" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="120dp"
        android:layout_marginTop="80dp"
        android:text="姓名:ljc"
        android:textColor="@color/colorwhite"
        android:textSize="18sp"
        android:textStyle="bold"
        app:behavior_dependTarget="-200dp"
        app:behavior_dependsOn="@+id/app_bar"
        app:behavior_targetAlpha="0"
        app:layout_behavior="@string/alpha_view_behavior"
        />
        
 自定义属性介绍
        behavior_dependsOn     监听依赖的布局的id
        behavior_dependTarget  到达100%滑动的距离
        behavior_targetX       位移动画最终X位置
        behavior_targetY       位移动画最终Y位置
        behavior_targetWidth   缩放动画最终宽度
        behavior_targetHeight  缩放动画最终高度
        behavior_targetAlpha   透明度动画最终透明度
    
 详细介绍博客地址 https://www.jianshu.com/p/0ef396d10796

