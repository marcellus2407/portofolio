package com.sewain;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemEditorAdapter  extends CursorAdapter {
    EditorActivity act;
    public ItemEditorAdapter(Context context, Cursor cursor, EditorActivity act) {
        super(context, cursor);
        this.act = act;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_item_editor, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nama = view.findViewById(R.id.namaRowEditor);
        ImageView img = view.findViewById(R.id.imgRowEditor);
        ImageView edit = view.findViewById(R.id.editRowEditor);
        ImageView delete = view.findViewById(R.id.deleteRowEditor);
        int id = cursor.getInt(1);
        String namadata = cursor.getString(2);
        byte[] imgdata = cursor.getBlob(3);
        nama.setText(namadata);
        img.setImageBitmap(BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length));
        view.setOnClickListener(view1 -> {
            context.startActivity(new Intent(context,DetailActivity.class).putExtra("id",id));
        });
        edit.setOnClickListener(view1 -> {
            act.updateItem(id);
        });
        delete.setOnClickListener(view1 -> {
            act.deleteItem(id);
        });
    }
}
