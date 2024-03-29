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

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.persistence.jpa.jpql.parser.AbstractExpressionVisitor;
import org.eclipse.persistence.jpa.jpql.parser.Expression;
import org.eclipse.persistence.jpa.jpql.parser.QueryPosition;
import org.eclipse.persistence.jpa.jpql.tools.JPQLQueryContext;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.tools.spi.IType;

/**
 * The abstract definition of a builder that visit an {@link Expression} and creates the supported
 * list of {@link IHyperlink} objects, which can open the associated object. For instance, a {@link
 * IHyperlink} for the entity name (abstract schema name) opens the Java editor on the associated
 * Java file.
 *
 * @version 3.3
 * @since 3.3
 * @author Pascal Filion
 */
public abstract class JpaJpqlHyperlinkBuilder extends AbstractExpressionVisitor {

	private Collection<IHyperlink> hyperlinks;
	private NamedQuery namedQuery;
	private JpaJpqlQueryHelper queryHelper;
	private QueryPosition queryPosition;

	/**
	 * Creates a new <code>JpaJpqlHyperlinkBuilder</code>.
	 *
	 * @param queryHelper This helper provides functionality related to JPQL queries
	 * @param namedQuery The model object representing the JPQL query
	 * @param queryPosition This object determines the position of the cursor within the parsed tree
	 */
	protected JpaJpqlHyperlinkBuilder(JpaJpqlQueryHelper queryHelper,
                                     NamedQuery namedQuery,
                                     QueryPosition queryPosition) {

		super();
		this.namedQuery    = namedQuery;
		this.queryHelper   = queryHelper;
		this.queryPosition = queryPosition;
		this.hyperlinks    = new ArrayList<IHyperlink>();
	}

	/**
	 * Adds two {@link IHyperlink} actions for the given {@link IMapping}: 'Open Declaration' and
	 * 'Open Declared Type}.
	 *
	 * @param expression The {@link Expression} being visited where {@link IHyperlink} objects can be
	 * created for the given {@link IMapping}
	 * @param mapping The {@link IMapping} for which a {@link IHyperlink} will be created, which will
	 * target the actual object: field or method
	 * @param offset The offset to be added to the {@link IRegion}'s offset, which is the beginning
	 * of the {@link IHyperlink}
	 */
	protected final void addFieldHyperlinks(Expression expression,
	                                        IMapping mapping,
	                                        int offset) {

		addFieldHyperlinks(
			expression,
			mapping.getParent().getType(),
			mapping.getType(),
			mapping.getName(),
			offset
		);
	}

	/**
	 * Adds two {@link IHyperlink} actions for the given {@link IMapping}: 'Open Declaration' and
	 * 'Open Declared Type}.
	 *
	 * @param expression The {@link Expression} being visited where {@link IHyperlink} objects can be
	 * created for the given {@link IMapping}
	 * @param parentType The {@link IType} of the parent of the mapping
	 * @param fieldType The {@link IType} of the mapping
	 * @param memberName The name of the given parent type's member
	 * @param offset The offset to be added to the {@link IRegion}'s offset, which is the beginning
	 * of the {@link IHyperlink}
	 */
	protected final void addFieldHyperlinks(Expression expression,
	                                        IType parentType,
	                                        IType fieldType,
	                                        String memberName,
	                                        int offset) {

		IRegion region = buildRegion(expression, offset, memberName.length());
		ISourceReference element = getJavaElement(parentType, memberName);

		// 'Open Declaration' opens the declaration of the persistent type
		if (element != null) {
			addOpenMemberDeclarationHyperlink(
				parentType,
				element,
				region,
				JptJpaUiMessages.JPA_JPQL_HYPERLINK_BUILDER_OPEN_DECLARATION
			);
		}

		// 'Open Declared Type' opens the member's type
		if (fieldType.isResolvable()) {
			addOpenDeclarationHyperlink(
				fieldType,
				region,
				JptJpaUiMessages.JPA_JPQL_HYPERLINK_BUILDER_OPEN_DECLARED_TYPE
			);
		}
	}

	/**
	 * Adds the given {@link IHyperlink}.
	 *
	 * @param hyperlink A {@link IHyperlink} that can open the object represented by the visited
	 * {@link Expression}
	 */
	protected final void addHyperlink(IHyperlink hyperlink) {
		hyperlinks.add(hyperlink);
	}

	/**
	 * Adds a {@link IHyperlink} to open the editor on the given type.
	 *
	 * @param hyperlink A {@link IHyperlink} that can open the object represented by the visited
	 * {@link Expression}.
	 */
	protected final void addOpenDeclarationHyperlink(IType type, IRegion region) {
		addHyperlink(buildOpenDeclarationHyperlink(type, region));
	}

	/**
	 * Adds a {@link IHyperlink} to open the editor on the given type.
	 *
	 * @param type The {@link IType} to open if the hyperlink is chosen
	 * @param region The {@link IRegion} represents the region to display the hyperlink within the JPQL query
	 * @param hyperlinkText The text of the {@link IHyperlink}
	 */
	protected final void addOpenDeclarationHyperlink(IType type, IRegion region, String hyperlinkText) {
		addHyperlink(buildOpenDeclarationHyperlink(type, region, hyperlinkText));
	}

	/**
	 * Adds a {@link IHyperlink} to open the editor on the given type's member.
	 *
	 * @param type The {@link IType} to open if the hyperlink is chosen
	 * @param member The member, a child element of the type, to select
	 * @param region The {@link IRegion} represents the region to display the hyperlink within the JPQL query
	 * @param hyperlinkText The text of the {@link IHyperlink}
	 */
	protected final void addOpenMemberDeclarationHyperlink(IType type,
	                                                       ISourceReference member,
	                                                       IRegion region,
	                                                       String hyperlinkText) {

		addHyperlink(buildOpenMemberDeclarationHyperlink(type, member, region, hyperlinkText));
	}

	/**
	 * Returns the position of the given {@link Expression} relative to the actual JPQL query.
	 *
	 * @param expression The {@link Expression} to retrieve its offset within the JPQL query
	 * @return The offset of the given {@link Expression} within the JPQL query but based on the
	 * actual string representation of the JPQL query
	 */
	protected final int adjustedPosition(Expression expression) {
		return adjustedPosition(expression, 0);
	}

	/**
	 * Returns the position of the given {@link Expression} relative to the actual JPQL query.
	 *
	 * @param expression The {@link Expression} to retrieve its offset within the JPQL query
	 * @param offset The offset to be added to the {@link IRegion}'s offset, which is the beginning
	 * of the {@link IHyperlink}
	 * @return The offset of the given {@link Expression} within the JPQL query but based on the
	 * actual string representation of the JPQL query
	 */
	protected final int adjustedPosition(Expression expression, int offset) {

		int position = ExpressionTools.repositionCursor(
			expression.getRoot().toActualText(),
			expression.getOffset(),
			namedQuery.getQuery()
		);

		return position + offset;
	}

	/**
	 * Creates a new {@link IHyperlink} that can open the editor on the given {@link IType}.
	 *
	 * @param type The {@link IType} to open if the hyperlink is chosen
	 * @param region The {@link IRegion} represents the region to display the hyperlink within the JPQL query
	 * @return A new {@link IHyperlink}
	 */
	protected final IHyperlink buildOpenDeclarationHyperlink(IType type, IRegion region) {
		return buildOpenDeclarationHyperlink(
			type,
			region,
			JptJpaUiMessages.JPA_JPQL_HYPERLINK_BUILDER_OPEN_DECLARATION
		);
	}

	/**
	 * Creates a new {@link IHyperlink} that can open the editor on the given {@link IType}.
	 *
	 * @param type The {@link IType} to open if the hyperlink is chosen
	 * @param region The {@link IRegion} represents the region to display the hyperlink within the JPQL query
	 * @param hyperlinkText The text of the {@link IHyperlink}
	 * @return A new {@link IHyperlink}
	 */
	protected final IHyperlink buildOpenDeclarationHyperlink(IType type,
	                                                         IRegion region,
	                                                         String hyperlinkText) {

		return new OpenDeclarationHyperlink(javaProject(), type.getName(), region, hyperlinkText);
	}

	/**
	 * Creates a new {@link IHyperlink} that can open the editor on a member of the given {@link IType}.
	 *
	 * @param type The {@link IType} to open if the hyperlink is chosen
	 * @param member The member, a child element of the type, to select
	 * @param region The {@link IRegion} represents the region to display the hyperlink within the JPQL query
	 * @param hyperlinkText The text of the {@link IHyperlink}
	 * @return A new {@link IHyperlink}
	 */
	protected final IHyperlink buildOpenMemberDeclarationHyperlink(IType type,
	                                                               ISourceReference member,
	                                                               IRegion region,
	                                                               String hyperlinkText) {

		return new OpenMemberDeclarationHyperlink(
			javaProject(),
			type.getName(),
			member,
			region,
			hyperlinkText
		);
	}

	/**
	 * Creates a new {@link IRegion} representing the location of the given {@link Expression} within
	 * the JPQL query.
	 *
	 * @param expression The {@link Expression} is used to calculate create the region within the JPQL query
	 * @return A new {@link IRegion}
	 */
	protected final IRegion buildRegion(Expression expression) {
		return buildRegion(expression, 0);
	}

	/**
	 * Creates a new {@link IRegion} where the location is within the given {@link Expression}.
	 *
	 * @param expression The {@link Expression} is used to calculate create the region within the JPQL query
	 * @param offset The offset to be added to the {@link IRegion}'s offset, which is the beginning
	 * of the {@link IHyperlink}
	 * @return A new {@link IRegion}
	 */
	protected final IRegion buildRegion(Expression expression, int offset) {
		return buildRegion(expression, offset, expression.toActualText().length());
	}

	/**
	 * Creates a new {@link IRegion} where the location is within the given {@link Expression}.
	 *
	 * @param expression The {@link Expression} is used to calculate create the region within the JPQL query
	 * @param offset The offset to be added to the {@link IRegion}'s offset, which is the beginning
	 * of the {@link IHyperlink}
	 * @return A new {@link IRegion}
	 */
	protected final IRegion buildRegion(Expression expression, int offset, int length) {
		return new Region(
			adjustedPosition(expression, offset),
			length
		);
	}

	/**
	 * Returns the {@link Entity} with the given fully qualified type name.
	 *
	 * @param typeName The fully qualified type name of the entity to retrieve
	 * @return The design-time representation of the entity or <code>null</code> if it could not be found
	 */
	protected Entity getEntity(String typeName) {
		return getNamedQuery().getPersistenceUnit().getEntity(typeName);
	}

	/**
	 * Returns the {@link Entity} with the given name.
	 *
	 * @param entityName The name of the entity to retrieve
	 * @return The design-time representation of the entity or <code>null</code> if it could not be found
	 */
	protected Entity getEntityNamed(String entityName) {

		for (Entity entity : getNamedQuery().getPersistenceUnit().getEntities()) {
			if (entity.getName().equals(entityName)) {
				return entity;
			}
		}

		return null;
	}

	/**
	 * Returns the Java element corresponding to the given member name (mapping name).
	 *
	 * @param parentType The type used to retrieve the entity
	 * @param memberName The name of the member to retrieve its {@link ISourceReference}
	 * @return The {@link ISourceReference} for the given member or <code>null</code> if it cannot
	 * be found in the Java source file
	 */
	protected final ISourceReference getJavaElement(IType parentType, String memberName) {

		Entity entity = getEntity(parentType.getName());

		// This could happen if the member is an enum constant
		if (entity == null) {
			return null;
		}

		PersistentAttribute attribute = entity.getPersistentType().getAttributeNamed(memberName);

		if (attribute == null) {
			return null;
		}

		return (ISourceReference) attribute.getJavaPersistentAttribute().getJavaElement();
	}

	/**
	 * Returns the model object of the JPQL query being edited.
	 *
	 * @return The {@link NamedQuery} being edited
	 */
	protected NamedQuery getNamedQuery() {
		return namedQuery;
	}

	/**
	 * Returns the position of the cursor in the query.
	 *
	 * @return The position of the cursor in the query
	 */
	protected final int getPosition() {
		return queryPosition.getPosition();
	}

	/**
	 * Returns the position of the cursor within the given {@link Expression}
	 *
	 * @param expression The {@link Expression} for which the position of the cursor is requested
	 * @return Either the position of the cursor within the given {@link Expression} or -1 if the
	 * cursor is not within it
	 */
	protected final int getPosition(Expression expression) {
		return queryPosition.getPosition(expression);
	}

	/**
	 * Returns the {@link JPQLQueryContext} that contains information about the JPQL query.
	 *
	 * @return The {@link JPQLQueryContext} that contains information contained in the JPQL query
	 */
	protected JPQLQueryContext getQueryContext() {
		return queryHelper.getQueryContext();
	}

	/**
	 * Returns the helper that can provide functionality related to JPQL query.
	 *
	 * @return The helper that contained cached information related to the JPQL query
	 */
	protected JpaJpqlQueryHelper getQueryHelper() {
		return queryHelper;
	}

	/**
	 * Returns the object that contains the position of the cursor within the parsed tree.
	 *
	 * @return The stack of {@link Expression} mapped to the position starting from the root to the
	 * child {@link Expression}
	 */
	protected QueryPosition getQueryPosition() {
		return queryPosition;
	}

	/**
	 * Retrieves the external class for the given fully qualified class name.
	 *
	 * @param typeName The fully qualified class name of the class to retrieve
	 * @return The external form of the class to retrieve
	 */
	protected final IType getType(String typeName) {
		return queryHelper.getTypeRepository().getType(typeName);
	}

	/**
	 * Returns an array of the {@link IHyperlink} objects that can open the corresponding object
	 * represented by the visited {@link Expression}.
	 *
	 * @return A non-<code>null</code> array
	 */
	protected final IHyperlink[] hyperlinks() {

		int size = hyperlinks.size();

		// HyperlinkManager throws an exception for an empty array
		if (size == 0) {
			return null;
		}

		return hyperlinks.toArray(new IHyperlink[size]);
	}

	/**
	 * Returns the Java project associated with the JPA project.
	 *
	 * @return The {@link IJavaProject} owning the JPA project
	 */
	protected final IJavaProject javaProject() {
		return namedQuery.getJpaProject().getJavaProject();
	}
}
