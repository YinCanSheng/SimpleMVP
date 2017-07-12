package ch.erp.management.mvp.utils.General;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具
 * <p>
 * 压缩/保存/旋转/转换格式等
 *
 * @author ChenHong
 */
public class MPictureUtils {
    /**
     * SD路径
     */
    public final static String SDCARD_MNT = "/mnt/sdcard";
    /**SD路径*/
    public final static String SDCARD = "/sdcard";
    /**
     * 开辟一个存放图片的路径
     */
    private static final File DIR = new File(
            Environment.getExternalStorageDirectory(), "smartCar");
    /**
     * 请求相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    /**
     * 请求裁剪
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;
    /**
     * 从拍照进入涂鸦
     */
    public static final int REQUEST_CODE_GETIMAGE_BYTUYA_FROME_CAMERA = 3;
    /**
     * 从相册进入涂鸦
     */
    public static final int REQUEST_CODE_GETIMAGE_BYTUYA_FROME_CROP = 4;
    /**
     * 从全屏预览编辑进入涂鸦
     */
    public static final int REQUEST_CODE_GETIMAGE_BYTUYA_FROME_FULL_BORSWER = 5;

    /**
     * 写图片文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @throws IOException
     */
    public static void saveImage(Context context, String fileName, Bitmap bitmap)
            throws IOException {
        saveImage(context, fileName, bitmap, 100);
    }

    public static void saveImage(Context context, String fileName,
                                 Bitmap bitmap, int quality) throws IOException {
        if (bitmap == null || fileName == null || context == null)
            return;

        FileOutputStream fos = context.openFileOutput(fileName,
                Context.MODE_PRIVATE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, quality, stream);
        byte[] bytes = stream.toByteArray();
        fos.write(bytes);
        fos.close();
    }

    /**
     * 把图片转换为字节码字符串/压缩
     *
     * @param bit
     * @return
     */
    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(CompressFormat.JPEG, 40, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    /**
     * 字符转文件
     */
    public static File byte2File(byte[] buf, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 把Bitmap转成字节字符串/不压缩
     *
     * @param bitmap
     * @return
     */
    public String bitmaptoString(Bitmap bitmap) {

        /**不压缩*/
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 写图片文件到SD卡
     *
     * @throws IOException
     */
    public static void saveImageToSD(Context ctx, String filePath,
                                     Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            File file = new File(filePath.substring(0,
                    filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bitmap.compress(CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            if (ctx != null) {
                scanPhoto(ctx, filePath);
            }
        }
    }

    public static void saveBackgroundImage(Context ctx, String filePath,
                                           Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            File file = new File(filePath.substring(0,
                    filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bitmap.compress(CompressFormat.PNG, quality, bos);
            bos.flush();
            bos.close();
            if (ctx != null) {
                scanPhoto(ctx, filePath);
            }
        }
    }

    /**
     * 让Gallery上能马上看到该图片
     */
    private static void scanPhoto(Context ctx, String imgFileName) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取bitmap
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getBitmap(Context context, String fileName) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度 获取从相册中选中图片的角度
     */
    public static int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
        }
        return degree;
    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap getBitmapByFile(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取照相机使用的目录
     *
     * @return
     */
    public static String getCamerPath() {
        return Environment.getExternalStorageDirectory() + File.separator
                + "FounderNews" + File.separator;
    }

    /**
     * 读取图片的旋转的角度，还是三星的问题，需要根据图片的旋转角度正确显示
     * <p>
     * 图片绝对路径
     *
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路径
     *
     * @return
     */
    public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String filePath = null;

        String mUriString = mUri.toString();
        mUriString = Uri.decode(mUriString);

        String pre1 = "file://" + SDCARD + File.separator;
        String pre2 = "file://" + SDCARD_MNT + File.separator;

        if (mUriString.startsWith(pre1)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre1.length());
        } else if (mUriString.startsWith(pre2)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre2.length());
        }
        return filePath;
    }

    /**
     * 通过uri获取文件的绝对路径
     *
     * @param uri
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String imagePath = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery(uri, proj, // Which columns to
                // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }

        return imagePath;
    }

    /**
     * 获取图片缩略图 只有Android2.1以上版本支持
     *
     * @param imgName
     * @param kind    MediaStore.Images.Thumbnails.MICRO_KIND
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Bitmap loadImgThumbnail(Activity context, String imgName,
                                          int kind) {
        Bitmap bitmap = null;

        String[] proj = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME};

        Cursor cursor = context.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,
                MediaStore.Images.Media.DISPLAY_NAME + "='" + imgName + "'",
                null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            ContentResolver crThumb = context.getContentResolver();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bitmap = MethodsCompatUtils.getThumbnail(crThumb, cursor.getInt(0),
                    kind, options);
        }
        return bitmap;
    }

    public static Bitmap loadImgThumbnail(String filePath, int w, int h) {
        Bitmap bitmap = getBitmapByPath(filePath);
        return zoomBitmap(bitmap, w, h, 0);
    }

    /**
     * 获取SD卡中最新图片路径
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getLatestImage(Activity context) {
        String latestImage = null;
        String[] items = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA};
        @SuppressWarnings({})
        Cursor cursor = context.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, items, null,
                null, MediaStore.Images.Media._ID + " desc");

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (cursor.moveToFirst(); !cursor.isAfterLast(); ) {
                latestImage = cursor.getString(1);
                break;
            }
        }

        return latestImage;
    }

    /**
     * 计算缩放图片的宽高
     *
     * @param img_size
     * @param square_size
     * @return
     */
    public static int[] scaleImageSize(int[] img_size, int square_size) {
        if (img_size[0] <= square_size && img_size[1] <= square_size)
            return img_size;
        double ratio = square_size
                / (double) Math.max(img_size[0], img_size[1]);
        return new int[]{(int) (img_size[0] * ratio),
                (int) (img_size[1] * ratio)};
    }

    /**
     * 创建缩略图
     *
     * @param context
     * @param largeImagePath 原始大图路径
     * @param thumbfilePath  输出缩略图路径
     * @param square_size    输出图片宽度
     * @param quality        输出图片质量
     * @throws IOException
     */
    public static void createImageThumbnail(Context context,
                                            String largeImagePath, String thumbfilePath, int square_size,
                                            int quality, int rotate) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(largeImagePath, options);
        options.inSampleSize = caculateInSampleSize(options, square_size,
                square_size);

        // 使用获得到的InSampleSize再次解析图片
        options.inJustDecodeBounds = false;
        // 原始图片bitmap
        Bitmap cur_bitmap = getBitmapByPath(largeImagePath, options);

        if (cur_bitmap == null)
            return;

        // 原始图片的高宽
        // int[] cur_img_size = new int[] { cur_bitmap.getWidth(),
        // cur_bitmap.getHeight() };
        // 计算原始图片缩放后的宽高
        // int[] new_img_size = scaleImageSize(cur_img_size, square_size);
        // 生成缩放后的bitmap
        // Bitmap thb_bitmap = zoomBitmap(cur_bitmap, new_img_size[0],
        // new_img_size[1],rotate);
        // 生成缩放后的图片文件
        saveImageToSD(null, thumbfilePath, cur_bitmap, quality);
    }

    public static int caculateInSampleSize(BitmapFactory.Options options,
                                           int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height;
            final int halfWidth = width;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, double w, double h,
                                    int rotate) {
        Bitmap newbmp = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidht = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidht, scaleHeight);
            matrix.postRotate(rotate);
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
        }
        return newbmp;
    }

    /**
     * 将图片根据平方根进行压缩
     *
     * @param image
     * @param maxSize
     * @param quality
     * @return
     */
    public static Bitmap comp(Context context, Bitmap image, int maxSize,
                              int quality, int rotate) {
//		Log.e("ImageUtils", image.getWidth() + "--" + image.getHeight());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, quality, baos);
        double mid = 1.0f * baos.toByteArray().length / 1024 / 4;
        Log.i("ImageUtils", "imageSize" + image.getWidth() + image.getHeight());
        Log.i("ImageUtils", mid + "mid" + maxSize + "maxSize");
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            Log.i("ImageUtils",
                    "imageSize_big" + image.getWidth() + image.getHeight());
            // 获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            // 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            if (MScreenParaUtils.getScreenWidth(context) <= image.getWidth()
                    / Math.sqrt(i)
                    && MScreenParaUtils.getScreenWidth(context) <= image
                    .getHeight() / Math.sqrt(i)) {
                image = zoomBitmap(image, image.getWidth() / Math.sqrt(i),
                        image.getHeight() / Math.sqrt(i), rotate);
            } else {
                if (rotate == 0 || rotate == 180) {
                    image = zoomBitmap(image,
                            MScreenParaUtils.getScreenWidth(context), 1.0f
                                    * image.getHeight() / image.getWidth()
                                    * MScreenParaUtils.getScreenWidth(context),
                            rotate);
                } else {
                    image = zoomBitmap(image,
                            1.0f * image.getWidth() / image.getHeight()
                                    * MScreenParaUtils.getScreenWidth(context),
                            MScreenParaUtils.getScreenWidth(context), rotate);
                }
            }
        }
        Log.i("ImageUtils",
                "imageSize_small" + image.getWidth() + image.getHeight()
                        + "rotate--" + rotate);
        return image;
    }

    @SuppressLint("NewApi")
    public static Bitmap compSmall(Context context, Bitmap image, int maxSize,
                                   int quality, int rotate) {
        Log.e("ImageUtils", image.getWidth() + "--" + image.getHeight());
        double mid = 1.0f * image.getByteCount() / 1024 / 4;
        Log.i("ImageUtils", "imageSize" + image.getWidth() + image.getHeight());
        Log.i("ImageUtils",
                mid + "mid" + maxSize + "maxSize" + 1.0f * image.getByteCount()
                        / 1024 / 4);

        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩

        if (mid > maxSize) {
            Log.i("ImageUtils",
                    "imageSize_big" + image.getWidth() + image.getHeight());
            // 获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            // 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            image = zoomBitmap(image, image.getWidth() / Math.sqrt(i),
                    image.getHeight() / Math.sqrt(i), rotate);
        }
        Log.i("ImageUtils",
                "imageSize_small" + image.getWidth() + image.getHeight());
        return image;
    }

    public static Bitmap comp(byte[] data, int maxSize, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap oldBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        oldBitmap.compress(CompressFormat.JPEG, quality, baos);
        double mid = 1.0f * baos.toByteArray().length / 1024;
        Log.i("ImageUtils",
                "imageSize" + oldBitmap.getWidth() + oldBitmap.getHeight());
        Log.i("ImageUtils", mid + "mid" + maxSize + "maxSize");
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            Log.i("ImageUtils", "imageSize_big" + oldBitmap.getWidth()
                    + oldBitmap.getHeight());
            // 获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            // 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            oldBitmap = zoomImage(oldBitmap,
                    oldBitmap.getWidth() / Math.sqrt(i), oldBitmap.getHeight()
                            / Math.sqrt(i));
        }
        Log.i("ImageUtils", "imageSize_small" + oldBitmap.getWidth()
                + oldBitmap.getHeight());
        return oldBitmap;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap) {
        // 获取这个图片的宽和高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 定义预转换成的图片的宽度和高度
        int newWidth = 200;
        int newHeight = 200;
        // 计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        // 旋转图片 动作
        // matrix.postRotate(45);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return resizedBitmap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * 取得缩略图
     *
     * @param imagePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getImageThumbnail(String imagePath, int width,
                                           int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 获得圆角图片的方法
     *
     * @param bitmap
     * @param roundPx 一般设成14
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 获取图片类型
     *
     * @param file
     * @return
     */
    public static String getImageType(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            String type = getImageType(in);
            return type;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 获取图片的类型信息
     *
     * @param in
     * @return
     * @see #getImageType(byte[])
     */
    public static String getImageType(InputStream in) {
        if (in == null) {
            return null;
        }
        try {
            byte[] bytes = new byte[8];
            in.read(bytes);
            return getImageType(bytes);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取图片的类型信息
     *
     * @param bytes 2~8 byte at beginning of the image file
     * @return image mimetype or null if the file is not image
     */
    public static String getImageType(byte[] bytes) {
        if (isJPEG(bytes)) {
            return "image/jpeg";
        }
        if (isGIF(bytes)) {
            return "image/gif";
        }
        if (isPNG(bytes)) {
            return "image/png";
        }
        if (isBMP(bytes)) {
            return "application/x-bmp";
        }
        return null;
    }

    private static boolean isJPEG(byte[] b) {
        if (b.length < 2) {
            return false;
        }
        return (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
    }

    private static boolean isGIF(byte[] b) {
        if (b.length < 6) {
            return false;
        }
        return b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8'
                && (b[4] == '7' || b[4] == '9') && b[5] == 'a';
    }

    private static boolean isPNG(byte[] b) {
        if (b.length < 8) {
            return false;
        }
        return (b[0] == (byte) 137 && b[1] == (byte) 80 && b[2] == (byte) 78
                && b[3] == (byte) 71 && b[4] == (byte) 13 && b[5] == (byte) 10
                && b[6] == (byte) 26 && b[7] == (byte) 10);
    }

    private static boolean isBMP(byte[] b) {
        if (b.length < 2) {
            return false;
        }
        return (b[0] == 0x42) && (b[1] == 0x4d);
    }

    /**
     * 获取图片路径 2014年8月12日
     *
     * @param uri
     * @return E-mail:mr.huangwenwei@gmail.com
     */
    public static String getImagePath(Uri uri, Context context) {

        String[] projection = {MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columIndex = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            String ImagePath = cursor.getString(columIndex);
            cursor.close();
            return ImagePath;
        }

        return uri.toString().replace("file://", "");
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static Bitmap bitmap = null;

    /**
     * 将bitmap作为图片保存起来（在指定路径下）
     *
     * @param bmp
     * @param filename
     * @return
     */
    public static File saveBitmap2file(Bitmap bmp, String filename, int size) {
        File dir = getAppDir();
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, filename);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, size, fOut);
            fOut.flush();
            fOut.close();
            return file;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("图片转文件", e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("图片转文件", e.getMessage());
        }
        return null;

    }

    /**
     * 创建app文件夹
     */
    private static void createDir() {
        if (!DIR.exists()) {
            DIR.mkdirs();
        }
    }

    /**
     * 获取app文件夹
     *
     * @return
     */
    public static File getAppDir() {
        if (DIR != null && DIR.exists()) {
        } else {
            createDir();
        }
        return DIR;
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null,
                    null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor
                            .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static void bitmapRecyle(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
            System.gc();
            bitmap = null;
        }

    }
}
