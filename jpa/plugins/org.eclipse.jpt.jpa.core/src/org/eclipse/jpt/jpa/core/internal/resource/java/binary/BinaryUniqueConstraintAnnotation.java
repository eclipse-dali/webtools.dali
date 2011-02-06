/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

/**
 * javax.persistence.UniqueConstraint
 */
final class BinaryUniqueConstraintAnnotation
	extends BinaryAnnotation
	implements UniqueConstraintAnnotation
{
	private final Vector<String> columnNames;


	BinaryUniqueConstraintAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.columnNames = this.buildColumnNames();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.updateColumnNames();
	}

	// TODO
	private void updateColumnNames() {
		throw new UnsupportedOperationException();
	}


	// ********** UniqueConstraintAnnotation implementation **********

	// ***** column names
	public ListIterator<String> columnNames() {
		return new CloneListIterator<String>(this.columnNames);
	}

	public int columnNamesSize() {
		return this.columnNames.size();
	}

	private Vector<String> buildColumnNames() {
		Object[] jdtColumnNames = this.getJdtMemberValues(JPA.UNIQUE_CONSTRAINT__COLUMN_NAMES);
		Vector<String> result = new Vector<String>(jdtColumnNames.length);
		for (Object jdtColumnName : jdtColumnNames) {
			result.add((String) jdtColumnName);
		}
		return result;
	}

	public void addColumnName(String columnName) {
		throw new UnsupportedOperationException();
	}

	public void addColumnName(int index, String columnName) {
		throw new UnsupportedOperationException();
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public void removeColumnName(String columnName) {
		throw new UnsupportedOperationException();
	}

	public void removeColumnName(int index) {
		throw new UnsupportedOperationException();
	}

}
