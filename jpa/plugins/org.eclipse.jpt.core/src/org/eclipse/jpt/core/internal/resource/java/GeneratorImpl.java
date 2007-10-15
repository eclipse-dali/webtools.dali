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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.NumberStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;

public abstract class GeneratorImpl extends AbstractAnnotationResource<Member> implements Generator
{
	private final AnnotationElementAdapter<String> nameAdapter;

	private final IntAnnotationElementAdapter initialValueAdapter;

	private final IntAnnotationElementAdapter allocationSizeAdapter;

	private String name;
	
	private int initialValue = -1;
	
	private int allocationSize = -1;
		
	public GeneratorImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.nameAdapter = this.buildAdapter(this.nameAdapter());
		this.initialValueAdapter = this.buildIntAdapter(this.initialValueAdapter());
		this.allocationSizeAdapter = this.buildIntAdapter(this.allocationSizeAdapter());
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
