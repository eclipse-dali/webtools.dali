/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterClassConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class EclipseLinkOrmConverterClassConverter<X extends XmlNamedConverter>
	extends OrmEclipseLinkConverter<X>
	implements EclipseLinkConverterClassConverter
{
	private String converterClass;
	private String fullyQualifiedConverterClass;


	public EclipseLinkOrmConverterClassConverter(JpaContextModel parent, X xmlConverter) {
		super(parent, xmlConverter);
		this.converterClass = this.getXmlConverterClass();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setConverterClass_(this.getXmlConverterClass());
	}

	@Override
	public void update() {
		super.update();
		this.setFullyQualifiedConverterClass(this.buildFullyQualifiedConverterClass());
	}

	// ********** converter class **********

	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		this.setConverterClass_(converterClass);
		this.setXmlConverterClass(converterClass);
	}

	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}

	public String getFullyQualifiedConverterClass() {
		return this.fullyQualifiedConverterClass;
	}

	protected void setFullyQualifiedConverterClass(String converterClass) {
		String old = this.fullyQualifiedConverterClass;
		this.fullyQualifiedConverterClass = converterClass;
		this.firePropertyChanged(FULLY_QUALIFIED_CONVERTER_CLASS_PROPERTY, old, converterClass);
	}

	protected String buildFullyQualifiedConverterClass() {
		return this.getMappingFileRoot().qualify(this.converterClass);
	}

	protected JavaResourceAbstractType getConverterJavaResourceType() {
		if (this.fullyQualifiedConverterClass == null) {
			return null;
		}
		return this.getJpaProject().getJavaResourceType(this.fullyQualifiedConverterClass);
	}

	protected abstract String getXmlConverterClass();

	protected abstract void setXmlConverterClass(String converterClass);


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateConverterClass(messages);
	}

	protected void validateConverterClass(List<IMessage> messages) {
		if (StringTools.isBlank(this.converterClass)) {
			messages.add(
				this.buildValidationMessage(
					this.getConverterClassTextRange(),
					JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_DEFINED
				)
			);
			return;
		}

		IType converterJdtType = JavaProjectTools.findType(this.getJavaProject(), this.getFullyQualifiedConverterClass()); 
		if (converterJdtType == null) {
			messages.add(
				this.buildValidationMessage(
					this.getConverterClassTextRange(),
					JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_EXISTS,
					this.getFullyQualifiedConverterClass()
				)
			);
			return;
		}

		if ( ! this.converterClassImplementsInterface(this.getEclipseLinkConverterInterface())) {
			messages.add(
				this.buildValidationMessage(
					this.getConverterClassTextRange(),
					this.getEclipseLinkConverterInterfaceErrorMessage(),
					this.getFullyQualifiedConverterClass()
				)
			);
		}
	}

	/**
	 * Return the name of the EclipseLink interface the converter class must
	 * implement.
	 */
	protected abstract String getEclipseLinkConverterInterface();

	protected abstract ValidationMessage getEclipseLinkConverterInterfaceErrorMessage();

	/**
	 * Add <code>null</code> check.
	 */
	protected boolean typeImplementsInterface(String typeName, String interfaceName) {
		return (typeName != null) && 
				TypeTools.isSubType(typeName, interfaceName, this.getJavaProject());
	}

	protected boolean converterClassImplementsInterface(String interfaceName) {
		return this.typeImplementsInterface(this.getFullyQualifiedConverterClass(), interfaceName);
	}

	protected TextRange getConverterClassTextRange() {
		return this.getValidationTextRange(this.getXmlConverterClassTextRange());
	}

	protected abstract TextRange getXmlConverterClassTextRange();


	@Override
	public boolean isEquivalentTo(JpaNamedContextModel node) {
		return super.isEquivalentTo(node)
				&& this.isEquivalentTo((EclipseLinkConverterClassConverter) node);
	}

	protected boolean isEquivalentTo(EclipseLinkConverterClassConverter converter) {
		return ObjectTools.equals(this.getFullyQualifiedConverterClass(), converter.getFullyQualifiedConverterClass());
	}

	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected abstract ReplaceEdit createRenameEdit(IType originalType, String newName);

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected abstract ReplaceEdit createRenamePackageEdit(String newName);

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.isIn(originalPackage) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected boolean isFor(String typeName) {
		String converterType = this.getFullyQualifiedConverterClass();
		return (converterType != null) && converterType.equals(typeName);
	}

	protected boolean isIn(IPackageFragment packageFragment) {
		JavaResourceAbstractType converterType = this.getConverterJavaResourceType();
		return (converterType != null) && converterType.isIn(packageFragment);
	}
}
