/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumValueAnnotation;

public class GenericJavaEnumConstant
		extends AbstractJaxbContextNode
		implements JaxbEnumConstant {

	final protected JavaResourceEnumConstant resourceEnumConstant;
	
	protected String specifiedValue;

	public GenericJavaEnumConstant(JaxbPersistentEnum parent, JavaResourceEnumConstant resourceEnumConstant) {
		super(parent);
		this.resourceEnumConstant = resourceEnumConstant;
		this.specifiedValue = this.getResourceEnumValue();
	}

	public JavaResourceEnumConstant getResourceEnumConstant() {
		return this.resourceEnumConstant;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedValue_(this.getResourceEnumValue());
	}


	// ********** xml enum value annotation **********

	protected XmlEnumValueAnnotation getXmlEnumValueAnnotation() {
		return (XmlEnumValueAnnotation) this.getResourceEnumConstant().getNonNullAnnotation(XmlEnumValueAnnotation.ANNOTATION_NAME);
	}


	// ********** name **********

	public String getName() {
		return this.resourceEnumConstant.getName();
	}

	// ********** value **********

	public String getValue() {
		return this.getSpecifiedValue() != null ? this.getSpecifiedValue() : this.getDefaultValue();
	}

	public String getDefaultValue() {
		return this.getName();
	}

	public String getSpecifiedValue() {
		return this.specifiedValue;
	}

	public void setSpecifiedValue(String value) {
		this.getXmlEnumValueAnnotation().setValue(value);
		this.setSpecifiedValue_(value);	
	}

	protected void setSpecifiedValue_(String value) {
		String old = this.specifiedValue;
		this.specifiedValue = value;
		this.firePropertyChanged(SPECIFIED_VALUE_PROPERTY, old, value);
	}

	protected String getResourceEnumValue() {
		return this.getXmlEnumValueAnnotation().getValue();
	}
}
