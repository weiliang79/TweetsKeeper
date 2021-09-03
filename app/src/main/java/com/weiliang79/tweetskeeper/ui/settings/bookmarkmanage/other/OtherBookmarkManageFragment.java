package com.weiliang79.tweetskeeper.ui.settings.bookmarkmanage.other;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.color.ColorViewModel;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmark;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmarkViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class OtherBookmarkManageFragment extends Fragment implements OtherBookmarkManageAdapter.ButtonClickListener {

    private ColorViewModel colorViewModel;
    private OtherBookmarkViewModel otherBookmarkViewModel;

    private RecyclerView rvList;
    private OtherBookmarkManageAdapter adapter;
    private TextView tvListEmpty;

    public OtherBookmarkManageFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rv, container, false);

        initModel();

        rvList = root.findViewById(R.id.rvList);
        tvListEmpty = root.findViewById(R.id.tvListEmpty);
        adapter = new OtherBookmarkManageAdapter(getContext(), this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.divider_layer));
        rvList.addItemDecoration(dividerItemDecoration);
        rvList.setAdapter(adapter);

        colorViewModel.getColorList();
        otherBookmarkViewModel.getBookmarkList();

        return root;
    }

    private void initModel(){

        colorViewModel = new ViewModelProvider(getActivity()).get(ColorViewModel.class);

        colorViewModel.getColorList().observe(
                getViewLifecycleOwner(),
                new Observer<List<BookmarkColor>>() {
                    @Override
                    public void onChanged(List<BookmarkColor> bookmarkColors) {
                        adapter.setColorList(bookmarkColors);
                    }
                }
        );

        otherBookmarkViewModel = new ViewModelProvider(getActivity()).get(OtherBookmarkViewModel.class);

        otherBookmarkViewModel.getBookmarkList().observe(
                getViewLifecycleOwner(),
                new Observer<List<OtherBookmark>>() {
                    @Override
                    public void onChanged(List<OtherBookmark> otherBookmarks) {

                        if(otherBookmarks.isEmpty()){
                            tvListEmpty.setVisibility(View.VISIBLE);
                            tvListEmpty.setText("Empty");
                        } else {
                            tvListEmpty.setVisibility(View.GONE);
                        }

                        adapter.setBookmarkList(otherBookmarks);

                    }
                }
        );

    }

    @Override
    public void editButtonClicked(int id) {
        Log.e("Other Manage Bookmark", "Edit: " + id);
    }

    @Override
    public void deleteButtonClicked(int id) {
        Log.e("Other Manage Bookmark", "Delete: " + id);
    }
}
