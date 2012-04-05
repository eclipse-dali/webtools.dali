/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.jpql.spi.GenericMappingBuilder;
import org.eclipse.jpt.jpa.core.jpql.spi.IManagedTypeBuilder;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeBuilder;
import org.eclipse.persistence.jpa.jpql.AbstractContentAssistVisitor;
import org.eclipse.persistence.jpa.jpql.AbstractGrammarValidator;
import org.eclipse.persistence.jpa.jpql.AbstractSemanticValidator;
import org.eclipse.persistence.jpa.jpql.DefaultContentAssistVisitor;
import org.eclipse.persistence.jpa.jpql.DefaultGrammarValidator;
import org.eclipse.persistence.jpa.jpql.DefaultJPQLQueryContext;
import org.eclipse.persistence.jpa.jpql.DefaultRefactoringTool;
import org.eclipse.persistence.jpa.jpql.DefaultSemanticValidator;
import org.eclipse.persistence.jpa.jpql.JPQLQueryContext;
import org.eclipse.persistence.jpa.jpql.RefactoringTool;
import org.eclipse.persistence.jpa.jpql.model.IJPQLQueryBuilder;
import org.eclipse.persistence.jpa.jpql.model.JPQLQueryBuilder1_0;
import org.eclipse.persistence.jpa.jpql.model.JPQLQueryBuilder2_0;
import org.eclipse.persistence.jpa.jpql.model.JPQLQueryBuilder2_1;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar;
import org.eclipse.persistence.jpa.jpql.spi.IMappingBuilder;
import org.eclipse.persistence.jpa.jpql.spi.IQuery;

/**
 * The default implementation of {@link JpaJpqlQueryHelper} that provides support based on the Java
 * Persistence functional specification (version 1.0 and 2.0).
 *
 * @version 3.2
 * @since 3.1
 * @author Pascal Filion
 */
public class GenericJpaJpqlQueryHelper extends JpaJpqlQueryHelper {

	/**
	 * Creates a new <code>AbstractJpaJpqlQueryHelper</code>.
	 *
	 * @param jpqlGrammar The grammar that defines how to parse a JPQL query
	 */
	public GenericJpaJpqlQueryHelper(JPQLGrammar jpqlGrammar) {
		super(jpqlGrammar);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractContentAssistVisitor buildContentAssistVisitor(JPQLQueryContext queryContext) {
		return new DefaultContentAssistVisitor(queryContext);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractGrammarValidator buildGrammarValidator(JPQLQueryContext queryContext) {
		return new DefaultGrammarValidator(queryContext.getGrammar());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected JPQLQueryContext buildJPQLQueryContext(JPQLGrammar jpqlGrammar) {
		return new DefaultJPQLQueryContext(jpqlGrammar);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IManagedTypeBuilder buildManagedTypeBuilder() {
		return new JpaManagedTypeBuilder();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IMappingBuilder<AttributeMapping> buildMappingBuilder() {
		return new GenericMappingBuilder();
	}

	/**
	 * Creates the right {@link IJPQLQueryBuilder} based on the JPQL grammar.
	 *
	 * @return A new concrete instance of {@link IJPQLQueryBuilder}
	 */
	protected IJPQLQueryBuilder buildQueryBuilder() {
		switch (getGrammar().getJPAVersion()) {
			case VERSION_1_0: return new JPQLQueryBuilder1_0();
			case VERSION_2_0: return new JPQLQueryBuilder2_0();
			default:          return new JPQLQueryBuilder2_1();
		}
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
		return new DefaultSemanticValidator(queryContext);
	}
}