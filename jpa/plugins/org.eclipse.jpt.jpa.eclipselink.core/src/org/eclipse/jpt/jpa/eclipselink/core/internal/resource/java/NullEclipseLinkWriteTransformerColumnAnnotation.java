/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jpt.jpa.core.internal.resource.java.NullColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.WriteTransformerAnnotation;

/**
 * <code>javax.persistence.Column</code>
 */
public final class NullEclipseLinkWriteTransformerColumnAnnotation
	extends NullColumnAnnotation
{	
	public NullEclipseLinkWriteTransformerColumnAnnotation(WriteTransformerAnnotation parent) {
		super(parent);
	}

	private WriteTransformerAnnotation getWriteTransformerAnnotation() {
		return (WriteTransformerAnnotation) this.parent;
	}
	
	@Override
	protected ColumnAnnotation addAnnotation() {
		return this.getWriteTransformerAnnotation().addColumn();
	}
}
