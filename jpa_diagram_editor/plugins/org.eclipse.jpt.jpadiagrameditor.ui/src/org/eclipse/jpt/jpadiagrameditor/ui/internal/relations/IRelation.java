/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;

public interface IRelation {

	public static enum RelType {
		ONE_TO_ONE, ONE_TO_MANY, MANY_TO_ONE, MANY_TO_MANY
	}

	public static enum RelDir {
		UNI, BI
	}

	public abstract String getId();

	public abstract PersistentType getOwner();

	public abstract PersistentType getInverse();

	public void setOwnerAnnotatedAttribute(PersistentAttribute ownerAnnotatedAttribute);

	public PersistentAttribute getOwnerAnnotatedAttribute();

	public void setInverseAnnotatedAttribute(PersistentAttribute inverseAnnotatedAttribute);
				
	public void setOwnerAttributeName(String ownerAttributeName);
		
	public void setInverseAttributeName(String inverseAttributeName);

	public abstract PersistentAttribute getInverseAnnotatedAttribute();

	public abstract String getOwnerAttributeName();

	public abstract String getInverseAttributeName();

	public abstract RelType getRelType();

	public abstract RelDir getRelDir();

}
