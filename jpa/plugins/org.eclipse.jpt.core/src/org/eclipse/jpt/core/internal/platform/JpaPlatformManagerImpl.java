/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import static org.eclipse.jpt.core.internal.XPointUtil.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.XPointUtil.XPointException;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.core.platform.JpaPlatformGroupDescription;
import org.eclipse.jpt.core.platform.JpaPlatformManager;
import org.eclipse.jpt.utility.internal.KeyedSet;
import org.eclipse.jpt.utility.internal.iterables.SuperIterableWrapper;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Singleton registry for storing all the registered JPA platform configuration
 * elements and instantiating JPA platforms from them.
 */
public class JpaPlatformManagerImpl 
		implements JpaPlatformManager {
	
	static final String EXTENSION_POINT_ID = "jpaPlatforms"; //$NON-NLS-1$
	static final String QUALIFIED_EXTENSION_POINT_ID = JptCorePlugin.PLUGIN_ID_ + EXTENSION_POINT_ID;
	static final String PLATFORM_GROUP_ELEMENT = "jpaPlatformGroup"; //$NON-NLS-1$
	static final String PLATFORM_ELEMENT = "jpaPlatform"; //$NON-NLS-1$
	static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	static final String LABEL_ATTRIBUTE = "label"; //$NON-NLS-1$
	static final String FACTORY_CLASS_ATTRIBUTE = "factoryClass"; //$NON-NLS-1$
	static final String JPA_FACET_VERSION_ATTRIBUTE = "jpaFacetVersion"; //$NON-NLS-1$
	static final String DEFAULT_ATTRIBUTE = "default"; //$NON-NLS-1$
	static final String GROUP_ELEMENT = "group";  //$NON-NLS-1$
	
	
	private static final JpaPlatformManagerImpl INSTANCE = new JpaPlatformManagerImpl();
	
	
	public static JpaPlatformManagerImpl instance() {
		return INSTANCE;
	}
	
	
	private KeyedSet<String, JpaPlatformGroupDescriptionImpl> jpaPlatformGroupDescriptions;
	private KeyedSet<String, JpaPlatformDescriptionImpl> jpaPlatformDescriptions;
	
	
	// ********** constructor/initialization **********
	
	private JpaPlatformManagerImpl() {
		super();
		this.jpaPlatformGroupDescriptions = new KeyedSet<String, JpaPlatformGroupDescriptionImpl>();
		this.jpaPlatformDescriptions = new KeyedSet<String, JpaPlatformDescriptionImpl>();
		readExtensions();
	}
	
	
	private void readExtensions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		final IExtensionPoint xpoint 
				= registry.getExtensionPoint(JptCorePlugin.PLUGIN_ID, EXTENSION_POINT_ID);
		
		if (xpoint == null) {
			throw new IllegalStateException();
		}
		
		List<IConfigurationElement> platformGroupConfigs = new ArrayList<IConfigurationElement>();
		List<IConfigurationElement> platformConfigs = new ArrayList<IConfigurationElement>();
		
		for (IExtension extension : xpoint.getExtensions()) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				if (element.getName().equals(PLATFORM_GROUP_ELEMENT)) {
					platformGroupConfigs.add(element);
				}
				else if (element.getName().equals(PLATFORM_ELEMENT)) {
					platformConfigs.add(element);
				}
			}
		}
		
		for (IConfigurationElement element : platformGroupConfigs) {
			readPlatformGroupExtension(element);
		}
		
		for (IConfigurationElement element : platformConfigs) {
			readPlatformExtension(element);
		}
	}
	
	private void readPlatformGroupExtension(IConfigurationElement element) {
		try {
			final JpaPlatformGroupDescriptionImpl desc = new JpaPlatformGroupDescriptionImpl();
			
			// plug-in id
			desc.setPluginId(element.getContributor().getName());
			
			// id
			desc.setId(findRequiredAttribute(element, ID_ATTRIBUTE));
			
			if (this.jpaPlatformGroupDescriptions.containsKey(desc.getId())) {
				logDuplicateExtension(QUALIFIED_EXTENSION_POINT_ID, ID_ATTRIBUTE, desc.getId());
				throw new XPointException();
			}
			
			// label
			desc.setLabel(findRequiredAttribute(element, LABEL_ATTRIBUTE));
			
			this.jpaPlatformGroupDescriptions.addItem(desc.getId(), desc);
		}
		catch (XPointException e) {
			// Ignore and continue. The problem has already been reported to the user
			// in the log.
		}
	}
	
	private void readPlatformExtension(IConfigurationElement element) {
		try {
			final JpaPlatformDescriptionImpl desc = new JpaPlatformDescriptionImpl();
			
			// plug-in id
			desc.setPluginId(element.getContributor().getName());
			
			// id
			desc.setId(findRequiredAttribute(element, ID_ATTRIBUTE));
			
			if (this.jpaPlatformDescriptions.containsKey(desc.getId())) {
				logDuplicateExtension(QUALIFIED_EXTENSION_POINT_ID, ID_ATTRIBUTE, desc.getId());
				throw new XPointException();
			}
			
			// label
			desc.setLabel(findRequiredAttribute(element, LABEL_ATTRIBUTE));
			
			// factory class 
			desc.setFactoryClassName(findRequiredAttribute(element, FACTORY_CLASS_ATTRIBUTE));
			
			// JPA facet version
			String jpaFacetVersionString = element.getAttribute(JPA_FACET_VERSION_ATTRIBUTE);
			if (jpaFacetVersionString != null) {
				IProjectFacetVersion jpaFacetVersion = JpaFacet.FACET.getVersion(jpaFacetVersionString);
				if (jpaFacetVersion != null) {
					desc.setJpaFacetVersion(jpaFacetVersion);
				}
				else {
					logInvalidValue(element, JPA_FACET_VERSION_ATTRIBUTE, jpaFacetVersionString);
					throw new XPointException();
				}
			}
			
			// default
			String defaultString = element.getAttribute(DEFAULT_ATTRIBUTE);
			if (defaultString != null) {
				if (defaultString.equals("true")) {
					desc.setDefault(true);
				}
				else if (defaultString.equals("false")) {
					desc.setDefault(false);
				}
				else {
					logInvalidValue(element, DEFAULT_ATTRIBUTE, defaultString);
					throw new XPointException();
				}
			}
			
			// group
			String groupId = element.getAttribute(GROUP_ELEMENT);
			if (groupId != null) {
				JpaPlatformGroupDescriptionImpl group = this.jpaPlatformGroupDescriptions.getItem(groupId);
				if (group != null) {
					desc.setGroup(group);
					group.addPlatform(desc);
				}
				else {
					logInvalidValue(element, GROUP_ELEMENT, groupId);
					throw new XPointException();
				}
			}
			
			this.jpaPlatformDescriptions.addItem(desc.getId(), desc);
		}
		catch (XPointException e) {
			// Ignore and continue. The problem has already been reported to the user
			// in the log.
		}
	}


	// ********** public methods **********
	
	public Iterable<JpaPlatformGroupDescription> getJpaPlatformGroups() {
		return new SuperIterableWrapper<JpaPlatformGroupDescription>(this.jpaPlatformGroupDescriptions.getItemSet());
	}
	
	public JpaPlatformGroupDescription getJpaPlatformGroup(String groupId) {
		return this.jpaPlatformGroupDescriptions.getItem(groupId);
	}
	
	public Iterable<JpaPlatformDescription> getJpaPlatforms() {
		return new SuperIterableWrapper<JpaPlatformDescription>(this.jpaPlatformDescriptions.getItemSet());
	}
	
	public JpaPlatformDescription getJpaPlatform(String platformId) {
		return this.jpaPlatformDescriptions.getItem(platformId);
	}
	
	public JpaPlatformDescription getDefaultJpaPlatform(IProjectFacetVersion jpaFacetVersion) {
		for (JpaPlatformDescription platform : getJpaPlatforms()) {
			if (platform.isDefault() && platform.supportsJpaFacetVersion(jpaFacetVersion)) {
				return platform;
			}
		}
		return null;
	}
	public JpaPlatform buildJpaPlatformImplementation(IProject project) {
		String jpaPlatformId = JptCorePlugin.getJpaPlatformId(project);
		JpaPlatformDescriptionImpl platformDesc = this.jpaPlatformDescriptions.getItem(jpaPlatformId);
		if (platformDesc == null) {
			throw new IllegalArgumentException("Project does not have a recognized JPA platform.");
		}
		return platformDesc.buildJpaPlatform();
	}
}
