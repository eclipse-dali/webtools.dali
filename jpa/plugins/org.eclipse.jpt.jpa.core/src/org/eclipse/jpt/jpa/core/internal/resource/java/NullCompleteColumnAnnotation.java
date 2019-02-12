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
import org.eclipse.jpt.jpa.core.resource.java.CompleteColumnAnnotation;

/**
 * <code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.MapKeyColumn
 * </ul></code>
 */
public abstract class NullCompleteColumnAnnotation<A extends CompleteColumnAnnotation>
	extends NullBaseColumnAnnotation<A>
	implements CompleteColumnAnnotation
{
	public NullCompleteColumnAnnotation(JavaResourceModel parent) {
		super(parent);
	}

	// ***** length
	public Integer getLength() {
		return null;
	}

	public void setLength(Integer length) {
		if (length != null) {
			this.addAnnotation().setLength(length);
		}
	}

	public TextRange getLengthTextRange() {
		return null;
	}

	// ***** scale
	public Integer getScale() {
		return null;
	}

	public void setScale(Integer scale) {
		if (scale != null) {
			this.addAnnotation().setScale(scale);
		}
	}

	public TextRange getScaleTextRange() {
		return null;
	}

	// ***** precision
	public Integer getPrecision() {
		return null;
	}

	public void setPrecision(Integer precision) {
		if (precision != null) {
			this.addAnnotation().setPrecision(precision);
		}
	}

	public TextRange getPrecisionTextRange() {
		return null;
	}
}
