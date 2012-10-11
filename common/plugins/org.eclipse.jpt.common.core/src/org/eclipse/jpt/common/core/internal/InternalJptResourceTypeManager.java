/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.JptResourceTypeManager;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.ConfigurationElementTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;

/**
 * Resource type manager.
 */
public class InternalJptResourceTypeManager
	implements JptResourceTypeManager
{
	private final InternalJptWorkspace jptWorkspace;

	/**
	 * The defined resource types.
	 * Initialized during construction.
	 */
	private final ArrayList<InternalJptResourceType> resourceTypes = new ArrayList<InternalJptResourceType>();


	// ********** extension point element and attribute names **********

	private static final String SIMPLE_EXTENSION_POINT_NAME = "resourceTypes"; //$NON-NLS-1$
	private static final String RESOURCE_TYPE_ELEMENT = "resource-type"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CONTENT_TYPE_ATTRIBUTE = "content-type"; //$NON-NLS-1$
	private static final String VERSION_ATTRIBUTE = "version"; //$NON-NLS-1$
	private static final String BASE_TYPE_ELEMENT = "base-type"; //$NON-NLS-1$


	/**
	 * Internal - called from only
	 * {@link InternalJptWorkspace#buildResourceTypeManager()}.
	 */
	public InternalJptResourceTypeManager(InternalJptWorkspace jptWorkspace) {
		super();
		this.jptWorkspace = jptWorkspace;
		this.initialize();
	}


	// ********** initialization **********

	private void initialize() {
		IExtensionPoint extensionPoint = this.getExtensionPoint();
		if (extensionPoint == null) {
			throw new IllegalStateException("missing extension point: " + this.getExtensionPointName()); //$NON-NLS-1$
		}

		ArrayList<IConfigurationElement> resourceTypeElements = new ArrayList<IConfigurationElement>();

		for (IExtension extension : extensionPoint.getExtensions()) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				String elementName = element.getName();  // probably cannot be null
				if (elementName.equals(RESOURCE_TYPE_ELEMENT)) {
					resourceTypeElements.add(element);
				}
			}
		}

		// build up the resource type stubs...
		LinkedList<SimpleAssociation<IConfigurationElement, InternalJptResourceType>> stubs = new LinkedList<SimpleAssociation<IConfigurationElement, InternalJptResourceType>>();
		for (IConfigurationElement element : resourceTypeElements) {
			InternalJptResourceType resourceType = this.buildResourceType(element);
			if (resourceType != null) {
				stubs.add(new SimpleAssociation<IConfigurationElement, InternalJptResourceType>(element, resourceType));
			}
		}

		// ...then make multiple further passes to add the base resource types
		int prev = -1;
		while (this.resourceTypes.size() != prev) {
			prev = this.resourceTypes.size();
			this.buildBaseTypes(stubs.iterator());
		}

		// the remaining stub resource types have invalid base types
		for (SimpleAssociation<IConfigurationElement, InternalJptResourceType> assoc : stubs) {
			IConfigurationElement resourceTypeElement = assoc.getKey();
			for (IConfigurationElement baseTypeElement : resourceTypeElement.getChildren(BASE_TYPE_ELEMENT)) {
				String baseTypeID = baseTypeElement.getAttribute(ID_ATTRIBUTE);
				InternalJptResourceType baseType = this.getResourceType(baseTypeID);
				if (baseType == null) {
					this.logInvalidBaseType(assoc.getValue(), baseTypeElement, baseTypeID);
				}
			}
		}
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * resource type from the specified configuration element.
	 */
	private InternalJptResourceType buildResourceType(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (StringTools.isBlank(id)) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.containsResourceType(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate resource types
		}

		// content type
		String contentTypeID = element.getAttribute(CONTENT_TYPE_ATTRIBUTE);
		if (contentTypeID == null) {
			this.logMissingAttribute(element, CONTENT_TYPE_ATTRIBUTE);
			return null;
		}
		IContentType contentType = this.getContentType(contentTypeID);
		if (contentType == null) {
			this.logInvalidContentType(element, id, contentTypeID);
			return null;
		}

		// version (optional)
		String version = element.getAttribute(VERSION_ATTRIBUTE);
		if (version == null) {
			version = JptResourceType.UNDETERMINED_VERSION;
		}

		if (this.containsResourceType(contentType, version)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate resource types
		}

		InternalJptResourceType resourceType = new InternalJptResourceType(this, id, contentType, version);
		resourceType.setPluginId(contributor);
		return resourceType;
	}

	private IContentType getContentType(String contentTypeID) {
		return Platform.getContentTypeManager().getContentType(contentTypeID);
	}

	/**
	 * Loop over the specified "stub" resource types, resolving the base types
	 * for each resource type and, if they are all resolved, set the resource
	 * type's base types and add the resource type to the manager's master
	 * list. As more resource types are added to the manager's master list,
	 * more base types will be resolved with later passes. This method is called
	 * repeatedly, until no more base types can be resolved.
	 * <p>
	 * <strong>NB:</strong> As a result of only adding resource types with
	 * resolved base types, there will be no loops in the "hierarchy" tree.
	 */
	private void buildBaseTypes(Iterator<SimpleAssociation<IConfigurationElement, InternalJptResourceType>> stubStream) {
		while (stubStream.hasNext()) {
			SimpleAssociation<IConfigurationElement, InternalJptResourceType> next = stubStream.next();
			IConfigurationElement resourceTypeElement = next.getKey();
			InternalJptResourceType resourceType = next.getValue();
			IConfigurationElement[] baseTypeElements = resourceTypeElement.getChildren(BASE_TYPE_ELEMENT);
			boolean validBaseTypes = true;
			HashSet<InternalJptResourceType> baseTypes = new HashSet<InternalJptResourceType>(baseTypeElements.length);
			for (IConfigurationElement baseTypeElement : baseTypeElements) {
				String baseTypeID = baseTypeElement.getAttribute(ID_ATTRIBUTE);
				if (baseTypeID == null) {
					this.logMissingAttribute(baseTypeElement, ID_ATTRIBUTE);
					stubStream.remove();  // remove from further passes
					validBaseTypes = false;
					break;
				}
				InternalJptResourceType baseType = this.getResourceType(baseTypeID);
				if (baseType != null) {
					baseTypes.add(baseType);
				} else {
					// leave for further passes
					validBaseTypes = false;
					break;
				}
			}
			// if all the base types are valid, set them on the resource type and
			// remove the resource type from the list of stubs and add it to the master list
			if (validBaseTypes) {
				resourceType.setBaseTypes(baseTypes);
				stubStream.remove();
				this.resourceTypes.add(resourceType);
			}
		}
	}


	// ********** resource types **********

	public Iterable<JptResourceType> getResourceTypes() {
		return new SuperIterableWrapper<JptResourceType>(this.resourceTypes);
	}

	public Iterable<JptResourceType> getResourceTypes(IContentType contentType) {
		return new FilteringIterable<JptResourceType>(this.getResourceTypes(), new InternalJptResourceType.ContentTypeFilter(contentType));
	}

	private boolean containsResourceType(String id) {
		return this.getResourceType(id) != null;
	}

	private InternalJptResourceType getResourceType(String id) {
		for (InternalJptResourceType resourceType : this.resourceTypes) {
			if (resourceType.getId().equals(id)) {
				return resourceType;
			}
		}
		return null;
	}

	public JptResourceType getResourceType(IContentType contentType) {
		return this.getResourceType(contentType, JptResourceType.UNDETERMINED_VERSION);
	}

	private boolean containsResourceType(IContentType contentType, String version) {
		return this.getResourceType(contentType, version) != null;
	}

	public JptResourceType getResourceType(IContentType contentType, String version) {
		for (JptResourceType resourceType : this.resourceTypes) {
			if (resourceType.getContentType().equals(contentType) && resourceType.getVersion().equals(version)) {
				return resourceType;
			}
		}
		return null;
	}


	// ********** logging **********

	private void logError(String msg, Object... args) {
		this.getPlugin().logError(msg, args);
	}

	private void logMissingAttribute(IConfigurationElement element, String attributeName) {
		this.getPlugin().logError(ConfigurationElementTools.buildMissingAttributeMessage(element, attributeName));
	}

	private void logInvalidContentType(IConfigurationElement element, String resourceTypeID, String contentTypeID) {
		this.getPlugin().logError(JptCommonCoreMessages.RESOURCE_TYPE_INVALID_CONTENT_TYPE,
				contentTypeID,
				resourceTypeID,
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getContributor().getName()
			);
	}

	private void logInvalidBaseType(InternalJptResourceType resourceType, IConfigurationElement element, String baseTypeID) {
		this.getPlugin().logError(JptCommonCoreMessages.RESOURCE_TYPE_INVALID_BASE_TYPE,
				baseTypeID,
				resourceType.getId(),
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getContributor().getName()
			);
	}


	// ********** misc **********

	public InternalJptWorkspace getJptWorkspace() {
		return this.jptWorkspace;
	}

	private String getExtensionPointName() {
		return this.getPluginID() + '.' + SIMPLE_EXTENSION_POINT_NAME;
	}

	private IExtensionPoint getExtensionPoint() {
		return this.getExtensionRegistry().getExtensionPoint(this.getPluginID(), SIMPLE_EXTENSION_POINT_NAME);
	}

	private IExtensionRegistry getExtensionRegistry() {
		return RegistryFactory.getRegistry();
	}

	private String getPluginID() {
		return this.getPlugin().getPluginID();
	}

	private JptCommonCorePlugin getPlugin() {
		return JptCommonCorePlugin.instance();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.resourceTypes);
	}
}
