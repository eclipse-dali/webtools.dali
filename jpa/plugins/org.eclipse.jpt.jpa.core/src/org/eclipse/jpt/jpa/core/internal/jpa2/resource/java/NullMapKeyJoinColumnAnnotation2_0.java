/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullBaseColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyJoinColumnAnnotation2_0;

/**
 * <code>javax.persistence.MapKeyJoinColumn</code>
 */
public final class NullMapKeyJoinColumnAnnotation2_0
	extends NullBaseColumnAnnotation<MapKeyJoinColumnAnnotation2_0>
	implements MapKeyJoinColumnAnnotation2_0
{	
	public NullMapKeyJoinColumnAnnotation2_0(JavaResourceModel parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ***** referenced column name
	public String getReferencedColumnName() {
		return null;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		throw new UnsupportedOperationException();
	}

	public TextRange getReferencedColumnNameTextRange() {
		return null;
	}
	
	public boolean referencedColumnNameTouches(int pos) {
		return false;
	}
}
