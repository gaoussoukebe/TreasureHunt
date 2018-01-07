package btu.treasurehunt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
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

/**
 * Created by MG on 17-07-2016.
 */
public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
    SessionManager session;
    String mString;
    String id="0";
    static Polygon mPolygon;
    static MyBottomSheetDialogFragment newInstance(Polygon polygon) {
        MyBottomSheetDialogFragment f = new MyBottomSheetDialogFragment();
        Bundle args = new Bundle();
        if(((Tag)polygon.getTag()).currentlayer==1) {
            args.putString("string", "You have never digged this cell");
        }
        if(((Tag)polygon.getTag()).currentlayer==((Tag)polygon.getTag()).numlayers) {
            args.putString("string", "You have already finished digging this cell.");
        }
        else {
            args.putString("string", "You have digged " + Integer.toString(((Tag) polygon.getTag()).currentlayer - 1) + " layers");
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
        if(session.isLoggedIn())
        {
            id=session.getUserDetails().get("id");
        }
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
                progressDialog.show();
                Call<UsermapCells> call = service.dig(Long.parseLong(id),((Tag)mPolygon.getTag()).id);
                call.enqueue(new Callback<UsermapCells>() {
                    @Override
                    public void onResponse(Call<UsermapCells> call, Response<UsermapCells> cells) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UsermapCells> call, Throwable t) {}
                });
                dialog.dismiss();
            }
        });
        TextView diged = (TextView)v.findViewById(R.id.diged);
        diged.setText(mString);
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
}
