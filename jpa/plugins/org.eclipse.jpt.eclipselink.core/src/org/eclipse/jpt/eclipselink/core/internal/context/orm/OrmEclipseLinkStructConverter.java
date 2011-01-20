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

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.text.edits.ReplaceEdit;

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

	public TextRange getConverterClassTextRange() {
		return this.xmlConverter.getConverterClassTextRange();
	}

	// ********** refactoring **********

	protected ReplaceEdit createRenameEdit(IType originalType, String newName) {
		return this.xmlConverter.createRenameEdit(originalType, newName);
	}

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
}
