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
import org.eclipse.jpt.core.internal.jdtutility.BooleanStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.NumberStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;

public abstract class AbstractNamedColumn extends AbstractAnnotationResource<Member> implements NamedColumn
{
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> columnDefinitionAdapter;

	private String name;
	private String columnDefinition;
	
	public AbstractNamedColumn(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildStringElementAdapter(this.nameElementName());
		this.nameAdapter = this.buildShortCircuitElementAdapter(this.nameDeclarationAdapter);
		this.columnDefinitionAdapter = this.buildShortCircuitStringElementAdapter(this.columnDefinitionElementName());
	}

	protected DeclarationAnnotationElementAdapter<String> buildStringElementAdapter(String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(getDeclarationAnnotationAdapter(), elementName);
	}

	protected DeclarationAnnotationElementAdapter<String> buildBooleanElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(getDeclarationAnnotationAdapter(), elementName, BooleanStringExpressionConverter.instance());
	}

	protected DeclarationAnnotationElementAdapter<String> buildNumberElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(getDeclarationAnnotationAdapter(), elementName, NumberStringExpressionConverter.instance());
	}

	protected AnnotationElementAdapter<String> buildShortCircuitElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(getMember(), daea);
	}

	protected AnnotationElementAdapter<String> buildShortCircuitStringElementAdapter(String elementName) {
		return this.buildShortCircuitElementAdapter(this.buildStringElementAdapter(elementName));
	}

	protected AnnotationElementAdapter<String> buildShortCircuitBooleanElementAdapter(String elementName) {
		return this.buildShortCircuitElementAdapter(this.buildBooleanElementAdapter(elementName));
	}

	protected IntAnnotationElementAdapter buildShortCircuitIntElementAdapter(String elementName) {
		return new IntAnnotationElementAdapter(this.buildShortCircuitElementAdapter(this.buildNumberElementAdapter(elementName)));
	}

	protected abstract String nameElementName();

	protected abstract String columnDefinitionElementName();

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		NamedColumn oldColumn = (NamedColumn) oldAnnotation;
		setName(oldColumn.getName());
		setColumnDefinition(oldColumn.getColumnDefinition());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}
	
	public String getColumnDefinition() {
		return this.columnDefinition;
	}
	
	public void setColumnDefinition(String columnDefinition) {
		this.columnDefinition = columnDefinition;
		this.columnDefinitionAdapter.setValue(columnDefinition);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		setName(this.nameAdapter.getValue(astRoot));
		setColumnDefinition(this.columnDefinitionAdapter.getValue(astRoot));
	}

}
