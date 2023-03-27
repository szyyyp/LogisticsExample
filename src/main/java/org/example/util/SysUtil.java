package org.example.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/*
    系统运行环境相关参数
    @Author szy
    @Date  2022-7-8
 */
public class SysUtil {

    //获取系统的实际根路径
    public static String getDir(HttpServletRequest req) {
        return getPath(req.getSession().getServletContext()
                .getRealPath("/"));
    }

    public static  String getPath(String Path) {
        StringBuffer sf = new StringBuffer(Path); //

        if(isWin()){
            if (!Path.endsWith("\\")) {
                sf.append("\\");
            }
        }
        else if
        (Path.endsWith("\\")){
            int startIndex = sf.indexOf("\\");
            sf.replace(startIndex,startIndex+1,"");
        }
        else if(!Path.endsWith("/")){
            sf.append("/");
        }
        createDir(Path); //

        return sf.toString();

    }

    private static boolean isWin() {
        String OS = System.getProperty("os.name").toLowerCase();
        if(OS.indexOf("windows")>-1)
            return true;
        else return false;
    }

    public static boolean createDir(String DirName) {
        File f = new File(DirName);
        if (!f.exists()) {
            return f.mkdirs();
        }
        return true;
    }
}
