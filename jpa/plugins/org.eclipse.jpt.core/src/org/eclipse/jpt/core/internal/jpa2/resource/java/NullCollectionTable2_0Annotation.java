/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * <code>javax.persistence.CollectionTable</code>
 */
public final class NullCollectionTable2_0Annotation
	extends NullBaseTableAnnotation<CollectionTable2_0Annotation>
	implements CollectionTable2_0Annotation
{
	public NullCollectionTable2_0Annotation(JavaResourceNode parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
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
		// the CollectionTable annotation is missing, add both it and a join column at the same time
		return this.addAnnotation().addJoinColumn(index);
	}
	
	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}
	
	public void removeJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
}
