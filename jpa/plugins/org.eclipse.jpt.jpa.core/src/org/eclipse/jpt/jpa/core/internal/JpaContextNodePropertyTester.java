/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;

/**
 * Property tester for {@link JpaContextNode}.
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml</code>
 */
public class JpaContextNodePropertyTester
	extends PropertyTester
{
	public static final String IS_COMPATIBLE_VERSION = "isCompatibleVersion"; //$NON-NLS-1$


	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JpaContextNode) {
			return this.test((JpaContextNode) receiver, property, (String) expectedValue);
		}
		return false;
	}

	private boolean test(JpaContextNode contextNode, String property, String expectedValue) {
		if (property.equals(IS_COMPATIBLE_VERSION)) {
			return JptJpaCorePlugin.resourceTypeIsCompatible(contextNode.getResourceType(), expectedValue);
		}
		return false;
	}
}
