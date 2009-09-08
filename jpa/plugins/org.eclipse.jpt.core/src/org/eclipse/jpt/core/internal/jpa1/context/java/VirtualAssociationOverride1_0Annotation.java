/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.internal.context.java.VirtualAssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;

/**
 * javax.persistence.AssociationOverride
 */
public class VirtualAssociationOverride1_0Annotation
	extends VirtualAssociationOverrideAnnotation
{
	
	public VirtualAssociationOverride1_0Annotation(JavaResourcePersistentMember parent, String name, JoiningStrategy joiningStrategy) {
		super(parent, name, joiningStrategy);
	}

}
