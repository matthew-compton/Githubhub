package com.bignerdranch.android.githubhub.utils;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class DialogUtils {

    private static final String TAG_PROGRESS_DIALOG = "DialogUtils.progressDialog";

    public static void showLoadingDialog(FragmentManager fragmentManager, boolean cancelable) {
        if (fragmentManager == null) {
            return;
        }

        Fragment existingDialogFragment = fragmentManager.findFragmentByTag(TAG_PROGRESS_DIALOG);
        boolean dialogExists = existingDialogFragment != null;

        DialogFragment updatedDialogFragment = ProgressDialogFragment.newInstance();
        updatedDialogFragment.setCancelable(cancelable);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(updatedDialogFragment, TAG_PROGRESS_DIALOG);
        if (dialogExists) {
            transaction.remove(existingDialogFragment);
        }
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public static void hideLoadingDialog(FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            return;
        }

        Fragment fragment = fragmentManager.findFragmentByTag(TAG_PROGRESS_DIALOG);
        if (fragment instanceof DialogFragment) {
            ((DialogFragment) fragment).dismissAllowingStateLoss();
        }
    }

}
