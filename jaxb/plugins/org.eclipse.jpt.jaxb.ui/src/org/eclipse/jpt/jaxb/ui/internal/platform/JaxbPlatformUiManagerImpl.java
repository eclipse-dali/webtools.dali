/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.platform;

import static org.eclipse.jpt.common.core.internal.utility.XPointTools.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.common.core.internal.utility.XPointTools.XPointException;
import org.eclipse.jpt.common.utility.internal.KeyedSet;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUiManager;

public class JaxbPlatformUiManagerImpl
		implements JaxbPlatformUiManager {
	
	static final String EXTENSION_POINT_ID = "jaxbPlatformUis"; //$NON-NLS-1$
	static final String QUALIFIED_EXTENSION_POINT_ID = JptJaxbUiPlugin.PLUGIN_ID_ + EXTENSION_POINT_ID; //$NON-NLS-1$
	static final String PLATFORM_UI_ELEMENT = "jaxbPlatformUi"; //$NON-NLS-1$
	static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$	
	static final String PLATFORM_ATTRIBUTE = "jaxbPlatform"; //$NON-NLS-1$	
	static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$	
	
	
	private static final JaxbPlatformUiManagerImpl INSTANCE = new JaxbPlatformUiManagerImpl();
	
	
	public static JaxbPlatformUiManagerImpl instance() {
		return INSTANCE;
	}
	
	
	private KeyedSet<String, JaxbPlatformUiConfig> platformUiConfigs;
	private KeyedSet<JaxbPlatformDescription, JaxbPlatformUiConfig> platformToUiConfigs;
	
	
	// ********** constructor/initialization **********
	
	private JaxbPlatformUiManagerImpl() {
		super();
		this.platformUiConfigs = new KeyedSet<String, JaxbPlatformUiConfig>();
		this.platformToUiConfigs = new KeyedSet<JaxbPlatformDescription, JaxbPlatformUiConfig>();
		readExtensions();
	}
	
	
	private void readExtensions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		final IExtensionPoint xpoint 
				= registry.getExtensionPoint(JptJaxbUiPlugin.PLUGIN_ID, EXTENSION_POINT_ID);
		
		if (xpoint == null) {
			throw new IllegalStateException();
		}
		
		List<IConfigurationElement> platformUiConfigs = new ArrayList<IConfigurationElement>();
		
		for (IExtension extension : xpoint.getExtensions()) {
			for (IConfigurationElement configElement : extension.getConfigurationElements()) {
				if (configElement.getName().equals(PLATFORM_UI_ELEMENT)) {
					platformUiConfigs.add(configElement);
				}
			}
		}
		
		for (IConfigurationElement configElement: platformUiConfigs) {
			readPlatformUiExtension(configElement);
		}
	}
	
	private void readPlatformUiExtension(IConfigurationElement element) {
		try {
			JaxbPlatformUiConfig platformUiConfig = new JaxbPlatformUiConfig();
			
			// plug-in id
			platformUiConfig.setPluginId(element.getContributor().getName());
			
			// id
			platformUiConfig.setId(findRequiredAttribute(element, ID_ATTRIBUTE));
			
			if (this.platformUiConfigs.containsKey(platformUiConfig.getId())) {
				logDuplicateExtension(QUALIFIED_EXTENSION_POINT_ID, ID_ATTRIBUTE, platformUiConfig.getId());
				throw new XPointException();
			}
			
			// jaxb platform id
			String jaxbPlatformId = findRequiredAttribute(element, PLATFORM_ATTRIBUTE);
			JaxbPlatformDescription jaxbPlatform = 
					JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform(jaxbPlatformId);
			
			if (jaxbPlatform == null) {
				logInvalidValue(element, PLATFORM_ATTRIBUTE, jaxbPlatformId);
			}
			
			if (this.platformToUiConfigs.containsKey(jaxbPlatform)) {
				logDuplicateExtension(QUALIFIED_EXTENSION_POINT_ID, PLATFORM_ATTRIBUTE, jaxbPlatformId);
				throw new XPointException();
			}
			
			platformUiConfig.setJaxbPlatform(jaxbPlatform);
			
			// class
			platformUiConfig.setClassName(findRequiredAttribute(element, CLASS_ATTRIBUTE));
			
			this.platformUiConfigs.addItem(platformUiConfig.getId(), platformUiConfig);
			this.platformToUiConfigs.addItem(jaxbPlatform, platformUiConfig);
		}
		catch (XPointException e) {
			// Ignore and continue. The problem has already been reported to the user
			// in the log.
		}
	}
	
	public JaxbPlatformUi getJaxbPlatformUi(JaxbPlatformDescription platformDesc) {
		JaxbPlatformUiConfig config = this.platformToUiConfigs.getItem(platformDesc);
		return (config == null) ? null : config.getPlatformUi();
	}
}
