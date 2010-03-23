/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved. This
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

public class JpaPlatformTester extends PropertyTester {
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (! property.equals("jpaPlatform")) { //$NON-NLS-1$
			return false;
		}
		
		String platformId = null;
		
		if (receiver instanceof IResource) {
			platformId = platformId(((IResource) receiver).getProject());
		} 
		else if (receiver instanceof IJavaElement) {
			platformId = platformId(((IJavaElement) receiver).getResource().getProject());
		} 
		
		
		return platformId == null ? false : platformId.equals(expectedValue);
	}
	
	private String platformId(IProject project) {
		return (project == null) ? null : JptCorePlugin.getJpaPlatformId(project);
	}
}
