/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;

public class XmlFileAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
		JpaPlatformConfig.class
	};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof XmlFile) {
			return this.getAdapter(((XmlFile) adaptableObject).getXmlResource().getFile(), adapterType);
		}
		return null;
	}
	
	private Object getAdapter(IResource resource, Class<?> adapterType) {
		if (adapterType == JpaPlatformConfig.class) {
			return this.getJpaPlatformConfig(resource);
		}
		return null;
	}
	
	private JpaPlatformConfig getJpaPlatformConfig(IResource resource) {
		JpaPlatformManager jpaPlatformManager = this.getJpaPlatformManager(resource.getWorkspace());
		return (jpaPlatformManager == null) ? null : jpaPlatformManager.getJpaPlatformConfig(JpaPreferences.getJpaPlatformID(resource.getProject()));
	}

	private JpaPlatformManager getJpaPlatformManager(IWorkspace workspace) {
		JpaWorkspace jpaWorkspace = this.getJpaWorkspace(workspace);
		return (jpaWorkspace == null) ? null : jpaWorkspace.getJpaPlatformManager();
	}

	private JpaWorkspace getJpaWorkspace(IWorkspace workspace) {
		return (JpaWorkspace) workspace.getAdapter(JpaWorkspace.class);
	}
}
