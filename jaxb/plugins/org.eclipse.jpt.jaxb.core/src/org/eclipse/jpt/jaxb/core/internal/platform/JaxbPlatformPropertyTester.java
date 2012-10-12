/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.platform;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;

/**
 * Property tester for {@link JaxbPlatformConfig}.
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.core.expressions.propertyTesters</code>
 */
public class JaxbPlatformPropertyTester
	extends PropertyTester
{
	public static final String JAXB_PLATFORM = "jaxbPlatform"; //$NON-NLS-1$
	public static final String JAXB_PLATFORM_GROUP = "jaxbPlatformGroup"; //$NON-NLS-1$

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JaxbPlatformConfig) {
			return this.test((JaxbPlatformConfig) receiver, property, expectedValue);
		}
		return false;
	}
	
	private boolean test(JaxbPlatformConfig platformConfig, String property, Object expectedValue) {
		if (property.equals(JAXB_PLATFORM)) {
			JaxbPlatformConfig expected = this.getJaxbPlatformConfig(expectedValue);
			return ObjectTools.equals(platformConfig, expected);
		}
		if (property.equals(JAXB_PLATFORM_GROUP)) {
			JaxbPlatformGroupConfig expected = this.getJaxbPlatformGroupConfig(expectedValue);
			return ObjectTools.equals(platformConfig.getGroupConfig(), expected);
		}
		return false;
	}

	private JaxbPlatformConfig getJaxbPlatformConfig(Object id) {
		return this.getJaxbPlatformManager().getJaxbPlatformConfig((String) id);
	}

	private JaxbPlatformGroupConfig getJaxbPlatformGroupConfig(Object id) {
		return this.getJaxbPlatformManager().getJaxbPlatformGroupConfig((String) id);
	}

	private JaxbPlatformManager getJaxbPlatformManager() {
		return getJaxbWorkspace().getJaxbPlatformManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}
}
