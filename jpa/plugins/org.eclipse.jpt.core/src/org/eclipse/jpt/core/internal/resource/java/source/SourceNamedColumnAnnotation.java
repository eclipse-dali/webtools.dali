/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.Column
 * javax.persistence.JoinColumn
 * javax.persistence.DiscriminatorColumn
 * javax.persistence.PrimaryKeyJoinColumn.
 */
abstract class SourceNamedColumnAnnotation
	extends SourceAnnotation<Member>
	implements NamedColumnAnnotation
{
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;

	private final DeclarationAnnotationElementAdapter<String> columnDefinitionDeclarationAdapter;
	private final AnnotationElementAdapter<String> columnDefinitionAdapter;
	private String columnDefinition;


	SourceNamedColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}
	
	SourceNamedColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildStringElementAdapter(this.getNameElementName());
		this.nameAdapter = this.buildShortCircuitElementAdapter(this.nameDeclarationAdapter);
		this.columnDefinitionDeclarationAdapter = this.buildStringElementAdapter(this.getColumnDefinitionElementName());
		this.columnDefinitionAdapter = this.buildShortCircuitElementAdapter(this.columnDefinitionDeclarationAdapter);
	}

	DeclarationAnnotationElementAdapter<String> buildStringElementAdapter(String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, elementName);
	}

	DeclarationAnnotationElementAdapter<Boolean> buildBooleanElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(this.daa, elementName, BooleanExpressionConverter.instance());
	}

	DeclarationAnnotationElementAdapter<Integer> buildIntegerElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(this.daa, elementName, NumberIntegerExpressionConverter.instance());
	}

	AnnotationElementAdapter<String> buildShortCircuitElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.member, daea);
	}

	AnnotationElementAdapter<Boolean> buildShortCircuitBooleanElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new ShortCircuitAnnotationElementAdapter<Boolean>(this.member, daea);
	}

	AnnotationElementAdapter<Integer> buildShortCircuitIntegerElementAdapter(DeclarationAnnotationElementAdapter<Integer> daea) {
		return new ShortCircuitAnnotationElementAdapter<Integer>(this.member, daea);
	}

	AnnotationElementAdapter<String> buildShortCircuitStringElementAdapter(String elementName) {
		return this.buildShortCircuitElementAdapter(this.buildStringElementAdapter(elementName));
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.columnDefinition = this.buildColumnDefinition(astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setName(this.buildName(astRoot));
		this.setColumnDefinition(this.buildColumnDefinition(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** NamedColumn implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasNotChanged(this.name, name)) {
			return;
		}
		String old = this.name;
		this.name = name;
		this.nameAdapter.setValue(name);
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}

	abstract String getNameElementName();

	// ***** column definition
	public String getColumnDefinition() {
		return this.columnDefinition;
	}

	public void setColumnDefinition(String columnDefinition) {
		if (this.attributeValueHasNotChanged(this.columnDefinition, columnDefinition)) {
			return;
		}
		String old = this.columnDefinition;
		this.columnDefinition = columnDefinition;
		this.columnDefinitionAdapter.setValue(columnDefinition);
		this.firePropertyChanged(COLUMN_DEFINITION_PROPERTY, old, columnDefinition);
	}

	private String buildColumnDefinition(CompilationUnit astRoot) {
		return this.columnDefinitionAdapter.getValue(astRoot);
	}

	public TextRange getColumnDefinitionTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.columnDefinitionDeclarationAdapter, astRoot);
	}

	abstract String getColumnDefinitionElementName();


	// ********** NestableAnnotation implementation **********

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		NamedColumnAnnotation oldColumn = (NamedColumnAnnotation) oldAnnotation;
		this.setName(oldColumn.getName());
		this.setColumnDefinition(oldColumn.getColumnDefinition());
	}

}
