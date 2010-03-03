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
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;

public interface OrmAttributeOverrideContainer extends AttributeOverrideContainer, OrmOverrideContainer
{
	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> attributeOverrides();

	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> specifiedAttributeOverrides();

	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> virtualAttributeOverrides();
		
	OrmAttributeOverride getAttributeOverrideNamed(String name);
	
	void update();
	
	void initializeFromAttributeOverrideContainer(OrmAttributeOverrideContainer oldContainer);
	
	interface Owner extends AttributeOverrideContainer.Owner, OrmOverrideContainer.Owner
	{		
		
		/**
		 * Build a virtual xml column based on the overridable column.
		 */
		XmlColumn buildVirtualXmlColumn(Column overridableColumn, String attributeName, boolean isMetadataComplete);
		
		EList<XmlAttributeOverride> getResourceAttributeOverrides();
	}
}