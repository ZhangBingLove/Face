package cn.ttsx.face.activity;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ttsx.face.Application;
import cn.ttsx.face.DetecterActivity;
import cn.ttsx.face.R;
import cn.ttsx.face.RegisterActivity;
import cn.ttsx.face.adapter.HomePostAdapter;
import cn.ttsx.face.base.BaseActivity;
import cn.ttsx.face.bean.HomeItemBean;
import cn.ttsx.face.dao.DaoSession;
import cn.ttsx.face.dao.Move;
import cn.ttsx.face.dao.MoveDao;
import cn.ttsx.face.view.GridSpacingItemDecoration;
import cn.ttsx.face.view.TopBar;

public class MoveDetailsActivity extends BaseActivity {
    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_IMAGE_OP = 2;
    private static final int REQUEST_CODE_OP = 3;

    @BindView(R.id.topbar)
    TopBar topbar;
    private String moveName;
    private Long moveId;
    private RecyclerView mRecycleView;
    private HomePostAdapter homePostAdpater;
    private MoveDao moveDao;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_move_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        DaoSession daoSession = ((Application) MoveDetailsActivity.this.getApplicationContext()).getDaoSession();
        moveDao = daoSession.getMoveDao();

        moveName = getIntent().getStringExtra("moveName");
        moveId = getIntent().getLongExtra("moveId", 0);
        topbar.setTopBarTitle(moveName);
        topbar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        topbar.setDeleteListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Move move = new Move();
                move.setId(moveId);
                moveDao.delete(move);
                finish();
            }
        });

        ArrayList<HomeItemBean> mList = new ArrayList<>();
        HomeItemBean homeItemBean = new HomeItemBean();

        homeItemBean.setName("名单管理");
        mList.add(homeItemBean);

        homePostAdpater = new HomePostAdapter(R.layout.item_home_list, mList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mRecycleView = (RecyclerView) findViewById(R.id.rv_recycle);
        mRecycleView.addItemDecoration(new GridSpacingItemDecoration(3, 0, 0, false));
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.setAdapter(homePostAdpater);

        homePostAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                startActivity(new Intent(MoveDetailsActivity.this, PersonActivity.class));

            }
        });

        findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(MoveDetailsActivity.this)
                        .setTitle("请选择相机")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setItems(new String[]{"后置相机", "前置相机"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startDetector(which);
                            }
                        })
                        .show();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_OP && resultCode == RESULT_OK) {
            Uri mPath = data.getData();
            String file = getPath(mPath);
            Bitmap bmp = Application.decodeImage(file);
            if (bmp == null || bmp.getWidth() <= 0 || bmp.getHeight() <= 0) {
                Log.e("haha", "error");
            } else {
                Log.i("haha", "bmp [" + bmp.getWidth() + "," + bmp.getHeight());
            }
            startRegister(bmp, file);
        } else if (requestCode == REQUEST_CODE_OP) {
            Log.i("haha", "RESULT =" + resultCode);
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            String path = bundle.getString("imagePath");
            Log.i("haha", "path=" + path);
        } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA && resultCode == RESULT_OK) {
            Uri mPath = ((Application) (MoveDetailsActivity.this.getApplicationContext())).getCaptureImage();
            String file = getPath(mPath);
            Bitmap bmp = Application.decodeImage(file);
            startRegister(bmp, file);
        }
    }

    /**
     * @param uri
     * @return
     */
    private String getPath(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(this, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(this, contentUri, selection, selectionArgs);
                }
            }
        }
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = this.getContentResolver().query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        String end = img_path.substring(img_path.length() - 4);
        if (0 != end.compareToIgnoreCase(".jpg") && 0 != end.compareToIgnoreCase(".png")) {
            return null;
        }
        return img_path;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param mBitmap
     */
    private void startRegister(Bitmap mBitmap, String file) {
        Intent it = new Intent(MoveDetailsActivity.this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", file);
        it.putExtras(bundle);
        startActivityForResult(it, REQUEST_CODE_OP);
    }

    private void startDetector(int camera) {
        Intent it = new Intent(MoveDetailsActivity.this, DetecterActivity.class);
        it.putExtra("Camera", camera);
        startActivityForResult(it, REQUEST_CODE_OP);
    }

}
