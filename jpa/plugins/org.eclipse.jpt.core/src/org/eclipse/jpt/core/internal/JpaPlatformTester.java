/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.core.platform.JpaPlatformGroupDescription;

public class JpaPlatformTester extends PropertyTester {
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (! (property.equals("jpaPlatform") || property.equals("jpaPlatformGroup"))
				|| ! (expectedValue instanceof String)) { //$NON-NLS-1$
			return false;
		}
		
		JpaPlatformDescription platform = null;
		
		if (receiver instanceof IResource) {
			platform = platform(((IResource) receiver).getProject());
		} 
		else if (receiver instanceof IJavaElement) {
			platform = platform(((IJavaElement) receiver).getResource().getProject());
		}
		else if (receiver instanceof JpaPlatformDescription) {
			platform = (JpaPlatformDescription) receiver;
		}
		else if (receiver instanceof JpaLibraryProviderInstallOperationConfig) {
			platform = ((JpaLibraryProviderInstallOperationConfig) receiver).getJpaPlatform();
		}
		
		if (property.equals("jpaPlatform")) {
			JpaPlatformDescription otherPlatform = JptCorePlugin.getJpaPlatformManager().getJpaPlatform((String) expectedValue);
			return platform == null ? false : platform.equals(otherPlatform);
		}
		if (property.equals("jpaPlatformGroup")) {
			JpaPlatformGroupDescription group = (platform == null) ? null : platform.getGroup();
			JpaPlatformGroupDescription otherGroup = JptCorePlugin.getJpaPlatformManager().getJpaPlatformGroup((String) expectedValue);
			return group == null ? false : group.equals(otherGroup);
		}
		return false;
	}
	
	private JpaPlatformDescription platform(IProject project) {
		return (project == null) 
				? null 
				: JptCorePlugin.getJpaPlatformManager().getJpaPlatform(JptCorePlugin.getJpaPlatformId(project));
	}
}
