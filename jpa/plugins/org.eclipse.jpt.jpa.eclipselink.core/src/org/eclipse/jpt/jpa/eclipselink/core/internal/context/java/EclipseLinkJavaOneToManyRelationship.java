/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.GenericJavaOneToManyRelationship;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaOneToManyRelationship2_0;

/**
 * We need this class because the EclipseLink 1.0 1:m mapping supports a join
 * column strategy while JPA did not until JPA 2.0. As a result, we need a class
 * that implements EclipseLink 1.0.
 */
public class EclipseLinkJavaOneToManyRelationship
	extends GenericJavaOneToManyRelationship
	implements EclipseLinkJavaOneToManyRelationship2_0
{
	public EclipseLinkJavaOneToManyRelationship(JavaOneToManyMapping parent) {
		super(parent, true);  // true=supports join column strategy
	}
}
