/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import java.util.Collection;
import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>class</code> element
 */
public class GenericClassRef
	extends AbstractPersistenceXmlContextNode
	implements ClassRef
{
	/**
	 * This is <code>null</code> for a <em>virtual</em> class ref.
	 */
	protected final XmlJavaClassRef xmlJavaClassRef;

	protected String className;

	/**
	 * The Java persistent type corresponding to the ref's class name;
	 * this can be <code>null</code> if the className is invalid or
	 * refers to an enum instead of a class or interface.
	 */
	protected JavaPersistentType javaPersistentType;

	/**
	 * Hold on to this for validation if the resourceType is not of type {@link AstNodeType#TYPE}
	 */
	protected JavaResourceAbstractType resourceType;

	// ********** constructors **********

	/**
	 * Construct a <em>specified</em> class ref; i.e. a class ref with
	 * an explicit entry in the <code>persistence.xml</code>.
	 */
	public GenericClassRef(PersistenceUnit parent, XmlJavaClassRef xmlJavaClassRef) {
		super(parent);
		this.xmlJavaClassRef = xmlJavaClassRef;
		this.className = xmlJavaClassRef.getJavaClass();
		JavaResourceAbstractType resourceType = this.resolveJavaResourceType();
		this.initializeJavaPersistentType(resourceType);
	}

	/**
	 * Construct an <em>virtual</em> class ref; i.e. a class ref without
	 * an explicit entry in the <code>persistence.xml</code>.
	 */
	public GenericClassRef(PersistenceUnit parent, JavaResourceAbstractType resourceType) {
		super(parent);
		this.xmlJavaClassRef = null;
		this.className = resourceType.getTypeBinding().getQualifiedName();
		this.initializeJavaPersistentType(resourceType);
	}

	protected void initializeJavaPersistentType(JavaResourceAbstractType resourceType) {
		this.resourceType = resourceType;
		if (this.resourceType.getKind() == Kind.TYPE) {
			this.javaPersistentType = this.buildJavaPersistentType((JavaResourceType) this.resourceType);
		}
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		// virtual class refs are matched by name in the persistence unit
		// so no need to sync it here (also, 'xmlJavaClassRef' is null...)
		if (this.isNotVirtual()) {
			// the name probably never changes...
			this.setClassName_(this.xmlJavaClassRef.getJavaClass());
		}
		if (this.javaPersistentType != null) {
			this.javaPersistentType.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.updateJavaPersistentType();
	}

	public void gatherRootStructureNodes(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		if (this.javaPersistentType != null) {
			this.javaPersistentType.gatherRootStructureNodes(jpaFile, rootStructureNodes);
		}
	}


	// ********** class name **********

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		if (this.isVirtual()) {
			throw new IllegalStateException("The name of a virtual class ref cannot be changed: " + this); //$NON-NLS-1$
		}
		this.setClassName_(className);
		this.xmlJavaClassRef.setJavaClass(className);
	}

	protected void setClassName_(String className) {
		String old = this.className;
		this.className = className;
		this.firePropertyChanged(CLASS_NAME_PROPERTY, old, className);
	}

	/**
	 * Nested classes will be qualified with a '$'; the Java name is qualified
	 * with a '.'. Like <code>className</code>, this can be <code>null</code>.
	 */
	protected String getJavaClassName() {
		return StringTools.isBlank(this.className) ? null : this.className.replace('$', '.');
	}

	public JavaResourceAbstractType getJavaResourceType() {
		return this.resourceType;
	}


	// ********** java persistent type **********

	public JavaPersistentType getJavaPersistentType() {
		return this.javaPersistentType;
	}

	protected void setJavaPersistentType(JavaPersistentType javaPersistentType) {
		JavaPersistentType old = this.javaPersistentType;
		this.javaPersistentType = javaPersistentType;
		this.firePropertyChanged(JAVA_PERSISTENT_TYPE_PROPERTY, old, javaPersistentType);
	}

	protected void updateJavaPersistentType() {
		this.resourceType = this.resolveJavaResourceType();
		if (this.resourceType == null || this.resourceType.getKind() != Kind.TYPE) {
			if (this.javaPersistentType != null) {
				this.javaPersistentType.dispose();
				this.setJavaPersistentType(null);
			}
		} else {
			JavaResourceType jrt = (JavaResourceType) this.resourceType;
			if (this.javaPersistentType == null) {
				this.setJavaPersistentType(this.buildJavaPersistentType(jrt));
			} else {
				if (this.javaPersistentType.getJavaResourceType() == jrt) {
					this.javaPersistentType.update();
				} else {
					this.javaPersistentType.dispose();
					this.setJavaPersistentType(this.buildJavaPersistentType(jrt));
				}
			}
		}
	}

	protected JavaResourceAbstractType resolveJavaResourceType() {
		String javaClassName = this.getJavaClassName();
		return (javaClassName == null) ? null : this.getJpaProject().getJavaResourceType(javaClassName);
	}

	protected JavaPersistentType buildJavaPersistentType(JavaResourceType jrt) {
		return this.getJpaFactory().buildJavaPersistentType(this, jrt);
	}


	// ********** misc **********

	@Override
	public PersistenceUnit getParent() {
		return (PersistenceUnit) super.getParent();
	}

	public XmlJavaClassRef getXmlClassRef() {
		return this.xmlJavaClassRef;
	}

	protected boolean isFor(IType type) {
		return this.isFor(type.getFullyQualifiedName('.'));
	}

	public boolean isFor(String typeName) {
		return ObjectTools.equals(typeName, this.getJavaClassName());
	}

	protected boolean isInPackage(IPackageFragment packageFragment) {
		return ObjectTools.equals(this.getPackageName(), packageFragment.getElementName());
	}

	protected String getPackageName() {
		int lastPeriod = this.className.lastIndexOf('.');
		return (lastPeriod == -1) ? null : this.className.substring(0, lastPeriod);
	}

	public boolean isVirtual() {
		return this.xmlJavaClassRef == null;
	}

	protected boolean isNotVirtual() {
		return ! this.isVirtual();
	}

	public boolean containsOffset(int textOffset) {
		return this.isNotVirtual() && this.xmlJavaClassRef.containsOffset(textOffset);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getJavaClassName());
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<ClassRef> getType() {
		return ClassRef.class;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public TextRange getSelectionTextRange() {
		return this.isVirtual() ? null : this.xmlJavaClassRef.getJavaClassTextRange();
	}

	public void dispose() {
		if (this.javaPersistentType != null) {
			this.javaPersistentType.dispose();
		}
	}


	// ********** PersistentType.Owner implementation **********

	public AccessType getOverridePersistentTypeAccess() {
		// no access type at this level overrides any local access type specification
		return null;
	}

	public AccessType getDefaultPersistentTypeAccess() {
		return this.getPersistenceUnit().getDefaultAccess();
	}


	//*********** refactoring ***********

	public Iterable<DeleteEdit> createDeleteTypeEdits(final IType type) {
		if (this.isVirtual()) {
			throw new IllegalStateException();
		}
		return this.isFor(type) ?
				new SingleElementIterable<DeleteEdit>(this.createDeleteEdit()) :
				EmptyIterable.<DeleteEdit>instance();
	}

	protected DeleteEdit createDeleteEdit() {
		return this.xmlJavaClassRef.createDeleteEdit();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		if (this.isVirtual()) {
			throw new IllegalStateException();
		}
		return this.isFor(originalType) ?
				new SingleElementIterable<ReplaceEdit>(this.createReplaceEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createReplaceEdit(IType originalType, String newName) {
		return this.xmlJavaClassRef.createRenameEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.isVirtual()) {
			throw new IllegalStateException();
		}
		return this.isFor(originalType) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.isVirtual()) {
			throw new IllegalStateException();
		}
		return this.isInPackage(originalPackage) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.xmlJavaClassRef.createRenamePackageEdit(newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (StringTools.isBlank(this.className)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS,
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}

		if (this.resourceType == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,
					new String[] {this.getJavaClassName()},
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}

		if (this.isNotVirtual()) {
			if (this.resourceType.getKind() == Kind.ENUM) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_ENUM,
						new String[] {this.getJavaClassName()},
						this,
						this.getValidationTextRange()
					)
				);
				return;
			}
	
			if (this.resourceType.getKind() == Kind.TYPE && this.resourceType.getTypeBinding().isInterface()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_INTERFACE,
						new String[] {this.getJavaClassName()},
						this,
						this.getValidationTextRange()
					)
				);
				return;
			}
		}

		if (this.javaPersistentType == null) {
			return;
		}
		// 190062 validate Java class only if this is the only reference to it
		// i.e. the persistence.xml ref is the only ref - none of the mapping
		// files reference the same class
		boolean validateJavaPersistentType = true;
		for (MappingFileRef mappingFileRef : this.getPersistenceUnit().getMappingFileRefsContaining(this.getJavaClassName())) {
			validateJavaPersistentType = false;
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.LOW_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS,
					new String[] {this.getJavaClassName(), mappingFileRef.getFileName()},
					this,
					this.getValidationTextRange()
				)
			);
		}

		if (validateJavaPersistentType) {
			this.validateJavaPersistentType(messages, reporter);
		}
	}

	protected void validateJavaPersistentType(List<IMessage> messages, IReporter reporter) {
		try {
			this.javaPersistentType.validate(messages, reporter);
		} catch (Throwable t) {
			JptJpaCorePlugin.instance().logError(t);
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getPersistenceUnit().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		return this.isVirtual() ? null : this.xmlJavaClassRef.getJavaClassTextRange();
	}
}
