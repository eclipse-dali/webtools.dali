/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.utility.XPointTools;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.FilterAdapter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperIterableWrapper;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.InternalJpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformGroupDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * JPA platform manager.
 */
public class InternalJpaPlatformManager
	implements JpaPlatformManager
{
	/**
	 * The JPA platform description manager's JPA workspace.
	 */
	private final InternalJpaWorkspace jpaWorkspace;

	/**
	 * The JPA platform group descriptions, keyed by ID.
	 * Initialized during construction.
	 */
	private final HashMap<String, InternalJpaPlatformGroupDescription> jpaPlatformGroupDescriptions = new HashMap<String, InternalJpaPlatformGroupDescription>();

	/**
	 * The JPA platform descriptions, keyed by ID.
	 * Initialized during construction.
	 */
	private final HashMap<String, InternalJpaPlatformDescription> jpaPlatformDescriptions = new HashMap<String, InternalJpaPlatformDescription>();


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
			throw new IllegalStateException();
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
			InternalJpaPlatformGroupDescription desc = this.buildPlatformGroupDescription(element);
			if (desc != null) {
				this.jpaPlatformGroupDescriptions.put(desc.getId(), desc);
			}
		}

		for (IConfigurationElement element : jpaPlatformElements) {
			InternalJpaPlatformDescription desc = this.buildPlatformDescription(element);
			if (desc != null) {
				this.jpaPlatformDescriptions.put(desc.getId(), desc);
			}
		}
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * group description from the specified configuration element.
	 */
	private InternalJpaPlatformGroupDescription buildPlatformGroupDescription(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (id == null) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.jpaPlatformGroupDescriptions.containsKey(id)) {
			this.logError(JptCommonCoreMessages.REGISTRY_DUPLICATE, this.getExtensionPointName(), contributor, ID_ATTRIBUTE, id);
			return null;  // drop any duplicate platform groups
		}

		// label
		String label = element.getAttribute(LABEL_ATTRIBUTE);
		if (label == null) {
			this.logMissingAttribute(element, LABEL_ATTRIBUTE);
			return null;
		}

		InternalJpaPlatformGroupDescription desc = new InternalJpaPlatformGroupDescription(this, id, label);
		desc.setPluginId(contributor);
		return desc;
	}

	/**
	 * Return <code>null</code> if there is any sort of problem building a
	 * platform description from the specified configuration element.
	 */
	private InternalJpaPlatformDescription buildPlatformDescription(IConfigurationElement element) {
		String contributor = element.getContributor().getName();
		// id
		String id = element.getAttribute(ID_ATTRIBUTE);
		if (id == null) {
			this.logMissingAttribute(element, ID_ATTRIBUTE);
			return null;
		}
		if (this.jpaPlatformDescriptions.containsKey(id)) {
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

		InternalJpaPlatformDescription desc = new InternalJpaPlatformDescription(this, id, label, factoryClassName);

		// JPA facet version
		String jpaFacetVersionString = element.getAttribute(JPA_FACET_VERSION_ATTRIBUTE);
		if (jpaFacetVersionString != null) {
			IProjectFacetVersion jpaFacetVersion = JpaProject.FACET.getVersion(jpaFacetVersionString);
			if (jpaFacetVersion == null) {
				this.logInvalidValue(element, JPA_FACET_VERSION_ATTRIBUTE, jpaFacetVersionString);
				return null;
			}
			desc.setJpaFacetVersion(jpaFacetVersion);
		}

		// default
		String defaultString = element.getAttribute(DEFAULT_ATTRIBUTE);
		if (defaultString != null) {
			Boolean default_ = defaultString.equals("true") ? Boolean.TRUE : defaultString.equals("false") ? Boolean.FALSE : null; //$NON-NLS-1$ //$NON-NLS-2$
			if (default_ == null) {
				this.logInvalidValue(element, DEFAULT_ATTRIBUTE, defaultString);
				return null;
			}
			desc.setDefault(default_.booleanValue());
		}

		// group
		String groupID = element.getAttribute(GROUP_ATTRIBUTE);
		if (groupID != null) {
			InternalJpaPlatformGroupDescription group = this.jpaPlatformGroupDescriptions.get(groupID);
			if (group == null) {
				this.logInvalidValue(element, GROUP_ATTRIBUTE, groupID);
				return null;  // drop any platform with an invalid group(?)
			}
			desc.setGroup(group);
			group.addPlatform(desc);
		}

		desc.setPluginId(contributor);
		return desc;
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
		JpaPlatformDescription description = this.buildDefaultJpaPlatformDescription(jpaFacetVersion);
		if (description != null) {
			this.getPlugin().setDefaultPreference(this.buildDefaultJpaPlatformPreferenceKey(jpaFacetVersion), description.getId());
		}
	}

	/**
	 * Return the first JPA platform description registered as a default for the
	 * specified JPA facet version. Return an <em>internal</em> platform
	 * description if none are registered. Log an error and return
	 * <code>null</code> if the specified JPA facet version is invalid.
	 */
	private JpaPlatformDescription buildDefaultJpaPlatformDescription(IProjectFacetVersion jpaFacetVersion) {
		JpaPlatformDescription description = this.selectJpaPlatformDescription(this.getDefaultJpaPlatformDescriptions(), jpaFacetVersion);
		if (description != null) {
			return description;
		}

		description = this.selectJpaPlatformDescription(this.getInternalJpaPlatformDescriptions(), jpaFacetVersion);
		if (description != null) {
			return description;
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
	 * @see #getDefaultJpaPlatformDescription(IProjectFacetVersion)
	 * @see org.eclipse.jpt.common.core.internal.utility.JptPlugin#getPreference(String)
	 */
	private static final String DEFAULT_JPA_PLATFORM_PREF_KEY_BASE = "defaultJpaPlatform_"; //$NON-NLS-1$


	// ********** JPA platforms **********

	public JpaPlatform getJpaPlatform(String jpaPlatformID) {
		InternalJpaPlatformDescription desc = this.jpaPlatformDescriptions.get(jpaPlatformID);
		return (desc == null) ? null : desc.getJpaPlatform();
	}


	// ********** JPA platform group descriptions **********

	public Iterable<JpaPlatformGroupDescription> getJpaPlatformGroupDescriptions() {
		return new SuperIterableWrapper<JpaPlatformGroupDescription>(this.jpaPlatformGroupDescriptions.values());
	}

	public JpaPlatformGroupDescription getJpaPlatformGroupDescription(String groupID) {
		return this.jpaPlatformGroupDescriptions.get(groupID);
	}


	// ********** JPA platform descriptions **********

	public Iterable<JpaPlatformDescription> getJpaPlatformDescriptions() {
		return new SuperIterableWrapper<JpaPlatformDescription>(this.jpaPlatformDescriptions.values());
	}

	public JpaPlatformDescription getJpaPlatformDescription(String jpaPlatformID) {
		return this.jpaPlatformDescriptions.get(jpaPlatformID);
	}

	public Iterable<JpaPlatformDescription> getJpaPlatformDescriptions(IProjectFacetVersion jpaFacetVersion) {
		return this.selectJpaPlatformDescriptions(this.getJpaPlatformDescriptions(), jpaFacetVersion);
	}

	/**
	 * Return the first description among those specified that supports the
	 * specified JPA facet version.
	 */
	private JpaPlatformDescription selectJpaPlatformDescription(Iterable<JpaPlatformDescription> descriptions, IProjectFacetVersion jpaFacetVersion) {
		Iterator<JpaPlatformDescription> stream = this.selectJpaPlatformDescriptions(descriptions, jpaFacetVersion).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	/**
	 * Return the JPA platform descriptions among those specified that support
	 * the specified facet version.
	 */
	private Iterable<JpaPlatformDescription> selectJpaPlatformDescriptions(Iterable<JpaPlatformDescription> descriptions, IProjectFacetVersion jpaFacetVersion) {
		return new FilteringIterable<JpaPlatformDescription>(descriptions, this.buildJpaPlatformDescriptionFilter(jpaFacetVersion));
	}

	private Filter<JpaPlatformDescription> buildJpaPlatformDescriptionFilter(IProjectFacetVersion jpaFacetVersion) {
		return new FacetVersionJpaPlatformDescriptionFilter(jpaFacetVersion);
	}

	/* CU private */ static class FacetVersionJpaPlatformDescriptionFilter
		extends FilterAdapter<JpaPlatformDescription>
	{
		private final IProjectFacetVersion jpaFacetVersion;
		FacetVersionJpaPlatformDescriptionFilter(IProjectFacetVersion jpaFacetVersion) {
			super();
			this.jpaFacetVersion = jpaFacetVersion;
		}
		@Override
		public boolean accept(JpaPlatformDescription description) {
			return description.supportsJpaFacetVersion(this.jpaFacetVersion);
		}
	}

	/**
	 * "Default" platforms (i.e. third-party platforms flagged as "default").
	 */
	private Iterable<JpaPlatformDescription> getDefaultJpaPlatformDescriptions() {
		return new FilteringIterable<JpaPlatformDescription>(this.getJpaPlatformDescriptions(), DEFAULT_JPA_PLATFORM_DESCRIPTION_FILTER);
	}

	private static final Filter<JpaPlatformDescription> DEFAULT_JPA_PLATFORM_DESCRIPTION_FILTER = new DefaultJpaPlatformDescriptionFilter();

	/* CU private */ static class DefaultJpaPlatformDescriptionFilter
		extends FilterAdapter<JpaPlatformDescription>
	{
		@Override
		public boolean accept(JpaPlatformDescription desc) {
			return desc.isDefault();
		}
	}

	/**
	 * "Internal" (i.e. Dali-defined generic) platforms.
	 */
	private Iterable<JpaPlatformDescription> getInternalJpaPlatformDescriptions() {
		return new FilteringIterable<JpaPlatformDescription>(this.getJpaPlatformDescriptions(), this.buildInternalJpaPlatformDescriptionFilter());
	}

	private Filter<JpaPlatformDescription> buildInternalJpaPlatformDescriptionFilter() {
		return new InternalJpaPlatformDescriptionFilter(this.getPluginID());
	}

	/* CU private */ static class InternalJpaPlatformDescriptionFilter
		extends FilterAdapter<JpaPlatformDescription>
	{
		private final String prefix;
		InternalJpaPlatformDescriptionFilter(String prefix) {
			super();
			this.prefix = prefix;
		}
		@Override
		public boolean accept(JpaPlatformDescription desc) {
			return desc.getFactoryClassName().startsWith(this.prefix);
		}
	}


	// ********** default JPA platform description **********

	public JpaPlatformDescription getDefaultJpaPlatformDescription(IProjectFacetVersion jpaFacetVersion) {
		String key = this.buildDefaultJpaPlatformPreferenceKey(jpaFacetVersion);
		String platformID = this.getPlugin().getPreference(key);
		return (platformID == null) ? null : this.getJpaPlatformDescription(platformID);
	}

	public void setDefaultJpaPlatformDescription(IProjectFacetVersion jpaFacetVersion, JpaPlatformDescription description) {
		String key = this.buildDefaultJpaPlatformPreferenceKey(jpaFacetVersion);
		this.getPlugin().setPreference(key, description.getId());
	}


	// ********** logging **********

	private void logError(String msg, Object... args) {
		this.getPlugin().logError(msg, args);
	}

	private void logMissingAttribute(IConfigurationElement element, String attributeName) {
		this.getPlugin().logError(XPointTools.buildMissingAttributeMessage(element, attributeName));
	}

	private void logInvalidValue(IConfigurationElement element, String nodeName, String invalidValue) {
		this.getPlugin().logError(XPointTools.buildInvalidValueMessage(element, nodeName, invalidValue));
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
		return StringTools.buildToStringFor(this);
	}
}
