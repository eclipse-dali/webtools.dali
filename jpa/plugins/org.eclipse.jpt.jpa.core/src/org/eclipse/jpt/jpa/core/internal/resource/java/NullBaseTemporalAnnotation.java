/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.BaseTemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TemporalType;

/**
 * <code><ul>
 * <li>javax.persistence.Temporal
 * <li>javax.persistence.MapKeyTemporal
 * </ul></code>
 */
public abstract class NullBaseTemporalAnnotation<A extends BaseTemporalAnnotation>
	extends NullAnnotation<A>
	implements BaseTemporalAnnotation
{
	protected NullBaseTemporalAnnotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	// ***** value
	public TemporalType getValue() {
		return null;
	}

	public void setValue(TemporalType value) {
		if (value != null) {
			this.addAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange() {
		return null;
	}
}
