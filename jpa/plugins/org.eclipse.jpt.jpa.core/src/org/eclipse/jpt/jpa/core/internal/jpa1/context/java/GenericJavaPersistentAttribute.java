/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentAttribute;

/**
 * Generic Java persistent attribute
 */
public class GenericJavaPersistentAttribute
	extends AbstractJavaPersistentAttribute
{

	public GenericJavaPersistentAttribute(PersistentType parent, JavaResourceField resourceField) {
		super(parent, resourceField);
	}

	public GenericJavaPersistentAttribute(PersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		super(parent, resourceGetter, resourceSetter);
	}

	public GenericJavaPersistentAttribute(PersistentType parent, Accessor accessor) {
		super(parent, accessor);
	}

	// ********** validation **********

	@Override
	protected JpaValidator buildAttributeValidator() {
		return getAccessor().buildAttributeValidator(this);
	}
}
