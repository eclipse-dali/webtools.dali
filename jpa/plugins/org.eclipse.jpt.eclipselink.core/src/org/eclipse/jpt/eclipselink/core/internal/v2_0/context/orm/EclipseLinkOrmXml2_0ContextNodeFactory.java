/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer.Owner;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmSequenceGenerator2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlContextNodeFactory;


public class EclipseLinkOrmXml2_0ContextNodeFactory extends EclipseLinkOrmXmlContextNodeFactory
{	

	@Override
	public OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(OrmEmbeddedMapping2_0 parent, Owner owner, XmlAssociationOverrideContainer resourceAssociationOverrideContainer) {
		return new GenericOrmAssociationOverrideContainer(parent, owner, resourceAssociationOverrideContainer);
	}
	
	@Override
	public OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator2_0(parent, (XmlSequenceGenerator) resourceSequenceGenerator);
	}

}
