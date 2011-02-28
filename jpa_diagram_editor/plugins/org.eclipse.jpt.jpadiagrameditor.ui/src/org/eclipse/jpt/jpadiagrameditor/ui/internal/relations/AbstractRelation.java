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

import java.util.Hashtable;

import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;

abstract public class AbstractRelation implements IRelation {
	protected final static String SEPARATOR = ";"; //$NON-NLS-1$
	protected JavaPersistentType owner;
	protected JavaPersistentType inverse;
	protected JavaPersistentAttribute ownerAnnotatedAttribute;
	protected JavaPersistentAttribute inverseAnnotatedAttribute;

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
	
	public AbstractRelation(JavaPersistentType owner, JavaPersistentType inverse) {
		this.owner = owner;
		this.inverse = inverse;
	}
	
	public String getId() {
		return generateId(owner, inverse, getOwnerAttributeName(), getRelType(), getRelDir());
	}
	
	public JavaPersistentType getOwner() {
		return owner; 
	}
	
	public JavaPersistentType getInverse() {
		return inverse; 
	}	
	
	public JavaPersistentAttribute getInverseAnnotatedAttribute() {
		return inverseAnnotatedAttribute;
	}
	
	public JavaPersistentAttribute getOwnerAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}	
	
	public void setOwnerAnnotatedAttribute(
			JavaPersistentAttribute ownerAnnotatedAttribute) {
		this.ownerAnnotatedAttribute = ownerAnnotatedAttribute;
	}

	public void setInverseAnnotatedAttribute(
			JavaPersistentAttribute inverseAnnotatedAttribute) {
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
	
	public int hashCode() {
		return getId().hashCode();
	}
	
	public boolean equals(Object otherRel) {
		if (!IRelation.class.isInstance(otherRel))
			return false;
		return getId().equals(((IRelation)otherRel).getId());
	}
	
	public abstract RelType getRelType(); 
	
	public abstract RelDir getRelDir();
	
	public static String generateId(JavaPersistentType owner, 
									JavaPersistentType inverse, 
									String ownerAttributeName,
									RelType relType, 
									RelDir relDir) {
		String id = owner.getName() + "-" + inverse.getName() + SEPARATOR +  //$NON-NLS-1$
					relTypeToIdPart.get(relType) + SEPARATOR +
					relDirToIdPart.get(relDir) + SEPARATOR + ownerAttributeName; 
		return id;
	}

}
