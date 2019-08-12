package com.clbee.pbcms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileZipUtil {
	
	/**
	 * 디렉토리 및 파일을 압축한다.
	 * @param path 압축할 디렉토리 및 파일
	 * @param toPath 압축파일을 생성할 경로
	 * @param fileName 압축파일의 이름
	 */
	public static void createZipFile(String path, String toPath, String fileName) {
		File dir = new File(path);
		String[] list = dir.list();
		String _path;

		if (!dir.canRead() || !dir.canWrite()) {
			return;
		}
 
		int len = list.length;

		if (path.charAt(path.length() - 1) != '/') {
			_path = path + "/";
		}else {
			_path = path;
		}

		try {
			ZipOutputStream zip_out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(toPath+"/"+fileName), 2048));

			for (int i = 0; i < len; i++) {
				zip_folder("",new File(_path + list[i]), zip_out, path);
			}			
            zip_out.close();
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ZipOutputStream를 넘겨 받아서 하나의 압축파일로 만든다.
	 * @param parent 상위폴더명
	 * @param file 압축할 파일
	 * @param zout 압축전체스트림
	 * @param path 압축할 디렉토리 및 파일
	 * @throws IOException
	 */
	private static void zip_folder(String parent, File file, ZipOutputStream zout, String path) throws IOException {
    	byte[] data = new byte[2048];
    	int read;

    	if (file.isFile()) {
    		ZipEntry entry = new ZipEntry(parent + file.getName());
    		zout.putNextEntry(entry);
    		BufferedInputStream instream = new BufferedInputStream(new FileInputStream(file));

    		while ((read = instream.read(data, 0, 2048)) != -1) {
    			zout.write(data, 0, read);
    		}

    		zout.flush();
    		zout.closeEntry();
    		instream.close();
    		
		}else if (file.isDirectory()) {
			String parentString = file.getPath().replace(path,"");
			parentString = parentString.substring(0,parentString.length() - file.getName().length());
			ZipEntry entry = new ZipEntry(parentString+file.getName()+"/");
			zout.putNextEntry(entry);

			String[] list = file.list();
			if (list != null) {
				int len = list.length;
				for (int i = 0; i < len; i++) {
					zip_folder(entry.getName(),new File(file.getPath() + "/" + list[i]), zout, path);
				}
			}
		}
	}

	/**
	 * 압축을 해제 한다
	 *
	 * @param zip_file
	 * @param directory
	 */
	public static boolean extractZipFiles(String zip_file, String directory) {
		boolean result = false;

		byte[] data = new byte[2048];
		ZipEntry entry = null;
		ZipInputStream zipstream = null;
		FileOutputStream out = null;

		if (!(directory.charAt(directory.length() - 1) == '/')) {
			directory += "/";
		}

		File destDir = new File(directory);
		boolean isDirExists = destDir.exists();
		boolean isDirMake = destDir.mkdirs();

		try {
			zipstream = new ZipInputStream(new FileInputStream(zip_file));

			while ((entry = zipstream.getNextEntry()) != null) {
				int read = 0;
				File entryFile;

				//디렉토리의 경우 폴더를 생성한다.
				if (entry.isDirectory()) {
					File folder = new File(directory+entry.getName());
					if(!folder.exists()){
						folder.mkdirs();
					}
					continue;
				}else {
					entryFile = new File(directory + entry.getName());
				}

				if (!entryFile.exists()) {
					boolean isFileMake = entryFile.createNewFile();
				}

				out = new FileOutputStream(entryFile);
				while ((read = zipstream.read(data, 0, 2048)) != -1) {
					out.write(data, 0, read);
				}

				zipstream.closeEntry();
			}

			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (zipstream != null) {
				try {
					zipstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}