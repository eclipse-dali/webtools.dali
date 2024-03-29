/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;

public class EclipseLinkOrmCustomConverter
	extends EclipseLinkOrmConverterClassConverter<XmlConverter>
	implements EclipseLinkCustomConverter
{
	public EclipseLinkOrmCustomConverter(JpaContextModel parent, XmlConverter xmlConverter) {
		super(parent, xmlConverter);
	}


	// ********** converter class **********

	@Override
	protected String getXmlConverterClass() {
		return this.xmlConverter.getClassName();
	}

	@Override
	protected void setXmlConverterClass(String converterClass) {
		this.xmlConverter.setClassName(converterClass);
	}


	// ********** misc **********

	public Class<EclipseLinkCustomConverter> getConverterType() {
		return EclipseLinkCustomConverter.class;
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
		return ECLIPSELINK_CONVERTER_CLASS_NAME;
	}

	@Override
	protected ValidationMessage getEclipseLinkConverterInterfaceErrorMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER;
	}

	@Override
	protected TextRange getXmlConverterClassTextRange() {
		return this.xmlConverter.getConverterClassTextRange();
	}

	// ********** metadata conversion **********

	public void convertFrom(EclipseLinkJavaCustomConverter javaConverter) {
		super.convertFrom(javaConverter);
		this.setConverterClass(javaConverter.getFullyQualifiedConverterClass());
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
		return JavaProjectTools.getJavaClassNames(this.getJavaProject());
	}

	protected boolean converterClassNameTouches(int pos) {
		return this.xmlConverter.converterClassTouches(pos);
	}
}
