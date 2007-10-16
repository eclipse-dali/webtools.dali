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

import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class NamedQueryImpl extends AbstractNamedQuery
	implements NestableNamedQuery
{
	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.NAMED_QUERY);

	protected NamedQueryImpl(JavaResource parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
	}

	public String getAnnotationName() {
		return JPA.NAMED_QUERY;
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
	static NamedQueryImpl createNamedQuery(JavaResource parent, Type type) {
		return new NamedQueryImpl(parent, type, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	static NamedQueryImpl createNestedNamedQuery(JavaResource parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(type, idaa);
		return new NamedQueryImpl(parent, type, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter namedQueriesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueriesAdapter, index, JPA.NAMED_QUERY);
	}

}
