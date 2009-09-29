/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlDerivedId;

public interface OrmXml2_0ContextNodeFactory extends OrmXmlContextNodeFactory
{
	// ********** ORM Context Model **********

	OrmDerivedId2_0 buildOrmDerivedId(OrmSingleRelationshipMapping2_0 parent, XmlDerivedId resource);

	OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(OrmEmbeddedMapping2_0 parent, OrmAssociationOverrideContainer.Owner owner, XmlAssociationOverrideContainer resourceAssociationOverrideContainer);
}
