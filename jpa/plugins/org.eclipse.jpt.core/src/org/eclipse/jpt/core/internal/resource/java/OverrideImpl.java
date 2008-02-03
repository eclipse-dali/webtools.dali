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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;

public abstract class OverrideImpl 
	extends AbstractAnnotationResource<Member>  
	implements OverrideResource
{		
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;
	
	private String name;
		
	
	protected OverrideImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.ATTRIBUTE_OVERRIDE__NAME, false); // false = do not remove annotation when empty
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(),this.nameDeclarationAdapter);
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
	}
	
	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
			
	public void moveAnnotation(int newIndex) {
		getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		OverrideResource oldOverride = (OverrideResource) oldAnnotation;
		setName(oldOverride.getName());
	}
	
	//************ AttriubteOverride implementation ****************
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(OverrideResource.NAME_PROPERTY, oldName, newName);
	}

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
}
