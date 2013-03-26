/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.xml;

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
import org.eclipse.jpt.common.core.ContentTypeReference;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.JptResourceModelListener;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.ContentTypeTools;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jst.j2ee.internal.xml.J2EEXmlDtDEntityResolver;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;
import org.xml.sax.EntityResolver;

/**
 * To retrieve the JPA XML resource corresponding to an Eclipse file:
 * <pre>
 * IFile file = (IFile) ResourcesPlugin.getWorkspace().getRoot().findMember("Foo.java");
 * JptXmlResource jpaXmlResource = (JptXmlResource) file.getAdapter(JptXmlResource.class);
 * </pre>
 * This is a non-blocking call; and as a result it will return <code>null</code>
 * if the JPA XML resource or its JPA project is currently under construction.
 * <p>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public class JptXmlResource
	extends TranslatorResourceImpl
	implements JptResourceModel, ContentTypeReference
{
	/**
	 * cache the content type - if the content type changes, the JPA project
	 * will throw out the JPA file holding the xml resource and build a new one
	 */
	protected final IContentType contentType;
	
	protected final Translator rootTranslator;
	
	protected final ListenerList<JptResourceModelListener> resourceModelListenerList =
			new ListenerList<JptResourceModelListener>(JptResourceModelListener.class);
	
	
	public JptXmlResource(URI uri, Renderer renderer, IContentType contentType, Translator rootTranslator) {
		super(uri, renderer);
		this.contentType = contentType;
		this.rootTranslator = rootTranslator;
	}
	
	public IContentType getContentType() {
		return this.contentType;
	}
	
	public String getVersion() {
		ERootObject root = this.getRootObject();
		return (root == null) ? null : root.getDocumentVersion();
	}

	/**
	 * The XML schema version is required.
	 */
	public JptResourceType getResourceType() {
		String version = this.getVersion();
		return (version == null) ? null : this.getResourceType(version);
	}

	protected JptResourceType getResourceType(String version) {
		return (this.contentType == null) ? null : ContentTypeTools.getResourceType(this.contentType, version);
	}


	// ********** BasicNotifierImpl override **********
	
	/**
	 * Override to fire notification only when:<ul>
	 * <li>the resource's state has actually changed; and
	 * <li>the resource is loaded; and
	 * <li>the resource's resource set is still present (EMF will fire an
	 *    notification when the resource set is set to 'null', just before
	 *    the resource is "unloaded" - we want to swallow this notification)
	 * </ul>
	 */
	@Override
	public void eNotify(Notification notification) {
		// unload events can happen before the resource set is removed - should always react to unload events
		if (this.loadedFlagCleared(notification)) {
			super.eNotify(notification);
			if (this.isReverting()) {// closing a modified file without saving
				this.resourceModelReverted();
			} else {
				this.resourceModelUnloaded();
			}
		}
		else if ( ! notification.isTouch() && this.isLoaded() && (this.resourceSet != null)) {
			super.eNotify(notification);
			this.resourceModelChanged();
		}
	}
	
	/**
	 * Return whether the specified notification indicates the resource has been
	 * unloaded.
	 * we could use this method to suppress some notifications; or we could just
	 * check whether 'resourceSet' is 'null' (which is what we do)
	 */
	protected boolean loadedFlagCleared(Notification notification) {
		return (notification.getNotifier() == this) &&
				(notification.getEventType() == Notification.SET) &&
				(notification.getFeatureID(Resource.class) == RESOURCE__IS_LOADED) &&
				( ! notification.getNewBooleanValue());
	}
	
	/**
	 * Return whether the specified notification indicates the resource's
	 * resource set was cleared.
	 * We could use this method to suppress some resource set notifications;
	 * or we could just check whether <code>resourceSet</code> is
	 * <code>null</code> (which is what we do)/
	 */
	protected boolean resultSetCleared(Notification notification) {
		return (notification.getNotifier() == this) &&
				(notification.getEventType() == Notification.SET) &&
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
	public ERootObject getRootObject() {
		EObject root = super.getRootObject();
		try {
			return (ERootObject) root;
		} catch (ClassCastException ex) {
			throw new IllegalStateException("The root object of a JPA XML resource must implement ERootObject: " + root, ex); //$NON-NLS-1$
		}
	}
	
	//296544 - override this to avoid internet access finding the schema during tests
	@Override
	public EntityResolver getEntityResolver() {
		return J2EEXmlDtDEntityResolver.INSTANCE;
	}
	
	
	// ********** convenience methods **********
	
	public boolean fileExists() {
		IFile file = this.getFile();
		return (file != null) && file.exists();
	}
	
	public IFile getFile() {
		IFile file = this.getFile(this.uri);
		return (file != null) ? file : this.getConvertedURIFile();
	}
	
	protected IFile getConvertedURIFile() {
		if (this.resourceSet == null) {
			return null;
		}
		URI convertedURI = this.resourceSet.getURIConverter().normalize(this.uri);
		return this.uri.equals(convertedURI) ? null : this.getFile(convertedURI);
	}
	
	/**
	 * Return the Eclipse file for the specified URI.
	 * This URI is assumed to be absolute in the following format:<pre>
	 *     platform:/resource/....
	 * </pre>
	 */
	protected IFile getFile(URI fileURI) {
		if ( ! WorkbenchResourceHelperBase.isPlatformResourceURI(fileURI)) {
			return null;
		}
		String fileName = URI.decode(fileURI.path()).substring(JEMUtilPlugin.PLATFORM_RESOURCE.length() + 1);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
	}
	
	public void save() {
		try {
			this.save(Collections.EMPTY_MAP);
		} catch (IOException ex) {
			JptCommonCorePlugin.instance().logError(ex);
		}
	}
	
	@Override
	public String toString() {
		// implementation in TranslatorResourceImpl is a bit off...
		return ObjectTools.toString(this, this.getURI());
	}
	
	
	// ********** JptResourceModel implementation **********
	
	public void addResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}
	
	public void removeResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}


	// ********** listener notifications **********

	protected void resourceModelChanged() {
		for (JptResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			try {
				listener.resourceModelChanged(this);
			} catch (Exception ex) {
				JptCommonCorePlugin.instance().logError(ex);
			}
		}
	}

	protected void resourceModelReverted() {
		for (JptResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			try {
				listener.resourceModelReverted(this);
			} catch (Exception ex) {
				JptCommonCorePlugin.instance().logError(ex);
			}
		}
	}

	protected void resourceModelUnloaded() {
		for (JptResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			try {
				listener.resourceModelUnloaded(this);
			} catch (Exception ex) {
				JptCommonCorePlugin.instance().logError(ex);
			}
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
