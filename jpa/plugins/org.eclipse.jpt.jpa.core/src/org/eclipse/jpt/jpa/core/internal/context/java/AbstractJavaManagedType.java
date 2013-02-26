/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.core.resources.IResource;
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


	protected AbstractJavaManagedType(P parent, JavaResourceType resourceType) {
		super(parent);
		this.resourceType = resourceType;
		this.name = this.resourceType.getTypeBinding().getQualifiedName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName(this.resourceType.getTypeBinding().getQualifiedName());
	}

	@Override
	public void update() {
		super.update();
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public String getSimpleName(){
		return ClassNameTools.simpleName(this.name);
	}

	public String getTypeQualifiedName() {
		String packageName = this.getPackageName();
		return StringTools.isBlank(packageName) ? this.name : this.name.substring(packageName.length() + 1);
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
