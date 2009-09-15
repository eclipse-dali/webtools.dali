/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAssociationOverrideContainer;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer;

public class GenericOrmAssociationOverrideContainer extends AbstractOrmAssociationOverrideContainer
{

	public GenericOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner, XmlAssociationOverrideContainer resource) {
		super(parent, owner, resource);
	}
	
	@Override
	protected XmlAssociationOverride buildResourceAssociationOverride() {
		return OrmFactory.eINSTANCE.createXmlAssociationOverride();
	}

}
