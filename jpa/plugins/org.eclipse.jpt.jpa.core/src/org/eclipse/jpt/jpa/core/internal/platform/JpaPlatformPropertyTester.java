/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.jpa.core.internal.platform;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformGroupConfig;

/**
 * Property tester for {@link JpaPlatformConfig}.
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.expressions.propertyTesters</code>
 */
public class JpaPlatformPropertyTester
	extends PropertyTester
{
	public static final String JPA_PLATFORM = "jpaPlatform"; //$NON-NLS-1$
	public static final String JPA_PLATFORM_GROUP = "jpaPlatformGroup"; //$NON-NLS-1$

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JpaPlatformConfig) {
			return this.test((JpaPlatformConfig) receiver, property, expectedValue);
		}
		return false;
	}
	
	private boolean test(JpaPlatformConfig config, String property, Object expectedValue) {
		if (property.equals(JPA_PLATFORM)) {
			JpaPlatformConfig expected = config.getJpaPlatformManager().getJpaPlatformConfig((String) expectedValue);
			return ObjectTools.equals(config, expected);
		}
		if (property.equals(JPA_PLATFORM_GROUP)) {
			JpaPlatformGroupConfig expected = config.getJpaPlatformManager().getJpaPlatformGroupConfig((String) expectedValue);
			return ObjectTools.equals(config.getGroupConfig(), expected);
		}
		return false;
	}
}
