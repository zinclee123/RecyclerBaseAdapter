package pers.zinclee123.recyclerbaseadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyanjin on 2017/5/23.
 */

public abstract class BaseAdapter<T, VH extends BaseAdapter.BaseViewHolder> extends RecyclerView.Adapter<VH> {

    public static final int VH_HEADER = 1;
    public static final int VH_FOOTER = 2;
    public static final int VH_NORMAL = 3;

    protected View headerView;

    protected View footerView;

    List<T> datas;

    protected Context context;

    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        if (headerView != null) {
            return new BaseViewHolder(headerView);
        }
        return null;
    }

    protected BaseViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        if (footerView != null) {
            return new BaseViewHolder(footerView);
        }
        return null;
    }

    protected abstract void onBindItemViewHolder(final VH oHolder, final int position);

    protected void onBindHeaderViewHolder(final VH oHolder, final int position) {

    }

    protected void onBindFooterViewHolder(final VH oHolder, final int position) {

    }

    public BaseAdapter(@NonNull Context context,
                       List<T> datas) {
        this.datas = datas;
        this.context = context;
    }

    public BaseAdapter(@NonNull Context context,
                       @Nullable View headerView,
                       List<T> datas) {
        this.datas = datas;
        this.headerView = headerView;
        this.context = context;
    }

    public BaseAdapter(@NonNull Context context,
                       @Nullable View headerView,
                       @Nullable View footerView,
                       List<T> datas) {
        this.headerView = headerView;
        this.footerView = footerView;
        this.datas = datas;
        this.context = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VH_HEADER) {
            VH vh = (VH) onCreateHeaderViewHolder(parent, viewType);
            if (vh != null) {
                headerView = vh.itemView;
            }
            return vh;
        } else if (viewType == VH_FOOTER) {
            VH vh = (VH) onCreateFooterViewHolder(parent, viewType);
            if (vh != null) {
                footerView = vh.itemView;
            }
            return vh;
        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                //这个方法的返回值决定了我们每个position上的item占据的单元格个数
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == VH_HEADER) {
                        return gridManager.getSpanCount();
                    } else if (getItemViewType(position) == VH_FOOTER) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = VH_NORMAL;
        if (position == 0 && headerView != null) {
            type = VH_HEADER;
        } else if (position == getItemCount() - 1 && footerView != null) {
            type = VH_FOOTER;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (datas != null)
            size = datas.size();
        if (headerView != null)
            size++;
        if (footerView != null)
            size++;
        return size;
    }

    @Override
    public void onBindViewHolder(final VH oHolder, final int position) {
        if (getItemViewType(position) == VH_HEADER) {
            onBindHeaderViewHolder(oHolder, 0);
        } else if (getItemViewType(position) == VH_FOOTER) {
            onBindFooterViewHolder(oHolder, 0);
        } else {
            final int realPos = getRealPosition(oHolder);
            oHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(realPos, datas.get(realPos));
                }
            });
            onBindItemViewHolder(oHolder, realPos);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (headerView != null)
            position = position - 1;
        return position;
    }

    public abstract void onItemClick(int position, T data);

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas) {
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyDataSetChanged();
    }

    public View getFooterView() {
        return footerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
        notifyDataSetChanged();
    }


    //必须继承BaseViewHolder,因为RecyclerView.ViewHolder是抽象类，无法实例化
    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

}
