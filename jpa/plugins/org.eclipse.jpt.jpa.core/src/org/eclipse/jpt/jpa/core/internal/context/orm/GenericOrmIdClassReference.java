/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.XmlIdClassContainer;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> ID class reference
 */
public class GenericOrmIdClassReference
	extends AbstractJpaContextModel
	implements OrmIdClassReference
{
	protected final Owner owner;

	protected String specifiedIdClassName;
	protected String defaultIdClassName;
	protected String fullyQualifiedIdClassName;
	protected JavaPersistentType idClass;


	public GenericOrmIdClassReference(OrmTypeMapping parent, Owner owner) {
		super(parent);
		this.owner = owner;
		this.specifiedIdClassName = this.buildSpecifiedIdClassName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedIdClassName_(this.buildSpecifiedIdClassName());
		// sync the id class *after* we have the specified name
		this.syncIdClass();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultIdClassName(this.buildDefaultIdClassName());
		this.setFullyQualifiedIdClassName(this.buildFullyQualifiedIdClassName());
		// update the id class *after* we have the fully qualified name
		this.updateIdClass();
	}


	// ********** fully-qualified id class name **********

	public String getFullyQualifiedIdClassName() {
		return this.fullyQualifiedIdClassName;
	}

	/**
	 * We clear out {@link #idClass} here because it needs to be rebuilt, the
	 * fully qualified name has changed. 
	 */
	protected void setFullyQualifiedIdClassName(String name) {
		String old = this.fullyQualifiedIdClassName;
		this.fullyQualifiedIdClassName = name;
		if (this.firePropertyChanged(FULLY_QUALIFIED_ID_CLASS_PROPERTY, old, name)) {
			// clear out the Java id class here, it will be rebuilt during "update"
			if (this.idClass != null) {
				this.setIdClass(null);
			}
		}
	}

	protected String buildFullyQualifiedIdClassName() {
		return (this.specifiedIdClassName == null) ?
			//this is the fully qualified java id class name
			this.defaultIdClassName :
			this.getEntityMappings().qualify(this.specifiedIdClassName);
	}


	// ********** id class name **********

	public String getIdClassName() {
		return (this.specifiedIdClassName != null) ? this.specifiedIdClassName : this.defaultIdClassName;
	}

	public String getSpecifiedIdClassName() {
		return this.specifiedIdClassName;
	}

	public void setSpecifiedIdClassName(String name) {
		if (this.valuesAreDifferent(this.specifiedIdClassName, name)) {
			XmlClassReference xmlClassRef = this.getXmlIdClassRefForUpdate();
			this.setSpecifiedIdClassName_(name);
			xmlClassRef.setClassName(name);
			this.removeXmlIdClassRefIfUnset();
		}
	}

	protected void setSpecifiedIdClassName_(String name) {
		String old = this.specifiedIdClassName;
		this.specifiedIdClassName = name;
		this.firePropertyChanged(SPECIFIED_ID_CLASS_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedIdClassName() {
		XmlClassReference xmlIdClassRef = this.getXmlIdClassRef();
		return (xmlIdClassRef == null) ? null : xmlIdClassRef.getClassName();
	}

	public String getDefaultIdClassName() {
		return this.defaultIdClassName;
	}

	protected void setDefaultIdClassName(String name) {
		String old = this.defaultIdClassName;
		this.defaultIdClassName = name;
		this.firePropertyChanged(DEFAULT_ID_CLASS_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultIdClassName() {
		JavaIdClassReference javaRef = this.owner.getJavaIdClassReferenceForDefaults();
		return (javaRef == null) ? null : javaRef.getFullyQualifiedIdClassName();
	}

	public boolean isSpecified() {
		return this.getIdClassName() != null;
	}


	// ********** xml id class ref **********

	/**
	 * Return null if the XML class ref does not exists.
	 */
	protected XmlClassReference getXmlIdClassRef() {
		return this.getXmlIdClassContainer().getIdClass();
	}

	/**
	 * Build the XML class ref if it does not exist.
	 */
	protected XmlClassReference getXmlIdClassRefForUpdate() {
		XmlClassReference xmlClassRef = this.getXmlIdClassRef();
		return (xmlClassRef != null) ? xmlClassRef : this.buildXmlIdClassRef();
	}

	protected XmlClassReference buildXmlIdClassRef() {
		XmlClassReference ref = OrmFactory.eINSTANCE.createXmlClassReference();
		this.getXmlIdClassContainer().setIdClass(ref);
		return ref;
	}

	protected void removeXmlIdClassRefIfUnset() {
		if (this.getXmlIdClassRef().isUnset()) {
			this.removeXmlIdClassRef();
		}
	}

	protected void removeXmlIdClassRef() {
		this.getXmlIdClassContainer().setIdClass(null);
	}


	// ********** id class **********

	public JavaPersistentType getIdClass() {
		return this.idClass;
	}

	protected void setIdClass(JavaPersistentType idClass) {
		JavaPersistentType old = this.idClass;
		this.idClass = idClass;
		this.firePropertyChanged(ID_CLASS_PROPERTY, old, idClass);
	}

	/**
	 * If the fully qualified ID class name changes during
	 * <em>update</em>, the ID class will be cleared out in
	 * {@link #setFullyQualifiedIdClassName(String)}. If we get here and
	 * the ID class is still present, we can
	 * <code>sync</code> it. In some circumstances it will be obsolete
	 * since the name is changed during <em>update</em> (the id class name and
	 * the entity mapping's package affect the fully qualified name)
	 *
	 * @see #updateIdClass()
	 */
	protected void syncIdClass() {
		if (this.idClass != null) {
			this.idClass.synchronizeWithResourceModel();
		}
	}

	/**
	 * @see #syncIdClass()
	 */
	protected void updateIdClass() {
		if (this.fullyQualifiedIdClassName == null) {
			if (this.idClass != null) {
				this.setIdClass(null);
			}
		} else {
			if (this.idClass == null) {
				this.setIdClass(this.buildIdClass());
			} else {
				if (this.idClass.getName().equals(this.fullyQualifiedIdClassName)) {
					this.idClass.update();
				} else {
					this.setIdClass(this.buildIdClass());
				}
			}
		}
	}

	protected JavaResourceType resolveJavaResourceIdClass() {
		if (this.fullyQualifiedIdClassName == null) {
			return null;
		}
		JavaResourceType jrt = (JavaResourceType) this.getJpaProject().getJavaResourceType(this.fullyQualifiedIdClassName, AstNodeType.TYPE);
		return jrt == null || jrt.isAnnotatedWithAnyOf(getJpaProject().getTypeMappingAnnotationNames()) ? null : jrt;
	}

	protected JavaPersistentType buildIdClass() {
		JavaResourceType jrt = this.resolveJavaResourceIdClass();
		return jrt != null ? this.buildIdClass(jrt) : null;
	}

	protected JavaPersistentType buildIdClass(JavaResourceType resourceIdClass) {
		return this.getJpaFactory().buildJavaPersistentType(this, resourceIdClass);
	}

	public char getIdClassEnclosingTypeSeparator() {
		return '$';
	}


	// ********** misc **********

	@Override
	public OrmTypeMapping getParent() {
		return (OrmTypeMapping) super.getParent();
	}

	protected OrmTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected OrmPersistentType getPersistentType() {
		return this.getTypeMapping().getPersistentType();
	}

	protected XmlIdClassContainer getXmlIdClassContainer() {
		return this.owner.getXmlIdClassContainer();
	}

	protected EntityMappings getEntityMappings() {
		return (EntityMappings) this.getMappingFileRoot();
	}


	// ********** PersistentType.Owner implementation **********

	public AccessType getOverridePersistentTypeAccess() {
		return this.getPersistentType().getAccess();
	}

	public AccessType getDefaultPersistentTypeAccess() {
		// this shouldn't be needed, since we've specified an override access, but just to be safe ...
		return this.getPersistentType().getAccess();
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return (this.getXmlIdClassRef() != null) && this.isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenameEdit(IType originalType, String newName) {
		return this.getXmlIdClassRef().createRenameEdit(originalType, newName);
	}

	protected boolean isFor(String typeName) {
		return (this.idClass != null) && this.idClass.isFor(typeName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return (this.getXmlIdClassRef() != null) && this.isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return (this.getXmlIdClassRef() != null) && this.isIn(originalPackage) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.getXmlIdClassRef().createRenamePackageEdit(newName);
	}

	protected boolean isIn(IPackageFragment originalPackage) {
		return (this.idClass != null) && this.idClass.isIn(originalPackage);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// most validation is done "holistically" from the type mapping level
		this.validateIdClass(messages, reporter);
	}
	
	protected void validateIdClass(List<IMessage> messages, IReporter reporter) {
		if (this.isSpecified()) {
			if (StringTools.isBlank(this.getIdClassName())) {
				messages.add(
						this.buildValidationMessage(
								this.getValidationTextRange(),
								JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NAME_EMPTY
						)
				);
				return;
			} 
			IType idClassJdtType = JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedIdClassName());
			if (idClassJdtType == null) {
				messages.add(
						this.buildValidationMessage(
								this.getValidationTextRange(),
								JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_EXIST,
								this.getFullyQualifiedIdClassName()
						)
				);
				return;
			}
	
			JavaResourceType jrt = this.getIdClassJavaResourceType();
			if (jrt != null) {

				if (!jrt.isPublic()) {
					messages.add(
							this.buildValidationMessage(
									this.getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_PUBLIC,
									jrt.getTypeBinding().getQualifiedName()
							)
					);
				}

				if (!JDTTools.typeIsSubType(this.getJavaProject(), jrt.getTypeBinding().getQualifiedName(), JDTTools.SERIALIZABLE_CLASS_NAME)) {
					messages.add(
							this.buildValidationMessage(
									this.getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_IMPLEMENT_SERIALIZABLE,
									jrt.getTypeBinding().getQualifiedName()
							)
					);
				}

				if (!jrt.hasEqualsMethod()) {
					messages.add(
							this.buildValidationMessage(
									this.getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_EQUALS_METHOD,
									jrt.getTypeBinding().getQualifiedName()
							)
					);
				}

				if (!jrt.hasHashCodeMethod()) {
					messages.add(
							this.buildValidationMessage(
									this.getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_HASHCODE_METHOD,
									jrt.getTypeBinding().getQualifiedName()
							)
					);
				}
			}
			
		}
	}

	protected JavaResourceType getIdClassJavaResourceType() {
		return (JavaResourceType) getEntityMappings().resolveJavaResourceType(this.getIdClassName(), AstNodeType.TYPE);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		XmlClassReference xmlIdClassRef = this.getXmlIdClassRef();
		return (xmlIdClassRef == null) ? null : xmlIdClassRef.getClassNameTextRange();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.idCLassNameTouches(pos)) {
			return this.getCandidateIdClassNames();
		}
		return null;
	}
	
	protected Iterable<String> getCandidateIdClassNames() {
		return MappingTools.getSortedJavaClassNames(this.getJavaProject());
	}

	protected boolean idCLassNameTouches(int pos) {
		return this.getXmlIdClassRef() == null ? false : this.getXmlIdClassRef().classNameTouches(pos);
	}
}
