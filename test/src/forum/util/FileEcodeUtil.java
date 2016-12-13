package forum.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileEcodeUtil {


	public static boolean fileEncrypt(File inFile, File outFile)
	{
		boolean flag = false;
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(inFile);
			out = new FileOutputStream(outFile);
			int c;
			while ((c = in.read()) != -1)
			{

				out.write(c ^ 0xFFFFFFFF);
			}
			flag = true;
		}
		catch (FileNotFoundException e) {
			try
			{
				if (in != null) {
					in.close();
				}
				if (out != null)
					out.close();
			}
			catch (IOException localIOException1)
			{
			}
		}
		catch (IOException e)
		{
			try
			{
				if (in != null) {
					in.close();
				}
				if (out != null)
					out.close();
			}
			catch (IOException localIOException2)
			{
			}
		}
		finally
		{
			try
			{
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
			catch (IOException localIOException3)
			{
			}
		}
		return flag;
	}
	
	public static boolean fileEncrypt(File inFile, File outFile,Integer mykey)
	{
		if(mykey == null){
			return false;
		}
		boolean flag = false;
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(inFile);
			out = new FileOutputStream(outFile);
			int c;
			while ((c = in.read()) != -1)
			{
				
				out.write(c ^ mykey);
			}
			flag = true;
		}
		catch (FileNotFoundException e) {
			try
			{
				if (in != null) {
					in.close();
				}
				if (out != null)
					out.close();
			}
			catch (IOException localIOException1)
			{
			}
		}
		catch (IOException e)
		{
			try
			{
				if (in != null) {
					in.close();
				}
				if (out != null)
					out.close();
			}
			catch (IOException localIOException2)
			{
			}
		}
		finally
		{
			try
			{
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
			catch (IOException localIOException3)
			{
			}
		}
		return flag;
	}
	
    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
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
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 把String写入到file中
     * @param xml
     * @param outFile
     */
	public static boolean writeStrToFile(String xml,File outFile){
		boolean flag = false;
        try {  
            FileOutputStream fos = new FileOutputStream(outFile);
            Writer os = new OutputStreamWriter(fos, "UTF-8");
            os.write(xml);
            os.flush();
            fos.close();
            flag = true;
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
        return flag;
	}
	
	/**
	 * 修改字符串
	 * @param file
	 * @param x	每一行首添加字符串x
	 * @param y 每一行首添加字符串y
	 * @return
	 */
	public static String modifyString(File file,String x,String y){
		String ans="";
		try {
            String encoding="UTF-8";
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                //写入新文件
//                FileWriter writer = new FileWriter("D:\\smc_menu123.unl", true);
                while((lineTxt = bufferedReader.readLine()) != null){
                    String temp = modifyLine(lineTxt,x,y);        //修改每一行
                    ans += temp+'\n';
//                    writer.write(temp+'\n');        //写入新文件
                }
                read.close();
//                writer.close();
//                System.out.println("执行成功");
            }else{
//                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
		return ans;
	}
	
	//修改数据
	public static String modifyLine(String str,String x,String y){
	    StringBuffer sb = new StringBuffer(str);
	    sb.insert(0, "x");
	    sb.append("y");
	    str= sb.toString();
	    return str;
	}
}