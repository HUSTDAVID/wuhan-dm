
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

    /*
     * public void addItem(Bitmap leftBmp, String leftTitle, int leftReview, int
     * leftNum, Bitmap rightBmp, String rightTitle, int rightReview, int
     * rightNum) { HashMap<String, Object> map = new HashMap<String, Object>();
     * // add left data map.put("leftImage", leftBmp); map.put("leftTitle",
     * leftTitle); map.put("leftReview", leftReview); map.put("leftNum",
     * leftNum); // add rigth data map.put("rightImage", rightBmp);
     * map.put("rightTitle", rightTitle); map.put("rightReview", rightReview);
     * map.put("rightNum", rightNum); mData.add(map); notifyDataSetChanged(); }
     */

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
        final int pos = position;
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

            holder.imgLeft.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    int id = twoPhotos.get(pos).getLeftPhoto().getAid();
                    String title = twoPhotos.get(pos).getLeftPhoto().getAname();
                    String description = twoPhotos.get(pos).getLeftPhoto().getDescription();
                    startActivity(id, title, description);
                }
            });

            holder.imgRight.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    int id = twoPhotos.get(pos).getRightPhoto().getAid();
                    String title = twoPhotos.get(pos).getRightPhoto().getAname();
                    String description = twoPhotos.get(pos).getRightPhoto().getDescription();
                    startActivity(id, title, description);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Photo leftPhoto = (Photo) twoPhotos.get(position).getLeftPhoto();
        Photo rightPhot = (Photo) twoPhotos.get(position).getRightPhoto();

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

    static class ViewHolder {
        ImageView imgLeft;
        TextView txtLeftTitle;
        TextView txtLeftReview;
        TextView txtLeftNum;

        ImageView imgRight;
        TextView txtRightTitle;
        TextView txtRightReview;
        TextView txtRightNum;

    }

    private void startActivity(int aid, String title, String description) {

        Intent intent = new Intent(context, PhotosDetailsActivity.class);
        intent.putExtra("aid", aid);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        context.startActivity(intent);
    }

}
