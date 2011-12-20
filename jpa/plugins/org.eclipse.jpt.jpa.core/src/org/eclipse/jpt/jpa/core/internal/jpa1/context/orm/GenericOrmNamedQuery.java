/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;

import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> named query
 */
public class GenericOrmNamedQuery
	extends AbstractOrmQuery<XmlNamedQuery>
	implements OrmNamedQuery
{
	public GenericOrmNamedQuery(XmlContextNode parent, XmlNamedQuery resourceNamedQuery) {
		super(parent, resourceNamedQuery);
	}


	// ********** validation **********

	@Override
	protected void validateQuery_(java.util.List<IMessage> messages, IReporter reporter) {

		// Convert the literal escape characters into actual escape characters
		String jpqlQuery = ExpressionTools.unescape(this.query, new int[1]);

		JpaJpqlQueryHelper helper = getJpaPlatform().getJpqlQueryHelper();
		helper.validate(this, jpqlQuery, this.getQueryTextRange(), 0, messages);
	}

	// ********** misc **********
	
	public Class<NamedQuery> getType() {
		return NamedQuery.class;
	}
}
