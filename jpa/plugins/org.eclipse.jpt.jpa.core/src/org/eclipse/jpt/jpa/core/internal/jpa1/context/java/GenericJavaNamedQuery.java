/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.jpa.core.internal.jpql.JpaJpqlQueryHelper;
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
	public GenericJavaNamedQuery(JavaJpaContextNode parent, NamedQueryAnnotation queryAnnotation) {
		super(parent, queryAnnotation);
	}


	// ********** validation **********

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

		JpaJpqlQueryHelper helper = new JpaJpqlQueryHelper();
		helper.validate(this, this.getQuery(), this.getQueryAnnotation().getQueryTextRange(astRoot), 1, messages);
	}
}