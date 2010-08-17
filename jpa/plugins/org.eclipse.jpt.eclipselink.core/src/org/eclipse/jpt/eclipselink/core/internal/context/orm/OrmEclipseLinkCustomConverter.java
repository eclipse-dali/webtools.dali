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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkCustomConverter extends OrmEclipseLinkConverter<XmlConverter>
	implements EclipseLinkCustomConverter
{	
	private String converterClass;
	
	private JavaResourcePersistentType converterPersistentType;
	
	public OrmEclipseLinkCustomConverter(XmlContextNode parent) {
		super(parent);
	}
	
	
	public String getType() {
		return EclipseLinkConverter.CUSTOM_CONVERTER;
	}

	
	// **************** converter class ****************************************
	
	public String getConverterClass() {
		return this.converterClass;
	}
	
	public void setConverterClass(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		this.resourceConverter.setClassName(newConverterClass);
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}

	protected JavaResourcePersistentType getConverterJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.converterClass);
	}
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(XmlConverter xmlResource) {
		super.initialize(xmlResource);
		this.converterClass = getResourceClassName();
		this.converterPersistentType = this.getConverterJavaResourcePersistentType();
	}
	
	@Override
	public void update() {
		super.update();
		setConverterClass_(getResourceClassName());
		updateConverterPersistentType();
	}
	
	protected String getResourceClassName() {
		return this.resourceConverter.getClassName();
	}
	
	protected void updateConverterPersistentType() {
		this.converterPersistentType = this.getConverterJavaResourcePersistentType();
	}

	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
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


	//************************* refactoring ************************

	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		if (this.isFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenameEdit(originalType, newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenameEdit(IType originalType, String newName) {
		return getXmlResource().createRenameEdit(originalType, newName);
	}

	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.isFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newPackage.getElementName()));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return getXmlResource().createRenamePackageEdit(newName);
	}

	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.isIn(originalPackage)) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newName));
		}
		return EmptyIterable.instance();
	}

	protected boolean isFor(String typeName) {
		if (this.converterPersistentType != null && this.converterPersistentType.getQualifiedName().equals(typeName)) {
			return true;
		}
		return false;	
	}

	protected boolean isIn(IPackageFragment packageFragment) {
		if (this.converterPersistentType != null) {
			return this.converterPersistentType.isIn(packageFragment);
		}
		return false;
	}
}