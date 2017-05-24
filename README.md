# RecyclerBaseAdapter
继承自RecyclerView.Adapter,可简单添加header和footer

## 示例(Demo)
<p><img src="https://github.com/zinclee123/RecyclerBaseAdapter/blob/master/img/Demo.gif?raw=true?raw=true" width="320" alt="Screenshot"/></p>

## 用法(Usage)
### Step 1.
在项目的根build.gradle文件中加入<br/>
```
allprojects {
   repositories {
      ...
      maven { url 'https://jitpack.io' }
   }
}
   ```
### Step 2.
在你需要使用该控件的module的build.gradle中加入依赖<br/>
```
dependencies {
  compile 'com.github.zinclee123:RecyclerBaseAdapter-Android:v0.1'
}
```
### Step 3.
实现一个ViewHolder,务必继承BaseAdapter.BaseViewHolder,如下</br>
```java
class TestViewHolder extends BaseAdapter.BaseViewHolder {

    TextView testTv;

    public TestViewHolder(View itemView) {
        super(itemView);
        testTv = (TextView) itemView.findViewById(R.id.tv_test);
    }
}
```
实现一个Adapter,如下</br>
```java
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
```
最后使用这个Adapter,header、footer可以在构造函数中，也可以通过set方法设置，如下</br>
```java
RecyclerView rcl = (RecyclerView) findViewById(R.id.rcl_test);
// rcl.setLayoutManager(new GridLayoutManager(this, 2));
rcl.setLayoutManager(new LinearLayoutManager(this));
//inflate用rcl当parent，必须在rcl setLayoutManager方法之后，否则报错
View headerView = getLayoutInflater().inflate(R.layout.layout_header, rcl, false);
View footerView = getLayoutInflater().inflate(R.layout.layout_footer, rcl, false);
rcl.setAdapter(new TestAdapter(this, headerView, footerView, strings));
```

### 注意(Notice)
1.如果在inflate header、footer时，以recyclerView为parent时，需要在recyclerView设置layoutManager之后调用，否则报错，报错提示recyclerView没有layoutManager</br>
2.目前仅支持LinearLayoutManager和GridLayoutManager</br>
