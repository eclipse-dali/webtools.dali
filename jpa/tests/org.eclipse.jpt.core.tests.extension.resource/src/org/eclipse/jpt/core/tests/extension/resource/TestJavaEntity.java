/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaEntity;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheable2_0;

public class TestJavaEntity extends AbstractJavaEntity
{
	protected TestJavaEntity(JavaPersistentType parent) {
		super(parent);
	}

	public JavaCacheable2_0 getCacheable() {
		return null;
	}
	
	public boolean calculateDefaultCacheable() {
		return false;
	}

	
}
