package npBase.BaseCommon.base.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public class NpBaseRecycleTag extends RecyclerView.ViewHolder {
    public View itemView;

    public NpBaseRecycleTag(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this,itemView);
    }
}