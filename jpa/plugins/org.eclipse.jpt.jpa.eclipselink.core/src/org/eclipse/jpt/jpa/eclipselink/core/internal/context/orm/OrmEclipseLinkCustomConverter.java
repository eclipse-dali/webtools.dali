/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.text.edits.ReplaceEdit;

public class OrmEclipseLinkCustomConverter
	extends OrmEclipseLinkConverterClassConverter<XmlConverter>
	implements EclipseLinkCustomConverter
{

	public OrmEclipseLinkCustomConverter(XmlContextNode parent, XmlConverter xmlConverter) {
		super(parent, xmlConverter);
		this.converterClass = xmlConverter.getClassName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setConverterClass_(this.xmlConverter.getClassName());
	}


	// ********** converter class **********

	public void setConverterClass(String converterClass) {
		this.setConverterClass_(converterClass);
		this.xmlConverter.setClassName(converterClass);
	}

	// ********** misc **********

	public Class<EclipseLinkCustomConverter> getType() {
		return EclipseLinkCustomConverter.class;
	}

	@Override
	public TextRange getConverterClassTextRange() {
		return this.xmlConverter.getConverterClassTextRange();
	}

	// ********** refactoring **********

	@Override
	protected ReplaceEdit createRenameEdit(IType originalType, String newName) {
		return this.xmlConverter.createRenameEdit(originalType, newName);
	}

	@Override
	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return xmlConverter.createRenamePackageEdit(newName);
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

		public Class<EclipseLinkCustomConverter> getConverterType() {
			return EclipseLinkCustomConverter.class;
		}

		public XmlConverter getXmlConverter(XmlConverterHolder xmlConverterContainer) {
			return xmlConverterContainer.getConverter();
		}

		public OrmEclipseLinkCustomConverter buildConverter(XmlNamedConverter xmlConverter, XmlContextNode parent) {
			return new OrmEclipseLinkCustomConverter(parent, (XmlConverter) xmlConverter);
		}

		@Override
		protected XmlConverter buildXmlConverter() {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		}

		@Override
		public void setXmlConverter(XmlConverterHolder xmlConverterContainer, XmlNamedConverter xmlConverter) {
			xmlConverterContainer.setConverter((XmlConverter) xmlConverter);
		}
	}
}
