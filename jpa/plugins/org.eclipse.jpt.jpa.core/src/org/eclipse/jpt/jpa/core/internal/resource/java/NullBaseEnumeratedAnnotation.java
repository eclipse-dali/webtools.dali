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
import org.eclipse.jpt.jpa.core.resource.java.BaseEnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EnumType;

/**
 * <code>javax.persistence.Enumerated</code>
 */
public abstract class NullBaseEnumeratedAnnotation<A extends BaseEnumeratedAnnotation>
	extends NullAnnotation<A>
	implements BaseEnumeratedAnnotation
{
	protected NullBaseEnumeratedAnnotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	// ***** value
	public EnumType getValue() {
		return null;
	}

	public void setValue(EnumType value) {
		if (value != null) {
			this.addAnnotation().setValue(value);
		}		
	}

	public TextRange getValueTextRange() {
		return null;
	}
}
