package com.example.vanessa.badmintonapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageList extends AppCompatActivity {
    DatabaseHelper myDatabaseHelper;
    private ListView listView;
    private static final String TAG = "ImageList";
    //ArrayList<MyData> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        myDatabaseHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.myList);
        
        populateListView();

       /* listView = (ListView) findViewById(R.id.myList);
        myDatabaseHelper = new DatabaseHelper(this);
        populateView();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_activity, listData);
        listView.setAdapter(adapter);
    }

    private void populateView() {
        Cursor data = myDatabaseHelper.getData();
        while(data.moveToNext())
        {
            String name = data.getString(1);
            byte[] image = data.getBlob(2);
            listData.add(new MyData(name, image));
        }
    }

    public class CustomAdapter extends BaseAdapter
    {
        private Context context;
        private int layout;
        ArrayList<MyData> textList;

        public CustomAdapter(Context context, int layout, ArrayList<MyData> textList)
        {
            this.context = context;
            this.layout = layout;
            this.textList = textList;
        }

        @Override
        public int getCount()
        {
            return textList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return textList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        private class ViewHolder
        {
            ImageView imageView1;
            TextView textName;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            View row = view;
            ViewHolder holder;

            if(row == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new ViewHolder();
                holder.textName =(TextView) row.findViewById(R.id.tbCustom);
                holder.imageView1 = (ImageView) row.findViewById(R.id.imgView);
                row.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) row.getTag();
            }

            final MyData ranks = textList.get(position);
            holder.textName.setText(ranks.GetName());
            byte[] rankImage = ranks.GetImage();
            final Bitmap bitmap = BitmapFactory.decodeByteArray(rankImage, 0, rankImage.length);
            holder.imageView1.setImageBitmap(bitmap);
            row.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                 Cursor data = myDatabaseHelper.getItemID(ranks.GetName());
                    int itemID = -1;
                    String rankWeek = "";
                    byte[] rankImage = null;

                    while(data.moveToNext())
                    {
                     itemID = data.getInt(0);
                        rankWeek = data.getString(1);
                        rankImage = data.getBlob(2);
                        Intent editIntent= new Intent(ImageList.this, EditData.class);
                        editIntent.putExtra("ID", itemID);
                        editIntent.putExtra("name", ranks.GetName());
                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                        editIntent.putExtra("byteArray", bs.toByteArray());
                        startActivity(editIntent);
                    }
                    if (itemID > -1)
                    {
                        ToastMessage("On item click: the ID is " + itemID + " " + rankWeek + " " + rankImage);
                    }
                    else
                    {
                        ToastMessage("No Data");
                    }
                }
            });
            return row;
        }
    }
    private void ToastMessage(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
*/
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Display data in list view");
        //get data
        Cursor data = myDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        //create list adapter
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);

        //set onClickListener to the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + name);

                Cursor data = myDatabaseHelper.getItemID(name); //getting id associated with name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if (itemID > -1){
                    Log.d(TAG, "onItemClick: ID is " + itemID);
                    Intent editIntent = new Intent(ImageList.this, EditData.class);
                    editIntent.putExtra("id", itemID);
                    editIntent.putExtra("name", name);
                    startActivity(editIntent);
                }
                else{
                    toastMessage("No ID is associated with that name");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}