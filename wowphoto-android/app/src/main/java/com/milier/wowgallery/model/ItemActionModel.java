package com.milier.wowgallery.model;

/*public class ItemActionModel {

    *//**
     * 图片收藏或取消
     *//*
    public static void pictureAction(long atlasId, final ImageType imageType) {
        final ImageService restService = RetrofitFactory.createRestService(ImageService.class);
        RxUtil.wrapRestCall(restService.storeAtlas(atlasId,imageType)).subscribe(new Action1<String>() {
            @Override
            public void call(String aVoid) {
                //给出结果
//                listener.success();
                if(imageType == ImageType.STORE){
                    Toaster.toast(MyApplication.getInstance().getResources().getString(R.string.like));
                    MilierLog.i("","收藏成功");
                }else{
                    Toaster.toast(MyApplication.getInstance().getResources().getString(R.string.unlike));
                    MilierLog.i("","取消收藏成功");
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
//                listener.fail();
            }
        });
    }
}*/
