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
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import java.util.Hashtable;

import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;

abstract public class AbstractRelation implements IRelation {
	protected final static String SEPARATOR = ";"; //$NON-NLS-1$
	protected PersistentType owner;
	protected PersistentType inverse;
	protected PersistentAttribute ownerAnnotatedAttribute;
	protected PersistentAttribute inverseAnnotatedAttribute;

	protected String ownerAttributeName;
	protected String inverseAttributeName;

	
	public final static Hashtable<RelType, String> relTypeToIdPart = new Hashtable<RelType, String>(); 
	public final static Hashtable<RelDir, String> relDirToIdPart = new Hashtable<RelDir, String>(); 
	
	static {
		relTypeToIdPart.put(RelType.ONE_TO_ONE, "1-1"); //$NON-NLS-1$
		relTypeToIdPart.put(RelType.ONE_TO_MANY, "1-N"); //$NON-NLS-1$
		relTypeToIdPart.put(RelType.MANY_TO_ONE, "N-1"); //$NON-NLS-1$
		relTypeToIdPart.put(RelType.MANY_TO_MANY, "N-N"); //$NON-NLS-1$
		
		relDirToIdPart.put(RelDir.UNI, "->"); //$NON-NLS-1$
		relDirToIdPart.put(RelDir.BI, "<->"); //$NON-NLS-1$
	}
	
	public AbstractRelation(PersistentType owner, PersistentType inverse) {
		this.owner = owner;
		this.inverse = inverse;
	}
	
	public String getId() {
		return generateId(owner, inverse, getOwnerAttributeName(), getInverseAttributeName(), getRelType(), getRelDir());
	}
	
	public PersistentType getOwner() {
		return owner; 
	}
	
	public PersistentType getInverse() {
		return inverse; 
	}	

	public PersistentAttribute getInverseAnnotatedAttribute() {
		return inverseAnnotatedAttribute;
	}

	public PersistentAttribute getOwnerAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}	
	
	public void setOwnerAnnotatedAttribute(
			PersistentAttribute ownerAnnotatedAttribute) {
		this.ownerAnnotatedAttribute = ownerAnnotatedAttribute;
	}

	public void setInverseAnnotatedAttribute(
			PersistentAttribute inverseAnnotatedAttribute) {
		this.inverseAnnotatedAttribute = inverseAnnotatedAttribute;
	}

	public void setOwnerAttributeName(String ownerAttributeName) {
		this.ownerAttributeName = ownerAttributeName;
	}
	
	public String getOwnerAttributeName() {
		if (this.ownerAnnotatedAttribute != null)
			return ownerAnnotatedAttribute.getName();
		return ownerAttributeName;
	}
	
	public void setInverseAttributeName(String inverseAttributeName) {
		this.inverseAttributeName = inverseAttributeName;
	}
	
	public String getInverseAttributeName() {
		if (this.inverseAnnotatedAttribute != null)
			return inverseAnnotatedAttribute.getName();
		return inverseAttributeName;
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object otherRel) {
		if (!IRelation.class.isInstance(otherRel))
			return false;
		return getId().equals(((IRelation)otherRel).getId());
	}
	
	public abstract RelType getRelType(); 
	
	public abstract RelDir getRelDir();
	
	public static String generateId(PersistentType owner, 
									PersistentType inverse, 
									String ownerAttributeName,
									String inverseAttributeName,
									RelType relType, 
									RelDir relDir) {
		String id = ""; //$NON-NLS-1$
		if(inverseAttributeName != null){
			id = owner.getName() + "-" + inverse.getName() + SEPARATOR +  //$NON-NLS-1$
					relTypeToIdPart.get(relType) + SEPARATOR +
					relDirToIdPart.get(relDir) + SEPARATOR + ownerAttributeName + SEPARATOR + inverseAttributeName;
		} else {
			id = owner.getName() + "-" + inverse.getName() + SEPARATOR +  //$NON-NLS-1$
					relTypeToIdPart.get(relType) + SEPARATOR +
					relDirToIdPart.get(relDir) + SEPARATOR + ownerAttributeName;
		}
		return id;
	}

}
