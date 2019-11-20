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
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.appmudanzas.R;
import com.example.appmudanzas.mCloud.MyConfiguration;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class Registro_Ine_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ProgressDialog progreso;
    private OnFragmentInteractionListener mListener;
    private View vista;
    private Button btn_registrar_Ine,btnFoto;
    private ImageView imageIne;
    private static final String CARPETA_PRINCIPAL="misImagenesApp/";
    private static final String CARPETA_IMAGEN="imagenes";
    private String nombreImagen,nombreImagenAux;
    private static final String DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
    private String path;
    private File fileImagen;
    private Bitmap bitmap;
    private static final int COD_SELECCIONA =10 ;
    private static final int COD_FOTO=20;
    public Registro_Ine_Fragment() {

    }

    public static Registro_Ine_Fragment newInstance(String param1, String param2) {
        Registro_Ine_Fragment fragment = new Registro_Ine_Fragment();
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
        final Registro_Licencia_Conducir_Fragment registro_licencia_conducir_fragment= new Registro_Licencia_Conducir_Fragment();
        vista=inflater.inflate(R.layout.fragment_registro__ine_, container, false);
        crearComponentes();

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoOpciones();
            }
        });

        btn_registrar_Ine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fileImagen!=null){
                    if(Conexion_Internet.compruebaConexion(getContext())){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Cloudinary cloud= new Cloudinary(MyConfiguration.getMyConfigs());
                                try{
                                    cloud.uploader().upload(fileImagen.getAbsolutePath(),ObjectUtils.asMap("public_id","ine/"+nombreImagenAux));
                                    cloud.url().transformation(new Transformation().width(400).height(400).crop("scale")).format("jpg").type("fetch").generate(nombreImagenAux);

                                }catch (IOException e){
                                    System.out.println(e.getMessage());
                                }
                            }
                        }).start();

                        Bundle datos= new Bundle();
                        datos.putString("foto_ine",nombreImagen);
                        FragmentTransaction fr= getFragmentManager().beginTransaction();
                        registro_licencia_conducir_fragment.setArguments(datos);

                        fr.replace(R.id.contenedor,registro_licencia_conducir_fragment).addToBackStack(null);
                        fr.commit();
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
                    imageIne.setImageURI(miPath);
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
                    imageIne.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    public void crearComponentes(){
        btn_registrar_Ine=vista.findViewById(R.id.btn_registrar_ine);
        imageIne=vista.findViewById(R.id.imageIne);
        btnFoto=vista.findViewById(R.id.btnFoto);
    }
}
