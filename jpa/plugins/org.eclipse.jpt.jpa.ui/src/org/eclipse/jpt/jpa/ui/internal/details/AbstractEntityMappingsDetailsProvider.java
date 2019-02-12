/*******************************************************************************
* Copyright (c) 2009, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;

public abstract class AbstractEntityMappingsDetailsProvider
	implements JpaDetailsProvider
{
	protected AbstractEntityMappingsDetailsProvider() {
		super();
	}


	public Class<EntityMappings> getStructureType() {
		return EntityMappings.class;
	}
}
