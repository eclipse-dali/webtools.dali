/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.BaseColumnAnnotation;

/**
 * <code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.MapKeyColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * </ul></code>
 */
public abstract class NullBaseColumnAnnotation<A extends BaseColumnAnnotation>
	extends NullNamedColumnAnnotation<A>
	implements BaseColumnAnnotation
{
	protected NullBaseColumnAnnotation(JavaResourceModel parent) {
		super(parent);
	}

	// ***** table
	public String getTable() {
		return null;
	}

	public void setTable(String table) {
		if (table != null) {
			this.addAnnotation().setTable(table);
		}
	}

	public TextRange getTableTextRange() {
		return null;
	}

	public boolean tableTouches(int pos) {
		return false;
	}

	// ***** unique
	public Boolean getUnique() {
		return null;
	}

	public void setUnique(Boolean unique) {
		if (unique != null) {
			this.addAnnotation().setUnique(unique);
		}
	}

	public TextRange getUniqueTextRange() {
		return null;
	}

	// ***** updatable
	public Boolean getUpdatable() {
		return null;
	}

	public void setUpdatable(Boolean updatable) {
		if (updatable != null) {
			this.addAnnotation().setUpdatable(updatable);
		}
	}

	public TextRange getUpdatableTextRange() {
		return null;
	}

	// ***** insertable
	public Boolean getInsertable() {
		return null;
	}

	public void setInsertable(Boolean insertable) {
		if (insertable != null) {
			this.addAnnotation().setInsertable(insertable);
		}
	}

	public TextRange getInsertableTextRange() {
		return null;
	}

	// ***** nullable
	public Boolean getNullable() {
		return null;
	}

	public void setNullable(Boolean nullable) {
		if (nullable != null) {
			this.addAnnotation().setNullable(nullable);
		}
	}

	public TextRange getNullableTextRange() {
		return null;
	}
}
