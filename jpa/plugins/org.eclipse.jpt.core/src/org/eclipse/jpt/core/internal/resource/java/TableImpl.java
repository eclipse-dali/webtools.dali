/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class TableImpl extends AbstractTableResource
{
	private static final String ANNOTATION_NAME = JPA.TABLE;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__CATALOG);
	
	protected TableImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	protected DeclarationAnnotationElementAdapter<String> nameAdapter(@SuppressWarnings("unused") DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @Table is never nested
		return NAME_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> schemaAdapter(@SuppressWarnings("unused") DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @Table is never nested
		return SCHEMA_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> catalogAdapter(@SuppressWarnings("unused") DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @Table is never nested
		return CATALOG_ADAPTER;
	}
	
	@Override
	protected NestableUniqueConstraint createUniqueConstraint(int index) {
		return UniqueConstraintImpl.createTableUniqueConstraint(new UniqueConstraintOwner(this), this.getMember(), index);
	}
	
	public static class TableAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final TableAnnotationDefinition INSTANCE = new TableAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private TableAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new TableImpl(parent, member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
