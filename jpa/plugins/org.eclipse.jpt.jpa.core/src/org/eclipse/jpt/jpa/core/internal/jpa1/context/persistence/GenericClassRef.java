/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaPersistentTypeDefinition;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextModel;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
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
	extends AbstractPersistenceXmlContextModel<PersistenceUnit>
	implements ClassRef, PersistentType.Parent
{
	/**
	 * This is <code>null</code> for a <em>virtual</em> class ref.
	 */
	protected final XmlJavaClassRef xmlJavaClassRef;

	protected String className;

	/**
	 * The Java managed type corresponding to the ref's class name;
	 * this can be <code>null</code> if the className is invalid or
	 * refers to an enum instead of a class or interface.
	 */
	protected JavaManagedType javaManagedType;

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
		this.initializeJavaManagedType(this.resolveJavaResourceType());
	}

	/**
	 * Construct an <em>virtual</em> class ref; i.e. a class ref without
	 * an explicit entry in the <code>persistence.xml</code>.
	 */
	public GenericClassRef(PersistenceUnit parent, JavaResourceAbstractType resourceType) {
		super(parent);
		this.xmlJavaClassRef = null;
		this.className = resourceType.getTypeBinding().getQualifiedName();
		this.initializeJavaManagedType(resourceType);
	}

	protected void initializeJavaManagedType(JavaResourceAbstractType jrat) {
		this.resourceType = jrat;
		if (this.resourceType != null && this.resourceType.getAstNodeType() == AstNodeType.TYPE) {
			this.javaManagedType = this.buildJavaManagedType((JavaResourceType) this.resourceType);
		}
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		// virtual class refs are matched by name in the persistence unit
		// so no need to sync it here (also, 'xmlJavaClassRef' is null...)
		if (this.isNotVirtual()) {
			this.setClassName_(this.xmlJavaClassRef.getJavaClass());
		}
		if (this.javaManagedType != null) {
			this.javaManagedType.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.updateJavaManagedType();
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		JavaPersistentType javaPersistentType = this.getJavaPersistentType();
		if (javaPersistentType != null) {
			javaPersistentType.addRootStructureNodesTo(jpaFile, rootStructureNodes);
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


	// ********** Java managed type **********

	public JavaManagedType getJavaManagedType() {
		return this.javaManagedType;
	}

	protected void setJavaManagedType(JavaManagedType managedType) {
		ManagedType old = this.javaManagedType;
		this.javaManagedType = managedType;
		this.firePropertyChanged(JAVA_MANAGED_TYPE_PROPERTY, old, managedType);
	}

	protected void updateJavaManagedType() {
		this.resourceType = this.resolveJavaResourceType();
		if ((this.resourceType == null) || (this.resourceType.getAstNodeType() != AstNodeType.TYPE)) {
			if (this.javaManagedType != null) {
				this.setJavaManagedType(null);
			}
		} else {
			JavaResourceType jrt = (JavaResourceType) this.resourceType;
			JavaManagedTypeDefinition def = this.getJavaManagedTypeDefinition(jrt);
			if (this.javaManagedType == null) {
				this.setJavaManagedType(this.buildJavaManagedType(jrt, def));
			} else {
				if ((this.javaManagedType.getJavaResourceType() == jrt) && (this.javaManagedType.getManagedTypeType() == def.getManagedTypeType())) {
					this.javaManagedType.update();
				} else {
					this.setJavaManagedType(this.buildJavaManagedType(jrt, def));
				}
			}
		}
	}

	public JavaPersistentType getJavaPersistentType() {
		return this.javaManagedType == null ? null : 
			(this.javaManagedType.getManagedTypeType() == PersistentType.class) ? (JavaPersistentType) this.javaManagedType : null;
	}

	protected JavaResourceAbstractType resolveJavaResourceType() {
		String javaClassName = this.getJavaClassName();
		return (javaClassName == null) ? null : this.getJpaProject().getJavaResourceType(javaClassName);
	}

	protected JavaManagedType buildJavaManagedType(JavaResourceType jrt) {
		return this.buildJavaManagedType(jrt, this.getJavaManagedTypeDefinition(jrt));
	}

	protected JavaManagedType buildJavaManagedType(JavaResourceType jrt, JavaManagedTypeDefinition managedTypeDefinition) {
		return managedTypeDefinition.buildContextManagedType(this, jrt, this.getJpaFactory());
	}

	protected Iterable<JavaManagedTypeDefinition> getJavaManagedTypeDefinitions() {
		return this.getJpaPlatform().getJavaManagedTypeDefinitions();
	}

	protected JavaManagedTypeDefinition getJavaManagedTypeDefinition(JavaResourceType jrt) {
		for (JavaManagedTypeDefinition managedTypeDefinition : this.getJavaManagedTypeDefinitions()) {
			if (jrt.isAnnotatedWithAnyOf(managedTypeDefinition.getAnnotationNames(this.getJpaProject()))) {
				return managedTypeDefinition;
			}
		}
		return JavaPersistentTypeDefinition.instance();
	}


	// ********** misc **********

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

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getJavaClassName());
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<ClassRef> getStructureType() {
		return ClassRef.class;
	}

	public Iterable<JpaStructureNode> getStructureChildren() {
		return EmptyIterable.instance();
	}

	public int getStructureChildrenSize() {
		return 0;
	}

	public boolean containsOffset(int textOffset) {
		return this.isNotVirtual() && this.xmlJavaClassRef.containsOffset(textOffset);
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public TextRange getFullTextRange() {
		return this.isVirtual() ? null : this.xmlJavaClassRef.getFullTextRange();
	}

	public TextRange getSelectionTextRange() {
		return this.isVirtual() ? null : this.xmlJavaClassRef.getJavaClassTextRange();
	}


	// ********** PersistentType.Parent implementation **********

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
				IterableTools.singletonIterable(this.createDeleteEdit()) :
				IterableTools.<DeleteEdit>emptyIterable();
	}

	protected DeleteEdit createDeleteEdit() {
		return this.xmlJavaClassRef.createDeleteEdit();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		if (this.isVirtual()) {
			throw new IllegalStateException();
		}
		return this.isFor(originalType) ?
				IterableTools.singletonIterable(this.createReplaceEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createReplaceEdit(IType originalType, String newName) {
		return this.xmlJavaClassRef.createRenameEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.isVirtual()) {
			throw new IllegalStateException();
		}
		return this.isFor(originalType) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.isVirtual()) {
			throw new IllegalStateException();
		}
		return this.isInPackage(originalPackage) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
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
				this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS
				)
			);
			return;
		}

		if (this.resourceType == null) {
			messages.add(
				this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,
					this.getJavaClassName()
				)
			);
			return;
		}

		if (this.isNotVirtual()) {
			if (this.resourceType.getAstNodeType() == AstNodeType.ENUM) {
				messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_ENUM,
						this.getJavaClassName()
					)
				);
				return;
			}
	
			if (this.resourceType.getAstNodeType() == AstNodeType.TYPE && this.resourceType.getTypeBinding().isInterface()) {
				messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_INTERFACE,
						this.getJavaClassName()
					)
				);
				return;
			}
		}

		if (this.javaManagedType == null) {
			return;
		}
		// 190062 validate Java class only if this is the only reference to it
		// i.e. the persistence.xml ref is the only ref - none of the mapping
		// files reference the same class
		boolean validateJavaManagedType = true;
		for (MappingFileRef mappingFileRef : this.getPersistenceUnit().getMappingFileRefsContaining(this.getJavaClassName())) {
			validateJavaManagedType = false;
			messages.add(
				this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaCoreValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS,
					this.getJavaClassName(),
					mappingFileRef.getFileName()
				)
			);
		}

		if (validateJavaManagedType) {
			this.validateJavaManagedType(messages, reporter);
		}
	}

	protected void validateJavaManagedType(List<IMessage> messages, IReporter reporter) {
		try {
			this.javaManagedType.validate(messages, reporter);
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
