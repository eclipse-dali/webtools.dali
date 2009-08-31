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

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;

public class GenericOrmAssociationOverrideRelationshipReference extends AbstractOrmAssociationOverrideRelationshipReference
{

	public GenericOrmAssociationOverrideRelationshipReference(OrmAssociationOverride parent, XmlAssociationOverride xao) {
		super(parent, xao);
	}

	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}
}
