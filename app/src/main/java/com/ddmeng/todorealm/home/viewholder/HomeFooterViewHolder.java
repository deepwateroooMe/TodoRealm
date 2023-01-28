package com.ddmeng.todorealm.home.viewholder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.home.HomeListAdapter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFooterViewHolder extends RecyclerView.ViewHolder {

    private HomeListAdapter.HomeListCallback callback;

    public HomeFooterViewHolder(View itemView, HomeListAdapter.HomeListCallback callback) {
        super(itemView);
        this.callback = callback;
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.footer_text)
    void onItemClicked() {
        callback.onCreateListClicked();
    }
}
