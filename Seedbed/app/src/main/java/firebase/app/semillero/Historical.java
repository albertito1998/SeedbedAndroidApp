package firebase.app.semillero;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.lang.Long.parseLong;


public class Historical extends AppCompatActivity {
    private static final String TAG = Historical.class.getSimpleName() ;
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //LINECHARTS
    LineChart mpLineChart, HumidityAirLineChart, TemperatureLineChart, CO2LineChart, LDRLineChart;
    Legend legend, legendTemperature, legendHumidityAir, legendCO2, legendLDR;
    TextView maxHumidityGround, dayMaxHumidityGround, averageHumidityGround;
    TextView maxHumidityAir, dayMaxHumidityAir, averageHumidityAir;
    TextView maxTemperature, dayMaxTemperature, averageTemperature;
    TextView maxLDR, dayMaxLDR, averageLDR;
    TextView maxCO2, dayMaxCO2, averageCO2;
    //COLORS
    private int[] colorClassArray= {Color.BLUE, Color.RED, Color.RED };

    // LEGENDS
    private String[] legendNameHumidityGround= {"Humidity Ground","Upper Limit", "Lower Limit"};
    private String[] legendNameTemperature= {"Temperature","Upper Limit", "Lower Limit"};
    private String[] legendNameHumidityAir= {"Humidity Air","Upper Limit", "Lower Limit"};
    private String[] legendNameCO2= {"CO2","Upper Limit", "Lower Limit"};
    private String[] legendNameLDR= {"LDR","Upper Limit", "Lower Limit"};

    // DATASETS
    LineDataSet lineDataSet = new LineDataSet(null,null);
    LineDataSet lineDataSetTemperature = new LineDataSet(null,null);
    LineDataSet lineDataSetHumidityAir = new LineDataSet(null,null);
    LineDataSet lineDataSetCO2 = new LineDataSet(null,null);
    LineDataSet lineDataSetLDR = new LineDataSet(null,null);

    // DATEPICKERS
    Long starting;
    Long ending;
    private TextView startDateDisplay;
    private TextView endDateDisplay;
    int year;
 int month;
 int dayOfMonth;
 Calendar calendar;
 DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog1;



    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_data);
        Button save = findViewById(R.id.SHOW);

        // CHARTS
        mpLineChart = (LineChart) findViewById(R.id.layoutHumidityGroundLineChart);
        HumidityAirLineChart = (LineChart) findViewById(R.id.layoutHumidityAirLineChart);
        TemperatureLineChart = (LineChart) findViewById(R.id.layoutTemperatureLineChart);
        CO2LineChart = (LineChart) findViewById(R.id.layoutCO2LineChart);
        LDRLineChart = (LineChart) findViewById(R.id.layoutLDRLineChart);


        //HUMIDITY GROUND

        TableLayout tableLayoutHumidityGround=findViewById(R.id.HumidityGroundTable);
        maxHumidityGround =findViewById(R.id.MaxHumidityGroundValue);
        averageHumidityGround=findViewById(R.id.AverageHumidityGroundValue);
        dayMaxHumidityGround=findViewById(R.id.DayMaxHumidityGroundValue);

        //TEMPERATURE

        TableLayout tableLayoutTemperature=findViewById(R.id.TemperatureTable);
        maxTemperature =findViewById(R.id.MaxTemperatureValue);
        averageTemperature=findViewById(R.id.AverageTemperatureValue);
        dayMaxTemperature=findViewById(R.id.DayMaxTemperatureValue);

        //HUMIDITY AIR

        TableLayout tableLayoutHumidityAir=findViewById(R.id.HumidityAirTable);
        maxHumidityAir=findViewById(R.id.MaxHumidityAirValue);
        averageHumidityAir=findViewById(R.id.AverageHumidityAirValue);
        dayMaxHumidityAir=findViewById(R.id.DayMaxHumidityAirValue);

        //LDR

        TableLayout tableLayoutLDR=findViewById(R.id.LDRTable);
        maxLDR =findViewById(R.id.MaxLDRValue);
        averageLDR=findViewById(R.id.AverageLDRValue);
        dayMaxLDR=findViewById(R.id.DayMaxLDRValue);

        //CO2

        TableLayout tableLayoutCO2=findViewById(R.id.CO2Table);
        maxCO2 =findViewById(R.id.MaxCO2Value);
        averageCO2=findViewById(R.id.AverageCO2Value);
        dayMaxCO2=findViewById(R.id.DayMaxCO2Value);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        calendar = Calendar.getInstance();
        year= calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfMonth =calendar.get(Calendar.DAY_OF_MONTH);

        /*  capture our View elements for the start date function   */
        startDateDisplay = (TextView) findViewById(R.id.startDateDisplay);
        Button startPickDate = (Button) findViewById(R.id.startPickDate);


        /* capture our View elements for the end date function */
        endDateDisplay = (TextView) findViewById(R.id.endDateDisplay);
        Button endPickDate = (Button) findViewById(R.id.endPickDate);

     // HUMIDITY GROUND CHART

        legend = mpLineChart.getLegend();
        iLineDataSets.add(lineDataSet);

        mpLineChart.getXAxis().setTextSize(5);

        mpLineChart.setBackgroundColor(Color.WHITE);
        mpLineChart.setNoDataText("No data loaded");
        mpLineChart.setNoDataTextColor(Color.BLACK);
        mpLineChart.setDrawGridBackground(true);
        mpLineChart.setDrawBorders(true);
        mpLineChart.setBorderColor(Color.BLACK);
        mpLineChart.setBorderWidth(1);

        lineDataSet.setLineWidth(1);
        lineDataSet.setColor(colorClassArray[1]);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleColor(Color.GRAY);

        lineDataSet.setColor(colorClassArray[2]);

        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(7);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(10);
        legend.setXEntrySpace(15);
        legend.setFormToTextSpace(10);

        LegendEntry[] legendEntries = new LegendEntry[3];

        int HumidityTopLimit = 70;
        LimitLine ll = new LimitLine(HumidityTopLimit, "Upper Limit");
        mpLineChart.getAxisLeft().addLimitLine(ll);
        ll.setLineWidth(1f);
        ll.setTextSize(10f);
        ll.setLineColor(Color.RED);

        int HumidityLowerLimit = 30;
        LimitLine l2 = new LimitLine(HumidityLowerLimit, "Lower Limit");
        mpLineChart.getAxisLeft().addLimitLine(l2);
        l2.setLineWidth(1f);
        l2.setTextSize(10f);
        l2.setLineColor(Color.RED);



        for (int i = 0; i < legendEntries.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colorClassArray[i];
            entry.label = String.valueOf(legendNameHumidityGround[i]);
            legendEntries[i] = entry;
        }

        legend.setCustom(legendEntries);


        Description description = new Description();
        description.setText("Humidity Ground %");
        description.setTextColor(Color.BLACK);
        description.setTextSize(15);
        mpLineChart.setDescription(description);

        mpLineChart.getXAxis().setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));

        }});


        // TEMPERATURE CHART

        legendTemperature = TemperatureLineChart.getLegend();
        iLineDataSets.add(lineDataSetTemperature);

        TemperatureLineChart.getXAxis().setTextSize(5);

        TemperatureLineChart.setBackgroundColor(Color.WHITE);
        TemperatureLineChart.setNoDataText("No data loaded");
        TemperatureLineChart.setNoDataTextColor(Color.BLACK);
        TemperatureLineChart.setDrawGridBackground(true);
        TemperatureLineChart.setDrawBorders(true);
        TemperatureLineChart.setBorderColor(Color.BLACK);
        TemperatureLineChart.setBorderWidth(1);

        lineDataSetTemperature.setLineWidth(1);
        lineDataSetTemperature.setColor(colorClassArray[1]);
        lineDataSetTemperature.setDrawCircles(true);
        lineDataSetTemperature.setDrawCircleHole(true);
        lineDataSetTemperature.setCircleColor(Color.GRAY);

        lineDataSetTemperature.setColor(colorClassArray[2]);

        legendTemperature.setEnabled(true);
        legendTemperature.setTextColor(Color.BLACK);
        legendTemperature.setTextSize(7);
        legendTemperature.setForm(Legend.LegendForm.LINE);
        legendTemperature.setFormSize(10);
        legendTemperature.setXEntrySpace(15);
        legendTemperature.setFormToTextSpace(10);

        LegendEntry[] legendEntriesTemperature = new LegendEntry[3];

        int TemperatureTopLimit =32;
        LimitLine llTemperature = new LimitLine(TemperatureTopLimit, "Upper Limit");
        TemperatureLineChart.getAxisLeft().addLimitLine(llTemperature);
        llTemperature.setLineWidth(1f);
        llTemperature.setTextSize(10f);
        llTemperature.setLineColor(Color.RED);

        int TemperatureLowerLimit = 15;
        LimitLine l2Temperature = new LimitLine(TemperatureLowerLimit, "Lower Limit");
        mpLineChart.getAxisLeft().addLimitLine(l2Temperature);
        l2Temperature.setLineWidth(1f);
        l2Temperature.setTextSize(10f);
        l2Temperature.setLineColor(Color.RED);



        for (int i = 0; i < legendEntriesTemperature.length; i++) {
            LegendEntry entryTemperature = new LegendEntry();
            entryTemperature.formColor = colorClassArray[i];
            entryTemperature.label = String.valueOf(legendNameTemperature[i]);
            legendEntriesTemperature[i] = entryTemperature;
        }

        legendTemperature.setCustom(legendEntriesTemperature);


        Description descriptionTemperature = new Description();
        descriptionTemperature.setText("Temperature ºC");
        descriptionTemperature.setTextColor(Color.BLACK);
        descriptionTemperature.setTextSize(15);
        TemperatureLineChart.setDescription(descriptionTemperature);

        TemperatureLineChart.getXAxis().setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));

            }});

        // HUMIDITYAIR CHART

        legendHumidityAir = HumidityAirLineChart.getLegend();
        iLineDataSets.add(lineDataSetHumidityAir);

        HumidityAirLineChart.getXAxis().setTextSize(5);

        HumidityAirLineChart.setBackgroundColor(Color.WHITE);
        HumidityAirLineChart.setNoDataText("No data loaded");
        HumidityAirLineChart.setNoDataTextColor(Color.BLACK);
        HumidityAirLineChart.setDrawGridBackground(true);
        HumidityAirLineChart.setDrawBorders(true);
        HumidityAirLineChart.setBorderColor(Color.BLACK);
        HumidityAirLineChart.setBorderWidth(1);

        lineDataSetHumidityAir.setLineWidth(1);
        lineDataSetHumidityAir.setColor(colorClassArray[1]);
        lineDataSetHumidityAir.setDrawCircles(true);
        lineDataSetHumidityAir.setDrawCircleHole(true);
        lineDataSetHumidityAir.setCircleColor(Color.GRAY);

        lineDataSetHumidityAir.setColor(colorClassArray[2]);

        legendHumidityAir.setEnabled(true);
        legendHumidityAir.setTextColor(Color.BLACK);
        legendHumidityAir.setTextSize(7);
        legendHumidityAir.setForm(Legend.LegendForm.LINE);
        legendHumidityAir.setFormSize(10);
        legendHumidityAir.setXEntrySpace(15);
        legendHumidityAir.setFormToTextSpace(10);

        LegendEntry[] legendEntriesHumidityAir = new LegendEntry[3];

        int HumidityAirTopLimit =70;
        LimitLine llHumidityAir = new LimitLine(HumidityAirTopLimit, "Upper Limit");
        HumidityAirLineChart.getAxisLeft().addLimitLine(llHumidityAir);
        llHumidityAir.setLineWidth(1f);
        llHumidityAir.setTextSize(10f);
        llHumidityAir.setLineColor(Color.RED);

        int HumidityAirLowerLimit = 30;
        LimitLine l2HumidityAir = new LimitLine(HumidityAirLowerLimit, "Lower Limit");
        HumidityAirLineChart.getAxisLeft().addLimitLine(l2HumidityAir);
        l2HumidityAir.setLineWidth(1f);
        l2HumidityAir.setTextSize(10f);
        l2HumidityAir.setLineColor(Color.RED);



        for (int i = 0; i < legendEntriesHumidityAir.length; i++) {
            LegendEntry entryHumidityAir = new LegendEntry();
            entryHumidityAir.formColor = colorClassArray[i];
            entryHumidityAir.label = String.valueOf(legendNameHumidityAir[i]);
            legendEntriesHumidityAir[i] = entryHumidityAir;
        }

        legendHumidityAir.setCustom(legendEntriesTemperature);


        Description descriptionHumidityAir= new Description();
        descriptionHumidityAir.setText("Humidity Air %");
        descriptionHumidityAir.setTextColor(Color.BLACK);
        descriptionHumidityAir.setTextSize(15);
        HumidityAirLineChart.setDescription(descriptionHumidityAir);

        HumidityAirLineChart.getXAxis().setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));

            }});

        // LDR CHART

        legendLDR = LDRLineChart.getLegend();
        iLineDataSets.add(lineDataSetLDR);

        LDRLineChart.getXAxis().setTextSize(5);

        LDRLineChart.setBackgroundColor(Color.WHITE);
        LDRLineChart.setNoDataText("No data loaded");
        LDRLineChart.setNoDataTextColor(Color.BLACK);
        LDRLineChart.setDrawGridBackground(true);
        LDRLineChart.setDrawBorders(true);
        LDRLineChart.setBorderColor(Color.BLACK);
        LDRLineChart.setBorderWidth(1);

        lineDataSetLDR.setLineWidth(1);
        lineDataSetLDR.setColor(colorClassArray[1]);
        lineDataSetLDR.setDrawCircles(true);
        lineDataSetLDR.setDrawCircleHole(true);
        lineDataSetLDR.setCircleColor(Color.GRAY);

        lineDataSetLDR.setColor(colorClassArray[2]);

        legendLDR.setEnabled(true);
        legendLDR.setTextColor(Color.BLACK);
        legendLDR.setTextSize(7);
        legendLDR.setForm(Legend.LegendForm.LINE);
        legendLDR.setFormSize(10);
        legendLDR.setXEntrySpace(15);
        legendLDR.setFormToTextSpace(10);

        LegendEntry[] legendEntriesLDR = new LegendEntry[3];

        int LDRTopLimit =70;
        LimitLine llLDR = new LimitLine(LDRTopLimit, "Night");
        LDRLineChart.getAxisLeft().addLimitLine(llLDR);
        llLDR.setLineWidth(1f);
        llLDR.setTextSize(10f);
        llLDR.setLineColor(Color.RED);

        int LDRLowerLimit = 30;
        LimitLine l2LDR = new LimitLine(LDRLowerLimit, "Day");
        LDRLineChart.getAxisLeft().addLimitLine(l2LDR);
        l2LDR.setLineWidth(1f);
        l2LDR.setTextSize(10f);
        l2LDR.setLineColor(Color.RED);



        for (int i = 0; i < legendEntriesLDR.length; i++) {
            LegendEntry entryLDR = new LegendEntry();
            entryLDR.formColor = colorClassArray[i];
            entryLDR.label = String.valueOf(legendNameLDR[i]);
            legendEntriesLDR[i] = entryLDR;
        }

        legendLDR.setCustom(legendEntriesLDR);


        Description descriptionLDR= new Description();
        descriptionLDR.setText("LDR lux");
        descriptionLDR.setTextColor(Color.BLACK);
        descriptionLDR.setTextSize(15);
        LDRLineChart.setDescription(descriptionLDR);

        LDRLineChart.getXAxis().setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));

            }});



        // CO2 CHART

        legendCO2 = CO2LineChart.getLegend();
        iLineDataSets.add(lineDataSetCO2);

        CO2LineChart.getXAxis().setTextSize(5);

        CO2LineChart.setBackgroundColor(Color.WHITE);
        CO2LineChart.setNoDataText("No data loaded");
        CO2LineChart.setNoDataTextColor(Color.BLACK);
        CO2LineChart.setDrawGridBackground(true);
        CO2LineChart.setDrawBorders(true);
        CO2LineChart.setBorderColor(Color.BLACK);
        CO2LineChart.setBorderWidth(1);

        lineDataSetCO2.setLineWidth(1);
        lineDataSetCO2.setColor(colorClassArray[1]);
        lineDataSetCO2.setDrawCircles(true);
        lineDataSetCO2.setDrawCircleHole(true);
        lineDataSetCO2.setCircleColor(Color.GRAY);

        lineDataSetCO2.setColor(colorClassArray[2]);

        legendCO2.setEnabled(true);
        legendCO2.setTextColor(Color.BLACK);
        legendCO2.setTextSize(7);
        legendCO2.setForm(Legend.LegendForm.LINE);
        legendCO2.setFormSize(10);
        legendCO2.setXEntrySpace(15);
        legendCO2.setFormToTextSpace(10);

        LegendEntry[] legendEntriesCO2 = new LegendEntry[3];

        int CO2TopLimit =1500;
        LimitLine llCO2 = new LimitLine(CO2TopLimit, "Gas Alert");
        CO2LineChart.getAxisLeft().addLimitLine(llCO2);
        llCO2.setLineWidth(1f);
        llCO2.setTextSize(10f);
        llCO2.setLineColor(Color.RED);

        int CO2LowerLimit = 400;
        LimitLine l2CO2 = new LimitLine(CO2LowerLimit, "Nomal Gas");
        CO2LineChart.getAxisLeft().addLimitLine(l2CO2);
        l2CO2.setLineWidth(1f);
        l2CO2.setTextSize(10f);
        l2CO2.setLineColor(Color.RED);



        for (int i = 0; i < legendEntriesCO2.length; i++) {
            LegendEntry entryCO2 = new LegendEntry();
            entryCO2.formColor = colorClassArray[i];
            entryCO2.label = String.valueOf(legendNameCO2[i]);
            legendEntriesCO2[i] = entryCO2;
        }

        legendCO2.setCustom(legendEntriesCO2);


        Description descriptionCO2= new Description();
        descriptionCO2.setText("CO2 ppm");
        descriptionCO2.setTextColor(Color.BLACK);
        descriptionCO2.setTextSize(15);
        CO2LineChart.setDescription(descriptionCO2);

        CO2LineChart.getXAxis().setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));

            }});


        startPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton1();
            }
        });
        endPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton2();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent(Historical.this, ActivityOne.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.nav_historical:
                        Intent intent2 = new Intent(Historical.this, ActivityTwo.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.nav_profile:
                        Intent intent3 = new Intent(Historical.this, ActivityThree.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.nav_actuators:
                        Intent intent4 = new Intent(Historical.this, ActivityFour.class);
                        startActivity(intent4);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;


                    case R.id.nav_LogOut:
                        Intent intent5 = new Intent(Historical.this, MainActivity.class);
                        startActivity(intent5);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                }


                return false;
            }
        });

    }

    private void save()  {
        Date startPoint = new Date(starting);
        Date endPoint = new Date(ending);
        db.collection("sensors")
                .whereLessThan("timestamp", endPoint)
                .whereGreaterThan("timestamp", startPoint)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        // DEFINITION OF ARRAYS
                        ArrayList<Entry> HumidityGroundVals = new ArrayList<Entry>();
                        ArrayList<Entry> HumidityAirVals = new ArrayList<Entry>();
                        ArrayList<Entry> TemperatureVals = new ArrayList<Entry>();
                        ArrayList<Entry> CO2Vals = new ArrayList<Entry>();
                        ArrayList<Entry> LDRVals = new ArrayList<Entry>();


                        /* float Average=0;
                        float sum=0;
                        String MaxDayHumidityGround="", MaxDayHumidityAir="",MaxDayTemperature="",MaxDayLDR="",MaxDayCO2="";
                        float MaxHumidityGround =0, MaxHumidityAir =0,MaxTemperature =0, MaxLDR =0, MaxCO2 =0; */

                        // GROUND; AIR; TEMP; LDR; CO2

                       String MaxDay[]= new String[5];
                        float  MaxValue[] = new float[5];
                        float  Average[] = new float[5];
                        for (int j=0;j<=4;j++){
                            MaxValue[j]=0;
                            Average[j]=0;
                            MaxDay[j]="";
                        }
                        float count=0;
                        long time;
                        float i=0;
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                DataPoint  dataPoint = document.toObject(DataPoint.class);
                               Date date = dataPoint.getTimestamp().toDate();
                               time= date.getTime();
                               i=i+1;

                               // INDEX VALUES
                                HumidityGroundVals.add(new Entry( i, Float.parseFloat(dataPoint.getHumidityGround())));
                            //    HumidityAirVals.add(new Entry( i, Float.parseFloat(dataPoint.getHumidityAir())));
                                TemperatureVals.add(new Entry( i, Float.parseFloat(dataPoint.getTemperature())));
                                LDRVals.add(new Entry( i, Float.parseFloat(dataPoint.getLDR())));
                                CO2Vals.add(new Entry( i, Float.parseFloat(dataPoint.getCO2())));

                                // HUMIDITY GROUND
                                if(Float.parseFloat(dataPoint.getHumidityGround())>MaxValue[0]){
                                    MaxValue[0]=Float.parseFloat(dataPoint.getHumidityGround());
                                    MaxDay[0]=(date.toString());
                                }
                                Average[0]= Average[0]+ Float.parseFloat(dataPoint.getHumidityGround());

   /*                             // HUMIDITY AIR
                                if(Float.parseFloat(dataPoint.getHumidityGround())>MaxValue[1]){
                                    MaxValue[1]=Float.parseFloat(dataPoint.getHumidityAir());
                                    MaxDay[1]=(date.toString());
                                }
                                Average[1]= Average[1]+ Float.parseFloat(dataPoint.getHumidityAir());
*/
                                // TEMPERATURE
                                if(Float.parseFloat(dataPoint.getTemperature())>MaxValue[2]){
                                    MaxValue[2]=Float.parseFloat(dataPoint.getTemperature());
                                    MaxDay[2]=(date.toString());
                                }
                                Average[2]= Average[2]+ Float.parseFloat(dataPoint.getTemperature());

                                // LDR
                                if(Float.parseFloat(dataPoint.getTemperature())>MaxValue[3]){
                                    MaxValue[3]=Float.parseFloat(dataPoint.getLDR());
                                    MaxDay[3]=(date.toString());
                                }
                                Average[3]= Average[3]+ Float.parseFloat(dataPoint.getLDR());

                                // CO2
                                if(Float.parseFloat(dataPoint.getTemperature())>MaxValue[4]){
                                    MaxValue[4]=Float.parseFloat(dataPoint.getCO2());
                                    MaxDay[4]=(date.toString());
                                }
                                Average[4]= Average[4]+ Float.parseFloat(dataPoint.getCO2());

                                count++;


                       // INDEX TABLES
                       // HUMIDITY GROUND
                            maxHumidityGround.setText( String.valueOf(MaxValue[0]));
                            dayMaxHumidityGround.setText(MaxDay[1]);
                            dayMaxHumidityGround.setTextSize(7);
                            averageHumidityGround.setText(String.valueOf(Average[0]/count));
/*
                            // HUMIDITY AIR
                                maxHumidityAir.setText( String.valueOf(MaxValue[1]));
                                dayMaxHumidityAir.setText(MaxDay[1]);
                                dayMaxHumidityAir.setTextSize(7);
                                averageHumidityAir.setText(String.valueOf(Average[1]/count));
*/
                                // TEMPERATURE
                                maxTemperature.setText( String.valueOf(MaxValue[2]));
                                dayMaxTemperature.setText(MaxDay[2]);
                                dayMaxTemperature.setTextSize(7);
                                averageTemperature.setText(String.valueOf(Average[2]/count));

                                // LDR
                                maxLDR.setText( String.valueOf(MaxValue[3]));
                                dayMaxLDR.setText(MaxDay[3]);
                                dayMaxLDR.setTextSize(7);
                                averageLDR.setText(String.valueOf(Average[3]/count));

                                // CO2
                                maxCO2.setText( String.valueOf(MaxValue[4]));
                                dayMaxCO2.setText(MaxDay[4]);
                                dayMaxCO2.setTextSize(7);
                                averageCO2.setText(String.valueOf(Average[4]/count));


                            }

                            // CREATE THE CHARTS

                            // HUMIDITY GROUND
                            final LineDataSet lineDataSet = new LineDataSet(HumidityGroundVals, "Humidity ground");
                            LineData data = new LineData(lineDataSet);
                            mpLineChart.setData(data);
                            mpLineChart.notifyDataSetChanged();
                            mpLineChart.invalidate();
/*
                            // HUMIDITY AIR
                            final LineDataSet lineDataSetHumidityAir = new LineDataSet(HumidityAirVals, "Humidity Air");
                            LineData dataHumidityAir = new LineData(lineDataSetHumidityAir);
                            HumidityAirLineChart.setData(dataHumidityAir);
                            HumidityAirLineChart.notifyDataSetChanged();
                            HumidityAirLineChart.invalidate();

*/
                            // TEMPERATURE
                            final LineDataSet lineDataSetTemperature = new LineDataSet(TemperatureVals, "Temperature");
                            LineData dataTemperature = new LineData(lineDataSetTemperature);
                            TemperatureLineChart.setData(dataTemperature);
                            TemperatureLineChart.notifyDataSetChanged();
                            TemperatureLineChart.invalidate();

                            // CO2
                            final LineDataSet lineDataSetCO2 = new LineDataSet(CO2Vals, "CO2");
                            LineData dataCO2 = new LineData(lineDataSetCO2);
                            CO2LineChart.setData(dataCO2);
                            CO2LineChart.notifyDataSetChanged();
                            CO2LineChart.invalidate();

                            // LDR
                            final LineDataSet lineDataSetLDR = new LineDataSet(LDRVals, "CO2");
                            LineData dataLDR = new LineData(lineDataSetLDR);
                            LDRLineChart.setData(dataLDR);
                            LDRLineChart.notifyDataSetChanged();
                            LDRLineChart.invalidate();
                        } else {
                            // HUMIDITY GROUND
                            mpLineChart.clear();
                            mpLineChart.invalidate();
                            // HUMIDITY AIR
                            HumidityAirLineChart.clear();
                            HumidityAirLineChart.invalidate();
                            // TEMPERATURE
                            TemperatureLineChart.clear();
                            TemperatureLineChart.invalidate();
                            // CO2
                            CO2LineChart.clear();
                            CO2LineChart.invalidate();
                            // LDR
                            LDRLineChart.clear();
                            LDRLineChart.invalidate();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    //BUTTONS DATEPICKERS
    private void handleDateButton1(){

         datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                 starting = calendar1.getTimeInMillis();
                String dateText1 = DateFormat.format("EEE, MMM d, yyyy", calendar1).toString();

                startDateDisplay.setText(dateText1);
            }
        }, year, month, dayOfMonth);
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        long april = calendar.getTimeInMillis();
         datePickerDialog.getDatePicker().setMinDate(april);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void handleDateButton2(){

        datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, date);
                ending = calendar.getTimeInMillis();
                String dateText = DateFormat.format("EEE, MMM d, yyyy", calendar).toString();

                endDateDisplay.setText(dateText);
            }
        }, year, month, dayOfMonth);
        long april = calendar.getTimeInMillis();
        datePickerDialog1.getDatePicker().setMinDate(april);
        datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog1.show();
    }
}





