/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullBaseTableAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CollectionTableAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;

/**
 * <code>javax.persistence.CollectionTable</code>
 */
public final class NullCollectionTableAnnotation2_0
	extends NullBaseTableAnnotation<CollectionTableAnnotation2_0>
	implements CollectionTableAnnotation2_0
{
	public NullCollectionTableAnnotation2_0(JavaResourceModel parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	// ***** join columns
	public ListIterable<JoinColumnAnnotation> getJoinColumns() {
		return EmptyListIterable.instance();
	}
	
	public int getJoinColumnsSize() {
		return 0;
	}
	
	public JoinColumnAnnotation joinColumnAt(int index) {
		return null;
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
