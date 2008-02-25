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
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableUniqueConstraint;
import org.eclipse.jpt.core.resource.java.TableAnnotation;

public class TableImpl extends AbstractTableResource
{
	private static final String ANNOTATION_NAME = JPA.TABLE;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__CATALOG);
	
	protected TableImpl(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	public String getAnnotationName() {
		return TableAnnotation.ANNOTATION_NAME;
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
		return UniqueConstraintImpl.createTableUniqueConstraint(this, this.getMember(), index);
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

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new TableImpl(parent, member);
		}

		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NullTable(parent);
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
