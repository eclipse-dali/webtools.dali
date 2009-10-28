/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.core.resource.xml;

import java.io.IOException;
import java.util.Collections;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.jem.util.plugin.JEMUtilPlugin;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaResourceModelListener;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JpaXmlResource
	extends TranslatorResourceImpl
	implements JpaResourceModel
{
	protected final IContentType contentType;
	
	protected final Translator rootTranslator;
	
	protected final ListenerList<JpaResourceModelListener> resourceModelListenerList;
	
	
	public JpaXmlResource(URI uri, Renderer renderer, IContentType contentType, Translator rootTranslator) {
		super(uri, renderer);
		this.contentType = contentType;
		this.rootTranslator = rootTranslator;
		this.resourceModelListenerList = new ListenerList<JpaResourceModelListener>(JpaResourceModelListener.class);
	}
	
	
	public IContentType getContentType() {
		return this.contentType;
	}
	
	public String getVersion() {
		return getRootObject().getVersion();
	}
	
	public JpaResourceType getResourceType() {
		return new JpaResourceType(getContentType(), getVersion());
	}
	
	
	// ********** BasicNotifierImpl override **********
	
	/**
	 * override to fire notification only when
	 * - the resource's state has actually changed; and
	 * - the resource is loaded; and
	 * - the resource's resource set is still present (EMF will fire an
	 *    notification when the resource set is set to 'null', just before
	 *    the resource is "unloaded" - we want to swallow this notification)
	 */
	@Override
	public void eNotify(Notification notification) {
		if ( ! notification.isTouch() && this.isLoaded() && (this.resourceSet != null)) {
			super.eNotify(notification);
			this.resourceModelChanged();
		}
	}

	/**
	 * we could use this method to suppress some notifications; or we could just
	 * check whether 'resourceSet' is 'null' (which is what we do)
	 */
	protected boolean resultSetCleared(Notification notification) {
		return (notification.getNotifier() == this) &&
					(notification.getFeatureID(Resource.class) == RESOURCE__RESOURCE_SET) &&
					(notification.getNewValue() == null);
	}
	
	
	// ********** TranslatorResource implementation **********
	
	/**
	 * only applicable for DTD-based files
	 */
	public String getDoctype() {
		return null;
	}
	
	public Translator getRootTranslator() {
		return this.rootTranslator;
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
	
	@Override
	public JpaRootEObject getRootObject() {
		try {
			return (JpaRootEObject) super.getRootObject();
		}
		catch (ClassCastException cce) {
			throw new IllegalStateException("Root objects of JPA XML resources must implement JpaRootEObject", cce);
		}
	}
	
	
	// ********** convenience methods **********
	
	public boolean fileExists() {
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
	
	public void modify(Runnable runnable) {
		try {
			runnable.run();
			try {
				save(Collections.EMPTY_MAP);
			} catch (IOException ioe) {
				JptCorePlugin.log(ioe);
			}
		} catch (Exception e) {
			JptCorePlugin.log(e);
		}
	}
	
	@Override
	public String toString() {
		// implementation in TranslatorResourceImpl is a bit off...
		return StringTools.buildToStringFor(this, this.getURI());
	}
	
	
	// ********** JpaResourceModel implementation **********
	
	public void addResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}
	
	public void removeResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}
	
	protected void resourceModelChanged() {
		for (JpaResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged();
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
