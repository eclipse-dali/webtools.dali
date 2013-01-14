/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;


public class PackageFragmentAdapterFactory
		implements IAdapterFactory {
	
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JaxbPackage.class };
	
	
	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}
	
	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IPackageFragment) {
			return getAdapter((IPackageFragment) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(IPackageFragment packageFragment, Class<?> adapterType) {
		if (adapterType == JaxbPackage.class) {
			return getJaxbPackage(packageFragment);
		}
		return null;
	}
	
	private JaxbPackage getJaxbPackage(IPackageFragment packageFragment) {
		JaxbProject jaxbProject = this.getJaxbProject(packageFragment.getJavaProject().getProject());
		return (jaxbProject == null) ? null : jaxbProject.getContextRoot().getPackage(packageFragment.getElementName());
	}

	private JaxbProject getJaxbProject(IProject project) {
		JaxbProjectManager jaxbProjectManager = this.getJaxbProjectManager(project.getWorkspace());
		return (jaxbProjectManager == null) ? null : jaxbProjectManager.getJaxbProject(project);
	}

	private JaxbProjectManager getJaxbProjectManager(IWorkspace workspace) {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace(workspace);
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace(IWorkspace workspace) {
		return (JaxbWorkspace) workspace.getAdapter(JaxbWorkspace.class);
	}
}
