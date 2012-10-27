
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SurveyActivity extends Activity implements OnClickListener {

    ImageButton btnBack;
    TextView txtTitle;
    TextView txtServeyQuestion;
    Button btnChoiceA;
    Button btnChoiceB;
    Button btnChoiceC;
    Button btnChoiceD;
    Button btnNext;

    ArrayList<HashMap<String, Object>> data;
    int current = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        initViews();
        data = loadData();
        nextQuestion();
    }

    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initViews() {

        btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        txtTitle = (TextView) findViewById(R.id.txt_header_title2);
        txtTitle.setText(getResources().getString(R.string.survey));

        txtServeyQuestion = (TextView) findViewById(R.id.txt_survey_question);
        btnChoiceA = (Button) findViewById(R.id.btn_survey_choice_A);
        btnChoiceB = (Button) findViewById(R.id.btn_survey_choice_B);
        btnChoiceC = (Button) findViewById(R.id.btn_survey_choice_C);
        btnChoiceD = (Button) findViewById(R.id.btn_survey_choice_D);
        btnNext = (Button) findViewById(R.id.btn_survey_next);

        btnBack.setOnClickListener(this);
        btnChoiceA.setOnClickListener(this);
        btnChoiceB.setOnClickListener(this);
        btnChoiceC.setOnClickListener(this);
        btnChoiceD.setOnClickListener(this);
        btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Btn_back_header2:
                SurveyActivity.this.finish();
                break;
            case R.id.btn_survey_choice_A:
                break;
            case R.id.btn_survey_choice_B:
                break;
            case R.id.btn_survey_choice_C:
                break;
            case R.id.btn_survey_choice_D:
                break;
            case R.id.btn_survey_next:
                if (current == data.size() - 1) {
                    commitAnswer();
                } else {
                    nextQuestion();
                }
                break;
        }

    }

    private ArrayList<HashMap<String, Object>> loadData() {

        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap map1 = new HashMap<String, Object>();
        map1.put("question", "农民工进城打工，温饱问题尚未解决，你怎么看待这个问题？");
        map1.put("choiceA", "需要加强扶持");
        map1.put("choiceB", "已经足够，无需调整");
        map1.put("choiceC", "恐怕很难改变");
        map1.put("choiceD", "没什么感觉");
        data.add(map1);

        HashMap map2 = new HashMap<String, Object>();
        map2.put("question", "莫言获得诺贝尔文学家，他的作品你看过吗？");
        map2.put("choiceA", "自豪的说看过");
        map2.put("choiceB", "我落后了");
        map2.put("choiceC", "准备拜读");
        map2.put("choiceD", "不看过小说");
        data.add(map2);

        return data;
    }

    private void nextQuestion() {

        current++;
        txtServeyQuestion.setText(data.get(current).get("question").toString());
        btnChoiceA.setText("A." + data.get(current).get("choiceA").toString());
        btnChoiceB.setText("B." + data.get(current).get("choiceB").toString());
        btnChoiceC.setText("C." + data.get(current).get("choiceC").toString());
        btnChoiceD.setText("D." + data.get(current).get("choiceD").toString());

        if (current == data.size() - 1) {
            btnNext.setText(getResources().getString(R.string.commit));
        }

    }

    private void commitAnswer() {

        Intent intent = new Intent(SurveyActivity.this, SurveyFinishActivity.class);
        startActivity(intent);

    }
}
