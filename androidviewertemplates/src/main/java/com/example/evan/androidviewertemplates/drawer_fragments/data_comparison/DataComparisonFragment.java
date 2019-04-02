package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

    Boolean onSelectMode = false;

    ArrayList<String> teamsList = new ArrayList<>();

    public static ArrayList<String> selectedTeamsList = new ArrayList<>();
    public static ArrayList<String> comparedAgainstTeamsList = new ArrayList<>();

    public static String selectedTeam;
    public static String typeComparison;

    //hardcoded colors of selected or not selected
    public static Integer neutralColor = -16777215;
    public static Integer selectedColor = -66921;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View localLayout = inflater.inflate(R.layout.data_comparison_team_select, null);
        initXML(localLayout);
        initTeamsList();
        initListView();
        restartComparisonSelection();
        initMain();

        return localLayout;
    }

    public void initXML(View myLayout) {
        //inits xml with according xml elements using myLayout (fragment)
        teamSelectListView = (ListView) myLayout.findViewById(R.id.teamListView);
        undoButton = (Button) myLayout.findViewById(R.id.undoButton);
        nextButton = (Button) myLayout.findViewById(R.id.nextButton);
        teamOneTextView = (TextView) myLayout.findViewById(R.id.teamOneTextView);
        teamTwoTextView = (TextView) myLayout.findViewById(R.id.teamTwoTextView);
        teamThreeTextView = (TextView) myLayout.findViewById(R.id.teamThreeTextView);
        teamFourTextView = (TextView) myLayout.findViewById(R.id.teamFourTextView);

        //updates button to default state
        updateButtonColor();
        //makes button onclick null
        disableButton();

    }

    public void initTeamsList() {
        //adds all the teams to teamsList
        for (String team : FirebaseLists.teamsList.getKeys()) {
            teamsList.add(team);
        }
    }

    public void initListView() {
        //saves state of scrolling
        Parcelable state = teamSelectListView.onSaveInstanceState();
        //adds adapter to list view
        DataComparisonTeamSelectAdapter dataComparisonAdapter = new DataComparisonTeamSelectAdapter(getContext(), teamsList);
        teamSelectListView.setAdapter(dataComparisonAdapter);
        dataComparisonAdapter.notifyDataSetChanged();
        //restores state of scrolling
        teamSelectListView.onRestoreInstanceState(state);
    }

    public void initMain() {
        //main purpose method
        //
        teamSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //if not on select mode
                if (!onSelectMode) {
                    onSelectMode = true;
                    //set selectedTeam to teamslist.get(position of click)
                    selectedTeam = teamsList.get(i);
                    //add the selected team to selectedTeamsList
                    selectedTeamsList.add(teamsList.get(i));
                    //updates button color to grey
                    updateButtonColor();
                    //re inits the list view to update the colors
                    initListView();
                    //sets the first text to the selected team
                    teamOneTextView.setText(selectedTeam);
                    //creates toast to notify user that the FIRST team was selected
                    SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_STANDARD)
                            .setText("Team Selected.")
                            .setDuration(Style.DURATION_VERY_SHORT)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN))
                            .setAnimations(Style.ANIMATIONS_FLY).show();
                    initComparisonSelect();
                } else {
                    restartComparisonSelection();
                }
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on undo button, if on select mode (>0 teams have been selected), restarts selection
                if (onSelectMode) {
                    restartComparisonSelection();
                }
            }
        });

    }

    private final SuperActivityToast.OnButtonClickListener undoButtonClickListener =
            //undo button in toast
            new SuperActivityToast.OnButtonClickListener() {

                @Override
                public void onClick(View view, Parcelable token) {
                    selectedTeamsList.remove(selectedTeam);
                    onSelectMode = false;
                    initListView();
                }
            };

    public void restartComparisonSelection() {
        //removes selected teams
        selectedTeamsList.remove(selectedTeam);
        //removes comparison teams
        comparedAgainstTeamsList.clear();
        //makes select mode be false
        onSelectMode = false;
        //updates button to grey
        updateButtonColor();
        //updates colors of cells
        initListView();
        //clears all the teams in the reminder bar
        clearTeamTextViewBar();
        //makes button onclick null
        disableButton();
        //restarts selection method
        initMain();
    }

    public void updateButtonColor() {
        //if selecting, make button green
        if (onSelectMode) {
            undoButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.LightGreen));
        }
        //else, make button light grey
        else if (!onSelectMode) {
            undoButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.LightGrey));
        }
    }

    public void updateTeamTextViewBar() {
        //sets the first teambar number to the selected team in bold
        teamOneTextView.setText(selectedTeam);
        Integer listSize = comparedAgainstTeamsList.size();
        //if one other team is selected, then set the second text view to the second team
        if (listSize.toString().equals("1")) {
            teamTwoTextView.setText(comparedAgainstTeamsList.get(0));
        }
        //if two others teams are selected, then set the third text view to the third team
        else if (listSize.toString().equals("2")) {
            teamTwoTextView.setText(comparedAgainstTeamsList.get(0));
            teamThreeTextView.setText(comparedAgainstTeamsList.get(1));
        }
        //if three others teams are selected, then set the fourth text view to the fourth team
        else if (listSize.toString().equals("3")) {
            teamTwoTextView.setText(comparedAgainstTeamsList.get(0));
            teamThreeTextView.setText(comparedAgainstTeamsList.get(1));
            teamFourTextView.setText(comparedAgainstTeamsList.get(2));
        }
    }

    public void clearTeamTextViewBar() {
        //makes all the teambar values turn to "?", rather than the actual team number
        teamOneTextView.setText("?");
        teamTwoTextView.setText("?");
        teamThreeTextView.setText("?");
        teamFourTextView.setText("?");
    }

    public void initComparisonSelect() {
        //if the primary team was selected, this is what is then called
        teamSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String comparisonTeam = teamsList.get(i);
                //checks to see if the selected cell was already the cell of the primary team
                if (!comparisonTeam.equals(selectedTeam)) {
                    String localComparisonTeam = teamsList.get(i);
                    //then checks to see if the selected cell contains a value that was already selected in general
                    if (!comparedAgainstTeamsList.contains(comparisonTeam)) {
                        // if the compared size is 3, it won't work
                        if (comparedAgainstTeamsList.size() < 3) {
                            //adds the selected team to the comparison teams list
                            comparedAgainstTeamsList.add(localComparisonTeam);
                            //updates button color
                            updateButtonColor();
                            //updates cell colors
                            initListView();
                            //updates the team bar
                            updateTeamTextViewBar();
                        }
                        activateButton();
                    } else {
                        //if it's already on the list, remove on click
                        comparedAgainstTeamsList.remove(localComparisonTeam);
                        //update button color
                        updateButtonColor();
                        //update cell colors
                        initListView();
                        //update team bar
                        clearTeamTextViewBar();
                        updateTeamTextViewBar();

                    }
                } else {
                    restartComparisonSelection();
                }
            }
        });
    }

    public void disableButton() {
        //makes color light grey and makes the button on click null
        nextButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.LightGrey));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NULL
            }
        });
    }

    public void activateButton() {
        //makes the button green and the on click start a dialog
        nextButton.setBackgroundColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //init xml to according xml elements
                final View comparison_selection = LayoutInflater.from(getContext()).inflate(R.layout.type_comparison_selection, null);
                final Button TIMDComparison = (Button) comparison_selection.findViewById(R.id.TIMDComparison);
                final Button TEAMSComparison = (Button) comparison_selection.findViewById(R.id.TEAMSComparison);

                //sets colors to the different buttons
                TIMDComparison.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JustinYellow));
                TEAMSComparison.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.White));

                //sets the colors to the different buttons
                TEAMSComparison.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JustinYellow));
                TIMDComparison.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.White));

                //adds the color concept (if red, turn blue, vice versa)
                initializeColorConcept(TEAMSComparison, TIMDComparison);

                builder.setView(comparison_selection);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //on OK clicked, starts intent
                        //checks to see whether the left button is yellow or white.
                        //if yellow (selected), begins TIMDComparison
                        //if white (not selected), begins TEAMSComparison
                        updateTypeComparison(TIMDComparison);
                        updateIntent();
                    }
                }).show();

            }
        });
    }

    public void initiateTIMDIntent() {
        //IF TIMD WAS SELECTED
        //creates intent to activity DataComparisonDatapointSelectActivityTIMD
        Intent GraphingActivity = new Intent(getActivity(), DataComparisonDatapointSelectActivityTIMD.class);
        GraphingActivity.putExtra("teamOne", teamOneTextView.getText().toString());
        GraphingActivity.putExtra("teamTwo", teamTwoTextView.getText().toString());
        GraphingActivity.putExtra("teamThree", teamThreeTextView.getText().toString());
        GraphingActivity.putExtra("teamFour", teamFourTextView.getText().toString());
        //adds slick animation
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(GraphingActivity, options.toBundle());

    }

    //IF TEAMS WAS SELECTED
    //creates intent to activity DataComparisonDatapointSelectActivityTEAMS
    public void initiateTEAMSIntent() {
        Intent GraphingActivity = new Intent(getActivity(), DataComparisonDatapointSelectActivityTEAMS.class);
        GraphingActivity.putExtra("teamOne", teamOneTextView.getText().toString());
        GraphingActivity.putExtra("teamTwo", teamTwoTextView.getText().toString());
        GraphingActivity.putExtra("teamThree", teamThreeTextView.getText().toString());
        GraphingActivity.putExtra("teamFour", teamFourTextView.getText().toString());
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(GraphingActivity, options.toBundle());

    }

    public void initializeColorConcept(final Button TEAMS, final Button TIMD) {
        TEAMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gets color of the right side
                //both sides work (you can click right side or left side and colorconcept will apply
                final Integer rightSideColor = ((ColorDrawable) TEAMS.getBackground()).getColor();
                if (String.valueOf(rightSideColor).equals(String.valueOf(selectedColor))) {
                    //if right color is the color of selected, switch colors
                    TEAMS.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.White));
                    TIMD.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JustinYellow));
                } else if (String.valueOf(rightSideColor).equals(String.valueOf(ContextCompat.getColor(getContext(), R.color.White)))) {
                    //if right color isn't the color of selected, still switch
                    TIMD.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.White));
                    TEAMS.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JustinYellow));
                }
            }
        });
        TIMD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gets color of the left side
                //both sides work (you can click right side or left side and colorconcept will apply
                final Integer leftSideColor = ((ColorDrawable) TIMD.getBackground()).getColor();
                if (String.valueOf(leftSideColor).equals(String.valueOf(selectedColor))) {
                    //if right color is the color of selected, switch colors
                    TIMD.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.White));
                    TEAMS.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JustinYellow));
                } else if (String.valueOf(leftSideColor).equals(String.valueOf(ContextCompat.getColor(getContext(), R.color.White)))) {
                    //if right color isn't the color of selected, still switch
                    TEAMS.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.White));
                    TIMD.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JustinYellow));
                }
            }
        });
    }

    public void updateTypeComparison(Button TIMDComparison) {
        Integer leftSideColor = ((ColorDrawable) TIMDComparison.getBackground()).getColor();
        if (String.valueOf(leftSideColor).equals(String.valueOf(selectedColor))) {
            //if left side color is SELECTEDCOLOR
            //set type to TIMD
            typeComparison = "TIMD";
        } else if (String.valueOf(leftSideColor).equals(String.valueOf(ContextCompat.getColor(getContext(), R.color.White)))) {
            //if left side color is not SELECTEDCOLOR
            //set type to TEAMS
            typeComparison = "TEAMS";
        }
    }

    public void updateIntent() {
        //if TIMD, starts TIMD intent
        if (typeComparison.equals("TIMD")) {
            initiateTIMDIntent();
        }
        //if TEAMS, starts TEAMS intent
        else if (typeComparison.equals("TEAMS")) {
            initiateTEAMSIntent();
        }

    }
}
