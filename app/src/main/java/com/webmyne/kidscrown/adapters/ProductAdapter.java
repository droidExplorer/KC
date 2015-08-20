package com.webmyne.kidscrown.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.webmyne.kidscrown.R;
import com.webmyne.kidscrown.helper.DatabaseHandler;

public class ProductAdapter extends SimpleCursorAdapter {

    private Context _ctx;
    private int colors[] = {R.color.quad_blue,R.color.quad_green,R.color.quad_orange,R.color.quad_violate};

    public ProductAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this._ctx = context;
    }

    class ViewHolder {
        TextView txtProductTitle;
        TextView txtDescription;
        ImageView imgProduct;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater layoutInflator1 = LayoutInflater.from(context);
        View rowView = layoutInflator1.inflate(R.layout.item_product, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.txtProductTitle = (TextView) rowView.findViewById(R.id.txtProductTitle);
        holder.txtDescription = (TextView)rowView.findViewById(R.id.txtProductDescription);
        holder.imgProduct = (ImageView)rowView.findViewById(R.id.imgProduct);

        int position = cursor.getPosition();
       // rowView.setBackgroundColor(colors[position]);

        displayValues(holder, cursor);
        rowView.setTag(holder);
        return rowView;
    }

    private void displayValues(ViewHolder holder, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String productID = cursor.getString(cursor.getColumnIndexOrThrow("product_id"));

        DatabaseHandler handler = new DatabaseHandler(_ctx);
        String path = handler.getImagePath(productID);
        handler.close();

        holder.txtProductTitle.setText(name);
        holder.txtDescription.setText(Html.fromHtml(description));
        if(path !=null && !path.isEmpty()){
            Glide.with(_ctx).load(path).into(holder.imgProduct);
        }

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ViewHolder holder = (ViewHolder) view.getTag();
        displayValues(holder, cursor);

    }
}