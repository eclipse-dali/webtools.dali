/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullJoinTableAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryAssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryJoinTableAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AssociationOverrideAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

/**
 * <code>javax.persistence.AssociationOverride</code>
 */
public final class BinaryAssociationOverrideAnnotation2_0
	extends BinaryAssociationOverrideAnnotation
	implements AssociationOverrideAnnotation2_0
{
	private JoinTableAnnotation joinTable;
	private final JoinTableAnnotation nullJoinTable;

	public BinaryAssociationOverrideAnnotation2_0(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.joinTable = this.buildJoinTable();
		this.nullJoinTable = this.buildNullJoinTable();
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
		return (this.joinTable != null) ? this.joinTable : this.nullJoinTable;
	}

	public JoinTableAnnotation addJoinTable() {
		throw new UnsupportedOperationException();
	}
	
	public void removeJoinTable() {
		throw new UnsupportedOperationException();
	}

	private JoinTableAnnotation buildJoinTable() {
		IAnnotation jdtJoinTable = this.getJdtJoinTable();
		return (jdtJoinTable == null) ? null : this.buildJoinTable(jdtJoinTable);
	}

	private JoinTableAnnotation buildNullJoinTable() {
		return new NullJoinTableAnnotation(this);
	}

	private JoinTableAnnotation buildJoinTable(IAnnotation jdtJoinTable) {
		return new BinaryJoinTableAnnotation(this, jdtJoinTable);
	}

	private IAnnotation getJdtJoinTable() {
		return (IAnnotation) this.getJdtMemberValue(JPA2_0.ASSOCIATION_OVERRIDE__JOIN_TABLE);
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
