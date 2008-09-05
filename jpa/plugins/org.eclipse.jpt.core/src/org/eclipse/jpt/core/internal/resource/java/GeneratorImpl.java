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
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.GeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public abstract class GeneratorImpl extends AbstractResourceAnnotation<Member> implements GeneratorAnnotation
{
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	// hold this so we can get the 'initialValue' text range
	private final DeclarationAnnotationElementAdapter<Integer> initialValueDeclarationAdapter;

	// hold this so we can get the 'allocationSize' text range
	private final DeclarationAnnotationElementAdapter<Integer> allocationSizeDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<Integer> initialValueAdapter;

	private final AnnotationElementAdapter<Integer> allocationSizeAdapter;

	private String name;
	
	private Integer initialValue;
	
	private Integer allocationSize;
		
	public GeneratorImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.nameDeclarationAdapter = this.getNameAdapter();
		this.nameAdapter = this.buildAdapter(this.nameDeclarationAdapter);
		this.initialValueDeclarationAdapter = this.getInitialValueAdapter();
		this.initialValueAdapter = this.buildIntegerAdapter(this.initialValueDeclarationAdapter);
		this.allocationSizeDeclarationAdapter = this.getAllocationSizeAdapter();
		this.allocationSizeAdapter = this.buildIntegerAdapter(this.allocationSizeDeclarationAdapter);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.initialValue = this.initialValue(astRoot);
		this.allocationSize = this.allocationSize(astRoot);
	}
	
	// ********** initialization **********
	protected AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(getMember(), daea);
	}

	protected AnnotationElementAdapter<Integer> buildIntegerAdapter(DeclarationAnnotationElementAdapter<Integer> daea) {
		return new ShortCircuitAnnotationElementAdapter<Integer>(getMember(), daea);
	}

	protected abstract DeclarationAnnotationElementAdapter<String> getNameAdapter();

	protected abstract DeclarationAnnotationElementAdapter<Integer> getInitialValueAdapter();

	protected abstract DeclarationAnnotationElementAdapter<Integer> getAllocationSizeAdapter();

	

	public Integer getAllocationSize() {
		return this.allocationSize;
	}
	
	public void setAllocationSize(Integer newAllocationSize) {
		if (attributeValueHasNotChanged(this.allocationSize, newAllocationSize)) {
			return;
		}
		Integer oldAllocationSize = this.allocationSize;
		this.allocationSize = newAllocationSize;
		this.allocationSizeAdapter.setValue(newAllocationSize);
		firePropertyChanged(ALLOCATION_SIZE_PROPERTY, oldAllocationSize, newAllocationSize);
	}

	public Integer getInitialValue() {
		return this.initialValue;
	}

	public void setInitialValue(Integer newInitialValue) {
		if (attributeValueHasNotChanged(this.initialValue, newInitialValue)) {
			return;
		}
		Integer oldInitialValue = this.initialValue;
		this.initialValue = newInitialValue;
		this.initialValueAdapter.setValue(newInitialValue);
		firePropertyChanged(INITIAL_VALUE_PROPERTY, oldInitialValue, newInitialValue);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		if (attributeValueHasNotChanged(this.name, newName)) {
			return;
		}
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}
	
	public TextRange getInitialValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.initialValueDeclarationAdapter, astRoot);
	}
	
	public TextRange getAllocationSizeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.allocationSizeDeclarationAdapter, astRoot);
	}

	// ********** java annotations -> persistence model **********
	public void update(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setAllocationSize(this.allocationSize(astRoot));
		this.setInitialValue(this.initialValue(astRoot));
	}

	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected Integer allocationSize(CompilationUnit astRoot) {
		return this.allocationSizeAdapter.getValue(astRoot);
	}
	
	protected Integer initialValue(CompilationUnit astRoot) {
		return this.initialValueAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(annotationAdapter, elementName);
	}

	protected static DeclarationAnnotationElementAdapter<Integer> buildIntegerAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(annotationAdapter, elementName, NumberIntegerExpressionConverter.instance());
	}
}
