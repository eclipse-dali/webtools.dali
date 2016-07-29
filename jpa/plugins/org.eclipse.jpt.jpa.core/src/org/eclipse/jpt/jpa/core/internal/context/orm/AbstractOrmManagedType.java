/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.EmptyTextRange;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * specified <code>orm.xml</code> managed type:<ul>

 * <li>Java managed type
 * </ul>
 */
public abstract class AbstractOrmManagedType<P extends EntityMappings>
	extends AbstractOrmXmlContextModel<P>
	implements OrmManagedType
{
	protected XmlManagedType xmlManagedType;

	protected String class_;

	protected String name;
	protected String simpleName;
	protected String typeQualifiedName;

	protected JavaManagedType javaManagedType;


	protected AbstractOrmManagedType(P parent, XmlManagedType xmlManagedType) {
		super(parent);
		this.xmlManagedType = xmlManagedType;
		this.class_ = this.xmlManagedType.getClassName();
		this.name = this.buildName(); 
		// 'javaManagedType' is resolved in the update
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setClass_(this.xmlManagedType.getClassName());
		this.setName(this.buildName());
		this.syncJavaManagedType(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setSimpleName(this.buildSimpleName());
		this.setTypeQualifiedName(this.buildTypeQualifiedName());
		this.updateJavaManagedType(monitor);
	}

	public XmlManagedType getXmlManagedType() {
		return this.xmlManagedType;
	}


	// ********** class **********

	public String getClass_() {
		return this.class_;
	}

	public void setClass(String class_) {
		this.setClass_(class_);
		this.xmlManagedType.setClassName(class_);
	}

	protected void setClass_(String class_) {
		String old = this.class_;
		this.class_ = class_;
		this.firePropertyChanged(CLASS_PROPERTY, old, class_);
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	protected void setName(String name) {
		String old = this.name;
		this.name = name;
		if (this.firePropertyChanged(NAME_PROPERTY, old, name)) {
			// clear out the Java managed type here, it will be rebuilt during "update"
			if (this.javaManagedType != null) {
				this.setJavaManagedType(null);
			}
		}
	}

	protected String buildName() {
		return this.getEntityMappings().qualify(this.class_);		
	}

	protected TextRange getClassTextRange() {
		return this.getValidationTextRange(this.getXmlManagedType().getClassTextRange());
	}


	// ********** simple name **********

	public String getSimpleName(){
		return this.simpleName;
	}

	protected void setSimpleName(String simpleName) {
		String old = this.simpleName;
		this.firePropertyChanged(SIMPLE_NAME_PROPERTY, old, this.simpleName = simpleName);
	}

	protected String buildSimpleName(){
		return StringTools.isBlank(this.name) ? null : ClassNameTools.simpleName(this.name);
	}


	// ********** type-qualified name **********

	public String getTypeQualifiedName() {
		return this.typeQualifiedName;
	}

	protected void setTypeQualifiedName(String typeQualifiedName) {
		String old = this.typeQualifiedName;
		this.firePropertyChanged(TYPE_QUALIFIED_NAME_PROPERTY, old, this.typeQualifiedName = typeQualifiedName);
	}

	protected String buildTypeQualifiedName() {
		String className = this.class_;
		if (className == null) {
			return null;
		}
		int lastPeriod = className.lastIndexOf('.');
		className = (lastPeriod == -1) ? className : className.substring(lastPeriod + 1);
		className = className.replace('$', '.');
		return className;
	}


	// ********** Java managed type **********

	public JavaManagedType getJavaManagedType() {
		return this.javaManagedType;
	}

	protected void setJavaManagedType(JavaManagedType javaManagedType) {
		ManagedType old = this.javaManagedType;
		this.javaManagedType = javaManagedType;
		this.firePropertyChanged(JAVA_MANAGED_TYPE_PROPERTY, old, javaManagedType);
	}

	/**
	 * If the managed type's name changes during <em>update</em>, 
	 * the Java managed type will be cleared out in
	 * {@link #setName(String)}. If we get here and
	 * the Java managed type is present, we can
	 * <em>sync</em> it. In some circumstances it will be obsolete
	 * since the name is changed during update (the class name or
	 * the entity mapping's package affect the name)
	 * @param monitor TODO
	 *
	 * @see #updateJavaManagedType(IProgressMonitor)
	 */
	protected void syncJavaManagedType(IProgressMonitor monitor) {
		if (this.javaManagedType != null) {
			this.javaManagedType.synchronizeWithResourceModel(monitor);
		}
	}

	/**
	 * @see #syncJavaManagedType(IProgressMonitor)
	 */
	protected void updateJavaManagedType(IProgressMonitor monitor) {
		if (this.getName() == null) {
			if (this.javaManagedType != null) {
				this.setJavaManagedType(null);
			}			
		}
		else {
			JavaResourceType resourceType = this.resolveJavaResourceType();
			if (this.javaManagedType == null) {
				this.setJavaManagedType(this.buildJavaManagedType(resourceType));
			}
			else {
				// bug 379051 using == here because it is possible that the names are the same, 
				// but the location has changed: the java resource type has moved from "external" 
				// to part of the jpa project's jpa files. 
				if (this.javaManagedType.getJavaResourceType() == resourceType) {
					this.javaManagedType.update(monitor);
				} else {
					this.setJavaManagedType(this.buildJavaManagedType(resourceType));
				}
			}
		}
	}

	/**
	 * Return null it's an enum; don't build a JavaManagedType
	 * @see #updateJavaManagedType(IProgressMonitor)
	 */
	protected JavaResourceType resolveJavaResourceType() {
		if (this.name == null) {
			return null;
		}
		return (JavaResourceType) this.getJpaProject().getJavaResourceType(this.name, AstNodeType.TYPE);
	}

	protected abstract JavaManagedType buildJavaManagedType(JavaResourceType jrt);


	public JavaResourceType getJavaResourceType() {
		return (this.javaManagedType == null) ? null : this.javaManagedType.getJavaResourceType();
	}

	public TextRange getValidationTextRange() {
		// this should never be null; 
		TextRange textRange = this.getXmlManagedType().getValidationTextRange();
		//*return an Empty text range because validation sometimes run concurrently
		//with the code adding the type mapping to xml; the IDOMNode might not
		//be set when this is called. Brian's batch update changes in 3.2 should
		//fix this problem.  bug 358745
		return (textRange != null) ? textRange : EmptyTextRange.instance();
	}

	public TextRange getFullTextRange() {
		return this.getXmlManagedType().getFullTextRange();
	}

	public boolean containsOffset(int textOffset) {
		return this.getXmlManagedType().containsOffset(textOffset);
	}


	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.classNameTouches(pos)) {
			return this.getCandidateClassNames();
		}
		return null;
	}

	protected Iterable<String> getCandidateClassNames() {
		return JavaProjectTools.getJavaClassNames(this.getJavaProject());
	}

	protected boolean classNameTouches(int pos) {
		return this.getXmlManagedType().classNameTouches(pos);
	}


	//*********** refactoring ***********

	public Iterable<DeleteEdit> createDeleteTypeEdits(IType type) {
		return this.isFor(type.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createDeleteTypeEdit()) :
				IterableTools.<DeleteEdit>emptyIterable();
	}
	
	protected DeleteEdit createDeleteTypeEdit() {
		return this.getXmlManagedType().createDeleteEdit();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameTypeEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}
	
	protected ReplaceEdit createRenameTypeEdit(IType originalType, String newName) {
		return this.getXmlManagedType().createRenameTypeEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.isFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.isIn(originalPackage) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.getXmlManagedType().createRenamePackageEdit(newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateClass(messages);
	}

	protected void validateClass(List<IMessage> messages) {
		if (StringTools.isBlank(this.class_)) {
			messages.add(
				this.buildValidationMessage(
					this.getClassTextRange(),
					JptJpaCoreValidationMessages.MANAGED_TYPE_UNSPECIFIED_CLASS
				)
			);
			return;
		}
		this.validateClassResolves(messages);
	}

	protected void validateClassResolves(List<IMessage> messages) {
		if (this.javaManagedType == null) {
			messages.add(
				this.buildValidationMessage(
					this.getClassTextRange(),
					JptJpaCoreValidationMessages.MANAGED_TYPE_UNRESOLVED_CLASS,
					this.getName()
				)
			);
		}
	}


	// ********** misc **********

	protected EntityMappings getEntityMappings() {
		return this.parent;
	}

	public String getDefaultPackage() {
		return this.getEntityMappings().getDefaultPersistentTypePackage();
	}

	public boolean isFor(String typeName) {
		return ObjectTools.equals(typeName, this.getName());
	}

	public boolean isIn(IPackageFragment packageFragment) {
		String packageName = this.getPackageName();
		if (ObjectTools.equals(packageName, packageFragment.getElementName())) {
			return true;
		}
		return false;
	}

	protected String getPackageName() {
		String className = this.class_;
		if (className == null) {
			return null;
		}
		int lastPeriod = className.lastIndexOf('.');
		return (lastPeriod == -1) ? this.getDefaultPackage() : className.substring(0, lastPeriod);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
