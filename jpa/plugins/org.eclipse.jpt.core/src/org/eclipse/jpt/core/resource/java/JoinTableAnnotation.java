/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.ListIterator;

public interface JoinTableAnnotation extends TableAnnotation
{
	String ANNOTATION_NAME = JPA.JOIN_TABLE;

	ListIterator<JoinColumnAnnotation> joinColumns();
	
	JoinColumnAnnotation joinColumnAt(int index);
	
	int indexOfJoinColumn(JoinColumnAnnotation joinColumn);
	
	int joinColumnsSize();

	JoinColumnAnnotation addJoinColumn(int index);
	
	void removeJoinColumn(int index);
	
	void moveJoinColumn(int targetIndex, int sourceIndex);
		String JOIN_COLUMNS_LIST = "joinColumnsList";
	
	ListIterator<JoinColumnAnnotation> inverseJoinColumns();
	
	JoinColumnAnnotation inverseJoinColumnAt(int index);
	
	int indexOfInverseJoinColumn(JoinColumnAnnotation joinColumn);
	
	int inverseJoinColumnsSize();

	JoinColumnAnnotation addInverseJoinColumn(int index);
	
	void removeInverseJoinColumn(int index);
	
	void moveInverseJoinColumn(int targetIndex, int sourceIndex);
		String INVERSE_JOIN_COLUMNS_LIST = "inverseJoinColumnsList";

}
