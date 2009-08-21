/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;

/**
 * Context persistence.xml class reference
 */
public class GenericClassRef
	extends AbstractClassRef
{
	/**
	 * Construct an "specified" class ref; i.e. a class ref with
	 * an explicit entry in the persistence.xml.
	 */
	public GenericClassRef(PersistenceUnit parent, XmlJavaClassRef classRef) {
		super(parent, classRef);
	}

	/**
	 * Construct an "implied" class ref; i.e. a class ref without
	 * an explicit entry in the persistence.xml.
	 */
	public GenericClassRef(PersistenceUnit parent, String className) {
		super(parent, className);
	}

}
