/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - ongoing maintenance
 *  
 *  Originally copied from 
 *  {@link org.eclipse.jst.common.project.facet.core.libprov.user.KeyClassesValidator}
 *******************************************************************************/
package org.eclipse.jpt.core.internal.utility;

import static org.eclipse.jst.common.project.facet.core.internal.FacetedProjectFrameworkJavaPlugin.PLUGIN_ID;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jst.common.project.facet.core.libprov.user.UserLibraryProviderInstallOperationConfig;
import org.eclipse.jst.common.project.facet.core.libprov.user.UserLibraryValidator;
import org.eclipse.osgi.util.NLS;

/** 
 * This class differs from the original in that it does not give an error if multiple instances of 
 * a class are discovered
 */
public class KeyClassesValidator
	extends UserLibraryValidator
{
	private final Set<String> classFileNames = new HashSet<String>();
	
	private final Map<String,String> classFileNameToClassName = new HashMap<String,String>();
	
	
	@Override
	public void init(final List<String> params) {
		for (String className : params) {
			String classFileName = className.replace('.', '/') + ".class";
			this.classFileNames.add(classFileName);
			this.classFileNameToClassName.put(classFileName, className);
		}
	}
	
	@Override
	public IStatus validate( final UserLibraryProviderInstallOperationConfig config ) {
		final Map<String,Integer> classAppearanceCounts = new HashMap<String,Integer>();
		
		for (String classFileName : this.classFileNames) {
			classAppearanceCounts.put(classFileName, 0);
		}
		
		for (IClasspathEntry cpe : config.resolve()) {
			if( cpe.getEntryKind() == IClasspathEntry.CPE_LIBRARY ) {
				final File file = cpe.getPath().toFile();
				
				if (file.exists()) {
					ZipFile zip = null;
					
					try {
						zip = new ZipFile(file);
						
						for (Enumeration<? extends ZipEntry> itr = zip.entries(); itr.hasMoreElements(); ) {
							final ZipEntry zipEntry = itr.nextElement();
							final String name = zipEntry.getName();
							
							Integer count = classAppearanceCounts.get(name);
							
							if (count != null) {
								classAppearanceCounts.put(name, count + 1);
							}
						}
					}
					catch (IOException e) {}
					finally {
						if (zip != null) {
							try {
								zip.close();
							}
							catch (IOException e) {}
						}
					}
				}
			}
		}
		
		for (Map.Entry<String,Integer> entry : classAppearanceCounts.entrySet()) {
			final int count = entry.getValue();
			
			if (count == 0) {
				final String classFileName = entry.getKey();
				final String className = this.classFileNameToClassName.get(classFileName);
				final String message = 
						NLS.bind(JptCoreMessages.KEY_CLASSES_VALIDATOR__CLASS_NOT_FOUND, className);
				return new Status(IStatus.ERROR, PLUGIN_ID, message);
			}
		}
		
		return Status.OK_STATUS;
	}
}
