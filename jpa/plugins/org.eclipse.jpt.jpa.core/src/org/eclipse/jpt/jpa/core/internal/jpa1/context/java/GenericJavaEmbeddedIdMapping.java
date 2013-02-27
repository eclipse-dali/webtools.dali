/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaEmbeddedIdMapping;

/**
 * Java embedded ID mapping
 */
public class GenericJavaEmbeddedIdMapping
	extends AbstractJavaEmbeddedIdMapping
{

	public GenericJavaEmbeddedIdMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
	}
}
