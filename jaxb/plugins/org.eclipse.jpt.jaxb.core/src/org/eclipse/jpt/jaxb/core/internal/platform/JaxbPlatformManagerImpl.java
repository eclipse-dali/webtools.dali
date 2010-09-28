/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.platform;

import static org.eclipse.jpt.core.internal.XPointUtil.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.XPointUtil.XPointException;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.jpt.utility.internal.KeyedSet;
import org.eclipse.jpt.utility.internal.iterables.SuperIterableWrapper;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public class JaxbPlatformManagerImpl
		implements JaxbPlatformManager {
	
	static final String EXTENSION_POINT_ID = "jaxbPlatforms"; //$NON-NLS-1$
	static final String QUALIFIED_EXTENSION_POINT_ID = JptJaxbCorePlugin.PLUGIN_ID_ + EXTENSION_POINT_ID;
	static final String PLATFORM_GROUP_ELEMENT = "jaxbPlatformGroup"; //$NON-NLS-1$
	static final String PLATFORM_ELEMENT = "jaxbPlatform"; //$NON-NLS-1$
	static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	static final String LABEL_ATTRIBUTE = "label"; //$NON-NLS-1$
	static final String FACTORY_CLASS_ATTRIBUTE = "factoryClass"; //$NON-NLS-1$
	static final String JAXB_FACET_VERSION_ATTRIBUTE = "jaxbFacetVersion"; //$NON-NLS-1$
	static final String DEFAULT_ATTRIBUTE = "default"; //$NON-NLS-1$
	static final String GROUP_ELEMENT = "group";  //$NON-NLS-1$
	
	
	private static final JaxbPlatformManagerImpl INSTANCE = new JaxbPlatformManagerImpl();
	
	
	public static JaxbPlatformManagerImpl instance() {
		return INSTANCE;
	}
	
	
	private KeyedSet<String, JaxbPlatformGroupDescriptionImpl> platformGroupDescriptions;
	private KeyedSet<String, JaxbPlatformDescriptionImpl> platformDescriptions;
	
	
	// ********** constructor/initialization **********
	
	private JaxbPlatformManagerImpl() {
		super();
		this.platformGroupDescriptions = new KeyedSet<String, JaxbPlatformGroupDescriptionImpl>();
		this.platformDescriptions = new KeyedSet<String, JaxbPlatformDescriptionImpl>();
		readExtensions();
	}
	
	
	private void readExtensions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		final IExtensionPoint xpoint 
				= registry.getExtensionPoint(JptJaxbCorePlugin.PLUGIN_ID, EXTENSION_POINT_ID);
		
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
			final JaxbPlatformGroupDescriptionImpl desc = new JaxbPlatformGroupDescriptionImpl();
			
			// plug-in id
			desc.setPluginId(element.getContributor().getName());
			
			// id
			desc.setId(findRequiredAttribute(element, ID_ATTRIBUTE));
			
			if (this.platformGroupDescriptions.containsKey(desc.getId())) {
				logDuplicateExtension(QUALIFIED_EXTENSION_POINT_ID, desc.getId());
				throw new XPointException();
			}
			
			// label
			desc.setLabel(findRequiredAttribute(element, LABEL_ATTRIBUTE));
			
			this.platformGroupDescriptions.addItem(desc.getId(), desc);
		}
		catch (XPointException e) {
			// Ignore and continue. The problem has already been reported to the user
			// in the log.
		}
	}
	
	private void readPlatformExtension(IConfigurationElement element) {
		try {
			final JaxbPlatformDescriptionImpl desc = new JaxbPlatformDescriptionImpl();
			
			// plug-in id
			desc.setPluginId(element.getContributor().getName());
			
			// id
			desc.setId(findRequiredAttribute(element, ID_ATTRIBUTE));
			
			if (this.platformDescriptions.containsKey(desc.getId())) {
				logDuplicateExtension(QUALIFIED_EXTENSION_POINT_ID, desc.getId());
				throw new XPointException();
			}
			
			// label
			desc.setLabel(findRequiredAttribute(element, LABEL_ATTRIBUTE));
			
			// factory class 
			desc.setFactoryClassName(findRequiredAttribute(element, FACTORY_CLASS_ATTRIBUTE));
			
			// JAXB facet version
			String jaxbFacetVersionString = element.getAttribute(JAXB_FACET_VERSION_ATTRIBUTE);
			if (jaxbFacetVersionString != null) {
				IProjectFacetVersion jpaFacetVersion = JaxbFacet.FACET.getVersion(jaxbFacetVersionString);
				if (jpaFacetVersion != null) {
					desc.setJaxbFacetVersion(jpaFacetVersion);
				}
				else {
					logInvalidValue(element, JAXB_FACET_VERSION_ATTRIBUTE, jaxbFacetVersionString);
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
				JaxbPlatformGroupDescriptionImpl group = this.platformGroupDescriptions.getItem(groupId);
				if (group != null) {
					desc.setGroup(group);
					group.addPlatform(desc);
				}
				else {
					logInvalidValue(element, GROUP_ELEMENT, groupId);
					throw new XPointException();
				}
			}
			else {
				JaxbPlatformGroupDescriptionImpl group = new JaxbPlatformGroupDescriptionImpl();
				group.setPluginId(desc.getPluginId());
				group.setId(desc.getId());
				group.setLabel(desc.getLabel());
				group.addPlatform(desc);
				
				if (this.platformGroupDescriptions.containsKey(group.getId())) {
					logInvalidValue(element, GROUP_ELEMENT, groupId);
					throw new XPointException();
				}
				
				this.platformGroupDescriptions.addItem(group.getId(), group);
			}
			
			this.platformDescriptions.addItem(desc.getId(), desc);
		}
		catch (XPointException e) {
			// Ignore and continue. The problem has already been reported to the user
			// in the log.
		}
	}


	// ********** public methods **********
	
	public Iterable<JaxbPlatformGroupDescription> getJaxbPlatformGroups() {
		return new SuperIterableWrapper<JaxbPlatformGroupDescription>(this.platformGroupDescriptions.getItemSet());
	}
	
	public JaxbPlatformGroupDescription getJaxbPlatformGroup(String groupId) {
		return this.platformGroupDescriptions.getItem(groupId);
	}
	
	public Iterable<JaxbPlatformDescription> getJaxbPlatforms() {
		return new SuperIterableWrapper<JaxbPlatformDescription>(this.platformDescriptions.getItemSet());
	}
	
	public JaxbPlatformDescription getJaxbPlatform(String platformId) {
		return this.platformDescriptions.getItem(platformId);
	}
	
	public JaxbPlatformDescription getDefaultJaxbPlatform(IProjectFacetVersion jaxbFacetVersion) {
		if (! jaxbFacetVersion.getProjectFacet().equals(JaxbFacet.FACET)) {
			throw new IllegalArgumentException(jaxbFacetVersion.toString());
		}
		for (JaxbPlatformDescription platform : getJaxbPlatforms()) {
			if (platform.isDefault() && platform.supportsJaxbFacetVersion(jaxbFacetVersion)) {
				return platform;
			}
		}
		return null;
	}
	
	public JaxbPlatformDefinition buildJaxbPlatformDefinition(IProject project) {
		String jaxbPlatformId = JptCorePlugin.getJpaPlatformId(project);
		JaxbPlatformDescriptionImpl platformDesc = this.platformDescriptions.getItem(jaxbPlatformId);
		if (platformDesc == null) {
			throw new IllegalArgumentException("Project does not have a recognized JAXB platform.");
		}
		return platformDesc.buildJaxbPlatformDefinition();
	}
}
