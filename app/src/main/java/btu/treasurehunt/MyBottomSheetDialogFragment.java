package btu.treasurehunt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.Polygon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static btu.treasurehunt.R.drawable.unactivatedbutton;

/**
 * Created by MG on 17-07-2016.
 */
public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
    SessionManager session;
    String mString;
    String id="0";
    static Polygon mPolygon;
    Activity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity=getActivity();
    }
    static MyBottomSheetDialogFragment newInstance(Polygon polygon) {
        MyBottomSheetDialogFragment f = new MyBottomSheetDialogFragment();
        Bundle args = new Bundle();
        if(((Tag)polygon.getTag()).currentlayer==((Tag)polygon.getTag()).numlayers) {
            args.putString("string", "Cell completely digged!");
        }
        else {
            args.putString("string",(((Tag) polygon.getTag()).currentlayer <= 5 ? 5 : ((Tag) polygon.getTag()).currentlayer <= 10 ? 10 : ((Tag) polygon.getTag()).currentlayer <= 15 ? 15 : 20)+" coins");
        }
        mPolygon=polygon;
        f.setArguments(args);
        return f;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_modal, container, false);
        final BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        Button btn = (Button)v.findViewById(R.id.dig_btn);
        session = new SessionManager(this.getActivity().getApplicationContext());
        if(session.isLoggedIn())
        {
            id=session.getUserDetails().get("id");
        }
        long coins=((MainActivity)activity).getCoins();
        if(((Tag) mPolygon.getTag()).currentlayer != ((Tag) mPolygon.getTag()).numlayers && coins>=(((Tag) mPolygon.getTag()).currentlayer <= 5 ? 5 : ((Tag) mPolygon.getTag()).currentlayer <= 10 ? 10 : ((Tag) mPolygon.getTag()).currentlayer <= 15 ? 15 : 20))
        {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://databaserest.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UsermapCellsservice service = retrofit.create(UsermapCellsservice.class);
                final ProgressDialog progressDialog = new ProgressDialog(MyBottomSheetDialogFragment.this.getActivity(),
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Digging...");
                progressDialog.setIndeterminate(true);
                progressDialog.setCanceledOnTouchOutside(false);progressDialog.setCancelable(false);
                progressDialog.show();

                Call<UsermapCells> call = service.dig(Long.parseLong(id),((Tag)mPolygon.getTag()).id);
                call.enqueue(new Callback<UsermapCells>() {
                    @Override
                    public void onResponse(Call<UsermapCells> call, Response<UsermapCells> cells) {
                        ((MainActivity)activity).setCoins(cells.body().account.coins);
                        mPolygon.setTag(new Tag(cells.body().cells.numlayers,cells.body().cells.id,cells.body().currentlayer));
                        mPolygon.setFillColor(((Tag) mPolygon.getTag()).currentlayer == ((Tag) mPolygon.getTag()).numlayers ? 0x7fbbbbbb : getcolor(((Tag) mPolygon.getTag()).currentlayer));
                        SpecialObjectsservice Sservice = retrofit.create(SpecialObjectsservice.class);
                        Call<SpecialObjects> call1 = Sservice.check(Long.parseLong(id));
                        call1.enqueue(new Callback<SpecialObjects>() {
                            @Override
                            public void onResponse(Call<SpecialObjects> call, Response<SpecialObjects> treasure) {
                                if(treasure.body()!=null)
                                {

                                    dialog.dismiss();
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        progressDialog.show();}
                                                }, 10000);

                                }
                                else{
                                        dialog.dismiss();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<SpecialObjects> call, Throwable t) {}
                        });
                    }

                    @Override
                    public void onFailure(Call<UsermapCells> call, Throwable t) {}
                });
            }
        });}
        else
        {
            btn.setClickable(false);
            btn.setBackgroundResource(R.drawable.unactivatedbutton);
        }
        TextView diged = (TextView)v.findViewById(R.id.diged);
        diged.setText(mString);
        diged.setTextColor(ContextCompat.getColor(activity.getBaseContext(), R.color.colorPrimary));
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = (FrameLayout)
                        dialog.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight(0);
            }
        });
    }

    private int getcolor(int layer) {
        float[] hsv = new float[3];
        int color = 0xF4A460;
        Color.colorToHSV(color, hsv);
        for(int i=0;i<layer;i++) {
            hsv[2] *= 0.8; // value component
        }
        color = Color.HSVToColor(hsv);
        color+=0x7f000000;
        return color;
    }
}
