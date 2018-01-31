package com.example.ktbffh.wardrobe.presenters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import com.example.ktbffh.wardrobe.Interface.FragmentInteractionListenerPant;
import com.example.ktbffh.wardrobe.Interface.FragmentInteractionListenerShirt;
import com.example.ktbffh.wardrobe.activities.WardrobeActivity;
import com.example.ktbffh.wardrobe.database.DbUtils;
import com.example.ktbffh.wardrobe.fragment.PantsFragment;
import com.example.ktbffh.wardrobe.fragment.ShirtsFragment;
import com.example.ktbffh.wardrobe.model.Combination;
import com.example.ktbffh.wardrobe.model.Favourite;
import com.example.ktbffh.wardrobe.model.Pants;
import com.example.ktbffh.wardrobe.model.Shirt;
import com.example.ktbffh.wardrobe.utils.AppConstant;
import com.example.ktbffh.wardrobe.utils.Utility;
import com.example.ktbffh.wardrobe.view.WardRobeView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ktbffh on 28/01/18.
 */

public class WardrobePresenter  {
    Context context;
    ShirtsFragment shirtsFragment;
    PantsFragment pantsFragment;
    WardRobeView wardRobeView;
    Pants pants;
    Shirt shirt;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    Shirt favShirt;
    Pants favPant;
    DbUtils dbUtils;

    Shirt currentShirt=null;
    Pants currentPant=null;

    int type;

    public WardrobePresenter(Context context, WardRobeView wardRobeView, ShirtsFragment shirtsFragment, PantsFragment pantsFragment, DbUtils dbUtils) {
        this.context = context;
        this.wardRobeView = wardRobeView;
        this.shirtsFragment = shirtsFragment;
        this.pantsFragment = pantsFragment;
        this.dbUtils = dbUtils;
        requestPermission();
    }
    //OnClick Of Suffle
    public void suffle(List<Shirt> shirtList, List<Pants> pantsList) {
        Combination combination = Utility.getRandomCombination(shirtList, pantsList);
        shirtsFragment.setItemPosition(combination.getShirtIndex());
        pantsFragment.setItemPosition(combination.getPantIndex());
    }
    //OnClick Of Fav
    public void getFavPicked() {
        shirtsFragment.OnFavClicked();
        pantsFragment.OnFavClicked();
        if (favPant != null && favShirt != null) {
            wardRobeView.changeFavIcon(true);
            final Favourite favourite = new Favourite();
            favourite.setPant_id(favPant.getId());
            favourite.setShirt_id(favShirt.getId());
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    dbUtils.addFav(favourite);
                }
            });
        }
    }
    public void OnFavPant(Pants pant) {
        favPant = pant;
    }

    public void OnFavShirt(Shirt shirt) {
        favShirt = shirt;
    }

    //OnScrolling Pants
    public void checkFavouriteForPant(Pants pants) {
        currentPant = pants;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (currentShirt != null && currentPant!=null && dbUtils.checkFavourite(currentShirt, currentPant)) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            wardRobeView.changeFavIcon(true);
                        }
                    });

                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            wardRobeView.changeFavIcon(false);
                        }
                    });
                }
            }
        });

    }
    //OnScrolling Shirts
    public void checkFavouriteForShirt(Shirt shirt) {
        currentShirt = shirt;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (currentPant != null && currentShirt!=null && dbUtils.checkFavourite(currentShirt, currentPant)) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            wardRobeView.changeFavIcon(true);
                        }
                    });
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            wardRobeView.changeFavIcon(false);
                        }
                    });
                }
            }
        });
    }

    public void selectImage(int type) {
        this.type = type;
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(wardRobeView.getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(wardRobeView.getContext());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        ((WardrobeActivity) wardRobeView.getContext()).startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((WardrobeActivity) wardRobeView.getContext()).startActivityForResult(intent, REQUEST_CAMERA);
    }


    public void onCaptureImageResult(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Random rand = new Random();
            int n = rand.nextInt(50) + 1;
            Bitmap yourImage = extras.getParcelable("data");
            String path = saveToInternalStorage(yourImage, n + "");
            Log.d("Insert: ", "Inserting ..");
            if (type == AppConstant.Shirt_Type) {
                List<Shirt> upDatedShirtList = new ArrayList<>();
                final Shirt shirt = new Shirt();
                shirt.setImage(path);
                shirt.setName(n + "");
                upDatedShirtList.add(shirt);
                shirtsFragment.showOrUpdateRecyclerView(upDatedShirtList);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        dbUtils.addShirt(shirt);
                    }
                });
            } else {
                List<Pants> pants = new ArrayList<>();
                final Pants pant = new Pants();
                pant.setImage(path);
                pant.setName(n + "");
                pants.add(pant);
                pantsFragment.showOrUpdateRecyclerView(pants);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        dbUtils.addPaint(pant);
                    }
                });
            }
        }

    }

    @SuppressWarnings("deprecation")
    public void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                Random rand = new Random();
                int n = rand.nextInt(50) + 1;
                bm = MediaStore.Images.Media.getBitmap(wardRobeView.getContext().getContentResolver(), data.getData());
                String path = saveToInternalStorage(bm, n + "");
                if (type == AppConstant.Shirt_Type) {
                    List<Shirt> upDatedShirtList = new ArrayList<>();
                    final Shirt shirt = new Shirt();
                    shirt.setImage(path);
                    shirt.setName(n + "");
                    upDatedShirtList.add(shirt);
                    shirtsFragment.showOrUpdateRecyclerView(upDatedShirtList);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            dbUtils.addShirt(shirt);
                            //TODO your background code
                        }
                    });


                } else {
                    List<Pants> pants = new ArrayList<>();
                    final Pants pant = new Pants();
                    pant.setImage(path);
                    pant.setName(n + "");
                    pants.add(pant);
                    pantsFragment.showOrUpdateRecyclerView(pants);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            dbUtils.addPaint(pant);
                            //TODO your background code
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    private String saveToInternalStorage(Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(wardRobeView.getContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public void requestPermission() {
        try {
            Dexter.checkPermissions(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                }
            }, getPermissionNames());
        } catch (Exception e)

        {
            e.printStackTrace();
        }
    }

    private String[] getPermissionNames() {
        ArrayList<String> result = new ArrayList<>();

        try {
            PackageManager pm = wardRobeView.getContext().getPackageManager();
            PackageInfo info = wardRobeView.getContext().getPackageManager().getPackageInfo(wardRobeView.getContext().getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    PermissionInfo pi = pm.getPermissionInfo(p, 0);
                    if (pi != null) {
                        if (pi.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS) {
                            result.add(p);
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result.toArray(new String[0]);
    }


}
