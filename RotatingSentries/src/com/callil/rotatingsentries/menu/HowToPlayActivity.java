package com.callil.rotatingsentries.menu;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.callil.rotatingsentries.R;


public class HowToPlayActivity extends AbstractActivity {
	

//	private ListView enemyList;
//	private ListView itemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setTagParent("HowToPlay");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_how_to_play);
        
//        enemyList = (ListView) findViewById(R.id.enemies);
//        itemList = (ListView) findViewById(R.id.itemsList);
        
        //Fill lists
//        List<GameEntity> enemies = new ArrayList<GameEntity>();
//        List<GameEntity> items = new ArrayList<GameEntity>();
        
        //Enemies
//        enemies.add(new Caterpillar());
        
        //Items
//        items.add(new BalloonCrate());
        
//        enemyList.setAdapter(new Item_Adapter(this, enemies));
//        itemList.setAdapter(new Item_Adapter(this, items));
        
//        DesignUtil.setListViewHeightBasedOnChildren(enemyList);
//        DesignUtil.setListViewHeightBasedOnChildren(itemList);
        
        ScrollView sv = (ScrollView)findViewById(R.id.scrollView);
        sv.smoothScrollTo(0,0);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_how_to_play, menu);
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
			finish();
			//this.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
			break;
		case KeyEvent.KEYCODE_MENU:

		default:
			break;
		}
		return false;
	}


}
