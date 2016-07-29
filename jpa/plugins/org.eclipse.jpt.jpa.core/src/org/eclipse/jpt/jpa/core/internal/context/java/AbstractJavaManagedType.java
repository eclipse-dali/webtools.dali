/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;

public abstract class AbstractJavaManagedType<P extends JpaContextModel>
	extends AbstractJavaContextModel<P>
	implements JavaManagedType
{
	protected final JavaResourceType resourceType;

	protected String name;
	protected String simpleName;
	protected String typeQualifiedName;


	protected AbstractJavaManagedType(P parent, JavaResourceType resourceType) {
		super(parent);
		this.resourceType = resourceType;
		this.name = this.resourceType.getTypeBinding().getQualifiedName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setName(this.resourceType.getTypeBinding().getQualifiedName());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setSimpleName(this.buildSimpleName());
		this.setTypeQualifiedName(this.buildTypeQualifiedName());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	protected void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	public TextRange getValidationTextRange() {
		return this.getSelectionTextRange();
	}

	public TextRange getSelectionTextRange() {
		return this.resourceType.getNameTextRange();
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
		return ClassNameTools.simpleName(this.name);
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
		String packageName = this.getPackageName();
		return StringTools.isBlank(packageName) ? this.name : this.name.substring(packageName.length() + 1);
	}


	// ********** misc **********

	@Override
	public IResource getResource() {
		return this.resourceType.getFile();
	}

	public JavaResourceType getJavaResourceType() {
		return this.resourceType;
	}

	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.resourceType.getFile());
	}

	public boolean isFor(String typeName) {
		return ObjectTools.equals(typeName, this.name);
	}

	public boolean isIn(IPackageFragment packageFragment) {
		return ObjectTools.equals(packageFragment.getElementName(), this.getPackageName());
	}

	protected String getPackageName() {
		return this.getJavaResourceType().getTypeBinding().getPackageName();
	}

	public IJavaElement getJavaElement() {
		try {
			return this.getJavaProject().findType(this.name);
		} catch (JavaModelException ex) {
			JptJpaCorePlugin.instance().logError(ex);
			return null;
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
