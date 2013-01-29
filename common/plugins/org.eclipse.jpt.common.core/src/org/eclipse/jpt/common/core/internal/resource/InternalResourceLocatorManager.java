/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource;

import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.InternalJptWorkspace;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.resource.ResourceLocatorConfig.Priority;
import org.eclipse.jpt.common.core.internal.utility.ConfigurationElementTools;
import org.eclipse.jpt.common.core.resource.ResourceLocator;
import org.eclipse.jpt.common.core.resource.ResourceLocatorManager;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.filter.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;

/**
 * Resource locator manager.
 */
public class InternalResourceLocatorManager
	implements ResourceLocatorManager
{
	/**
	 * The resource locator manager's Dali workspace.
	 */
	private final InternalJptWorkspace jptWorkspace;

	/**
	 * The resource locator configs.
	 * Initialized during construction.
	 */
	private final ArrayList<ResourceLocatorConfig> resourceLocatorConfigs = new ArrayList<ResourceLocatorConfig>();


	// ********** extension point element and attribute names **********

	private static final String SIMPLE_EXTENSION_POINT_NAME = "resourceLocators"; //$NON-NLS-1$
	private static final String RESOURCE_LOCATOR_ELEMENT = "resourceLocator"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String PRIORITY_ATTRIBUTE = "priority"; //$NON-NLS-1$
	private static final String ENABLEMENT_ELEMENT = "enablement"; //$NON-NLS-1$


	/**
	 * Internal - called from only
	 * {@link InternalJptWorkspace#buildResourceLocatorManager()}.
	 */
	public InternalResourceLocatorManager(InternalJptWorkspace jptWorkspace) {
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

		for (IExtension extension : extensionPoint.getExtensions()) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				String elementName = element.getName();  // probably cannot be null
				if (elementName.equals(RESOURCE_LOCATOR_ELEMENT)) {
					ResourceLocatorConfig config = this.buildResourceLocatorConfig(element);
					if (config != null) {
						this.resourceLocatorConfigs.add(config);
					}
				}
			}
		}
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * resource locator config from the specified configuration element.
	 */
	private ResourceLocatorConfig buildResourceLocatorConfig(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (StringTools.isBlank(id)) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.containsResourceLocatorConfig(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate resource locators
		}

		// resource locator class name
		String className = element.getAttribute(CLASS_ATTRIBUTE);
		if (className == null) {
			this.logMissingAttribute(element, CLASS_ATTRIBUTE);
			return null;
		}

		// priority (optional - default is NORMAL)
		String priorityString = element.getAttribute(PRIORITY_ATTRIBUTE).trim();
		Priority priority = Priority.get(priorityString);
		if (priority == null) {
			this.logInvalidPriority(element, priorityString);
			return null;
		}

		ResourceLocatorConfig config = new ResourceLocatorConfig(this, id, className, priority);

		// enablement (optional, but only one allowed)
		for (IConfigurationElement child : element.getChildren()) {
			if (child.getName().equals(ENABLEMENT_ELEMENT)) {
				if (config.getEnablementExpression() != null) {
					this.logMultipleEnablements(element);
					return null;
				}
				try {
					config.setEnablementExpression(ExpressionConverter.getDefault().perform(child));
				} catch (CoreException ex) {
					JptCommonCorePlugin.instance().logError(ex);
					return null;
				}
			}
		}

		config.setPluginID(contributor);
		return config;
	}


	// ********** resource locators **********

	public Iterable<ResourceLocator> getResourceLocators() {
		return new FilteringIterable<ResourceLocator>(
				this.getResourceLocators_(),
				NotNullFilter.<ResourceLocator>instance()
			);
	}

	/**
	 * Result may contain <code>null</code>s.
	 */
	private Iterable<ResourceLocator> getResourceLocators_() {
		return new TransformationIterable<ResourceLocatorConfig, ResourceLocator>(
				this.resourceLocatorConfigs,
				ResourceLocatorConfig.RESOURCE_LOCATOR_TRANSFORMER
			);
	}

	public ResourceLocator getResourceLocator(IProject project) {
		Iterator<ResourceLocator> stream = this.getResourceLocators(project).iterator();
		return (stream.hasNext()) ? stream.next() : null;
	}

	public Iterable<ResourceLocator> getResourceLocators(IProject project) {
		return new FilteringIterable<ResourceLocator>(
				this.getResourceLocators_(project),
				NotNullFilter.<ResourceLocator>instance()
			);
	}

	/**
	 * Result may contain <code>null</code>s.
	 */
	private Iterable<ResourceLocator> getResourceLocators_(IProject project) {
		return new TransformationIterable<ResourceLocatorConfig, ResourceLocator>(
				this.getSortedResourceLocatorConfigs(project),
				ResourceLocatorConfig.RESOURCE_LOCATOR_TRANSFORMER
			);
	}

	/**
	 * Return the resource locator configs enabled for the specified
	 * install config, sorted by priority.
	 */
	private Iterable<ResourceLocatorConfig> getSortedResourceLocatorConfigs(IProject project) {
		return IterableTools.sort(this.getResourceLocatorConfigs(project));
	}

	/**
	 * Return the resource locator configs enabled for the specified
	 * install config.
	 */
	private Iterable<ResourceLocatorConfig> getResourceLocatorConfigs(IProject project) {
		return new FilteringIterable<ResourceLocatorConfig>(
				this.resourceLocatorConfigs,
				new ResourceLocatorConfig.EnabledFilter(project)
			);
	}

	private boolean containsResourceLocatorConfig(String id) {
		return this.getResourceLocatorConfig(id) != null;
	}

	private ResourceLocatorConfig getResourceLocatorConfig(String id) {
		for (ResourceLocatorConfig config : this.resourceLocatorConfigs) {
			if (config.getID().equals(id)) {
				return config;
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

	private void logInvalidPriority(IConfigurationElement element, String priority) {
		this.getPlugin().logError(JptCommonCoreMessages.INVALID_RESOURCE_LOCATOR_PRIORITY,
				priority,
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getContributor().getName()
			);
	}

	private void logMultipleEnablements(IConfigurationElement element) {
		this.getPlugin().logError(JptCommonCoreMessages.MULTIPLE_RESOURCE_LOCATOR_ENABLEMENTS,
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getContributor().getName()
			);
	}


	// ********** misc **********

	public InternalJptWorkspace getJptWorkspace() {
		return this.jptWorkspace;
	}

	String getExtensionPointName() {
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
		return ObjectTools.toString(this, this.resourceLocatorConfigs);
	}
}
