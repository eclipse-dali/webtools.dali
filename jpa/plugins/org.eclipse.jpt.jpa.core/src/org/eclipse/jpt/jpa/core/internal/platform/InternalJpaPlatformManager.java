/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.platform;

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
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.InternalJpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * JPA platform manager.
 */
public class InternalJpaPlatformManager
	implements JpaPlatformManager
{
	/**
	 * The JPA platform manager's JPA workspace.
	 */
	private final InternalJpaWorkspace jpaWorkspace;

	/**
	 * The JPA platform group configs, keyed by ID.
	 * Initialized during construction.
	 */
	private final HashMap<String, InternalJpaPlatformGroupConfig> jpaPlatformGroupConfigs = new HashMap<String, InternalJpaPlatformGroupConfig>();

	/**
	 * The JPA platform configs, keyed by ID.
	 * Initialized during construction.
	 */
	private final HashMap<String, InternalJpaPlatformConfig> jpaPlatformConfigs = new HashMap<String, InternalJpaPlatformConfig>();


	// ********** extension point element and attribute names **********

	private static final String SIMPLE_EXTENSION_POINT_NAME = "jpaPlatforms"; //$NON-NLS-1$
	private static final String JPA_PLATFORM_GROUP_ELEMENT = "jpaPlatformGroup"; //$NON-NLS-1$
	private static final String JPA_PLATFORM_ELEMENT = "jpaPlatform"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String LABEL_ATTRIBUTE = "label"; //$NON-NLS-1$
	private static final String FACTORY_CLASS_ATTRIBUTE = "factoryClass"; //$NON-NLS-1$
	private static final String JPA_FACET_VERSION_ATTRIBUTE = "jpaFacetVersion"; //$NON-NLS-1$
	private static final String DEFAULT_ATTRIBUTE = "default"; //$NON-NLS-1$
	private static final String GROUP_ATTRIBUTE = "group";  //$NON-NLS-1$


	/**
	 * Internal - called from only
	 * {@link InternalJpaWorkspace#buildJpaPlatformManager()}.
	 */
	public InternalJpaPlatformManager(InternalJpaWorkspace jpaWorkspace) {
		super();
		this.jpaWorkspace = jpaWorkspace;
		this.initialize();
	}


	// ********** initialization **********

	private void initialize() {
		IExtensionPoint extensionPoint = this.getExtensionPoint();
		if (extensionPoint == null) {
			throw new IllegalStateException("missing extension point: " + this.getExtensionPointName()); //$NON-NLS-1$
		}

		ArrayList<IConfigurationElement> jpaPlatformGroupElements = new ArrayList<IConfigurationElement>();
		ArrayList<IConfigurationElement> jpaPlatformElements = new ArrayList<IConfigurationElement>();

		for (IExtension extension : extensionPoint.getExtensions()) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				String elementName = element.getName();  // probably cannot be null
				if (elementName.equals(JPA_PLATFORM_GROUP_ELEMENT)) {
					jpaPlatformGroupElements.add(element);
				}
				else if (elementName.equals(JPA_PLATFORM_ELEMENT)) {
					jpaPlatformElements.add(element);
				}
			}
		}

		// build the groups first so the platforms can be added as they are built
		for (IConfigurationElement element : jpaPlatformGroupElements) {
			InternalJpaPlatformGroupConfig config = this.buildPlatformGroupConfig(element);
			if (config != null) {
				this.jpaPlatformGroupConfigs.put(config.getId(), config);
			}
		}

		for (IConfigurationElement element : jpaPlatformElements) {
			InternalJpaPlatformConfig config = this.buildPlatformConfig(element);
			if (config != null) {
				this.jpaPlatformConfigs.put(config.getId(), config);
			}
		}
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * group config from the specified configuration element.
	 */
	private InternalJpaPlatformGroupConfig buildPlatformGroupConfig(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (id == null) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.jpaPlatformGroupConfigs.containsKey(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate platform groups
		}

		// label
		String label = element.getAttribute(LABEL_ATTRIBUTE);
		if (label == null) {
			this.logMissingAttribute(element, LABEL_ATTRIBUTE);
			return null;
		}

		InternalJpaPlatformGroupConfig config = new InternalJpaPlatformGroupConfig(this, id, label);
		config.setPluginId(contributor);
		return config;
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * platform config from the specified configuration element.
	 */
	private InternalJpaPlatformConfig buildPlatformConfig(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (id == null) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.jpaPlatformConfigs.containsKey(id)) {
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

		InternalJpaPlatformConfig config = new InternalJpaPlatformConfig(this, id, label, factoryClassName);

		// JPA facet version
		String jpaFacetVersionString = element.getAttribute(JPA_FACET_VERSION_ATTRIBUTE);
		if (jpaFacetVersionString != null) {
			IProjectFacetVersion jpaFacetVersion = JpaProject.FACET.getVersion(jpaFacetVersionString);
			if (jpaFacetVersion == null) {
				this.logInvalidValue(element, JPA_FACET_VERSION_ATTRIBUTE, jpaFacetVersionString);
				return null;
			}
			config.setJpaFacetVersion(jpaFacetVersion);
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
			InternalJpaPlatformGroupConfig groupConfig = this.jpaPlatformGroupConfigs.get(groupID);
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
	 * {@link InternalJpaWorkspace#initializeDefaultPreferences()
	 * JPA workspace}.
	 */
	public void initializeDefaultPreferences() {
		for (IProjectFacetVersion jpaFacetVersion : this.getJpaFacetVersions()) {
			this.initializeDefaultPreference(jpaFacetVersion);
		}
	}

	private Set<IProjectFacetVersion> getJpaFacetVersions() {
		return JpaProject.FACET.getVersions();
	}

	private void initializeDefaultPreference(IProjectFacetVersion jpaFacetVersion) {
		JpaPlatform.Config config = this.buildDefaultJpaPlatformConfig(jpaFacetVersion);
		if (config != null) {
			this.getPlugin().setDefaultPreference(this.buildDefaultJpaPlatformPreferenceKey(jpaFacetVersion), config.getId());
		}
	}

	/**
	 * Return the first JPA platform config registered as a default for the
	 * specified JPA facet version. Return an <em>internal</em> platform
	 * config if none are registered. Log an error and return
	 * <code>null</code> if the specified JPA facet version is invalid.
	 */
	private JpaPlatform.Config buildDefaultJpaPlatformConfig(IProjectFacetVersion jpaFacetVersion) {
		InternalJpaPlatformConfig config = this.selectJpaPlatformConfig(this.getDefaultJpaPlatformConfigs(), jpaFacetVersion);
		if (config != null) {
			return config;
		}

		config = this.selectJpaPlatformConfig(this.getDaliJpaPlatformConfigs(), jpaFacetVersion);
		if (config != null) {
			return config;
		}

		this.logError(JptCoreMessages.INVALID_FACET, jpaFacetVersion);
		return null;
	}

	private String buildDefaultJpaPlatformPreferenceKey(IProjectFacetVersion jpaFacetVersion) {
		return DEFAULT_JPA_PLATFORM_PREF_KEY_BASE + jpaFacetVersion.getVersionString();
	}

	/**
	 * The base of the keys for the default JPA platform IDs stored in the
	 * workspace preferences. The keys can calculated by appending the
	 * {@link IProjectFacetVersion#getVersionString() JPA facet version}
	 * to this base.
	 * @see #getDefaultJpaPlatformConfig(IProjectFacetVersion)
	 * @see org.eclipse.jpt.common.core.internal.utility.JptPlugin#getPreference(String)
	 */
	private static final String DEFAULT_JPA_PLATFORM_PREF_KEY_BASE = "defaultJpaPlatform_"; //$NON-NLS-1$


	// ********** JPA platforms **********

	public JpaPlatform getJpaPlatform(String jpaPlatformID) {
		InternalJpaPlatformConfig config = this.jpaPlatformConfigs.get(jpaPlatformID);
		return (config == null) ? null : config.getJpaPlatform();
	}


	// ********** JPA platform group configs **********

	public Iterable<JpaPlatform.GroupConfig> getJpaPlatformGroupConfigs() {
		return new SuperIterableWrapper<JpaPlatform.GroupConfig>(this.jpaPlatformGroupConfigs.values());
	}

	public JpaPlatform.GroupConfig getJpaPlatformGroupConfig(String groupID) {
		return this.jpaPlatformGroupConfigs.get(groupID);
	}


	// ********** JPA platform configs **********

	public Iterable<JpaPlatform.Config> getJpaPlatformConfigs() {
		return new SuperIterableWrapper<JpaPlatform.Config>(this.getInternalJpaPlatformConfigs());
	}

	private Iterable<InternalJpaPlatformConfig> getInternalJpaPlatformConfigs() {
		return this.jpaPlatformConfigs.values();
	}

	public JpaPlatform.Config getJpaPlatformConfig(String jpaPlatformID) {
		return this.jpaPlatformConfigs.get(jpaPlatformID);
	}

	public Iterable<JpaPlatform.Config> getJpaPlatformConfigs(IProjectFacetVersion jpaFacetVersion) {
		return new SuperIterableWrapper<JpaPlatform.Config>(this.getInternalJpaPlatformConfigs(jpaFacetVersion));
	}

	private Iterable<InternalJpaPlatformConfig> getInternalJpaPlatformConfigs(IProjectFacetVersion jpaFacetVersion) {
		return this.selectJpaPlatformConfigs(this.getInternalJpaPlatformConfigs(), jpaFacetVersion);
	}

	/**
	 * Return the first config among those specified that supports the
	 * specified JPA facet version.
	 */
	private InternalJpaPlatformConfig selectJpaPlatformConfig(Iterable<InternalJpaPlatformConfig> configs, IProjectFacetVersion jpaFacetVersion) {
		Iterator<InternalJpaPlatformConfig> stream = this.selectJpaPlatformConfigs(configs, jpaFacetVersion).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	/**
	 * Return the JPA platform configs among those specified that support
	 * the specified facet version.
	 */
	private Iterable<InternalJpaPlatformConfig> selectJpaPlatformConfigs(Iterable<InternalJpaPlatformConfig> configs, IProjectFacetVersion jpaFacetVersion) {
		return new FilteringIterable<InternalJpaPlatformConfig>(configs, this.buildJpaPlatformConfigFilter(jpaFacetVersion));
	}

	private Filter<InternalJpaPlatformConfig> buildJpaPlatformConfigFilter(IProjectFacetVersion jpaFacetVersion) {
		return new FacetVersionJpaPlatformConfigFilter(jpaFacetVersion);
	}

	/* CU private */ static class FacetVersionJpaPlatformConfigFilter
		extends Filter.Adapter<InternalJpaPlatformConfig>
	{
		private final IProjectFacetVersion jpaFacetVersion;
		FacetVersionJpaPlatformConfigFilter(IProjectFacetVersion jpaFacetVersion) {
			super();
			this.jpaFacetVersion = jpaFacetVersion;
		}
		@Override
		public boolean accept(InternalJpaPlatformConfig config) {
			return config.supportsJpaFacetVersion(this.jpaFacetVersion);
		}
	}

	/**
	 * "Default" platforms (i.e. third-party platforms flagged as "default").
	 */
	private Iterable<InternalJpaPlatformConfig> getDefaultJpaPlatformConfigs() {
		return new FilteringIterable<InternalJpaPlatformConfig>(this.getInternalJpaPlatformConfigs(), JpaPlatform.Config.DEFAULT_FILTER);
	}

	/**
	 * Dali-defined "generic" platforms.
	 */
	private Iterable<InternalJpaPlatformConfig> getDaliJpaPlatformConfigs() {
		return new FilteringIterable<InternalJpaPlatformConfig>(this.getInternalJpaPlatformConfigs(), this.buildDaliJpaPlatformConfigFilter());
	}

	private Filter<InternalJpaPlatformConfig> buildDaliJpaPlatformConfigFilter() {
		return new DaliJpaPlatformConfigFilter(this.getPluginID());
	}

	/* CU private */ static class DaliJpaPlatformConfigFilter
		extends Filter.Adapter<InternalJpaPlatformConfig>
	{
		private final String prefix;
		DaliJpaPlatformConfigFilter(String prefix) {
			super();
			this.prefix = prefix;
		}
		@Override
		public boolean accept(InternalJpaPlatformConfig config) {
			return config.getFactoryClassName().startsWith(this.prefix);
		}
	}


	// ********** default JPA platform config **********

	public JpaPlatform.Config getDefaultJpaPlatformConfig(IProjectFacetVersion jpaFacetVersion) {
		String key = this.buildDefaultJpaPlatformPreferenceKey(jpaFacetVersion);
		String id = this.getPlugin().getPreference(key);
		return (id == null) ? null : this.getJpaPlatformConfig(id);
	}

	public void setDefaultJpaPlatformConfig(IProjectFacetVersion jpaFacetVersion, JpaPlatform.Config config) {
		String key = this.buildDefaultJpaPlatformPreferenceKey(jpaFacetVersion);
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

	public JpaWorkspace getJpaWorkspace() {
		return this.jpaWorkspace;
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

	private JptJpaCorePlugin getPlugin() {
		return JptJpaCorePlugin.instance();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
