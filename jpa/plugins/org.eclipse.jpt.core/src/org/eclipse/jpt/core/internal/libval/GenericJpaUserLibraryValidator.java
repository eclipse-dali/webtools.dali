/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.libval;

import static org.eclipse.jst.common.project.facet.core.internal.FacetedProjectFrameworkJavaPlugin.PLUGIN_ID;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.libprov.JpaUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.internal.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.libval.LibraryValidator;
import org.eclipse.osgi.util.NLS;

public class GenericJpaUserLibraryValidator
	implements LibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JpaUserLibraryProviderInstallOperationConfig jpaConfig 
				= (JpaUserLibraryProviderInstallOperationConfig) config;
		Set<String> classNames = new HashSet<String>();
		classNames.add("javax.persistence.Entity");
		if (config.getProjectFacetVersion().compareTo(JpaFacet.VERSION_2_0) >= 0) {
			classNames.add("javax.persistence.ElementCollection");
		}
		return validate(jpaConfig, classNames);
	}
	
	protected IStatus validate(
			JpaUserLibraryProviderInstallOperationConfig config, Set<String> classNames) {
		
		Set<String> classFileNames = new HashSet<String>();
		Map<String,String> classFileNameToClassName = new HashMap<String,String>();
		for (String className : classNames) {
			String classFileName = className.replace('.', '/') + ".class";
			classFileNames.add(classFileName);
			classFileNameToClassName.put(classFileName, className);
		}
		
		final Map<String,Integer> classAppearanceCounts = new HashMap<String,Integer>();
		
		for (String classFileName : classFileNames) {
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
				final String className = classFileNameToClassName.get(classFileName);
				final String message = 
						NLS.bind(JptCoreMessages.USER_LIBRARY_VALIDATOR__CLASS_NOT_FOUND, className);
				return new Status(IStatus.ERROR, PLUGIN_ID, message);
			}
		}
		
		return Status.OK_STATUS;
	}
}
