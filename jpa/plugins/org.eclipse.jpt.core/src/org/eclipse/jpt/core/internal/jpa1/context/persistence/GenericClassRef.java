/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Context persistence.xml class reference
 */
public class GenericClassRef
	extends AbstractPersistenceXmlContextNode
	implements ClassRef
{
	// this is null for an "implied" class ref
	protected final XmlJavaClassRef xmlJavaClassRef;

	protected String className;

	protected JavaPersistentType javaPersistentType;


	// ********** construction/initialization **********

	/**
	 * Construct an "specified" class ref; i.e. a class ref with
	 * an explicit entry in the persistence.xml.
	 */
	public GenericClassRef(PersistenceUnit parent, XmlJavaClassRef classRef) {
		this(parent, classRef, classRef.getJavaClass());
	}

	/**
	 * Construct an "implied" class ref; i.e. a class ref without
	 * an explicit entry in the persistence.xml.
	 */
	public GenericClassRef(PersistenceUnit parent, String className) {
		this(parent, null, className);
	}

	protected GenericClassRef(PersistenceUnit parent, XmlJavaClassRef classRef, String className) {
		super(parent);
		this.xmlJavaClassRef = classRef;
		this.initialize(className);
	}

	protected void initialize(String typeName) {
		this.className = typeName;
		this.javaPersistentType = this.buildJavaPersistentType();
	}

	@Override
	public PersistenceUnit getParent() {
		return (PersistenceUnit) super.getParent();
	}
	
	public XmlJavaClassRef getResourceClassRef() {
		return this.xmlJavaClassRef;
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return PersistenceStructureNodes.CLASS_REF_ID;
	}
	
	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public TextRange getSelectionTextRange() {
		return this.isVirtual() ? null : this.xmlJavaClassRef.getSelectionTextRange();
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


	// ********** queries **********

	public boolean isFor(String typeName) {
		return (this.className != null) && this.className.replace('$', '.').equals(typeName);
	}

	public boolean isVirtual() {
		return this.xmlJavaClassRef == null;
	}

	public boolean containsOffset(int textOffset) {
		return this.isNotVirtual() && this.xmlJavaClassRef.containsOffset(textOffset);
	}

	protected boolean isNotVirtual() {
		return ! this.isVirtual();
	}


	// ********** class name **********

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		String old = this.className;
		this.className = className;
		this.xmlJavaClassRef.setJavaClass(className);
		this.firePropertyChanged(CLASS_NAME_PROPERTY, old, className);
	}

	protected void setClassName_(String newClassName) {
		String old = this.className;
		this.className = newClassName;
		this.firePropertyChanged(CLASS_NAME_PROPERTY, old, newClassName);
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

	protected JavaPersistentType buildJavaPersistentType() {
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType();
		return (jrpt == null) ? null : this.buildJavaPersistentType(jrpt);
	}

	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType jrpt) {
		return this.getJpaFactory().buildJavaPersistentType(this, jrpt);
	}

	protected void updateJavaPersistentType() {
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType();
		if (jrpt == null) {
			if (this.javaPersistentType != null) {
				this.javaPersistentType.dispose();
				this.setJavaPersistentType(null);
			}
		} else { 
			if (this.javaPersistentType == null) {
				this.setJavaPersistentType(this.buildJavaPersistentType(jrpt));
			} else {
				this.javaPersistentType.update(jrpt);
			}
		}
	}

	protected JavaResourcePersistentType getJavaResourcePersistentType() {
		return (this.className == null) ? null : this.getJpaProject().getJavaResourcePersistentType(this.className.replace('$', '.'));
	}


	// ********** updating **********

	public void update() {
		this.update(this.xmlJavaClassRef.getJavaClass());
	}

	public void update(String typeName) {
		this.setClassName_(typeName);
		this.updateJavaPersistentType();
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.javaPersistentType != null) {
			this.javaPersistentType.postUpdate();
		}
	}
	
	// ********** XmlContextNode implementation **********

	public TextRange getValidationTextRange() {
		return this.isVirtual() ? null : this.xmlJavaClassRef.getValidationTextRange();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (StringTools.stringIsEmpty(this.className)) {
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

		if (this.javaPersistentType == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,
					new String[] {this.className}, 
					this, 
					this.getValidationTextRange()
				)
			);
			return;
		}

		// 190062 validate Java class only if this is the only reference to it
		boolean validateJavaPersistentType = true;
		for (Iterator<MappingFileRef> stream = this.getPersistenceUnit().mappingFileRefsContaining(this.className); stream.hasNext(); ) {
			validateJavaPersistentType = false;
			MappingFileRef mappingFileRef = stream.next();
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.LOW_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS,
					new String[] {this.className, mappingFileRef.getFileName()},
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
			JptCorePlugin.log(t);
		}
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.className);
	}

}
