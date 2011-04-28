/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class OrmEclipseLinkConverterClassConverter<X extends XmlNamedConverter>
	extends OrmEclipseLinkConverter<X>
	implements EclipseLinkCustomConverter
{
	protected String converterClass;

	protected JavaResourcePersistentType converterPersistentType;

	public OrmEclipseLinkConverterClassConverter(XmlContextNode parent, X xmlConverter) {
		super(parent, xmlConverter);
	}

	// **************** converter class ****************************************

	public String getConverterClass() {
		return this.converterClass;
	}

	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}

	protected JavaResourcePersistentType getConverterJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.converterClass);
	}

	// **************** resource interaction ***********************************

	protected void updateConverterPersistentType() {
		this.converterPersistentType = this.getConverterJavaResourcePersistentType();
	}

	// **************** validation *********************************************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateConverterClass(messages);
	}

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
		if ( ! this.converterClassImplementsInterface(javaProject, ECLIPSELINK_CONVERTER_CLASS_NAME)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER,
					new String[] {this.converterClass},
					this,
					this.getConverterClassTextRange()
				)
			);
		}
	}

	private boolean converterClassExists(IJavaProject javaProject) {
		if (this.converterClass == null) {
			return false;
		}

		if (JDTTools.findType(javaProject, this.converterClass) != null) {
			return true;
		}

		String globalPackage = this.getEntityMappings().getPackage();
		if (StringTools.stringIsEmpty(globalPackage)) {
			return false;
		}
		return JDTTools.findType(javaProject, globalPackage + '.' + this.converterClass) != null;
	}

	private boolean converterClassImplementsInterface(IJavaProject javaProject, String interfaceName) {
		if (this.converterClass == null) {
			return false;
		}
		if (JDTTools.typeNamedImplementsInterfaceNamed(javaProject, this.converterClass, interfaceName)) {
			return true;
		}
		String globalPackage = this.getEntityMappings().getPackage();
		if (StringTools.stringIsEmpty(globalPackage)) {
			return false;
		}
		return JDTTools.typeNamedImplementsInterfaceNamed(javaProject, globalPackage + '.' + this.converterClass, interfaceName);
	}

	protected TextRange getConverterClassTextRange() {
		return this.getValidationTextRange(this.getXmlConverterClassTextRange());
	}

	protected abstract TextRange getXmlConverterClassTextRange();

	//************************* refactoring ************************

	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected abstract ReplaceEdit createRenameEdit(IType originalType, String newName);

	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected abstract ReplaceEdit createRenamePackageEdit(String newName);

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

}
