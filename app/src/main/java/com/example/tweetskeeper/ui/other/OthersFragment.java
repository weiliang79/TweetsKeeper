package com.example.tweetskeeper.ui.other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tweetskeeper.R;
import com.example.tweetskeeper.database.color.Color;
import com.example.tweetskeeper.database.color.ColorViewModel;
import com.example.tweetskeeper.database.other.bookmark.OtherBookmark;
import com.example.tweetskeeper.database.other.bookmark.OtherBookmarkViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class OthersFragment extends Fragment {

    private ColorViewModel colorViewModel;
    private OtherBookmarkViewModel otherBookmarkViewModel;

    private RecyclerView scrollView;
    private OtherBookmarkAdapter otherBookmarkAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_other, container, false);

        initViewModel();

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
                new Observer<List<Color>>() {
                    @Override
                    public void onChanged(List<Color> colors) {
                        otherBookmarkAdapter.setColorList(colors);
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
