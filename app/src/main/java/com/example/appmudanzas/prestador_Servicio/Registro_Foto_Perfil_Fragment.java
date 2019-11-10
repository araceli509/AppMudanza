package com.example.appmudanzas.prestador_Servicio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.appmudanzas.R;
import com.example.appmudanzas.mCloud.MyConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registro_Foto_Perfil_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registro_Foto_Perfil_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro_Foto_Perfil_Fragment extends Fragment {
    private String UPLOAD_URL="http://192.168.1.73:80/api/auth/insertar";
    private String nombre,apellidos,correo,password,direccion,telefono;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String CARPETA_PRINCIPAL="misImagenesApp/";
    private static final String CARPETA_IMAGEN="imagenes";
    private String nombreImagen;
    private static final String DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
    private String path;
    private File fileImagen;
    private Bitmap bitmap;
    private static final int COD_SELECCIONA =10 ;
    private static final int COD_FOTO=20;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View vista;
    private Button btn_registrar_foto_perfil;
    private Button btnFoto;
    private ImageView imagePerfil;

    public Registro_Foto_Perfil_Fragment() {

    }

    public static Registro_Foto_Perfil_Fragment newInstance(String param1, String param2) {
        Registro_Foto_Perfil_Fragment fragment = new Registro_Foto_Perfil_Fragment();
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

        Bundle datosRecuperados = getArguments();
        nombre=datosRecuperados.getString("nombre");
        apellidos=datosRecuperados.getString("apellidos");
        direccion=datosRecuperados.getString("direccion");
        telefono=datosRecuperados.getString("telefono");
        correo=datosRecuperados.getString("correo");
        password=datosRecuperados.getString("password");
        vista=inflater.inflate(R.layout.fragment_registro__foto__perfil_, container, false);

        btnFoto=vista.findViewById(R.id.btnFoto);
        imagePerfil=vista.findViewById(R.id.imagePerfil);

        if(validaPermisos()){
            btnFoto.setEnabled(true);
        }else{
            btnFoto.setEnabled(false);
        }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });


        btn_registrar_foto_perfil=vista.findViewById(R.id.btn_registrar_foto_perfil);
        final Registro_Ine_Fragment registro_ine_fragment= new Registro_Ine_Fragment();

        btn_registrar_foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cloudinary cloud= new Cloudinary(MyConfiguration.getMyConfigs());
                        try {
                            cloud.uploader().upload(fileImagen.getAbsolutePath(),ObjectUtils.asMap("public_id","foto_perfil/"+nombreImagen));
                            cloud.url().generate(nombreImagen);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();

                subirDatos();

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.contenedor,registro_ine_fragment).addToBackStack(null);
                fr.commit();
            }
        });

        return vista;
    }

    private void subirDatos() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<>();
                params.put("nombre", nombre);
                params.put("apellidos",apellidos);
                params.put("direccion", direccion);
                params.put("telefono",telefono);
                params.put("correo",correo);
                params.put("password",password);
                params.put("foto_perfil",nombreImagen);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private boolean validaPermisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            return true;
        }
        if((getContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)
                &&(getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA))||(shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length==2&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                btnFoto.setEnabled(true);
            }
        }
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo= new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el funcionamiento de la app");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    private void mostrarDialogOpciones() {
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
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN+
                    File.separator+nombreImagen;
            fileImagen= new File(path);
            Intent intent=null;
            intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                String authorities=getContext().getPackageName()+".provider";
                Uri imageUri=FileProvider.getUriForFile(getContext(),authorities,fileImagen);
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
                    fileImagen= new File(imgDecodableString);
                    cursor.close();
                    imagePerfil.setImageURI(miPath);
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
                    imagePerfil.setImageBitmap(bitmap);
                    break;
            }
        }
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
}
