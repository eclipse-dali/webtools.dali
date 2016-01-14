/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkOrmCustomizer
	extends AbstractOrmXmlContextModel<EclipseLinkOrmTypeMapping>
	implements EclipseLinkCustomizer, TypeRefactoringParticipant
{
	protected String specifiedCustomizerClass;
	protected String defaultCustomizerClass;
	protected String fullyQualifiedCustomizerClass;


	public EclipseLinkOrmCustomizer(EclipseLinkOrmTypeMapping parent) {
		super(parent);
		this.specifiedCustomizerClass = this.buildSpecifiedCustomizerClass();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedCustomizerClass_(this.buildSpecifiedCustomizerClass());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultCustomizerClass(this.buildDefaultCustomizerClass());
		this.setFullyQualifiedCustomizerClass(this.buildFullyQualifiedCustomizerClass());
	}


	// ********** customizer class **********

	public String getCustomizerClass() {
		return (this.specifiedCustomizerClass != null) ? this.specifiedCustomizerClass : this.defaultCustomizerClass;
	}

	public String getSpecifiedCustomizerClass() {
		return this.specifiedCustomizerClass;
	}

	public void setSpecifiedCustomizerClass(String customizerClass) {
		if (ObjectTools.notEquals(this.specifiedCustomizerClass, customizerClass)) {
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
		EclipseLinkJavaCustomizer javaCustomizer = this.getJavaCustomizerForDefaults();
		return (javaCustomizer == null) ? null : javaCustomizer.getFullyQualifiedCustomizerClass();
	}

	public String getFullyQualifiedCustomizerClass() {
		return this.fullyQualifiedCustomizerClass;
	}

	protected void setFullyQualifiedCustomizerClass(String customizerClass) {
		String old = this.fullyQualifiedCustomizerClass;
		this.fullyQualifiedCustomizerClass = customizerClass;
		this.firePropertyChanged(FULLY_QUALIFIED_CUSTOMIZER_CLASS_PROPERTY, old, customizerClass);
	}

	protected String buildFullyQualifiedCustomizerClass() {
		return (this.specifiedCustomizerClass == null) ?
			//this is the fully qualified java customizer class name
			this.defaultCustomizerClass :
			this.getEntityMappings().qualify(this.specifiedCustomizerClass);
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

	protected JavaResourceAbstractType getResourceCustomizerType() {
		if (this.fullyQualifiedCustomizerClass == null) {
			return null;
		}
		return this.getJpaProject().getJavaResourceType(this.fullyQualifiedCustomizerClass);
	}


	// ********** misc **********

	protected EclipseLinkOrmTypeMapping getTypeMapping() {
		return this.parent;
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

	protected EclipseLinkJavaCustomizer getJavaCustomizerForDefaults() {
		EclipseLinkJavaTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : (EclipseLinkJavaCustomizer) javaTypeMapping.getCustomizer();
	}

	protected EntityMappings getEntityMappings() {
		return this.getMappingFileRoot();
	}

	protected boolean isFor(String typeName) {
		JavaResourceAbstractType customizerType = this.getResourceCustomizerType();
		return (customizerType != null) && customizerType.getTypeBinding().getQualifiedName().equals(typeName);
	}

	protected boolean isIn(IPackageFragment packageFragment) {
		JavaResourceAbstractType customizerType = this.getResourceCustomizerType();
		return (customizerType != null) && customizerType.isIn(packageFragment);
	}

	public char getCustomizerClassEnclosingTypeSeparator() {
		return '$';
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.getXmlCustomizerClassRef() != null && this.isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameTypeEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenameTypeEdit(IType originalType, String newName) {
		return this.getXmlCustomizerClassRef().createRenameEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.getXmlCustomizerClassRef() != null && this.isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.getXmlCustomizerClassRef() != null && this.isIn(originalPackage) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
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
		if (this.getCustomizerClass() == null) {
			return;
		}
		if (StringTools.isBlank(this.getCustomizerClass())) {
			messages.add(
					this.buildValidationMessage(
							this.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_SPECIFIED
					)
			);
			return;
		}

		IType customizerJdtType = JavaProjectTools.findType(this.getJavaProject(), this.getFullyQualifiedCustomizerClass());
		if (customizerJdtType == null) {
			messages.add(
					this.buildValidationMessage(
							this.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_EXIST,
							this.getFullyQualifiedCustomizerClass()
					)
			);
			return;
		}
		if (!TypeTools.hasPublicZeroArgConstructor(customizerJdtType)) {
			messages.add(
					this.buildValidationMessage(
							this.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_VALID,
							this.getFullyQualifiedCustomizerClass()
					)
			);
		}
		if (!TypeTools.isSubTypeOf(customizerJdtType, ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME)) {
			messages.add(
					this.buildValidationMessage(
							this.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER,
							this.getFullyQualifiedCustomizerClass()
					)
			);
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

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.customizerClassTouches(pos)) {
			return this.getCandidateClassNames();
		}
		return null;
	}

	protected Iterable<String> getCandidateClassNames() {
		return JavaProjectTools.getJavaClassNames(this.getJavaProject());
	}

	protected boolean customizerClassTouches(int pos) {
		return this.getXmlCustomizerClassRef()== null? false : this.getXmlCustomizerClassRef().classNameTouches(pos);
	}
}
