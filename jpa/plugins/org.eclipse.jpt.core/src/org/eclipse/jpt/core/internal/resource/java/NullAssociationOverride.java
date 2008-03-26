/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;


public class NullAssociationOverride extends AbstractJavaResourceNode implements AssociationOverrideAnnotation, Annotation
{	
	private String name;

	public NullAssociationOverride(JavaResourceNode parent, String name) {
		super(parent);
		this.name = name;
	}

	public void initialize(CompilationUnit astRoot) {
		//null, nothing to initialize
	}

	public org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot) {
		return null;
	}
	
	public void newAnnotation() {
		throw new UnsupportedOperationException();
	}
	
	public void removeAnnotation() {
		throw new UnsupportedOperationException();
	}

	public String getAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		if (name != null) {
			createAssociationOverrideResource().setName(name);
		}		
	}

	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return EmptyListIterator.instance();
	}
	
	public JoinColumnAnnotation joinColumnAt(int index) {
		return null;
	}
	
	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		throw new UnsupportedOperationException();
	}
	
	public int joinColumnsSize() {
		return 0;
	}
	
	public JoinColumnAnnotation addJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
	
	public void removeJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
	
	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}
	
	protected AssociationOverrideAnnotation createAssociationOverrideResource() {
		return (AssociationOverrideAnnotation) getParent().addAnnotation(getAnnotationName());
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
	public TextRange nameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange textRange(CompilationUnit astRoot) {
		return null;
	}

}
