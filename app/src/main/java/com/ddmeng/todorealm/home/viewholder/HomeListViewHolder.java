package com.ddmeng.todorealm.home.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.home.HomeListAdapter;
import com.ddmeng.todorealm.ui.multiselect.MultiSelector;
import com.ddmeng.todorealm.ui.multiselect.SelectableHolder;
import com.ddmeng.todorealm.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class HomeListViewHolder extends RecyclerView.ViewHolder implements SelectableHolder {
    @BindView(R.id.title)
    TextView titleTextView;
    @BindView(R.id.number)
    TextView numberTextView;

    private HomeListAdapter.HomeListCallback callback;
    private MultiSelector multiSelector;
    private TodoList list;

    public HomeListViewHolder(View itemView, HomeListAdapter.HomeListCallback callback, MultiSelector multiSelector) {
        super(itemView);
        this.callback = callback;
        this.multiSelector = multiSelector;
        ButterKnife.bind(this, itemView);
    }

    public void populate(TodoList list) {
        LogUtils.d("getItemId: " + getItemId());
        this.list = list;
        multiSelector.bindHolder(this, getAdapterPosition());
        titleTextView.setText(list.getTitle());
        int tasksCount = getTodoTaskCount(list);
        numberTextView.setText(String.valueOf(tasksCount));
    }

    @OnClick(R.id.list_item_container)
    void onItemClick() {
        if (!multiSelector.tapSelection(this)) {
            callback.onListItemClicked(list);
        }
    }

    @OnLongClick(R.id.list_item_container)
    boolean onItemLongClicked() {
        if (!multiSelector.isSelectable()) {
            multiSelector.setSelectable(true);
            multiSelector.setSelected(this, true);
            callback.onListItemLongClicked(list);
            return true;
        }

        return false;
    }

//    @Override
//    public int getAdapterPosition() {
//        return 0;
//    }

    @Override
    public void setActivated(boolean activated) {
        itemView.setActivated(activated);
    }

//    @Override
//    public long getItemId() {
//        return 0;
//    }

    private int getTodoTaskCount(TodoList list) {
        int count = 0;
        for (Task task : list.getTasks()) {
            if (!task.isDone()) {
                count++;
            }
        }
        return count;
    }

}
