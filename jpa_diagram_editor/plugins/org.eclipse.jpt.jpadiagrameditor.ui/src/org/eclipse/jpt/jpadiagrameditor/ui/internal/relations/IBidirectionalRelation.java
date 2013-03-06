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
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.jpt.jpa.core.context.PersistentAttribute;


public interface IBidirectionalRelation extends IRelation {

	public abstract PersistentAttribute getOwnerAnnotatedAttribute();

	public abstract PersistentAttribute getInverseAnnotatedAttribute();

	public abstract void setOwnerAnnotatedAttribute(PersistentAttribute ownerAnnotatedAttribute);

	public abstract void setInverseAnnotatedAttribute(PersistentAttribute inverseAnnotatedAttribute);

}
