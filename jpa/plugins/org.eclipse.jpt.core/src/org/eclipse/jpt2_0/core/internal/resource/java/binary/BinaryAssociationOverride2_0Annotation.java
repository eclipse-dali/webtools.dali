/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt2_0.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.resource.java.NullJoinTableAnnotation;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryAssociationOverrideAnnotation;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryJoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember.AnnotationInitializer;
import org.eclipse.jpt2_0.core.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt2_0.core.resource.java.JPA;

/**
 *  BinarySequenceGenerator2_0Annotation
 */
public final class BinaryAssociationOverride2_0Annotation
	extends BinaryAssociationOverrideAnnotation
	implements AssociationOverride2_0Annotation
{
	private JoinTableAnnotation joinTable;

	public BinaryAssociationOverride2_0Annotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.joinTable = this.buildJoinTable();
	}

	@Override
	public void update() {
		super.update();
		this.updateJoinTable();
	}


	// ********** AssociationOverride2_0Annotation implementation **********

	// ***** joinTable
	public JoinTableAnnotation getJoinTable() {
		return this.joinTable;
	}

	public JoinTableAnnotation getNonNullJoinTable() {
		return (this.joinTable != null) ? this.joinTable : new NullJoinTableAnnotation(this);
	}

	public JoinTableAnnotation addJoinTable() {
		throw new UnsupportedOperationException();
	}
	
	public JoinColumnAnnotation addJoinTable(AnnotationInitializer initializer) {
		throw new UnsupportedOperationException();
	}

	public void removeJoinTable() {
		throw new UnsupportedOperationException();
	}

	private JoinTableAnnotation buildJoinTable() {
		IAnnotation jdtJoinTable = this.getJdtJoinTable();
		return (jdtJoinTable == null) ? null : this.buildJoinTable(jdtJoinTable);
	}

	private JoinTableAnnotation buildJoinTable(IAnnotation jdtJoinTable) {
		return new BinaryJoinTableAnnotation(this, jdtJoinTable);
	}

	private IAnnotation getJdtJoinTable() {
		return (IAnnotation) this.getJdtMemberValue(JPA.ASSOCIATION_OVERRIDE__JOIN_TABLE);
	}

	private void setJoinTable(JoinTableAnnotation column) {
		JoinTableAnnotation old = this.joinTable;
		this.joinTable = column;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, column);
	}

	// TODO
	private void updateJoinTable() {
		throw new UnsupportedOperationException();
//		IAnnotation jdtJoinTable = this.getJdtJoinTable();
//		if (jdtJoinTable == null) {
//			this.setJoinTable(null);
//		} else {
//			if (this.column == null) {
//				this.setJoinTable(this.buildJoinTable(jdtJoinTable));
//			} else {
//				this.column.update(jdtJoinTable);
//			}
//		}
	}

}