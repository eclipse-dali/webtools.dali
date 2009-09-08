/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * javax.persistence.AssociationOverride
 */
public abstract class VirtualAssociationOverrideAnnotation
	extends VirtualOverrideAnnotation
	implements AssociationOverrideAnnotation
{
	protected JoiningStrategy joiningStrategy;

	private final Vector<JoinColumnAnnotation> joinColumns;
	
	protected VirtualAssociationOverrideAnnotation(JavaResourcePersistentMember parent, String name, JoiningStrategy joiningStrategy) {
		super(parent, name);
		this.joiningStrategy = joiningStrategy;
		this.joinColumns = this.buildJoinColumns();
	}
	
	protected Vector<JoinColumnAnnotation> buildJoinColumns() {
		if (this.joiningStrategy instanceof JoinColumnJoiningStrategy) {
			Vector<JoinColumnAnnotation> result = new Vector<JoinColumnAnnotation>(((JoinColumnJoiningStrategy) this.joiningStrategy).joinColumnsSize());
			for (JoinColumn joinColumn : CollectionTools.iterable(((JoinColumnJoiningStrategy) this.joiningStrategy).joinColumns())) {
				result.add(new VirtualAssociationOverrideJoinColumnAnnotation(this, joinColumn));
			}
			return result;
		}
		return new Vector<JoinColumnAnnotation>();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected AssociationOverrideAnnotation addAnnotation() {
		return (AssociationOverrideAnnotation) super.addAnnotation();
	}

	// ***** join columns
	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return this.joinColumns.listIterator();
	}
	
	public JoinColumnAnnotation joinColumnAt(int index) {
		return this.joinColumns.elementAt(index);
	}
	
	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		return this.joinColumns.indexOf(joinColumn);
	}
	
	public int joinColumnsSize() {
		return this.joinColumns.size();
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
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		//Rebuilt every time so no need to implement update()
	}
}
