package com.example.yangchun.classreflex;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 反射构建 Student 对象
     */
    private void createStudent(){
        Class studentClass = Student.class;
        // 参数类型为一个 String 的构造函数
        Constructor constructor = null;
        try {
            constructor = studentClass.getConstructor(new Class[]{String.class});
            // 实例化 student 对象
            Student student = null;
            student = (Student)constructor.newInstance("Li Lei");
            Log.i("yc-createStudent",student.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射修改私有变量
     */
    private void updatePrivateVariable(){
        try {
            Student student = new Student("Han MeiMei");
            Log.i("yc-update-first","origin grade is " + student.getGrade());

            Class studentClass = Student.class;
            /**
             * 获取声明的 grade 字段，这里要注意 getField 和 getDeclaredField 的区别
                getDeclaredField是可以获取一个类的所有字段,getField只能获取类的public 字段.
              */
            Field gradeField = null;
            gradeField = studentClass.getDeclaredField("grade");
            // 如果是 private 或者 package 权限的，一定要赋予其访问权限
            gradeField.setAccessible(true);
            // 修改 student 对象中的 Grade 字段值
            gradeField.set(student, 2);
            Log.i("yc-update-after","after reflection grade is " + student.getGrade());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射调用有参数的私有方法
     */
    private void callPrivateMethodHaveParam(){
        try {
            Student student = new Student("Han MeiMei");

            // 获取私有方法，同样注意 getMethod 和 getDeclaredMethod 的区别
            Method goMethod = Student.class.getDeclaredMethod("goToSchool", new Class[]{String.class});
            // 赋予访问权限
            goMethod.setAccessible(true);

            // 调用 goToSchool 方法。
            goMethod.invoke(student, "1111");

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射调用没有参数的私有方法
     */
    private void callPrivateMethodNoParam(){
        try {
            Student student = new Student("Han MeiMei");

            // 获取私有方法，同样注意 getMethod 和 getDeclaredMethod 的区别
            Method goMethod = Student.class.getDeclaredMethod("goToSchool");
            // 赋予访问权限
            goMethod.setAccessible(true);

            // 调用 goToSchool 方法。
            goMethod.invoke(student);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                createStudent();
                break;
            case R.id.btn2:
                updatePrivateVariable();
                break;
            case R.id.btn3:
                callPrivateMethodHaveParam();
                break;
            case R.id.btn4:
                callPrivateMethodNoParam();
                break;
        }
    }
}
