package com.callil.rotatingsentries.menu;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.callil.rotatingsentries.R;
import com.callil.rotatingsentries.options.Difficulty.DifficultyEnum;
import com.callil.rotatingsentries.util.PrefsUtil;
import com.callil.rotatingsentries.util.PrefsUtil.PreferencesConstants;


public class OptionsActivity extends AbstractActivity {
	
	/** TAG for the logs. */
	private static final String TAG = OptionsActivity.class.getSimpleName();
	
	private static boolean isScalingChanged = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setTagParent("Options");
        super.onCreate(savedInstanceState);
		
        setContentView(R.layout.activity_options);
        
        //ACTIVATE SOUND
        RadioGroup soundRadioGroup = (RadioGroup) findViewById(R.id.radioSound);
        boolean isCurrentSoundOn = PrefsUtil.getPrefs().getBoolean(PreferencesConstants.SOUND_ENABLED, true);
        setChecked(soundRadioGroup, isCurrentSoundOn? 0:1);
        soundRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	boolean isSoundOn = checkedId == R.id.radioSoundOn;
            	PrefsUtil.setPrefs(boolean.class, PreferencesConstants.SOUND_ENABLED, isSoundOn);
            }
        });
        
        //ACTIVATE PARTICLE
        RadioGroup particleRadioGroup = (RadioGroup) findViewById(R.id.radioParticle);
        boolean isCurrentParticleOn = PrefsUtil.getPrefs().getBoolean(PreferencesConstants.PARTICLE_ENABLED, true);
        setChecked(particleRadioGroup, isCurrentParticleOn? 0:1);
        particleRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	boolean isParticleOn = checkedId == R.id.radioParticleOn;
            	PrefsUtil.setPrefs(boolean.class, PreferencesConstants.PARTICLE_ENABLED, isParticleOn);
            }
        });
        
        //CHANGE DIFFICULTY
        RadioGroup difficultyRadioGroup = (RadioGroup) findViewById(R.id.radioDifficulty);
        int currentDifficulty = PrefsUtil.getPrefs().getInt(PreferencesConstants.DIFFICULTY, 1);
        setChecked(difficultyRadioGroup, currentDifficulty - 1);
        difficultyRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	switch(checkedId) {
            	case R.id.radioDiffEasy:
            		PrefsUtil.setPrefs(int.class, PreferencesConstants.DIFFICULTY, DifficultyEnum.EASY.getValue());
            		break;
            	case R.id.radioDiffNormal:
            		PrefsUtil.setPrefs(int.class, PreferencesConstants.DIFFICULTY, DifficultyEnum.NORMAL.getValue());
            		break; 
            	case R.id.radioDiffHard:
            		PrefsUtil.setPrefs(int.class, PreferencesConstants.DIFFICULTY, DifficultyEnum.HARD.getValue());
            		break;           		
            	}
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_options, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			returnResult(AbstractActivity.RETURN_NOTHING);
			//this.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
			break;
		case KeyEvent.KEYCODE_MENU:

		default:
			break;
		}
		return false;
	}
	
	private void setChecked(RadioGroup radioGroup, int position) {
		RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
		radioButton.setChecked(true);
	}


}
