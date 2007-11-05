/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;



public enum InheritanceType {


	SINGLE_TABLE,
	JOINED,
	TABLE_PER_CLASS;

	public static InheritanceType fromJavaResourceModel(org.eclipse.jpt.core.internal.resource.java.InheritanceType javaInheritanceType) {
		if (javaInheritanceType == org.eclipse.jpt.core.internal.resource.java.InheritanceType.SINGLE_TABLE) {
			return SINGLE_TABLE;
		}
		else if (javaInheritanceType == org.eclipse.jpt.core.internal.resource.java.InheritanceType.JOINED) {
			return JOINED;
		}
		else if (javaInheritanceType == org.eclipse.jpt.core.internal.resource.java.InheritanceType.TABLE_PER_CLASS) {
			return TABLE_PER_CLASS;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.internal.resource.java.InheritanceType toJavaResourceModel(InheritanceType inheritanceType) {
		if (inheritanceType == SINGLE_TABLE)  {
			return org.eclipse.jpt.core.internal.resource.java.InheritanceType.SINGLE_TABLE;
		}
		else if (inheritanceType == JOINED) {
			return org.eclipse.jpt.core.internal.resource.java.InheritanceType.JOINED;
		}
		else if (inheritanceType == TABLE_PER_CLASS) {
			return org.eclipse.jpt.core.internal.resource.java.InheritanceType.TABLE_PER_CLASS;
		}
		return null;
	}
	

	public static InheritanceType fromXmlResourceModel(org.eclipse.jpt.core.internal.resource.orm.InheritanceType ormInheritanceType) {
		if (ormInheritanceType == org.eclipse.jpt.core.internal.resource.orm.InheritanceType.SINGLETABLE) {
			return SINGLE_TABLE;
		}
		else if (ormInheritanceType == org.eclipse.jpt.core.internal.resource.orm.InheritanceType.JOINED) {
			return JOINED;
		}
		else if (ormInheritanceType == org.eclipse.jpt.core.internal.resource.orm.InheritanceType.TABLEPERCLASS) {
			return TABLE_PER_CLASS;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.internal.resource.orm.InheritanceType toXmlResourceModel(InheritanceType inheritanceType) {
		if (inheritanceType == SINGLE_TABLE)  {
			return org.eclipse.jpt.core.internal.resource.orm.InheritanceType.SINGLETABLE;
		}
		else if (inheritanceType == JOINED) {
			return org.eclipse.jpt.core.internal.resource.orm.InheritanceType.JOINED;
		}
		else if (inheritanceType == TABLE_PER_CLASS) {
			return org.eclipse.jpt.core.internal.resource.orm.InheritanceType.TABLEPERCLASS;
		}
		return null;
	}
}
