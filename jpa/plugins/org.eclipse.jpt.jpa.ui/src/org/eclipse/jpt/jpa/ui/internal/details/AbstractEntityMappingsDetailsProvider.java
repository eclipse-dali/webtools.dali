/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;

public abstract class AbstractEntityMappingsDetailsProvider
	implements JpaDetailsProvider
{
	protected AbstractEntityMappingsDetailsProvider() {
		super();
	}
	
	
	public final boolean providesDetails(JpaStructureNode structureNode) {
		return Tools.valuesAreEqual(structureNode.getId(), OrmStructureNodes.ENTITY_MAPPINGS_ID)
				&& providesDetails(structureNode.getResourceType());
	}
	
	protected abstract boolean providesDetails(JptResourceType resourceType);
}
