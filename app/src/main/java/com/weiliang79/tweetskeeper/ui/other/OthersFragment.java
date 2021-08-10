package com.weiliang79.tweetskeeper.ui.other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.color.ColorViewModel;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmark;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmarkViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class OthersFragment extends Fragment {

    private Toolbar toolbar;

    private ColorViewModel colorViewModel;
    private OtherBookmarkViewModel otherBookmarkViewModel;

    private RecyclerView scrollView;
    private OtherBookmarkAdapter otherBookmarkAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_other, container, false);

        initViewModel();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        if(toolbar != null){
            toolbar.getMenu().clear();
        }

        scrollView = root.findViewById(R.id.bookmark_scrollView_other);
        otherBookmarkAdapter = new OtherBookmarkAdapter(getContext());
        scrollView.setAdapter(otherBookmarkAdapter);

        colorViewModel.getColorList();
        otherBookmarkViewModel.getBookmarkList();

        return root;
    }

    private void initViewModel(){

        //initialize color view model
        colorViewModel = new ViewModelProvider(getActivity()).get(ColorViewModel.class);

        colorViewModel.getColorList().observe(
                getViewLifecycleOwner(),
                new Observer<List<BookmarkColor>>() {
                    @Override
                    public void onChanged(List<BookmarkColor> bookmarkColors) {
                        otherBookmarkAdapter.setColorList(bookmarkColors);
                    }
                }
        );

        otherBookmarkViewModel = new ViewModelProvider(getActivity()).get(OtherBookmarkViewModel.class);

        otherBookmarkViewModel.getBookmarkList().observe(
                getViewLifecycleOwner(),
                new Observer<List<OtherBookmark>>() {
                    @Override
                    public void onChanged(List<OtherBookmark> otherBookmarks) {
                        otherBookmarkAdapter.setOtherBookmarkList(otherBookmarks);
                    }
                }
        );

    }
}
