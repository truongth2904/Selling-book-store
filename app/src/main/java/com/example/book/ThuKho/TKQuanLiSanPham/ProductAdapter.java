package com.example.book.ThuKho.TKQuanLiSanPham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<Product> data;

    public ProductAdapter(Context context, int resource, ArrayList<Product> data) {
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Định dạng số
        NumberFormat currentLocale = NumberFormat.getInstance();
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        //
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imgAnhSP = view.findViewById(R.id.TKTHImageitemViewSanPham);
            viewHolder.tvTenSP = view.findViewById(R.id.TKTHTextviewitemTenSanPham);
            viewHolder.tvGiaSP = view.findViewById(R.id.TKTHTextviewitemGiaSanPham);
            viewHolder.tvTacGia = view.findViewById(R.id.TKTHTextviewitemTacGia);
            viewHolder.tvTheLoai = view.findViewById(R.id.TKTHTextviewitemTheLoai);
            viewHolder.tvMoTa = view.findViewById(R.id.TKTHTextviewitemMoTa);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTenSP.setText("Tên Sản Phẩm: "+data.get(i).getTenSanPham());
        viewHolder.tvGiaSP.setText("Giá Sản Phẩm: "+en.format(data.get(i).getGiaTien()) + " VNĐ");
        viewHolder.tvTacGia.setText("Tác Giả: "+data.get(i).getAuthor());
        viewHolder.tvTheLoai.setText("Thể Loại:  "+data.get(i).getCategory());
        viewHolder.tvMoTa.setText("Mô Tả:   "+data.get(i).getDescription());
        Picasso.get().load(data.get(i).getHinhAnh()).into(viewHolder.imgAnhSP);
        return view;
    }

    private static class ViewHolder {
        ImageView imgAnhSP;
        TextView tvTenSP, tvGiaSP, tvTheLoai, tvTacGia, tvMoTa;
    }
}