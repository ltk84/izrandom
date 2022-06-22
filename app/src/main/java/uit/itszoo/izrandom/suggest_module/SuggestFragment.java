package uit.itszoo.izrandom.suggest_module;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kotlinx.coroutines.Delay;
import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.suggest_module.models.Suggestion;
import uit.itszoo.izrandom.suggest_module.source.SuggestionSource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuggestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int indexOfCollection = 0;
    private Spinner spinner;
    private ImageButton dropMenu;
    private AutoCompleteTextView autoCompleteTextView;
    private TextView title;
    private ImageView image;
    private TextView guide;
    private List<Suggestion> suggestions = new ArrayList<>();
    public SuggestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestFragment newInstance(String param1, String param2) {
        SuggestFragment fragment = new SuggestFragment();
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
        View layout =  inflater.inflate(R.layout.fragment_suggest, container, false);

        //binding and create data for spinner
        spinner = (Spinner)layout.findViewById(R.id.spinner);
        CustomSuggestionCollectionAdapter mCustomAdapter =
                new CustomSuggestionCollectionAdapter(container.getContext(), SuggestionSource.listCollection);
        spinner.setAdapter(mCustomAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        indexOfCollection = i;
                        suggestions.clear();
                        suggestions.addAll(SuggestionSource.listCollection.get(i).suggestions);
                        autoCompleteTextView.setText(SuggestionSource.listCollection.get(i).categories.get(0));
                        title.setText(SuggestionSource.listCollection.get(i).suggestions.get(0).title);
                        image.setImageResource(SuggestionSource.listCollection.get(i).suggestions.get(0).image);
                        createCategorySpinner();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                }
        );

        title = layout.findViewById(R.id.title);
        image = layout.findViewById(R.id.image);
        autoCompleteTextView = (AutoCompleteTextView) layout.findViewById(R.id.category_spinner);
        guide = layout.findViewById(R.id.guideline);

        dropMenu = layout.findViewById(R.id.dropdown_menu);
        dropMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinner.performClick();
                    }
                }
        );

        //set listener for layout
        layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Random random = new Random();
                        Thread timer = new Thread() {
                            public void run() {
                                try {
                                    sleep(100);
                                    for (int i = 0; i < 50; i++) {

                                        sleep(80);
                                        if(getActivity() != null)
                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run() {
                                                int result = random.nextInt(suggestions.size());
                                                image.setImageResource(suggestions.get(result).image);
                                                title.setText(suggestions.get(result).title);
                                            }
                                        });
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {
                                    System.out.println("finally");
                                }
                            }
                        };
                        timer.start();

                    }
                }
        );
        return layout;
    }
    void createCategorySpinner()
    {
        List<String> list = SuggestionSource.listCollection.get(indexOfCollection).categories;
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.item_drop_menu_category , list);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        autoCompleteTextView.getOnItemSelectedListener();
                    }
                }
        );
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(list.get(i).equals("Tất cả"))
                        {
                            suggestions.clear();
                            suggestions.addAll(SuggestionSource.listCollection.get(indexOfCollection).suggestions);
                        }
                        else{
                            suggestions.clear();
                            suggestions.addAll(SuggestionSource.listCollection.get(indexOfCollection).suggestions);
                            suggestions.removeIf(suggestion -> !suggestion.category.equals(list.get(i))
                            );
                        }
                        image.setImageResource(suggestions.get(0).image);
                        title.setText(suggestions.get(0).title);
                    }
                }
        );
    }

}