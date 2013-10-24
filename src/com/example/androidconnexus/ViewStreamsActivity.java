package com.example.androidconnexus;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.androidquery.util.XmlDom;
import com.google.android.gms.internal.aq;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;


import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class ViewStreamsActivity extends ImageLoadingListActivity {
	//private SimpleAdapter adpt;

/*
	protected List<Stream> allStreams = new ArrayList<Stream>();
	protected Item[] gridArray = new Item[12];
	protected Bitmap image[] = new Bitmap[12];
*/
	GridView gridView;
	CustomGridViewAdapter customGridAdapter;
    List<Stream> allStreams = new ArrayList<Stream>();
    ArrayList<Item> gridArray = new ArrayList<Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_view_streams);
//		gridView = (GridView) findViewById(R.id.gridView1);
		async_inputstream();
		
		/*
        if (allStreams != null)
        {
      	 gridArray = new ArrayList<Item>();
  	     for (int i = 0; i < 12; i++)
  	     {			    		  
  	       custom_callback(allStreams.get(i));			    		
  	     }
  	     
  		//    image[i] = aq.getCachedImage(imageUrl);
		//        gridArray[i] = new Item(image[i],allStreams.get(i).name);
		        
		     customGridAdapter = new CustomGridViewAdapter(getApplicationContext(), R.layout.activity_view_streams, gridArray);
		     gridView.setAdapter(customGridAdapter);
        }
        */
		
/*
        AQuery aq = new AQuery(view);
		
		   GridView gridView;
		   ArrayList<Item> gridArray = new ArrayList<Item>();
		   CustomGridViewAdapter customGridAdapter;
		   //set grid view item
		   Bitmap image[] = new Bitmap[16];
		   for (int i = 0; i < 16; i++)
		   {
			   image[i] = getBitmapFromURL(allStreams.get(i).coverImageUrl);
		       gridArray.add(new Item(image[i],allStreams.get(i).name));
		   }
	    
      	   gridView = (GridView) findViewById(R.id.gridView1);
		   customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
		   gridView.setAdapter(customGridAdapter);
		}
		*/
		
		
		/*
		String imageUrl = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";
		aq.id(R.id.image1).image(imageUrl, true, true, 200, 0);
	    
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(ViewStreamsActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	    */
        
        
	}

	/*
	protected int getContainer(){
		return R.layout.image_grid_activity;
	}
	*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_streams, menu);
		return true;
	}
	
	protected int getContainer(){
		return R.layout.activity_view_streams;
//		return R.layout.image_grid_activity;
	}

	
	
	public static void makeHTTPPOSTRequest() {
           String apiUrl = "http://apt-connexus.appspot.com/AllStreamsServletAPI";
           StringBuilder builder = new StringBuilder();
           HttpClient client = new DefaultHttpClient();
           try {

           HttpGet httpGet = new HttpGet(apiUrl);
           HttpResponse response = client.execute(httpGet);
           StatusLine statusLine = response.getStatusLine();
           int statusCode = statusLine.getStatusCode();
           if (statusCode == 200) { // success!
              HttpEntity entity = response.getEntity();
           	  InputStream content = entity.getContent();
        	  BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        	  String line;
        	  while ((line = reader.readLine()) != null) {
        	     builder.append(line);
        	  }
        	  String tstJson = builder.toString();
        	  Gson gson = new Gson();
        	  Type t = new TypeToken<List<Stream>>(){}.getType();
        	  //allStreams = gson.fromJson(tstJson, t);
           } 
           else
           {
              Log.e("makeHTTPPOSTRequest:", "Failed to download file");
           }               
        } catch (IOException e) {
            Log.e("HTTP GET:", e.toString());                       
        }
	}	
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public void async_inputstream(){
		AQUtility.cleanCacheAsync(this, 0, 0);
		BitmapAjaxCallback.clearCache();
		
		String url = "http://apt-connexus.appspot.com/AllStreamsServletAPI";		
		
		aq.progress(R.id.progress).ajax(url, InputStream.class, new AjaxCallback<InputStream>(){
			
			public void callback(String url, InputStream is, AjaxStatus status) {
				
				if(is != null){
			       StringBuilder builder = new StringBuilder();
		           BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		           String line;
		           try {

				    	  
				      while ((line = reader.readLine()) != null) {
					     builder.append(line);
					  }
			          String tstJson = builder.toString();
			          Gson gson = new Gson();
			          Type t = new TypeToken<ArrayList<Stream>>(){}.getType();		          
			    	  allStreams = gson.fromJson(tstJson, t);
			  		
			  		  listAq = new AQuery(getApplicationContext());
			  		  List<Photo> entries = process_streams(allStreams);
			  		  
			  		  ArrayAdapter<Photo> aa = new ArrayAdapter<Photo>(getApplicationContext(), R.layout.photo_item, entries){
						
						public View getView(int position, View convertView, ViewGroup parent) {
							
							if(convertView == null){
								convertView = getLayoutInflater().inflate(R.layout.photo_item, parent, false);
							}
							
							Photo photo = getItem(position);
							
							AQuery aq = listAq.recycle(convertView);
							
							aq.id(R.id.name).text(photo.title);
							aq.id(R.id.meta).text(photo.author);
							
							String tbUrl = photo.tb;
							
							Bitmap placeholder = aq.getCachedImage(R.drawable.image_ph);
							
							if(aq.shouldDelay(position, convertView, parent, tbUrl)){
										
								aq.id(R.id.tb).image(placeholder);
							}else{
								
								aq.id(R.id.tb).image(tbUrl, true, true, 0, R.drawable.image_missing, placeholder, AQuery.FADE_IN_NETWORK, 0);
							}
							
							return convertView;
							
						}
						
						
					};
					
					aq.id(R.id.list).adapter(aa);

				   } catch (IOException e) {
				      // TODO Auto-generated catch block
				      e.printStackTrace();
				   }

				}else{
					showResult("Failed", status);
				}
			}
			
		});
	}
	
	public void custom_callback(final Stream s) {
	   aq.id(R.id.image0).image(s.coverImageUrl, true, true, 0, 0, new BitmapAjaxCallback(){
	      @Override
	      public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
	    	  if (bm != null) 
	          {
	             iv.setImageBitmap(bm);
	             gridArray.add(new Item(bm, s.name));
	          }	                
	      }
	   });
	}
	
	public List<Photo> process_streams(List<Stream> all) {
		List<Photo> result = new ArrayList<Photo>();
		
		for(Stream s: all){
			result.add(convert(s));
		}
		
		return result;
	}
	
	private Photo convert(Stream s){
		
		String url = s.coverImageUrl;
		String title = s.name;
		String author = "";		
		String tb = url;
		
		Photo photo = new Photo();
		photo.url = url;
		photo.tb = tb;
		photo.title = title;
		photo.author = author;
		
		return photo;
	}
	
	class Photo{
		
		String tb;
		String url;
		String title;
		String author;
	}

}
