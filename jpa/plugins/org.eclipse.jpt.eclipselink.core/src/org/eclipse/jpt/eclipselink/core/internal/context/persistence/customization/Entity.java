/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization;

import java.io.Serializable;

import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

/**
 *  Entity
 */
public class Entity extends AbstractModel implements Cloneable, Serializable
{
	private String name;
	private Customization parent;

	public static final String DESCRIPTOR_CUSTOMIZER_PROPERTY = Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY; //$NON-NLS-1$

	// ********** EclipseLink properties **********
	private String descriptorCustomizer;

	private static final long serialVersionUID = 1L;

	// ********** constructors **********
	public Entity(Customization parent, String name) {
		this(parent);
		this.initialize(name);
	}

	private Entity(Customization parent) {
		this.parent = parent;
	}
	
	private void initialize(String name) {
		if(StringTools.stringIsEmpty(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	// ********** behaviors **********
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		Entity customizer = (Entity) o;
		return (
			(this.descriptorCustomizer == null ?
				customizer.descriptorCustomizer == null : 
				this.descriptorCustomizer.equals(customizer.descriptorCustomizer)));
	}
	
	 @Override
	 public Entity clone() {
		 try {
			 return (Entity)super.clone();
		 }
		 catch (CloneNotSupportedException ex) {
			 throw new InternalError();
		 }
	 }

	public boolean isEmpty() {
		return this.descriptorCustomizer == null;
	}
	
	public boolean entityNameIsValid() {
		return ! StringTools.stringIsEmpty(this.name);
	}

	public Customization getParent() {
		return this.parent;
	}

	// ********** getter/setter **********
	public String getName() {
		return this.name;
	}

	protected String getDescriptorCustomizer() {
		return this.descriptorCustomizer;
	}

	protected void setDescriptorCustomizer(String descriptorCustomizer) {
		String old = this.descriptorCustomizer;
		this.descriptorCustomizer = descriptorCustomizer;
		this.firePropertyChanged(DESCRIPTOR_CUSTOMIZER_PROPERTY, old, descriptorCustomizer);
	}

	public void toString(StringBuilder sb) {
		sb.append("name: ");
		sb.append(this.name);
		sb.append(", descriptorCustomizer: ");
		sb.append(this.descriptorCustomizer);
	}
}
