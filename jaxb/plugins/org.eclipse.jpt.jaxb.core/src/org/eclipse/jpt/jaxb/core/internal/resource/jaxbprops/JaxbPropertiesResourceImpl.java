/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.jaxbprops;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.JptResourceModelListener;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.resource.jaxbprops.JaxbPropertiesResource;


public class JaxbPropertiesResourceImpl
	implements JaxbPropertiesResource
{
	protected final IFile file;

	protected final String packageName;

	protected final Properties properties = new Properties();

	protected final ListenerList<JptResourceModelListener> resourceModelListenerList =
			new ListenerList<JptResourceModelListener>(JptResourceModelListener.class);


	public JaxbPropertiesResourceImpl(IFile file) {
		super();
		if (file == null) {
			throw new NullPointerException();
		}
		this.file = file;
		this.packageName = this.buildPackageName();
		this.loadProperties();
	}

	protected String buildPackageName() {
		IJavaElement javaElement = JavaCore.create(this.file.getParent());
		if ((javaElement != null) && (javaElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT)) {
			return ((IPackageFragment) javaElement).getElementName();
		}
		return null;
	}

	protected void reloadProperties() {
		this.properties.clear();
		this.loadProperties();
	}

	protected void loadProperties() {
		InputStream stream = null;
		try {
			stream = this.file.getContents();
			if (stream != null) {
				this.properties.load(stream);
			}
		} catch (Exception ex) {
			JptJaxbCorePlugin.log(ex);
		} finally {
			this.closeStream(stream);
		}
	}

	protected void closeStream(InputStream stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException ex) {
			JptJaxbCorePlugin.log(ex);
		}
	}

	void update() {
		this.reloadProperties();
		this.resourceModelChanged();
	}

	public String getPackageName() {
		return this.packageName;
	}

	public String getProperty(String propertyName) {
		return this.properties.getProperty(propertyName);
	}


	// ********** JptResourceModel implementation **********

	public JptResourceType getResourceType() {
		return JptJaxbCorePlugin.JAXB_PROPERTIES_RESOURCE_TYPE;
	}

	public void addResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}

	protected void resourceModelChanged() {
		for (JptResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged(this);
		}
	}
}
