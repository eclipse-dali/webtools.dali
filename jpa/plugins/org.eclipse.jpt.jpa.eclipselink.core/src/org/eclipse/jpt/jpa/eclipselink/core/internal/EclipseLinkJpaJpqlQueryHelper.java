/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.jpql.spi.IManagedTypeBuilder;
import org.eclipse.jpt.jpa.eclipselink.core.jpql.spi.EclipseLinkManagedTypeBuilder;
import org.eclipse.jpt.jpa.eclipselink.core.jpql.spi.EclipseLinkMappingBuilder;
import org.eclipse.persistence.jpa.jpql.AbstractGrammarValidator;
import org.eclipse.persistence.jpa.jpql.AbstractSemanticValidator;
import org.eclipse.persistence.jpa.jpql.EclipseLinkGrammarValidator;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar;
import org.eclipse.persistence.jpa.jpql.tools.AbstractContentAssistVisitor;
import org.eclipse.persistence.jpa.jpql.tools.BasicRefactoringTool;
import org.eclipse.persistence.jpa.jpql.tools.DefaultRefactoringTool;
import org.eclipse.persistence.jpa.jpql.tools.EclipseLinkBasicRefactoringTool;
import org.eclipse.persistence.jpa.jpql.tools.EclipseLinkContentAssistVisitor;
import org.eclipse.persistence.jpa.jpql.tools.EclipseLinkJPQLQueryContext;
import org.eclipse.persistence.jpa.jpql.tools.EclipseLinkSemanticValidator;
import org.eclipse.persistence.jpa.jpql.tools.JPQLQueryContext;
import org.eclipse.persistence.jpa.jpql.tools.RefactoringTool;
import org.eclipse.persistence.jpa.jpql.tools.model.EclipseLinkJPQLQueryBuilder;
import org.eclipse.persistence.jpa.jpql.tools.model.IJPQLQueryBuilder;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappingBuilder;
import org.eclipse.persistence.jpa.jpql.tools.spi.IQuery;

/**
 * The abstract implementation of {@link JpaJpqlQueryHelper} that supports EclipseLink.
 *
 * @version 3.3
 * @since 3.1
 * @author Pascal Filion
 */
public class EclipseLinkJpaJpqlQueryHelper extends JpaJpqlQueryHelper {

	/**
	 * Creates a new <code>EclipseLinkJpaJpqlQueryHelper</code>.
	 *
	 * @param jpqlGrammar The grammar that defines how to parse a JPQL query
	 */
	public EclipseLinkJpaJpqlQueryHelper(JPQLGrammar jpqlGrammar) {
		super(jpqlGrammar);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BasicRefactoringTool buildBasicRefactoringTool() {
		return new EclipseLinkBasicRefactoringTool(
			getQuery().getExpression(),
			getGrammar(),
			getProvider()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractContentAssistVisitor buildContentAssistVisitor(JPQLQueryContext queryContext) {
		return new EclipseLinkContentAssistVisitor(queryContext);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractGrammarValidator buildGrammarValidator(JPQLGrammar jpqlGrammar) {
		return new EclipseLinkGrammarValidator(jpqlGrammar);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected JPQLQueryContext buildJPQLQueryContext(JPQLGrammar jpqlGrammar) {
		return new EclipseLinkJPQLQueryContext(jpqlGrammar);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IManagedTypeBuilder buildManagedTypeBuilder() {
		return new EclipseLinkManagedTypeBuilder();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IMappingBuilder<AttributeMapping> buildMappingBuilder() {
		return new EclipseLinkMappingBuilder();
	}

	/**
	 * Creates the right {@link IJPQLQueryBuilder} based on the JPQL grammar.
	 *
	 * @return A new concrete instance of {@link IJPQLQueryBuilder}
	 */
	protected IJPQLQueryBuilder buildQueryBuilder() {
		return new EclipseLinkJPQLQueryBuilder(getGrammar());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RefactoringTool buildRefactoringTool() {

		IQuery query = getQuery();

		return new DefaultRefactoringTool(
			query.getProvider(),
			buildQueryBuilder(),
			query.getExpression()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractSemanticValidator buildSemanticValidator(JPQLQueryContext queryContext) {
		return new EclipseLinkSemanticValidator(queryContext);
	}
}