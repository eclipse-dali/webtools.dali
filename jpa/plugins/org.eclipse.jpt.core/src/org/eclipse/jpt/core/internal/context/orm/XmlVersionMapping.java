/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Version;
import org.eclipse.jpt.db.internal.Table;


public class XmlVersionMapping extends XmlAttributeMapping
	implements IVersionMapping, IXmlColumnMapping
{
	protected final XmlColumn column;

	protected TemporalType temporal;

	protected Version version;
	
	protected XmlVersionMapping(XmlPersistentAttribute parent) {
		super(parent);
		this.column = new XmlColumn(this, this);
	}

	@Override
	public int xmlSequence() {
		return 2;
	}

	public String getKey() {
		return IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlVersionMapping(this);
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.version.setTemporal(TemporalType.toOrmResourceModel(newTemporal));
		firePropertyChanged(IBasicMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}

	@Override
	public AttributeMapping addToResourceModel(TypeMapping typeMapping) {
		Basic basic = OrmFactory.eINSTANCE.createBasic();
		if (typeMapping.getAttributes() == null) {
			typeMapping.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		typeMapping.getAttributes().getBasics().add(basic);
		return basic;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getVersions().remove(this.version);
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}

	public String defaultTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Table dbTable(String tableName) {
		return typeMapping().dbTable(tableName);
	}

	public String defaultColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void initialize(Version version) {
		this.version = version;
		this.temporal = this.specifiedTemporal(version);
		this.column.initialize(version);
	}
	
	public void update(Version version) {
		this.version = version;
		this.setTemporal(this.specifiedTemporal(version));
		this.column.update(version);
	}
	
	protected TemporalType specifiedTemporal(Version version) {
		return TemporalType.fromOrmResourceModel(version.getTemporal());
	}

}
