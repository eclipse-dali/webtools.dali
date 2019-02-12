/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.jpa.core.context.XmlFile;

/**
 * Property tester for {@link XmlFile}.
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.expressions.propertyTesters</code>
 */
public class XmlFilePropertyTester
	extends PropertyTester
{
	public static final String IS_LATEST_SUPPORTED_VERSION = "isLatestSupportedVersion"; //$NON-NLS-1$
	public static final String IS_NOT_LATEST_SUPPORTED_VERSION = "isNotLatestSupportedVersion"; //$NON-NLS-1$
	public static final String IS_GENERIC_MAPPING_FILE = "isGenericMappingFile"; //$NON-NLS-1$


	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof XmlFile) {
			return this.test((XmlFile) receiver, property, expectedValue);
		}
		return false;
	}

	private boolean test(XmlFile xmlFile, String property, Object expectedValue) {
		if (property.equals(IS_NOT_LATEST_SUPPORTED_VERSION)) {
			return ! this.test(xmlFile, IS_LATEST_SUPPORTED_VERSION, expectedValue);
		}
		if (property.equals(IS_LATEST_SUPPORTED_VERSION)) {
			boolean expected = (expectedValue == null) ? true : ((Boolean) expectedValue).booleanValue();
			boolean actual = xmlFile.isLatestSupportedVersion();
			return actual == expected;
		}
		if (property.equals(IS_GENERIC_MAPPING_FILE)) {
			boolean expected = (expectedValue == null) ? true : ((Boolean) expectedValue).booleanValue();
			boolean actual = xmlFile.isGenericMappingFile();
			return actual == expected;
		}
		return false;
	}
}
