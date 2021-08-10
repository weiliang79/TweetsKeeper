package com.weiliang79.tweetskeeper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterMedia;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImagePreviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    class OneImagePreviewViewHolder extends RecyclerView.ViewHolder {

        final ImagePreviewAdapter adapter;

        OneImagePreviewViewHolder(View itemView, ImagePreviewAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }

    }

    class TwoImagePreviewViewHolder extends RecyclerView.ViewHolder {

        final ImagePreviewAdapter adapter;

        TwoImagePreviewViewHolder(View itemView, ImagePreviewAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }

    }

    class ThreeImagePreviewViewHolder extends RecyclerView.ViewHolder {

        final ImagePreviewAdapter adapter;

        ThreeImagePreviewViewHolder(View itemView, ImagePreviewAdapter adapter){
            super(itemView);
            this.adapter = adapter;
        }

    }

    class FourImagePreviewViewHolder extends RecyclerView.ViewHolder {

        final ImagePreviewAdapter adapter;

        FourImagePreviewViewHolder(View itemView, ImagePreviewAdapter adapter){
            super(itemView);
            this.adapter = adapter;
        }

    }

    private LayoutInflater inflater;
    private List<TwitterMedia> twitterMediaList;
    final Context context;

    public ImagePreviewAdapter(Context context, List<TwitterMedia> twitterMediaList){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.twitterMediaList = twitterMediaList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch(twitterMediaList == null ? 0 : twitterMediaList.size()){
            case 1: return new OneImagePreviewViewHolder(inflater.inflate(R.layout.image_item_one, parent, false), this);
            case 2: return new TwoImagePreviewViewHolder(inflater.inflate(R.layout.image_item_two, parent, false), this);
            case 3: return new ThreeImagePreviewViewHolder(inflater.inflate(R.layout.image_item_three, parent, false), this);
            case 4: return new FourImagePreviewViewHolder(inflater.inflate(R.layout.image_item_four, parent, false), this);
            default: return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (twitterMediaList == null ? 0 : twitterMediaList.size()){
            case 1:
                ShapeableImageView imagePreview = holder.itemView.findViewById(R.id.image_preview_one);
                ShapeableImageView imagePreviewType = holder.itemView.findViewById(R.id.image_preview_one_type);


                Glide.with(context)
                        .load(twitterMediaList.get(0).getUrl())
                        .placeholder(R.drawable.ic_image)
                        .error(R.drawable.ic_image_broke)
                        .fallback(R.drawable.ic_image)
                        .into(imagePreview);

                if(twitterMediaList.get(0).getType().equals("animated_gif")){
                    imagePreviewType.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewType);
                } else if(twitterMediaList.get(0).getType().equals("video")){
                    imagePreviewType.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewType);
                } else {
                    imagePreviewType.setAlpha(0.0f);
                }

                break;

            case 2:
                ShapeableImageView imagePreviewTwo1 = holder.itemView.findViewById(R.id.image_preview_two_1);
                ShapeableImageView imagePreviewTwo2 = holder.itemView.findViewById(R.id.image_preview_two_2);
                ShapeableImageView imagePreviewTwo1Type = holder.itemView.findViewById(R.id.image_preview_two_1_type);
                ShapeableImageView imagePreviewTwo2Type = holder.itemView.findViewById(R.id.image_preview_two_2_type);

                Glide.with(context)
                        .load(twitterMediaList.get(0).getUrl())
                        .into(imagePreviewTwo1);

                if(twitterMediaList.get(0).getType().equals("animated_gif")){
                    imagePreviewTwo1Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewTwo1Type);
                } else if(twitterMediaList.get(0).getType().equals("video")){
                    imagePreviewTwo1Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewTwo1Type);
                } else {
                    imagePreviewTwo1Type.setAlpha(0.0f);
                }

                Glide.with(context)
                        .load(twitterMediaList.get(1).getUrl())
                        .into(imagePreviewTwo2);

                if(twitterMediaList.get(1).getType().equals("animated_gif")){
                    imagePreviewTwo2Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewTwo2Type);
                } else if(twitterMediaList.get(1).getType().equals("video")){
                    imagePreviewTwo2Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewTwo2Type);
                } else {
                    imagePreviewTwo2Type.setAlpha(0.0f);
                }

                break;

            case 3:
                ShapeableImageView imagePreviewThree1 = holder.itemView.findViewById(R.id.image_preview_three_1);
                ShapeableImageView imagePreviewThree2 = holder.itemView.findViewById(R.id.image_preview_three_2);
                ShapeableImageView imagePreviewThree3 = holder.itemView.findViewById(R.id.image_preview_three_3);
                ShapeableImageView imagePreviewThree1Type = holder.itemView.findViewById(R.id.image_preview_three_1_type);
                ShapeableImageView imagePreviewThree2Type = holder.itemView.findViewById(R.id.image_preview_three_2_type);
                ShapeableImageView imagePreviewThree3Type = holder.itemView.findViewById(R.id.image_preview_three_3_type);

                Glide.with(context)
                        .load(twitterMediaList.get(0).getUrl())
                        .into(imagePreviewThree1);

                if(twitterMediaList.get(0).getType().equals("animated_gif")){
                    imagePreviewThree1Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewThree1Type);
                } else if(twitterMediaList.get(0).getType().equals("video")){
                    imagePreviewThree1Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewThree1Type);
                } else {
                    imagePreviewThree1Type.setAlpha(0.0f);
                }

                Glide.with(context)
                        .load(twitterMediaList.get(1).getUrl())
                        .into(imagePreviewThree2);

                if(twitterMediaList.get(1).getType().equals("animated_gif")){
                    imagePreviewThree2Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewThree2Type);
                } else if(twitterMediaList.get(1).getType().equals("video")){
                    imagePreviewThree2Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewThree2Type);
                } else {
                    imagePreviewThree2Type.setAlpha(0.0f);
                }

                Glide.with(context)
                        .load(twitterMediaList.get(2).getUrl())
                        .into(imagePreviewThree3);

                if(twitterMediaList.get(2).getType().equals("animated_gif")){
                    imagePreviewThree3Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewThree3Type);
                } else if(twitterMediaList.get(2).getType().equals("video")){
                    imagePreviewThree3Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewThree3Type);
                } else {
                    imagePreviewThree3Type.setAlpha(0.0f);
                }

                break;

            case 4:
                ShapeableImageView imagePreviewFour1 = holder.itemView.findViewById(R.id.image_preview_four_1);
                ShapeableImageView imagePreviewFour2 = holder.itemView.findViewById(R.id.image_preview_four_2);
                ShapeableImageView imagePreviewFour3 = holder.itemView.findViewById(R.id.image_preview_four_3);
                ShapeableImageView imagePreviewFour4 = holder.itemView.findViewById(R.id.image_preview_four_4);
                ShapeableImageView imagePreviewFour1Type = holder.itemView.findViewById(R.id.image_preview_four_1_type);
                ShapeableImageView imagePreviewFour2Type = holder.itemView.findViewById(R.id.image_preview_four_2_type);
                ShapeableImageView imagePreviewFour3Type = holder.itemView.findViewById(R.id.image_preview_four_3_type);
                ShapeableImageView imagePreviewFour4Type = holder.itemView.findViewById(R.id.image_preview_four_4_type);

                Glide.with(context)
                        .load(twitterMediaList.get(0).getUrl())
                        .into(imagePreviewFour1);

                if(twitterMediaList.get(0).getType().equals("animated_gif")){
                    imagePreviewFour1Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewFour1Type);
                } else if(twitterMediaList.get(0).getType().equals("video")){
                    imagePreviewFour1Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewFour1Type);
                } else {
                    imagePreviewFour1Type.setAlpha(0.0f);
                }

                Glide.with(context)
                        .load(twitterMediaList.get(1).getUrl())
                        .into(imagePreviewFour2);

                if(twitterMediaList.get(1).getType().equals("animated_gif")){
                    imagePreviewFour2Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewFour2Type);
                } else if(twitterMediaList.get(1).getType().equals("video")){
                    imagePreviewFour2Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewFour2Type);
                } else {
                    imagePreviewFour2Type.setAlpha(0.0f);
                }

                Glide.with(context)
                        .load(twitterMediaList.get(2).getUrl())
                        .into(imagePreviewFour3);

                if(twitterMediaList.get(2).getType().equals("animated_gif")){
                    imagePreviewFour3Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewFour3Type);
                } else if(twitterMediaList.get(2).getType().equals("video")){
                    imagePreviewFour3Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewFour3Type);
                } else {
                    imagePreviewFour3Type.setAlpha(0.0f);
                }

                Glide.with(context)
                        .load(twitterMediaList.get(3).getUrl())
                        .into(imagePreviewFour4);

                if(twitterMediaList.get(3).getType().equals("animated_gif")){
                    imagePreviewFour4Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_gif)
                            .into(imagePreviewFour4Type);
                } else if(twitterMediaList.get(3).getType().equals("video")){
                    imagePreviewFour4Type.bringToFront();

                    Glide.with(context)
                            .load(R.drawable.ic_video)
                            .into(imagePreviewFour4Type);
                } else {
                    imagePreviewFour4Type.setAlpha(0.0f);
                }

            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
