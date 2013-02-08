/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.platform;

import java.util.ArrayList;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.utility.ConfigurationElementTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.InternalJaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUiManager;

/**
 * JAXB platform UI manager.
 */
public class InternalJaxbPlatformUiManager 
	implements JaxbPlatformUiManager
{
	/**
	 * The JAXB platform UI manager's Dali JAXB workbench.
	 */
	private final InternalJaxbWorkbench jaxbWorkbench;

	/**
	 * The JAXB platform UI configs.
	 * Initialized during construction.
	 */
	private final ArrayList<JaxbPlatformUiConfig> jaxbPlatformUiConfigs = new ArrayList<JaxbPlatformUiConfig>();


	// ********** extension point element and attribute names **********

	private static final String SIMPLE_EXTENSION_POINT_NAME = "jaxbPlatformUis"; //$NON-NLS-1$
	private static final String JAXB_PLATFORM_UI_ELEMENT = "jaxbPlatformUi"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String JAXB_PLATFORM_ATTRIBUTE = "jaxbPlatform"; //$NON-NLS-1$


	/**
	 * Internal - called from only
	 * {@link InternalJaxbWorkbench#buildJaxbPlatformUiManager()}.
	 */
	public InternalJaxbPlatformUiManager(InternalJaxbWorkbench jaxbWorkbench) {
		super();
		this.jaxbWorkbench = jaxbWorkbench;
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
				if (elementName.equals(JAXB_PLATFORM_UI_ELEMENT)) {
					JaxbPlatformUiConfig config = this.buildJaxbPlatformUiConfig(element);
					if (config != null) {
						this.jaxbPlatformUiConfigs.add(config);
					}
				}
			}
		}
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * JAXB platform UI config from the specified configuration element.
	 */
	private JaxbPlatformUiConfig buildJaxbPlatformUiConfig(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (StringTools.isBlank(id)) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.containsJaxbPlatformUiConfig(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate JAXB platform UIs
		}

		// class name
		String className = element.getAttribute(CLASS_ATTRIBUTE);
		if (className == null) {
			this.logMissingAttribute(element, CLASS_ATTRIBUTE);
			return null;
		}

		// JAXB platform ID
		String jaxbPlatformID = element.getAttribute(JAXB_PLATFORM_ATTRIBUTE);
		if (jaxbPlatformID == null) {
			this.logMissingAttribute(element, JAXB_PLATFORM_ATTRIBUTE);
			return null;
		}
		if (this.containsJaxbPlatformUiConfigForJaxbPlatform(jaxbPlatformID)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, JAXB_PLATFORM_ATTRIBUTE, jaxbPlatformID);
			return null;  // drop any duplicate JAXB platform UIs
		}
		if (this.jaxbPlatformConfigIsMissing(jaxbPlatformID)) {
			this.logMissingJaxbPlatform(element, jaxbPlatformID);
			return null;
		}

		JaxbPlatformUiConfig config = new JaxbPlatformUiConfig(this, id, className, jaxbPlatformID);
		config.setPluginID(contributor);
		return config;
	}


	// ********** JAXB platform UIs **********

	public JaxbPlatformUi getJaxbPlatformUi(JaxbPlatform jaxbPlatform) {
		JaxbPlatformUiConfig config = this.getJaxbPlatformUiConfigForJaxbPlatform(jaxbPlatform.getConfig().getId());
		return (config == null) ? null : config.getJaxbPlatformUi();
	}

	private boolean containsJaxbPlatformUiConfig(String id) {
		return this.getJaxbPlatformUiConfig(id) != null;
	}

	private JaxbPlatformUiConfig getJaxbPlatformUiConfig(String id) {
		for (JaxbPlatformUiConfig config : this.jaxbPlatformUiConfigs) {
			if (config.getID().equals(id)) {
				return config;
			}
		}
		return null;
	}

	private boolean containsJaxbPlatformUiConfigForJaxbPlatform(String jaxbPlatformID) {
		return this.getJaxbPlatformUiConfigForJaxbPlatform(jaxbPlatformID) != null;
	}

	private JaxbPlatformUiConfig getJaxbPlatformUiConfigForJaxbPlatform(String jaxbPlatformID) {
		for (JaxbPlatformUiConfig config : this.jaxbPlatformUiConfigs) {
			if (config.getJaxbPlatformID().equals(jaxbPlatformID)) {
				return config;
			}
		}
		return null;
	}


	// ********** logging **********

	private void logMissingJaxbPlatform(IConfigurationElement element, String jaxbPlatformID) {
		this.logError(JptJaxbUiMessages.JAXB_PLATFORM_UI_MISSING_JAXB_PLATFORM,
				jaxbPlatformID,
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getContributor().getName()
			);
	}

	private void logError(String msg, Object... args) {
		this.getPlugin().logError(msg, args);
	}

	private void logMissingAttribute(IConfigurationElement element, String attributeName) {
		this.getPlugin().logError(ConfigurationElementTools.buildMissingAttributeMessage(element, attributeName));
	}


	// ********** misc **********

	public InternalJaxbWorkbench getJaxbWorkbench() {
		return this.jaxbWorkbench;
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

	private JptJaxbUiPlugin getPlugin() {
		return JptJaxbUiPlugin.instance();
	}

	/**
	 * Check for the config; although, we may still be unable to instantiate
	 * the actual JAXB platform....
	 */
	private boolean jaxbPlatformConfigIsMissing(String jaxbPlatformID) {
		return this.getJaxbPlatformConfig(jaxbPlatformID) == null;
	}

	private JaxbPlatformConfig getJaxbPlatformConfig(String jaxbPlatformID) {
		JaxbPlatformManager jaxbPlatformManager = this.getJaxbPlatformManager();
		return (jaxbPlatformManager == null) ? null : jaxbPlatformManager.getJaxbPlatformConfig(jaxbPlatformID);
	}

	private JaxbPlatformManager getJaxbPlatformManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbPlatformManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		return this.jaxbWorkbench.getJaxbWorkspace();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.jaxbPlatformUiConfigs);
	}
}
