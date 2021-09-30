package com.developer.vijay.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.developer.vijay.rxjava.databinding.ActivityMainBinding;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mydata";
    private final GitHubRepoAdapter repoAdapter = new GitHubRepoAdapter();
    private Subscription subscription;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.rvRepos.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRepos.setAdapter(repoAdapter);

        mBinding.btnSearch.setOnClickListener(v -> {
            final String username = mBinding.etUsername.getText().toString();
            if (!TextUtils.isEmpty(username)) {
                getStarredRepos(username);
                hideKeyboard(this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void getStarredRepos(String username) {
        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GitHubResponse>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override
                    public void onNext(List<GitHubResponse> gitHubResponses) {
                        Log.d(TAG, "In onNext()");
                        repoAdapter.setGitHubRepos(gitHubResponses);
                    }
                });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}