/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.Bundle;

/**
 * Collections of utility methods handling files.
 * 
 */
public class FileUtil
{
	
	private static String DELETE_FOLDER_ERR = "The directory %s could not be deleted.";
	private static String DELETE_FILE_ERR = "The file %s could not be deleted.";
	private static String FILE_READONLY_ERR = "The file %s could not be modified because write access is denied.\nPlease make sure that the file is not marked as readonly in the file system.";

	public static void deleteFolder(File folder)
		throws IOException
	{
		File[] files = folder.listFiles();
		//empty the folder first (java.io.file.delete requires it empty)
		if (files != null) {
			for (int i = 0; i < files.length; ++i) {
				File f = files[i];
				if (f.isDirectory())
					deleteFolder(f);
				else
					deletePath(f);
			}
		}
		deletePath(folder);
	}

	public static void deletePath(File f)
		throws IOException
	{
		if (!f.delete()) {
			String msgId = f.isDirectory() ?  DELETE_FOLDER_ERR :  DELETE_FILE_ERR;
			throw new IOException( String.format(msgId,f.getPath()));
		}
	}
	
	public static byte[] readFile(File src)
		throws IOException
	{
		java.io.FileInputStream fin = new java.io.FileInputStream(src);
		try {
			long fileLen = src.length();
			if (fileLen > Integer.MAX_VALUE)
				throw new IOException("file length too big to be read by FileUtil.readFile: " + fileLen);
		
			byte[] bytes = new byte[(int)fileLen];
			fin.read(bytes);
			return bytes;
		}
		finally {
			fin.close();
		}
	}

	public static void writeFile(File dest, byte[] bytes)
		throws IOException
	{
		if (dest.exists() && !dest.canWrite())
			throw new IOException( FILE_READONLY_ERR );  //throw with a clear error because otherwise FileOutputStream throws FileNotFoundException!
		java.io.FileOutputStream fout = new java.io.FileOutputStream(dest.getPath(), false/*append*/);
		try {
			fout.write(bytes);
		}
		finally {
			fout.flush();
			fout.close();
		}
	}

	/**
	 * Returns the url for a file.
	 * This basically the same as file.toUrl() but without the non-sense exception.
	 */
	public static URL getFileUrl(File file) {
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			return null; //should not happen as file.toURL() does not really throw an exception
		}
	}

	public static void setFileContent(File file, java.io.InputStream contents) throws java.io.IOException {
		Path path = new Path(file.getAbsolutePath());
		try {
			IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(path);
			if (iFile == null) {
				throw new IOException("The path " + file + " does not seem to be a valid file path.");
			}
			iFile.setContents(contents, true/*force*/, true/*keepHistory*/, null/*monitor*/);
		} catch (CoreException ex) {
			throw new IOException(ex.getMessage());
		}
	}	
	
    /**
     * Extract the contents of a Jar archive to the specified destination.
     */
    public static void unjar(InputStream in, File dest) throws IOException {
        if (!dest.exists()) {
            dest.mkdirs();
        }
        if (!dest.isDirectory()) {
            throw new IOException("Destination must be a directory.");//$NON-NLS-1$
        }
        JarInputStream jin = new JarInputStream(in);
        byte[] buffer = new byte[1024];

        ZipEntry entry = jin.getNextEntry();
        while (entry != null) {
            String fileName = entry.getName();
            if (fileName.charAt(fileName.length() - 1) == '/') {
                fileName = fileName.substring(0, fileName.length() - 1);
            }
            if (fileName.charAt(0) == '/') {
                fileName = fileName.substring(1);
            }
            if (File.separatorChar != '/') {
                fileName = fileName.replace('/', File.separatorChar);
            }
            File file = new File(dest, fileName);
            if (entry.isDirectory()) {
                // make sure the directory exists
                file.mkdirs();
                jin.closeEntry();
            } else {
                // make sure the directory exists
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }

                // dump the file
                OutputStream out = new FileOutputStream(file);
                int len = 0;
                while ((len = jin.read(buffer, 0, buffer.length)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.close();
                jin.closeEntry();
                file.setLastModified(entry.getTime());
            }
            entry = jin.getNextEntry();
        }
        /* Explicitly write out the META-INF/MANIFEST.MF so that any headers such
         as the Class-Path are seen for the unpackaged jar
         */
        Manifest mf = jin.getManifest();
        if (mf != null) {
            File file = new File(dest, "META-INF/MANIFEST.MF");//$NON-NLS-1$
            File parent = file.getParentFile();
            if (parent.exists() == false) {
                parent.mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            mf.write(out);
            out.flush();
            out.close();
        }
        jin.close();
    }
    
	//Used to Unzip the a specific folder packed inside a plug-in bundle to the plug-in state location
	public static File extractFilesFromBundle( URL url, Bundle bundle, String path ) throws Exception {
		URL jarUrl = UrlUtil.getJarFileUrl(url);
		File jarFile = new File(jarUrl.getFile() );
		Location configLocation = Platform.getConfigurationLocation();
		String pluginId = bundle.getSymbolicName();
		File configFolder = new File( configLocation.getURL().getFile(), pluginId);
		File templDir = new File( configFolder, path );
		if( !templDir.exists() ){
			FileUtil.unjar( new FileInputStream( jarFile ), configFolder );
			//Delete un-related files and folders
			File[] files = configFolder.listFiles();
			for( File f : files ){
				if( f.isFile() )
					f.delete();
				else if(  templDir.getPath().indexOf( f.getPath() ) !=0  ){
					FileUtil.deleteFolder(f);
				}
			}
		}
		return templDir ;
	}	
}

