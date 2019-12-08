package com.example.appmudanzas.prestador_Servicio;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.appmudanzas.R;
import com.example.appmudanzas.mCloud.MyConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class Foto_Trasera_Vehiculo_Fragment extends Fragment {
    private String UPLOAD_URL="http://mudanzito.site/api/auth/vehiculo/insertar";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String modelo,placas,capacidad_carga,foto_frontal,foto_lateral;
    private View vista;
    private Button btn_registrar_foto_trasera,btnFoto;
    private static final String CARPETA_PRINCIPAL="misImagenesApp/";
    private static final String CARPETA_IMAGEN="imagenes";
    private String nombreImagen,nombreImagenAux;
    private static final String DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
    private String path;
    private File fileImagen;
    private Bitmap bitmap;
    private static final int COD_SELECCIONA =10 ;
    private static final int COD_FOTO=20;
    private ImageView imageFotoTrasera;
    private ProgressDialog progreso;
    private boolean enviado=false;
    private String id_prestador;
    private OnFragmentInteractionListener mListener;

    public Foto_Trasera_Vehiculo_Fragment() {

    }

    public static Foto_Trasera_Vehiculo_Fragment newInstance(String param1, String param2) {
        Foto_Trasera_Vehiculo_Fragment fragment = new Foto_Trasera_Vehiculo_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle datosRecuperados=getArguments();
        id_prestador=datosRecuperados.getString("id_prestador");
        modelo=datosRecuperados.getString("modelo");
        placas=datosRecuperados.getString("placas");
        capacidad_carga=datosRecuperados.getString("capacidad_carga");
        foto_frontal=datosRecuperados.getString("foto_frontal");
        foto_lateral=datosRecuperados.getString("foto_lateral");

        vista=inflater.inflate(R.layout.fragment_foto__trasera__vehiculo_, container, false);

        crearComponentes();

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoOpciones();
            }
        });

        btn_registrar_foto_trasera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fileImagen!=null){
                    if(Conexion_Internet.compruebaConexion(getContext())){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Cloudinary cloud= new Cloudinary(MyConfiguration.getMyConfigs());
                                try{
                                    cloud.uploader().upload(fileImagen.getAbsolutePath(), ObjectUtils.asMap("public_id","foto_trasera/"+nombreImagenAux));
                                    cloud.url().transformation(new Transformation().width(400).height(400).crop("scale")).format("jpg").type("fetch").generate(nombreImagenAux);

                                }catch (IOException e){
                                    System.out.println(e.getMessage());
                                }
                            }
                        }).start();
                        subirDatos();
                    }else{
                        Toast.makeText(getContext(),"Comprueba tu conexion a internet",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(),"Seleccione una imagen",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void crearComponentes(){
        btn_registrar_foto_trasera=vista.findViewById(R.id.btn_registrar_foto_trasera);
        btnFoto=vista.findViewById(R.id.btnFoto);
        imageFotoTrasera=vista.findViewById(R.id.imageFotoTrasera);
    }

    private void mostrarDialogoOpciones() {
        final CharSequence []opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opciones[i].equals("Tomar Foto")){
                    abrirCamara();
                }else {
                    if(opciones[i].equals("Elegir de Galeria")){
                        Intent intent= new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }else{
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada){
            Long consecutivo=System.currentTimeMillis()/1000;
            nombreImagen=consecutivo.toString()+".jpg";
            nombreImagenAux=consecutivo.toString();
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN+
                    File.separator+nombreImagen;
            fileImagen= new File(path);
            Intent intent=null;
            intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                String authorities=getContext().getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            }else{
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent,COD_FOTO);;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(miPath, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    Long consecutivo=System.currentTimeMillis()/1000;
                    nombreImagen=consecutivo.toString()+".jpg";
                    nombreImagenAux=consecutivo.toString();
                    fileImagen= new File(imgDecodableString);
                    cursor.close();
                    imageFotoTrasera.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Path",path);
                                }
                            });
                    bitmap= BitmapFactory.decodeFile(path);
                    imageFotoTrasera.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    private void subirDatos(){
        progreso= new ProgressDialog(getContext());
        progreso.setMessage("Enviando");
        progreso.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        enviado=true;
                        progreso.hide();
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                enviado=false;
                Toast.makeText(getContext(),"Error al enviar los datos",Toast.LENGTH_LONG).show();
                progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<>();
                params.put("id_prestador",id_prestador);
                params.put("modelo",modelo);
                params.put("placas",placas);
                params.put("capacidad_carga",capacidad_carga);
                params.put("foto_frontal",("https://res.cloudinary.com/ito/image/upload/foto_frontal/"+foto_frontal));
                params.put("foto_lateral",("https://res.cloudinary.com/ito/image/upload/foto_laterak/"+foto_lateral));
                params.put("foto_trasera",("https://res.cloudinary.com/ito/image/upload/foto_trasera/"+nombreImagen));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
}
