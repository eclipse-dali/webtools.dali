/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 *  Entity
 */
public class EclipseLinkCustomizationEntity
	extends AbstractModel
	implements Cloneable, Serializable
{
	private String name;
	private EclipseLinkCustomization parent;

	public static final String DESCRIPTOR_CUSTOMIZER_PROPERTY = EclipseLinkCustomization.DESCRIPTOR_CUSTOMIZER_PROPERTY;

	// ********** EclipseLink properties **********
	private String descriptorCustomizer;

	private static final long serialVersionUID = 1L;

	// ********** constructors **********
	public EclipseLinkCustomizationEntity(EclipseLinkCustomization parent, String name) {
		this(parent);
		if(StringTools.isBlank(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	private EclipseLinkCustomizationEntity(EclipseLinkCustomization parent) {
		this.parent = parent;
	}
	
	// ********** behaviors **********
	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof EclipseLinkCustomizationEntity)) {
			return false;
		}
		EclipseLinkCustomizationEntity customizer = (EclipseLinkCustomizationEntity) o;
		return (
			(this.descriptorCustomizer == null ?
				customizer.descriptorCustomizer == null : 
				this.descriptorCustomizer.equals(customizer.descriptorCustomizer)));
	}
	
	@Override
	public int hashCode() {
		return (this.descriptorCustomizer == null) ? 0 : this.descriptorCustomizer.hashCode();
	}
	
	 @Override
	 public EclipseLinkCustomizationEntity clone() {
		 try {
			 return (EclipseLinkCustomizationEntity)super.clone();
		 }
		 catch (CloneNotSupportedException ex) {
			 throw new InternalError();
		 }
	 }

	public boolean isEmpty() {
		return this.descriptorCustomizer == null;
	}
	
	public boolean entityNameIsValid() {
		return ! StringTools.isBlank(this.name);
	}

	public EclipseLinkCustomization getParent() {
		return this.parent;
	}

	// ********** getter/setter **********
	public String getName() {
		return this.name;
	}
	public static final Transformer<EclipseLinkCustomizationEntity, String> NAME_TRANSFORMER = new NameTransformer();
	public static class NameTransformer
		extends TransformerAdapter<EclipseLinkCustomizationEntity, String>
	{
		@Override
		public String transform(EclipseLinkCustomizationEntity entity) {
			return entity.getName();
		}
	}

	public String getDescriptorCustomizer() {
		return this.descriptorCustomizer;
	}

	public void setDescriptorCustomizer(String descriptorCustomizer) {
		String old = this.descriptorCustomizer;
		this.descriptorCustomizer = descriptorCustomizer;
		this.firePropertyChanged(DESCRIPTOR_CUSTOMIZER_PROPERTY, old, descriptorCustomizer);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append("name: "); //$NON-NLS-1$
		sb.append(this.name);
		sb.append(", descriptorCustomizer: "); //$NON-NLS-1$
		sb.append(this.descriptorCustomizer);
	}
}
