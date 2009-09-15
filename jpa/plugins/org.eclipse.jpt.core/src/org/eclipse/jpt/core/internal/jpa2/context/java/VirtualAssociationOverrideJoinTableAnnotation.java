/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.internal.context.java.VirtualAssociationOverrideJoinColumnAnnotation;
import org.eclipse.jpt.core.internal.resource.java.NullJoinTableAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;

public final class VirtualAssociationOverrideJoinTableAnnotation extends NullJoinTableAnnotation
	implements JoinTableAnnotation
{
	private JoinTable joinTable; //TODO uniqueConstraints

	private final Vector<JoinColumnAnnotation> joinColumns;
	
	private final Vector<JoinColumnAnnotation> inverseJoinColumns;

	public VirtualAssociationOverrideJoinTableAnnotation(AssociationOverride2_0Annotation parent, JoinTable joinTable) {
		super(parent);
		this.joinTable = joinTable;
		this.joinColumns = this.buildJoinColumns();
		this.inverseJoinColumns = this.buildInverseJoinColumns();
	}

	@Override
	protected JoinTableAnnotation addAnnotation() {
		throw new UnsupportedOperationException();
	}
	
	private Vector<JoinColumnAnnotation> buildJoinColumns() {
		Vector<JoinColumnAnnotation> result = new Vector<JoinColumnAnnotation>(this.joinTable.joinColumnsSize());
		for (JoinColumn joinColumn : CollectionTools.iterable(this.joinTable.joinColumns())) {
			result.add(new VirtualAssociationOverrideJoinColumnAnnotation(this, joinColumn));
		}
		return result;
	}
	
	private Vector<JoinColumnAnnotation> buildInverseJoinColumns() {
		Vector<JoinColumnAnnotation> result = new Vector<JoinColumnAnnotation>(this.joinTable.inverseJoinColumnsSize());
		for (JoinColumn joinColumn : CollectionTools.iterable(this.joinTable.inverseJoinColumns())) {
			result.add(new VirtualAssociationOverrideJoinColumnAnnotation(this, joinColumn));
		}
		return result;
	}

	@Override
	public String getName() {
		return this.joinTable.getSpecifiedName();
	}

	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalog() {
		return this.joinTable.getCatalog();
	}
	
	@Override
	public void setCatalog(String catalog) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String getSchema() {
		return this.joinTable.getSchema();
	}
	
	@Override
	public void setSchema(String schema) {
		throw new UnsupportedOperationException();
	}
	
	// ***** join columns
	@Override
	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return this.joinColumns.listIterator();
	}
	
	@Override
	public JoinColumnAnnotation joinColumnAt(int index) {
		return this.joinColumns.elementAt(index);
	}
	
	@Override
	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		return this.joinColumns.indexOf(joinColumn);
	}
	
	@Override
	public int joinColumnsSize() {
		return this.joinColumns.size();
	}
	
	// ***** inverse join columns
	@Override
	public ListIterator<JoinColumnAnnotation> inverseJoinColumns() {
		return this.inverseJoinColumns.listIterator();
	}
	
	@Override
	public JoinColumnAnnotation inverseJoinColumnAt(int index) {
		return this.inverseJoinColumns.elementAt(index);
	}
	
	@Override
	public int indexOfInverseJoinColumn(JoinColumnAnnotation joinColumn) {
		return this.inverseJoinColumns.indexOf(joinColumn);
	}
	
	@Override
	public int inverseJoinColumnsSize() {
		return this.inverseJoinColumns.size();
	}


}
