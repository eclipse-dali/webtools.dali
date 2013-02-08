/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.utility.ConfigurationElementTools;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.InternalJaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * JAXB platform manager.
 */
public class InternalJaxbPlatformManager
	implements JaxbPlatformManager
{
	/**
	 * The JAXB platform manager's JAXB workspace.
	 */
	private final InternalJaxbWorkspace jaxbWorkspace;

	/**
	 * The JAXB platform group configs, keyed by ID.
	 * Initialized during construction.
	 */
	private final HashMap<String, InternalJaxbPlatformGroupConfig> jaxbPlatformGroupConfigs = new HashMap<String, InternalJaxbPlatformGroupConfig>();

	/**
	 * The JAXB platform configs, keyed by ID.
	 * Initialized during construction.
	 */
	private final HashMap<String, InternalJaxbPlatformConfig> jaxbPlatformConfigs = new HashMap<String, InternalJaxbPlatformConfig>();


	// ********** extension point element and attribute names **********

	private static final String SIMPLE_EXTENSION_POINT_NAME = "jaxbPlatforms"; //$NON-NLS-1$
	private static final String JAXB_PLATFORM_GROUP_ELEMENT = "jaxbPlatformGroup"; //$NON-NLS-1$
	private static final String JAXB_PLATFORM_ELEMENT = "jaxbPlatform"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String LABEL_ATTRIBUTE = "label"; //$NON-NLS-1$
	private static final String FACTORY_CLASS_ATTRIBUTE = "factoryClass"; //$NON-NLS-1$
	private static final String JAXB_FACET_VERSION_ATTRIBUTE = "jaxbFacetVersion"; //$NON-NLS-1$
	private static final String DEFAULT_ATTRIBUTE = "default"; //$NON-NLS-1$
	private static final String GROUP_ATTRIBUTE = "group";  //$NON-NLS-1$


	/**
	 * Internal - called from only
	 * {@link InternalJaxbWorkspace#buildJaxbPlatformManager()}.
	 */
	public InternalJaxbPlatformManager(InternalJaxbWorkspace jaxbWorkspace) {
		super();
		this.jaxbWorkspace = jaxbWorkspace;
		this.initialize();
	}


	// ********** initialization **********

	private void initialize() {
		IExtensionPoint extensionPoint = this.getExtensionPoint();
		if (extensionPoint == null) {
			throw new IllegalStateException("missing extension point: " + this.getExtensionPointName()); //$NON-NLS-1$
		}

		ArrayList<IConfigurationElement> jaxbPlatformGroupElements = new ArrayList<IConfigurationElement>();
		ArrayList<IConfigurationElement> jaxbPlatformElements = new ArrayList<IConfigurationElement>();

		for (IExtension extension : extensionPoint.getExtensions()) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				String elementName = element.getName();  // probably cannot be null
				if (elementName.equals(JAXB_PLATFORM_GROUP_ELEMENT)) {
					jaxbPlatformGroupElements.add(element);
				}
				else if (elementName.equals(JAXB_PLATFORM_ELEMENT)) {
					jaxbPlatformElements.add(element);
				}
			}
		}

		// build the groups first so the platforms can be added as they are built
		for (IConfigurationElement element : jaxbPlatformGroupElements) {
			InternalJaxbPlatformGroupConfig config = this.buildPlatformGroupConfig(element);
			if (config != null) {
				this.jaxbPlatformGroupConfigs.put(config.getId(), config);
			}
		}

		for (IConfigurationElement element : jaxbPlatformElements) {
			InternalJaxbPlatformConfig config = this.buildPlatformConfig(element);
			if (config != null) {
				this.jaxbPlatformConfigs.put(config.getId(), config);
			}
		}
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * group config from the specified configuration element.
	 */
	private InternalJaxbPlatformGroupConfig buildPlatformGroupConfig(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (id == null) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.jaxbPlatformGroupConfigs.containsKey(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate platform groups
		}

		// label
		String label = element.getAttribute(LABEL_ATTRIBUTE);
		if (label == null) {
			this.logMissingAttribute(element, LABEL_ATTRIBUTE);
			return null;
		}

		InternalJaxbPlatformGroupConfig config = new InternalJaxbPlatformGroupConfig(this, id, label);
		config.setPluginId(contributor);
		return config;
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * platform config from the specified configuration element.
	 */
	private InternalJaxbPlatformConfig buildPlatformConfig(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (id == null) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.jaxbPlatformConfigs.containsKey(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate platforms
		}

		// label
		String label = element.getAttribute(LABEL_ATTRIBUTE);
		if (label == null) {
			this.logMissingAttribute(element, LABEL_ATTRIBUTE);
			return null;
		}

		// factory class name
		String factoryClassName = element.getAttribute(FACTORY_CLASS_ATTRIBUTE);
		if (factoryClassName == null) {
			this.logMissingAttribute(element, FACTORY_CLASS_ATTRIBUTE);
			return null;
		}

		InternalJaxbPlatformConfig config = new InternalJaxbPlatformConfig(this, id, label, factoryClassName);

		// JAXB facet version
		String jaxbFacetVersionString = element.getAttribute(JAXB_FACET_VERSION_ATTRIBUTE);
		if (jaxbFacetVersionString != null) {
			IProjectFacetVersion jaxbFacetVersion = JaxbProject.FACET.getVersion(jaxbFacetVersionString);
			if (jaxbFacetVersion == null) {
				this.logInvalidValue(element, JAXB_FACET_VERSION_ATTRIBUTE, jaxbFacetVersionString);
				return null;
			}
			config.setJaxbFacetVersion(jaxbFacetVersion);
		}

		// default
		String defaultString = element.getAttribute(DEFAULT_ATTRIBUTE);
		if (defaultString != null) {
			Boolean default_ = defaultString.equals("true") ? Boolean.TRUE : defaultString.equals("false") ? Boolean.FALSE : null; //$NON-NLS-1$ //$NON-NLS-2$
			if (default_ == null) {
				this.logInvalidValue(element, DEFAULT_ATTRIBUTE, defaultString);
				return null;
			}
			config.setDefault(default_.booleanValue());
		}

		// group
		String groupID = element.getAttribute(GROUP_ATTRIBUTE);
		if (groupID != null) {
			InternalJaxbPlatformGroupConfig groupConfig = this.jaxbPlatformGroupConfigs.get(groupID);
			if (groupConfig == null) {
				this.logInvalidValue(element, GROUP_ATTRIBUTE, groupID);
				return null;  // drop any platform with an invalid group(?)
			}
			config.setGroup(groupConfig);
			groupConfig.addPlatform(config);
		}

		config.setPluginId(contributor);
		return config;
	}


	// ********** initialize default preferences **********

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link InternalJaxbWorkspace#initializeDefaultPreferences()
	 * JAXB workspace}.
	 */
	public void initializeDefaultPreferences() {
		for (IProjectFacetVersion jaxbFacetVersion : this.getJaxbFacetVersions()) {
			this.initializeDefaultPreference(jaxbFacetVersion);
		}
	}

	private Set<IProjectFacetVersion> getJaxbFacetVersions() {
		return JaxbProject.FACET.getVersions();
	}

	private void initializeDefaultPreference(IProjectFacetVersion jaxbFacetVersion) {
		JaxbPlatformConfig config = this.buildDefaultJaxbPlatformConfig(jaxbFacetVersion);
		if (config != null) {
			this.getPlugin().setDefaultPreference(this.buildDefaultJaxbPlatformPreferenceKey(jaxbFacetVersion), config.getId());
		}
	}

	/**
	 * Return the first JAXB platform config registered as a default for the
	 * specified JAXB facet version. Return an <em>internal</em> platform
	 * config if none are registered. Log an error and return
	 * <code>null</code> if the specified JAXB facet version is invalid.
	 */
	private JaxbPlatformConfig buildDefaultJaxbPlatformConfig(IProjectFacetVersion jaxbFacetVersion) {
		JaxbPlatformConfig config = this.selectJaxbPlatformConfig(this.getDefaultJaxbPlatformConfigs(), jaxbFacetVersion);
		if (config != null) {
			return config;
		}

		config = this.selectJaxbPlatformConfig(this.getInternalJaxbPlatformConfigs(), jaxbFacetVersion);
		if (config != null) {
			return config;
		}

		this.logError(JptJaxbCoreMessages.INVALID_FACET, jaxbFacetVersion);
		return null;
	}

	private String buildDefaultJaxbPlatformPreferenceKey(IProjectFacetVersion jaxbFacetVersion) {
		return DEFAULT_JAXB_PLATFORM_PREF_KEY_BASE + jaxbFacetVersion.getVersionString();
	}

	/**
	 * The base of the keys for the default JAXB platform IDs stored in the
	 * workspace preferences. The keys can calculated by appending the
	 * {@link IProjectFacetVersion#getVersionString() JAXB facet version}
	 * to this base.
	 * @see #getDefaultJaxbPlatformConfig(IProjectFacetVersion)
	 * @see org.eclipse.jpt.common.core.internal.utility.JptPlugin#getPreference(String)
	 */
	private static final String DEFAULT_JAXB_PLATFORM_PREF_KEY_BASE = "defaultJaxbPlatform_"; //$NON-NLS-1$


	// ********** JAXB platform definitions **********

	public JaxbPlatformDefinition getJaxbPlatformDefinition(String jaxbPlatformID) {
		InternalJaxbPlatformConfig config = this.jaxbPlatformConfigs.get(jaxbPlatformID);
		return (config == null) ? null : config.getJaxbPlatformDefinition();
	}


	// ********** JAXB platform group configs **********

	public Iterable<JaxbPlatformGroupConfig> getJaxbPlatformGroupConfigs() {
		return new SuperIterableWrapper<JaxbPlatformGroupConfig>(this.jaxbPlatformGroupConfigs.values());
	}

	public JaxbPlatformGroupConfig getJaxbPlatformGroupConfig(String groupID) {
		return this.jaxbPlatformGroupConfigs.get(groupID);
	}


	// ********** JAXB platform configs **********

	public Iterable<JaxbPlatformConfig> getJaxbPlatformConfigs() {
		return new SuperIterableWrapper<JaxbPlatformConfig>(this.jaxbPlatformConfigs.values());
	}

	public JaxbPlatformConfig getJaxbPlatformConfig(String jaxbPlatformID) {
		return this.jaxbPlatformConfigs.get(jaxbPlatformID);
	}

	public Iterable<JaxbPlatformConfig> getJaxbPlatformConfigs(IProjectFacetVersion jaxbFacetVersion) {
		return this.selectJaxbPlatformConfigs(this.getJaxbPlatformConfigs(), jaxbFacetVersion);
	}

	/**
	 * Return the first config among those specified that supports the
	 * specified JAXB facet version.
	 */
	private JaxbPlatformConfig selectJaxbPlatformConfig(Iterable<JaxbPlatformConfig> configs, IProjectFacetVersion jaxbFacetVersion) {
		Iterator<JaxbPlatformConfig> stream = this.selectJaxbPlatformConfigs(configs, jaxbFacetVersion).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	/**
	 * Return the JAXB platform configs among those specified that support
	 * the specified facet version.
	 */
	private Iterable<JaxbPlatformConfig> selectJaxbPlatformConfigs(Iterable<JaxbPlatformConfig> configs, IProjectFacetVersion jaxbFacetVersion) {
		return IterableTools.filter(configs, new JaxbPlatformConfig.SupportsJaxbFacetVersion(jaxbFacetVersion));
	}

	/**
	 * "Default" platforms (i.e. third-party platforms flagged as "default").
	 */
	private Iterable<JaxbPlatformConfig> getDefaultJaxbPlatformConfigs() {
		return IterableTools.filter(this.getJaxbPlatformConfigs(), JaxbPlatformConfig.IS_DEFAULT);
	}

	/**
	 * "Internal" (i.e. Dali-defined generic) platforms.
	 */
	private Iterable<JaxbPlatformConfig> getInternalJaxbPlatformConfigs() {
		return IterableTools.filter(this.getJaxbPlatformConfigs(), this.buildInternalJaxbPlatformConfigFilter());
	}

	private Filter<JaxbPlatformConfig> buildInternalJaxbPlatformConfigFilter() {
		return new InternalJaxbPlatformConfigFilter(this.getPluginID());
	}

	/* CU private */ static class InternalJaxbPlatformConfigFilter
		extends Filter.Adapter<JaxbPlatformConfig>
	{
		private final String prefix;
		InternalJaxbPlatformConfigFilter(String prefix) {
			super();
			this.prefix = prefix;
		}
		@Override
		public boolean accept(JaxbPlatformConfig config) {
			return config.getFactoryClassName().startsWith(this.prefix);
		}
	}


	// ********** default JAXB platform config **********

	public JaxbPlatformConfig getDefaultJaxbPlatformConfig(IProjectFacetVersion jaxbFacetVersion) {
		String key = this.buildDefaultJaxbPlatformPreferenceKey(jaxbFacetVersion);
		String id = this.getPlugin().getPreference(key);
		return (id == null) ? null : this.getJaxbPlatformConfig(id);
	}

	public void setDefaultJaxbPlatformConfig(IProjectFacetVersion jaxbFacetVersion, JaxbPlatformConfig config) {
		String key = this.buildDefaultJaxbPlatformPreferenceKey(jaxbFacetVersion);
		this.getPlugin().setPreference(key, config.getId());
	}


	// ********** logging **********

	private void logError(String msg, Object... args) {
		this.getPlugin().logError(msg, args);
	}

	private void logMissingAttribute(IConfigurationElement element, String attributeName) {
		this.getPlugin().logError(ConfigurationElementTools.buildMissingAttributeMessage(element, attributeName));
	}

	private void logInvalidValue(IConfigurationElement element, String nodeName, String invalidValue) {
		this.getPlugin().logError(ConfigurationElementTools.buildInvalidValueMessage(element, nodeName, invalidValue));
	}


	// ********** misc **********

	public JaxbWorkspace getJaxbWorkspace() {
		return this.jaxbWorkspace;
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

	private JptJaxbCorePlugin getPlugin() {
		return JptJaxbCorePlugin.instance();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
