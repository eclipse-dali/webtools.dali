/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;


public class JaxbPackageAdapterFactory
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
		JaxbProject jaxbProject = this.getJaxbProjectManager().getJaxbProject(packageFragment.getJavaProject().getProject());
		return (jaxbProject == null) ? null : jaxbProject.getContextRoot().getPackage(packageFragment.getElementName());
	}

	private JaxbProjectManager getJaxbProjectManager() {
		return this.getJaxbWorkspace().getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}
}
