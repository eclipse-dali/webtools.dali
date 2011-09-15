/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;

public interface IRelation {

	public static enum RelType {
		ONE_TO_ONE, ONE_TO_MANY, MANY_TO_ONE, MANY_TO_MANY
	}

	public static enum RelDir {
		UNI, BI
	}

	public abstract String getId();

	public abstract JavaPersistentType getOwner();

	public abstract JavaPersistentType getInverse();
	
	public void setOwnerAnnotatedAttribute(JavaPersistentAttribute ownerAnnotatedAttribute);
	
	public JavaPersistentAttribute getOwnerAnnotatedAttribute();
		
	public void setInverseAnnotatedAttribute(JavaPersistentAttribute inverseAnnotatedAttribute);
				
	public void setOwnerAttributeName(String ownerAttributeName);
		
	public void setInverseAttributeName(String inverseAttributeName);
	
	public abstract JavaPersistentAttribute getInverseAnnotatedAttribute();

	public abstract String getOwnerAttributeName();

	public abstract String getInverseAttributeName();

	public abstract RelType getRelType();

	public abstract RelDir getRelDir();

}