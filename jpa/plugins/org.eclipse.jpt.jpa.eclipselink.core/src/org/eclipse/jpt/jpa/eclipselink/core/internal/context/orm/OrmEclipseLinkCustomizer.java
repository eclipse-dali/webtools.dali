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
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkCustomizer
	extends AbstractOrmXmlContextNode
	implements EclipseLinkCustomizer
{
	protected String specifiedCustomizerClass;
	protected String defaultCustomizerClass;


	public OrmEclipseLinkCustomizer(EclipseLinkOrmTypeMapping parent) {
		super(parent);
		this.specifiedCustomizerClass = this.buildSpecifiedCustomizerClass();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedCustomizerClass_(this.buildSpecifiedCustomizerClass());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultCustomizerClass(this.buildDefaultCustomizerClass());
	}


	// ********** customizer class **********

	public String getCustomizerClass() {
		return (this.specifiedCustomizerClass != null) ? this.specifiedCustomizerClass : this.defaultCustomizerClass;
	}

	public String getSpecifiedCustomizerClass() {
		return this.specifiedCustomizerClass;
	}

	public void setSpecifiedCustomizerClass(String customizerClass) {
		if (this.valuesAreDifferent(this.specifiedCustomizerClass, customizerClass)) {
			XmlClassReference xmlClassRef = this.getXmlCustomizerClassRefForUpdate();
			this.setSpecifiedCustomizerClass_(customizerClass);
			xmlClassRef.setClassName(customizerClass);
			this.removeXmlCustomizerClassRefIfUnset();
		}
	}

	protected void setSpecifiedCustomizerClass_(String customizerClass) {
		String old = this.specifiedCustomizerClass;
		this.specifiedCustomizerClass = customizerClass;
		this.firePropertyChanged(SPECIFIED_CUSTOMIZER_CLASS_PROPERTY, old, customizerClass);
	}

	protected String buildSpecifiedCustomizerClass() {
		XmlClassReference xmlClassRef = this.getXmlCustomizerClassRef();
		return (xmlClassRef == null) ? null : xmlClassRef.getClassName();
	}

	public String getDefaultCustomizerClass() {
		return this.defaultCustomizerClass;
	}

	protected void setDefaultCustomizerClass(String customizerClass) {
		String old = this.defaultCustomizerClass;
		this.defaultCustomizerClass = customizerClass;
		this.firePropertyChanged(DEFAULT_CUSTOMIZER_CLASS_PROPERTY, old, customizerClass);
	}

	protected String buildDefaultCustomizerClass() {
		JavaEclipseLinkCustomizer javaCustomizer = this.getJavaCustomizerForDefaults();
		return (javaCustomizer == null) ? null : javaCustomizer.getFullyQualifiedCustomizerClass();
	}


	// ********** xml customizer class ref **********

	/**
	 * Return null if the XML class ref does not exists.
	 */
	protected XmlClassReference getXmlCustomizerClassRef() {
		return this.getXmlCustomizerHolder().getCustomizer();
	}

	/**
	 * Build the XML class ref if it does not exist.
	 */
	protected XmlClassReference getXmlCustomizerClassRefForUpdate() {
		XmlClassReference xmlClassRef = this.getXmlCustomizerClassRef();
		return (xmlClassRef != null) ? xmlClassRef : this.buildXmlCustomizerClassRef();
	}

	protected XmlClassReference buildXmlCustomizerClassRef() {
		XmlClassReference ref = OrmFactory.eINSTANCE.createXmlClassReference();
		this.getXmlCustomizerHolder().setCustomizer(ref);
		return ref;
	}

	protected void removeXmlCustomizerClassRefIfUnset() {
		if (this.getXmlCustomizerClassRef().isUnset()) {
			this.removeXmlCustomizerClassRef();
		}
	}

	protected void removeXmlCustomizerClassRef() {
		this.getXmlCustomizerHolder().setCustomizer(null);
	}

	protected JavaResourcePersistentType getResourceCustomizerPersistentType() {
		XmlClassReference customizerClassRef = this.getXmlCustomizerClassRef();
		if (customizerClassRef == null) {
			return null;
		}

		String className = customizerClassRef.getClassName();
		if (className == null) {
			return null;
		}

		return this.getEntityMappings().resolveJavaResourcePersistentType(className);
	}


	// ********** misc **********

	@Override
	public EclipseLinkOrmTypeMapping getParent() {
		return (EclipseLinkOrmTypeMapping) super.getParent();
	}

	protected EclipseLinkOrmTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return this.getTypeMapping().getXmlTypeMapping();
	}

	protected XmlCustomizerHolder getXmlCustomizerHolder() {
		return (XmlCustomizerHolder) this.getXmlTypeMapping();
	}

	protected EclipseLinkJavaTypeMapping getJavaTypeMappingForDefaults() {
		return this.getTypeMapping().getJavaTypeMappingForDefaults();
	}

	protected JavaEclipseLinkCustomizer getJavaCustomizerForDefaults() {
		EclipseLinkJavaTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : (JavaEclipseLinkCustomizer) javaTypeMapping.getCustomizer();
	}

	protected EntityMappings getEntityMappings() {
		return (EntityMappings) this.getMappingFileRoot();
	}

	protected boolean isFor(String typeName) {
		JavaResourcePersistentType customizerType = this.getResourceCustomizerPersistentType();
		return (customizerType != null) && customizerType.getQualifiedName().equals(typeName);
	}

	protected boolean isIn(IPackageFragment packageFragment) {
		JavaResourcePersistentType customizerType = this.getResourceCustomizerPersistentType();
		return (customizerType != null) && customizerType.isIn(packageFragment);
	}

	public char getCustomizerClassEnclosingTypeSeparator() {
		return '$';
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameTypeEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameTypeEdit(IType originalType, String newName) {
		return this.getXmlCustomizerClassRef().createRenameEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.isIn(originalPackage) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.getXmlCustomizerClassRef().createRenamePackageEdit(newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateCustomizerClass(messages);
	}

	protected void validateCustomizerClass(List<IMessage> messages) {
		IJavaProject javaProject = this.getPersistenceUnit().getJpaProject().getJavaProject();
		if (this.getCustomizerClass() != null) {
			if (StringTools.stringIsEmpty(this.getCustomizerClass())) {
				messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								EclipseLinkJpaValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_SPECIFIED,
								new String[] {},
								this,
								this.getTypeMapping().getValidationTextRange()
						)
				);
			} else if (JDTTools.findType(javaProject, this.getCustomizerClass()) == null) {
				messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								EclipseLinkJpaValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_EXIST,
								new String[] { this.getCustomizerClass()},
								this,
								this.getTypeMapping().getValidationTextRange()
						)
				);
			} else if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, this.getCustomizerClass())) {
				messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								EclipseLinkJpaValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_VALID,
								new String[] {this.getCustomizerClass()},
								this,
								this.getTypeMapping().getValidationTextRange()
						)
				);
			} else if (!JDTTools.typeNamedImplementsInterfaceNamed(javaProject, this.getCustomizerClass(), ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME)) {
				messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								EclipseLinkJpaValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER,
								new String[] {this.getCustomizerClass()},
								this,
								this.getTypeMapping().getValidationTextRange()
						)
				);
			}
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		XmlClassReference xmlClassRef = this.getXmlCustomizerClassRef();
		return (xmlClassRef == null) ? null : xmlClassRef.getClassNameTextRange();
	}
}
