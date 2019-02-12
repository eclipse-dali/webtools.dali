/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullNamedColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumnAnnotation2_0;

/**
 * <code>javax.persistence.OrderColumn</code>
 */
public final class NullOrderColumnAnnotation2_0
	extends NullNamedColumnAnnotation<OrderColumnAnnotation2_0>
	implements OrderColumnAnnotation2_0
{	
	public NullOrderColumnAnnotation2_0(JavaResourceAnnotatedElement parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
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
