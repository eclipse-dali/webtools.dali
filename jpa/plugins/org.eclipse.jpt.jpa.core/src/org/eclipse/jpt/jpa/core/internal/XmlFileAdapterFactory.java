/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;

public class XmlFileAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
		JpaPlatform.Config.class
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
		if (adapterType == JpaPlatform.Config.class) {
			return this.getJpaPlatformConfig(resource);
		}
		return null;
	}
	
	private JpaPlatform.Config getJpaPlatformConfig(IResource resource) {
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
