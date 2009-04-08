/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jpt.core.internal.resource.java.NullColumnAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.WriteTransformerAnnotation;

/**
 * javax.persistence.Column
 */
public class NullWriteTransformerColumnAnnotation
	extends NullColumnAnnotation
{	
	public NullWriteTransformerColumnAnnotation(WriteTransformerAnnotation parent) {
		super(parent);
	}

	private WriteTransformerAnnotation getWriteTransformerAnnotation() {
		return (WriteTransformerAnnotation) this.parent;
	}
	
	@Override
	protected ColumnAnnotation buildAnnotation() {
		return this.getWriteTransformerAnnotation().addColumn();
	}

}
