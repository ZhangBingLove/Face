package cn.ttsx.face.activity;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import cn.ttsx.face.Application;
import cn.ttsx.face.DetecterActivity;
import cn.ttsx.face.MainActivity;
import cn.ttsx.face.R;
import cn.ttsx.face.RegisterActivity;
import cn.ttsx.face.adapter.MoveListAdapter;
import cn.ttsx.face.adapter.PersonAdapter;
import cn.ttsx.face.base.BaseActivity;
import cn.ttsx.face.dao.DaoSession;
import cn.ttsx.face.dao.Face;
import cn.ttsx.face.dao.FaceDao;
import cn.ttsx.face.dao.Move;
import cn.ttsx.face.dao.MoveDao;
import cn.ttsx.face.view.TopBar;

/**
 * 人员管理
 *
 * @author zhangbing
 * @Description: (用一句话描述该类作用)
 * @CreateDate: 2018/1/4 15:42
 * @email 314835006@qq.com
 * @UpdateUser: zhangbing
 * @UpdateDate: 2018/1/4 15:42
 * @UpdateRemark:
 */
public class PersonActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvAdd;
    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_IMAGE_OP = 2;
    private static final int REQUEST_CODE_OP = 3;
    private TopBar topBar;
    private RecyclerView mRecycleView;
    private PersonAdapter mMoveListAdapter;
    private FaceDao faceDao;
    private LinearLayout llEmpty;
    private List<Face> moves;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_person;

    }

    @Override
    protected void initView() {
        DaoSession daoSession = ((Application) PersonActivity.this.getApplicationContext()).getDaoSession();
        faceDao = daoSession.getFaceDao();

        tvAdd = findViewById(R.id.tv_add);

        topBar = findViewById(R.id.topbar);
        topBar.setTopBarTitle("名单管理");
        topBar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llEmpty = findViewById(R.id.ll_emtpy);
        mRecycleView = (RecyclerView) findViewById(R.id.recyclerview);

        SmartRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000/*,false*/);//传入false表示加载失败
            }
        });
        refreshLayout.setEnableLoadmore(false);
//        moves = faceDao.loadAll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mMoveListAdapter = new PersonAdapter(R.layout.item_person, null);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mMoveListAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecycleView);
        mRecycleView.setAdapter(mMoveListAdapter);

        mMoveListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

//                Intent intent = new Intent(PersonActivity.this, MoveDetailsActivity.class);
//
//                intent.putExtra("moveName", ((Move) adapter.getItem(position)).getMoveName());
//
//                startActivity(intent);

                showToast("敬请期待");


            }
        });

        // 开启滑动删除
        mMoveListAdapter.enableSwipeItem();
        mMoveListAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
//                faceDao.delete(moves.get(pos));
                Log.e("haha111", pos + "");
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                faceDao.delete(moves.get(pos));
                List<Face> faces = faceDao.loadAll();

                if (!faces.isEmpty()) {
                    llEmpty.setVisibility(View.GONE);
                    mRecycleView.setVisibility(View.VISIBLE);
                    mMoveListAdapter.setNewData(moves);

                } else {
                    llEmpty.setVisibility(View.VISIBLE);
                    mRecycleView.setVisibility(View.GONE);
                }
                mMoveListAdapter.notifyDataSetChanged();
                Log.e("haha222", pos + "");
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });


        tvAdd.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        moves = faceDao.loadAll();

        if (!moves.isEmpty()) {
            llEmpty.setVisibility(View.GONE);
            mRecycleView.setVisibility(View.VISIBLE);
            mMoveListAdapter.setNewData(moves);

        } else {
            llEmpty.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add: //添加人脸

                new AlertDialog.Builder(this)
                        .setTitle("请选择注册方式")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setItems(new String[]{"打开图片", "拍摄照片"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 1: //照相机
                                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                        ContentValues values = new ContentValues(1);
                                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                        ((Application) (PersonActivity.this.getApplicationContext())).setCaptureImage(uri);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
                                        break;
                                    case 0: //图片
                                        Intent getImageByalbum = new Intent(Intent.ACTION_GET_CONTENT);
                                        getImageByalbum.addCategory(Intent.CATEGORY_OPENABLE);
                                        getImageByalbum.setType("image/jpeg");
                                        startActivityForResult(getImageByalbum, REQUEST_CODE_IMAGE_OP);
                                        break;
                                    default:
                                        ;
                                }
                            }
                        })
                        .show();
                break;
        }
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
            Uri mPath = ((Application) (PersonActivity.this.getApplicationContext())).getCaptureImage();
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
        Intent it = new Intent(PersonActivity.this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", file);
        it.putExtras(bundle);
        startActivityForResult(it, REQUEST_CODE_OP);
    }

    private void startDetector(int camera) {
        Intent it = new Intent(PersonActivity.this, DetecterActivity.class);
        it.putExtra("Camera", camera);
        startActivityForResult(it, REQUEST_CODE_OP);
    }


}
