/*******************************************************************************
 *  Copyright (c) 2008, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.text.edits.ReplaceEdit;

public abstract class OrmEclipseLinkConverter<T extends XmlNamedConverter>
	extends AbstractOrmXmlContextNode implements EclipseLinkConverter
{
	protected T resourceConverter;
	
	protected String name;
	
	
	protected OrmEclipseLinkConverter(XmlContextNode parent) {
		super(parent);
	}
		
	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
	
	
	protected T getXmlResource() {
		return this.resourceConverter;
	}
	
	public char getEnclosingTypeSeparator() {
		return '$';
	}

	protected EntityMappings getEntityMappings() {
		return (EntityMappings) getMappingFileRoot();
	}
	
	// **************** name ***************************************************
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.resourceConverter.setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	
	// **************** resource interaction ***********************************
	
	protected void initialize(T resource) {
		this.resourceConverter = resource;
		this.name = getResourceName();
	}
	
	public void update() {
		this.setName_(getResourceName());
		getPersistenceUnit().addConverter(this);
	}
	
	protected String getResourceName() {
		return this.resourceConverter.getName();
	}
	
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return EmptyIterable.instance();
	}


	// **************** validation *********************************************
	
	public boolean overrides(EclipseLinkConverter converter) {
		if (getName() == null) {
			return false;
		}
		// this isn't ideal, but it will have to do until we have further adopter input
		return this.getName().equals(converter.getName()) && converter instanceof JavaEclipseLinkConverter;
	}
	
	public boolean duplicates(EclipseLinkConverter converter) {
		return (this != converter)
				&& ! StringTools.stringIsEmpty(this.name)
				&& this.name.equals(converter.getName())
				&& ! this.overrides(converter)
				&& ! converter.overrides(this);
	}
	
	public TextRange getValidationTextRange() {
		return this.resourceConverter.getValidationTextRange();
	}
}