package mapprr.deepak.gitsearch.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import mapprr.deepak.gitsearch.R;

/**
 * Created by Sharmaji on 8/16/2018.
 */

public class GitFilterFragment extends DialogFragment implements View.OnClickListener {

    RadioGroup rgOrderBy;
    RadioGroup rgSortby;
    ImageButton imgBtnApply;
    ImageButton imgClose;

    int sortBy;
    int orderBy;
    Dialog dialog;
    public static final String SORT_BY="sortBy";
    public static final String ORDER_BY="orderBy";
    private OnButtonclickListener onButtonclickListener;

    public static GitFilterFragment newInstance(int sortBy, int orderBy) {
        GitFilterFragment filesFilterFragment = new GitFilterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SORT_BY, sortBy);
        bundle.putInt(ORDER_BY, orderBy);
        filesFilterFragment.setArguments(bundle);
        return filesFilterFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FilterDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filter_dialog, container, false);
        onButtonclickListener = (OnButtonclickListener) getActivity();
        rgOrderBy = (RadioGroup) rootView.findViewById(R.id.rgOrderBy);
        rgSortby = (RadioGroup) rootView.findViewById(R.id.rgSortby);
        imgBtnApply = (ImageButton) rootView.findViewById(R.id.imgbtn_apply);
        imgClose = (ImageButton) rootView.findViewById(R.id.btn_close);
        imgClose.setOnClickListener(this);
        imgBtnApply.setOnClickListener(this);
        Bundle bundle = this.getArguments();
        sortBy = bundle.getInt(SORT_BY, 0);
        orderBy = bundle.getInt(ORDER_BY, 0);
        RadioButton rb;
        for (int i = 0; i < this.rgSortby.getChildCount(); i++) {
            if(sortBy==i) {
                View radioButton = this.rgSortby.getChildAt(i);
                if (radioButton instanceof RadioButton) {
                    rb = (RadioButton) radioButton;
                    rb.setChecked(true);
                }
            }
        }
        for (int i = 0; i < this.rgOrderBy.getChildCount(); i++) {
            if(orderBy==i) {
                View radioButton = this.rgOrderBy.getChildAt(i);
                if (radioButton instanceof RadioButton) {
                    rb = (RadioButton) radioButton;
                    rb.setChecked(true);
                }
            }
        }
        rgSortby.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_stars:
                        sortBy = 0;
                        break;
                    case R.id.rb_forks:
                        sortBy = 1;
                        break;
                    case R.id.rb_watchers:
                        sortBy = 2;
                        break;
                }
            }
        });
        rgOrderBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_desc:
                        orderBy = 0;
                        break;
                    case R.id.rb_asc:
                        orderBy = 1;
                        break;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imgbtn_apply:
                onButtonclickListener.onApply(sortBy, orderBy);
                dismiss();
                break;
            case R.id.btn_close:
                dismiss();
                break;
        }
    }

    public interface OnButtonclickListener {
        void onApply(int sortBy, int orderBy);
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog !=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
