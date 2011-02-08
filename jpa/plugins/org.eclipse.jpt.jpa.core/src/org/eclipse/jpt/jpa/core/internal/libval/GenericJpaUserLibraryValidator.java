/*******************************************************************************
 *  Copyright (c) 2010, 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.libval;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jpt.common.core.internal.libval.LibValUtil;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.internal.libprov.JpaUserLibraryProviderInstallOperationConfig;

public class GenericJpaUserLibraryValidator
	implements LibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JpaUserLibraryProviderInstallOperationConfig jpaConfig 
				= (JpaUserLibraryProviderInstallOperationConfig) config;
		Set<String> classNames = new HashSet<String>();
		classNames.add("javax.persistence.Entity"); //$NON-NLS-1$
		if (config.getProjectFacetVersion().compareTo(JpaFacet.VERSION_2_0) >= 0) {
			classNames.add("javax.persistence.ElementCollection"); //$NON-NLS-1$
		}
		
		Iterable<IPath> libraryPaths = 
			new TransformationIterable<IClasspathEntry, IPath>(jpaConfig.resolve()) {
				@Override
				protected IPath transform(IClasspathEntry o) {
					return o.getPath();
				}
			};
		
		return LibValUtil.validate(libraryPaths, classNames);
	}
}
