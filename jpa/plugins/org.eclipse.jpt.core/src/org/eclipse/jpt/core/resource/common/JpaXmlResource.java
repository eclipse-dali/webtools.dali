/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.common;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.jem.util.plugin.JEMUtilPlugin;
import org.eclipse.jpt.core.JpaResourceModelListener;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class JpaXmlResource
	extends TranslatorResourceImpl
{
	private JpaResourceModelListener resourceModelListener;


	// ********** constructor **********

	protected JpaXmlResource(URI uri, Renderer renderer) {
		super(uri, renderer);
	}


	// ********** BasicNotifierImpl override **********

	/**
	 * override to prevent notification when the resource's state is unchanged
	 * or the resource is not loaded
	 */
	@Override
	public void eNotify(Notification notification) {
		if ( ! notification.isTouch() && this.isLoaded()) {
			super.eNotify(notification);
			this.resourceModelChanged();
		}
	}


	// ********** TranslatorResourceImpl implementation **********

	/**
	 * only applicable for DTD-based files
	 */
	@Override
	protected String getDefaultPublicId() {
		return null;
	}

	/**
	 * only applicable for DTD-based files
	 */
	@Override
	protected String getDefaultSystemId() {
		return null;
	}

	/**
	 * this seems to be the default version of the spec for this doc
	 * and the id 10 maps to the version 1.0
	 */
	@Override
	protected int getDefaultVersionID() {
		return 10;
	}


	// ********** TranslatorResource implementation **********

	/**
	 * only applicable for DTD-based files
	 */
	public String getDoctype() {
		return null;
	}


	// ********** convenience methods **********

	public boolean exists() {
		return this.getFile().exists();
	}

	public IFile getFile() {
		IFile file = getFile(this.uri);
		return (file != null) ? file : this.getConvertedURIFile();
	}

	protected IFile getConvertedURIFile() {
		if (this.resourceSet == null) {
			return null;
		}
		URI convertedURI = this.resourceSet.getURIConverter().normalize(this.uri);
		return this.uri.equals(convertedURI) ? null : getFile(convertedURI);
	}

	/**
	 * Return the Eclipse file for the specified URI.
	 * This URI is assumed to be absolute in the following format:
	 *     platform:/resource/....
	 */
	protected static IFile getFile(URI uri) {
		if ( ! WorkbenchResourceHelperBase.isPlatformResourceURI(uri)) {
			return null;
		}
		String fileName = URI.decode(uri.path()).substring(JEMUtilPlugin.PLATFORM_RESOURCE.length() + 1);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
	}

	@Override
	public String toString() {
		// implementation in TranslatorResourceImpl is a bit off...
		return StringTools.buildToStringFor(this, this.getURI());
	}


	// ********** resource model changes **********

	public void setResourceModelListener(JpaResourceModelListener resourceModelListener) {
		this.resourceModelListener = resourceModelListener;
	}

	public void resourceModelChanged() {
		if (this.resourceModelListener != null) {
			this.resourceModelListener.resourceModelChanged();
		}
	}


	// ********** cast things back to what they are in EMF **********

	@SuppressWarnings("unchecked")
	@Override
	public EList<Adapter> eAdapters() {
		return super.eAdapters();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EList<EObject> getContents() {
		return super.getContents();
	}

}
