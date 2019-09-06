package myapp.pack;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
//新闻的本地保存方式：文本与图片分开保存
//每一则新闻生成一个文本，若干个图片，文本中提供图片的文件名以供链接
//用一个额外的文件来记录所有的历史记录及顺序

public class NewsHistory {
    // 单例模式，实例化时从文件中读取之前的浏览记录
    private Vector<Mynews> newsList = new Vector<>();
    private HashSet<String> newsVis = new HashSet<>();

    private static String txtDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/txt/";
    private static String picDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/pic/";

    private static NewsHistory newsHistory = null;
    public static synchronized NewsHistory getNewsHistory() {
        if (newsHistory == null)
            newsHistory = new NewsHistory();
        return newsHistory;
    }

    private NewsHistory() {
        // TODO 从本地读取所有保存的新闻
    }

    public boolean contains(final String s) {
        return newsVis.contains(s);
    }

    public void add(Mynews e) {
        newsList.add(e);
        newsVis.add(e.toString());
        // TODO 将e保存到本地

        //将标题保存至历史记录
        String histDir = txtDir + "history";
        File hist = new File(histDir);
        if(!hist.exists()) {
            try {
                hist.createNewFile();
            } catch (Exception exc) { exc.printStackTrace();  }
        }
        try {
            FileOutputStream fos = new FileOutputStream(histDir);
            for (Mynews t : newsList)
                fos.write((t.toString()+"\n").getBytes());
            fos.close();
        } catch (Exception exc) { exc.printStackTrace(); }


        //保存文本
        String savedTxtDir = txtDir + e.toString().hashCode();
        try {
            FileOutputStream fos = new FileOutputStream(savedTxtDir);
            fos.write(e.getArticle().getText().toString().getBytes());
            fos.close();
        } catch (Exception exc) { exc.printStackTrace(); }

        //保存图片
        String savedPicDir = picDir + e.toString().hashCode() + "/";

    }

}
