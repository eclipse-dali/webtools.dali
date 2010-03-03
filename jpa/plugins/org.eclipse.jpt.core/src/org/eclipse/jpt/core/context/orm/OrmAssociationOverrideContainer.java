/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;

public interface OrmAssociationOverrideContainer extends AssociationOverrideContainer, OrmOverrideContainer
{
	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> associationOverrides();

	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> specifiedAssociationOverrides();

	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> virtualAssociationOverrides();
	
	OrmAssociationOverride getAssociationOverrideNamed(String name);
	
	void update();
	
	interface Owner extends AssociationOverrideContainer.Owner, OrmOverrideContainer.Owner
	{				
		EList<XmlAssociationOverride> getResourceAssociationOverrides();
	}
}