package com.example.shp.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.example.shp.myapplication.R;
import com.example.shp.myapplication.widget.AutoScrollViewPager;

import net.tsz.afinal.FinalBitmap;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sks on 2016/1/21.
 */
public class FirstFragment extends Fragment {
    private AutoScrollViewPager mPosterPager;
    private LinearLayout pointsLayout;
    private Context context;
    private FinalBitmap finalBitmap;
    private int index = 0;
    /**
     * 广告图片
     */
    private List<String> posterImage = null;

    /**
     * 标记点集合
     */
    private List<ImageView> points = null;
    /**
     * 广告个数
     */
    private int count = 3;
    /**
     * 循环间隔
     */
    private int interval = 4000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);

        mPosterPager = (AutoScrollViewPager) view.findViewById(R.id.poster_pager);
        pointsLayout = (LinearLayout) view.findViewById(R.id.points);

        init();
        initPoints();
        initPoster();

        return view;
    }

    private void init() {
        context = getActivity();

        points = new LinkedList<ImageView>();
        posterImage = new LinkedList<String>();
        finalBitmap = FinalBitmap.create(context);

        posterImage
                .add("http://c.hiphotos.baidu.com/image/h%3D800%3Bcrop%3D0%2C0%2C1280%2C800/sign=4d9580fd7b310a55db24d3f4877e20db/43a7d933c895d14317e8fe2d71f082025aaf0725.jpg");
        posterImage
                .add("http://f.hiphotos.baidu.com/image/h%3D800%3Bcrop%3D0%2C0%2C1280%2C800/sign=0664044d9045d688bc02bfa494f91e6c/7e3e6709c93d70cffe5e6773fadcd100baa12b2c.jpg");
        posterImage
                .add("http://c.hiphotos.baidu.com/image/h%3D800%3Bcrop%3D0%2C0%2C1280%2C800/sign=35fce30599504fc2bd5fbd05d5e68463/caef76094b36acaf340ff9f17ed98d1000e99ccb.jpg");
//        posterImage
//                .add("http://e.hiphotos.baidu.com/image/h%3D800%3Bcrop%3D0%2C0%2C1280%2C800/sign=35418a60544e9258b9348beeacb9b22a/b58f8c5494eef01f064b7ac1e2fe9925bc317d52.jpg");
    }

    private void initPoints() {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 15, 8, 15);

        for (int i = 0; i < count; i++) {
            // 添加标记点
            ImageView point = new ImageView(context);

            if (i == index % count) {
                point.setBackgroundResource(R.mipmap.lunbodianshishi2x);
            } else {
                point.setBackgroundResource(R.mipmap.lunbodianshikong2x);
            }
            point.setLayoutParams(lp);

            points.add(point);
            pointsLayout.addView(point);
        }
    }

    private void initPoster() {
        // 设置 ViewPager的高度为屏幕宽度的1/5
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT ); //(int) (getScreen(getActivity()).widthPixels /2)
        mPosterPager.setLayoutParams(params);

        mPosterPager.setAdapter(new PosterPagerAdapter());
        mPosterPager.setCurrentItem(count * 500);
        mPosterPager.setInterval(interval);
        mPosterPager.setOnPageChangeListener(new PosterPageChange());
        mPosterPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
        mPosterPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosterPager.stopAutoScroll();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        mPosterPager.startAutoScroll();

                        break;
                    case MotionEvent.ACTION_UP:
                        mPosterPager.startAutoScroll();

                        break;

                    default:
                        break;
                }

                return false;
            }

        });
    }

    /**
     * 获取屏幕宽高
     *
     * @param activity
     * @return
     */
    public DisplayMetrics getScreen(Activity activity) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPosterPager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPosterPager.stopAutoScroll();
    }

    class PosterPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            // 调整图片大小
            imageView.setScaleType(ScaleType.CENTER_CROP);

            android.view.ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);

//            bitmapUtils.display(imageView, posterImage.get(position % count));

            finalBitmap.configBitmapLoadThreadSize(3);
//            finalBitmap.configDiskCachePath("/sdcard/com.panwang.bingoso/cache/pic");
            finalBitmap.configDiskCacheSize(3 * 1024);
            Bitmap defaultPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            Bitmap failPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            finalBitmap.display(imageView, posterImage.get(position % count), defaultPic, failPic);

            ((ViewPager) container).addView(imageView);

            imageView.setOnClickListener(new PosterClickListener(position % count));

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ((ViewPager) container).removeView((ImageView) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    class PosterPageChange implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            index = position;
            for (int i = 0; i < count; i++) {
                points.get(i).setBackgroundResource(R.mipmap.lunbodianshikong2x);
            }

            points.get(position % count).setBackgroundResource(R.mipmap.lunbodianshishi2x);
        }

    }

    class PosterClickListener implements OnClickListener {

        private int position;

        public PosterClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            mPosterPager.stopAutoScroll();

            Toast.makeText(context, "position---->" + position, Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(getActivity(), DetialActivity.class);
//            intent.putExtra("BEAN", companylist.get(position));
//            intent.putExtra("FLAG", 3);
//            startActivity(intent);

        }

    }
}
