/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java named query
 */
public class GenericJavaNamedQuery
	extends AbstractJavaQuery<NamedQueryAnnotation>
	implements JavaNamedQuery
{
	public GenericJavaNamedQuery(JavaQueryContainer parent, NamedQueryAnnotation queryAnnotation) {
		super(parent, queryAnnotation);
	}

	// ********** metadata conversion *********

	public void convertTo(OrmQueryContainer queryContainer) {
		queryContainer.addNamedQuery().convertFrom(this);
	}

	public void delete() {
		this.getParent().removeNamedQuery(this);
	}

	// ********** validation **********

	@Override
	protected void validateQuery_(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		queryHelper.validate(
			this,
			this.query,
			this.query,
			this.queryAnnotation.getQueryTextRanges(),
			1,
			JpaJpqlQueryHelper.EscapeType.JAVA,
			messages
		);
	}

	// ********** misc **********

	public Class<NamedQuery> getType() {
		return NamedQuery.class;
	}
}