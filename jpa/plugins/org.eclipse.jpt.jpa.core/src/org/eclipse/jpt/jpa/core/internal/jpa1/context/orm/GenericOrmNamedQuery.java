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

import java.util.List;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmQuery;
import org.eclipse.jpt.jpa.core.internal.jpql.JpaJpqlQueryHelper;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		// Convert the literal escape characters into actual escape characters
		String jpqlQuery = ExpressionTools.unescape(this.getQuery(), new int[1]);

		JpaJpqlQueryHelper helper = new JpaJpqlQueryHelper();
		helper.validate(this, jpqlQuery, this.getQueryTextRange(), 0, messages);
	}
}