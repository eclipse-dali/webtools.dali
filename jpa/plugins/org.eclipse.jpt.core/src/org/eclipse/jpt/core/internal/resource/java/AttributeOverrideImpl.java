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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;

public class AttributeOverrideImpl 
	extends AbstractAnnotationResource<Member>  
	implements NestableAttributeOverride
{	
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final MemberAnnotationAdapter columnAdapter;
	
	private String name;
	
	private ColumnImpl column;
	
	
	protected AttributeOverrideImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.ATTRIBUTE_OVERRIDE__NAME, false); // false = do not remove annotation when empty
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(),this.nameDeclarationAdapter);
		this.columnAdapter = new MemberAnnotationAdapter(getMember(), ColumnImpl.buildAttributeOverrideAnnotationAdapter(getDeclarationAnnotationAdapter()));
	}

	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
	
	public String getAnnotationName() {
		return JPA.ATTRIBUTE_OVERRIDE;
	}
			
	public void moveAnnotation(int newIndex) {
		getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		setName(((AttributeOverride) oldAnnotation).getName());
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}

	public Column getColumn() {
		return this.column;
	}
	
	public Column addColumn() {
		ColumnImpl column = ColumnImpl.createAttributeOverrideColumn(this, getMember(), getDeclarationAnnotationAdapter());
		column.newAnnotation();
		setColumn(column);
		return column;
	}
	
	public void removeColumn() {
		this.column.removeAnnotation();
		setColumn(null);
	}
	
	private void setColumn(ColumnImpl column) {
		this.column = column;
		//change notification
	}
	public void updateFromJava(CompilationUnit astRoot) {
		setName(this.nameAdapter.getValue(astRoot));
		if (this.columnAdapter.getAnnotation(astRoot) == null) {
			setColumn(null);
		}
		else {
			ColumnImpl column = ColumnImpl.createAttributeOverrideColumn(this, getMember(), getDeclarationAnnotationAdapter());
			setColumn(column);
			column.updateFromJava(astRoot);
		}
	}
	

	// ********** static methods **********
	static AttributeOverrideImpl createAttributeOverride(JavaResource parent, Member member) {
		return new AttributeOverrideImpl(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static AttributeOverrideImpl createNestedAttributeOverride(JavaResource parent, Member member, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new AttributeOverrideImpl(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(attributeOverridesAdapter, index, JPA.ATTRIBUTE_OVERRIDE);
	}
}
