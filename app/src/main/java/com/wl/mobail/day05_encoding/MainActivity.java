package com.wl.mobail.day05_encoding;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = ((DrawerLayout) findViewById(R.id.drawer));
        toggle = new ActionBarDrawerToggle(this, drawer, 0, 0);
        /*
ActionBarDrawerToggle  是 DrawerLayout.DrawerListener实现。
和 NavigationDrawer 搭配使用，推荐用这个方法，符合Android design规范。
作用：
1.改变android.R.id.home返回图标。
2.Drawer拉出、隐藏，带有android.R.id.home动画效果。
3.监听Drawer拉出、隐藏；

*/
        drawer.setDrawerListener(toggle);// 给抽屉Layout绑定切换器监听
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (menuItem.getItemId()){
            case R.id.md5:
                transaction.replace(R.id.container, new Md5Fragment());
                break;
            case R.id.base64:
                transaction.replace(R.id.container, new Base64Fragment());
                break;
            case R.id.des:
                transaction.replace(R.id.container, new DesFragment());
                break;
        }
        transaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
