/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingResourceUiDefinition;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractOrmXmlResourceUiDefinition
	extends AbstractMappingResourceUiDefinition
{

	/**
	 * zero-argument constructor
	 */
	protected AbstractOrmXmlResourceUiDefinition() {
		super();
	}


	// ********** type mappings **********

	public DefaultMappingUiDefinition getDefaultTypeMappingUiDefinition() {
		//there is no way to choose an type in the orm.xml that doesn't have a specified mapping so we can return null here
		return null;
	}


	// ********** attribute mappings **********

	public MappingUiDefinition getAttributeMappingUiDefinition(String mappingKey) {
		for (MappingUiDefinition definition : this.getAttributeMappingUiDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		return this.getUnsupportedOrmAttributeMappingUiDefinition();
	}

	protected MappingUiDefinition getUnsupportedOrmAttributeMappingUiDefinition() {
		return UnsupportedOrmAttributeMappingUiDefinition.instance();
	}

	public DefaultMappingUiDefinition getDefaultAttributeMappingUiDefinition(String mappingKey) {
		return null;
	}
}
