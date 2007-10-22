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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.NumberStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;

public abstract class GeneratorImpl extends AbstractAnnotationResource<Member> implements Generator
{
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	// hold this so we can get the 'initialValue' text range
	private final DeclarationAnnotationElementAdapter<String> initialValueDeclarationAdapter;

	// hold this so we can get the 'allocationSize' text range
	private final DeclarationAnnotationElementAdapter<String> allocationSizeDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final IntAnnotationElementAdapter initialValueAdapter;

	private final IntAnnotationElementAdapter allocationSizeAdapter;

	private String name;
	
	private int initialValue = -1;
	
	private int allocationSize = -1;
		
	public GeneratorImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.nameDeclarationAdapter = this.nameAdapter();
		this.nameAdapter = this.buildAdapter(this.nameDeclarationAdapter);
		this.initialValueDeclarationAdapter = this.initialValueAdapter();
		this.initialValueAdapter = this.buildIntAdapter(this.initialValueDeclarationAdapter);
		this.allocationSizeDeclarationAdapter = this.allocationSizeAdapter();
		this.allocationSizeAdapter = this.buildIntAdapter(this.allocationSizeDeclarationAdapter);
	}
	
	// ********** initialization **********
	protected AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(getMember(), daea);
	}

	protected IntAnnotationElementAdapter buildIntAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new IntAnnotationElementAdapter(this.buildAdapter(daea));
	}

	protected abstract DeclarationAnnotationAdapter annotationAdapter();

	protected abstract DeclarationAnnotationElementAdapter<String> nameAdapter();

	protected abstract DeclarationAnnotationElementAdapter<String> initialValueAdapter();

	protected abstract DeclarationAnnotationElementAdapter<String> allocationSizeAdapter();

	

	public int getAllocationSize() {
		return this.allocationSize;
	}

	public int getInitialValue() {
		return this.initialValue;
	}

	public String getName() {
		return this.name;
	}

	public void setAllocationSize(int allocationSize) {
		this.allocationSize = allocationSize;
		this.allocationSizeAdapter.setValue(allocationSize);
	}

	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
		this.initialValueAdapter.setValue(initialValue);
	}

	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}
	
	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(nameDeclarationAdapter, astRoot);
	}
	
	public ITextRange initialValueTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(initialValueDeclarationAdapter, astRoot);
	}
	
	public ITextRange allocationSizeTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(allocationSizeDeclarationAdapter, astRoot);
	}

	// ********** java annotations -> persistence model **********
	public void updateFromJava(CompilationUnit astRoot) {
		setName(this.nameAdapter.getValue(astRoot));
		setAllocationSize(this.allocationSizeAdapter.getValue(astRoot));
		setInitialValue(this.initialValueAdapter.getValue(astRoot));
	}

	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(annotationAdapter, elementName);
	}

	protected static DeclarationAnnotationElementAdapter<String> buildNumberAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, NumberStringExpressionConverter.instance());
	}
}
