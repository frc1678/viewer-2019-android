package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import java.util.ArrayList;

public class DataComparisonFragment extends Fragment {

    ListView teamSelectListView;
    Button undoButton;
    Button nextButton;
    TextView teamOneTextView;
    TextView teamTwoTextView;
    TextView teamThreeTextView;
    TextView teamFourTextView;

    ArrayList<String> teamsList = new ArrayList<>();
    public static ArrayList<String> selectedTeamsList = new ArrayList<>();
    public static ArrayList<String> comparedAgainstTeamsList = new ArrayList<>();
    Boolean onSelectMode = false;
    public static String selectedTeam;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View localLayout = inflater.inflate(R.layout.data_comparison_team_select, null);
        initXML(localLayout); initTeamsList(); initListView(); initMain();

        return localLayout;
    }

    public void initXML(View myLayout) {
        teamSelectListView = (ListView) myLayout.findViewById(R.id.teamListView);
        undoButton = (Button) myLayout.findViewById(R.id.undoButton);
        nextButton = (Button) myLayout.findViewById(R.id.nextButton);
        teamOneTextView = (TextView) myLayout.findViewById(R.id.teamOneTextView);
        teamTwoTextView = (TextView) myLayout.findViewById(R.id.teamTwoTextView);
        teamThreeTextView = (TextView) myLayout.findViewById(R.id.teamThreeTextView);
        teamFourTextView = (TextView) myLayout.findViewById(R.id.teamFourTextView);

        updateButtonColor();

    }

    public void initTeamsList() {
        for (String team : FirebaseLists.teamsList.getKeys()) {
            teamsList.add(team);
        }
    }

    public void initListView() {
        Parcelable state = teamSelectListView.onSaveInstanceState();
        DataComparisonTeamSelectAdapter dataComparisonAdapter = new DataComparisonTeamSelectAdapter(getContext(), teamsList);
        teamSelectListView.setAdapter(dataComparisonAdapter);
        dataComparisonAdapter.notifyDataSetChanged();
        teamSelectListView.onRestoreInstanceState(state);
    }

    public void initMain() {
        teamSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!onSelectMode) {
                    Log.e("mode","SELECTING");
                    onSelectMode = true;
                    selectedTeam = teamsList.get(i);
                    selectedTeamsList.add(teamsList.get(i));
                    updateButtonColor();
                    initListView();
                    teamOneTextView.setText(selectedTeam);
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_STANDARD)
                                .setText("Team Selected.")
                                .setDuration(Style.DURATION_VERY_SHORT)
                                .setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN))
                                .setAnimations(Style.ANIMATIONS_FLY).show();

                } else {
                    initComparisonSelect();
                }
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectMode) {
                    restartComparisonSelection();
                }
            }
        });

    }

    private final SuperActivityToast.OnButtonClickListener undoButtonClickListener =
            new SuperActivityToast.OnButtonClickListener() {

                @Override
                public void onClick(View view, Parcelable token) {
                    selectedTeamsList.remove(selectedTeam);
                    onSelectMode = false;
                    initListView();
                }
            };

    public void restartComparisonSelection() {
        selectedTeamsList.remove(selectedTeam);
        comparedAgainstTeamsList.clear();
        onSelectMode = false;
        updateButtonColor();
        initListView();
        clearTeamTextViewBar();
        initMain();
    }

    public void updateButtonColor() {
        if (onSelectMode) {
            undoButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.LightGreen));
        } else if (!onSelectMode) {
            undoButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.LightGrey));
        }
    }

    public void updateTeamTextViewBar() {
        teamOneTextView.setText(selectedTeam);
        Integer listSize = comparedAgainstTeamsList.size();
        Log.e("listSize",listSize.toString());
        if (listSize.toString().equals("1")) {
            teamTwoTextView.setText(comparedAgainstTeamsList.get(0));
        }
        else if (listSize.toString().equals("2")) {
            teamThreeTextView.setText(comparedAgainstTeamsList.get(1));
        }
        else if (listSize.toString().equals("3")) {
            teamFourTextView.setText(comparedAgainstTeamsList.get(2));
        }
    }

    public void clearTeamTextViewBar() {
        teamOneTextView.setText("");
        teamTwoTextView.setText("");
        teamThreeTextView.setText("");
        teamFourTextView.setText("");
    }

    public void initComparisonSelect() {
        teamSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String comparisonTeam = teamsList.get(i);
                if (!comparisonTeam.equals(selectedTeam)) {
                    if (!comparedAgainstTeamsList.contains(comparisonTeam)) {
                        if (comparedAgainstTeamsList.size() < 3) {
                            String localComparisonTeam = teamsList.get(i);
                            comparedAgainstTeamsList.add(localComparisonTeam);
                            updateButtonColor();
                            initListView();
                            updateTeamTextViewBar();
                        } else {
                            //
                        }
                    }
                }
            }
        });
    }


}
