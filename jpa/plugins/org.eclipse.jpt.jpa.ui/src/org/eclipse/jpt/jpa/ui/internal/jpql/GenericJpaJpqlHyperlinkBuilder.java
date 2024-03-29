/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpql;

import java.util.StringTokenizer;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.persistence.jpa.jpql.BaseDeclarationIdentificationVariableFinder;
import org.eclipse.persistence.jpa.jpql.parser.AbstractPathExpression;
import org.eclipse.persistence.jpa.jpql.parser.AbstractSchemaName;
import org.eclipse.persistence.jpa.jpql.parser.AbstractTraverseParentVisitor;
import org.eclipse.persistence.jpa.jpql.parser.CollectionValuedPathExpression;
import org.eclipse.persistence.jpa.jpql.parser.ConstructorExpression;
import org.eclipse.persistence.jpa.jpql.parser.EntityTypeLiteral;
import org.eclipse.persistence.jpa.jpql.parser.Expression;
import org.eclipse.persistence.jpa.jpql.parser.IdentificationVariable;
import org.eclipse.persistence.jpa.jpql.parser.QueryPosition;
import org.eclipse.persistence.jpa.jpql.parser.RangeVariableDeclaration;
import org.eclipse.persistence.jpa.jpql.parser.StateFieldPathExpression;
import org.eclipse.persistence.jpa.jpql.tools.resolver.Resolver;
import org.eclipse.persistence.jpa.jpql.tools.resolver.StateFieldResolver;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.tools.spi.IType;

/**
 * The default implementation of the builder that visit an {@link Expression} and creates {@link
 * IHyperlink} objects.
 *
 * @version 3.3
 * @since 3.3
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public class GenericJpaJpqlHyperlinkBuilder extends JpaJpqlHyperlinkBuilder {

	private RangeVariableDeclarationVisitor rangeVariableDeclarationVisitor;

	/**
	 * Creates a new <code>GenericJpaJpqlHyperlinkBuilder</code>.
	 *
	 * @param queryHelper This helper provides functionality related to JPQL queries
	 * @param namedQuery The model object representing the JPQL query
	 * @param queryPosition This object determines the position of the cursor within the parsed tree
	 */
	public GenericJpaJpqlHyperlinkBuilder(JpaJpqlQueryHelper queryHelper,
                                         NamedQuery namedQuery,
                                         QueryPosition queryPosition) {

		super(queryHelper, namedQuery, queryPosition);
	}

	/**
	 * Creates the visitor that can determine if the parent of an {@link Expression} is a {@link
	 * RangeVariableDeclarationVisitor}.
	 *
	 * @return A new {@link RangeVariableDeclarationVisitor}
	 */
	protected RangeVariableDeclarationVisitor buildRangeVariableDeclarationVisitor() {
		return new RangeVariableDeclarationVisitor();
	}

	protected final IdentificationVariable findVirtualIdentificationVariable(AbstractSchemaName expression) {
		BaseDeclarationIdentificationVariableFinder visitor = new BaseDeclarationIdentificationVariableFinder();
		expression.accept(visitor);
		return visitor.expression;
	}

	/**
	 * Determines whether the given {@link CollectionValuedPathExpression} is a direct child of {@link
	 * RangeVariableDeclaration}.
	 *
	 * @param expression The {@link CollectionValuedPathExpression} to start the visit
	 * @return <code>true</code> if the parent of the given {@link CollectionValuedPathExpression} is
	 * {@link RangeVariableDeclaration}; <code>false</code> otherwise
	 */
	protected final boolean isRangeVariableDeclaration(Expression expression) {
		RangeVariableDeclarationVisitor visitor = rangeVariableDeclarationVisitor();
		expression.accept(visitor);
		try {
			return visitor.rangeVariableDeclaration;
		}
		finally {
			visitor.rangeVariableDeclaration = false;
		}
	}

	/**
	 * Returns the visitor that can determine if the parent of an {@link Expression} is a {@link
	 * RangeVariableDeclaration}.
	 *
	 * @return {@link RangeVariableDeclarationVisitor}
	 */
	protected final RangeVariableDeclarationVisitor rangeVariableDeclarationVisitor() {
		if (rangeVariableDeclarationVisitor == null) {
			rangeVariableDeclarationVisitor = buildRangeVariableDeclarationVisitor();
		}
		return rangeVariableDeclarationVisitor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(AbstractSchemaName expression) {

		String text = expression.getText();

		// First check for an entity
		Entity entity = getEntityNamed(text);

		if (entity != null) {

			// XML file is not supported
			if (entity.getMappingFileRoot() == null) {
				IType type = getType(entity.getPersistentType().getName());
				addOpenDeclarationHyperlink(type, buildRegion(expression));
			}
		}
		else {

			// Check to see if the "root" path is a class name before assuming it's a derived path
			IType type = getType(text);

			// Fully qualified class name
			if (type.isResolvable()) {
				addOpenDeclarationHyperlink(type, buildRegion(expression));
			}
			// Now resolve a derived path expression (for subqueries)
			else {
				visitDerivedPathExpression(expression);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(CollectionValuedPathExpression expression) {

		// Check to see if the entity name is actually a fully qualified class name
		if (!expression.startsWithDot() && isRangeVariableDeclaration(expression)) {
			IType type = getType(expression.toActualText());
			if (type.isResolvable()) {
				addOpenDeclarationHyperlink(type, buildRegion(expression));
			}
			else {
				visitPathExpression(expression);
			}
		}
		else {
			visitPathExpression(expression);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(ConstructorExpression expression) {

		String className = expression.getClassName();

		int length        = className.length();
		int startOffset   = 3 /* NEW */ + (expression.hasSpaceAfterNew() ? 1 : 0);
		int startPosition = expression.getOffset() + startOffset;
		int endPosition   = startPosition + length;
		int position      = getPosition();

		// Make sure the cursor position is within the class name
		if ((position >= startPosition) && (position <= endPosition)) {

			IType type = getType(className);

			// Make sure the type is resolvable
			if (type.isResolvable()) {
				IRegion region = buildRegion(expression, startOffset, length);
				addOpenDeclarationHyperlink(type, region);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(EntityTypeLiteral expression) {

		String text = expression.getEntityTypeName();
		Entity entity = getEntityNamed(text);

		if (entity != null) {
			IType type = getType(entity.getPersistentType().getName());
			addOpenDeclarationHyperlink(type, buildRegion(expression));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(StateFieldPathExpression expression) {
		visitPathExpression(expression);
	}

	protected void visitDerivedPathExpression(AbstractSchemaName expression) {

		String text = expression.getText();
		int position = getPosition();
		int offset = expression.getOffset();
		int length = 0;
		Resolver resolver = null;

		// Unqualified derived path
		// Example: UPDATE Employee SET firstName = 'MODIFIED'
		//          WHERE (SELECT COUNT(m) FROM managedEmployees m) > 0
		if (text.indexOf(".") == -1) {

			// Find the identification variable from the UPDATE range declaration
			IdentificationVariable identificationVariable = findVirtualIdentificationVariable(expression);
			String variableName = (identificationVariable != null) ? identificationVariable.getText() : null;

			// Cannot continue
			if (StringTools.isBlank(variableName)) {
				return;
			}

			// Now resolve the superquery's identification variable
			resolver = getQueryContext().getResolver(variableName);
		}

		// Now traverse the path, even if it's an unqualified path, the above
		// if statement resolved the superquery's identification variable
		for (StringTokenizer tokenizer = new StringTokenizer(text, "."); tokenizer.hasMoreTokens(); ) {

			String path = tokenizer.nextToken();

			// Identification variable
			if (resolver == null) {

				// The cursor is over the general identification variable
				if (position <= offset + length + 1 /* DOT */) {
					return;
				}

				resolver = getQueryContext().getDeclarationResolver().getChild(path);

				// The identification variable cannot be resolved
				if (resolver == null) {
					break;
				}
			}
			else {

				Resolver childResolver = resolver.getChild(path);

				if (childResolver == null) {
					childResolver = new StateFieldResolver(resolver, path);
					resolver.addChild(path, childResolver);
				}

				IMapping mapping = childResolver.getMapping();

				// Invalid path expression
				if (mapping == null) {
					break;
				}

				// The position is within the current path
				if (position <= offset + length + path.length()) {
					addFieldHyperlinks(expression, mapping, length);
					break;
				}

				resolver = childResolver;
			}

			// Update the length before continuing
			length += path.length() + 1 /* DOT */;
		}
	}

	/**
	 * Visits the given {@link AbstractPathExpression} and determines the possible usages a path
	 * expression can be used for:
	 * <ul>
	 * <li>An actual path expression, example: e.name</li>
	 * <li>A fully qualified enum constant, example: javax.persistence.AccessType.FIELD</li>
	 * </ul>
	 *
	 * @param expression The {@link AbstractPathExpression} being visited
	 */
	protected void visitPathExpression(AbstractPathExpression expression) {

		// Nothing to do
		if (expression.startsWithDot()) {
			return;
		}

		int position = getPosition();
		int offset = expression.getOffset();

		// First, check for an enum type
		String fullPath = expression.toActualText();
		IType enumType = getQueryContext().getTypeRepository().getEnumType(fullPath);

		// The path expression is a fully qualified enum constant
		if (enumType != null) {

			int stopIndex = expression.pathSize() - 1;
			String enumConstant = expression.getPath(stopIndex);
			int enumTypeLength = expression.toParsedText(0, stopIndex).length();

			// The cursor is within the fully qualified enum type
			if (position <= offset + enumTypeLength) {
				addOpenDeclarationHyperlink(
					enumType,
					buildRegion(expression, 0, enumTypeLength)
				);
			}
			// The cursor is within the enum constant name
			else {
				for (String constantName : enumType.getEnumConstants()) {
					if (enumConstant.equals(constantName)) {
						addFieldHyperlinks(
							expression,
							enumType,
							enumType,
							constantName,
							enumTypeLength + 1 /* DOT */
						);
						break;
					}
				}
			}
		}
		else {

			// Check to see if the position is within the general identification variable
			Expression identificationVariable = expression.getIdentificationVariable();
			int length = 0;

			if (!expression.hasVirtualIdentificationVariable()) {
				String variable = identificationVariable.toActualText();
				length = variable.length();

				// The cursor is over the general identification variable
				if (position <= offset + length + 1 /* DOT */) {
					return;
				}

				// Dot between general identification variable and the first path
				length++;
			}

			// Resolve the general identification variable
			Resolver resolver = getQueryContext().getResolver(identificationVariable);

			// Can't continue to resolve the path expression
			if (resolver == null) {
				return;
			}

			// Now traverse the path expression after the identification variable
			for (int index = expression.hasVirtualIdentificationVariable() ? 0 : 1, count = expression.pathSize(); index < count; index++) {

				// Retrieve the mapping for the path at the current position
				String path = expression.getPath(index);
				Resolver childResolver = resolver.getChild(path);

				if (childResolver == null) {
					childResolver = new StateFieldResolver(resolver, path);
					resolver.addChild(path, childResolver);
				}

				IMapping mapping = childResolver.getMapping();

				// Invalid path expression
				if (mapping == null) {
					break;
				}

				// The position is within the current path
				if (position <= offset + length + path.length()) {
					addFieldHyperlinks(expression, mapping, length);
					break;
				}

				length += path.length() + 1 /* DOT */;
				resolver = childResolver;
			}
		}
	}

	protected class RangeVariableDeclarationVisitor extends AbstractTraverseParentVisitor {

		/**
		 * Determines whether the parent of the visited {@link Expression} is {@link RangeVariableDeclaration}.
		 */
		protected boolean rangeVariableDeclaration;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(RangeVariableDeclaration expression) {
			rangeVariableDeclaration = true;
		}
	}
}