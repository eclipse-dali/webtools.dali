/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libval;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.libprov.JaxbUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class GenericJaxbUserLibraryValidator
		implements LibraryValidator {
	
	public synchronized IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		
		JaxbUserLibraryProviderInstallOperationConfig jaxbConfig 
				= (JaxbUserLibraryProviderInstallOperationConfig) config;
		
		IProjectFacetVersion jaxbVersion = config.getProjectFacetVersion();
		IProjectFacetVersion javaVersion = JaxbLibValUtil.getJavaVersion(jaxbConfig);
		IProjectFacetVersion javaJaxbVersion = JaxbLibValUtil.findJavaJaxbVersion(jaxbConfig);
		
		// dev-time portion of validation - error if actual java library *conflicts with* jaxb facet
		// or if library does not provide supplemental classes
		
		IProjectFacetVersion jreJaxbVersion = JaxbLibValUtil.findJreJaxbVersion(jaxbConfig);
		
		// null here implies something prior to jaxb 2.1
		if (jreJaxbVersion != null) {
			if (jreJaxbVersion.compareTo(jaxbVersion) < 0) {
				return JptJaxbCorePlugin.instance().buildErrorStatus(JptJaxbCoreMessages.UserLibraryValidator_incompatibleJavaLibrary, jaxbVersion.getVersionString());
			}
		}
		// if jre is enough for jaxb version, we don't look for classes in the library
		else {
			Set<String> classNames = new HashSet<String>();
	
			classNames.add("javax.xml.bind.annotation.XmlSeeAlso"); //$NON-NLS-1$
			if (jaxbVersion.compareTo(JaxbFacet.VERSION_2_2) >= 0) {
				classNames.add("javax.xml.bind.JAXBPermission"); //$NON-NLS-1$
			}
			
			IStatus status = LibraryValidatorTools.validateClasspathEntries(jaxbConfig.resolve(), classNames);
			
			if (! status.isOK()) {
				return status;
			}
		}
		
		// runtime portion of validation - warn if java facet is insufficient to provide jaxb api.
		// user may override implementation manually, however
		// assume 1.6 runtime environs are latest (with jaxb 2.1, not 2.0)
		
		// null here implies something prior to jaxb 2.1
		if (javaJaxbVersion != null && javaJaxbVersion.compareTo(jaxbVersion) < 0) {
			return JptJaxbCorePlugin.instance().buildStatus(IStatus.WARNING, JptJaxbCoreMessages.UserLibraryValidator_incompatibleJavaFacet, javaVersion.getVersionString(), jaxbVersion.getVersionString());
		}
		
		return Status.OK_STATUS;
	}
}
