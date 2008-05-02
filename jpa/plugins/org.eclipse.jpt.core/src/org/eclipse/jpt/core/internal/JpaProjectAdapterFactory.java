/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;

public class JpaProjectAdapterFactory
	implements IAdapterFactory
{
	private static Class[] PROPERTIES= new Class[] {
		JpaProject.class
	};
		
	public Class[] getAdapterList() {
		return PROPERTIES;
	}
	
	public Object getAdapter(Object element, Class key) {
		IProject project;
		
		if (element instanceof IProject) {
			project = (IProject) element;
		}
		else if (element instanceof IJavaProject) {
			project = ((IJavaProject) element).getProject();
		}
		else {
			return null;
		}
		
		return JptCorePlugin.getJpaProject(project);
	}	
}
