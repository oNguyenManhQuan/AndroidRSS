package com.licon.rssfeeds.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.AppData;
import com.licon.rssfeeds.data.constants.IntentData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by FRAMGIA\khairul.alam.licon on 9/3/16.
 */
public class FileUtil {
    private static String getDistinctPdfFileName() {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(AppData.APP_FOLDER_NAME)
                .append("_")
                .append(DateFormatUtil.getCurrentDate())
                .append(AppData.FILE_FORMAT_PDF);

        return fileNameBuilder.toString();
    }

    public static String getFilePath() {
        StringBuilder filepathBuilder = new StringBuilder();
        filepathBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath())
                .append("/")
                .append(AppData.APP_FOLDER_NAME)
                .append("/")
                .append(getDistinctPdfFileName());
        return filepathBuilder.toString();
    }

    public static void createPdfToPrint(final Activity activity, final String input_full_news)
            throws FileNotFoundException, DocumentException {
        AppUtil.createAppFolderIfNotExists(activity);
        final File myFile = new File(getFilePath());
        OutputStream output = new FileOutputStream(myFile);
        Document document = new Document(AppData.PDF_PAGE_DEFAULT_SIZE);
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(input_full_news));
        document.close();

        if (myFile.exists()) {
            String print_title = activity.getString(R.string.pdf_dialog_title_print);
            String print_message = String.format(activity.getString(R.string.pdf_dialog_msg_print),
                    myFile.getAbsolutePath());

            final String action_view = activity.getString(R.string.pdf_dialog_action_view);
            final String action_close = activity.getString(R.string.pdf_dialog_action_close);

            DialogInterface.OnClickListener onOkClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    viewPdfFile(activity, myFile);
                }
            };

            UIUtil.showDialogNotify(activity,
                    print_title,
                    print_message,
                    action_view,
                    action_close,
                    onOkClickListener,
                    UIUtil.getDefaultDismissListener());
        } else {
            UIUtil.showDialogNotify(activity,
                    activity.getString(R.string.text_dialog_title_error),
                    activity.getString(R.string.pdf_dialog_msg_print_error),
                    null,
                    activity.getString(R.string.text_dialog_btn_ok),
                    null,
                    UIUtil.getDefaultDismissListener());
        }
    }

    public static void viewPdfFile(Activity activity, File pdfFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(pdfFile), IntentData.APPLICATION_TYPE_PDF);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            UIUtil.showDialogNotify(activity,
                    activity.getString(R.string.text_dialog_title_error),
                    activity.getString(R.string.text_dialog_msg_no_application),
                    null,
                    activity.getString(R.string.text_dialog_btn_ok),
                    null,
                    UIUtil.getDefaultDismissListener());
        }
    }
}