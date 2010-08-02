/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.ReplaceEdit;

public class OrmEclipseLinkCustomizer extends AbstractOrmXmlContextNode
	implements EclipseLinkCustomizer
{
	protected final XmlCustomizerHolder resource;
	
	protected String specifiedCustomizerClass;
	
	protected String defaultCustomizerClass;
	
	protected JavaResourcePersistentType customizerPersistentType;

	public OrmEclipseLinkCustomizer(OrmTypeMapping parent, XmlCustomizerHolder resource, JavaEclipseLinkCustomizer javaCustomizer) {
		super(parent);
		this.resource = resource;
		this.defaultCustomizerClass = getJavaCustomizerClass(javaCustomizer);
		this.specifiedCustomizerClass = getResourceCustomizerClass();
		this.customizerPersistentType = getResourceCustomizerPersistentType();
	}

	
	// **************** EclipseLinkCustomizer impl *********************************

	public char getCustomizerClassEnclosingTypeSeparator() {
		return '$';
	}
	
	public String getCustomizerClass() {
		return this.specifiedCustomizerClass == null ? this.defaultCustomizerClass : this.specifiedCustomizerClass;
	}
	
	public String getDefaultCustomizerClass() {
		return this.defaultCustomizerClass;
	}
	
	protected void setDefaultCustomizerClass(String newDefaultCustomizerClass) {
		String oldDefaultCustomizerClass = this.defaultCustomizerClass;
		this.defaultCustomizerClass = newDefaultCustomizerClass;
		firePropertyChanged(DEFAULT_CUSTOMIZER_CLASS_PROPERTY, oldDefaultCustomizerClass, newDefaultCustomizerClass);
	}
	
	public String getSpecifiedCustomizerClass() {
		return this.specifiedCustomizerClass;
	}
	
	public void setSpecifiedCustomizerClass(String newCustomizerClass) {
		String oldCustomizerClass = this.specifiedCustomizerClass;
		this.specifiedCustomizerClass = newCustomizerClass;
		if (oldCustomizerClass != newCustomizerClass) {
			if (this.getResourceCustomizer() != null) {
				this.getResourceCustomizer().setClassName(newCustomizerClass);						
				if (this.getResourceCustomizer().isUnset()) {
					removeResourceCustomizer();
				}
			}
			else if (newCustomizerClass != null) {
				addResourceCustomizer();
				getResourceCustomizer().setClassName(newCustomizerClass);
			}
		}
		firePropertyChanged(SPECIFIED_CUSTOMIZER_CLASS_PROPERTY, oldCustomizerClass, newCustomizerClass);
	}
	
	protected void setSpecifiedCustomizerClass_(String newCustomizerClass) {
		String oldCustomizerClass = this.specifiedCustomizerClass;
		this.specifiedCustomizerClass = newCustomizerClass;
		firePropertyChanged(SPECIFIED_CUSTOMIZER_CLASS_PROPERTY, oldCustomizerClass, newCustomizerClass);
	}

	protected JavaResourcePersistentType getResourceCustomizerPersistentType() {
		XmlClassReference customizerClassRef = this.getResourceCustomizer();
		if (customizerClassRef == null) {
			return null;
		}

		String className = customizerClassRef.getClassName();
		if (className == null) {
			return null;
		}

		return this.getEntityMappings().resolveJavaResourcePersistentType(className);
	}

	protected EntityMappings getEntityMappings() {
		return (EntityMappings) getMappingFileRoot();
	}
	
	protected XmlClassReference getResourceCustomizer() {
		return this.resource.getCustomizer();
	}
	
	protected void addResourceCustomizer() {
		this.resource.setCustomizer(OrmFactory.eINSTANCE.createXmlClassReference());		
	}
	
	protected void removeResourceCustomizer() {
		this.resource.setCustomizer(null);
	}

	protected boolean isFor(String typeName) {
		if (this.customizerPersistentType != null && this.customizerPersistentType.getQualifiedName().equals(typeName)) {
			return true;
		}
		return false;
	}
	
	protected boolean isIn(IPackageFragment packageFragment) {
		if (this.customizerPersistentType != null) {
			return this.customizerPersistentType.isIn(packageFragment);
		}
		return false;
	}


	// **************** updating **************************************
	
	protected void update(JavaEclipseLinkCustomizer javaCustomizer) {
		setDefaultCustomizerClass(getJavaCustomizerClass(javaCustomizer));
		setSpecifiedCustomizerClass_(getResourceCustomizerClass());
		updateCustomizerPersistentType();
	}
	
	protected String getJavaCustomizerClass(JavaEclipseLinkCustomizer javaCustomizer) {
		return (javaCustomizer == null) ? null : javaCustomizer.getFullyQualifiedCustomizerClass();
	}
	
	protected String getResourceCustomizerClass() {
		XmlClassReference resource = getResourceCustomizer();
		return (resource == null) ? null : resource.getClassName();
	}
	
	protected void updateCustomizerPersistentType() {
		this.customizerPersistentType = this.getResourceCustomizerPersistentType();
	}


	//************************* refactoring ************************

	public Iterable<ReplaceEdit> createReplaceEdits(IType originalType, String newName) {
		if (this.isFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createReplaceEdit(originalType, newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createReplaceEdit(IType originalType, String newName) {
		return getResourceCustomizer().createReplaceEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createReplacePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.isIn(originalPackage)) {
			return new SingleElementIterable<ReplaceEdit>(this.createReplacePackageEdit(newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createReplacePackageEdit(String newName) {
		return getResourceCustomizer().createReplacePackageEdit(newName);
	}


	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		XmlClassReference resource = getResourceCustomizer();
		return resource == null ? null : resource.getClassNameTextRange();
	}

}
