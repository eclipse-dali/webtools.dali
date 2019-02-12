/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsIdAnnotation2_0;

/**
 * <code>javax.persistence.MapsId</code>
 */
public final class NullMapsIdAnnotation2_0
	extends NullAnnotation<MapsIdAnnotation2_0>
	implements MapsIdAnnotation2_0
{
	protected NullMapsIdAnnotation2_0(JavaResourceAnnotatedElement parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	// ***** value
	public String getValue() {
		return null;
	}
	
	public void setValue(String newValue) {
		if (newValue != null) {
			this.addAnnotation().setValue(newValue);
		}
	}
	
	public TextRange getValueTextRange() {
		return null;
	}
	
	public boolean valueTouches(int pos) {
		return false;
	}
}
