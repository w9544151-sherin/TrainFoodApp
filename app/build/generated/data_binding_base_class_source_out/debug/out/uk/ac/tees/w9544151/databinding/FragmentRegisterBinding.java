// Generated by view binder compiler. Do not edit!
package uk.ac.tees.w9544151.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import uk.ac.tees.w9544151.R;

public final class FragmentRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatTextView btnAddUser;

  @NonNull
  public final EditText etEmail;

  @NonNull
  public final EditText etMobile;

  @NonNull
  public final EditText etName;

  @NonNull
  public final EditText etPassword;

  @NonNull
  public final ProgressBar progressbar;

  @NonNull
  public final ConstraintLayout scrollView;

  @NonNull
  public final TextView tvHeading;

  @NonNull
  public final EditText tvId;

  private FragmentRegisterBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatTextView btnAddUser, @NonNull EditText etEmail, @NonNull EditText etMobile,
      @NonNull EditText etName, @NonNull EditText etPassword, @NonNull ProgressBar progressbar,
      @NonNull ConstraintLayout scrollView, @NonNull TextView tvHeading, @NonNull EditText tvId) {
    this.rootView = rootView;
    this.btnAddUser = btnAddUser;
    this.etEmail = etEmail;
    this.etMobile = etMobile;
    this.etName = etName;
    this.etPassword = etPassword;
    this.progressbar = progressbar;
    this.scrollView = scrollView;
    this.tvHeading = tvHeading;
    this.tvId = tvId;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAddUser;
      AppCompatTextView btnAddUser = ViewBindings.findChildViewById(rootView, id);
      if (btnAddUser == null) {
        break missingId;
      }

      id = R.id.etEmail;
      EditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.etMobile;
      EditText etMobile = ViewBindings.findChildViewById(rootView, id);
      if (etMobile == null) {
        break missingId;
      }

      id = R.id.etName;
      EditText etName = ViewBindings.findChildViewById(rootView, id);
      if (etName == null) {
        break missingId;
      }

      id = R.id.etPassword;
      EditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.progressbar;
      ProgressBar progressbar = ViewBindings.findChildViewById(rootView, id);
      if (progressbar == null) {
        break missingId;
      }

      id = R.id.scrollView;
      ConstraintLayout scrollView = ViewBindings.findChildViewById(rootView, id);
      if (scrollView == null) {
        break missingId;
      }

      id = R.id.tvHeading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      id = R.id.tvId;
      EditText tvId = ViewBindings.findChildViewById(rootView, id);
      if (tvId == null) {
        break missingId;
      }

      return new FragmentRegisterBinding((ConstraintLayout) rootView, btnAddUser, etEmail, etMobile,
          etName, etPassword, progressbar, scrollView, tvHeading, tvId);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
