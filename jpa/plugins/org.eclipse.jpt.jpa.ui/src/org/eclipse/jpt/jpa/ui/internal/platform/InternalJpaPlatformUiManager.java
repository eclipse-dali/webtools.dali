/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform;

import java.util.ArrayList;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.utility.ExtensionPointTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiManager;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.InternalJpaWorkbench;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;

/**
 * JPA platform UI manager.
 */
public class InternalJpaPlatformUiManager 
	implements JpaPlatformUiManager
{
	/**
	 * The JPA platform UI manager's Dali JPA workbench.
	 */
	private final InternalJpaWorkbench jpaWorkbench;

	/**
	 * The JPA platform UI configs.
	 * Initialized during construction.
	 */
	private final ArrayList<JpaPlatformUiConfig> jpaPlatformUiConfigs = new ArrayList<JpaPlatformUiConfig>();


	// ********** extension point element and attribute names **********

	private static final String SIMPLE_EXTENSION_POINT_NAME = "jpaPlatformUis"; //$NON-NLS-1$
	private static final String JPA_PLATFORM_UI_ELEMENT = "jpaPlatformUi"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String FACTORY_CLASS_ATTRIBUTE = "factoryClass"; //$NON-NLS-1$
	private static final String JPA_PLATFORM_ATTRIBUTE = "jpaPlatform"; //$NON-NLS-1$


	/**
	 * Internal - called from only
	 * {@link InternalJpaWorkbench#buildJpaPlatformUiManager()}.
	 */
	public InternalJpaPlatformUiManager(InternalJpaWorkbench jpaWorkbench) {
		super();
		this.jpaWorkbench = jpaWorkbench;
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
				if (elementName.equals(JPA_PLATFORM_UI_ELEMENT)) {
					JpaPlatformUiConfig config = this.buildJpaPlatformUiConfig(element);
					if (config != null) {
						this.jpaPlatformUiConfigs.add(config);
					}
				}
			}
		}
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * JPA platform UI config from the specified configuration element.
	 */
	private JpaPlatformUiConfig buildJpaPlatformUiConfig(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (StringTools.isBlank(id)) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.containsJpaPlatformUiConfig(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate JPA platform UIs
		}

		// factory class name
		String factoryClassName = element.getAttribute(FACTORY_CLASS_ATTRIBUTE);
		if (factoryClassName == null) {
			this.logMissingAttribute(element, FACTORY_CLASS_ATTRIBUTE);
			return null;
		}

		// JPA platform ID
		String jpaPlatformID = element.getAttribute(JPA_PLATFORM_ATTRIBUTE);
		if (jpaPlatformID == null) {
			this.logMissingAttribute(element, JPA_PLATFORM_ATTRIBUTE);
			return null;
		}
		if (this.containsJpaPlatformUiConfigForJpaPlatform(jpaPlatformID)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, JPA_PLATFORM_ATTRIBUTE, jpaPlatformID);
			return null;  // drop any duplicate JPA platform UIs
		}
		if (this.jpaPlatformConfigIsMissing(jpaPlatformID)) {
			this.logMissingJpaPlatform(element, jpaPlatformID);
			return null;
		}

		JpaPlatformUiConfig config = new JpaPlatformUiConfig(this, id, factoryClassName, jpaPlatformID);
		config.setPluginID(contributor);
		return config;
	}


	// ********** JPA platform UIs **********

	public JpaPlatformUi getJpaPlatformUi(JpaPlatform jpaPlatform) {
		JpaPlatformUiConfig config = this.getJpaPlatformUiConfigForJpaPlatform(jpaPlatform.getId());
		return (config == null) ? null : config.getJpaPlatformUi();
	}

	private boolean containsJpaPlatformUiConfig(String id) {
		return this.getJpaPlatformUiConfig(id) != null;
	}

	private JpaPlatformUiConfig getJpaPlatformUiConfig(String id) {
		for (JpaPlatformUiConfig config : this.jpaPlatformUiConfigs) {
			if (config.getID().equals(id)) {
				return config;
			}
		}
		return null;
	}

	private boolean containsJpaPlatformUiConfigForJpaPlatform(String jpaPlatformID) {
		return this.getJpaPlatformUiConfigForJpaPlatform(jpaPlatformID) != null;
	}

	private JpaPlatformUiConfig getJpaPlatformUiConfigForJpaPlatform(String jpaPlatformID) {
		for (JpaPlatformUiConfig config : this.jpaPlatformUiConfigs) {
			if (config.getJpaPlatformID().equals(jpaPlatformID)) {
				return config;
			}
		}
		return null;
	}


	// ********** logging **********

	private void logMissingJpaPlatform(IConfigurationElement element, String jpaPlatformID) {
		this.logError(JptJpaUiMessages.JpaPlatformUi_missingJpaPlatform,
				jpaPlatformID,
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getContributor().getName()
			);
	}

	private void logError(String msg, Object... args) {
		this.getPlugin().logError(msg, args);
	}

	private void logMissingAttribute(IConfigurationElement element, String attributeName) {
		this.getPlugin().logError(ExtensionPointTools.buildMissingAttributeMessage(element, attributeName));
	}


	// ********** misc **********

	public InternalJpaWorkbench getJpaWorkbench() {
		return this.jpaWorkbench;
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

	private JptJpaUiPlugin getPlugin() {
		return JptJpaUiPlugin.instance();
	}

	/**
	 * Check for the config; although, we may still be unable to instantiate
	 * the actual JPA platform....
	 */
	private boolean jpaPlatformConfigIsMissing(String jpaPlatformID) {
		return this.getJpaPlatformConfig(jpaPlatformID) == null;
	}

	private JpaPlatform.Config getJpaPlatformConfig(String jpaPlatformID) {
		JpaPlatformManager jpaPlatformManager = this.getJpaPlatformManager();
		return (jpaPlatformManager == null) ? null : jpaPlatformManager.getJpaPlatformConfig(jpaPlatformID);
	}

	private JpaPlatformManager getJpaPlatformManager() {
		JpaWorkspace jpaWorkspace = this.getJpaWorkspace();
		return (jpaWorkspace == null) ? null : jpaWorkspace.getJpaPlatformManager();
	}

	private JpaWorkspace getJpaWorkspace() {
		return this.jpaWorkbench.getJpaWorkspace();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.jpaPlatformUiConfigs);
	}
}
