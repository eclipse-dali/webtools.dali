/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkStructConverter
	extends OrmEclipseLinkConverter<XmlStructConverter>
	implements EclipseLinkStructConverter
{
	private String converterClass;


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

	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		this.setConverterClass_(converterClass);
		this.xmlConverter.setConverter(converterClass);
	}

	protected void setConverterClass_(String converterClass) {
		String old = this.converterClass;
		this.converterClass = converterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, old, converterClass);
	}

	protected JavaResourcePersistentType getConverterJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.converterClass);
	}


	// ********** misc **********

	public Class<EclipseLinkStructConverter> getType() {
		return EclipseLinkStructConverter.class;
	}


	// ********** refactoring **********

	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameEdit(IType originalType, String newName) {
		return this.xmlConverter.createRenameEdit(originalType, newName);
	}

	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.xmlConverter.createRenamePackageEdit(newName);
	}

	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.isIn(originalPackage) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected boolean isFor(String typeName) {
		JavaResourcePersistentType converterType = this.getConverterJavaResourcePersistentType();
		return (converterType != null) && converterType.getQualifiedName().equals(typeName);
	}

	protected boolean isIn(IPackageFragment packageFragment) {
		JavaResourcePersistentType converterType = this.getConverterJavaResourcePersistentType();
		return (converterType != null) && converterType.isIn(packageFragment);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO validate converter class
//		this.validateConverterClass(messages);
	}

//	protected void validateConverterClass(List<IMessage> messages) {
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
//	}
//
//	public TextRange getConverterClassTextRange() {
//		return getResourceConverter().getClassNameTextRange();
//	}

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
}
