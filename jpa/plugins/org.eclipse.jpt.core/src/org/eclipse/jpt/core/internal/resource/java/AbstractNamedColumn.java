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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NumberIntegerExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public abstract class AbstractNamedColumn extends AbstractResourceAnnotation<Member> implements NamedColumnAnnotation
{
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	// hold this so we can get the 'columnDefinition' text range
	private final DeclarationAnnotationElementAdapter<String> columnDefinitionDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> columnDefinitionAdapter;

	private String name;
	private String columnDefinition;
	
	public AbstractNamedColumn(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildStringElementAdapter(this.nameElementName());
		this.nameAdapter = this.buildShortCircuitElementAdapter(this.nameDeclarationAdapter);
		this.columnDefinitionDeclarationAdapter = this.buildStringElementAdapter(this.columnDefinitionElementName());		
		this.columnDefinitionAdapter = this.buildShortCircuitElementAdapter(this.columnDefinitionDeclarationAdapter);
	}

	protected DeclarationAnnotationElementAdapter<String> buildStringElementAdapter(String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(getDeclarationAnnotationAdapter(), elementName);
	}

	protected DeclarationAnnotationElementAdapter<Boolean> buildBooleanElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(getDeclarationAnnotationAdapter(), elementName, BooleanExpressionConverter.instance());
	}

	protected DeclarationAnnotationElementAdapter<Integer> buildIntegerElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(getDeclarationAnnotationAdapter(), elementName, NumberIntegerExpressionConverter.instance());
	}

	protected AnnotationElementAdapter<String> buildShortCircuitElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(getMember(), daea);
	}
	
	protected AnnotationElementAdapter<Boolean> buildShortCircuitBooleanElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new ShortCircuitAnnotationElementAdapter<Boolean>(getMember(), daea);
	}
	
	protected AnnotationElementAdapter<Integer> buildShortCircuitIntegerElementAdapter(DeclarationAnnotationElementAdapter<Integer> daea) {
		return new ShortCircuitAnnotationElementAdapter<Integer>(getMember(), daea);
	}

	protected AnnotationElementAdapter<String> buildShortCircuitStringElementAdapter(String elementName) {
		return this.buildShortCircuitElementAdapter(this.buildStringElementAdapter(elementName));
	}
	protected abstract String nameElementName();

	protected abstract String columnDefinitionElementName();

	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.columnDefinition = this.columnDefinition(astRoot);
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		NamedColumnAnnotation oldColumn = (NamedColumnAnnotation) oldAnnotation;
		setName(oldColumn.getName());
		setColumnDefinition(oldColumn.getColumnDefinition());
	}

	//************* NamedColumn implementation **************
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public String getColumnDefinition() {
		return this.columnDefinition;
	}
	
	public void setColumnDefinition(String newColumnDefinition) {
		String oldColumnDefinition = this.columnDefinition;
		this.columnDefinition = newColumnDefinition;
		this.columnDefinitionAdapter.setValue(newColumnDefinition);
		firePropertyChanged(COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, newColumnDefinition);
	}	

	public TextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public TextRange columnDefinitionTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.columnDefinitionDeclarationAdapter, astRoot);
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setColumnDefinition(this.columnDefinition(astRoot));
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected String columnDefinition(CompilationUnit astRoot) {
		return this.columnDefinitionAdapter.getValue(astRoot);
	}

}
