/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.customization;

import java.io.Serializable;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * CustomizerProperties
 */
public class CustomizerProperties implements Cloneable, Serializable
{
	private String entityName;

	// ********** EclipseLink properties **********
	private String className;

	private static final long serialVersionUID = 1L;

	// ********** constructors **********
	public CustomizerProperties(String entityName) {
		this.entityName = entityName;
	}

	// ********** behaviors **********
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		CustomizerProperties customizer = (CustomizerProperties) o;
		return (
			(this.className == null ?
				customizer.className == null : this.className.equals(customizer.className)));
	}
	
	 @Override
	 public synchronized CustomizerProperties clone() {
		 try {
			 return (CustomizerProperties)super.clone();
		 }
		 catch (CloneNotSupportedException ex) {
			 throw new InternalError();
		 }
	 }

	public boolean isEmpty() {
		return this.className == null;
	}

	// ********** getter/setter **********
	public String getEntityName() {
		return this.entityName;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String newClassName) {
		this.className = newClassName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.buildSimpleToStringOn(this, sb);
		sb.append(" (");
		this.toString(sb);
		sb.append(')');
		return sb.toString();
	}

	public void toString(StringBuilder sb) {
		sb.append(" class: ");
		sb.append(this.className);
		sb.append(", entityName: ");
		sb.append(this.entityName);
	}
}