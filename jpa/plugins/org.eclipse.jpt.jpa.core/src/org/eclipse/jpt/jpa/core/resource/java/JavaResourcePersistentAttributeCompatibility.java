/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;

//TODO Compatibility class which should be removed in Juno release
public class JavaResourcePersistentAttributeCompatibility implements
		JavaResourcePersistentAttribute {

	private JavaResourceAttribute javaResourceAttribute;
	
	public JavaResourcePersistentAttributeCompatibility (JavaResourceAttribute javaResourceAttribute){
		this.javaResourceAttribute = javaResourceAttribute;
	}
	
	public String getTypeName() {
		return this.javaResourceAttribute.getTypeName();
	}

}
