package com.lpii.evma.view;

 import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;
import com.lpii.evma.controller.BilletController;
import com.lpii.evma.controller.CommandeController;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.model.Event;
import com.lpii.evma.model.EventPack;
import com.lpii.evma.view.tools.Contents;
import com.lpii.evma.view.tools.QRCodeEncoder;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class OrderBilletOverview extends Activity {

	Button mbtnPrintTicket;
	Button mbtnDownloadTicket;
	String pack_id;
	ImageView BilletQrCode;
	Bitmap bitmap;
	Image imgQRCODEBilletCode = null;
	String root ;
	String fnames[];
	TextView tvUSername;
	
	int serverResponseCode = 0;
    ProgressDialog dialog = null;
    BilletController mBillerController;
    Event ev;
        
    String upLoadServerUri = null;
     
     final String uploadFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/Evmax/";
      

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdoneticket);
		mbtnPrintTicket = (Button) findViewById(R.id.btnPrintBillet);
		mbtnDownloadTicket = (Button) findViewById(R.id.btnDownloadBillet);
		tvUSername = (TextView) findViewById(R.id.tvUserFullname);
		//tvUSername.setText(EvmaApp.CurrentUsername);
		Intent orderDatasIntent = getIntent();
		mBillerController = new BilletController();
		if (orderDatasIntent != null) {
			pack_id = orderDatasIntent.getStringExtra("pack_id");
		}
		fnames = new String[99];
		generateBilletQRCode();

		mbtnDownloadTicket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
 
				createPDF();

			}
		});
		
		mbtnPrintTicket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
 
				EvmaApp.CurrentUsername = "adm1";
				EvmaApp.CurrentOrganizer = "";
				Intent intUser = new Intent(OrderBilletOverview.this,MainEv.class);
				startActivity(intUser);

			}
		});
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);

	}

	public void createPDF() {
		Document doc = new Document();
		EventsController mevenetcontroler = new EventsController();
		ev = mevenetcontroler.getEventById(Event_Description_fragment.EvID);

		try {
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Evmax";

			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();

			Log.d(" ", "PDF Path: " + path);

			File file = new File(dir, CommandeController.UnkcmdUUID + ".pdf");
			FileOutputStream fOut = new FileOutputStream(file);

			PdfWriter.getInstance(doc, fOut);

			// open the document
			doc.open();
 			for (int i = 0; i < Integer.valueOf(OrderOverview.pack_order_qty); i++) {
				doc.newPage();
				Paragraph p1 = new Paragraph(	"Hi!This is your ticket");
				Font paraFont = new Font(Font.COURIER);
				p1.setAlignment(Paragraph.ALIGN_CENTER);
				p1.setFont(paraFont);
				
				// add paragraph to document
				doc.add(p1);

				Paragraph p2 = new Paragraph(
						"have a nice day");
				Font paraFont2 = new Font(Font.COURIER, 14.0f, Color.GREEN);
				p2.setAlignment(Paragraph.ALIGN_CENTER);
				p2.setFont(paraFont2);

				doc.add(p2);
				String fname =   Environment.getExternalStorageDirectory().getPath()+"/saved_images" + "/"+fnames[i];

 				try {
					//imgQRCODEBilletCode = Image.getInstance(imageInByte);
					imgQRCODEBilletCode = Image.getInstance(fname);

				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
 				imgQRCODEBilletCode.scalePercent(50f, 50f);

				doc.add( imgQRCODEBilletCode ); 

 
				// set footer
				Phrase footerText = new Phrase("Copyleft AlouaneMed");
				HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
				doc.setFooter(pdfFooter);
 
				PdfPTable table = createTable1(ev,i);
				doc.add(table);
				
			    
			}
 			//Save to the server the pdf file ev
 			new SaveBilletPDF().execute();
 			

		} catch (DocumentException de) {
			Log.e("PDFCreator", "DocumentException:" + de);
		} catch (IOException e) {
			Log.e("PDFCreator", "ioException:" + e);
		} finally {
			doc.close();
		}

	}

	public PdfPTable createTable1(Event Ev,int i) throws DocumentException {

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(488 / 5.23f);
		table.setWidths(new int[] { 1, 2, 2, 1 });

		PdfPCell cell;
		table.getDefaultCell().setBorder(3);
		table.getDefaultCell().setBorderColor(harmony.java.awt.Color.gray);

		cell = new PdfPCell(new Phrase("tab"));
		cell.setColspan(4);
		cell.setBorderWidth(8f);
		cell.setBorderColor(harmony.java.awt.Color.gray);
		table.addCell(cell);
		if (BilletQrCode == null) {
			System.out.println("NULL");
		}
		String Imgpath = (String) BilletQrCode.getTag();
		String fname =   Environment.getExternalStorageDirectory().getPath()+"/saved_images" + "/"+fnames[i];

 		System.out.println("fname " + fname  );

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// Drawable d = new BitmapDrawable(getResources(),bitmap);
		//bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.avatar);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		System.out.println("Imgpath  " + Imgpath);
 		byte[] imageInByte = stream.toByteArray();
		try {
			//imgQRCODEBilletCode = Image.getInstance(imageInByte);
			imgQRCODEBilletCode = Image.getInstance(fname);

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		imgQRCODEBilletCode.scalePercent(15f, 15f);
		
		cell = new PdfPCell();
		cell.addElement(imgQRCODEBilletCode);
		cell.setBorderWidth(8f);
		cell.setBorderColor(harmony.java.awt.Color.gray);
		cell.setRowspan(8);
		table.addCell(cell);

		imgQRCODEBilletCode.setAlignment(Image.MIDDLE);

		Font paraFont2 = new Font(Font.COURIER, 20.0f, Color.GREEN);
		Font paraFont16 = new Font(Font.COURIER, 16.0f, Color.GREEN);

		// title
		Phrase p = new Phrase(Ev.getTitle(), paraFont2);
		cell.setBorderWidth(8f);
		cell.setBorderColor(harmony.java.awt.Color.gray);
		cell = new PdfPCell(p);
		cell.setColspan(2);
		cell.setPadding(10);
		table.addCell(cell);

		// picture
		ByteArrayOutputStream streamw = new ByteArrayOutputStream();
		Bitmap xbitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.ic_launcher);
		xbitmap.compress(Bitmap.CompressFormat.JPEG, 100, streamw);
		Image myImg = null;
		try {
			myImg = Image.getInstance(streamw.toByteArray());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		myImg.setAlignment(Image.MIDDLE);

		cell = new PdfPCell();
		cell.setPadding(10);
		cell.addElement(myImg);
		cell.setRowspan(8);
		cell.setBorderWidth(8f);
		cell.setBorderColor(harmony.java.awt.Color.gray);
		table.addCell(cell);

		// Date Time
		Date date = null;
		String oldstring = Ev.getDateTime();
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Formateddate = new SimpleDateFormat("dd MMMM yyyy à HH:mm:ss")
				.format(date);
		System.out.println(Formateddate + " |||"); // 2011-01-18

		p = new Phrase(Formateddate, paraFont16);
		cell = new PdfPCell(p);
		cell.setBorderWidth(8f);
		cell.setBorderColor(harmony.java.awt.Color.gray);
		cell.setPadding(10);
		table.addCell(cell);

		// Addresse
		p = new Phrase("Mountain View, CA ", paraFont16);
		cell = new PdfPCell(p);
		cell.setBorderWidth(8f);
		cell.setBorderColor(harmony.java.awt.Color.gray);
		cell.setPadding(10);
		table.addCell(cell);

		// Order Info
		Date today = new Date();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM yyyy");
		String Actualdate = DATE_FORMAT.format(today);
		System.out.println("Today in dd-MM-yyyy format : " + Actualdate);

		cell = new PdfPCell(new Phrase("Commande Numero #"
				+ CommandeController.UnkcmdUUID + " par "
				+ EvmaApp.CurrentUsername + " le " + Actualdate));
		cell.setColspan(2);
		cell.setBorderWidth(8f);
		cell.setBorderColor(harmony.java.awt.Color.gray);
		cell.setPadding(10);

		table.addCell(cell);
		
		//forfait info
		EventPack evPack = Event_Description_fragment.evpk;
		System.out.println("evpack" + evPack.toString());

		cell = new PdfPCell(new Phrase(evPack.getTAG_pack_Title()));
		cell.setColspan(2);
		cell.setBorderWidth(8f);
		cell.setBorderColor(harmony.java.awt.Color.gray);
		cell.setPadding(10);

		table.addCell(cell);

		return table;
	}

	public void generateBilletQRCode() {
		// String qrInputText = qrInput.getText().toString();
		System.out.println(CommandeController.UnkUUID);
		// Find screen size
		WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		Point point = new Point(); 

		display.getSize(point);
		int width = point.x;
		int height = point.y;
		int smallerDimension = width < height ? width : height;
		smallerDimension = smallerDimension * 3 / 4;

		// Encode with a QR Code image
		 
		try {
 
			
			
			root =  Environment.getExternalStorageDirectory().getAbsolutePath();
			File myDir = new File(root + "/saved_images");    
			myDir.mkdirs();
			String[] billetuuids = CommandeController.UnkUUID.split("MAROC");
			System.out.println(billetuuids);
			for (int i = 0; i < billetuuids.length; i++) {
				System.out.println(billetuuids[i]);
			}
			
			for (int i = 0; i < billetuuids.length; i++) {
				System.out.println("X : " + billetuuids[i]);
				System.out.println(fnames[i] +"  ---");
				fnames[i] =
						"Image-"+ billetuuids[i]
								+".png";
				File file = new File (myDir, fnames[i]);
				if (file.exists ()) file.delete (); 
				try {
					QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(
							 billetuuids[i], null, Contents.Type.TEXT,
							BarcodeFormat.QR_CODE.toString(), smallerDimension);
					bitmap = qrCodeEncoder.encodeAsBitmap();
						FileOutputStream out = new FileOutputStream(file);
				       bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				       out.flush();
				       out.close();
				       
						 
						BilletQrCode = (ImageView) findViewById(R.id.imageView1x);
						//BilletQrCode.setImageBitmap(bitmap);

				} catch (Exception e) {
				       e.printStackTrace();
				}
			}
  			 
			 
 			BilletQrCode.setImageBitmap(bitmap);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//
	private class SaveBilletPDF extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
                mBillerController.uploadFile(uploadFilePath + CommandeController.UnkcmdUUID + ".pdf",CommandeController.UnkcmdUUID + ".pdf", CommandeController.CurrEvID);
                System.out.println("pdf...");
     			for (int i = 0; i < Integer.valueOf(OrderOverview.pack_order_qty); i++) {
     				String fname =   Environment.getExternalStorageDirectory().getPath()+"/saved_images" + "/"+fnames[i];
                    mBillerController.uploadFile(fname,fnames[i],CommandeController.CurrEvID);


				}
 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			}
 			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			 System.out.println("done billet");
			 Intent tomybillet = new Intent(OrderBilletOverview.this,MainEv.class);
			 tomybillet.putExtra("action", "showmemybillet");
			 Intent intent = new Intent(Intent.ACTION_SEND);
			 intent.setType("text/html");
			 intent.putExtra(Intent.EXTRA_EMAIL, EvmaApp.CurrentUserEmail);
			 intent.putExtra(Intent.EXTRA_SUBJECT, "Your Tickets" + ev.getTitle());
			 intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
			 intent.setType("text/html");
			 intent.putExtra(
					 Intent.EXTRA_TEXT,
					 Html.fromHtml(new StringBuilder()
					     .append("<p><b>Some Content</b></p>")
					     .append("<h3><p>Congrats !</p></small>")
					     .append("<small><p><a href= >Click here to download your ticket</a></p></small>")
					     .toString())
					 );

			 File file = new File(uploadFilePath, CommandeController.UnkcmdUUID + ".pdf");
			 Uri uri = Uri.fromFile(file);

			 intent.putExtra(Intent.EXTRA_STREAM, uri); 
			 startActivity(Intent.createChooser(intent, "Send Email"));
			 if (ClearUserSdcardFromBillets()) {
				System.out.println("Done Deleting ...");
			}
		}

	}
	
	public Boolean ClearUserSdcardFromBillets(){
		boolean deleted = false ;
		for (int i = 0; i < Integer.valueOf(OrderOverview.pack_order_qty); i++) {
			File file = new File(Environment.getExternalStorageDirectory().getPath()+"/saved_images" + "/"+fnames[i]);
			deleted = file.delete();
		}
		return deleted;

		 
	}
}
