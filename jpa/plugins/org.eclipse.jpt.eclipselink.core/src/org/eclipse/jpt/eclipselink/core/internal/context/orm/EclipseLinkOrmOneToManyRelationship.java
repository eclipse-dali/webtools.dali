/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmOneToManyRelationshipReference;
import org.eclipse.jpt.eclipselink.core.v2_0.context.orm.EclipseLinkOrmOneToManyRelationshipReference2_0;

/**
 * We need this class because the EclipseLink 1.0 1:m mapping supports a join
 * column strategy while JPA did not until JPA 2.0. As a result, we need a class
 * that implements EclipseLink 1.0.
 */
public class EclipseLinkOrmOneToManyRelationship
	extends GenericOrmOneToManyRelationshipReference
	implements EclipseLinkOrmOneToManyRelationshipReference2_0
{
	public EclipseLinkOrmOneToManyRelationship(OrmOneToManyMapping parent) {
		super(parent, true);  // true=supports join column strategy
	}
}
