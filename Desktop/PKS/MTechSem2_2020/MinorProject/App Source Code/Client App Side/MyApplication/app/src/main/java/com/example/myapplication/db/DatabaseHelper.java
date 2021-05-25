package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databasehelper;
    private Context context;

    public static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "test.db";
    public static final int DATABASE_VERSION = 4; //changed 2 on 10-Apr-2021, changed 3 on 20-Apr-2021, changed 3 on 11-May-2021(Hindi quest)


//    public static final String TABLE_NAME_2 = "updates";
//   public static final String TABLE_2_CREATE = "Create table "
//            + TABLE_NAME_2
//            + " (_id integer primary key autoincrement, w_id text, title text, des text, date_text text, image_url text, video_url text,link text, con_type text, con_source text, timestamp integer, UNIQUE (_id) ON CONFLICT REPLACE)";


    //to enforce singleton
    public static DatabaseHelper getInstance(Context context) {
        //context.deleteDatabase(DATABASE_NAME);//Uncomment line to reset sqlite database

        if (databasehelper == null)
            databasehelper = new DatabaseHelper(context);
        return databasehelper;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists branching_logic_info(" +
                "lvl float," +
                "que_id float," +
                "ans_yes_id float," +
                "ans_no_id float," +
                "prev_que_id float," +
                "que_str_en text," +
                "que_str_hi text," +
                "remarks text," +
                "primary key(lvl, que_id)" +
                ")" );
        db.execSQL("create table if not exists answer_info(" +
                "lvl float," +
                "ans_id  float," +
                "headache_cat  integer," +
                "primary key(lvl,ans_id)" +
                ")");
        db.execSQL("create table if not exists headache_cat_info(" +
                "headache_cat integer," +
                "cat_str_en text," +
                "cat_str_hi text," +
                "primary key(headache_cat)" +
                ")");
        db.execSQL("Create table if not exists multi_branch_logic_info(" +
                "lvl float," +
                "que_group_id integer," +
                "min_yes_response integer," +
                "next_que_group_id integer," +
                "headache_cat integer," +
                "primary key(lvl,que_group_id,min_yes_response)" +
                ")");

        db.execSQL("Create table if not exists patient_test_basic_info(" +
                "t_id  integer primary key autoincrement," +
                "p_id  text," +
                "dr_id text," +
                "test_st_date date," +
                "test_end_date date," +
                "is_test_completed integer," +
                "unique (t_id,p_id)" +
                ")");
        db.execSQL("Create table if not exists test_detail_data(" +
                "t_id integer," +
                "p_id  text," +
                "lvl float," +
                "lvl_status text," +
                "que_ans_id  float," +
                "lvl_result_headache_cat integer," +
                "primary key(t_id,p_id, lvl)" +
                ")");
        db.execSQL("Create table if not exists patient_response_data(" +
                "t_id integer," +
                "p_id integer," +
                "lvl float," +
                "que_group_id integer," +
                "que_id  float," +
                "response_yes_no integer," +
                "response_str varchar2(100)," +
                "primary key(t_id, p_id,lvl,que_id)" +
                ")");

        db.execSQL("Create table if not exists patient_detail_info(" +
                "p_id text," +
                "pwd text," +
                "name text," +
                "dob date," +
                "gender  text," +
                "address text," +
                "mobile_no long," +
                "adhaar_no long," +
                "email text," +
                "health_history text," +
                "image text," +
                "primary key(p_id)" +
                ")");


        Log.i(TAG,"Database has been created.");

//        db.delete("headache_cat_info",null,null);
//        db.delete("branching_logic_info",null,null);
//        db.delete("answer_info",null,null);
//        db.delete("multi_branch_logic_info",null,null);


        //Insert Scripts
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (-1, 'Not Applicable', 'लागू नहीं' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (0, 'Headache Free', 'सिरदर्द मुक्त' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (1, 'Episodic Headache', 'प्रासंगिक सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (2, 'Chronic Headache', 'पुराना सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (3, 'Primary Headache', 'प्राथमिक सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (4, 'Secondary Headache', 'द्वितीयक सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (5, 'Non-migrainous Headache', 'गैर-माइग्रेन सिरदर्द' )"); //not in use
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (6, 'Migraine', 'माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (7, 'Probable Migraine', 'संभावित माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (8, 'Migraine without aura', 'आभा के बिना माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (9, 'Migraine with aura', 'आभा के साथ माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (10, 'Probable Migraine with aura', 'आभा के साथ संभावित माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (11, 'Migraine with typical aura', 'विशिष्ट आभा के साथ माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (12, 'Migraine with brainstem aura', 'दिमागी आभा वाली माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (13, 'Retinal Migraine', 'रेटिना माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (14, 'Hemiplegic Migraine', 'नकसीर माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (15, 'Tension Type Headache', 'तनाव का सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (16, 'Cluster Headache', 'क्लस्टर सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (17, 'Paroxysmal hemicrania', 'पैरोक्सिमल हेमिक्रानिया' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (18, 'Short-lasting unilateral neuralgiform headache attacks', 'लघु-स्थायी एकतरफा न्यूरलगर्मिस सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (19, 'Status Migrainous', 'स्थिति माइग्रेनस' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (20, 'New Daily Persistent Headache', 'नई दैनिक लगातार सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (21, 'Chronic Migraine', 'क्रोनिक माइग्रेन' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (22, 'Chronic Tension Type', 'क्रोनिक तनाव' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (23, 'Chronic hemicrania continua', 'क्रोनिक हेमिक्रानिया कॉन्टुआ' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (24, 'Chronic Cluster Headache', 'क्रोनिक क्लस्टर सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (25, 'Chronic Paroxysmal hemicrania', 'क्रोनिक पैरोक्सिमल हेमिक्रानिया' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (26, 'Chronic short-lasting unilateral neuralgiform', 'क्रोनिक शॉर्ट-चिरस्थाई एकतरफा न्यूरलजीफॉर्म' )");

        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (27, 'Epicrania fugax', 'एपिक्रेनिया फुगाक्स' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (28, 'Primary stabbing headache', 'प्राथमिक वेधन सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (29, 'Primary cough headache', 'प्राथमिक खांसी सरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (30, 'Primary exercise headache', 'प्राथमिक व्यायाम सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (31, 'Primary sexual headache', 'प्राथमिक यौन सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (32, 'Headache attributed to external application of cold stimulus', '' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (33, 'Headache attributed to ingestion or inhalation of a cold stimulus', '' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (34, 'External compression headache', 'बाहरी संपीड़न सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (35, 'External traction headache', 'बाहरी कर्षण सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (36, 'Hypnic headache', 'हाइपनिक सिरदर्द' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (37, 'Nummular headache', 'नम्बरवार सिरदर्द' )");

        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (38, 'Chronic Migraine with medication overuse', 'क्रोनिक माइग्रेन दवा के अति प्रयोग के साथ' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (39, 'Chronic Tension Type with medication overuse', 'क्रोनिक तनाव दवा के अति प्रयोग के साथ' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (40, 'Chronic hemicrania continua with medication overuse', 'क्रोनिक हेमिक्रानिया कॉन्टुआ दवा के अति प्रयोग के साथ' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (41, 'Chronic Cluster Headache with medication overuse', 'क्रोनिक क्लस्टर सिरदर्द दवा के अति प्रयोग के साथ' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (42, 'Chronic Paroxysmal hemicrania with medication overuse', 'क्रोनिक पैरोक्सिमल हेमिक्रानिया दवा के अति प्रयोग के साथ' )");
        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (43, 'Chronic SUNA with medication overuse', 'क्रोनिक सूना दवा के अति प्रयोग के साथ' )");

        db.execSQL("insert into headache_cat_info(headache_cat, cat_str_en, cat_str_hi) values (44, 'Primary thunderclap headache', 'प्राथमिक वज्रपात सिरदर्द' )");



        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(1, 1, 2, 3, 0, 'Do you ever have headache?', 'क्या आपको कभी सिरदर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(1, 2, 4, 5, 1,'Do you have intermittent and recurrent headaches?','क्या आपको रुक रुक कर और बार-बार सिरदर्द हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(1, 4, 6, 7, 2, 'Is the frequency of your headache is <= 14 days in a month?','क्या आपको सिरदर्द की आवृत्ति महीने में 14 दिन या कम है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(1, 5, 8, 9, 2, 'Do you have unremitting headaches?','क्या आपको निरंतर/लगातार सिरदर्द होता हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(1, 8, 10, 11, 5, 'Do you have headache >= 15 days in a month for more than 3 months?',' क्या आपको ३ महीने से ज्यादा महीने में 15 दिन या अधिक सिरदर्द होता है?', '')");

        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(1, 3, 0)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(1, 6, 1)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(1, 7, 0)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(1, 9, 0)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(1, 10, 2)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(1, 11, 0)");


        //level-2
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 1, 2, 3, null, 'Does the episode/episodes occurred for the first time in life?', 'क्या यह आपके जीवन का पहला सिरदर्द का दौरा है? ', 'In some persons, headache might be working for long time, for others, headache has occurred only recently prior to this consultation. We want to clarify that.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 2, 4 , 5, 1, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 4, 4.1 , 4.2, 2, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 3, 7, 11, 1, 'Was it the worst headache of your lifetime?', 'क्या यह आपके जीवनकाल का सबसे बुरा सिरदर्द था? ', 'Some persons have sudden onset severe headache which they term that the worst headache of lifetime. This signifies a significant secondary pathology.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 6, 8 , 9, 3,'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 8, 8.1 , 8.2, 6, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 7, 10, 11, 3, 'Does the headache progressed or worsened over a period of time since its onset?', ' क्या सिरदर्द शुरू होने के बाद से कुछ समय के लिए बढ़ता या बिगड़ता है?', 'If the frequency and severity of headache progresses or worsens over time, it may signify presence of intracranial pathology.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 7, 61, 11, 3, 'Does the headache is sudden in onset and reaches maximum intensity in less than 1 minute?', 'क्या सिरदर्द अचानक शुरू होता है और 1 मिनट से भी कम समय में अधिकतम तीव्रता तक पहुंच जाता है?', 'If the frequency and severity of headache progresses or worsens over time, it may signify presence of intracranial pathology.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 10, 12 , 13, 7, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 12, 12.1 , 12.2, 10, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 11, 14, 15, 7, 'Did your headache start after age of 50 years?', 'क्या आपका सिरदर्द 50 साल की उम्र के बाद शुरू हुआ था?',  'If headache has started only after only age of 50 years, possibility of secondary headache is high and patient needs investigations.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 14, 16 , 17, 11, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 16, 16.1 , 16.2, 14, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 15, 18, 19, 11, 'Do you have vomiting before start of headache?', 'क्या आपको सिरदर्द शुरू होने से पहले उल्टी होती है?',  'Usually vomiting occurs during the peak of headache in most of the patients however if it is occurring prior to onset of headache, secondary causes must be ruled out.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 18, 20 , 21, 15, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 20, 20.1 , 20.2, 18, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 19, 22, 23, 15, 'Do you have headache only during night or it awakes you or you get headache upon getting up on morning?', 'क्या आपको केवल रात के समय ही सिरदर्द होता है या यह आपको जगाता है या आपको सुबह उठने पर सिरदर्द होता है?',  'Consistent nocturnal and early morning headache may suggest raised intracranial hypertension.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 22, 24 , 25, 19, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 24, 24.1 , 24.2, 22, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 23, 26, 27, 19, 'Does your headache get precipitated by cough/sneezing/straining/sexual activity/postural change?', 'क्या खांसी / छींक / तनाव / यौन क्रिया / पोस्टुरल परिवर्तन से आपके सिर में दर्द होता है?',  '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 26, 28 , 29, 23, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 28, 28.1 , 28.2, 26, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 27, 30, 31, 23, 'Have you noted any recent change in the character of headache from previous headache?', 'क्या आपने पिछले सिरदर्द से सिरदर्द के प्रकार में कोई हालिया परिवर्तन देखा है?',  'Character of headache means throbbing, band like or pulsatile in sensation. It may change with time.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 30, 32 , 33, 27,'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 32, 32.1 , 32.2, 30, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 31, 34, 35, 27, 'Does your headache side locked and have redness, tearing, nasal stuffiness or watering from nose from same side of headache?', 'क्या आपका सिरदर्द सर के हमेशा एक ही तरफ होता है और उसी तरफ आंख लाल होना, नाक में बंद लगना, नाक का बहना या फिर पानी आना होता है?',  'Side locked headache means that headache happens only in the same side of the head.Sidelocked headache and ipsilateral cranial autonomic symptoms which suggests trigeminal autonomic cephalgias. All trigeminal autonomic cephalgias must be investigated for secondary cause.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 34, 36 , 37, 31, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 36, 36.1 , 36.2, 34, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 35, 38, 39, 31, 'Does the headache better upon assuming supine position or worsen when sitting or standing up?', 'क्या बैठने या खड़े होने पर सिरदर्द की स्थिति बेहतर हो जाती है या बिगड़ जाती है?',  'Specific postural triggers may indicate intracranial hypotension.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 38, 40 , 41, 35, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 40, 40.1 , 40.2, 38, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 39, 42, 43, 35, 'Does your vision becomes foggy or blurred for short duration of time with or without headache?', 'क्या आपकी दृष्टि सिरदर्द के साथ या बिना कम समय के लिए धुंधली हो जाती है?',  'Transient visual obscurations might indicate presence intracranial hypertension.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 42, 44 , 45, 39, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 44, 44.1 , 44.2, 42, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 43, 46, 47, 39, 'Do you have ringing sensations in your ears with or without headaches?', 'क्या आपको सरदर्द या सरदर्द के बिना, बिना वजह कानों में\n" +
                "बजने या फिर गंूजने की आवाज आती है?',  'Presence of tinnitus may indicate increased intracranial pressure.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 46, 48 , 49, 43, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 48, 48.1 , 48.2, 46, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 47, 50, 51, 43, 'Have you gained significant weight in last 6 months to 1 year?', 'क्या आपने पिछले 6 महीनों से 1 वर्ष में वजन में महत्वपूर्ण वृद्धि हुई है?',  'Significant weight gain is a risk factor for idiopathic intracranial hypertension and chronification of migraine.')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 50, 52 , 53, 47, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 52, 52.1 , 52.2, 50, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 51, 54, 55, 47, 'Are you having fever or any other medical illnesses with the onset of headache?', 'क्या आपको सिरदर्द की शुरुआत के साथ बुखार या कोई अन्य बीमारी हुई है?',  '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 54, 56 , 57, 51, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 56, 56.1 , 56.2, 54, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 55, 58, 59, 51, 'Have you ever been diagnosed with cancer or any disease that lowers body''s immunity?', 'क्या आपको कभी कैंसर या किसी ऐसी बीमारी का पता चला है जो शरीर की प्रतिरोधक क्षमता को कम करती है?',  '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 58, 60 , 61, 55, 'Does the patient have any following abnormal clinical findings?\n" +
                "1.Blood Pressure 2.Fundus 3.Visual field 4.Cranial nerve deficit 5.Motor deficit 6.Sensory deficit 7.Ataxia 8.Neck rigidity 9.Plantars',' क्या रोगी को कोई असामान्य नैदानिक निष्कर्ष है?\n" +
                "1.रक्तचाप 2.आँख का फंडस 3.विसुअल फील्ड 4.क्रेनियल नर्व में कमीं 5.मोटर परिक्षण में कमीं 6.सेंसरी परिक्षण में कमीं\n" +
                "7.अटैक्सिया 8.गर्दन की कठोरता 9.प्लांटर रिफ्लेक्स', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 60, 60.1 , 60.2, 58, 'Is the patient carrying any investigations which are abnormal and which can be correlated with headache?',' क्या रोगी की कोई ऐसी जांच है जो असामान्य है और जिसे सिरदर्द के साथ जोड़ा जा सकता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 61, 62 , 11, 7, 'Does the headache lasts more than 5 minutes?', 'क्या सिरदर्द 5 मिनट से अधिक रहता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(2, 62, 62.1 , 62.2, 61, 'Are the patient''s clinical examination and investigations normal?',' क्या मरीज की नैदानिक परीक्षण और जांच सामान्य है?', '')");


        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 4.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 4.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 5, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 8.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 8.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 9, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 12.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 12.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 13, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 16.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 16.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 17, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 20.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 20.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 21, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 24.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 24.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 25, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 28.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 28.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 29, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 32.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 32.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 33, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 36.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 36.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 37, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 40.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 40.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 41, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 44.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 44.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 45, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 48.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 48.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 49, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 52.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 52.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 53, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 56.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 56.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 57, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 59, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 60.1, 4)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 60.2, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 61, 3)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 62.1, 44)");
        db.execSQL("insert into answer_info(lvl, ans_id, headache_cat) values(2, 62.2, 4)");


        //Level-3.1(Episodic)
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 1, 0 , 51, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 1, 1 , 2, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 2, 0 , 20, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 2, 1 , 3, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 3, 0 , 6, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 3, 1 , 6, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 3, 2 , 4, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 3, 3 , 4, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 3, 4 , 4, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 3, 5 , 4, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 3, 6 , 4, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 4, 0 , null, 7)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 4, 1 , 5, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 4, 2 , 5, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 4, 3 , 5, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 5, 0 , null, 7)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 5, 1 , null, 6)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 6, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 6, 1 , 8, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 6, 2 , 7, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 6, 3 , 7, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 7, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 7, 1 , null, 15)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 8, 0 , 9, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 8, 1 , null, 5)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 9, 0 , 10, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 9, 1 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 9, 2 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 10, 0 , 11, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 10, 1 , 11, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 10, 2 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 11, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 11, 1 , null, 15)");

        //TTH Q
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 20, 0 , 30, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 20, 1 , 22, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 20, 2 , 21, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 20, 3 , 21, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 21, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 21, 1 , null, 15)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 22, 0 , 23, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 22, 1 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 23, 0 , 24, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 23, 1 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 23, 2 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 24, 0 , 25, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 24, 1 , 25, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 24, 2 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 25, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 25, 1 , null, 15)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 30, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 30, 1 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 30, 2 , 31, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 30, 3 , 31, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 30, 4 , 31, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 30, 5 , 31, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 30, 6 , 31, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 31, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 31, 1 , null, 19)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 31, 2 , null, 19)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 31, 3 , null, 19)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 51, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 51, 1 , 52, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 52, 0 , 58, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 52, 1 , 53, null)");

//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 51, 2 , 53, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 53, 0 ,  58, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 53, 1 , 54, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 53, 2 , 54, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 53, 3 , 54, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 53, 4 , 54, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 53, 5 , 54, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 53, 6 , 54, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 53, 7 , 54, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 54, 0 ,  55, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 54, 1 , 55, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 54, 2 , 55, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 54, 3 , null, 16)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 55, 0 ,  56, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 55, 1 , 56, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 55, 2 , 56, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 55, 3 , 56, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 55, 4 , null, 17)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 56, 0 ,  76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 56, 1 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 56, 2 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 56, 3 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 56, 4 , null, 18)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 58, 0 ,  76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 58, 1 , 60, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 60, 0 ,  76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 60, 1 , 62, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 60, 2 , 61, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 60, 3 , 61, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 61, 0 ,  76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 61, 1 , null, 15)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 62, 0 ,  63, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 62, 1 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 63, 0 ,  64, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 63, 1 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 63, 2 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 64, 0 ,  65, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 64, 1 , 65, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 64, 2 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 65, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 65, 1 , null, 15)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 70, 0 ,  76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 70, 1 , 72, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 70, 2 , 71, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 70, 3 , 71, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 71, 0 ,  76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 71, 1 , null, 15)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 72, 0 ,  73, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 72, 1 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 73, 0 ,  74, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 73, 1 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 73, 2 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 74, 0 ,  75, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 74, 1 , 75, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 74, 2 , 76, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 75, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 75, 1 , null, 15)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 76, 0 , 80, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 76, 1 , 77, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 77, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 77, 1 , 78, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 78, 0 , 79, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 78, 1 , null, 27)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 79, 0 , null, 28)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 79, 1 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 79, 2 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 79, 3 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 79, 4 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 79, 5 , null, -1)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 79, 6 , null, -1)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 80, 0 , 82, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 80, 1 , 81, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 81, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 81, 1 , null, 29)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 82, 0 , 84, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 82, 1 , 83, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 83, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 83, 1 , null, 30)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 84, 0 , 87, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 84, 1 , 85, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 85, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 85, 1 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 85, 2 , 86, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 86, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 86, 1 , null, 31)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 87, 0 , 89, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 87, 1 , 88, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 88, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 88, 1 , null, 32)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 89, 0 , 91, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 89, 1 , 90, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 90, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 90, 1 , null, 33)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 91, 0 , 94, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 91, 1 , 92, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 92, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 92, 1 , 93, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 93, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 93, 1 , null, 34)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 94, 0 , 97, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 94, 1 , 95, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 95, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 95, 1 , 96, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 96, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 96, 1 , null, 35)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 97, 0 , 100, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 97, 1 , 98, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 98, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 98, 1 , 99, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 99, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 99, 1 , null, 36)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 100, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 100, 1 , 101, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 101, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 101, 1 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 101, 2 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.1, 101, 3 ,  null, 37)");


        //Level 3.1(Episodic)
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 1, 0, 0, null, 'Do you have a headache which lasts longer than 4 hours, if you do not treat it with acute medications?', 'क्या आपका सिरदर्द 4 घंटे से अधिक समय तक रहता है, यदि आप इसे तीव्र दवाओं के साथ इलाज नहीं करते हैं?', '')");

        //right sub-tree
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 2, 0, 0, 1, 'Does your headache last 4 hours to 72 hours?','क्या आपका सिरदर्द 4 घंटे से 72 घंटे तक रहता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 3.1, 0, 0, 2, 'Do you have headaches on one side of the head only?','क्या आपको केवल सिर के एक तरफ सिरदर्द है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 3.2, 0, 0, 2, 'Do you have throbbing/pulsating headaches?','क्या आपको धड़क / धड़कने जैसा सिरदर्द है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 3.3, 0, 0, 2, 'Do you have headaches which either restricts or prevent all of your activities?', 'क्या आपका सिरदर्द आपकी सभी गतिविधियों को प्रतिबंधित या रोकता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 3.4, 0, 0, 2, 'Does the headache get aggravated if you continue to work or do some physical activity?', 'यदि आप लगातार काम करते हैं या कुछ शारीरिक गतिविधि करते हैं तो क्या सिरदर्द बढ़ जाता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 3.5, 0, 0, 2, 'Do you avoid daily activities like walking and climbing stairs during headache?', 'क्या आप सिरदर्द के दौरान सीढ़ियों पर चलना और चढ़ना जैसी दैनिक गतिविधियों से बचते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 3.6, 0, 0, 2, 'Does the headache have moderate-severe intensity?','क्या सिरदर्द में मध्यम-गंभीर तीव्रता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 4.1, 0, 0, 3, 'Do you have nausea during headaches?','क्या आपको सिरदर्द के दौरान मतली होती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 4.2, 0, 0, 3, 'Do you have vomiting during headaches?', 'क्या आपको सिरदर्द के दौरान उल्टी होती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 4.3, 0, 0, 3, 'Does the normal light at home and normal sound make you uncomfortable during the headache?', 'क्या घर पर सामान्य रोशनी और सामान्य आवाज़ आपको सिरदर्द के दौरान असहज बनाती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 5, 0, 0, 4, 'Do you have five such attacks till today from the start of the headache?', 'क्या आपको सिरदर्द शुरू होने से लेकर आज तक पांच ऐसे हमले हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 6.1, 0, 0, 3, 'Do you have a headache on both sides of your head?', 'क्या आप को सिर में दोनों तरफ दर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 6.2, 0, 0, 3, 'Does the headache feel like pressing or tightening sensations over the head?', 'क्या आपको सरदर्द के दौरान ऐसा महसूस होता है कि आपके सिर में भारीपन या फिर कुछ बांध रखा हो?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 6.3, 0, 0, 3, 'Does the headache have mild to moderate intensity?', 'क्या आपको हलका या मध्य तीव्रता का होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 7, 0, 0, 6, ' Do you have ten such attacks since the start of headache?', 'सिरदर्द शुरू होने के बाद से क्या आप को अब तक 10 ऐसे सिरदर्द के दौरे पड़ चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 8, 0, 0, 6, 'Does your headache get aggravated by routine physical activity such as walking or climbing stairs?', 'क्या आपका सरदर्द सामान्य शारीरिक गति विधियों जैसे कि चलना या फिर सीढ़ियां चढ़ना के दौरान बढ़ जाती हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 9.1, 0, 0, 8, 'Do you have nausea associated with the headache?', 'क्या आपको सिरदर्द के दौरान कई आती है या फिर उल्टी का मन करता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 9.2, 0, 0, 8, 'Do you have vomiting associated with the headache?', 'क्या आपको सरदर्द के दौरान उल्टी आती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 10.1, 0, 0, 9, 'Does the normal light at home make you uncomfortable during the headache?', 'क्या आपको सिरदर्द के दौरान घर पर मौजूद सामान्य रोशनी सेसामान्य रोशनी से असहजता महसूस होती है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 10.2, 0, 0, 9, 'Does the normal sound at home make you uncomfortable during the headache?', 'क्या आपको सिरदर्द के दौरान घर पर मौजूद सामान्य आवाज से असहजता महसूस होती है ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 11, 0, 0, 10, ' Do you have ten such attacks since the start of headache?', 'क्या आपको सिरदर्द शुरू होने से अब तक, इस प्रकार के 10 सरदर्द के दौरे हो चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 20.1, 0, 0, 2, 'Do you have a headache on both sides of your head?', 'क्या आपको सर की दोनों तरफ दर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 20.2, 0, 0, 2, 'Does the headache feel like pressing or tightening sensations over the head?', 'क्या आपको सरदर्द के दौरान ऐसा महसूस होता है कि आपके सिर में भारीपन या फिर कुछ बांध रखा हो?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 20.3, 0, 0, 2, 'Does the headache have mild to moderate intensity?', 'क्या आपको हलका या मध्य तीव्रता का होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 21, 0, 0, 20, ' Do you have ten such attacks since the start of headache?', 'क्या आपको सिरदर्द शुरू होने से अब तक, इस प्रकार के 10 सरदर्द के दौरे हो चुके हैं ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 22, 0, 0, 20, 'Does your headache get aggravated by routine physical activity such as walking or climbing stairs?', 'क्या आपका सरदर्द सामान्य शारीरिक गति विधियों जैसे कि चलना या फिर सीढ़ियां चढ़ना के दौरान बढ़ जाती हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 23.1, 0, 0, 22, 'Do you have nausea associated with the headache?', 'क्या आपको सिरदर्द के दौरान कई आती है या फिर उल्टी का मन करता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 23.2, 0, 0, 22, 'Do you have vomiting associated with the headache?', 'क्या आपको सरदर्द के दौरान उल्टी आती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 24.1, 0, 0, 23, 'Does the normal light at home make you uncomfortable during the headache?', 'क्या आपको सिरदर्द के दौरान घर पर मौजूद सामान्य रोशनी सेसामान्य रोशनी से असहजता महसूस होती है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 24.2, 0, 0, 23, 'Does the normal sound at home make you uncomfortable during the headache?', 'क्या आपको सिरदर्द के दौरान घर पर मौजूद सामान्य आवाज से असहजता महसूस होती है ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 25, 0, 0, 24, ' Do you have ten such attacks since the start of headache?', 'क्या आपको सिरदर्द शुरू होने से अब तक, इस प्रकार के 10 सरदर्द के दौरे हो चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 30.1, 0, 0, 20, 'Do you have headaches on one side of the head only?','क्या आपको केवल सिर के एक तरफ सिरदर्द है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 30.2, 0, 0, 20, 'Do you have throbbing/pulsating headaches?','क्या आपको धड़क / धड़कने जैसा सिरदर्द है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 30.3, 0, 0, 20, 'Do you have headaches which either restricts or prevent all of your activities?', 'क्या आपका सिरदर्द आपकी सभी गतिविधियों को प्रतिबंधित या रोकता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 30.4, 0, 0, 20, 'Does the headache get aggravated if you continue to work or do some physical activity?', 'यदि आप लगातार काम करते हैं या कुछ शारीरिक गतिविधि करते हैं तो क्या सिरदर्द बढ़ जाता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 30.5, 0, 0, 20, 'Do you avoid daily activities like walking and climbing stairs during headache?', 'क्या आप सिरदर्द के दौरान सीढ़ियों पर चलना और चढ़ना जैसी दैनिक गतिविधियों से बचते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 30.6, 0, 0, 20, 'Does the headache have moderate-severe intensity?','क्या सिरदर्द में मध्यम-गंभीर तीव्रता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 31.1, 0, 0, 30, 'Do you have nausea during headaches?','क्या आपको सिर दर्द के दौरान कई आती है या फिर उल्टी का मन करता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 31.2, 0, 0, 30, 'Do you have vomiting during headaches?', 'क्या आपको सिरदर्द के दौरान उल्टी आती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 31.3, 0, 0, 30, 'Does the normal light at home and normal sound make you uncomfortable during the headache?', 'क्या घर पर सामान्य रोशनी और सामान्य आवाज़ आपको सिरदर्द के दौरान असहज बनाती है?', '')");

        //left sub-tree
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 51, 0, 0, 1, 'Does your headache lasts less than 4 hours?','क्या आपको सिर दर्द 4 घंटे से कम होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 52, 0, 0, 1, 'Do you have one sided severe to very severe headaches involving regions around and above the orbit with or without involving the temporal area of the head or the face?','क्या आपको सर के एक तरफ आंखों के आसपास, कनपटी की जगह के साथ या फिर उसके बिना तीव्र या फिर अति तीव्र सिर दर्द होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 53.1, 0, 0, 51, 'Do you get eye redness on the same side of headache during the headache?','क्या आपको सिर की जिस तरफ दर्द हो रहा है, उसी तरह की आंख में लाली आती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 53.2, 0, 0, 51, 'Do you get increased lacrimation on the same side of headache during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की आंख से आंसू आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 53.3, 0, 0, 51, 'Do you have nasal watering with or without watering from your nose on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरफ की नाक से पानी आता है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 53.4, 0, 0, 51, 'Do you swelling over the eye lid on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक में सूजन महसूस होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 53.5, 0, 0, 51, 'Do you get forehead or facial sweating on the same side of the head  during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह के माथे या फिर चेहरे से पसीने आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 53.6, 0, 0, 51, 'Do you have drooping of eyelids or reduction in pupil size on the same side of headache  during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक झुकी हुई या फिर पुतली की आंख छोटी लगती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 53.7, 0, 0, 51, 'Do you have a sense of restlessness or you get agitated during the episode of headache?','क्या आपको सर दर्द के दौरान बेचैनी महसूस होती है या फिर आप उत्तेजित हो जाते हैं ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 54.1, 0, 0, 53, 'Does the headache last 15-180 minutes if you do not take medicines?','क्या आपको बिना दवाई लिए सिरदर्द 15 से 180 मिनट तक होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 54.2, 0, 0, 53, 'Do you have a headache every other day for a maximum of 8 episodes in a day?','क्या आप को सिरदर्द के इस प्रकार के एक दिन में अधिकतम 8 दौरे हो जाते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 54.3, 0, 0, 53, 'Do you have five such attacks since the start of headache?','क्या आपको सर दर्द शुरू होने से लेकर अब तक इस प्रकार के पांच दौरे पड़ चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 55.1, 0, 0, 54, 'Does the headache last 2-30 minutes if you do not take medicines?','क्या आपको बिना दवाई लिए सिरदर्द 2-30 मिनट तक होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 55.2, 0, 0, 54, 'Do you have more than 5 episodes of such headache in a day?','क्या आप को सिरदर्द के इस प्रकार के एक दिन में 5 या 5 से ज्यादा दौरे हो जाते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 55.3, 0, 0, 54, 'Have you ever used tablet Indomethacin and did the headache got relieved on taking it?','क्या आपने कभी इंडोमिथेसिन टेबलेट लिए है और उससे क्या आपका सर दर्द ठीक हो गया है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 55.4, 0, 0, 54, 'Do you have 20 such attacks since the start of such headaches?','क्या आपको सर दर्द शुरू होने से लेकर अब तक इस प्रकार के 20 दौरे पड़ चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 56.1, 0, 0, 55, 'Do you have headache stabbing in character, happening in isolation, or in series or in saw tooth pattern?','क्या आपको सर पर एक या फिर बार-बार छूरी मारने जैसा या फिर आरादंती जैसा दर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 56.2, 0, 0, 55, 'Does the headache last 1 sec to 10 minutes if you do not take medicines?','क्या आपको बिना दवाई लिए सिर दर्द 1 सेकंड से 10 मिनट तक होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 56.3, 0, 0, 55, 'Do you have at least one such episode of headache per day?','क्या आपको दिन में न्यूनतम एक बार इस प्रकार का सर दर्द का दौरा होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 56.4, 0, 0, 55, 'Do you have 20 such attacks since the start of such headaches?','क्या आपको सर दर्द शुरू होने से लेकर अब तक इस प्रकार के 20 दौरे पड़ चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 58, 0, 0, 53, 'Do you have headache lasts 30 minutes- 4 hours?','क्या आपको बिना दवाई लिए सिर दर्द 30 मिनट से 4 घंटे तक होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 60.1, 0, 0, 58, 'Do you have a headache on both sides of your head?', 'क्या आपको सर की दोनों तरफ दर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 60.2, 0, 0, 58, 'Does the headache feel like pressing or tightening sensations over the head?', 'क्या आपको सरदर्द के दौरान ऐसा महसूस होता है कि आपके सिर में भारीपन या फिर कुछ बांध रखा हो?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 60.3, 0, 0, 58, 'Does the headache have mild to moderate intensity?', 'क्या आपको सरदर्द हलका या मध्य तीव्रता का होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 61, 0, 0, 60, ' Do you have ten such attacks since the start of headache?', 'क्या आपको सिर दर्द शुरू होने से अब तक, इस प्रकार के 10 सरदर्द के दौरे हो चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 62, 0, 0, 60, 'Does your headache get aggravated by routine physical activity such as walking or climbing stairs?', 'क्या आपका सरदर्द सामान्य शारीरिक गतिविधियों जैसे कि चलना या फिर सीढ़ियां चढ़ना के दौरान बढ़ जाती हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 63.1, 0, 0, 62, 'Do you have nausea associated with the headache?', 'क्या आपको सिर दर्द के दौरान कई आती है या फिर उल्टी का मन करता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 63.2, 0, 0, 62, 'Do you have vomiting associated with the headache?', 'क्या आपको सर दर्द के दौरान उल्टी आती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 64.1, 0, 0, 63, 'Does the normal light at home make you uncomfortable during the headache?', 'क्या आपको सिर दर्द के दौरान घर पर मौजूद सामान्य रोशनी से सामान्य रोशनी से असहजता महसूस होती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 64.2, 0, 0, 63, 'Does the normal sound at home make you uncomfortable during the headache?', 'क्या आपको सर दर्द के दौरान घर पर मौजूद सामान्य आवाज से असहजता महसूस होती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 65, 0, 0, 64, ' Do you have ten such attacks since the start of headache?', 'क्या आपको सिर दर्द शुरू होने से अब तक, इस प्रकार के 10 सरदर्द के दौरे हो चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 70.1, 0, 0, 51, 'Do you have a headache on both sides of your head?', 'क्या आपको सर की दोनों तरफ दर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 70.2, 0, 0, 51, 'Does the headache feel like pressing or tightening sensations over the head ', 'क्या आपको सरदर्द के दौरान ऐसा महसूस होता है कि आपके सिर में भारीपन या फिर कुछ बांध रखा हो?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 70.3, 0, 0, 51, 'Does the headache have mild to moderate intensity?', 'क्या आपको सरदर्द हलका या मध्य तीव्रता का होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 71, 0, 0, 70, ' Do you have ten such attacks since the start of headache?', 'क्या आपको सिर दर्द शुरू होने से अब तक, इस प्रकार के 10 सरदर्द के दौरे हो चुके हैं', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 72, 0, 0, 70, 'Does your headache get aggravated by routine physical activity such as walking or climbing stairs?', 'क्या आपका सरदर्द सामान्य शारीरिक गतिविधियों जैसे कि चलना या फिर सीढ़ियां चढ़ना के दौरान बढ़ जाती हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 73.1, 0, 0, 72, 'Do you have nausea associated with the headache?', 'क्या आपको सिर दर्द के दौरान कई आती है या फिर उल्टी का मन करता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 73.2, 0, 0, 72, 'Do you have vomiting associated with the headache?', 'क्या आपको सर दर्द के दौरान उल्टी आती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 74.1, 0, 0, 73, 'Does the normal light at home make you uncomfortable during the headache?', 'क्या आपको सिर दर्द के दौरान घर पर मौजूद सामान्य रोशनी से असहजता महसूस होती है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 74.2, 0, 0, 73, 'Does the normal sound at home make you uncomfortable during the headache?', 'क्या आपको सिर दर्द के दौरान घर पर मौजूद सामान्य आवाज से असहजता महसूस होती है ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 75, 0, 0, 74, ' Do you have ten such attacks since the start of headache?', 'क्या आपको सिर दर्द शुरू होने से अब तक, इस प्रकार के 10 सरदर्द के दौरे हो चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 76, 0, 0, 1, 'Do you have stabs of head pain?','क्या आपको छुरा मारने जैसा सिर दर्द होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 77, 0, 0, 76, 'Does each stab last a few seconds?','क्या इस प्रकार का दर्द कुछ सेकंड के लिए ही रहता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 78, 0, 0, 77, 'Do you have headache which spreading from one side of head to other part of head in linear fashion ?','क्या आपका सिर दर्द सिर के एक तरफ से दूसरी तरफ लंबाई में फैलता है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 79.1, 0, 0, 78, 'Do you have head pain occurring spontaneously as a single stab or series of stabs with each stab lasting upto few seconds and recurring stabs with irregular frequency from one to many per day?','', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 79.1, 0, 0, 78, 'Do you get increased lacrimation on the same side of headache during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की आंख से आंसू आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 79.2, 0, 0, 78, 'Do you have nasal watering with or without watering from your nose on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरफ की नाक से पानी आता है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 79.3, 0, 0, 78, 'Do you swelling over the eye lid on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक में सूजन महसूस होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 79.4, 0, 0, 78, 'Do you get forehead or facial sweating on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह के माथे या फिर चेहरे से पसीने आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 79.5, 0, 0, 78, 'Do you have drooping of eyelids?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक झुकी हुई या फिर पुतली की आंख छोटी लगती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 80, 0, 0, 76, 'Do you have headache suddenly only on coughing or straining?','क्या सरदर्द खांसने या फिर जोर लगाने से बढ़ता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 81, 0, 0, 80, 'Does the headache last from one second to two hours?','क्या आपका सर दर्द 1 सेकंड से 2 घंटे तक रहता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 82, 0, 0, 80, 'Do you have headache suddenly only on exercise?','क्या आपका सर दर्द अचानक से कसरत करते दौरान आता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 83, 0, 0, 82, 'Does the headache last less than 48 hours?','क्या आपका सर दर्द 48 घंटे से कम रहता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 84, 0, 0, 82, 'Do you have headache suddenly only during sexual activity?','क्या आपका सर दर्द यौन संबंध के दौरान अचानक से होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 85.1, 0, 0, 84, 'Does your headache increase in intensity with increasing sexual excitement?','क्या यौन उत्तेजना बढ़ने के साथ आपका सिरदर्द तीव्रता में बढ़ जाता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 85.2, 0, 0, 84, 'Does your headache have abrupt explosive intensity just before or with orgasm?','क्या आपके सिरदर्द में ऑर्गेज्म से पहले या बाद में विस्फोटक तीव्रता होती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 86, 0, 0, 85, 'Does your headache last from one minute to 24 hours with severe intensity and/or up to 72 hours with mild intensity?','क्या आपका सिरदर्द गंभीर तीव्रता के साथ एक मिनट से 24 घंटे और / या 72 घंटे तक रहता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 87, 0, 0, 84, 'Do you have headache suddenly only during extremely cold temperatures?','क्या आपको बेहद ठंडे तापमान के दौरान अचानक सिरदर्द होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 88, 0, 0, 87, 'Does your headache resolve within 30 minutes after removal of the cold stimulus?','क्या आपका सर दर्द ठंड के स्टीमुलस के हटाने के 30 मिनट के अंदर की हो जाता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 89, 0, 0, 87, 'Do you have headache suddenly only after ingestion of cold food or drink or inhalation of cold air?','क्या आपका सर दर्द ठंड की उत्तेजना को दूर करने के 30 मिनट के भीतर हल हो जाता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 90, 0, 0, 89, 'Does your headache resolve within 10 minutes after removal of the cold stimulus?','ठंडी की उत्तेजना को दूर करने के बाद क्या आपका सिरदर्द 10 मिनट के भीतर हल हो जाता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 91, 0, 0, 89, 'Do you have headache suddenly only during sustained external compression of the forehead or scalp?','क्या आपको माथे या खोपड़ी के निरंतर बाहरी दबाव के कारण अचानक सिरदर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 92, 0, 0, 91, 'Does your headache maximal at the site of external compression?','क्या आपका सिरदर्द बाहरी संपीड़न/दबाव की जगह पर अधिकतम होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 93, 0, 0, 92, 'Does your headache resolve within one hour after external compression is relieved?','बाहरी संपीड़न/दबाव से राहत मिलने के बाद आपका सिरदर्द एक घंटे के भीतर हल हो जाता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 94, 0, 0, 91, 'Does the headache brought on by and occur only during sustained external traction on the scalp?','क्या सिरदर्द खोपड़ी पर निरंतर बाहरी कर्षण/खिचाव के कारण होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 95, 0, 0, 94, 'Does the headache have maximal intensity at the traction site?','क्या कर्षण/ खिचाव स्थल पर सिरदर्द की अधिकतम तीव्रता होती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 96, 0, 0, 95, 'Does the headache resolve within one hour after traction is relieved ?','क्या कर्षण/खिचाव से राहत मिलने के एक घंटे के भीतर सिरदर्द का समाधान हो जाता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 97, 0, 0, 94, 'Do you have recurring headaches developing only during sleep, and causing wakening ?','क्या आप का दर्द केवल सोने के दौरान होता है और आपको नींद से जगा भी देता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 98, 0, 0, 97, 'Does the headache occur for more than 10 days/month for >3 months?','क्या आपका सर दर्द महीने में 10 दिन से ज्यादा होता है जो कि आपका 3 महीने से या उससे ज्यादा हो रहा है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 99, 0, 0, 98, 'Does your headache last from 15 minutes up to four hours after waking?','क्या आपका सर दर्द 15 मिनट से 4 घंटे तक रहता है जागने के बाद?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 100, 0, 0, 94, 'Do you feel head pain felt only in an area of the scalp?','क्या आपको सिर पर दर्द सिर्फ सर के एक क्षेत्र में महसूस होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 101.1, 0, 0, 100, 'Is that area of pain sharply contoured?','क्या वह क्षेत्र अच्छी तरीके से परिभाषित रूपरेखा वाला होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 101.2, 0, 0, 100, 'Is the area fixed in size and shape?','क्या वह क्षेत्र निश्चित आकार और आकृति का होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.1, 101.3, 0, 0, 100, 'Is the shape of that area round or elliptical measuring 1-6 cm in diameter?','क्या उस क्षेत्र का आकार गोल या अण्डाकार जिसका व्यास1-6 सेमी तक होता है?', '')");



        //Level-3.2(Chronic)
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 1, 0 , 2, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 1, 1 , null, 20)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 2, 0 , 4, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 2, 1 , 3, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 3, 0 , 5, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 3, 1 , 6, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 4, 0 , 30, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 4, 1 , 25, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 4, 2 , 26, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 4, 3 , 26, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 5, 0 , 15, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 5, 1 , 10, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 5, 2 , 9, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 5, 3 , 9, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 6, 0 , 5, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 6, 1 , 5, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 6, 2 , 7, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 6, 3 , 7, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 6, 4 , 7, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 6, 5 , 7, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 6, 6 , 7, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 7, 0 , 5, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 7, 1 , 8, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 7, 2 , 8, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 7, 3 , 8, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 8, 0 , 5, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 8, 1 , null, 21)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 8, 1 , 102, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 9, 0 , 11, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 9, 1 , 15, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 9, 2 , 15, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 10, 0 , 9, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 10, 1 , 15, null)");

//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 11, 0 , null, 22)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 11, 1 , null, 22)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 11, 0 , 103, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 11, 1 , 103, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 11, 2 , 15, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 15, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 15, 1 , 16, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 0 , 76, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 1 , null, 23)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 2 , null, 23)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 3 , null, 23)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 4 , null, 23)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 5 , null, 23)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 6 , null, 23)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 7 , null, 23)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 1 , 104, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 2 , 104, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 3 , 104, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 4 , 104, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 5 , 104, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 6 , 104, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 16, 7 , 104, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 25, 0 , 26, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 25, 1 , 30, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 26, 0 , 28, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 26, 1 , 30, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 26, 2 , 30, null)");

//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 28, 0 , null, 22)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 28, 1 , null, 22)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 28, 0 , 103, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 28, 1 , 103, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 28, 2 , 30, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 30, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 30, 1 , 31, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 31, 0 , 41, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 31, 1 , 38, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 31, 2 , 38, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 31, 3 , 38, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 31, 4 , 38, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 31, 5 , 38, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 31, 6 , 38, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 31, 7 , 38, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 38, 0 , 41, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 38, 1 , 41, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 38, 2 , 41, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 38, 3 , null, 24)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 38, 3 , 105, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 41, 0 , 45, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 41, 1 , 45, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 41, 2 , 45, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 41, 3 , 45, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 41, 4 , null, 25)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 41, 4 , 106, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 45, 0 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 45, 1 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 45, 2 , 76, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 45, 3 , 76, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 45, 4 , null, 26)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 45, 4 , 107, null)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 76, 0 , 80, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 76, 1 , 77, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 77, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 77, 1 , 78, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 78, 0 , 79, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 78, 1 , null, 27)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 79, 0 , null, 28)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 79, 1 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 79, 2 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 79, 3 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 79, 4 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 79, 5 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 79, 6 , null, -1)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 80, 0 , 82, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 80, 1 , 81, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 81, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 81, 1 , null, 29)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 82, 0 , 84, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 82, 1 , 83, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 83, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 83, 1 , null, 30)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 84, 0 , 87, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 84, 1 , 85, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 85, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 85, 1 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 85, 2 , 86, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 86, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 86, 1 , null, 31)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 87, 0 , 89, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 87, 1 , 88, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 88, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 88, 1 , null, 32)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 89, 0 , 91, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 89, 1 , 90, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 90, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 90, 1 , null, 33)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 91, 0 , 94, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 91, 1 , 92, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 92, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 92, 1 , 93, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 93, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 93, 1 , null, 34)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 94, 0 , 97, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 94, 1 , 95, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 95, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 95, 1 , 96, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 96, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 96, 1 , null, 35)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 97, 0 , 100, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 97, 1 , 98, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 98, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 98, 1 , 99, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 99, 0 , null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 99, 1 , null, 36)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 100, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 100, 1 , 101, null)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 101, 0 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 101, 1 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 101, 2 ,  null, -1)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 101, 3 ,  null, 37)");

        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 102, 0 ,  null, 21)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 102, 1 ,  null, 38)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 103, 0 ,  null, 22)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 103, 1 ,  null, 39)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 104, 0 ,  null, 23)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 104, 1 ,  null, 40)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 105, 0 ,  null, 24)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 105, 1 ,  null, 41)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 106, 0 ,  null, 25)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 106, 1 ,  null, 42)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 107, 0 ,  null, 26)");
        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(3.2, 107, 1 ,  null, 43)");


        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 1, 0, 0, null, 'Do you remember the exact date of onset, with pain when it became continuous and unremitting within 24 hours?', 'क्या आपको सही तारीख याद है जब से आप का दर्द 24 घंटे के अंदर निरंतर और लगातार हो गया ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 2, 0, 0, 1, 'Do you have headaches lasting more than 4 hours?','क्या आपका सिरदर्द 4 घंटे से अधिक समय तक रहता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 3, 0, 0, 2, 'Does your headache last 4 hours to 72 hours?','क्या आपका सिरदर्द 4 घंटे से 72 घंटे तक रहता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 4.1, 0, 0, 2, 'Do you have a headache on both sides of your head?','क्या आपको सर की दोनों तरफ दर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 4.2, 0, 0, 2, 'Does the headache feel like pressing or tightening sensations over the head?','क्या आपको सरदर्द के दौरान ऐसा महसूस होता है कि आपके सिर में भारीपन या फिर कुछ बांध रखा हो?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 4.3, 0, 0, 2, 'Does the headache have mild to moderate intensity?', 'क्या आपको सिरदर्द हलका या मध्य तीव्रता का होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 5.1, 0, 0, 3, 'Do you have a headache on both sides of your head?','क्या आपको सर की दोनों तरफ दर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 5.2, 0, 0, 3, 'Does the headache feel like pressing or tightening sensations over the head?','क्या आपको सरदर्द के दौरान ऐसा महसूस होता है कि आपके सिर में भारीपन या फिर कुछ बांध रखा हो?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 5.3, 0, 0, 3, 'Does the headache have mild to moderate intensity?', 'क्या आपको सरदर्द हलका या मध्य तीव्रता का होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 6.1, 0, 0, 3, 'Do you have headaches on one side of the head only?','क्या आपको केवल सिर के एक तरफ सिरदर्द है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 6.2, 0, 0, 3, 'Do you have throbbing/pulsating headaches?','क्या आपको धड़क / धड़कने जैसा सिरदर्द है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 6.3, 0, 0, 3, 'Do you have headaches which either restrict or prevent all of your activities?', 'क्या आपका सिरदर्द आपकी सभी गतिविधियों को प्रतिबंधित या रोकता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 6.4, 0, 0, 3, 'Does the headache get aggravated if you continue to work or do some physical activity?','यदि आप लगातार काम करते हैं या कुछ शारीरिक गतिविधि करते हैं तो क्या सिरदर्द बढ़ जाता है', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 6.5, 0, 0, 3, 'Do you avoid daily activities like walking and climbing stairs during headache?','क्या आप सिरदर्द के दौरान सीढ़ियों पर चलना और चढ़ना जैसी दैनिक गतिविधियों से बचते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 6.6, 0, 0, 3, 'Does the headache have moderate-severe intensity ?', 'क्या सिरदर्द में मध्यम-गंभीर तीव्रता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 7.1, 0, 0, 6, 'Do you have nausea during headaches?','क्या आपको सिर दर्द के दौरान उल्टी का मन करता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 7.2, 0, 0, 6, 'Do you have vomiting during headaches?','क्या आपको सिरदर्द के दौरान उल्टी होती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 7.3, 0, 0, 6, 'Does the normal light at home and normal sound make you uncomfortable during the headache?', 'क्या आपको सर दर्द के दौरान घर पर मौजूद सामान्य रोशनी एवं सामान्य आवाज से असहजता महसूस होती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 8, 0, 0, 7, 'Are you having 8 such attacks per month for more than 3 months?','क्या आपको तीन या उससे अधिक महीनों से प्रति माह आठ या उससे ज्यादा बार दर्द उठ जाता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 9.1, 0, 0, 5, 'Do you have nausea associated with the headache?','क्या आपको सिरदर्द के दौरान उल्टी का मन करता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 9.2, 0, 0, 5, 'Do you have vomiting associated with the headache?','क्या आपको सिरदर्द के दौरान उल्टी होती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 10, 0, 0, 5, 'Does your headache get aggravated by routine physical activity such as walking or climbing stairs?','क्या आपका सरदर्द सामान्य शारीरिक गतिविधियों जैसे कि चलना या फिर सीढ़ियां चढ़ना के दौरान बढ़ जाती हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 11.1, 0, 0, 9, 'Does the normal light at home make you uncomfortable during the headache?','क्या आपको सिर दर्द के दौरान घर पर मौजूद सामान्य रोशनी से असहजता महसूस होती है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 11.2, 0, 0, 9, 'Does the normal sound at home make you uncomfortable during the headache?','क्या आपको सिरदर्द के दौरान घर पर मौजूद सामान्य आवाज से असहजता महसूस होती है ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 15, 0, 0, 10, 'Do you have one sided severe to very severe headaches involving regions around and above the orbit with or without involving the temporal area of the head or the face?','क्या आपको सर के एक तरफ आंखों के आसपास, कनपटी की जगह के साथ या फिर उसके बिना, तीव्र या फिर अति तीव्र सिरदर्द होता है ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 16.1, 0, 0, 3, 'Do you get eye redness on the same side of headache during the headache?','क्या आपको सिर की जिस तरफ दर्द हो रहा है, उसी तरह की आंख में लाली आती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 16.2, 0, 0, 3, 'Do you get increased lacrimation on the same side of headache during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की आंख से आंसू आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 16.3, 0, 0, 3, 'Do you have nasal watering with or without watering from your nose on the same side of the head during the headache?', 'क्या आपको सिर के जिस तरफ दर्द हो रहा है उस ही तरफ की नाक से पानी आता है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 16.4, 0, 0, 3, 'Do you swelling over the eye lid on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक में सूजन महसूस होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 16.5, 0, 0, 3, 'Do you get forehead or facial sweating on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह के माथे या फिर चेहरे से पसीने आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 16.6, 0, 0, 3, 'Do you have drooping of eyelids or reduction in pupil size on the same side of headache during the headache?', 'क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक झुकी हुई या फिर पुतली की आंख छोटी लगती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 16.7, 0, 0, 3, 'Do you have a sense of restlessness or you get agitated during the episode of headache?', 'क्या आपको सर दर्द के दौरान बेचैनी महसूस होती है या फिर आप उत्तेजित हो जाते हैं ?', '')");


        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 25, 0, 0, 4, 'Does your headache get aggravated by routine physical activity such as walking or climbing stairs?','क्या आपका सरदर्द सामान्य शारीरिक गतिविधियों जैसे कि चलना या फिर सीढ़ियां चढ़ना के दौरान बढ़ जाती हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 26.1, 0, 0, 4, 'Do you have nausea associated with the headache?','क्या आपको सिर दर्द के दौरान उल्टी का मन करता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 26.2, 0, 0, 4, 'Do you have vomiting associated with the headache?','क्या आपको सिरदर्द के दौरान उल्टी होती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 28.1, 0, 0, 26, 'Does the normal light at home make you uncomfortable during the headache?','क्या आपको सिर दर्द के दौरान घर पर मौजूद सामान्य रोशनी से असहजता महसूस होती है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 28.2, 0, 0, 26, 'Does the normal sound at home make you uncomfortable during the headache?','क्या आपको सर दर्द के दौरान घर पर मौजूद सामान्य आवाज से असहजता महसूस होती है ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 30, 0, 0, 28, 'Do you have one sided severe to very severe headaches involving regions around and above the orbit with or without involving the temporal area of the head or the face?','क्या आपको सर के एक तरफ आंखों के आसपास, कनपटी की जगह के साथ या फिर उसके बिना, तीव्र या फिर अति तीव्र सिर दर्द होता है ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 31.1, 0, 0, 30, 'Do you get eye redness on the same side of headache during the headache?','क्या आपको सिर की जिस तरफ दर्द हो रहा है, उसी तरह की आंख में लाली आती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 31.2, 0, 0, 30, 'Do you get increased lacrimation on the same side of headache during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की आंख से आंसू आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 31.3, 0, 0, 30, 'Do you have nasal watering with or without watering from your nose on the same side of the head during the headache?', 'क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरफ की नाक से पानी आता है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 31.4, 0, 0, 30, 'Do you swelling over the eye lid on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक में सूजन महसूस होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 31.5, 0, 0, 30, 'Do you get forehead or facial sweating on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह के माथे या फिर चेहरे से पसीने आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 31.6, 0, 0, 30, 'Do you have drooping of eyelids or reduction in pupil size on the same side of headache during the headache?', 'क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक झुकी हुई या फिर पुतली की आंख छोटी लगती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 31.7, 0, 0, 30, 'Do you have a sense of restlessness or you get agitated during the episode of headache?', 'क्या आपको सर दर्द के दौरान बेचैनी महसूस होती है या फिर आप उत्तेजित हो जाते हैं ?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 38.1, 0, 0, 31, 'Does the headache last 15-180 minutes if you do not take medicines?','क्या आपको बिना दवाई लिए सिर दर्द 15 से 180 मिनट तक होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 38.2, 0, 0, 31, 'Do you have a headache every other day for a maximum of 8 episodes in a day?','क्या आप को सिरदर्द के इस प्रकार के एक दिन में अधिकतम 8 दौरे हो जाते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 38.3, 0, 0, 31, 'Do you have five such attacks since the start of headache?', 'क्या आपको सिरदर्द शुरू होने से लेकर अब तक इस प्रकार के पांच दौरे पड़ चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 41.1, 0, 0, 31, 'Does the headache last 2-30 minutes if you do not take medicines?','क्या आपको बिना दवाई लिए सिरदर्द 2-30 मिनट तक होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 41.2, 0, 0, 31, 'Do you have more than 5 episodes of such headache in a day?','क्या आप को सिरदर्द के इस प्रकार के एक दिन में 5 या 5 से ज्यादा दौरे हो जाते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 41.3, 0, 0, 31, 'Have you ever used tablet Indomethacin and did the headache got relieved on taking it?', 'क्या आपने कभी इंडोमिथेसिन टेबलेट लिए है और उससे क्या आपका सर दर्द ठीक हो गया है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 41.4, 0, 0, 31, 'Do you have 20 such attacks since the start of such headaches?','क्या आपको सिरदर्द शुरू होने से लेकर अब तक इस प्रकार के 20 दौरे पड़ चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 45.1, 0, 0, 41, 'Do you have headache stabbing in character, happening in isolation, or in series or in saw tooth pattern?','क्या आपको सिर पर एक या फिर बार-बार छूरी मारने जैसा या फिर आरादंती जैसा दर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 45.2, 0, 0, 41, 'Does the headache last 1 sec to 10 minutes if you do not take medicines?','क्या आपको बिना दवाई लिए सिर दर्द 1 सेकंड से 10 मिनट तक होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 45.3, 0, 0, 41, 'Do you have at least one such episode of headache per day?', 'क्या आपको दिन में न्यूनतम एक बार इस प्रकार का सिरदर्द का दौरा होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 45.4, 0, 0, 41, 'Do you have 20 such attacks since the start of such headaches?','क्या आपको सिरदर्द शुरू होने से लेकर अब तक इस प्रकार के 20 दौरे पड़ चुके हैं?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 76, 0, 0, 1, 'Do you have stabs of head pain?','क्या आपको छुरा मारने जैसा सिर दर्द होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 77, 0, 0, 76, 'Does each stab last a few seconds?','क्या इस प्रकार का दर्द कुछ सेकंड के लिए ही रहता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 78, 0, 0, 77, ' Do you have headache which spreading from one side of head to other part of head in linear fashion ?','क्या आपका सिर दर्द सिर के एक तरफ से दूसरी तरफ लंबाई में फैलता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 79.1, 0, 0, 78, ' Do you have head pain occurring spontaneously as a single stab or series of stabs with each stab lasting upto few seconds and recurring stabs with irregular frequency from one to many per day?','क्या आपको सिरदर्द के दौरान ऐसा महसूस होता है की कोई आपको छुरा कुछ समय के अंतराल में एक बार या बार बार मार रहा है, या फिर कोई बार बार छुरा अनियमित रूप से मार रहा है, और ऐसे दौरे आपको दिन में एक से कई बार हो जाते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 79.2, 0, 0, 78, ' Do you get increased lacrimation on the same side of headache during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की आंख से आंसू आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 79.3, 0, 0, 78, ' Do you have nasal watering with or without watering from your nose on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरफ की नाक से पानी आता है ?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 79.4, 0, 0, 78, ' Do you swelling over the eye lid on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक में सूजन महसूस होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 79.5, 0, 0, 78, ' Do you get forehead or facial sweating on the same side of the head during the headache?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह के माथे या फिर चेहरे से पसीने आते हैं?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 79.6, 0, 0, 78, ' Do you have drooping of eyelids?','क्या आपको सिर के जिस तरफ दर्द हो रहा है उसी तरह की पलक झुकी हुई या फिर पुतली की आंख छोटी लगती है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 80, 0, 0, 76, ' Do you have headache suddenly only on coughing or straining?','क्या सिरदर्द खांसने या फिर जोर लगाने से लगाने से बढ़ता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 81, 0, 0, 80, ' Does the headache last from one second to two hours?','क्या आपका सिरदर्द 1 सेकंड से 2 घंटे तक रहता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 82, 0, 0, 80, ' Do you have headache suddenly only on exercise?','क्या आपका सिर दर्द अचानक से कसरत करते दौरान आता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 83, 0, 0, 82, ' Does the headache last less than 48 hours?','क्या आपका सिरदर्द 48 घंटे से कम रहता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 84, 0, 0, 82, ' Do you have headache suddenly only during sexual activity?','क्या आपका सिरदर्द यौन संबंध के दौरान अचानक से होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 85.1, 0, 0, 84, ' Does your headache increase in intensity with increasing sexual excitement?','क्या यौन उत्तेजना बढ़ने के साथ आपका सिरदर्द तीव्रता में बढ़ जाता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 85.2, 0, 0, 84, ' Does your headache have abrupt explosive intensity just before or with orgasm?','क्या आपके सिरदर्द में ऑर्गेज्म से पहले या बाद में विस्फोटक तीव्रता होती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 86, 0, 0, 85, ' Does your headache last from one minute to 24 hours with severe intensity and/or up to 72 hours with mild intensity?','क्या आपका सिरदर्द गंभीर तीव्रता के साथ एक मिनट से 24 घंटे और / या 72 घंटे तक रहता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 87, 0, 0, 84, ' Do you have headache suddenly only during extremely cold temperatures?','क्या आपको बेहद ठंडे तापमान के दौरान अचानक सिरदर्द होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 88, 0, 0, 87, ' Does your headache resolve within 30 minutes after removal of the cold stimulus?','क्या आपका सर दर्द ठंड के स्टीमुलस के हटाने के 30 मिनट के अंदर की हो जाता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 89, 0, 0, 87, ' Do you have headache suddenly only after ingestion of cold food or drink or inhalation of cold air?','क्या आपका सर दर्द ठंड की उत्तेजना को दूर करने के 30 मिनट के भीतर हल हो जाता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 90, 0, 0, 89, ' Does your headache resolve within 10 minutes after removal of the cold stimulus?','ठंडी की उत्तेजना को दूर करने के बाद क्या आपका सिरदर्द 10 मिनट के भीतर हल हो जाता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 91, 0, 0, 89, ' Do you have headache suddenly only during sustained external compression of the forehead or scalp?','क्या आपको माथे या खोपड़ी के निरंतर बाहरी दबाव के कारण अचानक सिरदर्द होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 92, 0, 0, 91, ' Does your headache maximal at the site of external compression?','क्या आपका सिरदर्द बाहरी संपीड़न/दबाव की जगह पर अधिकतम होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 93, 0, 0, 92, ' Does your headache resolve within one hour after external compression is relieved?','बाहरी संपीड़न/दबाव से राहत मिलने के बाद आपका सिरदर्द एक घंटे के भीतर हल हो जाता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 94, 0, 0, 91, ' Does the headache brought on by and occur only during sustained external traction on the scalp?','क्या सिरदर्द खोपड़ी पर निरंतर बाहरी कर्षण/खिचं ाव के कारण होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 95, 0, 0, 94, ' Does the headache have maximal intensity at the traction site?','क्या कर्षण/ खिचं ाव स्थल पर सिरदर्द की अधिकतम तीव्रता होती है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 96, 0, 0, 95, 'Does the headache resolve within one hour after traction is relieved ?','क्या कर्षण/खिचं ाव से राहत मिलने के एक घंटे के भीतर सिरदर्द का समाधान हो जाता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 97, 0, 0, 94, 'Do you have recurring headaches developing only during sleep, and causing wakening ?','क्या आप का दर्द केवल सोने के दौरान होता है और आपको नींद से जगा भी देता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 98, 0, 0, 97, 'Does the headache occur for more than 10 days/month for >3 months?','क्या आपका सर दर्द महीने में 10 दिन से ज्यादा होता है जो कि आपका 3 महीने से या उससे ज्यादा हो रहा है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 99, 0, 0, 98, 'Does your headache last from 15 minutes up to four hours after waking?','क्या आपका सर दर्द 15 मिनट से 4 घंटे तक रहता है जागने के बाद?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 100, 0, 0, 94, 'Do you feel head pain felt only in an area of the scalp? ','क्या आपको सिर पर दर्द सिर्फ सर के एक क्षेत्र में महसूस होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 101.1, 0, 0, 100, 'Is that area of pain sharply contoured?','क्या वह क्षेत्र अच्छी तरीके से परिभाषित रूपरेखा वाला होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 101.2, 0, 0, 100, 'Is the area fixed in size and shape?','क्या वह क्षेत्र निश्चित आकार और आकृति का होता है?', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 101.3, 0, 0, 100, 'Is the shape of that area round or elliptical measuring 1-6 cm in diameter?','क्या उस क्षेत्र का आकार गोल या अण्डाकार जिसका व्यास 1-6 सेमी तक होता है?', '')");

        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 102, 0, 0, 8, 'Do you take any of the following classes of drugs for the mentioned days per month for more than 3 months?\n" +
                "(a) ergotamine on ≥ 10 days/month\n" +
                "(b) one or more triptans, in any formulation, on ≥ 10 days/month \n" +
                "(c) paracetamol on ≥ 15 days/ month \n" +
                "(d) one or more non-steroidal anti- inflammatory drugs (NSAIDs) (other than acetylsalicylic acid) on ≥15 days/month\n" +
                "(e) one or more opioids on ≥10 days/month\n" +
                "(f) one or more combination- analgesic medications on ≥10 days/month\n" +
                "(g) any combination of ergotamine, triptans, non-opioid analgesics and/or opioids on a total of ≥10 days/month without overuse of any single drug or drug class alone\n" +
                "(h) one or more medications other than those described above,1 taken for acute or symp- tomatic treatment of headache ≥10 days/months\n','क्या आप 3 महीने से अधिक के लिए प्रति माह उल्लिखित दिनों के लिए दवाओं के किसी भी वर्ग को लेते हैं?\n" +
                "1. ergotamine ≥ 10 दिन / महीने\n" +
                "2. एक या अधिक ट्रिप्टन, किसी भी सूत्रीकरण में, ≥ 10 दिन / महीने\n" +
                "3. पेरासिटामोल ≥ 15 दिन / महीने\n" +
                "4. एक या अधिक NSAID (एनएसएआईडी) (एसि टाइलसैलि सिलिक एसिड के अलावा) ≥ 15 दिन / महीने\n" +
                "5. एक या एक से ज्यादा opioids on ≥10 दिन / महीने\n" +
                "6. एक या एक से ज्यादा संयोजन- एनाल्जेसिक दवाएं ≥ 10 दिन / महीने\n" +
                "7. ergotamine, triptans, non-opioid analgesics and/or opioids का संयोजन ≥10 दिन / महीने\n" +
                "8. ऊपर वर्णित एक या एक से अधिक दवाएँ,सर दर्द के तीव्र या रोगसूचक उपचार के लिए ≥ 10 दिन / महीने', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 103, 0, 0, 11, 'Do you take any of the following classes of drugs for the mentioned days per month for more than 3 months?\n" +
                "(a) ergotamine on ≥ 10 days/month\n" +
                "(b) one or more triptans, in any formulation, on ≥ 10 days/month \n" +
                "(c) paracetamol on ≥ 15 days/ month \n" +
                "(d) one or more non-steroidal anti- inflammatory drugs (NSAIDs) (other than acetylsalicylic acid) on ≥15 days/month\n" +
                "(e) one or more opioids on ≥10 days/month\n" +
                "(f) one or more combination- analgesic medications on ≥10 days/month\n" +
                "(g) any combination of ergotamine, triptans, non-opioid analgesics and/or opioids on a total of ≥10 days/month without overuse of any single drug or drug class alone\n" +
                "(h) one or more medications other than those described above,1 taken for acute or symp- tomatic treatment of headache ≥10 days/months\n','क्या आप 3 महीने से अधिक के लिए प्रति माह उल्लिखित दिनों के लिए दवाओं के किसी भी वर्ग को लेते हैं?\\n\" +\n" +
                "                \"1. ergotamine ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"2. एक या अधिक ट्रिप्टन, किसी भी सूत्रीकरण में, ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"3. पेरासिटामोल ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"4. एक या अधिक NSAID (एनएसएआईडी) (एसि टाइलसैलि सिलिक एसिड के अलावा) ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"5. एक या एक से ज्यादा opioids on ≥10 दिन / महीने\\n\" +\n" +
                "                \"6. एक या एक से ज्यादा संयोजन- एनाल्जेसिक दवाएं ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"7. ergotamine, triptans, non-opioid analgesics and/or opioids का संयोजन ≥10 दिन / महीने\\n\" +\n" +
                "                \"8. ऊपर वर्णित एक या एक से अधिक दवाएँ,सर दर्द के तीव्र या रोगसूचक उपचार के लिए ≥ 10 दिन / महीने', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 104, 0, 0, 16, 'Do you take any of the following classes of drugs for the mentioned days per month for more than 3 months?\n" +
                "(a) ergotamine on ≥ 10 days/month\n" +
                "(b) one or more triptans, in any formulation, on ≥ 10 days/month \n" +
                "(c) paracetamol on ≥ 15 days/ month \n" +
                "(d) one or more non-steroidal anti- inflammatory drugs (NSAIDs) (other than acetylsalicylic acid) on ≥15 days/month\n" +
                "(e) one or more opioids on ≥10 days/month\n" +
                "(f) one or more combination- analgesic medications on ≥10 days/month\n" +
                "(g) any combination of ergotamine, triptans, non-opioid analgesics and/or opioids on a total of ≥10 days/month without overuse of any single drug or drug class alone\n" +
                "(h) one or more medications other than those described above,1 taken for acute or symp- tomatic treatment of headache ≥10 days/months\n','क्या आप 3 महीने से अधिक के लिए प्रति माह उल्लिखित दिनों के लिए दवाओं के किसी भी वर्ग को लेते हैं?\\n\" +\n" +
                "                \"1. ergotamine ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"2. एक या अधिक ट्रिप्टन, किसी भी सूत्रीकरण में, ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"3. पेरासिटामोल ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"4. एक या अधिक NSAID (एनएसएआईडी) (एसि टाइलसैलि सिलिक एसिड के अलावा) ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"5. एक या एक से ज्यादा opioids on ≥10 दिन / महीने\\n\" +\n" +
                "                \"6. एक या एक से ज्यादा संयोजन- एनाल्जेसिक दवाएं ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"7. ergotamine, triptans, non-opioid analgesics and/or opioids का संयोजन ≥10 दिन / महीने\\n\" +\n" +
                "                \"8. ऊपर वर्णित एक या एक से अधिक दवाएँ,सर दर्द के तीव्र या रोगसूचक उपचार के लिए ≥ 10 दिन / महीने', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 105, 0, 0, 38, 'Do you take any of the following classes of drugs for the mentioned days per month for more than 3 months?\n" +
                "(a) ergotamine on ≥ 10 days/month\n" +
                "(b) one or more triptans, in any formulation, on ≥ 10 days/month \n" +
                "(c) paracetamol on ≥ 15 days/ month \n" +
                "(d) one or more non-steroidal anti- inflammatory drugs (NSAIDs) (other than acetylsalicylic acid) on ≥15 days/month\n" +
                "(e) one or more opioids on ≥10 days/month\n" +
                "(f) one or more combination- analgesic medications on ≥10 days/month\n" +
                "(g) any combination of ergotamine, triptans, non-opioid analgesics and/or opioids on a total of ≥10 days/month without overuse of any single drug or drug class alone\n" +
                "(h) one or more medications other than those described above,1 taken for acute or symp- tomatic treatment of headache ≥10 days/months\n','क्या आप 3 महीने से अधिक के लिए प्रति माह उल्लिखित दिनों के लिए दवाओं के किसी भी वर्ग को लेते हैं?\\n\" +\n" +
                "                \"1. ergotamine ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"2. एक या अधिक ट्रिप्टन, किसी भी सूत्रीकरण में, ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"3. पेरासिटामोल ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"4. एक या अधिक NSAID (एनएसएआईडी) (एसि टाइलसैलि सिलिक एसिड के अलावा) ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"5. एक या एक से ज्यादा opioids on ≥10 दिन / महीने\\n\" +\n" +
                "                \"6. एक या एक से ज्यादा संयोजन- एनाल्जेसिक दवाएं ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"7. ergotamine, triptans, non-opioid analgesics and/or opioids का संयोजन ≥10 दिन / महीने\\n\" +\n" +
                "                \"8. ऊपर वर्णित एक या एक से अधिक दवाएँ,सर दर्द के तीव्र या रोगसूचक उपचार के लिए ≥ 10 दिन / महीने', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 106, 0, 0, 41, 'Do you take any of the following classes of drugs for the mentioned days per month for more than 3 months?\n" +
                "(a) ergotamine on ≥ 10 days/month\n" +
                "(b) one or more triptans, in any formulation, on ≥ 10 days/month \n" +
                "(c) paracetamol on ≥ 15 days/ month \n" +
                "(d) one or more non-steroidal anti- inflammatory drugs (NSAIDs) (other than acetylsalicylic acid) on ≥15 days/month\n" +
                "(e) one or more opioids on ≥10 days/month\n" +
                "(f) one or more combination- analgesic medications on ≥10 days/month\n" +
                "(g) any combination of ergotamine, triptans, non-opioid analgesics and/or opioids on a total of ≥10 days/month without overuse of any single drug or drug class alone\n" +
                "(h) one or more medications other than those described above,1 taken for acute or symp- tomatic treatment of headache ≥10 days/months\n','क्या आप 3 महीने से अधिक के लिए प्रति माह उल्लिखित दिनों के लिए दवाओं के किसी भी वर्ग को लेते हैं?\\n\" +\n" +
                "                \"1. ergotamine ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"2. एक या अधिक ट्रिप्टन, किसी भी सूत्रीकरण में, ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"3. पेरासिटामोल ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"4. एक या अधिक NSAID (एनएसएआईडी) (एसि टाइलसैलि सिलिक एसिड के अलावा) ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"5. एक या एक से ज्यादा opioids on ≥10 दिन / महीने\\n\" +\n" +
                "                \"6. एक या एक से ज्यादा संयोजन- एनाल्जेसिक दवाएं ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"7. ergotamine, triptans, non-opioid analgesics and/or opioids का संयोजन ≥10 दिन / महीने\\n\" +\n" +
                "                \"8. ऊपर वर्णित एक या एक से अधिक दवाएँ,सर दर्द के तीव्र या रोगसूचक उपचार के लिए ≥ 10 दिन / महीने', '')");
        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(3.2, 107, 0, 0, 45, 'Do you take any of the following classes of drugs for the mentioned days per month for more than 3 months?\n" +
                "(a) ergotamine on ≥ 10 days/month\n" +
                "(b) one or more triptans, in any formulation, on ≥ 10 days/month \n" +
                "(c) paracetamol on ≥ 15 days/ month \n" +
                "(d) one or more non-steroidal anti- inflammatory drugs (NSAIDs) (other than acetylsalicylic acid) on ≥15 days/month\n" +
                "(e) one or more opioids on ≥10 days/month\n" +
                "(f) one or more combination- analgesic medications on ≥10 days/month\n" +
                "(g) any combination of ergotamine, triptans, non-opioid analgesics and/or opioids on a total of ≥10 days/month without overuse of any single drug or drug class alone\n" +
                "(h) one or more medications other than those described above,1 taken for acute or symp- tomatic treatment of headache ≥10 days/months\n','क्या आप 3 महीने से अधिक के लिए प्रति माह उल्लिखित दिनों के लिए दवाओं के किसी भी वर्ग को लेते हैं?\\n\" +\n" +
                "                \"1. ergotamine ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"2. एक या अधिक ट्रिप्टन, किसी भी सूत्रीकरण में, ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"3. पेरासिटामोल ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"4. एक या अधिक NSAID (एनएसएआईडी) (एसि टाइलसैलि सिलिक एसिड के अलावा) ≥ 15 दिन / महीने\\n\" +\n" +
                "                \"5. एक या एक से ज्यादा opioids on ≥10 दिन / महीने\\n\" +\n" +
                "                \"6. एक या एक से ज्यादा संयोजन- एनाल्जेसिक दवाएं ≥ 10 दिन / महीने\\n\" +\n" +
                "                \"7. ergotamine, triptans, non-opioid analgesics and/or opioids का संयोजन ≥10 दिन / महीने\\n\" +\n" +
                "                \"8. ऊपर वर्णित एक या एक से अधिक दवाएँ,सर दर्द के तीव्र या रोगसूचक उपचार के लिए ≥ 10 दिन / महीने', '')");


        //Level-4
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 1, 0 , null, 5)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 1, 1 , 2, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 2, 0 , null, 5)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 2, 1 , 3, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 3, 0 , null, 5)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 3, 1 , null, 7)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 3, 2 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 3, 3 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 3, 4 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 3, 5 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 4, 0 , null, 7)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 4, 1 , 5, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 4, 2 , 5, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 4, 3 , 5, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 5, 0 , null, 5)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.1, 5, 1 , null, 6)");
//
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 1, 0 , null, 8)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 1, 1 , 2, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 2, 0 , null, 8)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 2, 1 , null, 8)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 2, 2 , null, 8)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 2, 3 , 3, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 2, 4 , 3, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 2, 5 , 3, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 2, 6 , 3, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 0 , null, 8)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 1 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 2 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 3 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 4 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 5 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 6 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 7 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 8 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 9 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 10 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 11 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 12 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 3, 13 , 4, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 4, 0 , null, 10)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 4, 1 , 5, null)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 5, 0 , null, 10)");
//        db.execSQL("insert into multi_branch_logic_info(lvl,que_group_id,min_yes_response,next_que_group_id, headache_cat) values(4.2, 5, 1 , null, 9)");
//
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 1, 0, 0, null, 'Do you have a headache which lasts longer than 4 hours, if you do not treat it with acute medications?', 'क्या आपका सिरदर्द 4 घंटे से अधिक समय तक रहता है, यदि आप इसे तीव्र दवाओं के साथ इलाज नहीं करते हैं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 2, 0, 0, 1, 'Does your headache last 4 hours to 72 hours?','क्या आपका सिरदर्द 4 घंटे से 72 घंटे तक रहता है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 3.1, 0, 0, 2, 'Do you have headaches on one side of the head only?','क्या आपको केवल सिर के एक तरफ सिरदर्द है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 3.2, 0, 0, 2, 'Do you have throbbing/pulsating headaches?','क्या आपको धड़क / धड़कते सिरदर्द है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 3.3, 0, 0, 2, 'Do you have headaches which either restricts or prevent all of your activities?', 'क्या आपका सिरदर्द आपकी सभी गतिविधियों को प्रतिबंधित या रोकता है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 3.4, 0, 0, 2, 'Does the headache get aggravated if you continue to work or do some physical activity?', 'यदि आप लगातार काम करते हैं या कुछ शारीरिक गतिविधि करते हैं तो क्या सिरदर्द बढ़ जाता है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 3.5, 0, 0, 2, 'Do you avoid daily activities like walking and climbing stairs during headache?', 'क्या आप सिरदर्द के दौरान सीढ़ियों पर चलना और चढ़ना जैसी दैनिक गतिविधियों से बचते हैं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 4.1, 0, 0, 3, 'Do you have nausea during headaches?','क्या आपको सिरदर्द के दौरान मतली होती है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 4.2, 0, 0, 3, 'Do you have vomiting during headaches?', 'क्या आपको सिरदर्द के दौरान उल्टी होती है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 4.3, 0, 0, 3, 'Does the normal light at home and normal sound make you uncomfortable during the headache?', 'क्या घर पर सामान्य रोशनी और सामान्य आवाज़ आपको सिरदर्द के दौरान असहज बनाती है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.1, 5, 0, 0, 4, 'Do you have five such attacks till today from the start of the headache?', 'क्या आपको सिरदर्द शुरू होने से लेकर आज तक पांच ऐसे हमले हैं?', '')");
//
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 1, 0, 0, null, 'Do you get any short lasting problems affecting your vision, body sensations, movements, speech, balance, hearing or consciousness prior or during headaches?', 'क्या आपको पहले या सिरदर्द के दौरान आपकी दृष्टि, शरीर की संवेदनाओं, गति, भाषण, संतुलन, सुनने या चेतना को प्रभावित करने वाली कोई छोटी-मोटी समस्या है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 2.1, 0, 0, 1, 'Do you have at least one of such symptoms which spread gradually over 5 minutes?','क्या आपको कम से कम एक ऐसा लक्षण है जो 5 मिनट में धीरे-धीरे फैलता है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 2.2, 0, 0, 1, 'Do you have two or more such symptoms occurring in succession?','क्या आपको निरंतरता में दो या अधिक ऐसे लक्षण हैं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 2.3, 0, 0, 1, 'Do you have each individual such symptom lasting 5–60 minutes?','क्या आपको ऐसे प्रत्येक लक्षण हैं जो 5-60 मिनट तक टिकते हैं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 2.4, 0, 0, 1, 'Do you have at least one such symptom which occurs on one side?','क्या आपके पास कम से कम एक ऐसा लक्षण है जो एक तरफ होता है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 2.5, 0, 0, 1, 'Do you have at least one such symptom is positive like having abnormal visual sights and sensory symptoms like pins and needles sensations?','क्या आपको कम से कम एक ऐसा लक्षण सकारात्मक है जैसे असामान्य दृश्य दृष्टि और संवेदी लक्षण जैसे कि पिन और सुई संवेदनाएं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 2.6, 0, 0, 1, 'Do you have aura accompanying, or followed within 60 minutes, by headache?','क्या आपको सिरदर्द के साथ, या 60 मिनट के भीतर आभा है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.1, 0, 0, 2, 'During or before headaches do you see coloured or black in white angulated, arcuate, and gradually enlarging visual sights or do you see unusual bright objects that do not actually exist or do you have blind parts in your visual field of one or both eyes?','सिर दर्द के दौरान या उससे पहले आप  रंगीन या काले-सफेद एंगुलेटेड, आर्किट, और धीरे-धीरे दृश्य दृष्टि को बढ़ाते हैं या क्या आप असामान्य उज्ज्वल वस्तुओं को देखते हैं जो वास्तव में मौजूद नहीं हैं या क्या आपको एक या दोनों आंखों के दृश्य क्षेत्र में अंधा हिस्सा है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.2, 0, 0, 2, 'Do you get sensations in the form of pins and needles moving slowly from the point of origin and affecting a greater or smaller part of one side of the body, face and/or tongue or numbness?','क्या आप पिंस और सुइयों के रूप में उत्तेजना प्राप्त करते हैं जो धीरे-धीरे मूल बिंदु से आगे बढ़ती हैं और शरीर, चेहरे और / या जीभ या सुन्नता के एक तरफ के अधिक या छोटे हिस्से को प्रभावित करती हैं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.3, 0, 0, 2, 'Do you have speech disturbances in the form of that you are not able to speak properly or words are spoken fluently or take a long time to speak?','क्या आपको भाषण की गड़बड़ी इस रूप में है कि आप ठीक से बोल नहीं पा रहे हैं या शब्दों को धाराप्रवाह बोला जाता है या बोलने में लंबा समय लगता है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.41, 0, 0, 2, 'Do you have weakness in tone or more limbs associated with the headache?','क्या आपको स्वर या सिरदर्द से जुड़े अंग में कमजोरी है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.42, 0, 0, 2, 'Do you have weakness in one side of the body?','क्या आपको शरीर के एक तरफ कमजोरी है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.51, 0, 0, 2, 'Do you have slurring of speech?','क्या आप भाषण में सुस्त हैं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.52, 0, 0, 2, 'Do you have vertigo?','क्या आपको चक्कर है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.53, 0, 0, 2, 'Do you have ringing of ear?','क्या आपके कान बज रहे हैं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.54, 0, 0, 2, 'Do you have hearing difficulty?','क्या आपको सुनने में कठिनाई है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.55, 0, 0, 2, 'Do you have double vision?','क्या आपको दोहरी दृष्टि है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.56, 0, 0, 2, 'Do you have imbalance while walking?','क्या आपको चलते समय असंतुलन है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.57, 0, 0, 2, 'Do you decreased awareness or responsiveness to the self or the environment?','क्या आपने स्वयं या पर्यावरण के प्रति जागरूकता या प्रतिक्रिया को कम कर दिया है?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 3.6, 0, 0, 2, 'Do you have fully reversible, positive, and/or negative visual phenomena like scintillations, scotoma, or blindness in one eye?','क्या आपको पूरी तरह से प्रतिवर्ती, सकारात्मक और / या नकारात्मक दृश्य घटनाएं हैं जैसे कि एक आंख में झालर, स्कोटोमा, या अंधापन?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 4, 0, 0, 3, 'If yes to any of the above symptoms, do the above symptoms resolve completely?','यदि उपरोक्त लक्षणों में से किसी के लिए हाँ, तो क्या उपरोक्त लक्षण पूरी तरह से हल करते हैं?', '')");
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.2, 5, 0, 0, 4, 'Do you have five such attacks till today from the start of the headache?','क्या आपको सिरदर्द शुरू होने से लेकर आज तक पांच ऐसे हमले हैं?', '')");
//
//        db.execSQL("insert into branching_logic_info(lvl,que_id,ans_yes_id,ans_no_id,prev_que_id,que_str_en,que_str_hi, remarks) values(4.3, 1, 0, 0, null, 'Do you want to evaluate Migraine sub-type on basis of patient responses?','क्या आप रोगी के उत्तरों के आधार पर माइग्रेन उप-प्रकार का मूल्यांकन करना चाहते हैं?', '')");



        Log.i(TAG,"Inserted Branching Logic Data.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS branching_logic_info");
        db.execSQL("DROP TABLE IF EXISTS answer_info");
        db.execSQL("DROP TABLE IF EXISTS headache_cat_info" );
        //db.execSQL("DROP TABLE IF EXISTS patient_test_basic_info");
        //db.execSQL("DROP TABLE IF EXISTS test_detail_data" );
        //db.execSQL("DROP TABLE IF EXISTS patient_response_data" );
        db.execSQL("DROP TABLE IF EXISTS multi_branch_logic_info" );

        // create new tables
        onCreate(db);
        Log.i(TAG,"Database has been upgraded from ver "+oldVersion+" to ver "+newVersion+".");
    }

}