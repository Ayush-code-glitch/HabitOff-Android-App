package com.habitoff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelper class to manage SQLite database operations
 * This class handles user registration, login, profile management, and motivational quotes
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HabitOff.db";
    private static final int DATABASE_VERSION = 2;

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_QUOTES = "quotes";

    // Users table column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_QUIT_DATE = "quit_date";
    private static final String COLUMN_CIGARETTES_PER_DAY = "cigarettes_per_day";
    private static final String COLUMN_PRICE_PER_CIGARETTE = "price_per_cigarette";
    private static final String COLUMN_CREATED_AT = "created_at";

    // Quotes table column names
    private static final String COLUMN_QUOTE_ID = "id";
    private static final String COLUMN_QUOTE_TEXT_EN = "quote_text_en";
    private static final String COLUMN_QUOTE_TEXT_HI = "quote_text_hi";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT NOT NULL,"
                + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE,"
                + COLUMN_PASSWORD + " TEXT NOT NULL,"
                + COLUMN_QUIT_DATE + " TEXT,"
                + COLUMN_CIGARETTES_PER_DAY + " INTEGER DEFAULT 0,"
                + COLUMN_PRICE_PER_CIGARETTE + " REAL DEFAULT 0,"
                + COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create quotes table
        String CREATE_QUOTES_TABLE = "CREATE TABLE " + TABLE_QUOTES + "("
                + COLUMN_QUOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_QUOTE_TEXT_EN + " TEXT NOT NULL,"
                + COLUMN_QUOTE_TEXT_HI + " TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_QUOTES_TABLE);

        // Insert default quotes
        insertDefaultQuotes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    /**
     * Insert default motivational quotes into database
     */
    private void insertDefaultQuotes(SQLiteDatabase db) {
        String[][] quotes = {
                {"Quit smoking today, breathe freely tomorrow.", "आज सिगरेट छोड़ो, कल स्वतंत्र रूप से सांस लो।"},
                {"Each cigarette you skip adds minutes to your life.", "हर सिगरेट जो आप छोड़ते हो, आपके जीवन में मिनट जोड़ता है।"},
                {"Your body is your home, protect it from cigarettes.", "आपका शरीर आपका घर है, इसे सिगरेट से बचाओ।"},
                {"Choose life over a cigarette.", "सिगरेट के बजाय जीवन चुनें।"},
                {"Smoking is a slow poison - quit now.", "धूम्रपान एक धीमा जहर है - अभी छोड़ो।"},
                {"Clear lungs, clear mind.", "साफ फेफड़े, साफ दिमाग।"},
                {"One smoke can kill, zero smokes save.", "एक धुआं मार सकता है, शून्य धुआं बचाता है।"},
                {"Health is wealth, not cigarettes.", "स्वास्थ्य ही धन है, सिगरेट नहीं।"},
                {"Quit smoking, embrace a healthy life.", "सिगरेट छोड़ो, स्वस्थ जीवन अपनाओ।"},
                {"He who quits smoking, wins at life.", "जो सिगरेट छोड़ता है, वह जीवन में जीतता है।"},
                {"Breathe fresh, live fresh – say no to cigarettes.", "ताजी सांस लो, ताजा जीओ - सिगरेट के लिए ना कहो।"},
                {"Kick the habit, embrace a bright future.", "आदत को मार दो, एक उज्ज्वल भविष्य को गले लगाओ।"},
                {"Your life is worth more than a cigarette.", "आपका जीवन एक सिगरेट से ज्यादा कीमती है।"},
                {"Distance yourself from smoking, stay healthy.", "धूम्रपान से दूरी बनाएँ, स्वस्थ रहें।"},
                {"Take care of yourself and loved ones, quit cigarettes.", "अपना और अपनों का ध्यान रखें, सिगरेट छोड़ें।"},
                {"Life gets destroyed in cigarette ashes.", "जीवन सिगरेट की राख में नष्ट हो जाता है।"},
                {"True happiness of life is not in cigarettes.", "जीवन की असली खुशियाँ सिगरेट में नहीं।"},
                {"Start a new journey today, say goodbye to cigarettes.", "आज से नया सफर शुरू करें, सिगरेट को अलविदा कहें।"},
                {"No smoke, want pure air.", "धुआं नहीं, साफ हवा चाहिए।"},
                {"Quit smoking, increase your life.", "सिगरेट छोड़ो, जीवन बढ़ाओ।"},
                {"Break the chain, live tobacco free.", "जंजीर को तोड़ो, तंबाकू मुक्त जियो।"},
                {"Quit for your family, quit for yourself.", "अपने परिवार के लिए छोड़ो, अपने लिए छोड़ो।"},
                {"Each day smoke-free is a victory.", "हर दिन धुआं मुक्त एक जीत है।"},
                {"Don't let smoking control your life.", "सिगरेट को अपने जीवन पर नियंत्रण न करने दो।"},
                {"Life is too precious to waste on smoke.", "जीवन धुआं पर बर्बाद करने के लिए बहुत कीमती है।"},
                {"From smoker to survivor - make the change.", "धूम्रपान करने वाले से बचने वाले तक - बदलाव लाओ।"},
                {"Quitting smoking is winning health.", "सिगरेट छोड़ना स्वास्थ्य जीतना है।"},
                {"Leave the smoke, find your hope.", "धुआं छोड़ो, अपनी आशा खोजो।"},
                {"Small steps lead to big changes.", "छोटे कदम बड़े बदलाव लाते हैं।"},
                {"Choose air, not smoke.", "हवा चुनो, धुआं नहीं।"},
                {"Be the hero of your health story.", "अपनी स्वास्थ्य कहानी का नायक बनो।"},
                {"Quit smoking, start living.", "सिगरेट छोड़ो, जीना शुरू करो।"},
                {"Your breath matters, quit today.", "आपकी सांस महत्वपूर्ण है, आज छोड़ो।"},
                {"Smoke-free is the way to be.", "धुआं मुक्त ही रहने का तरीका है।"},
                {"Strong lungs, strong life.", "मजबूत फेफड़े, मजबूत जीवन।"},
                {"The best time to quit is now.", "छोड़ने का सबसे अच्छा समय अब है।"},
                {"Stop smoking, start breathing.", "सिगरेट बंद करो, सांस लेना शुरू करो।"},
                {"Clear your lungs, clear your future.", "अपने फेफड़े साफ करो, अपना भविष्य साफ करो।"},
                {"Live long, quit strong.", "लंबा जियो, मजबूती से छोड़ो।"},
                {"Better health begins with quitting smoking.", "बेहतर स्वास्थ्य सिगरेट छोड़ने के साथ शुरू होता है।"},
                {"It's never too late to quit.", "छोड़ना कभी देर नहीं है।"},
                {"Break free from smoking, embrace freedom.", "धूम्रपान से मुक्त हो, आजादी को गले लगाओ।"},
                {"Quit smoking, cherish every breath.", "सिगरेट छोड़ो, हर सांस को संजो।"},
                {"Choose lungs full of life, not full of smoke.", "जीवन से भरे फेफड़े चुनो, धुआं से नहीं।"},
                {"Quit smoking for a brighter tomorrow.", "उज्जवल कल के लिए सिगरेट छोड़ो।"},
                {"Your health, your choice - quit now.", "आपका स्वास्थ्य, आपकी पसंद - अब छोड़ो।"},
                {"Don't let smoking steal your dreams.", "सिगरेट को अपने सपनों को चोरी न करने दो।"},
                {"Live clean, breathe clean.", "स्वच्छ जियो, स्वच्छ सांस लो।"},
                {"Quit today, live tomorrow.", "आज छोड़ो, कल जियो।"},
                {"Freedom from smoking is freedom to live.", "धूम्रपान से आजादी जीने की आजादी है।"},
                {"Be proud to be smoke-free.", "धुआं मुक्त होने पर गर्व करो।"},
                {"Say NO to tobacco, say YES to life.", "तंबाकू के लिए ना कहो, जीवन के लिए हां कहो।"},
                {"Quit smoking, feel the new energy.", "सिगरेट छोड़ो, नई ऊर्जा का अनुभव करो।"},
                {"Stay away from cigarettes for a healthy life.", "एक स्वस्थ जीवन के लिए सिगरेट से दूर रहो।"},
                {"Break free from the chains of cigarettes.", "सिगरेट की जंजीरों से मुक्त हो।"},
                {"Choose hope in life, not cigarettes.", "सिगरेट नहीं, जीवन में आशा चुनें।"},
                {"Smoking dims your future, quitting brightens it.", "सिगरेट आपका भविष्य मंद करता है, छोड़ना इसे उज्ज्वल करता है।"},
                {"Take the first step, quit smoking now.", "पहला कदम उठाओ, अभी सिगरेट छोड़ो।"},
                {"Don't trade your life for a puff.", "एक कश के लिए अपना जीवन मत बेचो।"},
                {"Set yourself free — quit smoking.", "अपने आप को मुक्त करो - सिगरेट छोड़ो।"},
                {"Smoking steals your breath and your life.", "सिगरेट आपकी सांस और जीवन चोरी करता है।"},
                {"Every smoke harms you.", "हर धुआं तुम्हें खराब करता है।"},
                {"One less cigarette a day, more happiness in life.", "दिन में एक सिगरेट कम, जीवन में खुशियाँ ज्यादा।"},
                {"Health is prosperity, not cigarettes.", "स्वास्थ्य ही समृद्धि है, सिगरेट नहीं।"},
                {"Leaving cigarettes, gaining health.", "सिगरेट का त्याग, स्वास्थ्य का लाभ।"},
                {"Say goodbye to cigarettes with the strength of your mind.", "मन की ताकत से सिगरेट को अलविदा कहो।"},
                {"Quit smoking, avoid pain.", "सिगरेट छोड़िए, दर्द से बचिए।"},
                {"Stay away from cigarettes, embrace happiness.", "सिगरेट से दूर रहें, खुशियाँ अपनाएं।"},
                {"Quit smoking, stay happy.", "सिगरेट छोड़ो, खुशहाल रहो।"},
                {"Every cigarette reduces life.", "हर सिगरेट जीवन को घटाता है।"},
                {"Cigarette is an enemy of health.", "सिगरेट स्वास्थ्य का दुश्मन है।"},
                {"Quit smoking, bloom life.", "सिगरेट छोड़ो, जीवन खिलाओ।"},
                {"Away from cigarettes for a healthy life.", "स्वस्थ जीवन के लिए सिगरेट से दूर।"},
                {"Not cigarettes, pure air is needed.", "सिगरेट नहीं, शुद्ध हवा चाहिए।"},
                {"Quit tobacco, gain life.", "तंबाकू छोड़ो, जीवन पाओ।"},
                {"Life is beautiful without cigarettes.", "सिगरेट के बिना जीवन सुहाना है।"},
                {"Take care of your health, avoid smoking.", "अपने स्वास्थ्य का ख्याल रखें, सिगरेट से बचें।"},
                {"Quit smoking, bring smile.", "सिगरेट छोड़ो, मुस्कान लाओ।"},
                {"Pledge today to quit smoking.", "आज सिगरेट छोड़ने का वचन दो।"},
                {"Quitting smoking saves lives—starting with yours.", "सिगरेट छोड़ना जीवन बचाता है—अपने से शुरू करो।"},
                {"Smoke-free is easy if you can.", "अगर कर सकते हो तो धुआं मुक्त आसान है।"},
                {"If you want to quit smoking, do it today.", "अगर सिगरेट छोड़ना है तो आज करो।"},
                {"Life free from cigarettes is the best.", "सिगरेट से मुक्त जीवन सबसे अच्छा है।"},
                {"Live a healthy life by quitting cigarettes.", "सिगरेट छोड़कर स्वस्थ जीवन जियो।"},
                {"Health is possible only when you distance yourself from tobacco.", "स्वास्थ्य तभी संभव है जब आप तंबाकू से दूर रहो।"},
                {"When cigarettes leave, life blooms.", "जब सिगरेट चला जाता है, जीवन खिल उठता है।"},
                {"Quit cigarettes for your family.", "अपने परिवार के लिए सिगरेट छोड़ो।"},
                {"Stay away from cigarettes every day, health will be fully prepared.", "हर दिन सिगरेट से दूर, सेहत हो पूरी तैयार।"},
                {"Quit smoking and embrace life.", "सिगरेट छोड़ो और जीवन को गले लगाओ।"},
                {"Quit tobacco, choose happiness.", "तम्बाकू छोड़ो, खुशियाँ चुनो।"},
                {"Quit smoking and stay healthy.", "सिगरेट छोड़ो और स्वस्थ रहो।"},
                {"You can do this. Believe in yourself.", "तुम यह कर सकते हो। अपने आप पर विश्वास करो।"},
                {"Every day is a new opportunity to quit.", "हर दिन छोड़ने का एक नया अवसर है।"},
                {"Your family believes in you. Quit smoking.", "आपका परिवार आप पर विश्वास करता है। सिगरेट छोड़ो।"}
        };

        for (String[] quote : quotes) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUOTE_TEXT_EN, quote[0]);
            values.put(COLUMN_QUOTE_TEXT_HI, quote[1]);
            db.insert(TABLE_QUOTES, null, values);
        }
    }

    /**
     * Register a new user
     * @param username User's username
     * @param email User's email
     * @param password User's password
     * @return true if registration successful, false otherwise
     */
    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_QUIT_DATE, getCurrentDate());
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    /**
     * Check if user credentials are valid
     * @param email User's email
     * @param password User's password
     * @return true if credentials are valid, false otherwise
     */
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    /**
     * Check if email already exists
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    /**
     * Get user details by email
     * @param email User's email
     * @return User object with user details
     */
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_QUIT_DATE,
                COLUMN_CIGARETTES_PER_DAY, COLUMN_PRICE_PER_CIGARETTE};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        User user = null;

        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setQuitDate(cursor.getString(3));
            user.setCigarettesPerDay(cursor.getInt(4));
            user.setPricePerPack(cursor.getDouble(5));
        }

        cursor.close();
        return user;
    }

    /**
     * Update user profile information
     * @param userId User ID
     * @param cigarettesPerDay Number of cigarettes smoked per day
     * @param pricePerCigarette Price per cigarette
     * @return true if update successful, false otherwise
     */
    public boolean updateUserProfile(int userId, int cigarettesPerDay, double pricePerCigarette) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CIGARETTES_PER_DAY, cigarettesPerDay);
        values.put(COLUMN_PRICE_PER_CIGARETTE, pricePerCigarette);
        int result = db.update(TABLE_USERS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userId)});
        return result > 0;
    }

    /**
     * Get a random motivational quote
     * @return Random quote text in English
     */
    public String getRandomQuoteEnglish() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_QUOTE_TEXT_EN + " FROM " + TABLE_QUOTES + " ORDER BY RANDOM() LIMIT 1", null);
        String quote = "";
        if (cursor.moveToFirst()) {
            quote = cursor.getString(0);
        }
        cursor.close();
        return quote;
    }

    /**
     * Get a random motivational quote in Hindi
     * @return Random quote text in Hindi
     */
    public String getRandomQuoteHindi() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_QUOTE_TEXT_HI + " FROM " + TABLE_QUOTES + " ORDER BY RANDOM() LIMIT 1", null);
        String quote = "";
        if (cursor.moveToFirst()) {
            quote = cursor.getString(0);
        }
        cursor.close();
        return quote;
    }

    /**
     * Get current date in YYYY-MM-DD format
     * @return Current date string
     */
    private String getCurrentDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }
}