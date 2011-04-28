/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.XmlIdClassContainer;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> ID class reference
 */
public class GenericOrmIdClassReference
	extends AbstractXmlContextNode
	implements OrmIdClassReference
{
	protected final Owner owner;

	protected String specifiedIdClassName;
	protected String defaultIdClassName;
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
		this.updateIdClass();
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

	/**
	 * We clear out {@link #idClass} here because we cannot compare its name
	 * to the specified name, since it may have been prefixed by the entity
	 * mappings package.
	 */
	protected void setSpecifiedIdClassName_(String name) {
		String old = this.specifiedIdClassName;
		this.specifiedIdClassName = name;
		if (this.firePropertyChanged(SPECIFIED_ID_CLASS_NAME_PROPERTY, old, name)) {
			// clear out the Java type here, it will be rebuilt during "update"
			if (this.idClass != null) {
				this.idClass.dispose();
				this.setIdClass(null);
			}
		}
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
	 * If the specified ID class name changes during
	 * <em>sync</em>, the ID class will be cleared out in
	 * {@link #setSpecifiedIdClassName_(String)}. If we get here and
	 * the ID class is still present, we can
	 * <code>sync</code> it. Of course, it might be still obsolete if the
	 * entity mappings's package has changed....
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
		JavaResourcePersistentType resourceIdClass = this.resolveJavaResourceIdClass();
		if (resourceIdClass == null) {
			if (this.idClass != null) {
				this.idClass.dispose();
				this.setIdClass(null);
			}
		} else {
			if (this.idClass == null) {
				this.setIdClass(this.buildIdClass(resourceIdClass));
			} else {
				if (this.idClass.getResourcePersistentType() == resourceIdClass) {
					this.idClass.update();
				} else {
					this.idClass.dispose();
					this.setIdClass(this.buildIdClass(resourceIdClass));
				}
			}
		}
	}

	// TODO I'm not sure we should be go to the entity mappings to resolve
	// our name if it is taken from the Java ID class reference...
	protected JavaResourcePersistentType resolveJavaResourceIdClass() {
		String idClassName = this.getIdClassName();
		if (idClassName == null) {
			return null;
		}
		JavaResourcePersistentType jrpt = this.getEntityMappings().resolveJavaResourcePersistentType(idClassName);
		return (jrpt == null) ? null : (jrpt.isMapped() ? null : jrpt);
	}

	protected JavaPersistentType buildIdClass(JavaResourcePersistentType resourceIdClass) {
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
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameEdit(IType originalType, String newName) {
		return this.getXmlIdClassRef().createRenameEdit(originalType, newName);
	}

	protected boolean isFor(String typeName) {
		return (this.idClass != null) && this.idClass.isFor(typeName);
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
		IJavaProject javaProject = getJpaProject().getJavaProject();
		if (this.isSpecified()) {
			JavaResourcePersistentType jrpt = getJpaProject().getJavaResourcePersistentType(this.getIdClassName());
			if ((jrpt != null) && (jrpt.isMapped())) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_VALID,
								new String[] {jrpt.getName()}, 
								this,
								this.getValidationTextRange()
						)
				);
			} else if (StringTools.stringIsEmpty(this.getIdClassName())) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_NAME_EMPTY,
								new String[] {}, 
								this,
								this.getValidationTextRange()
						)
				);
			} else if (JDTTools.findType(javaProject, this.getIdClassName()) == null) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_EXIST,
								new String[] { this.getIdClassName()},
								this,
								this.getValidationTextRange()
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
		XmlClassReference xmlIdClassRef = this.getXmlIdClassRef();
		return (xmlIdClassRef == null) ? null : xmlIdClassRef.getClassNameTextRange();
	}
}
