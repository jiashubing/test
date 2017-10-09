package forum.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileEcodeUtil {

    public static boolean fileEncrypt(File inFile, File outFile) {
        boolean flag = false;
        try (FileInputStream in = new FileInputStream(inFile);
             FileOutputStream out = new FileOutputStream(outFile);) {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c ^ 0xFFFFFFFF);
            }
            flag = true;
        } catch (Exception e) {
            // do nothing
        }
        return flag;
    }

    public static boolean fileEncrypt(File inFile, File outFile, Integer mykey) {
        if (mykey == null) {
            return false;
        }
        boolean flag = false;
        try (FileInputStream in = new FileInputStream(inFile);
             FileOutputStream out = new FileOutputStream(outFile)) {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c ^ mykey);
            }
            flag = true;
        } catch (Exception e) {
            // do nothing
        }
        return flag;
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return false;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = deleteAllFiles(dirFile);
        if (!flag)
            return false;
        // 删除当前目录
        return dirFile.delete();
    }

    /**
     * 删除目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectoryChildren(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
//        if (!dirFile.exists() || !dirFile.isDirectory()) {
//            return false;
//        }
        return dirFile.exists() && !dirFile.isDirectory() && deleteAllFiles(dirFile);
    }

    private static boolean deleteAllFiles(File dirFile) {
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                // 删除子文件
                if (file.isFile()) {
                    flag = deleteFile(file.getAbsolutePath());
                    if (!flag)
                        break;
                } // 删除子目录
                else {
                    flag = deleteDirectory(file.getAbsolutePath());
                    if (!flag)
                        break;
                }
            }
        }
        return flag;
    }

    /**
     * 把String写入到file中
     */
    public static boolean writeStrToFile(String xml, File outFile) {
        boolean flag = false;
        try (FileOutputStream fos = new FileOutputStream(outFile);
             Writer os = new OutputStreamWriter(fos, "UTF-8")) {
            os.write(xml);
            os.flush();
            flag = true;
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return flag;
    }

    public static String modifyString(File file, String startChar,
                                      String endChar) {
        StringBuilder ans = new StringBuilder();
        String encoding = "UTF-8";
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            // 考虑到编码格式
            try (InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);
                 BufferedReader bufferedReader = new BufferedReader(read)) {
                String lineTxt;
                // 写入新文件
                // FileWriter writer = new FileWriter("D:\\smc_menu123.unl",
                // true);
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String temp = modifyLine(lineTxt, startChar, endChar); // 修改每一行
                    ans.append(temp).append('\n');
                    // writer.write(temp+'\n'); //写入新文件
                }
                // System.out.println("执行成功");
            } catch (Exception e) {
                // do nothing
            }
        } else {
            // System.out.println("找不到指定的文件");
        }
        return ans.toString();
    }

    public static String deleteString(File file, String startChar) {
        StringBuilder ans = new StringBuilder();
        String encoding = "UTF-8";
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            try (InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);
                 BufferedReader bufferedReader = new BufferedReader(read)) {
                String lineTxt;
                // 写入新文件
                // FileWriter writer = new FileWriter("D:\\smc_menu123.unl",
                // true);
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String temp = modifyLineDelete(lineTxt, startChar); // 修改每一行
                    ans.append(temp).append('\n');
                    // writer.write(temp+'\n'); //写入新文件
                }
                // System.out.println("执行成功");
            } catch (Exception e) {
                // System.out.println("读取文件内容出错");
            }
        } else {
            // System.out.println("找不到指定的文件");
        }
        return ans.toString();
    }

    public static String addBlankLine(File file) {
        StringBuilder ans = new StringBuilder();
        String encoding = "UTF-8";
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            try (InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);
                 BufferedReader bufferedReader = new BufferedReader(read)) {
                String lineTxt;
                // 写入新文件
                // FileWriter writer = new FileWriter("D:\\smc_menu123.unl",
                // true);
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    ans.append(lineTxt).append("\n\n");
                    // writer.write(temp+'\n'); //写入新文件
                }
                // System.out.println("执行成功");
            } catch (Exception e) {
                // System.out.println("读取文件内容出错");
            }
        } else {
            // System.out.println("找不到指定的文件");
        }
        return ans.toString();
    }

    public static String deleteBlankLine(File file) {
        StringBuilder ans = new StringBuilder();
        String encoding = "UTF-8";
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            try (InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);
                 BufferedReader bufferedReader = new BufferedReader(read)) {

                String lineTxt;
                // 写入新文件
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    lineTxt += "\n";
                    ans.append(lineTxt.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+",
                            "$1").replaceAll("^((\r\n)|\n)", ""));
                }
            } catch (Exception e) {
                // do nothing
            }
        } else {
            // System.out.println("找不到指定的文件");
        }
        return ans.toString();
    }

    // 修改数据
    private static String modifyLine(String x, String a, String b) {
        StringBuilder sb = new StringBuilder(x);
        sb.insert(0, a);
        sb.append(b);
        x = sb.toString();
        return x;
    }

    // 删除数据
    private static String modifyLineDelete(String x, String a) {
        String y = "";
        int tmp = x.indexOf(a);
        if (tmp != -1) {
            y = x.substring(tmp + a.length(), x.length());
        } else {
            y = x;
        }
        return y;
    }

    /**
     * file 转字 String
     */
    public static String readFileByLines(String fileName) {
        StringBuilder content = new StringBuilder();
        String tempString;

        try (FileInputStream file = new FileInputStream(fileName);
             InputStreamReader inputFileReader = new InputStreamReader(file);
             BufferedReader reader = new BufferedReader(inputFileReader)) {
            // inputFileReader = new InputStreamReader(file, "UTF-8");
            // inputFileReader = new InputStreamReader(file,"GBK");
            // 我的电脑上本地好像只有使用这个才行
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content.append(tempString);
            }
        } catch (Exception e) {
            return null;
        }
        return content.toString();
    }

    /**
     * File to byte[]
     */
    public static byte[] file2byte(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return buffer;
    }

    /**
     * inputStream转File
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        // TODO 这里需要看看啊，把stream作为参数好不好
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try (OutputStream os = new FileOutputStream(file);) {
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * 移动文件
     *
     * @param inName  移动前路径
     * @param outName 移动后路径
     */
    public static boolean fileRemove(String inName, String outName) {
        boolean flag = false;
        try {
            File afile = new File(inName);
            flag = afile.renameTo(new File(outName));
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return flag;
    }

}