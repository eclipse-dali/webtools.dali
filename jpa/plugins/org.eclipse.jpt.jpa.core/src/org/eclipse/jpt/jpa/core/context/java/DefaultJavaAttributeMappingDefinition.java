/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

public interface DefaultJavaAttributeMappingDefinition
	extends JavaAttributeMappingDefinition
{
	/**
	 * Return whether the definition's mapping is the "default" mapping for the
	 * specified persistent attribute.
	 */
	boolean isDefault(JavaSpecifiedPersistentAttribute persistentAttribute);
}
