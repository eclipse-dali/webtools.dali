/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmStructConverter extends EclipseLinkOrmConverter<XmlStructConverter>
	implements StructConverter
{	
	private String converterClass;
	
	
	public EclipseLinkOrmStructConverter(XmlContextNode parent, XmlStructConverter xmlResource) {
		super(parent, xmlResource);
	}
		
	public String getType() {
		return EclipseLinkConverter.STRUCT_CONVERTER;
	}
	
	
	// **************** converter class ****************************************
	
	public String getConverterClass() {
		return this.converterClass;
	}
	
	public void setConverterClass(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		getXmlResource().setConverter(newConverterClass);
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(XmlStructConverter xmlResource) {
		super.initialize(xmlResource);
		this.converterClass = getResourceConverterClass();
	}
	
	@Override
	public void update() {
		super.update();
		setConverterClass_(getResourceConverterClass());
	}
	
	protected String getResourceConverterClass() {
		return this.resourceConverter.getConverter();
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		validateConverterClass(messages);
	}
	
//TODO validate converter class	
	protected void validateConverterClass(List<IMessage> messages) {
//		if (!getResourceConverter().implementsConverter()) {
//			messages.add(
//				DefaultEclipseLinkJpaValidationMessages.buildMessage(
//					IMessage.HIGH_SEVERITY,
//					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER,
//					new String[] {this.converterClass},
//					this, 
//					getConverterClassTextRange()
//				)
//			);
//		}
	}
	
//	public TextRange getConverterClassTextRange() {
//		return getResourceConverter().getClassNameTextRange();
//	}
}