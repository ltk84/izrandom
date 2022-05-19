package uit.itszoo.izrandom.setting_module;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.jetbrains.annotations.NotNull;

import uit.itszoo.izrandom.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences prefs;

    AudioManager audioManager;
    Vibrator vibrator;

    SwitchMaterial soundSwitcher;
    SwitchMaterial vibrationSwitcher;
    SwitchMaterial darkModeSwitcher;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        prefs = getActivity().getSharedPreferences(getString(R.string.shared_preferences),Context.MODE_PRIVATE);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        initView(view);
        setListenerForView();
    }

    private void setListenerForView() {
        soundSwitcher.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
            } else {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            }
        });

        vibrationSwitcher.setOnCheckedChangeListener((compoundButton, b) -> {
            SharedPreferences.Editor editor = prefs.edit();
            if (b) {
                editor.putBoolean("vibrationOn", true);
            } else {
                editor.putBoolean("vibrationOn", false);
            }
            editor.apply();
            boolean defaultVibrationOn = getResources().getBoolean(R.bool.defaultVibrationOn);
            System.out.println(prefs.getBoolean("vibrationOn", defaultVibrationOn));
        });
    }

    private void initView(View view) {
        soundSwitcher = view.findViewById(R.id.sound_switcher);

        int audioVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (audioVolume == 0) {
            soundSwitcher.setChecked(false);
        } else {
            soundSwitcher.setChecked(true);
        }

        vibrationSwitcher = view.findViewById(R.id.vibration_switcher);
        boolean defaultVibrationOn = getResources().getBoolean(R.bool.defaultVibrationOn);
        boolean vibrationOn = prefs.getBoolean("vibrationOn", defaultVibrationOn);

        if (vibrationOn) {
            vibrationSwitcher.setChecked(true);
        } else {
            vibrationSwitcher.setChecked(false);
        }

        darkModeSwitcher = view.findViewById(R.id.dark_mode_switcher);
    }
}