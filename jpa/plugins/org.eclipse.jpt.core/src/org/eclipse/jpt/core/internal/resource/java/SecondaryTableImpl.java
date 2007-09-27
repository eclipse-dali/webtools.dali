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

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NonIndexedMemberIndexedAnnotationAdapter;

public class SecondaryTableImpl extends AbstractTableResource implements SecondaryTable
{	
	protected SecondaryTableImpl(JpaPlatform jpaPlatform, Member member, DeclarationAnnotationAdapter daa, IndexedAnnotationAdapter annotationAdapter) {
		super(jpaPlatform, member, daa, annotationAdapter);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> catalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__CATALOG);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> schemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__SCHEMA);
	}

	@Override
	public IndexedAnnotationAdapter getAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
	
	public String getAnnotationName() {
		return JPA.SECONDARY_TABLE;
	}
		
	public Annotation annotation(CompilationUnit astRoot) {
		return getAnnotationAdapter().getAnnotation(astRoot);
	}
	
	public void moveAnnotation(int newIndex) {
		getAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	public void initializeFrom(SingularAnnotation oldAnnotation) {
		setName(((SecondaryTable) oldAnnotation).getName());
		setCatalog(((SecondaryTable) oldAnnotation).getCatalog());
		setSchema(((SecondaryTable) oldAnnotation).getSchema());
	}
	
	// ********** static methods **********
	static SecondaryTable createJavaSecondaryTable(JpaPlatform jpaPlatform, Member member) {
		return new SecondaryTableImpl(jpaPlatform, member, DECLARATION_ANNOTATION_ADAPTER, new NonIndexedMemberIndexedAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static SecondaryTable createNestedJavaSecondaryTable(JpaPlatform jpaPlatform, Member member, int index, DeclarationAnnotationAdapter secondaryTablesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, secondaryTablesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new SecondaryTableImpl(jpaPlatform, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter secondaryTablesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(secondaryTablesAdapter, index, JPA.SECONDARY_TABLE);
	}
}
