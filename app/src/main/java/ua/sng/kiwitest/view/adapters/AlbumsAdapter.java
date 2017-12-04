package ua.sng.kiwitest.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.listeners.OnAlbumItemClickedListener;
import ua.sng.kiwitest.model.entities.album.AlbumModel;
import ua.sng.kiwitest.utils.DpPxUtils;
import ua.sng.kiwitest.utils.GlideApp;

/**
 * Created by sanug on 04.12.2017.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumItemHolder>{

    private Context context;
    private ArrayList<AlbumModel> albumModels;
    private OnAlbumItemClickedListener itemClickListener;

    @Inject
    public AlbumsAdapter(Context context){
        this.context = context;
        this.albumModels = new ArrayList<>();
    }

    public void setupData(ArrayList<AlbumModel> albumModels){
        this.albumModels.clear();

        if(albumModels != null && albumModels.size() > 0){
            this.albumModels.addAll(albumModels);
            notifyDataSetChanged();
        }
    }

    public void setItemClickListener(OnAlbumItemClickedListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public AlbumItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_cover_item, parent, false);
        return new AlbumItemHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumItemHolder holder, int position) {

        AlbumModel albumModel = albumModels.get(position);

        if(albumModel.getPhotoModel() != null){

            GlideApp.with(context)
                    .load(albumModel.getPhotoModel().getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .override(holder.coverImgSize)
                    .into(holder.coverImg);
        }

        holder.albumName.setText(albumModel.getName());
        holder.photosCount.setText(context
                .getString(R.string.photos_count_template, albumModel.getPhotoCount()));

        holder.mainItemContainer.setOnClickListener(view ->
                itemClickListener.onAlbumItemClicked(albumModel));
    }

    @Override
    public int getItemCount() {
        return albumModels.size();
    }

    class AlbumItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.album_item_container)
        LinearLayout mainItemContainer;

        @BindView(R.id.album_item_cover_img)
        ImageView coverImg;

        @BindView(R.id.album_item_name_txt)
        TextView albumName;

        @BindView(R.id.album_item_photos_count_txt)
        TextView photosCount;

        int coverImgSize;

        public AlbumItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            int screenWidth = itemView.getContext().getResources().getDisplayMetrics().widthPixels;
            int dividersTotalSize = DpPxUtils.dpToPx(itemView.getContext(), 20);

            coverImgSize = (screenWidth - dividersTotalSize) / 2;

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) coverImg.getLayoutParams();
            params.width = coverImgSize;
            params.height = coverImgSize;
            coverImg.setLayoutParams(params);
        }
    }

}
