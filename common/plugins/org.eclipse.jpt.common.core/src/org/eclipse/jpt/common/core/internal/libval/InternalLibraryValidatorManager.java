/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.libval;

import java.util.ArrayList;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.InternalJptWorkspace;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.ConfigurationElementTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.core.libval.LibraryValidatorManager;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.filter.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;

/**
 * Library validator manager.
 */
public class InternalLibraryValidatorManager
	implements LibraryValidatorManager
{
	/**
	 * The library validator manager's Dali workspace.
	 */
	private final InternalJptWorkspace jptWorkspace;

	/**
	 * The library validator configs.
	 * Initialized during construction.
	 */
	private final ArrayList<LibraryValidatorConfig> libraryValidatorConfigs = new ArrayList<LibraryValidatorConfig>();


	// ********** extension point element and attribute names **********

	private static final String SIMPLE_EXTENSION_POINT_NAME = "libraryValidators"; //$NON-NLS-1$
	private static final String LIBRARY_VALIDATOR_ELEMENT = "libraryValidator"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String ENABLEMENT_ELEMENT = "enablement"; //$NON-NLS-1$


	/**
	 * Internal - called from only
	 * {@link InternalJptWorkspace#buildLibraryValidatorManager()}.
	 */
	public InternalLibraryValidatorManager(InternalJptWorkspace jptWorkspace) {
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
				if (elementName.equals(LIBRARY_VALIDATOR_ELEMENT)) {
					LibraryValidatorConfig config = this.buildLibraryValidatorConfig(element);
					if (config != null) {
						this.libraryValidatorConfigs.add(config);
					}
				}
			}
		}
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * library validator config from the specified configuration element.
	 */
	private LibraryValidatorConfig buildLibraryValidatorConfig(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (StringTools.isBlank(id)) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.containsLibraryValidatorConfig(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate library validators
		}

		// library validator class name
		String className = element.getAttribute(CLASS_ATTRIBUTE);
		if (className == null) {
			this.logMissingAttribute(element, CLASS_ATTRIBUTE);
			return null;
		}

		LibraryValidatorConfig config = new LibraryValidatorConfig(this, id, className);

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


	// ********** library validators **********

	public Iterable<LibraryValidator> getLibraryValidators() {
		return new FilteringIterable<LibraryValidator>(
				this.getLibraryValidators_(),
				NotNullFilter.<LibraryValidator>instance()
			);
	}

	/**
	 * Result may contain <code>null</code>s.
	 */
	private Iterable<LibraryValidator> getLibraryValidators_() {
		return new TransformationIterable<LibraryValidatorConfig, LibraryValidator>(
				this.libraryValidatorConfigs,
				LibraryValidatorConfig.LIBRARY_VALIDATOR_TRANSFORMER
			);
	}

	public Iterable<LibraryValidator> getLibraryValidators(JptLibraryProviderInstallOperationConfig installConfig) {
		return new FilteringIterable<LibraryValidator>(
				this.getLibraryValidators_(installConfig),
				NotNullFilter.<LibraryValidator>instance()
			);
	}

	/**
	 * Result may contain <code>null</code>s.
	 */
	private Iterable<LibraryValidator> getLibraryValidators_(JptLibraryProviderInstallOperationConfig installConfig) {
		return new TransformationIterable<LibraryValidatorConfig, LibraryValidator>(
				this.getLibraryValidatorConfigs(installConfig),
				LibraryValidatorConfig.LIBRARY_VALIDATOR_TRANSFORMER
			);
	}

	/**
	 * Return the library validator configs enabled for the specified
	 * install config.
	 */
	private Iterable<LibraryValidatorConfig> getLibraryValidatorConfigs(JptLibraryProviderInstallOperationConfig installConfig) {
		return new FilteringIterable<LibraryValidatorConfig>(
				this.libraryValidatorConfigs,
				new LibraryValidatorConfig.EnabledFilter(installConfig)
			);
	}

	private boolean containsLibraryValidatorConfig(String id) {
		return this.getLibraryValidatorConfig(id) != null;
	}

	private LibraryValidatorConfig getLibraryValidatorConfig(String id) {
		for (LibraryValidatorConfig config : this.libraryValidatorConfigs) {
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

	private void logMultipleEnablements(IConfigurationElement element) {
		this.getPlugin().logError(JptCommonCoreMessages.MULTIPLE_LIBRARY_VALIDATOR_ENABLEMENTS,
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
		return ObjectTools.toString(this, this.libraryValidatorConfigs);
	}
}
