/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

public interface DefaultJavaAttributeMappingDefinition
	extends JavaAttributeMappingDefinition
{
	/**
	 * Return whether the definition's mapping is the "default" mapping for the
	 * specified persistent attribute.
	 */
	boolean isDefault(JavaPersistentAttribute persistentAttribute);
}
