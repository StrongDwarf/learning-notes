# Android文件存储  

在开发应用程序时,文件存储是必不可少要用到的功能,Android开发中同样如此,经过查阅网络上的资料后,对Android中文件存储方面的知识总结如下:
  [内存,内部存储,外部存储的区别](#内存,内部存储,外部存储的区别)  
  [内部存储](#内部存储)
  [外部存储](#外部存储)
  [访问内部存储和外部存储的API](#访问内部存储和外部存储的API)
  [文件存储中几点容易让人迷惑的地方](#文件存储中几点容易让人迷惑的地方)
  [一个简便的文件操作工具类](#一个简便的文件操作工具类)


  需要注意的是,因为考虑到现在android低版本(android6.0和6.0之前的版本)的使用率已经很低了,所以本文中的内容默认是对android高版本适用的,未考虑低版本的兼容性。

### 内存,内部存储,外部存储的区别

内存,内部存储,外部存储的区别如下: 

 * 内存: 英文名memory,内存是计算机中重要的部件之一,它是与CPU进行沟通的桥梁,计算机中所有的程序都是在内存中运行的,它是用于计算机运行时的,不是用来存储数据的
 * 内部存储,外部存储: 在Android手机中,用来存储数据的有两部分,机身存储和外置sd卡存储, 对于机身存储,其中一部分是内部存储,还有一部分是外部存储,对于sd卡存储,都是外部存储。

### 内部存储  

内部存储位于系统中很特殊的一个位置,如果你想将文件存储于内部存储中,那么文件默认只能被你的应用访问到,且一个应用创建的所有文件都在和应用包名相同的目录下。也就是说应用创建于内部存储的文件,与这个应用是关联起来的。当一个应用卸载之后，内部存储中的这些文件也会被删除,从技术上来讲如果你在创建内部存储文件的时候讲文件属性设为可读,其他app能够访问自己应用的数据,前提是他知道你这个应用的包名,如果一个文件的属性是私有的(private),那么即使知道包名其他应用也无法访问。内部存储空间十分有限，因而显得可贵，另外，它也是系统本身和系统应用程序主要的数据存储所在地，一旦内部存储空间耗尽，手机就无法使用了。内部存储有两个重要的目录如下:
 * 1,app文件夹,没有root的手机不能打开该文件夹。app文件夹里存放着我们所有安装的app的apk文件夹,当我们调试一个app的时候，可以看到控制台输出的内容，有一项是uploading...,就是上传我们的apk到这个文件夹
 * 2,data文件夹,这个文件夹里边都是包名,打开这些包名后我们会看到这样一些文件:
   * data/data/包名/shared_prefs
   * data/data/包名/database
   * data/data/包名/files
   * data/data/包名/cache
我们在使用SharePerference的时候,将数据持久化存储到本地,其实就是存在data/data/包名/shared_prefs文件夹里的xml文件里,我们的app里边的数据库文件就存储在database文件夹中,还有我们的普通数据存储在files中,缓存在cache文件夹中。

### 外部存储

外部存储就是我们看到的storage文件夹,在android低版本中,外部存储是sd卡中的存储空间,不过现在,在android手机内置存储空间中,分内部存储空间和外部存储空间,同时外置sd卡也属于外部存储空间,所以现在的版本中,外部存储有两部分。我们可以用代码打印一下看看:

``` java
File[] files;
if(Bulid.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
    files = getExternalFilesDirs(Environment.MEDIA_MOUNTED)
    for(File file:files){
        Log.i("file",file);
    }
}

/*输出结果
手机内置存储空间中的外部存储  /storage/emulated/0/Android/data/packname/files/mounted
外置SD卡中的外部存储 /storage/B3E4-1711/Android/data/packname/files/mounted
*/
```

### 访问内部存储和外部存储的API

#### 访问内部存储  

1,获取内部存储的根路径:Environment.getDataDirectory() = /data  
2,获取某个应用在内部存储中的files路径:getFilesDir().getAbsolutePath() = /data/user/0/packname/files  
3,获取某个应用在内部存储中的cache路径:getCacheDir().getAbsolutePath() = /data/user/0/packname/cache  
4,获取某个应用在内部存储中的自定义路径:getDir("myFile",MODE_PRIVATE).getAbsolutePath() = /data/user/0/packname/app_myFile  

#### 访问外部存储

1,获取外部存储的根路径:Environment.getExternalStorageDirectory().getAbsolutePath() = /storage/emulated/0  
2,获取外部存储的根路径:Environment.getExternalStoragePublicDirectory("").getAbsolutePath() = /storage/emulated/0  
3,获取某个应用在外部存储中的files路径:getExternalFilesDir("").getAbsolutePath() = /storage/emulated/0/Android/data/packname/files  
4,获取某个应用在外部存储中的cache路径:getExternalCacheDir().getAbsolutePath() = /stoage/emulated/0/Android/data/packname/cache  

#### 其他API

Environment.getDownloadCacheDirectory() = /cache
Environment.getRootDirectory() = /system

### 文件存储中几点容易让人迷惑的地方

#### 1,cache文件夹和files文件夹中存放的文件有什么不同?

通过上面的API,可能很多人都会疑惑,不管是内部存储还是外部存储,都有对应的API访问相同文件夹中的cache和files文件夹,在实际开发中我们应该如何使用这两个文件夹呢?即两个文件夹中应该存放哪些文件呢?从名字可以知道cache中存放缓存数据包括图片缓存,数据缓存等,files中存放普通数据(log数据,json型数据)。

#### 2,当用户清除应用缓存,清除应用数据时候删除的都是什么数据?

1,当用户清除应用缓存时,清除的是RAM(内存)中的数据,这些数据都是程序运行时读入内存中的数据。
2,当用户清除应用数据时,删除的是内部存储和外部存储中对应包名中的数据,目录方面是/data/user/0/packname/ 和 /storage/emulated/0/Android/data/packname/ 两个目录。

### 一个简便的文件操作工具类

下面是一个封装了常用文件操作的工具类:

``` java
public class SDCardHelper{

    //判断SD卡是否被挂载
    public static boolean isSDCradMounted(){
        return Environment.getExternalStorageState().equals(Environment.MODIA_MOUNTED);
    }

    //获取SD卡的根目录
    public static String getSDCardBaseDir(){
        if(isSDCardMounted()){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    //获取SD卡的完整空间大小,返回MB
    public static long getSDCardSize(){
        if(isSDCardMounted()){
            StatFS fs = new StatFs(getSDCardBaseDir());
            long count = fs.getBlockCountLong();
            long size = fs.getBlockSizeLong();
            return count * size/1024/1024
        }
    }

    //获取SD卡的剩余空间大小
    public static long getSDCardFreeSize(){
        if(isSDCardMounted()){
            StatFs fs = new StatFs(getSDCardBaseDir());
            long count = fs.getFreeBlocksLong();
            long size = fs.getBlockSizeLong();
            return count * size/1024/1024;
        }
        return 0;
    }

    //获取SD卡的可用空间大小
    public static long getSDCardAvailableSize(){
        if(isSDCardMounted()){
            StatFs fs = new StatFs(getSDCardBaseDir());
            long count = fs.getAvailableBlocksLong();
            long size = fs.getBlockSizeLong();
            return count*size/1024/1024;
        }
        return 0;
    }

    //往SD卡的公有目录下保存文件
    public static boolean saveFileToSDCardPublicDir(byte[] data,String type,String fileName){
        BufferedOutputStream bos = null;
        if(isSDCardMounted()){
            File file = Environment.getExternalStoragePublicDirectory(type);
            try{
                bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
                bos.write(data);
                bos.flush();
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    bos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //往SD卡的自定义目录下保存文件
    public static boolean saveFileToSDCardCustomDir(byte[] data,String dir,String fileName){
        BufferedOutputStream bos = null;
        if(isSDCardMounted()){
            File file = new File(getSDCardBaseDir() + File.separator + dir);
            if(!file.exists()){
                file.mkdirs(); //递归创建自定义目录
            }
            try{
                bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
                bos.write(data);
                bos.flush();
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    bos.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return false
    }

    //往SD卡的私有Files目录下保存文件
    public static boolean saveFileToSDCardPrivateFilesDir(byte[] data,String type,String fileName,Context context){
        BufferedOutputStream bos = null;
        if(isSDCardMounted()){
            File file = context.getExternalFilesDir(type);
            try{
                bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
                bos.write(data);
                bos.flush();
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    bos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //往SD卡的私有Cache目录下保存文件
    public static boolean saveFileToSDCardPrivateCacheDir(byte[] data,String fileName,Context context){
        BufferedOutputStream bos = null;
        if(isSDCardMounted()){
            File file = context.getExternalCacheDir();
            try{
                bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
                bos.write(data);
                bos.flush();
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    bos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //保存bitmap图片到SDCard的私有Cache目录
    public static boolean saveBitmapToSDCardPrivateCacheDir(Bitmap bitmap,String fileName,Context context){
        if(isSDCardMounted()){
            BufferedOutputStream bos = null;
            //获取私有的Cache缓存目录
            File file = context.getExternalCacheDir();
            try{
                bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
                if(fileName != null && (fileName.contains(".png")||fileName.contains(".PNG"))){
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                }else{
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                }
                bos.flush();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(bos != null){
                    try{
                        bos.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }

    //从SD卡获取文件
    public static byte[] loadFileFromSDCard(String fileDir){
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
           bis = new BufferedInputStream(new FileInputStream(new File(fileDir)));
            byte[] buffer = new byte[8*1024];
            int c = 0;
            while((c = bis.read(buffer)) != -1){
                baos.write(buffer,0,c);
                baos.flush();
            }
            return baos.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                baos.close();
                bis.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    //从SDCard中寻找指定目录下的文件,返回bitmap
    public Bitmap loadBitmapFromSDCard(String filePath){
        byte[] data = loadFileFromSDCard(filePath);
        if(data != null){
            Bitmap bm = BitmapFactory.decodeByteArray(data,0,data.length);
            if(bm != null){
                return bm;
            }
        }
        return null;
    }

    //获取SD卡公有目录的路径
    public static String getSDCardPublicDir(String type){
        return Environment.getExternalStoragePublicDirectory(type).toString();
    }

    //获取SD卡私有Cache目录的路径
    public static String getSDCardPrivateCacheDir(Context cntext){
        return context.getExternalCacheDir().getAbsolutePath();
    }

    //获取SD卡私有Files目录的路径
    public static String getSDCardPrivateFilesDir(Context context,String type){
        return context.getExternalFilesDir(type).getAbsolutePath();
    }

    public static boolean isFileExist(String filePath){
        File file = new File(filePath);
        return file.isFile();
    }

    //从sdcard中删除文件
    public static boolean removeFileFromSDCard(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            try{
                file.delete();
                return true;
            }catch(Exception e){
                return false;
            }
        }else{
            return false;
        }
    }
}
```

文章参考：
  [彻底了解android中的内部存储于外部存储](https://www.cnblogs.com/jingmo0319/p/5586559.html)
  [彻底搞懂Android文件存储--内部存储,外部存储以及各种存储路径解惑](https://blog.csdn.net/u010937230/article/details/73303034)
  [Android数据存储的五种方法汇总](https://www.cnblogs.com/chengzhengfu/p/4582515.html)
  [Android内部存储和外部存储解析](https://www.jianshu.com/p/31670805f32a)
  [手机内部存储空间](https://zhidao.baidu.com/question/134594722405280085.html)
  [Android中的文件操作的9个实例](https://blog.csdn.net/rodulf/article/details/50950233)

