package com.developer.vijay.rxjava;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.vijay.rxjava.databinding.ItemGithubRepoBinding;

import java.util.ArrayList;
import java.util.List;

class GitHubRepoAdapter extends RecyclerView.Adapter<GitHubRepoAdapter.GitHubRepoViewHolder> {

    private final List<GitHubResponse> gitHubResponses = new ArrayList<>();

    @NonNull
    @Override
    public GitHubRepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GitHubRepoViewHolder(ItemGithubRepoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GitHubRepoViewHolder holder, int position) {
        GitHubResponse gitHubResponse = gitHubResponses.get(position);

        holder.mBinding.tvRepoName.setText(gitHubResponse.name);
        holder.mBinding.tvRepoDescription.setText(gitHubResponse.description);
        holder.mBinding.tvLanguage.setText("Language: " + gitHubResponse.language);
        holder.mBinding.tvStars.setText("Stars: " + gitHubResponse.stargazersCount);
    }

    @Override
    public int getItemCount() {
        return gitHubResponses.size();
    }

    public void setGitHubRepos(@Nullable List<GitHubResponse> repos) {
        if (repos == null) {
            return;
        }
        gitHubResponses.clear();
        gitHubResponses.addAll(repos);
        notifyDataSetChanged();
    }

    public static class GitHubRepoViewHolder extends RecyclerView.ViewHolder {

        public ItemGithubRepoBinding mBinding;

        public GitHubRepoViewHolder(@NonNull ItemGithubRepoBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
