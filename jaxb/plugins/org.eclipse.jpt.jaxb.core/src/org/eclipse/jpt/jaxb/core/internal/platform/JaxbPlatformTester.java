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
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupDescription;

/**
 * Property tester for {@link JaxbPlatformDescription}.
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml</code>
 */
public class JaxbPlatformTester
	extends PropertyTester
{
	public static final String JAXB_PLATFORM = "jaxbPlatform"; //$NON-NLS-1$
	public static final String JAXB_PLATFORM_GROUP = "jaxbPlatformGroup"; //$NON-NLS-1$

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JaxbPlatformDescription) {
			return this.test((JaxbPlatformDescription) receiver, property, expectedValue);
		}
		return false;
	}
	
	private boolean test(JaxbPlatformDescription platform, String property, Object expectedValue) {
		if (property.equals(JAXB_PLATFORM)) {
			JaxbPlatformDescription expected = this.getJaxbPlatform(expectedValue);
			return Tools.valuesAreEqual(platform, expected);
		}
		if (property.equals(JAXB_PLATFORM_GROUP)) {
			JaxbPlatformGroupDescription expected = this.getJaxbPlatformGroup(expectedValue);
			return Tools.valuesAreEqual(platform.getGroup(), expected);
		}
		return false;
	}

	private JaxbPlatformDescription getJaxbPlatform(Object id) {
		return JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform((String) id);
	}

	private JaxbPlatformGroupDescription getJaxbPlatformGroup(Object id) {
		return JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatformGroup((String) id);
	}
}
