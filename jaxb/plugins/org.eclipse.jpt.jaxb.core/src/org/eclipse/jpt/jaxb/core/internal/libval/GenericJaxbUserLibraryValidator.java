/*******************************************************************************
 *  Copyright (c) 2010, 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libval;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jpt.common.core.internal.libval.LibValUtil;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.internal.libprov.JaxbUserLibraryProviderInstallOperationConfig;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class GenericJaxbUserLibraryValidator
		implements LibraryValidator {
	
	public synchronized IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		
		JaxbUserLibraryProviderInstallOperationConfig jaxbConfig 
				= (JaxbUserLibraryProviderInstallOperationConfig) config;
		
		IProjectFacetVersion jreJaxbVersion = JaxbLibValUtil.findJreJaxbVersion(jaxbConfig);
		IProjectFacetVersion jaxbVersion = config.getProjectFacetVersion();
		
		Set<String> classNames = new HashSet<String>();
		
		if (jreJaxbVersion == null) {
			classNames.add("javax.xml.bind.annotation.XmlSeeAlso"); //$NON-NLS-1$
		}
		if (jaxbVersion.compareTo(JaxbFacet.VERSION_2_2) >= 0 
				&& (jreJaxbVersion == null || jreJaxbVersion.compareTo(JaxbFacet.VERSION_2_2) < 0)) {
			classNames.add("javax.xml.bind.JAXBPermission"); //$NON-NLS-1$
		}
		
		Iterable<IPath> libraryPaths = 
			new TransformationIterable<IClasspathEntry, IPath>(jaxbConfig.resolve()) {
				@Override
				protected IPath transform(IClasspathEntry o) {
					return o.getPath();
				}
			};
		
		return LibValUtil.validate(libraryPaths, classNames);
	}
}
