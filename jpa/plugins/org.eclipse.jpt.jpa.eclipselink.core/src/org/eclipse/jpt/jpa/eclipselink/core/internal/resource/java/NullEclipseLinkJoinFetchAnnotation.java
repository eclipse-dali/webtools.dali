/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType;

/**
 * <code>org.eclipse.persistence.annotations.JoinFetch</code>
 */
public final class NullEclipseLinkJoinFetchAnnotation
	extends NullAnnotation<JoinFetchAnnotation>
	implements JoinFetchAnnotation
{
	protected NullEclipseLinkJoinFetchAnnotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ***** value
	public JoinFetchType getValue() {
		return null;
	}

	public void setValue(JoinFetchType value) {
		if (value != null) {
			this.addAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange() {
		return null;
	}
}
