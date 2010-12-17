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

import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;

public class GenericJavaPersistentEnum
		extends AbstractJavaPersistentType
		implements JaxbPersistentEnum {

	protected String enumType;

	protected final EnumConstantContainer enumConstantContainer;

	public GenericJavaPersistentEnum(JaxbContextRoot parent, JavaResourceEnum resourceEnum) {
		super(parent, resourceEnum);
		this.enumType = this.getResourceEnumType();
		this.enumConstantContainer = new EnumConstantContainer();
	}

	@Override
	public JavaResourceEnum getJavaResourceType() {
		return (JavaResourceEnum) super.getJavaResourceType();
	}


	// ********** JaxbType impl **********

	public Kind getKind() {
		return Kind.PERSISTENT_ENUM;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setEnumType_(this.getResourceEnumType());
		this.enumConstantContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.enumConstantContainer.update();
	}

	// ********** xml enum annotation **********

	protected XmlEnumAnnotation getXmlEnumAnnotation() {
		return (XmlEnumAnnotation) this.getJavaResourceType().getNonNullAnnotation(XmlEnumAnnotation.ANNOTATION_NAME);
	}


	// ********** JaxbPersistentEnum impl **********

	// ********** enum type **********

	public String getEnumType() {
		return this.enumType;
	}

	public void setEnumType(String enumType) {
		this.getXmlEnumAnnotation().setValue(enumType);
		this.setEnumType_(enumType);	
	}

	protected void setEnumType_(String enumType) {
		String old = this.enumType;
		this.enumType = enumType;
		this.firePropertyChanged(ENUM_TYPE_PROPERTY, old, enumType);
	}

	protected String getResourceEnumType() {
		return this.getXmlEnumAnnotation().getValue();
	}

	public Iterable<JaxbEnumConstant> getEnumConstants() {
		return this.enumConstantContainer.getContextElements();
	}
	
	public int getEnumConstantsSize() {
		return this.enumConstantContainer.getContextElementsSize();
	}

	private Iterable<JavaResourceEnumConstant> getResourceEnumConstants() {
		return getJavaResourceType().getEnumConstants();
	}
	
	private JaxbEnumConstant buildEnumConstant(JavaResourceEnumConstant resourceEnumConstant) {
		return getFactory().buildJavaEnumConstant(this, resourceEnumConstant);
	}

	/**
	 * enum constant container adapter
	 */
	protected class EnumConstantContainer
		extends ContextCollectionContainer<JaxbEnumConstant, JavaResourceEnumConstant>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return ENUM_CONSTANTS_COLLECTION;
		}
		@Override
		protected JaxbEnumConstant buildContextElement(JavaResourceEnumConstant resourceElement) {
			return GenericJavaPersistentEnum.this.buildEnumConstant(resourceElement);
		}
		@Override
		protected Iterable<JavaResourceEnumConstant> getResourceElements() {
			return GenericJavaPersistentEnum.this.getResourceEnumConstants();
		}
		@Override
		protected JavaResourceEnumConstant getResourceElement(JaxbEnumConstant contextElement) {
			return contextElement.getResourceEnumConstant();
		}
	}

}
