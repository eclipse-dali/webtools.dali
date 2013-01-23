/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;

/**
 * <code>orm.xml</code> named query
 */
public class GenericOrmNamedQuery
	extends AbstractOrmNamedQuery
{

	public GenericOrmNamedQuery(JpaContextNode parent, XmlNamedQuery resourceNamedQuery) {
		super(parent, resourceNamedQuery);
	}
}
