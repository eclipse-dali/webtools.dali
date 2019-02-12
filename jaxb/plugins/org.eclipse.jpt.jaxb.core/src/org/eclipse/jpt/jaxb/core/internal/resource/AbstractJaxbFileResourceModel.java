/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.JptResourceModelListener;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.ModelTools;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;

public abstract class AbstractJaxbFileResourceModel<S>
	implements JptResourceModel
{
	protected final IFile file;

	/**
	 * This is <code>null</code> if there is no package name.
	 */
	protected final String packageName;

	protected final S state;

	protected final ListenerList<JptResourceModelListener> resourceModelListenerList = ModelTools.listenerList();


	protected AbstractJaxbFileResourceModel(IFile file) {
		super();
		if (file == null) {
			throw new NullPointerException();
		}
		this.file = file;
		this.packageName = this.buildPackageName();
		this.state = this.buildState();
		this.load();
	}

	protected String buildPackageName() {
		String pkg = this.buildPackageName_();
		return StringTools.isBlank(pkg) ? null : pkg;
	}

	protected String buildPackageName_() {
		IJavaElement javaElement = JavaCore.create(this.file.getParent());
		if ((javaElement != null) && (javaElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT)) {
			return ((IPackageFragment) javaElement).getElementName();
		}
		return null;
	}

	protected abstract S buildState();

	public String getPackageName() {
		return this.packageName;
	}

	protected void reload() {
		this.load();
	}

	protected void load() {
		InputStream stream = null;
		try {
			stream = this.file.getContents();
			if (stream != null) {
				this.load(new BufferedInputStream(stream));
			}
		}
		catch (CoreException ce) {
			// workspace out of sync - no underlying file - simply don't load
		}
		catch (Exception ex) {
			JptJaxbCorePlugin.instance().logError(ex);
		}
		finally {
			this.closeStream(stream);
		}
	}

	/**
	 * The specified stream is not <code>null</code>.
	 */
	protected abstract void load(InputStream stream) throws IOException;

	protected void closeStream(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException ex) {
			JptJaxbCorePlugin.instance().logError(ex);
		}
	}

	public void update() {
		this.reload();
		this.resourceModelChanged();
	}


	// ********** JptResourceModel implementation **********

	public IFile getFile() {
		return this.file;
	}

	public void addResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}

	protected void resourceModelChanged() {
		for (JptResourceModelListener listener : this.resourceModelListenerList) {
			listener.resourceModelChanged(this);
		}
	}
}
