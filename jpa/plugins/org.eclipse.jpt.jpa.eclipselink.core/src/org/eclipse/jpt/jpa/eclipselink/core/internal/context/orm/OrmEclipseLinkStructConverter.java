/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkStructConverter
	extends OrmEclipseLinkConverterClassConverter<XmlStructConverter>
	implements EclipseLinkStructConverter
{

	public OrmEclipseLinkStructConverter(XmlContextNode parent, XmlStructConverter xmlConverter) {
		super(parent, xmlConverter);
		this.converterClass = xmlConverter.getConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setConverterClass_(this.xmlConverter.getConverter());
	}


	// ********** converter class **********

	public void setConverterClass(String converterClass) {
		this.setConverterClass_(converterClass);
		this.xmlConverter.setConverter(converterClass);
	}

	// ********** misc **********

	public Class<EclipseLinkStructConverter> getType() {
		return EclipseLinkStructConverter.class;
	}

	@Override
	protected TextRange getXmlConverterClassTextRange() {
		return this.xmlConverter.getConverterClassTextRange();
	}

	// ********** refactoring **********

	@Override
	protected ReplaceEdit createRenameEdit(IType originalType, String newName) {
		return this.xmlConverter.createRenameEdit(originalType, newName);
	}

	@Override
	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.xmlConverter.createRenamePackageEdit(newName);
	}

	// ********** adapter **********

	public static class Adapter
		extends AbstractAdapter
	{
		private static final Adapter INSTANCE = new Adapter();
		public static Adapter instance() {
			return INSTANCE;
		}

		private Adapter() {
			super();
		}

		public Class<EclipseLinkStructConverter> getConverterType() {
			return EclipseLinkStructConverter.class;
		}

		public XmlStructConverter getXmlConverter(XmlConverterHolder xmlConverterContainer) {
			return xmlConverterContainer.getStructConverter();
		}

		public OrmEclipseLinkStructConverter buildConverter(XmlNamedConverter xmlConverter, XmlContextNode parent) {
			return new OrmEclipseLinkStructConverter(parent, (XmlStructConverter) xmlConverter);
		}

		@Override
		protected XmlStructConverter buildXmlConverter() {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		}

		@Override
		public void setXmlConverter(XmlConverterHolder xmlConverterContainer, XmlNamedConverter xmlConverter) {
			xmlConverterContainer.setStructConverter((XmlStructConverter) xmlConverter);
		}
	}
	
	//   ********* validation ********************
	
	@Override
	protected void validateConverterClass(List<IMessage> messages) {
		IJavaProject javaProject = this.getJpaProject().getJavaProject();

		if (StringTools.stringIsEmpty(this.converterClass)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_DEFINED,
					this,
					this.getConverterClassTextRange()
				)
			);
			return;
		}
		if ( ! this.converterClassExists(javaProject)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_EXISTS,
					new String[] {this.converterClass},
					this,
					this.getConverterClassTextRange()
				)
			);
			return;
		}
		if ( ! this.converterClassImplementsInterface(javaProject, ECLIPSELINK_STRUCT_CONVERTER_CLASS_NAME)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.STRUCT_CONVERTER_CLASS_IMPLEMENTS_STRUCT_CONVERTER,
					new String[] {this.converterClass},
					this,
					this.getConverterClassTextRange()
				)
			);
		}
	}

	private boolean converterClassExists(IJavaProject javaProject) {
		return (this.converterClass != null) &&
				(JDTTools.findType(javaProject, this.converterClass) != null);
	}

	private boolean converterClassImplementsInterface(IJavaProject javaProject, String interfaceName) {
		return (this.converterClass != null) &&
				JDTTools.typeNamedImplementsInterfaceNamed(javaProject, this.converterClass, interfaceName);
	}
	
}
