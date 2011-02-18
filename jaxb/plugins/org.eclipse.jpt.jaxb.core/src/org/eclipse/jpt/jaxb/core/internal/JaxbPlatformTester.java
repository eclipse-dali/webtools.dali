/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupDescription;


public class JaxbPlatformTester
		extends PropertyTester {
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (! (property.equals("jaxbPlatform") || property.equals("jaxbPlatformGroup"))
				|| ! (expectedValue instanceof String)) { //$NON-NLS-1$
			return false;
		}
		
		JaxbPlatformDescription platform = null;
		
		if (receiver instanceof IResource) {
			platform = platform(((IResource) receiver).getProject());
		} 
		else if (receiver instanceof IJavaElement) {
			platform = platform(((IJavaElement) receiver).getResource().getProject());
		}
		else if (receiver instanceof JaxbPlatformDescription) {
			platform = (JaxbPlatformDescription) receiver;
		}
		else if (receiver instanceof JaxbLibraryProviderInstallOperationConfig) {
			platform = ((JaxbLibraryProviderInstallOperationConfig) receiver).getJaxbPlatform();
		}
		
		if (property.equals("jaxbPlatform")) {
			JaxbPlatformDescription otherPlatform = JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform((String) expectedValue);
			return platform == null ? false : platform.equals(otherPlatform);
		}
		if (property.equals("jaxbPlatformGroup")) {
			JaxbPlatformGroupDescription group = (platform == null) ? null : platform.getGroup();
			JaxbPlatformGroupDescription otherGroup = JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatformGroup((String) expectedValue);
			return group == null ? false : group.equals(otherGroup);
		}
		return false;
	}
	
	private JaxbPlatformDescription platform(IProject project) {
		return (project == null) 
				? null 
				: JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform(JptJaxbCorePlugin.getJaxbPlatformId(project));
	}
}
