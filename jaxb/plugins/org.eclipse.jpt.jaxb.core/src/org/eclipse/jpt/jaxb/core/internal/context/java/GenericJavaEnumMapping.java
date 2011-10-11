/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;


public class GenericJavaEnumMapping
		extends AbstractJavaTypeMapping
		implements JaxbEnumMapping {
	
	protected String enumType;

	protected final EnumConstantContainer enumConstantContainer;

	
	public GenericJavaEnumMapping(JaxbEnum parent) {
		super(parent);
		this.enumConstantContainer = new EnumConstantContainer();
		
		initEnumType();
		initEnumConstants();
	}
	
	
	@Override
	public JavaResourceEnum getJavaResourceType() {
		return (JavaResourceEnum) super.getJavaResourceType();
	}
	
	
	// ********** sync/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncEnumType();
		syncEnumConstants();
	}
	
	@Override
	public void update() {
		super.update();
		updateEnumConstants();
	}
	

	// ***** XmlEnum.value *****
	
	public String getEnumType() {
		return this.enumType;
	}
	
	public void setEnumType(String enumType) {
		getXmlEnumAnnotation().setValue(enumType);
		setEnumType_(enumType);	
	}
	
	protected void setEnumType_(String enumType) {
		String old = this.enumType;
		this.enumType = enumType;
		firePropertyChanged(ENUM_TYPE_PROPERTY, old, enumType);
	}
	
	protected XmlEnumAnnotation getXmlEnumAnnotation() {
		return (XmlEnumAnnotation) getJavaResourceType().getNonNullAnnotation(JAXB.XML_ENUM);
	}
	
	protected String getResourceEnumType() {
		return getXmlEnumAnnotation().getValue();
	}
	
	protected void initEnumType() {
		this.enumType = getResourceEnumType();
	}
	
	protected void syncEnumType() {
		setEnumType_(getResourceEnumType());
	}
	
	
	// ***** enum constants *****
	
	public Iterable<JaxbEnumConstant> getEnumConstants() {
		return this.enumConstantContainer.getContextElements();
	}
	
	public int getEnumConstantsSize() {
		return this.enumConstantContainer.getContextElementsSize();
	}
	
	protected void initEnumConstants() {
		this.enumConstantContainer.initialize();
	}
	
	protected void syncEnumConstants() {
		this.enumConstantContainer.synchronizeWithResourceModel();
	}
	
	protected void updateEnumConstants() {
		this.enumConstantContainer.update();
	}
	
	protected Iterable<JavaResourceEnumConstant> getResourceEnumConstants() {
		return getJavaResourceType().getEnumConstants();
	}
	
	private JaxbEnumConstant buildEnumConstant(JavaResourceEnumConstant resourceEnumConstant) {
		return getFactory().buildJavaEnumConstant(this, resourceEnumConstant);
	}
	
	
	/**
	 * enum constant container adapter
	 */
	protected class EnumConstantContainer
			extends ContextCollectionContainer<JaxbEnumConstant, JavaResourceEnumConstant> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return ENUM_CONSTANTS_COLLECTION;
		}
		
		@Override
		protected JaxbEnumConstant buildContextElement(JavaResourceEnumConstant resourceElement) {
			return GenericJavaEnumMapping.this.buildEnumConstant(resourceElement);
		}
		
		@Override
		protected Iterable<JavaResourceEnumConstant> getResourceElements() {
			return GenericJavaEnumMapping.this.getResourceEnumConstants();
		}
		
		@Override
		protected JavaResourceEnumConstant getResourceElement(JaxbEnumConstant contextElement) {
			return contextElement.getResourceEnumConstant();
		}
	}
}
