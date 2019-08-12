package com.clbee.pbcms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.clbee.pbcms.util.StringUtil;


public class FileUtil {

    /**
     * ������ ī���ϴ¸޼ҵ��̸� ������ ���丮�̸� ���丮�������ϰ� �����ҽ��� �����̰�ī���Ϸ��°��� ���丮�̸� �������ϰ� ��������������
     * �����Ѵ�.
     *
     * @param source
     *            ī�Ǹ��ϰ����ϴ� ��������.
     * @param dest
     *            ī�Ǹ� ������ ��������.
     * @exception ������
     *                �дٰ� ������ �߻��ϸ� java.io.IOException�� throw��.
     */
    public static void copy(File source, File dest) throws IOException {
        File parentFile = null;

        if (source.isDirectory()) {
            if (dest.isDirectory()) {
            } else if (dest.exists()) {
                //�̷�! �̹� ������ �����ϰ������� ��� �ϳ�??
                throw new IOException("�̹� �����ϴ������Դϴ�. --> '" + dest + "'.");
            } else {
                dest = new File(dest, source.getName());
                dest.mkdirs();
            }
        }

        if (source.isFile() && dest.isDirectory()) {
            parentFile = new File(dest.getAbsolutePath());
            dest = new File(dest, source.getName());
        } else {
            parentFile = new File(dest.getParent());
        }

        parentFile.mkdirs();

        if (!source.canRead()) {
            throw new IOException("Cannot read file '" + source + "'.");
        }

        if (dest.exists() && (!dest.canWrite())) {
            throw new IOException("Cannot write to file '" + dest + "'.");
        }

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
        byte[] buffer = new byte[1024];
        int read = -1;

        while ((read = in.read(buffer, 0, 1024)) != -1) {
            out.write(buffer, 0, read);
        }

        out.flush();
        out.close();
        in.close();
    }

    /**
     * ������ ���ʿ��� �ٸ������� ���縦 �ϴ� �޼ҵ�.
     *
     * @param fileName
     *            ������ �̸� ( ��ΰ� �ƴ� )
     * @param fromDir
     *            ���������� �ִµ��丮
     * @param toDir
     *            ������ ������ �־���� ���丮
     * @exception ������
     *                �дٰ� ������ �߻��ϸ� java.io.IOException�� throw��.
     */
    public static void copy(String fileName, String fromDir, String toDir)
        throws IOException {
        copy(new File(fromDir + File.separator + fileName),
            new File(toDir + File.separator + fileName));
    }

    /**
     * ���Ͽ� ���� Stream�� ���ڷ� �ָ� Copy�� �ϴ� �޼ҵ�.
     *
     * @param in
     *            �������Ͽ����� ��Ʈ��
     * @param out
     *            ī���� �����Ϸ��� ���Ͽ����� ��Ʈ��
     * @exception ������
     *                �дٰ� ������ �߻��ϸ� java.io.IOException�� throw��.
     */
    public static void copy(InputStream in, OutputStream out)
        throws IOException {
        BufferedInputStream bin = new BufferedInputStream(in);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        byte[] buffer = new byte[1024];
        int read = -1;

        while ((read = bin.read(buffer, 0, 1024)) != -1)
            bout.write(buffer, 0, read);

        bout.flush();
        bout.close();
        bin.close();
    }

    /**
     * �������̵��� ������̵��� �������ʰ� ���ϰ�ü���� InputStream�� ������.
     *
     * @param file
     *            �о���� ���ϰ�ü
     * @param c
     *            �ν��Ͻ�ȭ�� Ŭ������ü
     * @return ���Ͽ����� InputStream
     * @exception ������
     *                ã�����ϸ� java.io.FileNotFoundException�� throw��.
     */
    public static InputStream getInputStream(File file, Class c)
        throws FileNotFoundException {
        InputStream rtn;
        String s;

        if (file != null) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                s = file.toString();

                int i = s.indexOf(File.separator);

                if (i >= 0) {
                    s = s.substring(i);
                    s = StringUtil.replace("\\", "/", s);

                    if ((rtn = c.getResourceAsStream(s)) != null) {
                        return rtn;
                    }
                }

                throw e;
            }
        }

        return null;
    }

    /**
     * ���� ����� �޾� ��� ���� ó���Ѵ�.
     * @author Administrator
     * @param  list
     * @return cnt - ������ ����
     * @date   2007. 06. 04
     */
    public static int delete(List fileList) {

        int cnt = 0;

        if(fileList.size()>0) {

            File f = null;

            for(int i=0 ; i<fileList.size() ; i++) {
                Map file = (Map)fileList.get(i);

                f = new File(file.get("FILE_DIR") + "" + file.get("FILE_SAVED_NAME"));

                if( FileUtil.delete( f ) ) {
                    cnt++;
                }
            }
        }

        return cnt;
    }

    /**
     * ���丮�ΰ�쿡 ������ ��������� �����ϰ� �ڱ��ڽŵ� ������. �����ϰ�쿡�� �ڱ��ڽ��� ��������.
     *
     * @param file
     *            the file (or directory) to delete.
     * @return �����̳� ���丮�� ��������.
     */
    public static boolean delete(File file) {
    	//System.out.println("��ΰ�???====="+file.getAbsolutePath()+"@@@");
    	//System.out.println("������ �ֳ�??====="+file.exists());
        if (file.exists()) {
        	//System.out.println("������ �ֳ�====="+file.exists());
            if (file.isDirectory()) {
            	//System.out.println("���丮�ΰ�??====="+file.isDirectory());
                if (clean(file)) {
                    return file.delete();
                } else {
                    return false;
                }
            } else {
            	//System.out.println("�׳� �����ε�====="+file.isDirectory());
                return file.delete();
            }
        }

        return true;
    }

    /**
     * ���丮�ΰ�쿡 ������ ��������� �����ϰ� true�� �Ѱ���. �����ϰ�쿡�� �׳� true�� �ѱ�.
     *
     * @param file
     *            the directory to clean
     */
    public static boolean clean(File file) {
        if (file.isDirectory()) {
            String[] filen = file.list();

            for (int i = 0; i < filen.length; i++) {
                File subfile = new File(file, filen[i]);

                if ((subfile.isDirectory()) && (!clean(subfile))) {
                    return false;
                } else if (!subfile.delete()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * ���丮�ΰ�� ������ ���ϵ�l ����Ʈ�� ���� �Ѱ���.
     *
     * @param dir
     *            ���丮�� ���ϰ�ü
     * @return ���ϸ���Ʈ File[] <-- �����ǹ迭 �������������� null�� return��.
     */
    public static File[] listFiles(File dir) {
        String[] ss = dir.list();

        if (ss == null) {
            return null;
        }

        int n = ss.length;
        File[] fs = new File[n];

        for (int i = 0; i < n; i++) {
            fs[i] = new File(dir.getPath(), ss[i]);
        }

        return fs;
    }

    /**
     * �Ķ���ͷ� �Ѱ��� ��ΰ� ���丮�����ƴ����� ���θ� �Ѱ���
     *
     * @param path
     *            ������ ���
     * @return ���丮������ ����
     */
    public static boolean isDirectory(String path) {
        boolean dir = false;

        if (path != null) {
            File file = new File(path);

            dir = file.isDirectory();
        }

        return dir;
    }

    /**
     * �Ķ���ͷ� �Ѱ��� ��ΰ� ���������ƴ����� ���θ� �Ѱ���
     *
     * @param path
     *            ������ ���
     * @return ���������� ����
     */
    public static boolean isFile(String path) {
        boolean file = false;

        if (path != null) {
            File f = new File(path);

            file = f.isFile();
        }

        return file;
    }

    /**
     * �ý��ۿ� ������� �������̰ų� ����η� ���͵� ������� ���� �ý��ۿ� �´� ���԰�θ� ��ȯ��.
     *
     * @param in
     *            ���ϱ������Ե� ��� �����쳪 ���н���������� �����������.
     * @return ���� �ý��ۿ� �´� ���԰��.
     */
    public static String toCanonicalPath(final String in) {
        final String DOT = new String() + '.';
        String current = FileUtil.toCurrentPath(in);
        String out = new String(current);
        int index = -1;

        index = in.indexOf(DOT + DOT);

        if (index < 0) {
            index = current.indexOf(File.separator + '.');
        }

        if (index < 0) {
            index = current.indexOf('.' + File.separator);
        }

        if ((index > -1) || in.startsWith(DOT) || in.endsWith(DOT)) {
            File file = new File(current);

            try {
                out = file.getCanonicalPath();
            } catch (Exception e) {
                out = current;
                e.printStackTrace();
            }
        }

        return out;
    }

    /**
     * �ڹٿ��� �νİ����� ��η� ��ȯ�Ͽ���.
     *
     * @param in
     *            ���Ͽ� ���� ���
     */
    public static String toJavaPath(final String in) {
        String path = new String(in);

        path = FileUtil.toCurrentPath(path);

        return path.replace('\\', '/');
    }

    /**
     * ���� �ý��ۿ� �´� ��η� ��ȯ�Ͽ���
     *
     * @param ���Ͽ�
     *            ���� ���
     * @return ���� �ý��ۿ� �´� ���
     */
    public static String toCurrentPath(String path) {
        String cPath = path;
        File file;

        if (File.separatorChar == '/') {
            cPath = FileUtil.toShellPath(cPath);
        } else {
            cPath = FileUtil.toWindowsPath(cPath);
        }

        file = new File(cPath);

        // Add default drive
        file = new File(file.getAbsolutePath());

        if (file.exists()) {
            cPath = file.getAbsolutePath();
        }

        return cPath.trim();
    }

    /**
     * ���ϱ��� ���Եȴ°�ο��� ���ԵǾ��� file seperators�� ���н��� �°� �ٲپ���. ���� ���������� ��� ���ۺκ���
     * ����̺� ���ڿ����̸� ���н��� ��Ʈ�� �ٲپ���.
     *
     * @param path
     *            ���ϱ������Ե� ��� �����쳪 ���н���������� �����������.
     * @return Cygnus shell�� �´� ���.
     */
    public static String toShellPath(String inPath) {
        StringBuffer path = new StringBuffer();
        int index = -1;

        inPath = inPath.trim();
        index = inPath.indexOf(":\\"); // nores
        inPath = inPath.replace('\\', '/');

        if (index > -1) {
            path.append("//"); // nores
            path.append(inPath.substring(0, index));
            path.append('/');
            path.append(inPath.substring(index + 2));
        } else {
            path.append(inPath);
        }

        return path.toString();
    }

    /**
     * ���ϱ��� ���Եȴ°�ο��� ���ԵǾ��� file seperators�� �����쿡 �°� �ٲپ���. ���� ����� ������ '\\'�̸� ������
     * ����̺� ���ڷ� �ٲپ���.
     *
     * @param path
     *            ���ϱ������Ե� ��� �����쳪 ���н���������� �����������.
     * @return ���н� �´� ���.
     */
    public static String toWindowsPath(String path) {
        String winPath = path;
        int index = winPath.indexOf("//"); // nores

        if (index > -1) {
            winPath = winPath.substring(0, index) + ":\\" // nores
                 +winPath.substring(index + 2);
        }

        index = winPath.indexOf(':');

        if (index == 1) {
            winPath = winPath.substring(0, 1).toUpperCase() +
                winPath.substring(1);
        }

        winPath = winPath.replace('/', '\\');

        return winPath;
    }

    /**
     * �Էµ� ���ϸ��� �Ľ��Ͽ� Ȯ���ڸ� ���Ѵ�.
     * @author Administrator
     * @param filename
     * @return
     * @date   2007. 06. 01
     */
    public static String getFileExt(String filename) {

        String ext = "";

        if(filename==null || "".equals(filename)) {
            ext = "";
        } else {
            if(filename.indexOf(".")>-1) {
                ext = filename.substring(filename.lastIndexOf(".")+1, filename.length());
            }
        }

        return ext;
    }

    /**
     * ������ ũ�⸦ String���� ��ȯ�Ͽ� ����
     * @author Administrator
     * @param filename
     * @return
     * @date   2007. 06. 01
     */
    public static String getFileSize(Object obj) {

        long size = 0;
        String strSize = "0 byte";

        if(obj instanceof Number) {
            size = ((Number)obj).longValue();

            if(size>0) {
                if(size<1024) {
                    strSize = size + "byte";
                } else if(size < (1024*1000)) {
                    strSize = StringUtil.jeolsa( ((size / 1024.0)+""), 2 ) + "Kb";
                } else {
                    strSize = StringUtil.jeolsa( ((size / (1024*1000.0))+""), 2 ) + "Mb";
                }
            }
        }

        return strSize;
    }

    /**
     * ������ �о ���ڿ��� �����Ѵ�.
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String readFile(String fileName) throws Exception {

        StringBuffer sb = new StringBuffer();

        BufferedReader br=null;

        try {
        	InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            br = new BufferedReader(isr);
            String data;

            while( (data=br.readLine())!=null ) {
                sb.append(data + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(br!=null) br.close();
        }

        return sb.toString();
    }
    /**
 	 * ���ϸ��� �̸��κа� Ȯ���� �κ����� �и�
 	 * @param fileName	���ϸ�
 	 * @return	�̸�, Ȯ����
 	 */
 	public static String[] getFileNamePart(String filePath) {
 		String[] filePart = new String[3];
 		
 		int idx = filePath.lastIndexOf("\\");
 		
 		String [] aStrPath = null; 
 		if(idx > -1)
 			aStrPath = StringUtil.StringSplit(filePath, "\\");
 		else
 			aStrPath = StringUtil.StringSplit(filePath, "/");
 		
 		String strFileName = aStrPath[aStrPath.length - 1];
 		
 		int index_path = -1;
 		index_path= filePath.lastIndexOf("\\");
 		if(index_path == -1)
 			index_path= filePath.lastIndexOf("/");
 		
 		String strFilePath = ""; 
 		if(index_path != -1)	
 			strFilePath = filePath.substring(0, index_path);
 		
         int index = strFileName.lastIndexOf(".");
         if (index != -1) {
             filePart[0] = strFileName.substring(0, index); //�����̸�
             filePart[1] = strFileName.substring(index + 1);	//����Ȯ��
             filePart[2] = strFilePath;	//�����̸��� ���
         } else {
         	filePart[0] = strFileName; //�����̸�
         	filePart[1] = ""; //����Ȯ��
             filePart[2] = strFilePath; //�����̸��� ���
         }
         
 		return filePart;
 	}
 	
	/**
	 * �����̵�
	 * @param imgSaveFile
	 * @param tempPath
	 * @param toPath
	 */
 	public static Boolean movefile(String imgSaveFile, String tempPath, String toPath) {
		// TODO Auto-generated method stub
		try {
			FileUtil.copy(imgSaveFile, tempPath, toPath);
			if(new File(toPath+imgSaveFile).exists()){
				if(!FileUtil.delete(new File(tempPath+imgSaveFile))){
					return false;
				}
			}else{
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return true;
		
	}

} //End of FileUtil.java
