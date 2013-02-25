/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;

public class OrmEclipseLinkStructConverter
	extends OrmEclipseLinkConverterClassConverter<XmlStructConverter>
	implements EclipseLinkStructConverter
{
	public OrmEclipseLinkStructConverter(JpaContextModel parent, XmlStructConverter xmlConverter) {
		super(parent, xmlConverter);
	}


	// ********** converter class **********

	@Override
	protected String getXmlConverterClass() {
		return this.xmlConverter.getConverter();
	}

	@Override
	protected void setXmlConverterClass(String converterClass) {
		this.xmlConverter.setConverter(converterClass);
	}


	// ********** misc **********

	public Class<EclipseLinkStructConverter> getType() {
		return EclipseLinkStructConverter.class;
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
	

	// ********** validation **********
	
	@Override
	protected String getEclipseLinkConverterInterface() {
		return ECLIPSELINK_STRUCT_CONVERTER_CLASS_NAME;
	}

	@Override
	protected ValidationMessage getEclipseLinkConverterInterfaceErrorMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.STRUCT_CONVERTER_CLASS_IMPLEMENTS_STRUCT_CONVERTER;
	}

	@Override
	protected TextRange getXmlConverterClassTextRange() {
		return this.xmlConverter.getConverterClassTextRange();
	}

	// ********** metadata conversion **********

	public void convertFrom(JavaEclipseLinkStructConverter javaConverter) {
		super.convertFrom(javaConverter);
		this.setConverterClass(javaConverter.getConverterClass());
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.converterClassNameTouches(pos)) {
			return this.getCandidateClassNames();
		}
		return null;
	}

	protected Iterable<String> getCandidateClassNames() {
		return MappingTools.getSortedJavaClassNames(this.getJavaProject());
	}

	protected boolean converterClassNameTouches(int pos) {
		return this.xmlConverter.converterClassTouches(pos);
	}
}
