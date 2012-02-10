/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.jpa.core.internal.platform;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformGroupDescription;

/**
 * Property tester for {@link JpaPlatformDescription}.
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml</code>
 */
public class JpaPlatformTester
	extends PropertyTester
{
	public static final String JPA_PLATFORM = "jpaPlatform"; //$NON-NLS-1$
	public static final String JPA_PLATFORM_GROUP = "jpaPlatformGroup"; //$NON-NLS-1$

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JpaPlatformDescription) {
			return this.test((JpaPlatformDescription) receiver, property, expectedValue);
		}
		return false;
	}
	
	private boolean test(JpaPlatformDescription platform, String property, Object expectedValue) {
		if (property.equals(JPA_PLATFORM)) {
			JpaPlatformDescription expected = this.getJpaPlatform(expectedValue);
			return Tools.valuesAreEqual(platform, expected);
		}
		if (property.equals(JPA_PLATFORM_GROUP)) {
			JpaPlatformGroupDescription expected = this.getJpaPlatformGroup(expectedValue);
			return Tools.valuesAreEqual(platform.getGroup(), expected);
		}
		return false;
	}

	private JpaPlatformDescription getJpaPlatform(Object id) {
		return JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform((String) id);
	}

	private JpaPlatformGroupDescription getJpaPlatformGroup(Object id) {
		return JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatformGroup((String) id);
	}
}
