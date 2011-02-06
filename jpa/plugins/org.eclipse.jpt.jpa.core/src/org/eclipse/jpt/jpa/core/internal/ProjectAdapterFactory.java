/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;

/**
 * [Double-duty] factory to build adapters for either standard projects or
 * Java projects:
 *   - JPA project
 * 
 * See org.eclipse.jpt.jpa.core plugin.xml.
 */
public class ProjectAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JpaProject.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}
	
	public Object getAdapter(Object adaptableObject, @SuppressWarnings("unchecked") Class adapterType) {
		if (adaptableObject instanceof IProject) {
			return this.getAdapter((IProject) adaptableObject, adapterType);
		}
		if (adaptableObject instanceof IJavaProject) {
			return this.getAdapter((IJavaProject) adaptableObject, adapterType);
		}
		return null;
	}	

	private Object getAdapter(IProject project, Class <?>adapterType) {
		if (adapterType == JpaProject.class) {
			return this.getJpaProject(project);
		}
		return null;
	}

	private Object getAdapter(IJavaProject javaProject, Class <?>adapterType) {
		if (adapterType == JpaProject.class) {
			return this.getJpaProject(javaProject.getProject());
		}
		return null;
	}

	private JpaProject getJpaProject(IProject project) {
		return JptJpaCorePlugin.getJpaProject(project);
	}

}
