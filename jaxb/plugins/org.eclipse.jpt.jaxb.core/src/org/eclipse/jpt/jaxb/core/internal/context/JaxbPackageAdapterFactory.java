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

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
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
		JaxbProject jaxbProject = JptJaxbCorePlugin.instance().getProjectManager().getJaxbProject(packageFragment.getJavaProject().getProject());
		if (jaxbProject == null) {
			return null;
		}
		return jaxbProject.getContextRoot().getPackage(packageFragment.getElementName());
	}
}
