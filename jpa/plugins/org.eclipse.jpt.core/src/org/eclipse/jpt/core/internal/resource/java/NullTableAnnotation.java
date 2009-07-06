/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TableAnnotation;

/**
 * javax.persistence.Table
 */
public final class NullTableAnnotation
	extends NullBaseTableAnnotation
	implements TableAnnotation
{
	protected NullTableAnnotation(JavaResourcePersistentType parent) {
		super(parent);
	}

	@Override
	protected TableAnnotation buildAnnotation() {
		return (TableAnnotation) addSupportingAnnotation();
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

}
