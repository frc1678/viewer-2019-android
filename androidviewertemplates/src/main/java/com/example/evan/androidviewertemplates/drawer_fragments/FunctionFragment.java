package com.example.evan.androidviewertemplates.drawer_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evan.androidviewertemplates.MainActivity;
import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertools.ViewerActivity;
import com.example.evan.androidviewertools.services.StarManager;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Teo on 3/23/18.
 */

public class FunctionFragment extends Fragment {
    Context context;
	LayoutInflater inflater;
	//booleans for state of buttons
	public Boolean isTeamsOfPicklist = false;
	public Boolean isClearedStarredTeams = false;
	public Boolean isClearHighlightedTeams = false;
	public Boolean isClearHighlightedMatches = false;
	public Boolean isClearStarredMatches = false;
	public Boolean isClearSelectedTeamsFromPicklist = false;

    //the six buttons
    public Button teamsOfPicklist;
	public Button clearedStarredTeams;
	public Button clearHighlightedTeams;
	public Button clearHighlightedMatches;
	public Button clearStarredMatches;
	public Button clearSelectedTeamsFromPicklist;

	//separators
	public View topSeparator;
	public View bottomSeparator;

	//square sections
	public LinearLayout topSquaresDescriptionBox;
	public LinearLayout midSquaresDescriptionBox;
	public LinearLayout bottomSquaresDescriptionBox;

	//square layouts
	public FrameLayout topSquaresFL;
	public FrameLayout midSquaresFL;
	public FrameLayout bottomSquaresFL;

	public Animation fadeIn;
	public Animation fadeOut;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myLayout = inflater.inflate(R.layout.functionfragment, null);

        //calls method that sets each xml variable with their associating xml element
        initXml(myLayout);
        //creates listener for onClick
        initShortClicks();
        //creates listener for onLongClick
		initLongClicks();

        return myLayout;
    }

    //saves value of the teamsFromPicklist in the internal storage
    public void saveToSharedTeamsFromPicklist() {
        ViewerActivity.myEditor.putInt("teamsFromPicklist", Constants.teamsFromPicklist);
        ViewerActivity.myEditor.apply();
    }

    //retrieves the teamsFromPicklist from the internal storage
    public static Integer getFromSharedTeamsFromPicklist() {
        Constants.teamsFromPicklist = ViewerActivity.myPref.getInt("teamsFromPicklist", 0);
        return Constants.teamsFromPicklist;
    }

    //method to make a toast with the given string message
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_SHORT).show();
        //toast will always be short to prevent toast spams stacking up
    }

	//method that sets each xml variable with their associating xml element
    public void initXml(View myLayout) {

		//creates inflater for future use
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		//creates the fade animations
	    fadeIn  = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
		fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

	    teamsOfPicklist = (Button) myLayout.findViewById(R.id.teamsOfPicklist);
	    clearedStarredTeams = (Button) myLayout.findViewById(R.id.clearStarredTeams);
	    clearHighlightedTeams = (Button) myLayout.findViewById(R.id.clearHighlightedTeams);
	    clearHighlightedMatches = (Button) myLayout.findViewById(R.id.clearHighlightedMatches);
	    clearStarredMatches = (Button) myLayout.findViewById(R.id.clearStarredMatches);
	    clearSelectedTeamsFromPicklist = (Button) myLayout.findViewById(R.id.clearSelectedFromPicklist);

	    //the two views that separate at the thirds marks
	    topSeparator = (View) myLayout.findViewById(R.id.topSeparator);
	    bottomSeparator = (View) myLayout.findViewById(R.id.bottomSeparator);

	    //the three sections (1/3,2/3,3/3) . vertical
	    topSquaresDescriptionBox = (LinearLayout) myLayout.findViewById(R.id.topSquares);
	    midSquaresDescriptionBox = (LinearLayout) myLayout.findViewById(R.id.midSquares);
	    bottomSquaresDescriptionBox = (LinearLayout) myLayout.findViewById(R.id.bottomSquares);

	    topSquaresFL = (FrameLayout) myLayout.findViewById(R.id.topSquaresFL);
	    midSquaresFL = (FrameLayout) myLayout.findViewById(R.id.midSquaresFL);
	    bottomSquaresFL = (FrameLayout) myLayout.findViewById(R.id.bottomSquaresFL);

    }

    //creates dialog on the long click of the teamsOfPicklist button
	//saves the amount of teams of picklist to be highlighted on all schedule views
    public void teamsOfPicklistLongClick() {
	    final Dialog teamsOfPicklistDialog = new Dialog(getContext());
	    teamsOfPicklistDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    teamsOfPicklistDialog.setContentView(R.layout.teamsofpicklist);
	    final EditText teamsOfPicklistEditText = (EditText) teamsOfPicklistDialog.findViewById(R.id.teamsOfPicklistEditText);

	    Constants.teamsFromPicklist = getFromSharedTeamsFromPicklist();

	    teamsOfPicklistEditText.setText(String.valueOf(Constants.teamsFromPicklist));
	    Button confirmButton = (Button) teamsOfPicklistDialog.findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    if (teamsOfPicklistEditText.getText().toString().equals("")) {
				    teamsOfPicklistEditText.setText(0);
			    }
			    if (Integer.parseInt(teamsOfPicklistEditText.getText().toString()) < FirebaseLists.teamsList.getKeys().size() && teamsOfPicklistEditText.getText() != null) {
				    Constants.teamsFromPicklist = Integer.parseInt(teamsOfPicklistEditText.getText().toString());
				    saveToSharedTeamsFromPicklist();
				    teamsOfPicklistDialog.dismiss();
			    } else {
				    Toast.makeText(getActivity(), "Please make sure the team you've inputed is lower than the amount of teams at the event!",
						    Toast.LENGTH_LONG).show();
			    }
		    }
	    });
	    teamsOfPicklistDialog.show();
    }

    //method that listens for the long clicks on the buttons

	//if a button is long clicked, the action that the button is supposed
	//to perform happens.

	//previously click, but people assumed the UI was a listview and tried scrolling, which
	//activated the button that they tried scrolling

    public void initLongClicks() {
		//if long click on teamsOfPicklist button, clear the teams of picklist list
	    teamsOfPicklist.setOnLongClickListener(new View.OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View view) {
		    	//makes sure that the click isnt happening while another button is being focused (description reading)
		    	if (!isClearedStarredTeams && !isClearSelectedTeamsFromPicklist && !isClearHighlightedTeams &&
					    !isClearHighlightedMatches && !isClearStarredMatches) {
				    teamsOfPicklistLongClick();
			    }
			    return true;
		    }
	    });
	    //if long click on the clearedStarredTeams button, clear the starred teams list
	    clearedStarredTeams.setOnLongClickListener(new View.OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View view) {
			    //makes sure that the click isnt happening while another button is being focused (description reading)
			    if (!isTeamsOfPicklist && !isClearSelectedTeamsFromPicklist && !isClearHighlightedTeams &&
					    !isClearHighlightedMatches && !isClearStarredMatches) {
				    StarManager.starredTeams.clear();
				    makeToast("Starred Teams have been cleared.");
			    }
			 return true;
		    }
	    });
	    //if long click on clearHighlightedTeams button, clear the highlighted teams list
	    clearHighlightedTeams.setOnLongClickListener(new View.OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View view) {
			    //makes sure that the click isnt happening while another button is being focused (description reading)
			    if (!isTeamsOfPicklist && !isClearedStarredTeams &&
					    !isClearHighlightedMatches && !isClearStarredMatches && !isClearSelectedTeamsFromPicklist) {
				    Constants.highlightedTeams.clear();
				    makeToast("Highlighted Teams have been cleared.");
			    }
			    return true;
		    }
	    });
	    //if long click on the clearHighlightedMatches button, clear the highlighted matches list
	    clearHighlightedMatches.setOnLongClickListener(new View.OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View view) {
			    //makes sure that the click isnt happening while another button is being focused (description reading)
			    if (!isTeamsOfPicklist && !isClearSelectedTeamsFromPicklist && !isClearHighlightedTeams &&
					    !isClearedStarredTeams && !isClearStarredMatches) {
				    Constants.highlightedMatches.clear();
				    makeToast("Highlighted Matches have been cleared.");
			    }
			    return true;
		    }
	    });
	    //if long click on the clearStarredMatches button, clear the starred matches list
	    clearStarredMatches.setOnLongClickListener(new View.OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View view) {
			    if (!isTeamsOfPicklist && !isClearSelectedTeamsFromPicklist && !isClearHighlightedTeams &&
					    !isClearedStarredTeams && !isClearHighlightedMatches) {
				    StarManager.importantMatches.clear();
				    makeToast("Starred Matches have been cleared.");
			    }
			    return true;
		    }
	    });
	    //if long click on the selectedteamsfrompicklist button, claer the selected teams of picklist list
	    clearSelectedTeamsFromPicklist.setOnLongClickListener(new View.OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View view) {
			    //makes sure that the click isnt happening while another button is being focused (description reading)
			    if (!isTeamsOfPicklist && !isClearStarredMatches && !isClearHighlightedTeams &&
					    !isClearedStarredTeams && !isClearHighlightedMatches) {
				    Constants.alreadySelectedOnPicklist.clear();
				    makeToast("Highlighted Picklist Teams have been cleared.");
			    }
			    return true;
		    }
	    });
    }

    //method for the listener for onClicks

	//if a button is clicked, the button will become focused and have a description box for the button appear
	//this is click because the long click is saved for the executing action part because people would accidentally click
	//a button and accidentally perform the action.

	//for each of the six buttons, if it's clicked and no other button is currently being focused, the button will become focused and
	//the description box will pop up

	public void initShortClicks() {
		teamsOfPicklist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isTeamsOfPicklist && !isClearedStarredTeams && !isClearHighlightedTeams && !isClearHighlightedMatches && !isClearStarredMatches && !isClearSelectedTeamsFromPicklist) {
					enableView(topSquaresDescriptionBox, topSquaresFL, topSeparator, clearedStarredTeams,
							"Highlighted Picklist Teams",
							"description");
					isTeamsOfPicklist = true;
				} else if (trueBesidesBoolean(isTeamsOfPicklist))
				{} else {
					disableView(topSeparator, clearedStarredTeams);
					isTeamsOfPicklist = false;
				}

			}
		});
		clearedStarredTeams.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isTeamsOfPicklist && !isClearedStarredTeams && !isClearHighlightedTeams && !isClearHighlightedMatches && !isClearStarredMatches && !isClearSelectedTeamsFromPicklist) {
					enableView(topSquaresDescriptionBox, topSquaresFL, topSeparator, teamsOfPicklist,
							"Clear Starred Teams",
							"description");
					isClearedStarredTeams = true;
				} else if (trueBesidesBoolean(isClearedStarredTeams))
				{} else {
					disableView(topSeparator, teamsOfPicklist);
					isClearedStarredTeams = false;
				}

			}
		});
		clearHighlightedTeams.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isTeamsOfPicklist && !isClearedStarredTeams && !isClearHighlightedTeams && !isClearHighlightedMatches && !isClearStarredMatches && !isClearSelectedTeamsFromPicklist) {
					enableView(midSquaresDescriptionBox, midSquaresFL, bottomSeparator, clearHighlightedMatches,
							"Clear Highlighted Teams",
							"description");
					grayOutButton(clearedStarredTeams); grayOutButton(teamsOfPicklist);
					isClearHighlightedTeams = true;
				} else if (trueBesidesBoolean(isClearHighlightedTeams))
				{} else {
					disableView(bottomSeparator, clearHighlightedMatches);
					reverseGrayButton(clearedStarredTeams); reverseGrayButton(teamsOfPicklist);
					isClearHighlightedTeams = false;
				}

			}
		});
		clearHighlightedMatches.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isTeamsOfPicklist && !isClearedStarredTeams && !isClearHighlightedTeams && !isClearHighlightedMatches && !isClearStarredMatches && !isClearSelectedTeamsFromPicklist) {
					enableView(midSquaresDescriptionBox, midSquaresFL, bottomSeparator, clearHighlightedTeams,
							"Clear Highlighted Matches",
							"description");
					grayOutButton(clearedStarredTeams); grayOutButton(teamsOfPicklist);
					isClearHighlightedMatches = true;
				} else if (trueBesidesBoolean(isClearHighlightedMatches))
				{} else {
					disableView(bottomSeparator, clearHighlightedTeams);
					reverseGrayButton(clearedStarredTeams); reverseGrayButton(teamsOfPicklist);
					isClearHighlightedMatches = false;
				}

			}
		});
		clearStarredMatches.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isTeamsOfPicklist && !isClearedStarredTeams && !isClearHighlightedTeams && !isClearHighlightedMatches && !isClearStarredMatches && !isClearSelectedTeamsFromPicklist) {
					enableView(bottomSquaresDescriptionBox, bottomSquaresFL, bottomSeparator, clearSelectedTeamsFromPicklist,
							"Clear Starred Matches",
							"description");
					isClearStarredMatches = true;
				} else if (trueBesidesBoolean(isClearStarredMatches))
				{} else {
					disableView(bottomSeparator, clearSelectedTeamsFromPicklist);
					isClearStarredMatches = false;
				}

			}
		});
		clearSelectedTeamsFromPicklist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isTeamsOfPicklist && !isClearedStarredTeams && !isClearHighlightedTeams && !isClearHighlightedMatches && !isClearStarredMatches && !isClearSelectedTeamsFromPicklist) {
					enableView(bottomSquaresDescriptionBox, bottomSquaresFL, bottomSeparator, clearStarredMatches,
							"Clear Highlighted Picklist Teams",
							"description");
					isClearSelectedTeamsFromPicklist = true;
				} else if (trueBesidesBoolean(isClearSelectedTeamsFromPicklist))
				{} else {
					disableView(bottomSeparator, clearStarredMatches);
					isClearSelectedTeamsFromPicklist = false;
				}
			}
		});
	}

	//turns the layout that it's given and makes its visibility visible
    public void turnVisible(LinearLayout layout) {
	    if (layout.getVisibility() == View.GONE) {
		    layout.startAnimation(fadeIn);
		    layout.setVisibility(View.VISIBLE);
	    }
    }

	//turns the layout that it's given and makes its visibility invisible
	public void turnInvisible(LinearLayout layout) {
	    if (layout.getVisibility() == View.VISIBLE) {
		    layout.startAnimation(fadeOut);
		    layout.setVisibility(View.GONE);
	    }
    }

    //turns the view that it's given and makes it turn from the nonhidden color to the hidden color (ex light gray -> white)
	//takes 250 milliseconds to occur
    public void makeViewInvisible(View view) {
	    TransitionDrawable transition = (TransitionDrawable) view.getBackground();
	    transition.startTransition(250);

    //turns the view that it's given and makes it turn from the non hidden color to the hidden color (ex white -> light gray)
    //takes 250 milliseconds to occur
    }
    public void makeViewVisible(View view) {
	    TransitionDrawable transition = (TransitionDrawable) view.getBackground();
	    transition.reverseTransition(250);

    //turns the button it's given and makes it turn from the non hidden color to the hidden color
    //takes 250 milliseconds to occur
	}
    public void grayOutButton(Button button) {
	    TransitionDrawable transition = (TransitionDrawable) button.getBackground();
	    transition.startTransition(250);
    }

    //reverses the color change from the previous method
	//takes 250 milliseconds to occur
    public void reverseGrayButton(Button button) {
	    TransitionDrawable transition = (TransitionDrawable) button.getBackground();
	    transition.reverseTransition(250);
    }

    //inflates the layouts of the framelayouts to prevent creating 6 different layouts for each section rather than one
    public void inflateLayout(FrameLayout layout, int layout_id, String title, String description) {
	    inflater.inflate(layout_id, layout, false);
	    TextView titleTV = (TextView) layout.findViewById(R.id.title);
	    TextView descriptionTV = (TextView) layout.findViewById(R.id.description);
	    titleTV.setText(title);
	    descriptionTV.setText(description);
    }

    //makes the layout visible and everything that has to change color change color
    public void enableView(LinearLayout layout, FrameLayout fl, View view, Button oppositeButton, String title, String description) {
	    turnVisible(layout);
	    makeViewInvisible(view);
	    grayOutButton(oppositeButton);

	    //inflates the layout of framelayout with the description box
	    inflateLayout(fl, R.layout.function_fragment_description_box, title, description);
    }

    //makes everything disappear or turn back to the original color
    public void disableView(View view, Button oppositeButton) {

	    if (topSquaresDescriptionBox.getVisibility() != View.GONE) {
			turnInvisible(topSquaresDescriptionBox);
		}
	    if (midSquaresDescriptionBox.getVisibility() != View.GONE) {
		    turnInvisible(midSquaresDescriptionBox);
	    }
	    if (bottomSquaresDescriptionBox.getVisibility() != View.GONE) {
		    turnInvisible(bottomSquaresDescriptionBox);
	    }
	    makeViewVisible(view);
	    reverseGrayButton(oppositeButton);

	    //restarts activity to prevent inflater overlay
	    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
	    Fragment fragment = new FunctionFragment();
	    fragmentManager.beginTransaction()
			    .replace(R.id.container, fragment)
			    .commit();
    }

    //method to check if any other button is being focused
    public Boolean trueBesidesBoolean(Boolean bool) {
	    if (isTeamsOfPicklist) {
		    if (isTeamsOfPicklist.equals(bool)) {
			    return false;
		    } else {
			    return true;
		    }
	    } else if (isClearedStarredTeams) {
			if (isClearedStarredTeams.equals(bool)) {
				return false;
			} else {
				return true;
			}
		} else if (isClearHighlightedTeams) {
			if (isClearHighlightedTeams.equals(bool)) {
				return false;
			} else {
				return true;
			}
		} else if (isClearHighlightedMatches) {
			if (isClearHighlightedMatches.equals(bool)) {
				return false;
			} else {
				return true;
			}
		} else if (isClearStarredMatches) {
		    if (isClearStarredMatches.equals(bool)) {
			    return false;
		    } else {
			    return true;
		    }
	    } else if (isClearSelectedTeamsFromPicklist) {
	    	if (isClearSelectedTeamsFromPicklist.equals(bool)) {
			    return false;
		    } else {
	    		return true;
		    }
	    }
	    return false;
    }
    
}

