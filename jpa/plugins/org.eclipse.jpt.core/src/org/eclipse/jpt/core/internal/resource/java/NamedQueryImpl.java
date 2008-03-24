/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableNamedQuery;
import org.eclipse.jpt.core.resource.java.NestableQueryHint;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;

public class NamedQueryImpl extends AbstractNamedQuery
	implements NestableNamedQuery
{

	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	protected NamedQueryImpl(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//************* AbstractNamedQuery implementation *************
	@Override
	protected String nameElementName() {
		return JPA.NAMED_QUERY__NAME;
	}
	
	@Override
	protected String queryElementName() {
		return JPA.NAMED_QUERY__QUERY;
	}
	
	@Override
	protected NestableQueryHint createQueryHint(int index) {
		return QueryHintImpl.createNamedQueryQueryHint(this, getMember(), this.getDeclarationAnnotationAdapter(), index);
	}

	// ********** static methods **********
	static NamedQueryImpl createNamedQuery(JavaResourceNode parent, Type type) {
		return new NamedQueryImpl(parent, type, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	static NamedQueryImpl createNestedNamedQuery(JavaResourceNode parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(type, idaa);
		return new NamedQueryImpl(parent, type, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter namedQueriesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueriesAdapter, index, JPA.NAMED_QUERY);
	}

	public static class NamedQueryAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final NamedQueryAnnotationDefinition INSTANCE = new NamedQueryAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private NamedQueryAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return NamedQueryImpl.createNamedQuery(parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
