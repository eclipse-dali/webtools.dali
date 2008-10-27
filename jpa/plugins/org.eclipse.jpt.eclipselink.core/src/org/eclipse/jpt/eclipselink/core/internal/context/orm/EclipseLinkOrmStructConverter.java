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
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmStructConverter extends AbstractXmlContextNode implements StructConverter, EclipseLinkOrmConverter
{	
	private XmlConvertibleMapping resourceMapping;
	
	private String name;
	
	private String converterClass;
	
	public EclipseLinkOrmStructConverter(XmlContextNode parent, XmlConvertibleMapping resourceMapping) {
		super(parent);
		this.initialize(resourceMapping);
	}

	public String getType() {
		return EclipseLinkConverter.STRUCT_CONVERTER;
	}
		
	public void addToResourceModel() {
		this.resourceMapping.setStructConverter(EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverterImpl());
	}
	
	public void removeFromResourceModel() {
		this.resourceMapping.setStructConverter(null);
	}

	protected XmlStructConverter getResourceConverter() {
		return this.resourceMapping.getStructConverter();
	}
	
	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		getResourceConverter().setConverter(newConverterClass);
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		getResourceConverter().setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	protected void initialize(XmlConvertibleMapping resourceMapping) {
		this.resourceMapping = resourceMapping;
		this.name = this.name();
		this.converterClass = this.converterClass();
	}
	
	public void update() {
		this.setName_(this.name());
		this.setConverterClass_(this.converterClass());
	}

	protected String name() {
		return getResourceConverter() == null ? null : getResourceConverter().getName();
	}
	
	protected String converterClass() {
		return getResourceConverter() == null ? null : getResourceConverter().getConverter();
	}
	
	//************ validation ***************
	
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

	public TextRange getValidationTextRange() {
		return getResourceConverter().getValidationTextRange();
	}

}
