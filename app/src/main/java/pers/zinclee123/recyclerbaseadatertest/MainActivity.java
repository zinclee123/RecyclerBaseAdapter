package pers.zinclee123.recyclerbaseadatertest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pers.zinclee123.recyclerbaseadpter.BaseAdapter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            strings.add("测试项" + i);
        }

        RecyclerView rcl = (RecyclerView) findViewById(R.id.rcl_test);
//        rcl.setLayoutManager(new GridLayoutManager(this, 2));
        rcl.setLayoutManager(new LinearLayoutManager(this));
        //inflate用rcl当parent，必须在rcl setLayoutManager方法之后，否则报错
        View headerView = getLayoutInflater().inflate(R.layout.layout_header, rcl, false);
        View footerView = getLayoutInflater().inflate(R.layout.layout_footer, rcl, false);
        rcl.setAdapter(new TestAdapter(this, headerView, footerView, strings));
    }


    class TestAdapter extends BaseAdapter<String, TestViewHolder> {

        public TestAdapter(@NonNull Context context, @Nullable View headerView, @Nullable View footerView, List<String> datas) {
            super(context, headerView, footerView, datas);
        }

        @Override
        protected TestViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.item_test, parent, false);
            return new TestViewHolder(v);
        }

        @Override
        protected void onBindItemViewHolder(TestViewHolder oHolder, int position) {
            oHolder.testTv.setText(getDatas().get(position));
        }

        @Override
        public void onItemClick(int position, String data) {

        }
    }

    class TestViewHolder extends BaseAdapter.BaseViewHolder {

        TextView testTv;

        public TestViewHolder(View itemView) {
            super(itemView);
            testTv = (TextView) itemView.findViewById(R.id.tv_test);
        }
    }

}
