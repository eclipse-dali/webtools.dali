/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java;

import java.util.ListIterator;

import org.eclipse.jpt.core.internal.resource.java.NullBaseTableAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember.AnnotationInitializer;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * javax.persistence.CollectionTable
 */
public class NullCollectionTable2_0Annotation
	extends NullBaseTableAnnotation
	implements CollectionTable2_0Annotation
{
	public NullCollectionTable2_0Annotation(JavaResourceNode parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	protected CollectionTable2_0Annotation addAnnotation() {
		return (CollectionTable2_0Annotation) super.addAnnotation();
	}
	
	@Override
	protected JoinColumnAnnotation addAnnotation(AnnotationInitializer initializer) {
		return (JoinColumnAnnotation) super.addAnnotation(initializer);
	}
	
	// ***** join columns
	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return EmptyListIterator.instance();
	}
	
	public int joinColumnsSize() {
		return 0;
	}
	
	public JoinColumnAnnotation joinColumnAt(int index) {
		return null;
	}
	
	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		throw new UnsupportedOperationException();
	}
	
	public JoinColumnAnnotation addJoinColumn(int index) {
		// the JoinTable annotation is missing, add both it and a join column at the same time
		return addAnnotation(buildJoinColumnInitializer());
	}
	
	protected AnnotationInitializer buildJoinColumnInitializer() {
		return JOIN_COLUMN_INITIALIZER;
	}
	
	protected static final AnnotationInitializer JOIN_COLUMN_INITIALIZER =
			new AnnotationInitializer() {
				public Annotation initializeAnnotation(Annotation supportingAnnotation) {
					return ((CollectionTable2_0Annotation) supportingAnnotation).initializeJoinColumns();
				}
			};
	
	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}
	
	public void removeJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
	
	public JoinColumnAnnotation initializeJoinColumns() {
		throw new UnsupportedOperationException();
	}
}