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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;


public class NullAssociationOverride extends AbstractResource implements AssociationOverride, Annotation
{	
	private String name;

	public NullAssociationOverride(JavaResource parent, String name) {
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
		return AssociationOverride.ANNOTATION_NAME;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		if (name != null) {
			createAssociationOverrideResource().setName(name);
		}		
	}

	public ListIterator<JoinColumn> joinColumns() {
		return EmptyListIterator.instance();
	}
	
	public JoinColumn joinColumnAt(int index) {
		return null;
	}
	
	public int indexOfJoinColumn(JoinColumn joinColumn) {
		throw new UnsupportedOperationException();
	}
	
	public int joinColumnsSize() {
		return 0;
	}
	
	public JoinColumn addJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
	
	public void removeJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
	
	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public JavaPersistentResource parent() {
		return (JavaPersistentResource) super.parent();
	}
	
	protected AssociationOverride createAssociationOverrideResource() {
		return (AssociationOverride) parent().addAnnotation(getAnnotationName());
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public ITextRange textRange(CompilationUnit astRoot) {
		return null;
	}

}
