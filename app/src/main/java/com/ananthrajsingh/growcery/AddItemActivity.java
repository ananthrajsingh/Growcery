package com.ananthrajsingh.growcery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ananthrajsingh.growcery.Model.Item;

/**
 * In this activity we are adding new growcery items to database.
 */
public class AddItemActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.ananthrajsingh.growcery.NAME";
    public static final String EXTRA_AMOUNT = "com.ananthrajsingh.growcery.AMOUNT";
    public static final String EXTRA_UNIT = "com.ananthrajsingh.growcery.UNIT";
    private EditText mEditName;
    private EditText mEditAmount;
    private Spinner mSpinnerUnit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEditName = findViewById(R.id.edit_item);
        mEditAmount = findViewById(R.id.edit_amount);
        mSpinnerUnit = findViewById(R.id.spinner_unit);
        setContentView(R.layout.activity_add_item);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditName.getText()) || TextUtils.isEmpty(mEditAmount.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = mEditName.getText().toString();
                    String amount = mEditAmount.getText().toString();
                    int unit = mSpinnerUnit.getSelectedItemPosition();
                    Item item = new Item();
                    item.setName(name);
                    item.setQuantity(Integer.parseInt(amount));
                    item.setUnitType(unit);
                    replyIntent.putExtra(EXTRA_NAME, name);
                    replyIntent.putExtra(EXTRA_AMOUNT, amount);
                    replyIntent.putExtra(EXTRA_UNIT, unit);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
