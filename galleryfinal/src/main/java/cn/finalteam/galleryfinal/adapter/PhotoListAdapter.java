/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.finalteam.galleryfinal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import cn.finalteam.galleryfinal.GalleryHelper;
import cn.finalteam.galleryfinal.R;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * Desction:
 * Author:pengjianbo
 * Date:15/10/10 下午4:59
 */
public class PhotoListAdapter extends CommonBaseAdapter<PhotoListAdapter.PhotoViewHolder, PhotoInfo> {

    private Map<String, PhotoInfo> mSelectList;
    private int mScreenWidth;
    private int mPickMode;

    public PhotoListAdapter(Context context, List<PhotoInfo> list, Map<String, PhotoInfo> selectList, int screenWidth, int pickMode) {
        super(context, list);
        this.mSelectList = selectList;
        this.mScreenWidth = screenWidth;
        this.mPickMode = pickMode;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflate(R.layout.gf_adapter_photo_list_item, parent);
        setHeight(view);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        PhotoInfo photoInfo = mList.get(position);

        String path = "";
        if (photoInfo != null) {
            path = photoInfo.getThumbPath();
            if (StringUtils.isEmpty(path)) {
                path = photoInfo.getPhotoPath();
            }
        }
        path = "file:/" + path;

        holder.mIvThumb.setImageResource(R.drawable.ic_gf_default_photo);
        GalleryHelper.mImageLoader.displayImage(holder.mIvThumb, path);

        if ( mPickMode == GalleryHelper.MULTIPLE_IMAGE ) {
            holder.mIvCheck.setVisibility(View.VISIBLE);
            holder.mIvCheck.setImageDrawable(createCheckIcon(Color.WHITE, R.drawable.ic_gf_done));
            if (mSelectList.get(photoInfo.getPhotoPath()) != null) {
                TypedValue typedValue = new TypedValue();
                mContext.getTheme().resolveAttribute(R.attr.colorTheme, typedValue, true);
                int colorTheme = typedValue.data;
                holder.mIvCheck.setBackgroundColor(colorTheme);
            } else {
                holder.mIvCheck.setBackgroundColor(mContext.getResources().getColor(R.color.gf_gray));
            }
        } else {
            holder.mIvCheck.setVisibility(View.GONE);
        }
    }


    private void setHeight(final View convertView) {
        int height = mScreenWidth / 3 - 8;
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    public static class PhotoViewHolder extends CommonBaseAdapter.ViewHolder {

        public ImageView mIvThumb;
        public ImageView mIvCheck;

        public PhotoViewHolder(View view) {
            super(view);
            mIvThumb = (ImageView) view.findViewById(R.id.iv_thumb);
            mIvCheck = (ImageView) view.findViewById(R.id.iv_check);
        }
    }
}
