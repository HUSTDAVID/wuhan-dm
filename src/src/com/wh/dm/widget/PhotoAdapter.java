
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.activity.PhotosDetailsActivity;
import com.wh.dm.type.Photo;
import com.wh.dm.type.TwoPhotos;
import com.wh.dm.util.UrlImageViewHelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotoAdapter extends BaseAdapter {

    ArrayList<TwoPhotos> twoPhotos;
    LayoutInflater mInflater;
    Context context;

    public PhotoAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<TwoPhotos> _twoPhotos) {

        twoPhotos = _twoPhotos;
        notifyDataSetChanged();
    }

    public void addList(ArrayList<TwoPhotos> _twoPhotos) {

        twoPhotos.addAll(_twoPhotos);
        notifyDataSetChanged();
    }

    public ArrayList<TwoPhotos> getList() {

        return twoPhotos;
    }

    @Override
    public int getCount() {

        if (twoPhotos == null) {
            return 0;
        } else {
            return twoPhotos.size();
        }
    }

    @Override
    public Object getItem(int position) {

        return twoPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.photos_item, null);

            holder.imgLeft = (ImageView) convertView.findViewById(R.id.img_left_potos_item);
            holder.txtLeftTitle = (TextView) convertView.findViewById(R.id.txt_left_photos_title);
            holder.txtLeftReview = (TextView) convertView.findViewById(R.id.txt_left_review_total);
            holder.txtLeftNum = (TextView) convertView.findViewById(R.id.txt_left_num_total);

            holder.imgRight = (ImageView) convertView.findViewById(R.id.img_right_potos_item);
            holder.txtRightTitle = (TextView) convertView.findViewById(R.id.txt_right_photos_title);
            holder.txtRightReview = (TextView) convertView
                    .findViewById(R.id.txt_right_review_total);
            holder.txtRightNum = (TextView) convertView.findViewById(R.id.txt_right_num_total);
            final PhotoMsg left = new PhotoMsg();
            left.setAid(twoPhotos.get(position).getLeftPhoto().getAid());
            left.setTitle(twoPhotos.get(position).getLeftPhoto().getTitle());
            left.setDescription(twoPhotos.get(position).getLeftPhoto().getDescription());
            final PhotoMsg right = new PhotoMsg();
            right.setAid(twoPhotos.get(position).getRightPhoto().getAid());
            right.setTitle(twoPhotos.get(position).getRightPhoto().getTitle());
            right.setDescription(twoPhotos.get(position).getRightPhoto().getDescription());
            holder.imgLeft.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    startActivity(left);
                }
            });

            holder.imgRight.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    startActivity(right);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Photo leftPhoto = twoPhotos.get(position).getLeftPhoto();
        Photo rightPhot = twoPhotos.get(position).getRightPhoto();

        holder.txtLeftTitle.setText(leftPhoto.getAname());
        holder.txtLeftReview.setText(String.valueOf(leftPhoto.getFcount()));
        holder.txtLeftNum.setText(String.valueOf(leftPhoto.getPcount()));

        holder.txtRightTitle.setText(rightPhot.getAname());
        holder.txtRightReview.setText(String.valueOf(rightPhot.getFcount()));
        holder.txtRightNum.setText(String.valueOf(rightPhot.getPcount()));

        if (WH_DMApp.isLoadImg) {
            UrlImageViewHelper.setUrlDrawable(holder.imgLeft,
                    WH_DMHttpApiV1.URL_DOMAIN + leftPhoto.getLitpic(), R.drawable.item_default,
                    null);

            UrlImageViewHelper.setUrlDrawable(holder.imgRight, WH_DMHttpApiV1.URL_DOMAIN
                    + rightPhot.getLitpic(), R.drawable.item_default, null);
        }

        return convertView;
    }

    public static class ViewHolder {
        ImageView imgLeft;
        TextView txtLeftTitle;
        TextView txtLeftReview;
        TextView txtLeftNum;

        ImageView imgRight;
        TextView txtRightTitle;
        TextView txtRightReview;
        TextView txtRightNum;

    };

    public class PhotoMsg {
        int aid;
        String title;
        String description;

        public void setAid(int _aid) {

            aid = new Integer(_aid);
        }

        public void setTitle(String _title) {

            title = new String(_title);
        }

        public void setDescription(String _description) {

            description = new String(_description);
        }

        public int getAid() {

            return aid;
        }

        public String getTitle() {

            return title;
        }

        public String getDescription() {

            return description;
        }
    }

    private void startActivity(PhotoMsg msg) {

        Intent intent = new Intent(context, PhotosDetailsActivity.class);
        intent.putExtra("aid", msg.getAid());
        intent.putExtra("title", msg.getTitle());
        intent.putExtra("description", msg.getDescription());
        context.startActivity(intent);
    }

}
