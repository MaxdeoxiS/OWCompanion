package com.example.maxime.overwatchstats.tools;



        import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by coderzlab on 18/1/16.
 */
public class Methods {

    private static final String TAG = Methods.class.getSimpleName();

    private static final int PULSE_ANIMATOR_DURATION = 300;

    private static SimpleDateFormat NOTIF_DATE_FORMAT = new SimpleDateFormat("dd MMM");
    private static SimpleDateFormat orderDateTimeFormat = new SimpleDateFormat("dd-MMM-yy");

    private static SimpleDateFormat chatTimeFormat = new SimpleDateFormat("hh:mm a");
    private static SimpleDateFormat chatHeaderTimeFormat = new SimpleDateFormat("dd-MMM-yy");
    private static String imgPath;

    public static String formatDateTimeForOderDetail(Date inputDateTime) {
        return orderDateTimeFormat.format(inputDateTime);
    }


    public static Date timestampToDate(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp);
        return calendar.getTime();
    }


    public static String timestampToOrderDateTime(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp);
        return formatDateTimeForOderDetail(calendar.getTime());
    }


    public static String timestampToNotificationTime(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp);
        return formatNotificationTime(calendar.getTime());
    }


    public static String getChatTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = chatTimeFormat.format(cal.getTime());//,new StringBuffer("hh:mm a"),new FieldPosition(0));
        return date.toUpperCase();
    }


    public static String getChatHeaderDateTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = chatHeaderTimeFormat.format(cal.getTime());//,new StringBuffer("hh:mm a"),new FieldPosition(0));
        return date.toUpperCase();
    }


    public static String getThreadTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = formatNotificationTime(cal.getTime());//,new StringBuffer("hh:mm a"),new FieldPosition(0));
        return date.toUpperCase();
    }


    public static String getLastSeenTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = formatLastSeenTime(cal.getTime());//,new StringBuffer("hh:mm a"),new FieldPosition(0));
        return date;
    }


    public static String getStaticData(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        return mcpPreferences.getString("staticData", null);
    }

    public static boolean saveStaticData(Context context, String staticData) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastSavedStaticData", System.currentTimeMillis()).putString("staticData", staticData).commit();
    }

    public static String getDataFromSharedPreferences(Context context) {

        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        String sliderresponse = mcpPreferences.getString("response", "");

        return sliderresponse;
    }


    public static long getLastSavedStaticDataTime(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        return mcpPreferences.getLong("lastSavedStaticData", 0);
    }


    public static boolean saveLastSavedGcm(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastSavedGcm", System.currentTimeMillis()).commit();
    }

    public static long getLastSavedGcmKeyTime(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        return mcpPreferences.getLong("lastSavedGcm", 0);
    }


    public static boolean saveLastUserSyncTime(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastUserSync", System.currentTimeMillis()).commit();
    }

    public static boolean clearLastUserSyncTime(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastUserSync", 0).commit();
    }

    public static long getLastUserSyncTime(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        return mcpPreferences.getLong("lastUserSync", 0);
    }


    public static boolean saveLastUserGetCountTime(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastUserGetCountSync", System.currentTimeMillis()).commit();
    }

    public static boolean clearLastUserGetCountTime(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastUserGetCountSync", 0).commit();
    }

    public static long getLastUserGetCountTime(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        return mcpPreferences.getLong("lastUserGetCountSync", 0);
    }


    public static String getPreviousAppVersion(Context context) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        return mcpPreferences.getString("previousAppVersion", null);
    }

    public static boolean saveCurrentAppVersion(Context context, String currentAppVersion) {
        SharedPreferences mcpPreferences = getSKSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putString("previousAppVersion", currentAppVersion).commit();
    }


    public static void requestForCall(Context context, String mobileNumber) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(mobileNumber.trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String formatNotificationTime(Date createdAt) {

        String time = "";
        long diff = new Date().getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffDays > 1)
            time = NOTIF_DATE_FORMAT.format(createdAt);
        else if (diffDays > 0)
            time = "Yesterday";
        else if (diffHours > 0)
            time = diffHours + " hours ago";
        else if (diffMinutes > 0)
            time = diffMinutes + " minutes ago";
        else
            time = "Now";

        return time;
    }


    public static String formatLastSeenTime(Date createdAt) {

        String time = "Last seen ";
        long diff = new Date().getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffDays > 1)
            time = time + NOTIF_DATE_FORMAT.format(createdAt);
        else if (diffDays > 0)
            time = time + "Yesterday";
        else if (diffHours > 0)
            time = time + diffHours + " hours ago";
        else if (diffMinutes > 0)
            time = time + diffMinutes + " minutes ago";
        else
            time = "Online";

        return time;
    }


    public static String formatRequirementFulfilledTime(Date createdAt, Date updatedAt) {

        String time = "";
        long diff = updatedAt.getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffDays > 0)
            time = diffDays + " Days";
        else if (diffHours > 0)
            time = diffHours + " Hours";
        else if (diffMinutes > 0)
            time = diffMinutes + " Minutes";
        else
            time = "Now";

        return time;
    }


    public static String getFormattedAmount(double value) {
        return String.format("%.2f", value);
    }

   /* public static String getFormattedDate(Date inputDateTime){

        if(inputDateTime==null){
            return "NA";
        }
        try {
            return outputDateTimeFormat.format(inputDateTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  inputDateTime.toString();
    }
*/


    public static boolean isMobileNumber(String paramCharSequence) {
        return paramCharSequence.matches("[0-9]+");
    }

    public static boolean isValidMobileNumber(String paramCharSequence) {
        return paramCharSequence.matches("[0-9]+") && paramCharSequence.length() >= 5;
    }

    public static boolean isValidPhoneNumber(String paramCharSequence) {
        return paramCharSequence.matches("[0-9]+") && paramCharSequence.length() >= 5 && paramCharSequence.length() <= 13;
    }

    public static boolean isValidEmail(CharSequence paramCharSequence) {
        return (!TextUtils.isEmpty(paramCharSequence) &&
                Patterns.EMAIL_ADDRESS.matcher(paramCharSequence).matches());
    }

    public static boolean isValidAddress(CharSequence paramCharSequence) {
        return (!TextUtils.isEmpty(paramCharSequence) &&
                paramCharSequence.length() >= 10);
    }

    public static ProgressDialog setUpProgressDialog(Context paramContext, String paramString, boolean paramBoolean) {
        ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
        localProgressDialog.setMessage(paramString);
        localProgressDialog.setCancelable(paramBoolean);
        if (!((Activity) paramContext).isFinishing())
            localProgressDialog.show();

        return localProgressDialog;
    }


    //(men|red)
    public static String convertToORPattern(List<String> stringList) {
        if (stringList == null || stringList.isEmpty())
            return null;

        StringBuffer pattern = new StringBuffer("(");
        for (String s : stringList)
            pattern.append(s).append("|");

        pattern.setCharAt(pattern.length() - 1, ')');
        System.out.println("pattern:" + pattern);
        return pattern.toString();
    }


    //^(?=.*men)(?=.*red).*
    public static String convertToANDPattern(List<String> stringList) {
        if (stringList == null || stringList.isEmpty())
            return null;

        StringBuffer pattern = new StringBuffer("^(");
        for (String s : stringList)
            pattern.append("(?=.*").append(s).append(")");

        pattern.append(".*");
        //System.out.println("pattern:"+pattern);
        return pattern.toString();
    }

    //^(?=.*\bwomen\b)(?=.*\bAV\b).*
    public static String convertToANDPattern(String[] stringList) {
        if (stringList == null || stringList.length == 0)
            return null;

        StringBuffer pattern = new StringBuffer("^");

        for (String s : stringList)
            pattern.append("(?=.*\\b").append(s).append("\\b)");

        pattern.append(".*");
        //System.out.println("pattern:"+pattern);
        return pattern.toString();
    }

//    /**
//     * Render an animator to pulsate a view in place.
//     *
//     * @param labelToAnimate the view to pulsate.
//     * @return The animator object. Use .start() to begin.
//     */
//    public static ObjectAnimator getPulseAnimator(View labelToAnimate, float decreaseRatio,
//                                                  float increaseRatio) {
//        Keyframe k0 = Keyframe.ofFloat(0f, 1f);
//        Keyframe k1 = Keyframe.ofFloat(0.275f, decreaseRatio);
//        Keyframe k2 = Keyframe.ofFloat(0.69f, increaseRatio);
//        Keyframe k3 = Keyframe.ofFloat(1f, 1f);
//
//        PropertyValuesHolder scaleX = PropertyValuesHolder.ofKeyframe("scaleX", k0, k1, k2, k3);
//        PropertyValuesHolder scaleY = PropertyValuesHolder.ofKeyframe("scaleY", k0, k1, k2, k3);
//        ObjectAnimator pulseAnimator =
//                ObjectAnimator.ofPropertyValuesHolder(labelToAnimate, scaleX, scaleY);
//        pulseAnimator.setDuration(PULSE_ANIMATOR_DURATION);
//
//        return pulseAnimator;
//    }


    public static void storeRegistrationId(Context paramContext, String paramString) {
        Object localObject = Methods.getSKSharedPreferences(paramContext);
        //int i = Methods.getAppVersion(paramContext);
        localObject = ((SharedPreferences) localObject).edit();
        ((SharedPreferences.Editor) localObject).putString("registration_id", paramString);
        //((SharedPreferences.Editor)localObject).putInt("appVersion", i);
        ((SharedPreferences.Editor) localObject).commit();
        Log.d("GCM", "GCM Token Saved Locally");
    }


    public static int getAppVersion(Context paramContext) {
        try {
            int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
            return i;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            throw new RuntimeException("Could not get package name: " + localNameNotFoundException);
        }
    }


    public static String getAppVersionName(Context context) {

        String versionName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(Methods.class.getSimpleName(), e.getLocalizedMessage());
        }
        return versionName;
    }

    public static String getRegistrationId(Context paramContext) {
        SharedPreferences localSharedPreferences = getSKSharedPreferences(paramContext);
        String regID = localSharedPreferences.getString("registration_id", "");

        return regID;
    }


    public static void openShortToast(Context paramContext, String paramString) {
        Toast.makeText(paramContext, paramString, Toast.LENGTH_SHORT).show();
    }


    public static String fetchInitial(String paramString) {
        String str;
        if (!valid(paramString))
            str = "";
        else
            str = Character.toString(paramString.charAt(0)).toUpperCase();
        return str;
    }


   /* public static void setToolbarTitle(Context paramContext, Menu paramMenu, String paramString1, String paramString2, String paramString3) {
        if (paramContext != null) {
            ((MainActivity) paramContext).mToolbar.setTitle(paramString1);
            ((TextView) ((MainActivity) paramContext).mToolbar.findViewById(R.id.toolbar_center_title)).setText(paramString2);
            *//*if (paramMenu != null)
            {
                MenuItem localMenuItem = paramMenu.findItem(R.id.action_next);
                if (localMenuItem != null)
                    localMenuItem.setTitle(paramString3);
            }*//*
        }
    }*/


    /*public static void setMenuItems(Context paramContext, Menu paramMenu, boolean showNotifications, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5) {
        MenuItem localMenuItem = paramMenu.findItem(R.id.actionNotification);
        if (localMenuItem != null) {
            localMenuItem.setVisible(showNotifications).setEnabled(showNotifications);
            if (showNotifications)
                ((MainActivity) paramContext).setUpNotificationItem();
        }*/

        /*localMenuItem = paramMenu.findItem(R.id.action_search);

        if (localMenuItem != null)
            localMenuItem.setVisible(false).setEnabled(false);

        localMenuItem = paramMenu.findItem(R.id.action_edit);

        if (localMenuItem != null)
            localMenuItem.setVisible(paramBoolean4).setEnabled(paramBoolean4);

        localMenuItem = paramMenu.findItem(R.id.action_next);

        if (localMenuItem != null)
            localMenuItem.setVisible(paramBoolean5).setEnabled(paramBoolean5);


        localMenuItem = paramMenu.findItem(R.id.action_location);

        if (localMenuItem != null) {
            localMenuItem.setVisible(paramBoolean2).setEnabled(paramBoolean2);
        }

        localMenuItem = paramMenu.findItem(R.id.action_filter);

        if (localMenuItem != null) {
            localMenuItem.setVisible(paramBoolean2).setEnabled(paramBoolean2);
        }

*/
    //}


    public static boolean valid(String paramString) {
        boolean bool;
        if ((paramString != null) && (!paramString.trim().equals("")) && (!paramString.equals("null")) && (!isOnlyWhiteSpaces(paramString)))
            bool = true;
        else
            bool = false;
        return bool;
    }


    public static boolean isValidColorCode(String colorCode) {
        try {
            Color.parseColor(colorCode);
            return true;
        } catch (Exception e) {
        }
        return false;
    }


    public static boolean containsOnlyZeros(String paramString) {
        return paramString.matches("[0]+");
    }


    private static boolean isOnlyWhiteSpaces(String paramString) {
        boolean bool;
        if (paramString.trim().length() != 0)
            bool = false;
        else
            bool = true;
        return bool;
    }


   /* public static void showAppendedErrorMsg(Context paramContext, String paramString) {
        String str = paramContext.getResources().getString(R.string.some_error_occurred);
        if (!valid(paramString)) {
            str = str;
        } else {
            str = paramString + ". " + str;
        }
        openShortToast(paramContext, str);
    }


    public static boolean isInternetConnected(Context paramContext, boolean showToast) {
        NetworkInfo localNetworkInfo =
                ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean bool;
        if ((localNetworkInfo == null) || (!localNetworkInfo.isConnected())) {
            bool = false;

            if (showToast) {
                Toast.makeText(paramContext, paramContext.getResources().getString(R.string.please_check_your_internet_connection), Toast.LENGTH_SHORT).show();
            }

        } else
            bool = true;

        return bool;
    }


    public static void noInternetDialog(Context paramContext) {
        openShortToast(paramContext, paramContext.getResources().getString(R.string.please_check_your_internet_connection));
    }*/


    public static boolean isInternetConnected(Context paramContext) {
        NetworkInfo localNetworkInfo =
                ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean bool;
        if ((localNetworkInfo == null) || (!localNetworkInfo.isConnected()))
            bool = false;
        else
            bool = true;
        return bool;
    }


    public static void hideSoftKeyboard(Activity paramActivity) {
        InputMethodManager localInputMethodManager = (InputMethodManager) paramActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View localView = paramActivity.getCurrentFocus();
        if (localView != null)
            Log.e("boolean", String.valueOf(localInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 0)));
    }

    private static SharedPreferences getSKSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }






   /* private static Uri setImageUri(final Activity paramActivity) {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + paramActivity.getResources().getString(R.string.shoekonnect_sample));
        Uri imgUri = Uri.fromFile(file);
        imgPath = file.getAbsolutePath();
        return imgUri;
    }*/

    //Create Thumbnail from big images
    public static Bitmap getThumbnailBitmap(String path, int thumbnailSize) {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            return null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / thumbnailSize;
        return BitmapFactory.decodeFile(path, opts);
    }

    public static String getImagePath() {
        return imgPath;
    }


    public static Bitmap doParse(String paramString, int paramInt1, int paramInt2) {
        try {
            BitmapFactory.Options localOptions = new BitmapFactory.Options();
            localOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(paramString, localOptions);
            int m = localOptions.outWidth;
            int k = localOptions.outHeight;
            int i = getResizedDimension(paramInt1, paramInt2, m, k);
            int j = getResizedDimension(paramInt1, paramInt2, k, m);
            localOptions.inJustDecodeBounds = false;
            localOptions.inSampleSize = findBestSampleSize(m, k, i, j);
            Bitmap localBitmap2 = BitmapFactory.decodeFile(paramString, localOptions);
            Bitmap localBitmap1 = null;
            if ((localBitmap2 == null) || ((localBitmap2.getWidth() <= i) && (localBitmap2.getHeight() <= j))) {
                localBitmap1 = localBitmap2;
            } else {
                localBitmap1 = Bitmap.createScaledBitmap(localBitmap2, i, j, true);
                localBitmap2.recycle();
            }
            return localBitmap1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getResizedDimension(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if ((paramInt1 != 0) || (paramInt2 != 0))
            if (paramInt1 != 0) {
                if (paramInt2 != 0) {
                    double d = paramInt4 / paramInt3;
                    int i = paramInt1;
                    if (d * i > paramInt2)
                        i = (int) (paramInt2 / d);
                    paramInt3 = i;
                } else {
                    paramInt3 = paramInt1;
                }
            } else
                paramInt3 = (int) (paramInt2 / paramInt4 * paramInt3);
        return paramInt3;
    }


    public static int findBestSampleSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        double d = Math.min(paramInt1 / paramInt3, paramInt2 / paramInt4);
        for (float f = 1.0F; ; f *= 2.0F)
            if (2.0F * f > d)
                return (int) f;
    }


    public static String getRealPathFromURI(Context paramContext, Uri paramUri) {
        Object localObject;
        if (paramUri == null) {
            localObject = "";
        } else {
            localObject = paramUri.getPath();
            String str = getImagePath(paramContext, paramUri);
            if (str == null)
                localObject = localObject;
            else
                localObject = str;
        }
        return localObject.toString();
    }


    public static String getImagePath(Context paramContext, Uri paramUri) {
        String[] localArr = new String[1];
        localArr[0] = "_data";

        String path = null;
        Cursor cursor = ((Activity) paramContext).managedQuery(paramUri, localArr, null, null, null);

        if (cursor != null) {
            int i = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            path = cursor.getString(i);
        }

        if (path == null)
            path = paramUri.getPath();

        return path;
    }


    public static boolean hasCameraPermissions(Context context, int requestCode) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, requestCode);

            return false;
        } else {
            return true;
        }
    }


    public static boolean hasStoragePermissions(Context context, int requestCode) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

            return false;
        } else {
            return true;
        }
    }

    public static boolean hasSmsReceivePermissions(Context context, int requestCode) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_SMS}, requestCode);

            return false;
        } else {
            return true;
        }
    }


    public static String capitalizeString(String inputString) {
        String outputString = "";
        if (!Methods.valid(inputString))
            return outputString;

        String wordsName[] = inputString.trim().split("[.\\s]+");
        for (int i = 0; i < wordsName.length; i++)
            wordsName[i] = wordsName[i].substring(0, 1).toUpperCase() + wordsName[i].substring(1).toLowerCase();
        outputString = TextUtils.join(" ", wordsName);
        return outputString;
    }


    public static String removeSpecialCharacters(String s) {
        if (Methods.valid(s))
            return s.replaceAll("\\s+", "-").replaceAll("[^a-zA-Z0-9]+", "-").replaceAll("-+", "-");

        return s;
    }


    public static SharedPreferences getPreferances(Context context) {

        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

    }


    public static boolean isBuyTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_BUY_TUTORIAL_SHOW", false);
    }

    public static boolean setBuyTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_BUY_TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isLeadsTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_LEADS_TUTORIAL_SHOW", false);
    }

    public static boolean setLeadsTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_LEADS_TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isOpenMarketTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_OPEN_MARKET_TUTORIAL_SHOW", false);
    }

    public static boolean setOpenMarketTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_OPEN_MARKET_TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isBuyItemTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_BUY_ITEM_TUTORIAL_SHOW", false);
    }

    public static boolean setBuyItemTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_BUY_ITEM_TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isCustomerSupportTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_CUSTOMER_SUPPORT_TUTORIAL_SHOW", false);
    }

    public static boolean setCustomerSupportTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_CUSTOMER_SUPPORT_TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isProductDetailsImageTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_PRODUCT_DETAILS_IMAGE_TUTORIAL_SHOW", false);
    }

    public static boolean setProductDetailsImageTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_PRODUCT_DETAILS_IMAGE_TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isProductDetailsColorsTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_PRODUCT_DETAILS_COLOR_TUTORIAL_SHOW", false);
    }

    public static boolean setProductDetailsColorsTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_PRODUCT_DETAILS_COLOR_TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isProductDetailsAddToCartonTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_PRODUCT_DETAILS_ADD_TO_CARTON__TUTORIAL_SHOW", false);
    }

    public static boolean setProductDetailsAddToCartonTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_PRODUCT_DETAILS_ADD_TO_CARTON__TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isMyCartonTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_MY_CARTON__TUTORIAL_SHOW", false);
    }

    public static boolean setMyCartonTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_MY_CARTON__TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isShoppingPaymentTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_SHOPPING_PAYMENT__TUTORIAL_SHOW", false);
    }

    public static boolean setShoppingPaymentTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_SHOPPING_PAYMENT__TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isPaymentTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_PAYMENT__TUTORIAL_SHOW", false);
    }

    public static boolean setPaymentTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_PAYMENT__TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isLeadsSendInquiryTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_LEADS_SEND_INQUIRY__TUTORIAL_SHOW", false);
    }

    public static boolean setLeadsSendInquiryTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_LEADS_SEND_INQUIRY__TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isTellUsYourReqTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_Tell_US_YOUR_REQ__TUTORIAL_SHOW", false);
    }

    public static boolean setTellUsYourReqTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_Tell_US_YOUR_REQ__TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isPinchZoomTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_PINCH_ZOOM_TUTORIAL_SHOW", false);
    }

    public static boolean setPinchZoomTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_PINCH_ZOOM_TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isPostSamplesTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_POST_Samples_TUTORIAL_SHOW", false);
    }

    public static boolean setPostSamplesTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_POST_Samples_TUTORIAL_SHOW_TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isSearchSamplesTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_SEARCH_SAMPLE_TUTORIAL_SHOW", false);
    }

    public static boolean setSearchSamplesTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_SEARCH_SAMPLE_TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isSampleTabTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_SAMPLE_Tab_TUTORIAL_SHOW", false);
    }

    public static boolean setSampleTabTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_SAMPLE_Tab_TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isMemberTabTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_MEMBERS_TAB_TUTORIAL_SHOW", false);
    }

    public static boolean setMemberTabTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_MEMBERS_TAB_TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isMemberUserProfileTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_USER_PROFILE_TUTORIAL_SHOW", false);
    }

    public static boolean setMemberUserProfileTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_USER_PROFILE_TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isMembersSearchTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_MEMBERS_SEARCH_TUTORIAL_SHOW", false);
    }

    public static boolean setMembersSearchTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_MEMBERS_SEARCH_TUTORIAL_SHOW", showState).commit();
    }

    public static boolean isOpenMarketTabTutorialShowPre(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_OPEN_MARKET_TUTORIAL_SHOW_PRE", false);
    }

    public static boolean setOpenMarketTabTutorialShowPre(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_OPEN_MARKET_TUTORIAL_SHOW_PRE", showState).commit();
    }


    public static boolean showOnboarding(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("SHOW_ONBOARDING", true);
    }

    public static boolean setShowOnboarding(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("SHOW_ONBOARDING", showState).commit();
    }


    public static String getCategoryByProfileType(String profileType) {

        String category = null;
        if (Methods.valid(profileType)) {
            if (profileType.equalsIgnoreCase("Footwear Retailer") || profileType.equalsIgnoreCase("Footwear Manufacturer") || profileType.equalsIgnoreCase("Footwear Wholesaler"))
                category = "footwear";
            else if (profileType.equalsIgnoreCase("Machinery Supplier"))
                category = "machinery";
            else if (profileType.equalsIgnoreCase("Tannery"))
                category = "leather";

            else if (profileType.equalsIgnoreCase("Component Trader") || profileType.equalsIgnoreCase("Component Manufacturer"))
                category = "components";
        }
        return category;
    }


    public static String generateObjectId(int size, String userId, String threadId) {
        if (size == 0)
            throw new RuntimeException("Length can't be zero");

        if (!Methods.valid(userId))
            throw new RuntimeException("user id is null or empty");

        if (!Methods.valid(threadId))
            throw new RuntimeException("thread id is null or empty");


        String objectId = UUID.randomUUID().toString();
        return objectId;
    }


    public static int getSampleSizeByFileSize(long fileSize) {
        if (fileSize >= 1000000 * 3) // > 3MB
            return 8;

        else if (fileSize >= 1000000 * 1) // > 1 MB
            return 4;
        else if (fileSize >= 1000000 / 2) // > 500 KB
            return 2;

        return 1;
    }


    public static Bitmap decodeFromBase64ToBitmap(String encodedImage, ImageView imageView) {
        if (Methods.valid(encodedImage)) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedBitmap);

            return decodedBitmap;
        }
        return null;
    }

    public static Bitmap decodeFromBase64ToBitmap(String encodedImage) {
        if (Methods.valid(encodedImage)) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedBitmap;
        }
        return null;
    }


    public static boolean isValidList(List list) {
        return list != null && !list.isEmpty();
    }

    public static boolean isNewsTutorialShow(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_NEWS_TUTORIAL_SHOW", false);
    }

    public static boolean setNewsTutorialShow(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_NEWS_TUTORIAL_SHOW", showState).commit();
    }


    public static boolean isHindiNews(Context mContext) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.getBoolean("IS_HINDI", false);
    }

    public static boolean setHindiNews(Context mContext, boolean showState) {
        SharedPreferences preferences = getPreferances(mContext);
        return preferences.edit().putBoolean("IS_HINDI", showState).commit();
    }


    public static Bitmap takeScreenshot(View view) {

        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache();
    }

    public static File saveBitmap(Bitmap bitmap) {

        File imagePath = null;
        try {
            imagePath = new File(android.os.Environment.getExternalStorageDirectory() + "/ShoeKonnect" + "/screenshot.png");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(imagePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return imagePath;
    }


    public static String formatNavigation(String navigation) {
        return Methods.valid(navigation) ? navigation : "";
    }


}


